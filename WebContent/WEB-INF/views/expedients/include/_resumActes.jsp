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
	<div class="documentacioActes"></div>
	<p>
		<label>Acta replanteig:</label>
	</p>	
	<div class="row col-md-12">
		<div class="actaReplanteig"></div>
		<br>					            		
	</div>
	<c:if test="${isIBISEC}">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	           <div class="col-xs-5">   
	           	<input type="file" class="btn" name="documentActaReplanteig" multiple/><br/>
			</div> 
		</div>
	</c:if>
	<p>
		<label>Acta comprovació replanteig:</label>
	</p>	
	<div class="row col-md-12">
		<div class="actaComprovacioReplanteig"></div>
		<br>					            		
	</div>
	<c:if test="${isIBISEC}">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	           <div class="col-xs-5">   
	           	<input type="file" class="btn" name="documentActaComprovacioReplanteig" multiple/><br/>
			</div> 
		</div>
	</c:if>
	<p>
		<label>Acta inici d'obra:</label>
	</p>		
	<div class="row col-md-12">
		<div class="actaIniciObra"></div>
		<br>					            		
	</div>
	<c:if test="${isIBISEC}">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	           <div class="col-xs-5">   
	           	<input type="file" class="btn" name="documentActaIniciObra" multiple/><br/>
			</div> 
		</div>
	</c:if>
	<p>
		<label>Acta aprovació pla de seguretat:</label>
	</p>
	<div class="row col-md-12">
		<div class="actaAprovacioPlaSeguretat"></div>
		<br>					            		
	</div>	
	<c:if test="${isIBISEC}">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	           <div class="col-xs-5">   
	           	<input type="file" class="btn" name="documentActaAprovacioPlaSeguretat" multiple/><br/>
			</div> 
		</div>
	</c:if>
	<p>
		<label>Acta aprovació pla de gestió de residus:</label>
	</p>	
	<div class="row col-md-12">
		<div class="actaAprovacioResidus"></div>
		<br>					            		
	</div>	
	<c:if test="${isIBISEC}">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	           <div class="col-xs-5">   
	           	<input type="file" class="btn" name="documentActaAprovacioResidus" multiple/><br/>
			</div> 
		</div>
	</c:if>
	<p>
		<label>Acta aprovació del programa de treball:</label>
	</p>		
	<div class="row col-md-12">
	<div class="actaAprovacioProgramaTreball"></div>
		<br>					            		
	</div>
	<c:if test="${isIBISEC}">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	           <div class="col-xs-5">   
	           	<input type="file" class="btn" name="documentActaAprovacioProgramaTreball" multiple/><br/>
			</div> 
		</div>
	</c:if>
	<p>
		<label>Acta de recepció:</label>
	</p>	
	<div class="row col-md-12">
		<div class="actaRecepcio"></div>
		<br>					            		
	</div>	
	<c:if test="${isIBISEC}">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	           <div class="col-xs-5">   
	           	<input type="file" class="btn" name="documentActaRecepcio" multiple/><br/>
			</div> 
		</div>	
	</c:if>
	<p>
		<label>Acta medició general:</label>
	</p>	
	<div class="row col-md-12">
		<div class="actaMedicioGeneral"></div>
		<br>					            		
	</div>	
	<c:if test="${isIBISEC}">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	           <div class="col-xs-5">   
	           	<input type="file" class="btn" name="documentActaMedicioGeneral" multiple/><br/>
			</div> 
		</div>
	</c:if>
	<c:if test="${isIBISEC}">
	<div class="row">
		<div class="col-md-12">
			<div class="row">	  			
				<div class="col-md-offset-9 col-md-2 margin_top30">
					<input type="submit" class="btn btn-primary loadingButton" value="Actualitzar" />
				</div>
	    	</div>       
		</div>
	</div>
	</c:if>
</form>	  