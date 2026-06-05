"use strict";

const scores = [];

const displayScores = () => scores.join(", ");

const calculateAverage = () => {
	const total = scores.reduce( (prev, curr) => prev + parseInt(curr), 0);
	return total / scores.length;
};

// load user entries in scores array
for (const element of process.argv)
{
	if(isNaN(element)) continue;

	scores.push(element);
}
// display all scores
console.log(`All Scores: ${displayScores()}`);

// display average score
console.log(`Average Score: ${calculateAverage().toFixed(0)}`);
