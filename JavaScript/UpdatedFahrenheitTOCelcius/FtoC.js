let again = "y"

do {
const userInput = parseInt(prompt("Enter the temperature in Fahrenheit"))
if(userInput >= -100 && userInput <= 212)
{
const FtoC = (userInput - 32) * 5 / 9
document.write(`<p>${userInput.toFixed(1)}<label> Fahrenheit</label><label> = </label> ${FtoC.toFixed(1)}<label> Celcius`)
}
else
{
    alert(`You entered ${userInput}` + "\nEntry must be between -100 and +212")
}

again = prompt("Enter another temperature. (y/n)");

} while(again == "y");