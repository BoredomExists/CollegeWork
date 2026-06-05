f = open("EncryptFile.txt", 'w')
f.truncate(0)

plainText = input("Enter a one-word message: ").lower()
distance = int(input("Enter a distance value: "))
code = ""
for ch in plainText:
    ordvalue = ord(ch)
    cipherValue = ordvalue + distance
    if cipherValue > 127:
        cipherValue = 127 + distance - \
                      (127 - ordvalue + 1)
    code += chr(cipherValue)
    
f.write(code)

f.close()