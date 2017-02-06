<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<h2>Tasca</h2>
<div class="panel panel-${tasca.activa ? "success" : "danger"}">
   	<div class="panel-heading">
    	<div class="row">
       		<div class="col-lg-4">
       			Referència Tasca: ${tasca.idTasca}
       		</div>
       		<div class="col-lg-4">
       			Assumpte: ${tasca.name}
      		</div>
       		<div class="col-lg-4">
       			Responsable: ${tasca.usuari.getNomComplet()}
      		</div>
       	</div>
   	</div>
</div>
<div class="panel panel-${actuacio.activa ? actuacio.aprovada ? "success" : "warning" : "danger"}">
    <div class="panel-heading">
        <div class="row">
    		<div class="col-lg-6">
    			id actuació: <a href="actuacionsDetalls?ref=${actuacio.referencia}">${actuacio.referencia}</a>
    		</div>
    		<div class="col-lg-6">
    			Centre: ${actuacio.nomCentre}
   			</div>
    	</div>
    </div>
    <div class="panel-body">
        <p>${actuacio.descripcio}</p>
    </div>
    <div class="panel-footer">
    	<div class="row">
    		<div class="col-lg-6">
    			Data Petició: ${actuacio.peticioString}
    		</div>
    		<div class="col-lg-6">
    			Solicitant: ${actuacio.solicitant}
   			</div>
    	</div>
    </div>
</div>