### 1. TC-identifier: 

TC-Pokemon_Tournament_Team_Statistics-01

### 2. TC-name: 

Legal Team Recommendation for Tournament Format

### 3. TC-objective: 

To determine if the AI can generate a competitive Pokemon team that follows a tournament format, using valid pokemon/items/moves, and explains why the team work work together.

### 4. TC-input: 

Pokemon and their:

- Base Statistics

- Movesets

Other Data include:

- Items in the game

- The current tournament format rules

- Commonly used teams in the current format

- If possible, win/lose percentages against commonly used teams

### 5. TC-reference-output: 

The model with the given input should output:

- A pokemon team of 6 that is legal

- Valid Items, moves, abilities, and basic roles for each pokemon

- A short explanation as to why each pokemon is used on the team

- A description of team synergy and battle strategy

- A win/loss percentage of a predicted team against commonly used teams

### 6. TC-harm-risk-info: 

HC1-incorrect-info is one of the risk information that the model can have because the model can give illegal pokemon, teams, items, and give inaccurate competitive information for the user. Also, the percentage chances could be a risk factor since not all aspects of a tournament match goes into play such as the player's ability to make predictions on what the opponent will do or if someone brings a team that has nothing correlating with any teams given in the input.

### 7. TC-other-info: 

For some of the AI models, they will be used along side other software such as the Monte Carlo Simulation method being used with the Pokemon Showdown website to simulate pokemon battles. The project will also use a variety of AI models, so more risk factors can appear due to the use of multiple AI models and what risk factors come with them individually.
