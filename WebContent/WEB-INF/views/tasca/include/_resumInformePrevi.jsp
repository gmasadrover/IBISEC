<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<div class="panel panel-${propostaActuacio.seleccionada ? 'success' : 'default'} default">
   	<div class="panel-heading">
		<h4 class="panel-title">
			<a data-toggle="collapse" data-parent="#accordionPropostes${informePrevi.idInf}" href="#propostaActuacio${numPA}">Proposta actuació ${numPA}</a>
			<c:if test="${canModificarActuacio and informePrevi.getDataAprovacio() == null and actuacio.isActiva() and ! propostaActuacio.seleccionada}">
				<input class="btn btn-xs btn-success seleccionarProposta" data-proposta="${propostaActuacio.idProposta}" data-informe="${informePrevi.idInf}" value="Seleccionar">
			</c:if>
		</h4>
	</div>
	<div id="propostaActuacio${numPA}" class="panel-collapse collapse ${view == 'dadesT' ? 'in' : ''}">					    	
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
			<c:if test="${propostaActuacio.tipusObra} == 'obr'">
				<div class="row">
					<div class="col-md-4">
						<label>Requereix llicència:</label> ${propostaActuacio.llicencia ? "Si" : "No"}
					</div>
					<c:if test="${propostaActuacio.llicencia}">
						<div class="col-md-4">
							<label>Tipus llicència:</label> ${propostaActuacio.tipusLlicencia}
						</div>
					</c:if>
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
			       	<label>VEC:</label> ${propostaActuacio.vec}€						                                
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
	</div>
</div>
	