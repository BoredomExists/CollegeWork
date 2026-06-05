import copy
import os
import random
import subprocess
import sys

sys.path.append(os.path.abspath(".."))

from UniversalFunctions import *
from .LoadTeam import LoadandValidateTeam
from .ConvertToShowdown import ConvertTeamToShowdownJSON

# Get Ollama AI team and showdown version, simulation report from the Monte Carlo Simulation
AI_TEAM_FILE = "../../data/GeneratedTeams/ollama_generated_team.json"
AI_SHOWDOWN_TEAM_FILE = "../../data/ShowdownFormattedTeams/ollama_generated_team_showdown.json"
SIMULATION_REPORT_FILE = "../../data/Output/simulation_report.json"
MONTE_CARLO_SIMULATION_FILE = "../SimulationCode/MonteCarloSimulation.js"

# Get the Genetic Algorithm teams
GA_DATASET_FILE = "../../data/Output/GA_evaluated_teams.json"

# Set variables
TEAM_COUNT = 10             # How many teams to test
TOTAL_ITERATIONS = 12       # How many iterations of teams to test
CHANGE_RATE = 0.15          # How likely to change an aspect of a team member
BEST_PARENT_COUNT = 3       # Get the best top 3 teams

# Get the regulation pokemon, rules, items
regulationPool = LoadJSON("../../data/pokemon_regulation_i.json")
regulationRules = LoadJSON("../../data/regulation_i_rules.json")
items = LoadJSON("../../data/items.json")

pokemonLookup = {p["name"]: p for p in regulationPool}

# Get the list of natures to change
VALID_NATURES = [
    "hardy", "lonely", "brave", "adamant", "naughty",
    "bold", "docile", "relaxed", "impish", "lax",
    "timid", "hasty", "serious", "jolly", "naive",
    "modest", "mild", "quiet", "bashful", "rash",
    "calm", "gentle", "sassy", "careful", "quirky"
]

# Function to run the monte carlo simulation
def RunSimulation():
    result = subprocess.run(
    ["node", MONTE_CARLO_SIMULATION_FILE],
    capture_output=True,
    text=True,
    encoding="utf-8",
    errors="replace"
)
    
    if result.returncode != 0:
        raise RuntimeError(f"Simulation Failed:\n{result.stderr}")
    
    report = LoadJSON(SIMULATION_REPORT_FILE)
    return report["averageWinRate"]

# Function to call when running the genetic algorithm so it runs for a certain amount of times
def RunRepeatedGeneticAlgorithm(team, totalRuns = 5):
    # Copy the best team
    currentBest = copy.deepcopy(team)
    currentBestResult = {
        "score": -1,
        "valid": False,
        "errors": [],
        "team": currentBest
    }
    
    runHistory = []
    
    # Start the runs of the Genetic Algorithm
    for run in range(1, totalRuns + 1):
        print(f"\n=========== GA Run {run}/{totalRuns} ===========")
        
        runBestResult = RunGeneticAlgorithm(currentBest, run)
        
        if runBestResult is None:
            runHistory.append({
                "run": run,
                "bestScore": None,
                "improved": False
            })
            continue
        
        # Check if the simulation for that team is better than the previous team's simulation, if so replace it
        improved = runBestResult["score"] > currentBestResult["score"]
        if improved:
            currentBestResult = copy.deepcopy(runBestResult)
            currentBest = copy.deepcopy(runBestResult["team"])
            
            WriteJSON("../../data/Output/final_best_GA_result.json", currentBestResult)
            WriteJSON("../../data/Output/final_best_GA_team.json", currentBest)
        
        runHistory.append({
            "run": run,
            "bestScore": runBestResult["score"],
            "improved": improved
        })
    
    # Write out all instances of the Genetic Algorithm
    WriteJSON("../../data/Output/GA_Run_History.json", runHistory)
    return currentBestResult
        
# Create the new pokemon set
def BuildPokemonSet(pokemonName, team, index):
    return {
        "name": pokemonName,
        "moves": GetRandomLegalMoves(pokemonName),
        "ability": GetRandomLegalAbility(pokemonName),
        "item": GetRandomLegalItem(team, index),
        "nature": GetRandomNature(),
        "gender": "",
        "evs": GetRandomEVSpread(),
        "ivs": {
            "hp": 31,
            "atk": 31,
            "def": 31,
            "spa": 31,
            "spd": 31,
            "spe": 31
        }
    }

# Function to get a random legal pokemon to replace
def GetRandomLegalPokemon(team, index):
    usedPokemon = set()
    
    # Add pokemon that were in the team to the used Pokemon set
    for i, member in enumerate(team):
        if i != index and member.get("name"):
            usedPokemon.add(member["name"])
         
    # Count number of restricted pokemon in the team  
    restrictedCount = 0
    for i, member in enumerate(team):
        if i == index:
            continue
        pokemonName = member.get("name")
        if pokemonLookup.get(pokemonName, {}).get("restricted_regulation_i", False):
            restrictedCount += 1
        
    legalChoices = []
    
    # Get available pokemon to select from
    for pokemon in regulationPool:
        name = pokemon["name"]
        
        if name in usedPokemon:
            continue
        
        isRestricted = pokemon.get("restricted_regulation_i", False)
        if isRestricted and restrictedCount >= regulationRules["max_restricted"]:
            continue
        
        legalChoices.append(name)
        
    if not legalChoices:
        return team[index]["name"]
    
    return random.choice(legalChoices)

# Function to replace an item on a team member
def GetRandomLegalItem(team, index):
    usedItems = set()
    
    # Add items that already existed on the team to the used items set
    for i, member in enumerate(team):
        if i != index and member.get("item"):
            usedItems.add(member["item"])
    
    # Get all the items available   
    itemNames = items
    if items and isinstance(items[0], dict):
        itemNames = [item["name"] for item in items]
        
    legalChoices = [item for item in itemNames if item not in usedItems]
    
    if not legalChoices:
        return team[index]["item"]
    
    return random.choice(legalChoices)

# Function to replace a random move of a team member
def GetRandomLegalMoves(pokemonName):
    moveset = pokemonLookup[pokemonName].get("moveset", [])
    if len(moveset) < 4:
        return pokemonLookup[pokemonName].get("moveset", [])
    
    return random.sample(moveset, 4)

# Function to replace a random ability of a team member
def GetRandomLegalAbility(pokemonName):
    abilities = [a["name"] for a in pokemonLookup[pokemonName].get("abilities", [])]
    if not abilities:
        return ""
    return random.choice(abilities)

# Function to replace a random nature of a team member
def GetRandomNature():
    return random.choice(VALID_NATURES)

# Function to replace the EV spread with another ev spread
def GetRandomEVSpread():
    stats = ["hp", "atk", "def", "spa", "spd", "spe"]
    evs = {stat: 0 for stat in stats}
    
    chosenStats = random.sample(stats, k=2)
    evs[chosenStats[0]] = 252
    evs[chosenStats[1]] = 252
    
    remainingStats = [s for s in stats if s not in chosenStats]
    evs[random.choice(remainingStats)] = 4
    
    return evs

# Function to change the team
def ChangeTeam(team):
    changedTeam = copy.deepcopy(team)

    for i in range(len(changedTeam)):
        if random.random() > CHANGE_RATE:
            continue

        changedCount = random.choice([1, 2])
        choicesToChange = ["species", "moves", "item", "ability", "nature", "evs"]
        chosenChanges = random.sample(choicesToChange, k=changedCount)

        # Change the pokemon and its set within the team
        for change in chosenChanges:
            if change == "species":
                newPokemon = GetRandomLegalPokemon(changedTeam, i)
                changedTeam[i] = BuildPokemonSet(newPokemon, changedTeam, i)
            elif change == "moves":
                changedTeam[i]["moves"] = GetRandomLegalMoves(changedTeam[i]["name"])
            elif change == "item":
                changedTeam[i]["item"] = GetRandomLegalItem(changedTeam, i)
            elif change == "ability":
                changedTeam[i]["ability"] = GetRandomLegalAbility(changedTeam[i]["name"])
            elif change == "nature":
                changedTeam[i]["nature"] = GetRandomNature()
            elif change == "evs":
                changedTeam[i]["evs"] = GetRandomEVSpread()

    return changedTeam

# Add the newly evaluated team to the data
def AppendEvaluatedTeamData(dataEntry):
    if os.path.exists(GA_DATASET_FILE):
        data = LoadJSON(GA_DATASET_FILE)
    else:
        data = []
    
    data.append(dataEntry)
    WriteJSON(GA_DATASET_FILE, data)

# Function to evaluate the newly generated team
def EvaluateTeam(team, generation = None, runNumber = None, teamIndex = None):
    WriteJSON(AI_TEAM_FILE, team)
    
    validGATeam = LoadandValidateTeam(AI_TEAM_FILE, regulationPool, regulationRules, items)
    
    if not validGATeam["valid"]:
        result = {
            "score": -1,
            "valid": False,
            "errors": validGATeam["errors"],
            "team": team
        }
        
        AppendEvaluatedTeamData({
            "run": runNumber,
            "generation": generation,
            "teamIndex": teamIndex,
            "score": -1,
            "valid": False,
            "errors": validGATeam["errors"],
            "team": team
        })
        return result
        
    showdownTeam = ConvertTeamToShowdownJSON(validGATeam["team"], regulationRules["battle_level"])
    WriteJSON(AI_SHOWDOWN_TEAM_FILE, showdownTeam)
    try:
        score = RunSimulation()
    except Exception as e:
        result = {
            "score": -1,
            "valid": False,
            "errors": [str(e)],
            "team": team
        }
        
        AppendEvaluatedTeamData({
            "run": runNumber,
            "generation": generation,
            "teamIndex": teamIndex,
            "score": -1,
            "valid": False,
            "errors": [str(e)],
            "team": team
        })
        
        return result
    
    result = {
        "score": score,
        "valid": True,
        "errors": [],
        "team": team
    }

    AppendEvaluatedTeamData({
        "run": runNumber,
        "generation": generation,
        "teamIndex": teamIndex,
        "score": score,
        "valid": True,
        "errors": [],
        "team": team
    })

    return result

# Function to create the first population of teams
def MakeFirstTeam(seedTeam):
    population = [copy.deepcopy(seedTeam)]
    
    # Keep creating new versions of the team until the team count is reached
    while len(population) < TEAM_COUNT:
        population.append(ChangeTeam(seedTeam))
        
    return population

# Function to select the best valid teams from the population
def SelectBestTeams(evaluatedPopulation):
    validPopulation = [entry for entry in evaluatedPopulation if entry["valid"]]

    if not validPopulation:
        return []

    # Sort by score from high to low
    sortedPopulation = sorted(validPopulation, key=lambda x: x["score"], reverse=True)
    return sortedPopulation[:BEST_PARENT_COUNT]

# Function to merge team members from two parent teams
def MergeBestTeamMembers(parent1, parent2):
    child = []

    for i in range(len(parent1)):
        chosenParent = random.choice([parent1, parent2])
        child.append(copy.deepcopy(chosenParent[i]))

    return child

# Function to create the next iteration of teams
def CreateNextGeneration(elites, seedTeam):
    if not elites:
        return MakeFirstTeam(seedTeam)

    nextGen = [copy.deepcopy(entry["team"]) for entry in elites]

    while len(nextGen) < TEAM_COUNT:
        parent1 = copy.deepcopy(random.choice(elites)["team"])
        parent2 = copy.deepcopy(random.choice(elites)["team"])

        child = MergeBestTeamMembers(parent1, parent2)
        child = ChangeTeam(child)
        nextGen.append(child)

    return nextGen

# Function to run a single genetic algorithm process
def RunGeneticAlgorithm(seedTeam, runNumber=None):
    teamPopulation = MakeFirstTeam(seedTeam)
    bestOverall = None
    iterationHistory = []
    
    for gen in range(1, TOTAL_ITERATIONS + 1):
        print(f"\nGeneration {gen}")
        evaluatedPopulation = []
        
        # Evaluate every team in the current population
        for index, team in enumerate(teamPopulation, start=1):
            print(f"Evaluating Team {index}/{len(teamPopulation)}")
            result = EvaluateTeam(team, generation=gen, runNumber=runNumber, teamIndex=index)
            evaluatedPopulation.append(result)
            
            if result["valid"]:
                print(f"Score: {result['score']}")
            else:
                print(f"Invalid Team: {result['errors']}")
                
        validResults = [e for e in evaluatedPopulation if e["valid"]]
        
        # Get the ebst valid team of the current iteration
        if validResults:
            genBest = max(validResults, key=lambda x: x["score"])
            iterationHistory.append({
            "generation": gen,
            "bestScore": genBest["score"]
            })
            
            # Update the best overall team
            if bestOverall is None or genBest["score"] > bestOverall["score"]:
                bestOverall = copy.deepcopy(genBest)
                WriteJSON("../../data/Output/best_ga_result.json", bestOverall)
                
            print(f"Best Score This Generation: {genBest['score']}")
            print(f"Best Overall Score: {bestOverall['score']}")
        else:
            print("No valid teams in this generation")
        
        elites = SelectBestTeams(evaluatedPopulation)
        teamPopulation = CreateNextGeneration(elites, seedTeam)
        
    WriteJSON("../../data/Output/ga_generation_history.json", iterationHistory)
    return bestOverall