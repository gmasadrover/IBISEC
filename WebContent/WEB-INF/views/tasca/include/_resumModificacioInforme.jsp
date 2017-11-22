<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		

		<div class="panel-body">				      	
			<p>			                     				
				<label>Objecte:</label> ${propostaModificacio.propostaInformeSeleccionada.objecte}
			</p>
			<p>
				<label>Tècnic:</label> ${propostaModificacio.usuari.getNomComplet()}
			</p>	
			<p>
				<label>Data:</label> ${propostaModificacio.getDataCreacioString()}
			</p>		                         	
			<p>
				<label>Tipus de contracte:</label> <m:message key="${propostaModificacio.propostaInformeSeleccionada.tipusObra}"/>
			</p>
			<c:if test="${propostaModificacio.propostaInformeSeleccionada.tipusObra=='obr'}">
				<div class="row">
					<div class="col-md-4">
						<label>Requereix llicència:</label> ${propostaModificacio.propostaInformeSeleccionada.llicencia ? propostaModificacio.propostaInformeSeleccionada.tipusLlicencia : "No"}
					</div>					
				</div>
				<p></p>
			</c:if>
			<p>
				<label>Termini d'execució:</label> ${propostaModificacio.propostaInformeSeleccionada.termini}
			</p>	
			<p>
				<label>Empresa adjudicataria:</label> ${propostaModificacio.ofertaSeleccionada.nomEmpresa} (${propostaModificacio.ofertaSeleccionada.cifEmpresa})
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
			       		<label>PBase:</label> ${propostaModificacio.ofertaSeleccionada.pbase}€		
			       	</p>				                                
				</div>
				<div class="col-md-4">
					<p> 
						<label>IVA:</label> ${propostaModificacio.ofertaSeleccionada.iva}€
					</p>
				</div>
				<div class="col-md-4">
					<p>
						<label>Plic:</label> ${propostaModificacio.ofertaSeleccionada.plic}€
					</p>
			   	</div>					  
			</div>
			<p>			                     				
				<label>Comentari tècnic:</label> ${propostaModificacio.ofertaSeleccionada.comentari}
			</p>
			<c:if test="${propostaModificacio.propostaTecnica.ruta != null}">		
				<div class="document">			
	               	<label>Informe justificatiu:</label>													                  	
	           		<a target="_blanck" href="downloadFichero?ruta=${propostaModificacio.propostaTecnica.getEncodedRuta()}">
						${propostaModificacio.propostaTecnica.nom}
					</a>
					<c:if test="${propostaModificacio.propostaTecnica.signat}">
							<span class="glyphicon glyphicon-pencil signedFile"></span>
					</c:if><br>
					<div class="infoSign hidden">
						<c:forEach items="${propostaModificacio.propostaTecnica.firmesList}" var="firma" >
							<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
							<br>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<p></p>
			<c:if test="${propostaModificacio.conformeAreaEconomivaPropostaActuacio.ruta != null}">		
				<div class="document">			
	               	<label>Autorització àrea econòmica signada:</label>													                  	
	           		<a target="_blanck" href="downloadFichero?ruta=${propostaModificacio.conformeAreaEconomivaPropostaActuacio.getEncodedRuta()}">
						${propostaModificacio.conformeAreaEconomivaPropostaActuacio.nom}
					</a>
					<c:if test="${propostaModificacio.conformeAreaEconomivaPropostaActuacio.signat}">
							<span class="glyphicon glyphicon-pencil signedFile"></span>
					</c:if><br>
					<div class="infoSign hidden">
						<c:forEach items="${propostaModificacio.conformeAreaEconomivaPropostaActuacio.firmesList}" var="firma" >
							<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
							<br>
						</c:forEach>
					</div>
				</div>
			</c:if>		
			<p></p>
			<c:if test="${propostaModificacio.autoritzacioPropostaDespesa.ruta != null}">		
				<div class="document">			
	               	<label>Autorització proposta despesa:</label>													                  	
	           		<a target="_blanck" href="downloadFichero?ruta=${propostaModificacio.autoritzacioPropostaDespesa.getEncodedRuta()}">
						${propostaModificacio.autoritzacioPropostaDespesa.nom}
					</a>
					<c:if test="${propostaModificacio.autoritzacioPropostaDespesa.signat}">
							<span class="glyphicon glyphicon-pencil signedFile"></span>
					</c:if><br>
					<div class="infoSign hidden">
						<c:forEach items="${propostaModificacio.autoritzacioPropostaDespesa.firmesList}" var="firma" >
							<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
							<br>
						</c:forEach>
					</div>
				</div>
			</c:if>		
			<p></p>
		</div>
	