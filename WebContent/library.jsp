<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mia libreria</title>

<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="js/library.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

<link href="http://fontawesome.io/assets/font-awesome/css/font-awesome.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="css/profile.css">
<script src="https://unpkg.com/sweetalert2@7.12.12/dist/sweetalert2.all.js"></script>

<style>
.pointer:hover {
	cursor: pointer;
}
</style>

</head>
<body class="bg-secondary">

<jsp:include page="header.jsp"></jsp:include>

<div class="container" style="margin-top: 100px; margin-bottom: 150px;">

    <div class="row user-menu-container rounded">
        <div class="col-md-12 user-details">
            <div class="row coralbg white">
                <div class="col-md-6 no-pad border border-white">
                    <div class="user-pad text-center">
                        <h2>Playlist</h2>
                        <br><br>
                        <div id="playlist-info bg-black">
                        	<ul class="list-group">
	                        	<li id="create-playlist" class="list-group-item list-group-item-info pointer">Crea Playlist</li>
	                        	<div id="create-choice" class="form bg-secondary">
	                        		<br><br>
                            		<div class="form-group" style="width:60%; margin:auto">
                                		<input id="name-playlist-create" type="text" name="nome_playlist" class="form-control" placeholder="Nome playlist..." required><br>
                                		<button id="create-playlist-action" class="btn btn-info btn-md" style="width: 20%;">Crea</button><br><br>
                            		</div>
                        		</div>
	                        	<li id="manage-playlist" class="list-group-item list-group-item-info pointer">Gestisci tue playlist</li>
	                        		<ul class="list-group" id="manage-choice">
			                		</ul>
	                        	<li id="favourite-playlist" class="list-group-item list-group-item-info pointer">Playlist preferite</li>
		                        	<ul class="list-group" id="favourite-choice">
				                	</ul>
                        	</ul>
	                    </div>
                        <br><br>
                    </div>  
                </div>   
	           	<div class="col-md-6 no-pad border border-secondary">
		           	<div class="user-pad text-center">
		                <h2>Brani</h2>
		                <br><br>
		                <div id="songs-info">
		                	<ul class="list-group">
	                        	<li class="list-group-item list-group-item-danger" id="name-playlist-show-songs">Nessuna playlist selezionata</li>
	                        	<ul class="list-group" id="songs-list">
	                        	</ul>
                        	</ul>
			            </div>
		            </div>  
	    		</div>
	    	</div>
	    	<div class="row coralbg white">
                <div class="col-md-12 no-pad border border-white">
                    <div class="user-pad text-center">
                        <h2>Brani preferiti</h2>
                        <br><br>
                        <div id="favourite-songs bg-black">
                        	<button id="show-favourite-songs" class="btn btn-success btn-md" style="width:100%">Mostra</button><br><br>
                        	<ul class="list-group" id="favourite-songs-list">
	                    	
							</ul>
	                    </div>
                        <br><br>
                    </div>  
                </div>
	    	</div>
     	</div>
     </div>
    
    
</div>

<jsp:include page="footer.jsp"></jsp:include>
</body>

<!--  Rename Modal -->
 <div class="modal fade" id="rename-modal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Rinomina</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
        	<h6>Nome attuale: <b id="rename-actual-name"></b></h6>
	        <input id="new-name-playlist" type="text" class="form-control" placeholder="Nuovo nome.." required>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
        	<button id="rename-playlist-action" class="btn btn-success btn-md" data-dismiss="modal">Rinomina</button><br><br>
        </div>
        
      </div>
    </div>
  </div>

</html>