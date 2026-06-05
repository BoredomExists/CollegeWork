"use strict";

$(document).ready( () => {
	// display data from session storage
	const json = sessionStorage.profile;
	const profileInfo = JSON.parse(json);
	$("#email").text( profileInfo[0][1] );
	$("#phone").text( profileInfo[1][1] );
	$("#zip").text( profileInfo[2][1] );
	$("#dob").text( profileInfo[3][1] );
	
	$("#back").click( () => {
		history.back();
	}); // end of click()
	
}); // end of ready()