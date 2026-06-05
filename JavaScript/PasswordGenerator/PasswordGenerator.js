"use strict";

const getRandomNumber = max => {
    let random = null;
    if(!isNaN(max))
    {
        random = Math.random();
        random = Math.floor(random * max);
        random = random + 1;
    }
    return random;
};

$(document).ready( () => {
    $("#generate").click( () => {
        $("#password").val( "" );
        
        const chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-+!@";

        const numOfChar = $("#num").val();
        let newPassword = "";

        if(isNaN(numOfChar))
        {
            alert("Please enter a valid number.");
            $("#num").focus();
            $("#num").select();
        }
        else
        {
            for(let i = 0; i < numOfChar; i++)
            {
                let charsIndex = getRandomNumber(chars.length);
                newPassword += chars.charAt(charsIndex);
            }
        }
        $("#password").val(newPassword);
    });

    $("#clear").click( () => {
        $("#num").val( "" );
        $("#password").val( "" );
        $("#num").focus();
    });

    $("#num").focus();
})