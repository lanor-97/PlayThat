$(document).ready(function(){
	
	var name = $("#name2").text();
	var surname = $("#surname").text();
	var email = $("#email").text();
			
	$("#mod_name").val(name);
	$("#mod_surname").val(surname);
	$("#mod_email").val(email);
	
	$("#save-info").click(function()  {
		
		var name_ = $("#mod_name").val();
		var surname_ = $("#mod_surname").val();
		var email_ = $("#mod_email").val();
		
		$.ajax({
		    url: "saveprofile",
		    type: "POST",
		    data: {name: name_, surname: surname_, email: email_ }
		
		}).done(function( e ) {
			$("#name").text(name_);
			$("#name2").text(name_);
			$("#surname").text(surname_);
			$("#email").text(email_);
		})
	});
});

function user_search(user) {
	var path = "user?" + user;
	window.location.replace(path);
}

function artist_search(artist) {
	var path = "artist?" + artist;
	window.location.replace(path);
}

function toggle_follow(username, text, session_username)  {
	
	var follow = "true";
	
	if(text == "Non seguire")  {
		follow = "false";
	}
	
	$.ajax({
	    url: "toggleFollow",
	    type: "POST",
	    data: { toFollow: follow, username: username }
	}).done(function() {
		
		var txt = $("#num-followers").text();
		var num = parseInt(txt);
		
		if(follow == "true")  {
			
			$("#follow").text("Non seguire");
			num += 1;
			
			var htmltxt = "<div id=\"";
			htmltxt += session_username;
			htmltxt += "\"><button class=\"btn btn-info btn-md\" onclick=\"user_search($(this).text())\">";
			htmltxt += session_username;
			htmltxt += "</button><br><br></div>";
			
			$("#flw-modal-body").html(htmltxt);
		} else  {
			
			$("#follow").text("Inizia a seguire");
			num -= 1;
			
			$("#" + session_username).remove();
		}
		
		$("#num-followers").text(num);
		
	})
}

