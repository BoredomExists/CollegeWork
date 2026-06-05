# Function for Python Notebooks to use for getting and writting JSON files

import json

def LoadJSON(path):
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)
    
def WriteJSON(path, data):
    with open(path, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=2)