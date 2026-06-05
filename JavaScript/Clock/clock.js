"use strict";

const $ = selector => document.querySelector(selector);
const padSingleDigit = num => num.toString().padStart(2, "0");

const displayCurrentTime = () => {
    const today = new Date();
    let hours = $("#hours");
    let minutes = $("#minutes");
    let seconds = $("#seconds");

    hours.textContent = padSingleDigit(today.getHours());
    minutes.textContent = padSingleDigit(today.getMinutes());
    seconds.textContent = padSingleDigit(today.getSeconds());


    if(hours.textContent >= 12)
    {
        $("#ampm").textContent = "PM";
    }
    else
    {
        $("#ampm").textContent = "AM";
    }
};

document.addEventListener("DOMContentLoaded", () => {
    setInterval(displayCurrentTime, 1000);
});