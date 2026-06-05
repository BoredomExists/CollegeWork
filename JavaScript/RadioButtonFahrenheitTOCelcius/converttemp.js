"use strict";

const $ = selector => document.querySelector(selector);
const firstBox = $("#firstbox");
const secondBox = $("#secondbox");

const errorMsgs = () => {
    $("#celcius").value = "";
    const error = document.createElement("p");
    const errorText = document.createTextNode("You must enter a valid number for degrees.");
    error.appendChild(errorText);

    const button = $("#convert")
    const parent = button.parentNode;

    const node = $("p");
    if(node == null)
    {
        parent.insertBefore(error, button.nextElementSibling);
    }
    else
    {
        parent.replaceChild(p, node);
    }
    
}


const convertTemp = () => {
    let inputBox = parseFloat($("#fahrenheit").value);

    //Fix Error Message
    if (isNaN(inputBox) || inputBox == null) {
        errorMsgs();
    }
    else {
        if ($("#FtoC").checked) {
            const result = (inputBox - 32) * 5 / 9;
            $("#celcius").value = result.toFixed(1);
        }
        else if ($("#CtoF").checked) {
            const result = inputBox * 9 / 5 + 32;
            $("#celcius").value = result.toFixed(1);
        }
        $("p").remove();
    }

}

const FtoC = () => {
    firstBox.textContent = "Enter F Degrees:"
    secondBox.textContent = "Degrees Celcius:"
    $("#fahrenheit").value = "";
    $("#fahrenheit").focus();
    $("#celcius").value = "";
    $("p").remove();
}

const CtoF = () => {
    firstBox.textContent = "Enter C Degrees:"
    secondBox.textContent = "Degrees Fahrenheit:"
    $("#fahrenheit").value = "";
    $("#fahrenheit").focus();
    $("#celcius").value = "";
    $("p").remove();
}


document.addEventListener("DOMContentLoaded", () => {
    $("#convert").addEventListener("click", convertTemp);
    $("#FtoC").addEventListener("click", FtoC);
    $("#CtoF").addEventListener("click", CtoF);
});