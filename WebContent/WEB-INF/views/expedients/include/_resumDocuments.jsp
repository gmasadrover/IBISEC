<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<p>
	<label>Altra documentació:</label>
</p>	
<div class="row col-md-12 margin_bottom30">
	<c:forEach items="${actuacio.arxiusAdjunts.arxiusAltres}" var="arxiu" >
		<div class="document">
			<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
				${arxiu.getDataString()} - ${arxiu.nom}
			</a>
			<c:if test="${arxiu.signat}">
				<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<c:if test="${canModificarExpedient && arxiu.ruta != null}">
				<span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${arxiu.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>
		</div>					            		
	</c:forEach>
	<c:forEach items="${informePrevi.adjunts}" var="arxiu" >
		<div class="document">
			<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
				${arxiu.getDataString()} - ${arxiu.nom}
			</a>
			<c:if test="${arxiu.signat}">
				<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<c:if test="${canModificarExpedient && arxiu.ruta != null}">
				<span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${arxiu.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>
		</div>					            		
	</c:forEach>	
</div>
<div class="row">            			
	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentacioAdjunta">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
            <div class="col-xs-5">   
            	<input type="file" class="btn" name="file" multiple/><br/>
			</div> 
			<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
			<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
			<input type="hidden" name="idInforme" value="${informePrevi.idInf}">			    
			<div class="col-xs-2"> 
				<input type="submit" class="btn btn-primary" value="Pujar" />
			</div>    						
		</div>         				
	</form>							
</div>   