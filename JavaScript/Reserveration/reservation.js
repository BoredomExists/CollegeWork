"use strict";

$(document).ready( () => {
	const emailPattern = /\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}\b/;

    $("#arrival_date").focus();

    $("#reservation_form").submit( event => {
        let isValid = true;

        const arrivalDate = $("#arrival_date").val().trim();
        if(arrivalDate == "")
        {
            $("#arrival_date").next().text("This field is required.");
            isValid = false;
        }
        else
        {
            $("#arrival_date").next().text("");
        }
        $("#arrival_date").val(arrivalDate);

        const nights = $("#nights").val().trim();
        if(isNaN(nights) || nights == "")
        {
            $("#nights").next().text("Must be numeric.");
            isValid = false;
        }
        else
        {
            $("#nights").next().text("");
        }
        $("#nights").val(nights);

        const name = $("#name").val().trim();
        if(name == "")
        {
            $("#name").next().text("This field is required.");
            isValid = false;
        }
        else
        {
            $("#name").next().text("");
        }
        $("#name").val(name);

        const email = $("#email").val().trim();
        if(email == "" || !emailPattern.test(email))
        {
            $("#email").next().text("Must be a valid email address.");
            isValid = false;
        }
        else
        {
            $("#email").next().text("");
        }
        $("#email").val(email);

        const phone = $("#phone").val().trim();
        if(phone == "")
        {
            $("#phone").next().text("This field is required.");
            isValid = false;
        }
        else
        {
            $("#phone").next().text("");
        }
        $("#phone").val(phone);

        if(isValid == false)
        {
            event.preventDefault();
        }
    });
});