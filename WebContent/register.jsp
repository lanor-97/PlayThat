<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html class="html-login">
<head>
	<title>Registrati</title>
  	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="keywords" content="">
  	<meta name="description" content="">

	<!-- stylesheets css -->
	<link rel="stylesheet" href="css/login.css">
	
	<script src="https://unpkg.com/sweetalert2@7.12.12/dist/sweetalert2.all.js"></script>
</head>



<body>
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<div id="register" class="body-login">
		
        <div class="container">
            <div id="login-row" class="row justify-content-center align-items-center">
                <div id="login-column" class="col-md-6">
                    <div id="login-box" class="col-md-12 col-md-offset-6">
                        <form id="login-form" class="form" action="register" method="POST">
                            <h3 class="text-center text-info">Registrati</h3>
                            
                            <div class="form-group">
                                <label for="username" class="text-info">Nome:</label><br>
                                <input type="text" name="name" id="name" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="username" class="text-info">Cognome:</label><br>
                                <input type="text" name="surname" id="surname" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="username" class="text-info">Username:</label><br>
                                <input type="text" name="username" id="username" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="email" class="text-info">Email:</label><br>
                                <input type="email" name="email" id="email" class="form-control" required required>
                            </div>
                            
                            <div class="form-group">
                                <label for="password" class="text-info">Password:</label><br>
                                <input type="password" name="password" id="password" class="form-control" required>
                            </div>
                            
                            <div class="form-group text-center">
                           		<input type="submit" name="submit" class="btn btn-info btn-md" value="Registra">
                            </div>
                            
                            <div class="form-group text-center">
                            	<label class="text-info"><span>Già registrato? <a href="login.jsp" class="text-info">Accedi</a></span></label><br>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
<jsp:include page="footer.jsp"></jsp:include>
</body>

<c:if test="${wrong}">
	<script>
	swal({
		type: 'error',
		title: 'Oops...',
		text: 'Username già esistente',
		confirmButtonText: 'Riprova'
	})
	</script>
</c:if>

</html>