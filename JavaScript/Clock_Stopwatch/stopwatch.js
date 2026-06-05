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

let timer = null;
let elapsedMinutes = 0;
let elapsedSeconds = 0;
let elapsedMilliseconds = 0;

let stopMinutes = $("#stopMinutes");
let stopSeconds = $("#stopSeconds");
let stopMilliseconds = $("#stopMilliseconds");

const incrementTimer = () => {
    elapsedMilliseconds += 10;

    if(elapsedMilliseconds == 1000)
    {
        elapsedSeconds += 1;
        elapsedMilliseconds = 0;
    }
    if(elapsedSeconds == 60)
    {
        elapsedMinutes += 1;
        elapsedSeconds = 0;
    }

    stopMilliseconds.textContent = padSingleDigit(elapsedMilliseconds);
    stopMinutes.textContent = padSingleDigit(elapsedMinutes);
    stopSeconds.textContent = padSingleDigit(elapsedSeconds);
}

const startStopwatch = evt => {
    timer = setInterval(incrementTimer, 1000);
    $("#start").disabled = true;

    evt.preventDefault();
};

const stopStopwatch = evt => {
    clearInterval(timer);
    $("#start").disabled = false;

    evt.preventDefault();
};

const resetStopwatch = evt => {
    clearInterval(timer);
    $("#start").disabled = false;

    elapsedMilliseconds = 0;
    elapsedMinutes = 0;
    elapsedSeconds = 0;

    stopMinutes.textContent = padSingleDigit(0);
    stopSeconds.textContent = padSingleDigit(0);
    stopMilliseconds.textContent = padSingleDigit(0);

    evt.preventDefault();
}

document.addEventListener("DOMContentLoaded", () => {
    setInterval(displayCurrentTime, 1000);
    $("#start").addEventListener("click", startStopwatch);
    $("#stop").addEventListener("click", stopStopwatch);
    $("#reset").addEventListener("click", resetStopwatch);
});