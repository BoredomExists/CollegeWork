from UniversalFunctions import *
from .LoadTeam import LoadandValidateTeam

# Format the name so the validation works properly
def FormatName(value):
    return " ".join(part.capitalize() for part in value.split("-"))

# Update the team JSON file for it to be used in the showdown simulation without error
def FormatToShowdown(member, battle_level=50):
    return {
        "name": FormatName(member["name"]),
        "species": FormatName(member["name"]),
        "item": FormatName(member["item"]),
        "ability": FormatName(member["ability"]),
        "moves": [FormatName(move) for move in member.get("moves", [])],
        "nature": FormatName(member.get("nature", "")),
        "gender": member.get("gender", ""),
        "evs": {
            "hp": member.get("evs", {}).get("hp", 0),
            "atk": member.get("evs", {}).get("atk", 0),
            "def": member.get("evs", {}).get("def", 0),
            "spa": member.get("evs", {}).get("spa", 0),
            "spd": member.get("evs", {}).get("spd", 0),
            "spe": member.get("evs", {}).get("spe", 0),
        },
        "ivs": {
            "hp": member.get("ivs", {}).get("hp", 31),
            "atk": member.get("ivs", {}).get("atk", 31),
            "def": member.get("ivs", {}).get("def", 31),
            "spa": member.get("ivs", {}).get("spa", 31),
            "spd": member.get("ivs", {}).get("spd", 31),
            "spe": member.get("ivs", {}).get("spe", 31),
        },
        "level": battle_level,
    }

# Function to call for converting a JSON to the Showdown Format
def ConvertTeamToShowdownJSON(team, battle_level=50):
    return [FormatToShowdown(member, battle_level) for member in team]

# Function to call to get the datasets of the teams
def GetTournamentTeamDatasets(dataset):
    if isinstance(dataset, dict) and "teams" in dataset:
        return dataset["teams"]
    return dataset