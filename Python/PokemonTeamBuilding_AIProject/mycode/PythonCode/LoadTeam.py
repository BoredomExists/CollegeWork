import sys
import os
sys.path.append(os.path.abspath(".."))

from UniversalFunctions import *
from .Validator import ValidateTeam

# Function to validate the structure of the team before legal checks
def ValidateTeamStructure(team):
    errors = []
    
    if not isinstance(team, list):
        return {
            "valid": False,
            "errors": ["Team must be a list"]
        }
    
    # Check Team Members
    for index, member in enumerate(team, start=1):
        if not isinstance(member, dict):
            errors.append(f"Team member {index} must be an object")
            continue
        
        if not member.get("name"):
            errors.append(f"Team member {index} is missing 'name'")

        if not member.get("item"):
            errors.append(f"Team member {index} is missing 'item'")

        if not member.get("ability"):
            errors.append(f"Team member {index} is missing 'ability'")

        if not member.get("nature"):
            errors.append(f"Team member {index} is missing 'nature'")
        
        # Check Movesets
        moves = member.get("moves")
        if not isinstance(moves, list):
            errors.append(f"Team member {index} must have 'moves' as a list")
        else:
            if len(moves) != 4:
                errors.append(f"Team member {index} must have exactly 4 moves")
            
            for move_index, move in enumerate(moves, start=1):
                if not isinstance(move, str) or not move.strip():
                    errors.append(f"Team member {index} has an invalid move in slot {move_index}.")
           
        # Check EVs         
        evs = member.get("evs")
        if not isinstance(evs, dict):
            errors.append(f"Team member {index} must have 'evs' as an object.")
        else:
            requiredStats = ["hp", "atk", "def", "spa", "spd", "spe"]

            for stat in requiredStats:
                if stat not in evs:
                    errors.append(f"Team member {index} is missing EV field '{stat}'")
                elif not isinstance(evs[stat], int):
                    errors.append(f"Team member {index} EV '{stat}' must be an integer")
                elif evs[stat] < 0 or evs[stat] > 252:
                    errors.append(f"Team member {index} EV '{stat}' must be between 0 and 252")

            if all(stat in evs and isinstance(evs[stat], int) for stat in requiredStats):
                total_evs = sum(evs[stat] for stat in requiredStats)
                if total_evs > 510:
                    errors.append(f"Team member {index} has {total_evs} total EVs. Maximum is 510")
        
        # Check IVs         
        ivs = member.get("ivs")
        if not isinstance(ivs, dict):
            errors.append(f"Team Member {index} must have 'ivs' as an object.")
        else:
            requiredStats = ["hp", "atk", "def", "spa", "spd", "spe"]
            
            for stat in requiredStats:
                if stat not in ivs:
                    errors.append(f"Team member {index} is missing IV filed '{stat}'")
                elif not isinstance(ivs[stat], int):
                    errors.append(f"Team member {index} IV '{stat}' must be an integer")
                elif ivs[stat] != 31:
                    errors.append(f"Team member {index} IV '{stat}' must be 31")
        
        # Check gender         
        gender = member.get("gender", "")
        if gender not in ["", "M", "F"]:
            errors.append(f"Team member {index} has invalid gender '{gender}'. Use '', 'M', or 'F'")
            
    return {
        "valid": len(errors) == 0,
        "errors": errors
    }

# Function to load a team from a file and validate both the structure and legality of the team
def LoadandValidateTeam(teamFile, regulationPool, rules, itemData=None):
    team = LoadJSON(teamFile)
    
    # Validate team structure
    validTeamStructure = ValidateTeamStructure(team)
    if not validTeamStructure["valid"]:
        return {
            "valid": False,
            "stage": "structure",
            "errors": validTeamStructure["errors"],
            "team": team
        }
    
    # Validate legality
    legalTeam = ValidateTeam(team, regulationPool, rules, itemData)
    if not legalTeam["valid"]:
        return {
            "valid": False,
            "stage": "legality",
            "errors": legalTeam["errors"],
            "team": team
        }
    
    return {
        "valid": True,
        "stage": "complete",
        "errors": [],
        "team": team
    } 