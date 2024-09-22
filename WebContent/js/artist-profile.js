$(document).ready(function()  {
	
	
	var rand = Math.random() * 10;
	rand = parseInt(rand);	
	
	var api_query = $("#name2").text() + " live";
	var api_engine_id = "007800387884010163298:yjfp343aqwe";
	var api_key = "AIzaSyDsBglMUaAojYOoFSg9L4Km26Frx-PSlgg";
	var api_url = "https://www.googleapis.com/customsearch/v1?key=";
	api_url += api_key;
	api_url += "&cx=" + api_engine_id;
	api_url += "&q=" + api_query;;
	
	$.ajax({
		url:  api_url,
		type: "GET"
	}).done(function(data) {
		var url_img = data.items[ rand ].pagemap.imageobject[0].url;
		$("#artist-image").attr("src", url_img);
	})
	
	
})

function user_search(user) {
	var path = "user?" + user;
	window.location.replace(path);
}

function toggle_follow(artist_name, text, session_username)  {
	
	var follow = "true";
	
	if(text == "Non seguire")  {
		follow = "false";
	}
	
	$.ajax({
	    url: "toggleFollowArtist",
	    type: "POST",
	    data: { toFollow: follow, name: artist_name },
	    error: function (jqXHR, exception) {
	    	var msg = '';
	        if (jqXHR.status === 0) {
	            msg = 'Not connect.\n Verify Network.';
	        } else if (jqXHR.status == 404) {
	            msg = 'Requested page not found. [404]';
	        } else if (jqXHR.status == 500) {
	            msg = 'Internal Server Error [500].';
	        } else if (exception === 'parsererror') {
	            msg = 'Requested JSON parse failed.';
	        } else if (exception === 'timeout') {
	            msg = 'Time out error.';
	        } else if (exception === 'abort') {
	            msg = 'Ajax request aborted.';
	        } else {
	            msg = 'Uncaught Error.\n' + jqXHR.responseText;
	        }
	        alert(msg);
	    }
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
			
			$("#flw-modal-body").append(htmltxt);
		} else  {
			
			$("#follow").text("Inizia a seguire");
			num -= 1;
			
			$("#" + session_username).remove();
		}
		
		$("#num-followers").text(num);
		
	})
}