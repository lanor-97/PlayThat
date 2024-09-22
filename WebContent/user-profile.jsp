<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profilo utente</title>

<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="js/user-profile.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

<!------ Include the above in your HEAD tag ---------->

<link href="http://fontawesome.io/assets/font-awesome/css/font-awesome.css" rel="stylesheet" media="screen">
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
	                        <h4 class="white">Nome: <b id="name2" class="text-secondary">${utente_cercato.name }</b></h4>
	                        <h4 class="white">Cognome: <b id="surname" class="text-secondary">${utente_cercato.surname }</b></h4>
	                        <h4 class="white">Email: <b id="email" class="text-secondary">${utente_cercato.email }</b></h4>
	                    </div>
                        <br><br>
                        <c:if test="${loggato }">
                        	<button id="follow" type="button" class="btn btn-labeled btn-success"></button>
                        </c:if>
                    </div>  
                </div>
                <div class="col-md-3 no-pad">
                    <div class="user-image">
                        <img src="images/empty-avatar.png" alt="avatar" class="img-responsive thumbnail">
                    </div>
                </div>
            </div>
            <div class="row overview coralbg">
                <div style="cursor: pointer;" class="col-md-4 user-pad text-center border border-white" id="show-followers" data-toggle="modal" data-target="#showfls">
                    <h3 class="white">Followers</h3>
                    <h4 id="num-followers">${utente_cercato.numFollowers }</h4>
                </div>
                <div style="cursor: pointer;" class="col-md-4 user-pad text-center border border-white" id="show-followingusers" data-toggle="modal" data-target="#showflwu">
                    <h3 class="white">Utenti seguiti</h3>
                    <h4>${utente_cercato.numFollowingUsers }</h4>
                </div>
                <div style="cursor: pointer;" class="col-md-4 user-pad text-center border border-white" id="show-followingartists" data-toggle="modal" data-target="#showflwa">
                    <h3 class="white">Artisti seguiti</h3>
                    <h4>${utente_cercato.numFollowingArtists }</h4>
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

<!-- Mostra utenti seguiti -->
<div class="modal" id="showflwu">
    <div class="modal-dialog">
      	<div class="modal-content">
      
       	<!-- Header -->
       	<div class="modal-header">
       		<h4 class="modal-title">Utenti seguiti</h4>
       		<button type="button" class="close" data-dismiss="modal">&times;</button>
       	</div>
        
       	<!-- Body -->
       	<div class="modal-body">
         <c:forEach items="${followingusers}" var="following_u">
         	<button class="btn btn-info btn-md" onclick="user_search($(this).text())">${following_u.username }</button><br><br>
         </c:forEach>
       	</div>
      	</div>
    </div>
</div>

<!-- Mostra artisti seguiti -->
<div class="modal" id="showflwa">
    <div class="modal-dialog">
      	<div class="modal-content">
      
       	<!-- Header -->
       	<div class="modal-header">
       		<h4 class="modal-title text-center">Artisti seguiti</h4>
       		<button type="button" class="close" data-dismiss="modal">&times;</button>
       	</div>
        
       	<!-- Body -->
       	<div class="modal-body">
         <c:forEach items="${followingartists}" var="following_a">
         	<button class="btn btn-info btn-md" onclick="artist_search($(this).text())">${following_a.name }</button><br><br>
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
		var username = "${utente_cercato.username}";
		var session_username = "${utente.username}";
		
		toggle_follow(username, text, session_username);
	});
});
</script>
</c:if>

</html>