let total = 0;
do
{
    const input = parseInt(prompt("Enter Test Score"))
    if(isNaN(input) || input < 0 || input > 100)
    {
        alert("Invalid Input. Enter a Number between 0 - 100")
    }
    else if (input >= 88 && input <= 100)
    {
        document.write(`<p><label>Grade</label> ${input} <label> =  A</label>` )
    }
    else if (input >= 80 && input <= 87)
    {
        document.write(`<p><label>Grade</label> ${input} <label> =  B</label>` )
    }
    else if (input >= 68 && input <= 79)
    {
        document.write(`<p><label>Grade</label> ${input} <label> =  C</label>` )
    }
    else if (input >= 60 && input <= 67)
    {
        document.write(`<p><label>Grade</label> ${input} <label> =  D</label>` )
    }
    else if (input <= 59)
    {
        document.write(`<p><label>Grade</label> ${input} <label> =  F</label>` )
    }

    
    again = prompt("Enter another test score? (y/n)")
} while (again == "y")
