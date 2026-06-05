"use strict";

const getMonthText = currentMonth => {
	if (currentMonth === 0) { return "January"; }
	else if (currentMonth === 1) { return "February"; }
	else if (currentMonth === 2) { return "March"; }
	else if (currentMonth === 3) { return "April"; }
	else if (currentMonth === 4) { return "May"; }
	else if (currentMonth === 5) { return "June"; }
	else if (currentMonth === 6) { return "July"; }
	else if (currentMonth === 7) { return "August"; }
	else if (currentMonth === 8) { return "September"; }
	else if (currentMonth === 9) { return "October"; }
	else if (currentMonth === 10) { return "November"; }
	else if (currentMonth === 11) { return "December"; }
};

const getLastDayofMonth = currentMonth => {
	currentMonth = new Date();
    currentMonth.setMonth(currentMonth.getMonth() + 1);
    currentMonth.setDate(0);
    return currentMonth.getDate(0);
};

$(document).ready(function(){
    const currentMonth = new Date();
    let lastDay = getLastDayofMonth(currentMonth);
    $("#month_year").text(getMonthText(currentMonth.getMonth()) + " " + currentMonth.getFullYear());

    let rows = $("#calendar").html();
    let start;
    
    
    for(let i = 0; i < lastDay; i++) {
        
        currentMonth.setDate(i + 1);
        let currentDate = currentMonth.getDate();
        let currentDay = currentMonth.getDay(); 

        if(currentDate === 1 || currentDay === 0)
        {
            rows = rows.concat("<tr>");
        }

        if(currentDate === 1)
        {
            start = 0;
            while(start < currentDay)
            {
                rows = rows.concat("<td></td>");
                start += 1;
            }
        }

        rows = rows.concat("<td>" + currentDate + "</td>");

        if(currentDate === lastDay)
        {
            start = currentDay;
            while(start < 6)
            {
                rows = rows.concat("<td></td>");
                start += 1;
            }
        }

        if(currentDate === lastDay || currentDay === 6)
        {
            rows = rows.concat("</tr>");
        }
    }
    $("#calendar").html(rows);
});