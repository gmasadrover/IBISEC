<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<div class="row">
	<div class="col-md-12">		      	
		<p>			                     				
			<label>Recurs:</label> ${informePrevi.recursAdministratiu}
		</p>	
	</div>
</div>
<p>
	<label>Documentació recursos administratius:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentsRecursosAdministratius}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
	</c:forEach>
	<br>					            		
</div>
<c:if test="${canModificarExpedient}">
	<div class="row">            			
		<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentsRecursosAdministratius">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	            <div class="col-xs-5">   
	            	<input type="file" class="btn" name="file" multiple/><br/>
				</div> 
				<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
				<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
				<input type="hidden" name="idInforme" value="${informePrevi.idInf}">			    
				<div class="col-xs-2"> 
					<input type="submit" class="btn btn-primary loadingButton" value="Pujar" />
				</div>    						
			</div>         				
		</form>							
	</div>
</c:if>