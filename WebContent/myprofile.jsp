<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profilo</title>

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
                <div class="col-md-9 no-pad">
                    <div class="user-pad">
                        <h3>Ben tornato/a, <b id="name" class="text-secondary">${utente.name }</b></h3>
                        <br><br>
                        <div id="profile-info">
	                        <h4 class="white">Nome: <b id="name2" class="text-secondary">${utente.name }</b></h4>
	                        <h4 class="white">Cognome: <b id="surname" class="text-secondary">${utente.surname }</b></h4>
	                        <h4 class="white">Email: <b id="email" class="text-secondary">${utente.email }</b></h4>
	                    </div>
                        <br><br>
                        <button type="button" class="btn btn-labeled btn-success" data-toggle="modal" data-target="#modify-profile">Modifica info</button>
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
                    <h4>${utente.numFollowers }</h4>
                </div>
                <div style="cursor: pointer;" class="col-md-4 user-pad text-center border border-white" id="show-followingusers" data-toggle="modal" data-target="#showflwu">
                    <h3 class="white">Utenti seguiti</h3>
                    <h4>${utente.numFollowingUsers }</h4>
                </div>
                <div style="cursor: pointer;" class="col-md-4 user-pad text-center border border-white" id="show-followingartists" data-toggle="modal" data-target="#showflwa">
                    <h3 class="white">Artisti seguiti</h3>
                    <h4>${utente.numFollowingArtists }</h4>
                </div>
            </div>
        </div>
    </div>
    
    
</div>
</div>


<jsp:include page="footer.jsp"></jsp:include>
</body>

<!-- Modifica profilo -->
<div class="modal" id="modify-profile">
    <div class="modal-dialog">
      	<div class="modal-content">
      
       	<!-- Header -->
       	<div class="modal-header">
       		<h4 class="modal-title">Modifica profilo</h4>
       		<button type="button" class="close" data-dismiss="modal">&times;</button>
       	</div>
        
       	<!-- Form -->
       	<div class="modal-body">
         	<div class="form">
            	<div class="form-group">
	                <label for="name" class="text-success">Nome:</label><br>
	                <input type="text" name="name" id="mod_name" class="form-control" required>
               	</div>
               	<div class="form-group">
              		<label for="surname" class="text-success">Cognome:</label><br>
                  	<input type="text" name="surname" id="mod_surname" class="form-control" required>
               	</div>
               	<div class="form-group">
           			<label for="email" class="text-success">Email:</label><br>
               		<input type="email" name="email" id="mod_email" class="form-control" required>
            	</div>
        	</div>
       	</div>
        
       	<!-- Salva  -->
       	<div class="modal-footer">
	   		<input type="submit" id="save-info" class="btn btn-success btn-md" data-dismiss="modal" value="Salva">
	   		<input type="button" class="btn btn-danger btn-md" data-dismiss="modal" value="Annulla">
       	</div>
      	</div>
    </div>
</div>


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
       	<div class="modal-body">
         <c:forEach items="${followers}" var="follower">
         	<button class="btn btn-info btn-md" onclick="user_search($(this).text())">${follower.username }</button><br><br>
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
  
</html>