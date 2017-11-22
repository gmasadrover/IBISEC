<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<div class="panel-body">				      	
	<p>			                     				
		<label>Objecte:</label> ${propostaActuacio.objecte}
	</p>	
	<p>			                     				
		<label>Comentari tècnic:</label> ${propostaActuacio.comentari}
	</p>				                         	
	<p>
		<label>Tipus de contracte:</label> <m:message key="${propostaActuacio.tipusObra}"/>
	</p>
	<c:if test="${propostaActuacio.tipusObra=='obr'}">
		<div class="row">
			<div class="col-md-4">
				<label>Requereix llicència:</label> ${propostaActuacio.llicencia ? propostaActuacio.tipusLlicencia : "No"}
			</div>					
		</div>
		<p></p>
	</c:if>
	<p>
		<label>Requereix formalització contracte:</label> ${propostaActuacio.contracte ? "Si" : "No"}
	</p>
	<p>
		<label>Termini d'execució:</label> ${propostaActuacio.termini}
	</p>	
	<div class="row">
		<div class="col-md-4">
	       	<label>PBase:</label> ${propostaActuacio.pbase}€						                                
		</div>
		<div class="col-md-4"> 
			<label>IVA:</label> ${propostaActuacio.iva}€
		</div>
		<div class="col-md-4">
			<label>Plic:</label> ${propostaActuacio.plic}€
	   	</div>					  
	</div>	
	<p></p>
</div>