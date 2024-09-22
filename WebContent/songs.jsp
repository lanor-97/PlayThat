<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cerca brani</title>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/songs.js"></script>
</head>

<body class="bg-secondary">
<jsp:include page="header.jsp"></jsp:include>

<div class="container card-columns">
	<c:forEach items="${brani}" var="b">
	<div class="card bg-light song-popover" style="margin-top: 50px; margin-bottom: 50px;">
  		<div class="card-body text-center song-card" id="${b.id }">
  			<h2 class="text-secondary">${b.name }</h2>
  			<h5>Autore: </h5><a href="artist?${b.author.name }"><h7 class="text-secondary">${b.author.name }</h7></a>
  			<h5>Genere: </h5><h7 class="text-secondary genre">${b.genre }</h7>
  			<h5>Album: </h5><h7 class="text-secondary album">${b.album }</h7>
  			<h5>Anno: </h5><h7 class="text-secondary">${b.releaseDate }</h7><br><br>
  			<audio controls>
  				<source src="songs/${b.author.name } - ${b.name }.mp3" type="audio/mpeg">
				Your browser does not support the audio element.
			</audio>
  			<c:if test="${loggato}">
  				<button class="btn btn-primary my-2 my-sm-0" data-toggle="modal" data-target="#song-add-to-modal">Aggiungi a...</button>
  			</c:if>
  		</div>
	</div>
	</c:forEach>
	<c:forEach items="${playlists}" var="p">
	<div class="card bg-light song-popover" style="margin-top: 50px; margin-bottom: 50px;">
  		<div class="card-body text-center song-card" id="${p.id }">
  			<h2 class="text-secondary">${p.name}</h2>
  			<h5>Creatore: </h5><a href="user?${p.creator.username }"><h7 class="text-secondary">${p.creator.name } ${p.creator.surname }</h7></a><br><br>
  			<c:if test="${loggato}">
  				<button onclick="add_favourite_playlist($(this).parent().attr('id'))" class="btn btn-primary my-2 my-sm-0">Aggiungi alle preferite</button>
  			</c:if>
  		</div>
	</div>
	</c:forEach>
</div>

<c:if test="${!utente.premium }">
	<div class="container" style="margin-bottom: 40px;">
	    <img src="https://www.onedanceuk.org/wp-content/uploads/2015/11/June13-BOM-Digital-Ad-Horizontal.jpg" alt="" class="img-responsive" style="width: 100%; height: 200px;">      
	</div>
</c:if>
</body>

<!--  Add to -->
 <div class="modal fade" id="song-add-to-modal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Aggiungi a...</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
        	<label for="where-add-to">Seleziona dove: </label>
        	<select id="where-add-to" class="form-control">
		        <option>Brani preferiti</option>
		        <option>Mia playlist</option>
      		</select><br><br>
      		<div id="if-playlist-selected">
	      		<label for="who-add-to">Seleziona quale: </label>
	      		<select id="who-add-to" class="form-control">
	      			<c:forEach items="${utente_playlists}" var="p">
			        	<option>${p.name }</option>
			        </c:forEach>
	      		</select>
	      	</div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
        	<button id="add-song-action" class="btn btn-success btn-md" data-dismiss="modal">Aggiungi</button><br><br>
        </div>
        
      </div>
    </div>
  </div>

</html>