"use strict";

class Coins {
    constructor() {
        this.quarters = 0;
        this.dimes = 0;
        this.nickels = 0;
        this.pennies = 0;
    }
    makeChange(numOfCents) {
        if(isNaN(numOfCents) || numOfCents == "" || numOfCents < 0 || numOfCents > 99) { throw new TypeError("Number of cents must be a number between 0 and 99.") }
        else
        {
        this.quarters = numOfCents / 25;
        this.quarters = Math.floor(this.quarters);
        numOfCents = numOfCents % 25;

        this.dimes = numOfCents / 10;
        this.dimes = Math.floor(this.dimes);
        numOfCents = numOfCents % 10;

        this.nickels = numOfCents / 5;
        this.nickels = Math.floor(this.nickels);

        this.pennies = numOfCents % 5;
        }
    }
}