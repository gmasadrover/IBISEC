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
	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDjjjVKq3Fyvi6XiH-Mu8veFBD49iEQJLY"></script>
</head>	
<body class="fullbody" style="height:100%">	 				
	<div class="col-md-12" style="height:100%">
			<input type="hidden" id="filterWithOutDate" value="${filterWithOutDate}">
			<input type="hidden" id="dataInici" value="${dataInici}">
			<input type="hidden" id="dataFi" value="${dataFi}">
			<input type="hidden" id="filterWithOutDateExec" value="${filterWithOutDateExec}">
			<input type="hidden" id="dataIniciExec" value="${dataIniciExec}">
			<input type="hidden" id="dataFiExec" value="${dataFiExec}">
			<input type="hidden" id="estatList" value="${estatFilter}">
			<input type="hidden" id="tipusList" value="${tipusFilter}">
      		<div class="informacioCentres hidden">
      			<c:forEach items="${centresList}" var="centre" >
      				<c:if test="${centre.lat > 0 && centre.lng > 0}">
      					<input data-idcentre="${centre.idCentre}" data-lat="${centre.lat}" data-lng="${centre.lng}" value="${centre.nom}">
      				</c:if>
      			</c:forEach>
      		</div>         
		<div id="map" style="width:100%; height:100%"></div>
	</div>
     <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/llistats/llistatComplet.js?<%=application.getInitParameter("datakey")%>"></script>   
    <!-- /#wrapper -->
</body>
</html>