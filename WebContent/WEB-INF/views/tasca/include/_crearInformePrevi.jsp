<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div id="propostaActuacio" class="">
	<input type="hidden" name="idProposta" value="${propostaActuacio.idProposta}">	
	<div class="form-group">
		<div class="col-md-12">
			<p>Arxius adjunts:</p>
           	<c:forEach items="${informePrevi.getInformesPrevis()}" var="arxiu" >
           		<c:set var="arxiu" value="${arxiu}" scope="request"/>
           		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
			</c:forEach>
		</div>
		<c:if test="${!isGerencia}">
			<div class="col-md-12">
	      		<input type="file" class="btn" name="informe" multiple/><br/>																 		
			</div>
		</c:if>
	</div>	
</div>
	