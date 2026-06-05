fileName = input("Enter the file name: ")
openFile = open(fileName, 'r')
uniqueWords = {}

for line in openFile:
    words = line.split()
    for word in words:
        number = uniqueWords.get(word, None)
        if number == None:
            uniqueWords[word] = 1
        else:
            uniqueWords[word] = number + 1

words = list(uniqueWords.items())
words.sort()
print("The unique words and their frequences are", words)