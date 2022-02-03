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
			<label>Objecte:</label> ${propostaActuacio.objecte}
		</p>	
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<p>			                     				
			<label>Comentari tècnic:</label> ${propostaActuacio.comentari}
		</p>
	</div>	
</div>
<div class="row">
	<div class="col-md-12">
		<p>			                     				
			<label>Comentari administratiu:</label> ${propostaActuacio.comentariAdministratiu}
		</p>
	</div>	
</div>
<div class="row">
	<div class="col-md-4">			                         	
		<p>
			<label>Tipus de contracte:</label> <m:message key="${propostaActuacio.tipusObra}"/>
		</p>
	</div>
	<div class="col-md-4">
		<c:if test="${propostaActuacio.tipusObra=='obr'}">
			<div class="row">
				<div class="col-md-12">
					<label>Autorització urbanística:</label> ${propostaActuacio.llicencia ? propostaActuacio.getTipusLlicenciaFormat() : "No"}
				</div>					
			</div>
			<p></p>
		</c:if>
	</div>
	<div class="col-md-4">
		<p>
			<label>Requereix formalització contracte:</label> ${propostaActuacio.contracte ? "Si" : "No"}
		</p>
	</div>
</div>
<div class="row">
	<div class="col-md-4">
		<p>
			<label> ${propostaActuacio.tipusObra == 'conveni' ? 'Vigència conveni' : 'Termini execució'}:
			</label> ${propostaActuacio.termini}
		</p>
	</div>	
</div>
<div class="row">
	<div class="col-md-4">
       	<label>PBase:</label> <m:formatNumber pattern= "#,##0.00" type = "number" value ="${propostaActuacio.pbase}"/>€					                                
	</div>
	<div class="col-md-4"> 
		<label>IVA:</label> <m:formatNumber pattern= "#,##0.00" type = "number" value ="${propostaActuacio.iva}"/>€
	</div>
	<div class="col-md-4">
		<label>Plic:</label> <m:formatNumber pattern= "#,##0.00" type = "number" value ="${propostaActuacio.plic}"/>€
   	</div>					  
</div>
<p></p>	
<c:if test="${expedient.contracte == 'major'}">
	<div class="row">
		<div class="col-md-4">
	       	<label>VEC:</label> <m:formatNumber pattern= "#,##0.00" type = "number" value ="${propostaActuacio.vec}"/>€					                                
		</div>					  
	</div>	
	<p></p>
</c:if>

