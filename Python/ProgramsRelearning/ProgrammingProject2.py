import random

# User guesses the computer's number
def userguessing():
    smaller = int(input("Enter the smaller number in the range: "))
    larger = int(input("Enter the larger number in the range: "))
    
    guess = 0
    randomnumber = random.randint(smaller, larger)
    while True:
        guess += 1
        guessnumber = int(input("Enter a number " + str(smaller) + "-" + str(larger) + ": "))
        if guessnumber < randomnumber:
            print("Random Number is higher")
        elif guessnumber > randomnumber:
            print("Random Number is lower")
        elif guessnumber == randomnumber:
            print("You guessed the random number")
            print("Number of guesses:", guess)
            break
        
        
# Computer guesses the User's number
def computerguessing():
    smaller = int(input("Enter the smaller number in the range: "))
    larger = int(input("Enter the larger number in the range: "))
    guessLimit = int(input("Enter how many guesses that computer is allowed: "))
    
    comguess = random.randint(smaller, larger)
    print("Your number is", comguess)
    
    guessCounter = 0
    while True:
        guessCounter += 1        
        
        if guessCounter == guessLimit:
            print("I could not guess your number in", guessCounter, "tries.")
            break
        
        hint = input("Enter =, <, or >: ")
        
        if hint == "=":
            print("Hooray, I've got it in", guessCounter, "tries!")
            break
        elif hint == ">":
            smaller = comguess
            comguess = random.randint(comguess + 1, larger - 1)
            print("Your number is", comguess)
        elif hint == "<":
            larger = comguess
            comguess = random.randint(smaller + 1, comguess - 1)
            print("Your number is", comguess)
        
        
        
            
        
if __name__ == "__main__":
    #userguessing() 
   computerguessing()