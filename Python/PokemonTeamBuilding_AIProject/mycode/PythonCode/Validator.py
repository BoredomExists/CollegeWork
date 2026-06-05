from collections import Counter

# All valid natures for pokemon
VALIDNATURES = {
    "hardy", "lonely", "brave", "adamant", "naughty",
    "bold", "docile", "relaxed", "impish", "lax",
    "timid", "hasty", "serious", "jolly", "naive",
    "modest", "mild", "quiet", "bashful", "rash",
    "calm", "gentle", "sassy", "careful", "quirky"
}

# Function to modify names so alternate base forms match the regulation data
def CheckPokemonNames(name):
    name = name.strip().lower()

    aliases = {
        "tornadus-incarnate": "tornadus",
        "thundurus-incarnate": "thundurus",
        "landorus-incarnate": "landorus",

        "indeedee-female": "indeedee-f",
        "indeedee-male": "indeedee-m",

        "ogerpon-hearthflame": "ogerpon-hearthflame-mask",
        "ogerpon-wellspring": "ogerpon-wellspring-mask",
        "ogerpon-cornerstone": "ogerpon-cornerstone-mask"
    }

    return aliases.get(name, name)

# Function to find a pokemon within the regulation pokemon
def PokemonLookup(regulationPool):
    return {pokemon["name"]: pokemon for pokemon in regulationPool}

# Function validate a pokemon's item, ability, mvoes, and nature
def ValidatePokemon(member, pokemonLookup, itemLookup):
    errors = []

    species = member.get("name")
    item = member.get("item")
    ability = member.get("ability")
    moves = member.get("moves", [])
    nature = member.get("nature", "").lower()

    normalizedSpecies = CheckPokemonNames(species)

    if normalizedSpecies not in pokemonLookup:
        return errors

    pokemonData = pokemonLookup[normalizedSpecies]

    # Check item
    if item and item not in itemLookup:
        errors.append(f"{species} has invalid item: {item}")
    
    # Check ability
    validAbilities = {a["name"] for a in pokemonData.get("abilities", [])}
    if ability and ability not in validAbilities:
        errors.append(f"{species} has invalid ability: {ability}")
    
    # Check moves
    validMoves = set(pokemonData.get("moveset", []))
    for move in moves:
        if move not in validMoves:
            errors.append(f"{species} has invalid move: {move}")

    if nature and nature not in VALIDNATURES:
        errors.append(f"{species} has invalid nature: {member.get('nature')}")

    return errors

# Function to validate the team against the regulation rules
def ValidateTeam(team, regulationPool, rules, itemData=None):
    errors = []

    # Check team number
    if len(team) != rules["team_size"]:
        errors.append(f"Team must have exactly {rules['team_size']} Pokemon")

    pokemonLookup = PokemonLookup(regulationPool)

    # Check items
    itemLookup = set(itemData or [])
    if itemData and isinstance(itemData[0], dict):
        itemLookup = {item["name"] for item in itemData}

    restrictedCount = 0

    speciesNames = []
    itemNames = []

    # Check each pokemon within the team
    for member in team:
        species = member.get("name")
        item = member.get("item")

        if not species:
            errors.append("A team member is missing a name")
            continue

        normalizedSpecies = CheckPokemonNames(species)

        if normalizedSpecies not in pokemonLookup:
            errors.append(f"{species} is not legal in {rules['name']}")
            continue

        speciesNames.append(normalizedSpecies)

        if item:
            itemNames.append(item)

        if pokemonLookup[normalizedSpecies].get("restricted_regulation_i", False):
            restrictedCount += 1

        errors.extend(ValidatePokemon(member, pokemonLookup, itemLookup))

    # Check duplicate pokemon and items
    if rules.get("duplicate_species_clause", False):
        speciesCount = Counter(speciesNames)
        duplicateSpecies = [name for name, count in speciesCount.items() if count > 1]
        for name in duplicateSpecies:
            errors.append(f"Duplicate species not allowed: {name}")

    if rules.get("duplicate_item_clause", False):
        itemCount = Counter(itemNames)
        duplicateItems = [name for name, count in itemCount.items() if count > 1]
        for name in duplicateItems:
            errors.append(f"Duplicate item not allowed: {name}")

    if restrictedCount > rules.get("max_restricted", 0):
        errors.append(
            f"Team has {restrictedCount} restricted pokemon. Maximum allowed is {rules['max_restricted']}"
        )

    return {
        "valid": len(errors) == 0,
        "errors": errors
    }