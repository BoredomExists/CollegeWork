import random

rollCount = 0

playerMoney = (int(input("Enter the amount of money you want to put in the pot: ")))
totalMoney = playerMoney

while playerMoney > 0:
    die1 = random.randint(1, 6)
    die2 = random.randint(1, 6)
    result = die1 + die2

    if result == 7:
        playerMoney += 4
        totalMoney += 4
    else:
        playerMoney -= 1

    rollCount += 1
    #print(die1, die2, rollCount) # To look at each roll

print("Total Money In Pot:", totalMoney)
print("Total Roll Count:", rollCount)
