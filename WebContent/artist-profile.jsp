<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profilo artista</title>

<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="js/artist-profile.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

<!------ Include the above in your HEAD tag ---------->

<link rel="stylesheet" href="css/profile.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="masthead">
<div class="container">
    <div class="row user-menu-container rounded">
        <div class="col-md-12 user-details">
            <div class="row coralbg white">
                <div class="col-md-6 no-pad">
                    <div class="user-pad">
                        <br><br>
                        <div id="profile-info">
	                        <h4 class="white">Nome: <b id="name2" class="text-secondary">${artista_cercato.name }</b></h4>
	                        <h4 class="white">Nazione: <b id="surname" class="text-secondary">${artista_cercato.country }</b></h4>
	                        <h4 class="white">Data inizio: <b id="surname" class="text-secondary">${artista_cercato.startDate }</b></h4>
	                    </div>
                        <br><br>
                        <c:if test="${loggato }">
                        	<button id="follow" type="button" class="btn btn-labeled btn-success"></button>
                        </c:if>
                    </div>  
                </div>
                <div class="col-md-3 no-pad">
                    <div class="user-image">
                        <img id="artist-image" src="images/empty-avatar.png" alt="avatar" class="img-responsive thumbnail">
                    </div>
                </div>
            </div>
            <div class="row overview coralbg">
                <div style="cursor: pointer;" class="col-md-6 user-pad text-center border border-white" id="show-followers" data-toggle="modal" data-target="#showfls">
                    <h3 class="white">Followers</h3>
                    <h4 id="num-followers">${artista_cercato.numFollowers }</h4>
                </div>
	            <div style="cursor: pointer;" class="col-md-6 user-pad text-center border border-white" id="show-songs">
	            	<h3 class="white">Brani</h3>
	            </div>
            </div>
        </div>
        
    </div>
    
    
</div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
</body>

<!-- Mostra followers -->
<div class="modal" id="showfls">
    <div class="modal-dialog">
      	<div class="modal-content">
      
	       	<!-- Header -->
	       	<div class="modal-header">
	       		<h4 class="modal-title">Followers</h4>
	       		<button type="button" class="close" data-dismiss="modal" >&times;</button>
	       	</div>
	        
	       	<!-- Body -->
	       	<div class="modal-body" id="flw-modal-body">
	         <c:forEach items="${followers}" var="follower">
	         	<div id="${follower.username }"><button class="btn btn-info btn-md" onclick="user_search($(this).text())">${follower.username }</button><br><br></div>
	         </c:forEach>
	       	</div>
      	</div>
    </div>
</div>

<c:if test="${loggato }">
<script>
$(document).ready(function()  {
	
	if( ${following_already} )  {
		$("#follow").text("Non seguire");
	} else  {
		$("#follow").text("Inizia a seguire");
	}
	
	$("#follow").click(function()  {
		var text = $("#follow").text();
		var artista_name = "${artista_cercato.name}";
		var session_username = "${utente.username}";
		
		toggle_follow(artista_name, text, session_username);
	});
});
</script>
</c:if>

<script>
$("#show-songs").click(function()  {
	window.location.replace("song?value=${artista_cercato.name }&parameter=Brano+-+autore");
})
</script>

</html>