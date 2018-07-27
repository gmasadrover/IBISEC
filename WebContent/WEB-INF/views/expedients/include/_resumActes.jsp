<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentacioActes">	
	<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
	<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
	<input type="hidden" name="idInforme" value="${informePrevi.idInf}">			    
	<p>
		<label>Acta replanteig:</label>
	</p>	
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.documentActaReplanteig}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>
	<div class="form-group">
		<label class="col-xs-2 control-label">Adjuntar arxius:</label>
           <div class="col-xs-5">   
           	<input type="file" class="btn" name="documentActaReplanteig" multiple/><br/>
		</div> 
	</div>
	<p>
		<label>Acta comprovació replanteig:</label>
	</p>	
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.documentActaComprovacioReplanteig}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>
	<div class="form-group">
		<label class="col-xs-2 control-label">Adjuntar arxius:</label>
           <div class="col-xs-5">   
           	<input type="file" class="btn" name="documentActaComprovacioReplanteig" multiple/><br/>
		</div> 
	</div>
	<p>
		<label>Acta inici d'obra:</label>
	</p>		
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.documentActaIniciObra}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>
	<div class="form-group">
		<label class="col-xs-2 control-label">Adjuntar arxius:</label>
           <div class="col-xs-5">   
           	<input type="file" class="btn" name="documentActaIniciObra" multiple/><br/>
		</div> 
	</div>
	<p>
		<label>Acta aprovació pla de seguretat:</label>
	</p>
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.documentActaAprovacioPlaSeguretat}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>	
	<div class="form-group">
		<label class="col-xs-2 control-label">Adjuntar arxius:</label>
           <div class="col-xs-5">   
           	<input type="file" class="btn" name="documentActaAprovacioPlaSeguretat" multiple/><br/>
		</div> 
	</div>
	<p>
		<label>Acta aprovació pla de gestió de residus:</label>
	</p>	
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.documentActaAprovacioResidus}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>	
	<div class="form-group">
		<label class="col-xs-2 control-label">Adjuntar arxius:</label>
           <div class="col-xs-5">   
           	<input type="file" class="btn" name="documentActaAprovacioResidus" multiple/><br/>
		</div> 
	</div>
	<p>
		<label>Acta aprovació del programa de treball:</label>
	</p>		
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.documentActaAprovacioProgramaTreball}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>
	<div class="form-group">
		<label class="col-xs-2 control-label">Adjuntar arxius:</label>
           <div class="col-xs-5">   
           	<input type="file" class="btn" name="documentActaAprovacioProgramaTreball" multiple/><br/>
		</div> 
	</div>
	<p>
		<label>Acta de recepció:</label>
	</p>	
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.documentActaRecepcio}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>	
	<div class="form-group">
		<label class="col-xs-2 control-label">Adjuntar arxius:</label>
           <div class="col-xs-5">   
           	<input type="file" class="btn" name="documentActaRecepcio" multiple/><br/>
		</div> 
	</div>	
		<p>
		<label>Acta medició general:</label>
	</p>	
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.documentActaMedicioGeneral}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>	
	<div class="form-group">
		<label class="col-xs-2 control-label">Adjuntar arxius:</label>
           <div class="col-xs-5">   
           	<input type="file" class="btn" name="documentActaMedicioGeneral" multiple/><br/>
		</div> 
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="row">	  			
				<div class="col-md-offset-9 col-md-2 margin_top30">
					<input type="submit" class="btn btn-primary" value="Actualitzar" />
				</div>
	    	</div>       
		</div>
	</div>
</form>	  