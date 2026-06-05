"use strict";
$(document).ready( () => {

    const slider = $("#image_list");      

    $("#right_button").click( () => { 

        const leftProperty = parseInt(slider.css("left"));
        
        let newLeftProperty = 0;
        if (leftProperty - 300 > -900) {
            newLeftProperty = leftProperty - 300;
        }
        
        slider.animate({left: newLeftProperty}, 1000);    
    }); 
    
    $("#left_button").click( () => {
        const leftProperty = parseInt(slider.css("left"));
        
        let newLeftProperty = 0;
        if (leftProperty < 0) {
            newLeftProperty = leftProperty + 300;
        }
        slider.animate({left: newLeftProperty}, 1000);
    });

    $("#image_list a").click(evt => {
        const target = evt.currentTarget;

        $("#image").animate({opacity: 0, marginLeft: "-205"}, 1000, () => {
            const imageURL = $(target).attr("href");
            $("#image").attr("src", imageURL).animate({ opacity: 205, marginLeft: "+100"}, 1000);
        });
        
        evt.preventDefault();
    });
});