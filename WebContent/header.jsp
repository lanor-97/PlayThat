<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="it">
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://unpkg.com/sweetalert2@7.12.12/dist/sweetalert2.all.js"></script>
	<script src="https://www.paypalobjects.com/api/checkout.js"></script>
	<script src="js/paypalButton.js"></script>
</head>

<header>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">

		<a class="navbar-brand" href="home">Home</a>
		<button class="navbar-toggler navbar-toggler-right collapsed" type="button" data-toggle="collapse" data-target="#navb" aria-expanded="false">
    		<span class="navbar-toggler-icon"></span>
  		</button>
		<div class="navbar-collapse" id="navb">
			<c:if test="${loggato }">
				<ul class="navbar-nav mr-auto">
	 				<li class="nav-item"><a class="navbar-brand" href="myprofile">Profilo</a></li>
	 				<li class="nav-item"><a class="navbar-brand" href="mylibrary">Libreria</a></li>
	 				<c:if test="${!utente.premium}"><li class="nav-item"><a class="navbar-brand" href="#" data-toggle="modal" data-target="#become-premium">Premium</a></li></c:if>
	 				<li class="nav-item"><a class="navbar-brand" href="logout.jsp">Logout</a></li>
				</ul>
			</c:if>
			<form action="song" method="GET" class="form-inline my-2 my-lg-0">
	  			<input class="form-control mr-sm-2" type="text" name="value" placeholder="Cerca brano o playlist" id="song_value">
	  			<div class="form-group" style="margin-right: 10px;">
  					<select class="form-control" name="parameter" id="parameter">
  						<option>Tutti i brani</option>
  						<option>Tutte le playlist</option>
    					<option>Brano - titolo</option>
    					<option>Brano - genere</option>
    					<option>Brano - album</option>
    					<option>Brano - autore</option>
  					</select>
				</div> 
	 			<button id="songsearch" class="btn btn-success my-2 my-sm-0" type="submit">Cerca</button>
			</form>
		</div>
 	</nav>	 
</header>

<div class="modal" id="become-premium">
    <div class="modal-dialog">
      	<div class="modal-content">
      
	       	<!-- Header -->
	       	<div class="modal-header">
	       		<h4 class="modal-title">Diventa Premium</h4>
	       		<button type="button" class="close" data-dismiss="modal" >&times;</button>
	       	</div>
	        
	       	<!-- Body -->
	       	<div class="modal-body">
	        	<h2 class="text-secondary">Diventa premium per disattivare le pubblicità sul sito! Dura per sempre!</h2><br>
	        	<h3>Costo: 5 EUR</h3>
	        	<div id="paypal-button"></div>
	       	</div>
      	</div>
    </div>
</div>
</html>