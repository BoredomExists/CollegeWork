from tkinter import *

window = Tk()

def getInputText():
    text = inputText.get("1.0", "end")
    text = text.upper()
    outputText.insert('1.0', text)
    
inputText = Text(window, height= 5)
inputText.grid(row= 0, column= 0)
outputText = Text(window, height= 5)
outputText.grid(row= 0, column= 1)
convertButton = Button(window, text= "Convert", command= getInputText)
convertButton.grid(row= 1, column= 0)

window.mainloop()