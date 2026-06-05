import os
import sys
sys.path.append(os.path.abspath(".."))

from UniversalFunctions import *

# Get the pokemon that is allowed in the regulation
regulationPool = LoadJSON("../../data/pokemon_regulation_i.json")
pokemonLookup = {p["name"]: p for p in regulationPool}

# Count the number of physical, special, and support pokemon that is on the team
def CountPhysicalSpecialSupportPokemon(team):
    physicalCount = 0
    specialCount = 0
    supportCount = 0

    # For each member check the moves, if they have a physical, special, or support move
    # Add to the counters respectively
    for member in team:
        pokemonName = member["name"]
        moves = member.get("moves", [])
        moveDetails = pokemonLookup.get(pokemonName, {}).get("move_details", {})

        hasPhysical = False
        hasSpecial = False
        hasSupport = False

        for move in moves:
            detail = moveDetails.get(move, {})
            category = detail.get("category", "").lower()

            if category == "physical":
                hasPhysical = True
            elif category == "special":
                hasSpecial = True
            elif category == "status":
                hasSupport = True

        if hasPhysical:
            physicalCount += 1
        if hasSpecial:
            specialCount += 1
        if hasSupport:
            supportCount += 1

    return physicalCount, specialCount, supportCount

# Function to check if a pokemon has a specific move
def HasMove(team, targetMoves):
    for member in team:
        for move in member.get("moves", []):
            if move in targetMoves:
                return 1
    return 0

# Function to check if a pokemon has a specific ability
def HasAbility(team, targetAbilities):
    for member in team:
        if member.get("ability") in targetAbilities:
            return 1
        
    return 0

# Function to count the number of specific moves a team member may have
def CountMoves(team, targetMoves):
    count = 0

    for member in team:
        for move in member.get("moves", []):
            if move in targetMoves:
                count += 1

    return count

# Function to count how many pokemon has specific abilities
def CountAbilities(team, targetAbilities):
    count = 0

    for member in team:
        if member.get("ability") in targetAbilities:
            count += 1

    return count

# Function to count how many restricted pokemon are in the team
def CountRestricted(team):
    count = 0

    for member in team:
        pokemonName = member["name"]
        if pokemonLookup.get(pokemonName, {}).get("restricted_regulation_i", False):
            count += 1

    return count

# Function to count how many pokemon that have priority moves that are on the team
def CountPriorityPokemon(team):
    count = 0

    for member in team:
        pokemonName = member["name"]
        moves = member.get("moves", [])
        moveDetails = pokemonLookup.get(pokemonName, {}).get("move_details", {})

        for move in moves:
            detail = moveDetails.get(move, {})
            if detail.get("priority", 0) > 0:
                count += 1
                break

    return count

# Function to count how many pokemon on the team have spread moves
def CountSpreadMovePokemon(team):
    count = 0

    spreadTargets = {"alladjacentfoes", "alladjacent", "foeside"}

    for member in team:
        pokemonName = member["name"]
        moves = member.get("moves", [])
        moveDetails = pokemonLookup.get(pokemonName, {}).get("move_details", {})

        for move in moves:
            detail = moveDetails.get(move, {})
            if detail.get("target", "").lower() in spreadTargets:
                count += 1
                break

    return count

# Function to count how many status moves a pokemon team has
def CountStatusMoves(team):
    count = 0

    for member in team:
        pokemonName = member["name"]
        moves = member.get("moves", [])
        moveDetails = pokemonLookup.get(pokemonName, {}).get("move_details", {})

        for move in moves:
            detail = moveDetails.get(move, {})
            if detail.get("category", "").lower() == "status":
                count += 1

    return count

# Function to check if the team has a weather changing pokemon like rain or sun
def HasWeather(team):
    weatherMoves = {"rain-dance", "sunny-day", "snowscape", "sandstorm"}
    weatherAbilities = {"drizzle", "drought", "snow-warning", "sand-stream"}

    return 1 if CountMoves(team, weatherMoves) > 0 or CountAbilities(team, weatherAbilities) > 0 else 0

# Function to check  if the team has a terrain changing pokemon
def HasTerrain(team):
    terrainMoves = {"electric-terrain", "grassy-terrain", "misty-terrain", "psychic-terrain"}
    terrainAbilities = {"electric-surge", "grassy-surge", "misty-surge", "psychic-surge"}

    return 1 if CountMoves(team, terrainMoves) > 0 or CountAbilities(team, terrainAbilities) > 0 else 0

# Function to extract the features used in the Logistic Regression dataset from a given team
def ExtractTeamFeatures(team):
    physicalCount, specialCount, supportCount = CountPhysicalSpecialSupportPokemon(team)

    protectMoves = {"protect", "detect", "spiky-shield", "burning-bulwark"}
    fakeOutMoves = {"fake-out"}
    redirectionMoves = {"follow-me", "rage-powder"}
    pivotMoves = {"u-turn", "volt-switch", "parting-shot"}
    setupMoves = {"calm-mind", "nasty-plot", "swords-dance", "howl", "coaching", "iron-defense"}
    weatherMoves = {"rain-dance", "sunny-day", "snowscape", "sandstorm"}
    terrainMoves = {"electric-terrain", "grassy-terrain", "misty-terrain", "psychic-terrain"}

    features = {
        "restricted_count": CountRestricted(team),
        "physical_count": physicalCount,
        "special_count": specialCount,
        "support_count": supportCount,
        "priority_user_count": CountPriorityPokemon(team),

        "protect_count": CountMoves(team, protectMoves),
        "fake_out_count": CountMoves(team, fakeOutMoves),
        "redirection_count": CountMoves(team, redirectionMoves),
        "pivot_move_count": CountMoves(team, pivotMoves),
        "setup_move_count": CountMoves(team, setupMoves),
        "spread_move_user_count": CountSpreadMovePokemon(team),
        "status_move_count": CountStatusMoves(team),
        "weather_setter_count": CountMoves(team, weatherMoves),
        "terrain_setter_count": CountMoves(team, terrainMoves),
        "intimidate_count": CountAbilities(team, {"intimidate"}),

        "has_fake_out": 1 if CountMoves(team, fakeOutMoves) > 0 else 0,
        "has_tailwind": 1 if CountMoves(team, {"tailwind"}) > 0 else 0,
        "has_trick_room": 1 if CountMoves(team, {"trick-room"}) > 0 else 0,
        "has_redirection": 1 if CountMoves(team, redirectionMoves) > 0 else 0,
        "has_wide_guard": 1 if CountMoves(team, {"wide-guard"}) > 0 else 0,
        "has_speed_control": 1 if CountMoves(team, {"tailwind", "trick-room", "icy-wind", "thunder-wave", "nuzzle", "electroweb"}) > 0 else 0,
        "has_protect": 1 if CountMoves(team, protectMoves) > 0 else 0,
        "has_setup": 1 if CountMoves(team, setupMoves) > 0 else 0,
        "has_intimidate": 1 if CountAbilities(team, {"intimidate"}) > 0 else 0,
        "has_weather": HasWeather(team),
        "has_terrain": HasTerrain(team)
    }

    return features

# Function to build the dataset based on a Genetic Algorithm Improved team
def BuildDataset(GA_EVALUATION_FILE, DATASET_FILE, scoreThreshold = 50, sourceName = ""):
    evaluatedTeams = LoadJSON(GA_EVALUATION_FILE)
    datasetRows = []
    
    for entry in evaluatedTeams:
        if not entry.get("valid", False):
            continue
        
        team = entry["team"]
        score = entry["score"]
        
        row = {
            "source": sourceName,
            "run": entry.get("run"),
            "generation": entry.get("generation"),
            "teamIndex": entry.get("teamIndex"),
            "score": score,
            "label": 1 if score >= scoreThreshold else 0,
            "team": team
        }
        
        row.update(ExtractTeamFeatures(team))
        datasetRows.append(row)
        
    WriteJSON(DATASET_FILE, datasetRows)
    return datasetRows