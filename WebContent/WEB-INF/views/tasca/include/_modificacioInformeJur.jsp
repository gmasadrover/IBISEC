<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>			                         	
<div class="panel-body">
	<h4>Modificació informe</h4>	
	<br />		      	
	<p>			                     				
		<label>Objecte:</label> ${informePrevi.propostaInformeSeleccionada.objecte}
	</p>
	<p>
		<label>Tècnic:</label> ${informePrevi.usuari.getNomComplet()}
	</p>	
	<p>
		<label>Data:</label> ${informePrevi.getDataCreacioString()}
	</p>		                         	
	<p>
		<label>Tipus de contracte:</label> <m:message key="${informePrevi.propostaInformeSeleccionada.tipusObra}"/>
	</p>
	<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra=='obr'}">
		<div class="row">
			<div class="col-md-4">
				<label>Requereix llicència:</label> ${informePrevi.propostaInformeSeleccionada.llicencia ? informePrevi.propostaInformeSeleccionada.tipusLlicencia : "No"}
			</div>					
		</div>
		<p></p>
	</c:if>
	<p>
		<label>Termini d'execució:</label> ${informePrevi.propostaInformeSeleccionada.termini}
	</p>	
	<p>
		<label>Empresa adjudicataria:</label> ${informePrevi.ofertaSeleccionada.nomEmpresa} (${informePrevi.ofertaSeleccionada.cifEmpresa})
	</p>
	<div class="row">
		<div class="col-md-12">
			<p>
				<label>Valor afegit</label>
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<p>
	       		<label>PBase:</label> ${informePrevi.ofertaSeleccionada.pbase}€		
	       	</p>				                                
		</div>
		<div class="col-md-4">
			<p> 
				<label>IVA:</label> ${informePrevi.ofertaSeleccionada.iva}€
			</p>
		</div>
		<div class="col-md-4">
			<p>
				<label>Plic:</label> ${informePrevi.ofertaSeleccionada.plic}€
			</p>
	   	</div>					  
	</div>
	<div class="row">
		<div class="col-md-12">
			<p>
				<label>Valor Total: ${informeActuacioOriginal.ofertaSeleccionada.plic + informePrevi.ofertaSeleccionada.plic}€</label>
			</p>
		</div>
	</div>
	<p>			                     				
		<label>Comentari tècnic:</label> ${informePrevi.ofertaSeleccionada.comentari}
	</p>
	<c:if test="${informePrevi.propostaTecnica.ruta != null}">					
             	<label>Informe justificatiu:</label>													                  	
         		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaTecnica.getEncodedRuta()}">
			${informePrevi.propostaTecnica.nom}
		</a>
		<c:if test="${informePrevi.propostaTecnica.signat}">
				<span class="glyphicon glyphicon-pencil signedFile"></span>
		</c:if><br>
		<div class="infoSign hidden">
			<c:forEach items="${informePrevi.propostaTecnica.firmesList}" var="firma" >
				<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
				<br>
			</c:forEach>
		</div>
	</c:if>
 </div>