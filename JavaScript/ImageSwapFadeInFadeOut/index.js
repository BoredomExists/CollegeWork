"use strict";

$(document).ready(() => {
    $("#image_list a").each((index, img) => {
        const swappedImage = new Image();
        swappedImage.src = $(img).attr("href");
    });

    $("#image_list a").click(evt => {
        const target = evt.currentTarget;

        $("#caption").fadeOut(1000);
        $("#image").fadeOut(1000, () => {
            const imageURL = $(target).attr("href");
            $("#image").attr("src", imageURL).fadeIn(1000);

            const caption = $(target).attr("title");
            $("#caption").text(caption).fadeIn(1000);

        });

        evt.preventDefault();
    });

    $("li:first-child a").focus();
}); 