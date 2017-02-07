<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<div class="panel panel-${incidencia.activa ? "success" : "danger"}">
    <div class="panel-heading">
        <div class="row">
    		<div class="col-lg-6">
    			id incidència: <a href="incidenciaDetalls?ref=${incidencia.idIncidencia}">${incidencia.idIncidencia}</a>
    		</div>
    		<div class="col-lg-6">
    			Centre: ${incidencia.nomCentre}
   			</div>
    	</div>
    </div>
    <div class="panel-body">
        <p>${incidencia.nom}</p>
    </div>
    <div class="panel-footer">
    	<div class="row">
    		<div class="col-lg-6">
    			Data Petició: ${incidencia.getPeticioString()}
    		</div>
    		<div class="col-lg-6">
    			Solicitant:
   			</div>
    	</div>
    </div>
</div>