from tkinter import *
from tkinter.font import Font

window = Tk()
window.title("Button Demo")
window.geometry("600x600")
labelFont = Font(family= "Arial", size= 30, weight="bold")
buttonFont = Font(family= "Arial", size= 20)

def clear():
    label["text"] = ""
    clearButton["state"] = "disabled"
    
def restore():
    label["text"] = "Hello World"
    restoreButton["state"] = "disabled"
    
label = Label(window, text="Hello World", font= labelFont)
label.pack(pady=20)

restoreButton = Button(window, text="Restore Label", command=restore).pack(pady=20)
clearButton = Button(window, text="Clear Text", command=clear).pack(pady=20)

window.mainloop()