<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>

	<title>PlayThat</title>
	<meta charset="utf-8">
	
	<!-- Google Fonts -->
  	<link href="https://fonts.googleapis.com/css?family=Merriweather+Sans:400,700" rel="stylesheet">
  	<link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic' rel='stylesheet' type='text/css'>

  	<!-- Theme CSS - Includes Bootstrap -->
  	<link href="css/style.css" rel="stylesheet">
  	<script src="https://unpkg.com/sweetalert2@7.12.12/dist/sweetalert2.all.js"></script>

</head>

<body class="bg-secondary">
<jsp:include page="header.jsp"></jsp:include>

<c:if test="${!loggato}">
	<div class="masthead">
		<div class="container">
	      <div class="row h-100 align-items-center justify-content-center text-center">
	        <div class="col-lg-10 align-self-end">
	          <h1 class="text-uppercase text-white font-weight-bold">Il tuo sito preferito per l'ascolto di musica streaming</h1>
	          <hr class="divider my-4">
	        </div>
	        <div class="col-lg-8 align-self-baseline">
	          <p class="text-white-75 font-weight-light mb-5">Accedi o registrati per sbloccare tutte le funzionalità</p>
	          <a class="btn btn-success btn-xl js-scroll-trigger" href="login.jsp">Accedi</a>
	          <a class="btn btn-success btn-xl js-scroll-trigger" href="register.jsp">Registrati</a>
	        </div>
	      </div>
	    </div>
	</div>
</c:if>
<c:if test="${loggato }">
	<div style="width: 100%; height:80%; margin-bottom:10%; margin-top: 5%;">
		<h1 class="text-uppercase text-white font-weight-bold text-center" style="margin-bottom: 3%;">Attività recenti utenti seguiti</h1>
		<div class="container">
 			
 			<c:forEach var="feed" items="${feeds}" varStatus="status">
				<div class="card text-center">
			    	<p>${feed }</p>
			  	</div>
	  			<br>
	  			
	  		</c:forEach>
		</div>
	</div>
	
	<c:if test="${!utente.premium }">
		<div class="container" style="margin-bottom: 40px;">
		    <img src="https://image.shutterstock.com/z/stock-vector-winter-sale-horizontal-ad-blue-banner-747277033.jpg" alt="" class="img-responsive" style="width: 100%; height: 200px;">      
		</div>
	</c:if>
</c:if>

<jsp:include page="footer.jsp"></jsp:include>
</body>

<c:if test="${nothing}">
	<script>
	swal({
		type: 'error',
		title: 'Oops...',
		text: 'Non è stato trovato nessun brano con i parametri inseriti',
		confirmButtonText: 'Riprova'
	})
	</script>
</c:if>
<c:if test="${no_user}">
	<script>
	swal({
		type: 'error',
		title: 'Oops...',
		text: 'Non è stato trovato nessun utente con i parametri inseriti',
		confirmButtonText: 'Riprova'
	})
	</script>
</c:if>
<c:if test="${no_artist}">
	<script>
	swal({
		type: 'error',
		title: 'Oops...',
		text: 'Non è stato trovato nessun artista con i parametri inseriti',
		confirmButtonText: 'Riprova'
	})
	</script>
</c:if>
<c:if test="${!utente.premium}">
</c:if>

</html>