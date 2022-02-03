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
    <meta charset="UTF-8">
    <title>Delete Product</title>
 </head>
 
 <body>
 
    <jsp:include page="../_header.jsp"></jsp:include>
    <jsp:include page="../_menu.jsp"></jsp:include>
    
    <h3>Delete Product</h3>
    
    <p style="color: red;">${errorString}</p>
    <a href="productList">Product List</a>
    
    <jsp:include page="../_footer.jsp"></jsp:include>
    
 </body>
</html>