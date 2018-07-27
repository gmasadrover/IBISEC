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
			<label>Informe:</label> ${informePrevi.expcontratacio.expContratacio}
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
			<label>Comentari Cap:</label> ${informePrevi.comentariCap}
		</p>
		<p>
			<label>Vistiplau:</label> ${informePrevi.usuariCapValidacio.getNomComplet()} - ${informePrevi.getDataCapValidacioString()}
		</p>
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
	     	<input type="hidden" name="document" value="autoritzacioPA">
			<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
			<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">															
			<input type="hidden" name="idInforme" value="${informePrevi.idInf}">	
			<input type="hidden" name="idTasca" value="${tasca.idTasca}">	
			<c:if test="${!(informePrevi.expcontratacio.idInforme != null && informePrevi.expcontratacio.idInforme != '-1' && informePrevi.expcontratacio.contracte == 'major')}">
	           	<div class="form-group">
	           		<div class="col-md-12">
			           	<div class="document">
			              		<label>Memòria justificativa i Ordre d'inici:</label>											                  	
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
					</div>																
					<div class="col-md-8">
						<div class="row margin_top10">
			    			<div class="col-md-12">
			           			Pujar ordre d'inici signat: <input type="file" class="btn uploadImage" name="ordreInici" /><br/>																 		
			    			</div>
			    		</div>																													        			
		      		</div>	
	      		</div>
	      	</c:if>
      		<div class="form-group">
      			<div class="col-md-12">
		      		<div class="document">
		              	<label>Certificat d'existència de credit:</label>											                  	
		           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.conformeAreaEconomivaPropostaActuacio.getEncodedRuta()}">
							${informePrevi.conformeAreaEconomivaPropostaActuacio.nom}
						</a>	
						<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.signat}">
								<span data-ruta="${informePrevi.conformeAreaEconomivaPropostaActuacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if>
						<br>
						<div class="infoSign hidden">
						</div>
					</div>	
				</div>															
				<div class="col-md-8">
					<div class="row margin_top10">
		    			<div class="col-md-12">
		           			Pujar certificat d'existència de credit signat: <input type="file" class="btn uploadImage" name="certCredit" /><br/>																 		
		    			</div>
		    		</div>																													        			
	      		</div>	
      		</div>
      		<div class="col-md-4">												        		
	    		<div class="row">
	        		<div class="col-md-12">															        																						 				
				 		<input class="btn btn-success margin_top30 upload" type="submit" name="guardar" value="Enviar autorització signada">
				 	</div>
	     		</div>
     		</div>															     											    		
  		</form>	  		
	</div>
</c:if>
