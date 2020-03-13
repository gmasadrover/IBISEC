<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<div class="document">
	<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
		${arxiu.getDataString()} - ${arxiu.nom}
	</a>
	<c:if test="${isCap && arxiu.ruta != null}">
		<span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
	</c:if>
	<c:if test="${arxiu.signat}">
		<span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
	</c:if>
	<br>
	<div class="infoSign hidden">							
	</div>
</div>