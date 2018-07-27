<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>			                         	
<div class="panel-body">
	<div class="panel-body">
		<h4>Informe inicial</h4>	
		<br />
		<p>
			<label>Expedient:</label> ${informePrevi.expcontratacio.expContratacio}
		</p>
		<p>
			<label>Tècnic:</label> ${informePrevi.usuari.getNomComplet()}
		</p>
		<p>
			<label>Data:</label> ${informePrevi.getDataCreacioString()}
		</p>
		<p>			                     				
			<label>Objecte:</label> ${informePrevi.propostaInformeSeleccionada.objecte}
		</p>	
		<p>			                     				
			<label>Comentari tècnic:</label> ${informePrevi.propostaInformeSeleccionada.comentari}
		</p>				                         	
		<p>
			<label>Tipus de contracte:</label> <m:message key="${informePrevi.propostaInformeSeleccionada.tipusObra}"/>
		</p>
		<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra} == 'obr'">
			<div class="row">
				<div class="col-md-4">
					<label>Requereix llicència:</label> ${informePrevi.propostaInformeSeleccionada.llicencia ? "Si" : "No"}
				</div>
				<c:if test="${informePrevi.propostaInformeSeleccionada.llicencia}">
					<div class="col-md-4">
						<label>Tipus llicència:</label> ${informePrevi.propostaInformeSeleccionada.tipusLlicencia}
					</div>
				</c:if>
			</div>
			<p></p>
		</c:if>
		<p>
			<label>Requereix formalització contracte:</label> ${informePrevi.propostaInformeSeleccionada.contracte ? "Si" : "No"}
		</p>
		<p>
			<label>Termini d'execució:</label> ${informePrevi.propostaInformeSeleccionada.termini}
		</p>	
		<div class="row">
			<div class="col-md-4">
		       	<label>PBase:</label> ${informePrevi.propostaInformeSeleccionada.pbase}€						                                
			</div>
			<div class="col-md-4"> 
				<label>IVA:</label> ${informePrevi.propostaInformeSeleccionada.iva}€
			</div>
			<div class="col-md-4">
				<label>Plic:</label> ${informePrevi.propostaInformeSeleccionada.plic}€
		   	</div>					  
		</div>	
		<p></p>
		<p>
			<label>Arxius ajunts:</label>
		</p>	
		<div class="row col-md-12">
			<c:forEach items="${informePrevi.adjunts}" var="arxiu" >
				<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
				<br>
			</c:forEach>					            		
		</div>
		<p>			                     				
			<label>Notes:</label> ${informePrevi.notes}
		</p>
		<p>
			<label>Partida:</label> ${informePrevi.assignacioCredit.partida.nom}
		</p>
	</div>
 </div>
 <c:if test="${canRealitzarTasca}">
	<div class="panel-body">   	
     	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA">
	     	<input type="hidden" name="document" value="documentsPreLicitacio">
			<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
			<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">															
			<input type="hidden" name="idInforme" value="${informePrevi.idInf}">	
			<input type="hidden" name="idTasca" value="${tasca.idTasca}">
			<c:if test="${informePrevi.memoriaOrdreInici.ruta != null}">			
				<div class="col-md-12">				       			
	       			<div class="document">
	              			<label>Memòria més ordre d'inici signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.memoriaOrdreInici.getEncodedRuta()}">
							${informePrevi.memoriaOrdreInici.nom}
						</a>	
						<c:if test="${informePrevi.memoriaOrdreInici.signat}">
							<span data-ruta="${informePrevi.memoriaOrdreInici.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if>
						<br>
						<div class="infoSign hidden">
						</div>
					</div>	
					<input type="file" class="btn uploadImage" name="memoriaOrdreInici" /><br/>	
	       		</div>
       		</c:if>	
       		<c:if test="${informePrevi.justProcForma.ruta != null}">
	       		<div class="col-md-12">				       			
	       			<div class="document">
	              			<label>Justificació de procediment signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.justProcForma.getEncodedRuta()}">
							${informePrevi.justProcForma.nom}
						</a>	
						<c:if test="${informePrevi.justProcForma.signat}">
							<span data-ruta="${informePrevi.justProcForma.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if>					
						<br>
						<div class="infoSign hidden">
						</div>
					</div>	
					<input type="file" class="btn uploadImage" name="justProcForma" /><br/>	
	       		</div>
       		</c:if>
       		<c:if test="${informePrevi.justCriterisAdjudicacio.ruta != null}">
	       		<div class="col-md-12">				       			
	       			<div class="document">
	              			<label>Justificació criteris d'adjudicació, condicions d'aptitud licitador i condicions especials signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.justCriterisAdjudicacio.getEncodedRuta()}">
							${informePrevi.justCriterisAdjudicacio.nom}
						</a>	
						<c:if test="${informePrevi.justCriterisAdjudicacio.signat}">
							<span data-ruta="${informePrevi.justCriterisAdjudicacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if>					
						<br>
						<div class="infoSign hidden">
						</div>
					</div>	
					<input type="file" class="btn uploadImage" name="justCriterisAdjudicacio" /><br/>	
	       		</div>
       		</c:if>       		
       		<c:if test="${informePrevi.declaracioUrgencia.ruta != null}">
	       		<div class="col-md-12">				       			
	       			<div class="document">
	              			<label>Declaració d'urgència signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.declaracioUrgencia.getEncodedRuta()}">
							${informePrevi.declaracioUrgencia.nom}
						</a>	
						<c:if test="${informePrevi.declaracioUrgencia.signat}">
							<span data-ruta="${informePrevi.declaracioUrgencia.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if>					
						<br>
						<div class="infoSign hidden">
						</div>
					</div>	
					<input type="file" class="btn uploadImage" name="declaracioUrgencia" /><br/>	
	       		</div>
       		</c:if>
       		<c:if test="${informePrevi.aprovacioDispoTerrenys.ruta != null}">	
	       		<div class="col-md-12">				       			
	       			<div class="document">
	              			<label>Resolució d'aprovació del projecte amb indicació de disponibilitat dels terrenys signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.aprovacioDispoTerrenys.getEncodedRuta()}">
							${informePrevi.aprovacioDispoTerrenys.nom}
						</a>	
						<c:if test="${informePrevi.aprovacioDispoTerrenys.signat}">
							<span data-ruta="${informePrevi.aprovacioDispoTerrenys.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if>					
						<br>
						<div class="infoSign hidden">
						</div>
					</div>	
					<input type="file" class="btn uploadImage" name="aprovacioDispoTerrenys" /><br/>	
	       		</div>	
       		</c:if>
       		<c:if test="${informePrevi.informeInsuficienciaMitjans.ruta != null}">	
	       		<div class="col-md-12">				       			
	       			<div class="document">
	              		<label>Informe insuficiència de mitjans signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.informeInsuficienciaMitjans.getEncodedRuta()}">
							${informePrevi.informeInsuficienciaMitjans.nom}
						</a>	
						<c:if test="${informePrevi.informeInsuficienciaMitjans.signat}">
							<span data-ruta="${informePrevi.informeInsuficienciaMitjans.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if>					
						<br>
						<div class="infoSign hidden">
						</div>
					</div>	
					<input type="file" class="btn uploadImage" name="insuficienciaMitjans" /><br/>	
	       		</div>	
       		</c:if>
       		<c:if test="${informePrevi.aprovacioEXPPlecsDespesa.ruta != null}">	
	       		<div class="col-md-12">				       			
	       			<div class="document">
	              			<label>Aprovació expedient, plecs i despesa signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.aprovacioEXPPlecsDespesa.getEncodedRuta()}">
							${informePrevi.aprovacioEXPPlecsDespesa.nom}
						</a>	
						<c:if test="${informePrevi.aprovacioEXPPlecsDespesa.signat}">
							<span data-ruta="${informePrevi.aprovacioEXPPlecsDespesa.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if>					
						<br>
						<div class="infoSign hidden">
						</div>
					</div>	
					<input type="file" class="btn uploadImage" name="aprovacioEXPPlecsDespesa" /><br/>	
	       		</div>
       		</c:if>
      		<div class="col-md-4">												        		
	    		<div class="row">
	        		<div class="col-md-12">															        																						 				
				 		<input class="btn btn-success margin_top30 upload" type="submit" name="guardar" value="Enviar documents signats">
				 	</div>
	     		</div>
     		</div>															     											    		
  		</form>			
	</div>
</c:if>
