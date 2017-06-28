<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<input type="hidden" id="resPartida">				                         	
<div class="panel-body">
	<c:set var="numPA" value="1" scope="request" />
	<div class="panel-body">
		<h4>Informe inicial</h4>	
		<br />
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
		       	<label>VEC:</label> ${informePrevi.propostaInformeSeleccionada.vec}€						                                
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
				<a target="_blanck" href="downloadFichero?ruta=${arxiu.ruta}">${arxiu.nom}</a>
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
<div class="panel-body">
	<form class="form-horizontal" target="_blank" method="POST" action="DoAddReserva" onsubmit="setTimeout(function () { window.location.reload(); }, 10)">
		<input type="hidden" name="idIncidencia" value="${actuacio.idIncidencia}">
   		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
   		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
   		<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${informePrevi.idInf}">	
   		<input type="hidden" name="importReserva" value="${informePrevi.propostaInformeSeleccionada.plic}">					                    		
   		<div class="form-group">
    		<div class="col-md-4">	
      			<label>Partida asignada</label>									            	 										            	 	
                <select class="form-control selectpicker" name="llistaPartides" id="llistaPartides">
                	<c:forEach items="${partidesList}" var="partida">
                		<option value="${partida.codi}">${partida.nom} - Restant: ${partida.getPartidaPerAsignarFormat()}</option>
                	</c:forEach>					                                	
                </select>	
            </div>					                       		
       	</div>	
   		<div class="form-group">
    		<div class="col-md-12">		
    			<div class="row">	 
    				<div class="col-md-12">						                    						
    					<textarea class="form-control" name="comentariFinancer" placeholder="observacions" rows="3"></textarea> 
      				</div>
      			</div>
      		</div>						                       		
       	</div>	
       	<div class="form-group">
       		<div class="col-md-6">
		        <div class="row">
		            <div class="col-md-12">
						<input class="btn btn-success" type="submit" name="reservar" value="Generar conforme àrea econòmico-financera">
					</div>
		        </div>
	    	</div>
		    <div class="col-md-6">
		        <div class="row">
		            <div class="col-md-12">
						<input class="btn btn-danger" type="submit" name="rebutjar" value="Generar no conforme">
					</div>
		        </div>
		    </div>
		</div>	                       	
	</form>
	<c:if test="${estatActuacio == 4}">
		<div class="separator"></div>												        	
		<div class="panel-body">
	     	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA">
		     	<input type="hidden" name="document" value="autoritzacioAreaFinancera">
				<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
				<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
				<input type="hidden" name="idTasca" value="${tasca.idTasca}">
				<input type="hidden" name="idInforme" value="${informePrevi.idInf}">																	
		       	<c:if test="${hasVistiplauPA}">
					<div class="col-md-12">	
		               	<p>Vistiplau proposta d'actuació signada:</p>													                  	
		           		<a target="_blanck" href="downloadFichero?ruta=${vistiplauPropostaActuacioFirmada.ruta}">
							${vistiplauPropostaActuacioFirmada.nom}
						</a>																			
					</div>
				</c:if>																	
				<div class="col-md-8">
					<div class="row margin_top10">
		    			<div class="col-md-12">
		           			Pujar Vistiplau proposta d'actuació signada: <input type="file" class="btn" name="informe" /><br/>																 		
		    			</div>
		    		</div>																													        			
	      		</div>	
	      		<div class="col-md-4">												        		
	    		<div class="row">
	        		<div class="col-md-12">															        																						 				
				 		<input class="btn btn-success margin_top30" type="submit" name="guardar" value="Enviar vistiplau signat">
				 	</div>
	     		</div>
	    		</div>
	  		</form>	
	  	</div>	
	</c:if>
</div>