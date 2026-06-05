from breezypythongui import EasyFrame

class TidBitGUI(EasyFrame):
    def __init__(self):
        EasyFrame.__init__(self, "TidBit Computer Plan")
        self.addLabel(text= "Purchase Price", row= 0, column= 0)
        self.addLabel(text= "Annual Interest Rate", row= 1, column= 0)
        self.purchasePrice = self.addFloatField(value= 0.0, row= 0, column= 1)
        self.annualRate = self.addIntegerField(value= 0.0, row= 1, column= 1)
        
        outputHeadings = "Month  Starting Balance  Interest to Pay  Principle to Pay  Payment  Ending Balance"
        self.addLabel(text= outputHeadings, row= 4, column= 0)
        
        self.outputArea = self.addTextArea("", row= 5, column= 0, columnspan= 2, width= 50, height= 15)
        
        self.calculate = self.addButton(text= "Calculate", row= 3, column= 0, columnspan= 2, command= self.calcPlan)
        
    def calcPlan(self):
        startBalance = self.purchasePrice.getNumber()
        rate = self.annualRate.getNumber() / 100
        
        monthlyRate = rate / 12
        monthlyPayment = .05 * startBalance
        month = 1
        balance = startBalance
        while balance > 0:
            if monthlyPayment > balance:
                monthlyPayment = balance
                interest = 0
            else:
                interest = balance * monthlyRate
            principal = monthlyPayment - interest
            remaining = balance - monthlyPayment
            outputText = "%2d%10.2f%10.2f%11.2f%11.2f%11.2f" % \
                (month, balance, interest, principal, monthlyPayment, remaining)
            balance = remaining
            month += 1
            self.outputArea.insert("1.0", outputText +"\n")
        
if __name__ == "__main__":
    TidBitGUI().mainloop()