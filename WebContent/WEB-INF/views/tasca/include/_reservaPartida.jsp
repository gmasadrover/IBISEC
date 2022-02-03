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
			<label>Comentari administratiu:</label> ${informePrevi.propostaInformeSeleccionada.comentariAdministratiu}
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
		 <div class="row">
			<div class="col-md-4">
				<p>
					<label>Partida:</label>									
					<div class="table-responsive col-xs-offset-1 col-md-12">							                        
	                    <table class="table table-striped table-bordered filerTable">
	                    	<thead>
	                            <tr>
	                           		<th>Partida</th>	
	                                <th>Valor</th>
	                                <th></th>			                                        							                                       
	                            </tr>
	                        </thead>
	                        <tbody>
	                        <c:set var="total" value="0" />
							<c:forEach items="${informePrevi.assignacioCredit}" var="assignacioCredit" >
					          	<tr>		
					          		<td><a target="_black" href="partidaDetalls?codi=${assignacioCredit.partida.codi}">${assignacioCredit.partida.codi} (${assignacioCredit.partida.nom})</a></td>	
					          		<td>${assignacioCredit.getValorPAFormat()}</td>	
					          		<td><input class="btn btn-primary carregarModal" data-idassignacio="${assignacioCredit.idAssignacio}" data-valorassignacio="${assignacioCredit.valorPA}" data-partidaprev="${assignacioCredit.partida.subpartidaDe}" data-subpartidaprev="${assignacioCredit.partida.codi}" data-toggle="modal" data-target="#modalAssignacio" name="modificar" value="Modificar"></td>
					          		<c:set var="total" value="${total + assignacioCredit.valorPA}" />
					          	</tr>
					      	</c:forEach>  	
					      		<tr>
					      			<td>Total</td>
					      			<td><m:formatNumber pattern= "#,##0.00" type = "number" value ="${total}"/>€</td>
					      			<td><input class="btn btn-success carregarModal" data-idassignacio="-1" data-valorassignacio="${informePrevi.propostaInformeSeleccionada.plic - total}" data-toggle="modal" data-target="#modalAssignacio" name="novaAssignacio" value="Afegir"></td>
					      		</tr>	
					      	</tbody>					      		     
	                    </table>
	                </div>
				</p>
			</div>		
		</div>						
		<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.size() > 0}">
			<p>
				<div class="document">
					<label>Certificat d'existència de crèdit:</label>	
					<div class="row col-md-12">
						<c:forEach items="${informePrevi.conformeAreaEconomivaPropostaActuacio}" var="arxiu" >
							<c:set var="arxiu" value="${arxiu}" scope="request"/>
							<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
						</c:forEach>
						<br>					            		
					</div>
				</div>																	
			</p>	
		</c:if>	
	</div>
 </div>
 <c:if test="${canRealitzarTasca}">
	<div class="panel-body">
		<form class="form-horizontal" target="_blank" method="POST" action="DoAddReserva" onsubmit="setTimeout(function () { window.location.reload(); }, 10)">
			<input type="hidden" name="idIncidencia" value="${actuacio.idIncidencia}">
	   		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
	   		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
	   		<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${informePrevi.idInf}">	
	   		<input type="hidden" name="importReserva" value="${informePrevi.propostaInformeSeleccionada.plic}">	
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
		
			<div class="separator"></div>												        	
			<div class="panel-body">
		     	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoTasca">
			     	<input type="hidden" name="document" value="autoritzacioAreaFinancera">
					<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
					<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
					<input type="hidden" name="idTasca" value="${tasca.idTasca}">
					<input type="hidden" name="idInforme" value="${informePrevi.idInf}">																	
			       <c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.size() > 0}">
						<p>
							<div class="document">
								<label>Certificat d'existència de crèdit:</label>	
								<div class="row col-md-12">
									<c:forEach items="${informePrevi.conformeAreaEconomivaPropostaActuacio}" var="arxiu" >
										<c:set var="arxiu" value="${arxiu}" scope="request"/>
										<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
									</c:forEach>
									<br>					            		
								</div>
							</div>																	
						</p>	
					</c:if>																
					<div class="col-md-8">
						<div class="row margin_top10">
			    			<div class="col-md-12">
			           			Pujar Autorització àrea econòmica signada: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
			    			</div>
			    		</div>																													        			
		      		</div>	
		      		<div class="col-md-4">												        		
		    		<div class="row">
		        		<div class="col-md-12">															        																						 				
					 		<input class="btn btn-success margin_top30 upload" type="submit" name="guardar" value="Enviar Autorització àrea econòmica signat">
					 	</div>
		     		</div>
		    		</div>
		  		</form>	
		  	</div>
		</div>
		 <form class="form-horizontal" method="POST" enctype="multipart/form-data" action="modificarAssignacioPartida">
			<input class="hidden" name="expedient" value="${informePrevi.expcontratacio.expContratacio}">
             		<input class="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
             		<input class="hidden" name="idInforme" value="${informePrevi.idInf}">
             		<input class="hidden" name="redireccio" value="/tasca?id=${tasca.idTasca}">	
             		<input class="hidden" name="idAssignacio" id="idAssignacioModal" value="">	
      			<!-- Modal -->	        			
			<div id="modalAssignacio" class="modal fade" role="dialog">
				<div class="modal-dialog">																	
			    <!-- Modal content-->
			    	<div class="modal-content">
			      		<div class="modal-header">
			        		<button type="button" class="close" data-dismiss="modal">&times;</button>
			        		<h4 class="modal-title">Modificar assignació</h4>
			      		</div>
			      		<div class="modal-body">
			        		<div class="form-group">
					    		<div class="col-md-6">	
					      			<label>Partida asignada</label>		
					      			<input type="hidden" id="partidaPrev" value="" >							            	 										            	 	
					                <select class="form-control selectpicker llistaPartides" name="llistaPartides" id="llistaPartides">
					                	<option value="-1">No assignar partida</option>
					                	<c:forEach items="${partidesList}" var="partida">
					                		<option value="${partida.codi}">${partida.codi} (${partida.nom} - Restant: ${partida.getPartidaPerAsignarFormat()})</option>
					                	</c:forEach>					                                	
					                </select>	
					            </div>					                       		
					       	</div>	  
					        <div class="form-group">
					    		<div class="col-md-6">	
					      			<label>SubPartida asignada</label>	
					      			<input type="hidden" id="subPartidaPrev" value="" >										            	 										            	 	
					                <select class="form-control selectpicker" name="llistaSubPartides" id="llistaSubPartides"></select>	
					            </div>					                       		
					       	</div>
					       	<div class="form-group">
						       	<div class="col-md-6">
						          	<label>Valor</label>
						          	<input name="valorAssignacio" class="valorAssignacio" required>
						          	<label class="">€</label>
						        </div>
							</div>
			      		</div>
			      		<div class="modal-footer">
			      			<input class="btn btn-danger" type="submit" name="eliminar" value="Eliminar">
			      			<input class="btn btn-success" type="submit" name="modificar" value="Modificar">
			      		</div>
		    		</div>																	
			  	</div>
			</div> 
		</form>
	</c:if>
