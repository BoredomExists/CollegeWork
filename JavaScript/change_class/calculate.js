"use strict";

$(document).ready( () => {
    $("#calculate").click( () => {
        // get the number of cents from the user
        let cents = Math.floor( parseInt( $("#cents").val() ) );

        if (isNaN(cents) || cents < 0 || cents > 99) {
            alert("Please enter a valid number between 0 and 99");
        } else {
            const change = new Coins();
            change.makeChange(cents);
            $("#quarters").val( change.quarters );
            $("#dimes").val( change.dimes );
            $("#nickels").val( change.nickels );
            $("#pennies").val( change.pennies );
            
            // set focus on cents text box
            $("#cents").focus();
        }
    }); // end click() method
    
    // set focus on cents text box on initial load
    $("#cents").focus();
            
}); // end ready() method