"""
File: crapsgui.py
Project 9.7
Pops up a window that allows the user to play the game of craps.
"""

from breezypythongui import EasyFrame
from tkinter import PhotoImage
from craps import Player

class CrapsGUI(EasyFrame):
   
    def __init__(self):
        """Creates the player, and sets up the Images and labels for the two dice to be displayed, the text area for the game state, and the two command buttons."""
        EasyFrame.__init__(self, title = "Craps Game")
        self.setSize(220, 320)
        """Instantiate the model and initial values of the dice"""
        self.player = Player() # self.player
        self.v1 = Player().die1 #self.v1
        self.v2 = Player().die2 #self.v2
        """Add labels and buttons to the view"""
        self.dieLabel1 = self.addLabel("", row = 0, column= 0, sticky= "NSEW")# self.dieLabel1
        self.dieLabel2 = self.addLabel("", row = 0, column= 1, sticky= "NSEW", columnspan= 2)# self.dieLabel2
        self.stateArea = self.addTextArea("", row = 1, column= 0, columnspan= 5, height= 1, width= 1)# self.stateArea
        self.rollButton = self.addButton(row = 2, column = 0, text= "Roll", command= self.nextRoll)# self.rollButton
        self.addButton = self.addButton(row = 2, column = 1, text = "New game", command= self.newGame)# self.addButton
        self.refreshImages()

    def nextRoll(self):
        """Rolls the dice and updates the view with
        the results."""
        # Add your code here
        self.v1.roll()
        self.v2.roll()
        winLose = self.player.play()
        total = self.v1.getValue() + self.v2.getValue()
        self.stateArea.appendText("Total = " + str(total) + "\n")
        if(not winLose):
            self.stateArea.appendText("You Lose!")
            self.rollButton["state"] = "disabled"
    
        self.refreshImages()
        
        
            
    def newGame(self):
        """Create a new craps game and updates the view."""
        # Add your code here
        self.v1 = Player().die1
        self.v2 = Player().die2
        self.stateArea.setText("")
        self.rollButton["state"] = "normal"
        self.refreshImages()
        
    def refreshImages(self):
        """Updates the images in the window."""
        # Add your code here
        dieImage1 = "DICE/" + str(self.v1) + ".gif"
        dieImage2 = "DICE/" + str(self.v2) + ".gif"
        self.image1 = PhotoImage(file = dieImage1)
        self.dieLabel1["image"] = self.image1
        self.image2 = PhotoImage(file = dieImage2)
        self.dieLabel2["image"] = self.image2

def main():
    CrapsGUI().mainloop()

if __name__ == "__main__":
    main()
