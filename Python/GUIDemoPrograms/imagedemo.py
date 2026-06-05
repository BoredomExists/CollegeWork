from tkinter import *
from tkinter.font import Font

file = r"C:\Users\cbier\Desktop\College\Spring 2023\CPT_187\Chapter8\PersonalMethods\images\smokey.gif"

window = Tk()
window.title("Image Demo")
window.resizable(False, False)

img = PhotoImage(file= file)
Label(image=img).pack()
Label(text= "").pack()
Label(text="Smokey the cat", font=("Verdana", 20, "italic"), fg="blue").pack()


window.mainloop()