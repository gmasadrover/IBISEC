<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}"  style="height:100%">  
<head>
	<jsp:include page="../_header.jsp"></jsp:include>	
	
</head>	
<body class="fullbody" style="height:100%">	 				
	
	
	<div style="width: 100%"><iframe width="100%" height="600" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://www.google.com/maps/d/u/0/viewer?mid=1XpLCSAVHzsmN_dMqZaspN5b9BjGTL0RJ&ll=39.62891945151308%2C2.9168108523184477&z=10"><a href="https://www.gps.ie/car-satnav-gps/">Sat Navs</a></iframe></div>
     <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/llistats/llistatComplet.js?<%=application.getInitParameter("datakey")%>"></script>   
    <!-- /#wrapper -->
</body>
</html>