"use strict";

$(document).ready( () => {
    $("#jdom a").click( evt => {

        const a = evt.currentTarget;

        $(a).prev().toggleClass("hide");

        if($(a).prev().attr("class") != "hide")
        {
            $(a).prev().show();
            $(a).text("Show less");
        }
        else
        {
            $(a).prev().hide();
            $(a).text("Show more");
        }

        evt.preventDefault();
    });
    
    $("#jdom").find("a:first").focus();
 });