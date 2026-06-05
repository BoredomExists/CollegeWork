def main():
    height = float(input("Enter the height that the ball would be dropped from: "))
    bouncinessIndex = float(input("Enter the bounciness of the ball: "))
    bounces = float(input("Enter how many bounces the ball will bounce: "))
    
    
    distance = 0
    count = 0
    while count <= bounces:
        count += 1
        distance += height
        height *= bouncinessIndex
    
    print("The total distance is", distance, "ft" )
        
    
if __name__ == "__main__":
    main()