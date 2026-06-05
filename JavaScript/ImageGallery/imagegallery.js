"use strict";

$(document).ready( () => {
    $("#image_list a").each( (index, link) => {
        const image = new Image();
        image.src = link.href;
    });

    $("#image_list a").click( evt => {
        const link = evt.currentTarget;

        $("#image").attr("src", link.href);

        $("#caption").text(link.title);

        evt.preventDefault();
    });

    $("#image_list a:first").focus();
});