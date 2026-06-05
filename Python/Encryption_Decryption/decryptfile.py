f = open("EncryptFile.txt", 'r+')

code = f.readline()
distance = int(input("Enter the distance value: "))
plainText = ""
for ch in code:
    ordValue = ord(ch)
    cipherValue = ordValue - distance
    if cipherValue < ord('a'):
        cipherValue = ord('z') - \
                        (distance - (ord('a') - ordValue - 1))
    plainText += chr(cipherValue)
    
code = code.replace(code, plainText)
with open("EncryptFile.txt", 'w') as file:
    file.write(code)
    f.close()
