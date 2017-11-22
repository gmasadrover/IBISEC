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
			<label>Informe:</label> ${informePrevi.idInf}
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
				<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.nom}</a>
				<br>
			</c:forEach>					            		
		</div>
		<p>
			<label>Comentari Cap:</label> ${informePrevi.comentariCap}
		</p>
		<p>
			<label>Vistiplau:</label> ${informePrevi.usuariCapValidacio.getNomComplet()} - ${informePrevi.getDataCapValidacioString()}
		</p>
		<p>			                     				
			<label>Notes:</label> ${informePrevi.notes}
		</p>
		<p>
			<label>Partida:</label> ${informePrevi.partida}
		</p>
	</div>
 </div>
 <c:if test="${canRealitzarTasca}">
	<div class="panel-body">		
   		<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.ruta != null}">	
           	<div class="document">
              		<label>Certificat existència de crèdit signada:</label>											                  	
           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.conformeAreaEconomivaPropostaActuacio.getEncodedRuta()}">
					${informePrevi.conformeAreaEconomivaPropostaActuacio.nom}
				</a>	
				<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.signat}">
						<span class="glyphicon glyphicon-pencil signedFile"></span>
				</c:if>
				<br>
				<div class="infoSign hidden">
					<c:forEach items="${informePrevi.conformeAreaEconomivaPropostaActuacio.firmesList}" var="firma" >
						<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
						<br>
					</c:forEach>
				</div>
			</div>	
		</c:if>			
     	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA">
	     	<input type="hidden" name="document" value="certificatCredit">
			<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
			<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">															
			<input type="hidden" name="idInforme" value="${informePrevi.idInf}">	
			<input type="hidden" name="idTasca" value="${tasca.idTasca}">																		
			<div class="col-md-8">
				<div class="row margin_top10">
	    			<div class="col-md-12">
	           			Pujar certificat d'existència de crèdit signada per gerència: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
	    			</div>
	    		</div>																													        			
      		</div>	
      		<div class="col-md-4">												        		
	    		<div class="row">
	        		<div class="col-md-12">															        																						 				
				 		<input class="btn btn-success margin_top30 upload" type="submit" name="guardar" value="Enviar certificat existència de crèdit signat">
				 	</div>
	     		</div>
     		</div>															     											    		
  		</form>			
	</div>
</c:if>
