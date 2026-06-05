"use strict";

const displayScores = (scores, scoresString) => {
    let total = 0;
    for(let i of scores)
    {
        total += parseInt(i);
    }
    const avg = total / scores.length;
    $("#average_score").text(avg.toFixed(2));

    //Output needs to be fixed and check sort button when fixed
    let msg = scoresString.join("\n");
    $("#scores").text(msg);
    
};

$(document).ready( () => {
    const scores = [];
    const studentInfo = [];

    $("#add_button").click( () => {
        const score = $("#score").val();
        const firstName = $("#first_name").val();
        const lastName = $("#last_name").val();

        scores.push(score);
        studentInfo.push([lastName, firstName, score]);

        console.log(studentInfo)
        displayScores(scores, studentInfo);
        
        // get the add form ready for next entry
        $("#first_name").val( "" );
        $("#last_name").val( "" );
        $("#score").val( "" );
        $("#first_name").focus();
    }); // end click()
    
    $("#clear_button").click( () => {
        // remove the score data from the web page
        $("#average_score").text( "" );
        $("#scores").text( "" );
        scores.length = 0;
        studentInfo.length = 0;

        $("#first_name").focus();
    }); // end click()
       
    $("#sort_button").click( () => {
        studentInfo.sort();

        displayScores(scores, studentInfo);
    }); // end click()
    
    $("#first_name").focus();
}); // end ready()