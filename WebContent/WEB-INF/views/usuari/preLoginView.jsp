<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
	<jsp:include page="../_header.jsp"></jsp:include>	
	<link href="css/prelogin.css" rel="stylesheet">
</head>	
<body>	
	<div class="container">
		<div class="row">
             	<div class="form-signin">
            			<p style="color: red;">${errorString}</p>
            		</div>
            	 </div>
      <form class="form-signin" method="POST" action="doLogin">
        <h2 class="form-signin-heading">Iniciar sessió</h2>
        <label for="userName" class="sr-only">Usuari</label>
        <input name="userName" class="form-control" placeholder="Usuari" value="${user.usuari}" required autofocus>
        <label for="password" class="sr-only">Password</label>       
        <input type="password" name="password" class="form-control" placeholder="Password">
        <c:if test="${newUser}">
        	<label for="repeatPassword" class="sr-only">Repeteix password</label>
        	<input type="password" name="repeatPassword" class="form-control" placeholder="Repeteix password" required>
        </c:if>
        <!-- <div class="checkbox">
          <label>
            <input type="checkbox" name="rememberMe" value="remember-me"> Recordar
          </label>
        </div> -->
        
        <button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
      </form>

    </div> <!-- /container -->
    <jsp:include page="../_footer.jsp"></jsp:include>
    <!-- /#wrapper -->
</body>
</html>