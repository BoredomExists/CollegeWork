"use strict";
const game = {
    player1: new Pig(),  
    player2: new Pig(), 
    currentPlayer: null, 
    get isValid() {
        if ( this.player1.name === "" || this.player2.name === "" ) {
            return false;
        } else { 
            return true; 
        }
    },
	start(name1, name2) {
        this.player1.name = name1;
        this.player2.name = name2;
        this.currentPlayer = this.player1;
        return this;
    },
    reset() {
        // call the reset() method on each of the player Pig objects
        this.player1.reset();
        this.player2.reset();
    },
    changePlayer() {
        // determine whether player1 or player2 is the current player, then
        // update the currentPlayer property to hold the other player.
        if (this.currentPlayer == this.player1) 
        { this.currentPlayer = this.player2 }
        else
        { this.currentPlayer = this.player1 }
    },
    hold( score1, score2 ) {
        // call the hold() method of the current player
        this.currentPlayer.hold();

        // determine whether player1 or player2 is the current player, then
        // update that player's score with the current total

        const total = parseInt($("#total").val());
        if (this.currentPlayer === this.player1)
        {
            score1 = $("#score1");
            score1.val( parseInt( $("#score1").val() ) + total);
        }
        else
        {
            score2 = $("#score2");
            score2.val( parseInt( $("#total").val() ) + total);
        }
        
    },
    checkWinner() {
        // check the players' totals to see if either is at or above 100
        // points. If so, return that player's name. Otherwise, return 
        // the string "none".
        if($("#score1").val() >= 100)
        {
            return this.player1.name;
        }
        else if($("#score2").val() >= 100)
        {
            return this.player2.name;
        }
        else
        {
            return "none";
        }
    }
};