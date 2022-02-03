<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<c:choose>
	<c:when test="${actuacio != null}">
		<div class="panel panel-${actuacio.activa ? actuacio.aprovada ? "success" : "warning" : "danger"}">
		    <div class="panel-heading">
		        <div class="row">
		    		<div class="col-md-6">
		    			Anar a l'actuació: <a href="actuacionsDetalls?ref=${actuacio.referencia}&exp=${tasca.idinformeOriginal}" class="loadingButton"  data-msg="obrint actuació...">${actuacio.referencia}</a>
		    		</div>
		    		<div class="col-md-6">
		    			Centre: ${actuacio.centre.getNomComplet()}
		   			</div>
		    	</div>
		    </div>
		    <div class="panel-body">
		        <p>${actuacio.descripcio}</p>
		        <c:if test="${descripcioExpedient != null && descripcioExpedient != ''}">
		        	Expedient: ${descripcioExpedient}
		        </c:if>
		    </div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="panel panel-${incidencia.activa ? "success" : "danger"}">
		    <div class="panel-heading">
		        <div class="row">
		    		<div class="col-md-6">
		    			<c:if test="${isGerencia}">
		    				Anar a la incidència: <a href="incidenciaDetalls?ref=${incidencia.idIncidencia}" class="loadingButton"  data-msg="obrint actuació...">${incidencia.idIncidencia}</a>
		    			</c:if>
		    		</div>
		    		<div class="col-md-6">
		    			Centre: ${incidencia.nomCentre}
		   			</div>
		    	</div>
		    </div>
		    <div class="panel-body">
		        <p>${incidencia.descripcio}</p>		       
		    </div>
		</div>
	</c:otherwise>
</c:choose>