// Imports

const fs = require("fs");
const path = require("path");
const { BattleStream, Teams, getPlayerStreams } = require("pokemon-showdown");
const { RandomPlayerAI } = require("pokemon-showdown/dist/sim/tools/random-player-ai");

// Get the showdown teams of the Ollama AI and the tournament teams
const AITeamPath = path.resolve(
    __dirname,
    "..",
    "..",
    "data",
    "ShowdownFormattedTeams",
    "ollama_generated_team_showdown.json"
);

const opponentTeamsPath = path.resolve(
    __dirname,
    "..",
    "..",
    "data",
    "ShowdownFormattedTeams",
    "Limitless426"
);

// Get the output folder to put the final result at
const outputFolder = path.resolve(
    __dirname,
    "..",
    "..",
    "data",
    "Output"
);

const outputFile = path.join(outputFolder, "simulation_report.json");

// Function to load the JSON
function LoadJSON(filePath) {
    return JSON.parse(fs.readFileSync(filePath, "utf-8"));
}

// Function to write the JSON
function WriteJSON(filePath, data) {
    fs.writeFileSync(filePath, JSON.stringify(data, null, 2), "utf-8");
}

// Function to get all the teams for the AI to battle against
function GetOpponentTeams(folderPath, aiFilePath) {
    const aiFileName = path.basename(aiFilePath);

    return fs.readdirSync(folderPath)
        .filter(file => file.endsWith(".json"))
        .filter(file => file !== aiFileName)
        .map(file => path.join(folderPath, file));
}

// Function to run the showdown simulation
async function RunBattle(aiTeam, opponentTeam, timeoutMS = 30000) {
    const streams = getPlayerStreams(new BattleStream());
    let winner = null;
    let battleFinished = false;

    const player1AI = new RandomPlayerAI(streams.p1);
    const player2AI = new RandomPlayerAI(streams.p2);

    void player1AI.start();
    void player2AI.start();

    const monitorTask = (async () => {
        for await (const output of streams.omniscient) {
            const lines = output.split("\n");

            // Split certain outputted lines to get important information such as winner
            for (const line of lines) {
                if (line.startsWith("|win|")) {
                    winner = line.split("|")[2];
                    battleFinished = true;
                    return;
                }

                if (line.startsWith("|tie|")) {
                    winner = "Tie";
                    battleFinished = true;
                    return;
                }
            }
        }
    })();

    const format = {
        formatid: "gen9vgc2026regi"
    };

    const player1 = {
        name: "AI",
        team: Teams.pack(aiTeam)
    };

    const player2 = {
        name: "Opponent",
        team: Teams.pack(opponentTeam)
    };

    // Write out to terminal which battles started
    streams.omniscient.write(`>start ${JSON.stringify(format)}`);
    streams.omniscient.write(`>player p1 ${JSON.stringify(player1)}`);
    streams.omniscient.write(`>player p2 ${JSON.stringify(player2)}`);

    const startTime = Date.now();

    while (!battleFinished) {
        if (Date.now() - startTime > timeoutMS) {
            throw new Error("Battle timed out");
        }

        await new Promise(resolve => setTimeout(resolve, 10));
    }

    await monitorTask;
    return winner;
}

// Function to get the information of a completed battle such as wins
async function EvaluateOpponentTeam(aiTeam, opponentFilePath, runs = 25) {
    const opponentTeam = LoadJSON(opponentFilePath);
    const opponentName = path.basename(opponentFilePath, ".json");

    let aiWins = 0;
    let opponentWins = 0;
    let ties = 0;
    let failedBattles = 0;

    for (let i = 0; i < runs; i++) {
        try {
            const winner = await RunBattle(aiTeam, opponentTeam);

            // Check winner after each battle
            if (winner === "AI") {
                aiWins++;
            } else if (winner === "Opponent") {
                opponentWins++;
            } else if (winner === "Tie") {
                ties++;
            }

            console.log(`${opponentName} - Battle ${i + 1}/${runs}: ${winner}`);
        } catch (err) {
            failedBattles++;
            console.log(`${opponentName} - Battle ${i + 1}/${runs}: FAILED (${err.message})`);
        }
    }

    const completedBattles = aiWins + opponentWins + ties;

    return {
        opponentTeam: opponentName,
        runs: runs,
        completedBattles: completedBattles,
        failedBattles: failedBattles,
        aiWins: aiWins,
        opponentWins: opponentWins,
        ties: ties,
        aiWinRate: completedBattles > 0 ? Number(((aiWins / completedBattles) * 100).toFixed(2)) : 0,
        opponentWinRate: completedBattles > 0 ? Number(((opponentWins / completedBattles) * 100).toFixed(2)) : 0
    };
}

// Function to create the simluation report
function ReportSimulation(aiFilePath, runsPerOpponent, matchupResults) {
    const totalAIWinRate = matchupResults.reduce((sum, result) => sum + result.aiWinRate, 0);

    const avgWinRate = matchupResults.length > 0
        ? Number((totalAIWinRate / matchupResults.length).toFixed(2))
        : 0;

    return {
        aiTeamFile: path.basename(aiFilePath),
        runsPerOpponent: runsPerOpponent,
        opponentTeamCount: matchupResults.length,
        averageWinRate: avgWinRate,
        matchupResults: matchupResults
    };
}

// Function to start the Monte Carlo Simulation
async function StartMonteCarloSimulation(runsPerOpponent = 25) {
    const AITeam = LoadJSON(AITeamPath);
    const opponentFiles = GetOpponentTeams(opponentTeamsPath, AITeamPath);
    const matchupResults = [];

    for (const opponentFilePath of opponentFiles) {
        console.log(`\nStarting match vs ${path.basename(opponentFilePath)}`);

        try {
            const result = await EvaluateOpponentTeam(AITeam, opponentFilePath, runsPerOpponent);
            matchupResults.push(result);
        } catch (error) {
            console.log(`Failed matchup vs ${path.basename(opponentFilePath)}: ${error.message}`);
        }
    }

    const report = ReportSimulation(AITeamPath, runsPerOpponent, matchupResults);
    WriteJSON(outputFile, report);

    console.log("\nResults");
    console.log(`Opponent teams tested: ${report.opponentTeamCount}`);
    console.log(`Average AI win rate: ${report.averageWinRate}%`);
    console.log(`Saved report to: ${outputFile}`);
}

StartMonteCarloSimulation(10).catch(console.error);