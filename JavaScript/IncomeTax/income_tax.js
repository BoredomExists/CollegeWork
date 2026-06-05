"use strict";

const $ = selector => document.querySelector(selector);

const getErrorMsg = err => `${err} must be a valid number greater than zero.`;

const focusAndSelect = selector => {
    const elem = $(selector)
    elem.focus();
    elem.select();
}

const processEntries = () => {
    const tax = parseInt($('#income').value);

    if(isNaN(tax) || tax <= 0) {
        alert(getErrorMsg('Taxable Income'));
        focusAndSelect('#income');
    }
    else
    {
        if(tax > 0 && tax <= 9875)
        {
            $('#income_owed').value = (0 + (tax - 0) * .10).toFixed(2);
        }
        else if (tax > 9875 && tax <= 40125)
        {
            $('#income_owed').value = (987.50 + (tax - 9875) * .12).toFixed(2);
        }
        else if (tax > 40125 && tax <= 85525)
        {
            $('#income_owed').value = (4617.50 + (tax - 40125) * .22).toFixed(2);
        }
        else if (tax > 85525 && tax <= 163300)
        {
            $('#income_owed').value = (14605.50 + (tax - 85525) * .24).toFixed(2);
        }
        else if (tax > 163300 && tax <= 207350)
        {
            $('#income_owed').value = (33271.50 + (tax - 163300) * .32).toFixed(2);
        }
        else if (tax > 207350 && tax <= 518400)
        {
            $('#income_owed').value = (47367.50 + (tax - 207350) * .35).toFixed(2);
        }
        else if (tax > 518400)
        {
            $('#income_owed').value = (156235.00 + (tax - 518400) * .37).toFixed(2);
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    $('#calculate').addEventListener('click', processEntries);
    $('#income').focus();
});