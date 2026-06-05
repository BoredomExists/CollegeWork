"use strict";

$(document).ready( () => {
    $("#calculate").click( () => {
        let numOfCents = Math.floor(parseInt($("#cents").val()));
        if(isNaN(numOfCents) || numOfCents == "" || numOfCents < 0 || numOfCents > 99)
        {
            alert("Number of cents must be an integer that is between 0 and 99.");
            $("#cents").focus();
            $("#cents").select();
        }
        else
        {
        let quarterCount = numOfCents / 25;
        quarterCount = Math.floor(quarterCount);
        numOfCents = numOfCents % 25;

        let dimeCount = numOfCents / 10;
        dimeCount = Math.floor(dimeCount);
        numOfCents = numOfCents % 10;

        let nickelCount = numOfCents / 5;
        nickelCount = Math.floor(nickelCount);
        numOfCents = numOfCents % 5;

        let pennyCount = numOfCents % 5;
        pennyCount = Math.floor(pennyCount);


        $("#quarters").val(quarterCount);
        $("#dimes").val(dimeCount);
        $("#nickels").val(nickelCount);
        $("#pennies").val(pennyCount);
        $("#cents").focus();
    }
    $("#cents").focus();
    });

    $("#cents").focus();
});