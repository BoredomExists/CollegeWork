from tkinter import *

window = Tk()
window.title("Label Demo")

Label(window, text="(0, 0)").grid(row=0, column=0)
Label(window, text="(0, 1)").grid(row=0, column=1)
Label(window, text="(1, 0)").grid(row=1, column=0)
Label(window, text="(1, 1)").grid(row=1, column=1)

window.mainloop()