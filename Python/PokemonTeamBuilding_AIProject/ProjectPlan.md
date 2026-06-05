Title: Pokemon Tournament Team Statistics


Key idea: (2-3 lines): Competitive Pokemon tournaments require players to carefully build teams with optimal movesets, EV/IV allocations, and team synergy, New or experienced players often struggle to determine which Pokemon combinations and strategies perform best in a given tournament format. The goal is to analyze team data to find effective team compositions, commonly used teammates, and estimated win/loss percentages against other commonly used teams.


Who will care when done: People who want to participate in competitive Pokemon tournaments but may not have the experience in building teams. The process of viewing online guides or looking at various videos may be time-consuming, and the project is a means to cleanly and clearly detail data on team compositions and why the composition works well together.


Data need: The data needed for this project would be the list of all the pokemon in the game, all items available in the game, the tournament format rules, common teams made for that format, movesets of the pokemon, and if possible, win/lose percentages of common teams against other teams.
AI Methods: The project will use a Large Language Model (LLM) as a baseline to summarize tournament data and possible team compositions based on commonly used strategies. A Rule-Based Expert System will be used to enforce tournament rules, so that only generated teams include pokemon, moves, and items that is a part of the tournament format. Monte Carlo Simulation will also be used as a means to simulate battles between teams in order to create an estimate on the win/lose probabilities. Lastly, a Genetic Algorithm will be used to iteratively improve team compositions by selecting and improvising teams that perform well in the simulation.


Evaluation: The project’s effectiveness will be measured by how well the generated teams perform against commonly used tournament teams. With the use of the Monte Carlo Simulations, the system will simulate a large number of battles between generated teams and popular teams. For now, we can create a baseline of 60% simulated win rate to determine if the system is effective or not. Also, the system will follow the tournament rules given so if the generated teams were to be used in the actual game, they would be able to be used under the format.
Users: The users for this project are people that want to play in pokemon tournaments but struggle with major aspects such as team compositions and strategies to use.


Trust issue: One main trust issue with this project is that the AI Model might give a team that would not perform well in a real battle due to not considering all aspects of competitive play. The model may give a team and explain how it is meant to be played but if the other player is able to understand what your team is and what is commonly used in the team composition, they may be able to correctly predict your next action in a battle and gain the winning advantage. Another trust issue is that the user may not understand why the model gave a specific team, so to maintain that trust, it will also involve an explanation as to why some decisions were made. The model may be bias is using some pokemon due to data on the pokemon. For example, a pokemon may be used more often due to having higher base statistics like attack or speed, or stronger abilities that other pokemon do not have. A solution for this may not be directly focused on because the model is intending on give data that will result in winning a lot of battles and in Pokemon general, a term called power creep (Releasing something, in this case a new pokemon, with better statistics), is commonly done so some pokemon may never be used in this model.


References for AI Models

LLM - LLaMA (Ollama or llama-cpp-python)

Monte Carlo Simulation - NumPy

Genetic Algorithm - PyGAD

InterpretML for Explanations - Logistic Regression (scikit-learn) and SHAP

Rule-Based System - Custom Python Logic and JSON Usage (Pokemon movesets and rules)


Reference for Data:

https://pokemondb.net/

https://limitlessvgc.com/

https://www.pokemon.com/us/play-pokemon/worlds/2025/vgc-masters

https://pokemonshowdown.com/

https://deepwiki.com/smogon/pokemon-showdown-client/8-web-apis?utm_source=chatgpt.com

https://www.smogon.com/

https://pkmn.github.io/smogon/data/stats/?utm_source=chatgpt.com
