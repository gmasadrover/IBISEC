<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
	<jsp:include page="../_header.jsp"></jsp:include>	
</head>	
<body>	
    <div id="wrapper">
    	<jsp:include page="../_menu.jsp"></jsp:include>
       	<div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Actuació <small>Detalls actuació</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Actuació
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<c:if test="${not empty actuacio}">
				<div class="row">
	                <div class="col-lg-12">
	                    <div class="panel panel-${actuacio.isActiva() ? actuacio.isPaAprovada() ? actuacio.isAprovada() ? "success" : "info" : "warning" : "danger"}">
	                        <div class="panel-heading">
	                           	<div class="row">
	                        		<div class="col-lg-6">
	                        			 ${actuacio.referencia} - ${actuacio.nomCentre}
	                        		</div>
	                        		<div class="col-lg-6">
	                        			Darrera modificació: ${actuacio.modificacio} - ${actuacio.getDarreraModificacioString()}
	                        		</div>
	                        	</div>
	                        </div>
	                        <div class="panel-body">
	                            <p>${actuacio.descripcio}</p>
	                            <br />
	                            <p>Notes: ${actuacio.notes}</p>
	                        </div>
	                        <div class="panel-footer">
	                        	<div class="row">
	                        		<div class="col-lg-3">
	                        			Creació: ${actuacio.getDataCreacioString()}
	                        		</div>
	                        		<div class="col-lg-3">
	                        			<c:if test="${actuacio.isPaAprovada()}">
	                        				Aprovació PA: ${actuacio.getDataAprovarPaString()}
	                        			</c:if>
	                        		</div>
	                        		<div class="col-lg-3">
	                        			<c:if test="${actuacio.isAprovada()}">
	                        				Aprovació: ${actuacio.getDataAprovacioString()}
	                        			</c:if>
	                        		</div>
	                        		<div class="col-lg-3">
	                        			<c:if test="${!actuacio.isActiva()}">
	                        				Tancament: ${actuacio.getDataTancamentString()}
	                        			</c:if>
	                        		</div>
	                        	</div>
	                        </div>
	                    </div>
	                </div>
            	</div> 
            	<div class="row">
            		<form class="form-horizontal" method="POST" action="DoCanvisActuacio">
                		<input class="hidden" name="idActuacio" value=${actuacio.referencia}>
                		<input class="hidden" name="idIncidencia" value=${incidencia.idIncidencia}>
                		<input class="hidden" name="idInforme" value="-1">
                		<div class="form-group">
               				<div class="col-lg-6">
               					
               				</div>
		        			<div class="col-lg-3">
				        		<c:if test="${actuacio.isActiva() && canModificarActuacio}">
				        			<input class="btn btn-danger" data-toggle="modal" data-target="#myModal" name="tancar" value="Tancar actuació">
				        		</c:if>
				        	</div>
				        	<div class="col-lg-2"> 
					        	<c:if test="${actuacio.activa && canCreateInformePrevi}">					      			
						      		<a href="createTasca?idActuacio=${actuacio.referencia}&tipus=infPrev" class="btn btn-primary right" role="button">Sol·licitar valoració econòmica ${informes.size() > 0 ? "nova" : ""}</a>
								</c:if> 
							</div>   
							<!-- Modal -->
							<div id="myModal" class="modal fade" role="dialog">
								<div class="modal-dialog">																	
							    <!-- Modal content-->
							    	<div class="modal-content">
							      		<div class="modal-header">
							        		<button type="button" class="close" data-dismiss="modal">&times;</button>
							        		<h4 class="modal-title">Motiu tancament</h4>
							      		</div>
							      		<div class="modal-body">
							        		<textarea required></textarea>
							      		</div>
							      		<div class="modal-footer">
							        		<input class="btn btn-danger" type="submit" name="tancar" value="Tancar actuació">
							      		</div>
						    		</div>																	
							  	</div>
							</div>                  			 
                 		</div>	                    		                       	
                	</form>		  
            	</div>
            	<div class="row">
            		<div class="col-lg-12 panel-group" id="accordion">
            		  <div class="panel panel-default">
            		  	<div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#dadesTecniques">Dades tècniques</a>
					      </h4>
					    </div>
					    <div id="dadesTecniques" class="panel-collapse collapse">					    	
					      	<div class="panel-body">
					      		<h4>Informes d'actuació</h4>					      		
					      		<div class="col-lg-12 panel-group" id="accordionInformes">
					      			<c:forEach items="${informes}" var="informePrevi" >					      				
					      				<div class="panel panel-default">
						      				<div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordionInformes" href="#informe${informePrevi.idInf}">Informe ${informePrevi.idInf} - ${informePrevi.objecte} - ${informePrevi.getDataCreacioString()} ${informePrevi.partida == "" ? "Pendent de reserva de partida" : "Partida reservada" }</a>
										      </h4>
										    </div>
										    <div id="informe${informePrevi.idInf}" class="panel-collapse collapse">
										    	<div class="panel-body">
											    	<c:set var="informePrevi" scope="request" value="${informePrevi}"></c:set>
											      	<jsp:include page="../tasca/include/_resumInformePrevi.jsp"></jsp:include>
											    </div>
										      	<div class="panel-body">
								    				<c:if test="${informePrevi.llistaOfertes.size()>0}">
								    					<c:set var="ofertes" scope="request" value="${informePrevi.llistaOfertes}"></c:set>
								    					<c:set var="ofertaSeleccionada" scope="request" value="${informePrevi.ofertaSeleccionada}"></c:set>								    					
										    			<jsp:include page="../tasca/include/_resumOfertes.jsp"></jsp:include>
										    			<c:if test="${informePrevi.ofertaSeleccionada.dataAprovacio != null && informePrevi.ofertaSeleccionada.dataAprovacio > '2017-04-01'}">
										            	<p>
										            		<label class="">Autorització generada amb data ${informePrevi.ofertaSeleccionada.getDataAprovacioString()}: </label>	
										            		<a target="_blanck" href="downloadFichero?ruta=${rutaActuacio}/${actuacio.referencia}/autoritzacio_informe_${informePrevi.idInf}.pdf">
																autoritzacio_informe_${informePrevi.idInf}.pdf	
															</a>																
										            	</p>
										            	</c:if>
										            	<c:if test="${informePrevi.ofertaSeleccionada.dataAprovacio == null}">
											            	<c:if test="${actuacio.activa && canModificarActuacio}">				      			
													      		<div class="row margin_bottom10">
														    		<div class="col-lg-12 panel">
																		<a target="_blanck" href="CrearDocument?tipus=autMen&idIncidencia=${incidencia.idIncidencia}&idActuacio=${actuacio.referencia}&idInforme=${informePrevi.idInf}" class="btn btn-success right" role="button">Aprovar i generar autorització actuació</a>
																	</div>
													    		</div>
											    			</c:if>	
										    			</c:if>
										    		</c:if>	
										    	</div>
										    	<c:if test="${informePrevi.ofertaSeleccionada.dataAprovacio != null}">
											    	<div class="panel-body">
									    				<div class="row panel-body">
									    					<h4>Factures</h4>
											    			<div class="table-responsive">                        
									                            <table class="table table-striped table-bordered filerTable factures">
									                            	<thead>
									                                    <tr>                                        
									                                    	<th>Factura</th>
									                                        <th>Actuació</th>
									                                        <th>Data entrada</th>
									                                        <th>Data entrada</th>
									                                        <th>Data factura</th>
									                                        <th>Data factura</th>
									                                        <th>Import</th>
									                                        <th>Tipus</th>
									                                        <th>Proveïdor</th>
									                                        <th>notes</th>  
									                                    </tr>
									                                </thead>
									                                <tbody>
									                                	<c:forEach items="${informePrevi.llistaFactures}" var="factura" >
																          	<tr class="">							          	
																           		<td><a href="facturaDetalls?ref=${factura.idFactura}">${factura.idFactura}</a></td>
																            	<td>${factura.idActuacio}</td>
																            	<td>${factura.getDataEntradaString()}</td>
																            	<td>${factura.dataEntrada}</td>
																            	<td>${factura.getDataFacturaString()}</td>
																            	<td>${factura.dataFactura}</td>
																            	<td>${factura.valor}</td>
																            	<td>${factura.tipusFactura}</td>
																            	<td>${factura.idProveidor}</td>
																            	<td>${factura.notes}</td>	 				            	
																          	</tr>
																       	</c:forEach>
									                                </tbody>
									                            </table>
									                        </div>
									                        <c:if test="${canCreateFactura}">
										                        <div class="">	
										                    		<a href="registrarFactura?idInforme=${informePrevi.idInf}" class="btn btn-primary" role="button">Registrar factura</a>							                    				                       	
											                   	</div>
										                   	</c:if>																	
														</div>
											    	</div>
											    </c:if>	
										    	<c:if test="${canModificarActuacio and informePrevi.getDataAprovacio() == null and actuacio.isActiva()}">  
									            	<div class="panel-body">
								                    	<div class="">		                    	
									                    	<form class="form-horizontal" method="POST" action="DoCanvisActuacio">
									                    		<input class="hidden" name="idActuacio" value=${actuacio.referencia}>
									                    		<input class="hidden" name="idIncidencia" value=${incidencia.idIncidencia}>
									                    		<input class="hidden" name="idInforme" value=${informePrevi.idInf}>
									                    		<div class="form-group">
								                    				<div class="col-lg-6">
								                    					<input class="btn btn-success" type="submit" name="aprovarPA" value="Aprovar proposta d'actuació">
								                    				</div>																	               			 
									                    		</div>	                    		                       	
									                       	</form>		                       	
									                   	</div>
									            	</div>
									            </c:if>
								    		</div>
					      				</div>
					      			</c:forEach>
					      		</div>					      		  					          	
				    		</div>
				    	</div>
            		  </div>
					  				  
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#tasques">
					        Tasques</a>
					      </h4>
					    </div>
					    <div id="tasques" class="panel-collapse collapse">	
					      	<div class="panel-body">
					      		<c:if test="${actuacio.activa && canCreateTasca}">
						      		<div class="row margin_bottom10">
							    		<div class="col-lg-12 panel">
											<a href="createTasca?idActuacio=${actuacio.referencia}&tipus=generic" class="btn btn-primary" role="button">Nova tasca</a>
										</div>
						    		</div>
						    	</c:if>
					    		<div class="row panel-body">
									<div class="table-responsive">                        
			                            <table class="table table-striped table-bordered">
			                            	<thead>
			                                    <tr>                                        
			                                        <th>Tasca</th>
			                                        <th>Assumpte</th>
			                                        <th>id Actuació</th>
			                                        <th>id Incidència</th>
			                                        <th>Centre</th>                                        
			                                        <th>Data creació</th>
			                                        <th>Responsable</th>
			                                    </tr>
			                                </thead>
			                                <tbody>
			                                	<c:forEach items="${tasques}" var="tasca" >
										          	<tr class="${tasca.activa ? "success" : "danger"}">							          	
										           		<td><a href="tasca?id=${tasca.idTasca}">${tasca.idTasca} - ${tasca.descripcio}</a></td>
										            	<td>${tasca.primerComentari}</td>	
										            	<td><a href="actuacionsDetalls?ref=${tasca.actuacio.referencia}">${tasca.actuacio.referencia}</a></td>
										            	<td><a href="incidenciaDetalls?ref=${tasca.incidencia.idIncidencia}">${tasca.incidencia.idIncidencia}</a></td>
										            	<td>${tasca.actuacio.nomCentre}</td>							            	
										            	<td>${tasca.getDataCreacioString()}</td>
										            	<td>${tasca.usuari.getNomComplet()}					            	
										          	</tr>
										       	</c:forEach>
			                                </tbody>
			                            </table>
			                        </div>
			                	</div>
							</div>
					    </div>
					  </div>
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#notificacions">Notificacions</a>
					      </h4>
					    </div>
					    <div id="notificacions" class="panel-collapse collapse">	
					      	<div class="panel-body">
					      		<c:if test="${actuacio.activa && canCreateTasca}">
						      		<div class="row margin_bottom10">
							    		<div class="col-lg-12 panel">
											<a href="createTasca?idActuacio=${actuacio.referencia}&tipus=notificacio" class="btn btn-primary" role="button">Nova notificació</a>
										</div>
						    		</div>
						    	</c:if>
					    		<div class="row panel-body">
									<div class="table-responsive">                        
			                            <table class="table table-striped table-bordered">
			                            	<thead>
			                                    <tr>                                        
			                                        <th>Notificació</th>
			                                        <th>id Actuació</th>
			                                        <th>id Incidència</th>
			                                        <th>Centre</th>                                        
			                                        <th>Data creació</th>
			                                        <th>Notificat a</th>
			                                    </tr>
			                                </thead>
			                                <tbody>
			                                	<c:forEach items="${notificacions}" var="notificacio" >
										          	<tr class="${notificacio.activa ? notificacio.llegida ? "success" : "warning" : "danger"}">					          	
										           		<td><a href="tasca?id=${notificacio.idTasca}">${notificacio.idTasca} - ${notificacio.descripcio}</a></td>
										            	<td><a href="actuacionsDetalls?ref=${notificacio.actuacio.referencia}">${notificacio.actuacio.referencia}</a></td>
										            	<td><a href="incidenciaDetalls?ref=${notificacio.incidencia.idIncidencia}">${notificacio.incidencia.idIncidencia}</a></td>
										            	<td>${notificacio.actuacio.nomCentre}</td>							            	
										            	<td>${notificacio.getDataCreacioString()}</td>
										            	<td>${notificacio.usuari.getNomComplet()}					            	
										          	</tr>
										       	</c:forEach>
			                                </tbody>
			                            </table>
			                        </div>
			                	</div>
							</div>
					    </div>
					  </div>
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#entradesRegistre">Registre entrades</a>
					      </h4>
					    </div>
					    <div id="entradesRegistre" class="panel-collapse collapse">
					      	<div class="panel-body">
					      		<c:if test="${canCreateRegistre}">
						      		<div class="row margin_bottom10">
							    		<div class="col-lg-12 panel">
											<a href="novaEntrada?idIncidencia=${incidencia.idIncidencia}" class="btn btn-primary" role="button">Nova entrada</a>
										</div>
						    		</div>
						    	</c:if>
					    		<div class="row panel-body">					    		
									<div class="table-responsive">                        
				                            <table class="table table-striped table-bordered">
				                                <thead>
				                                    <tr>
				                                        <th>Referència</th>
				                                        <th>Data registre</th>
				                                        <th>Remitent</th>
				                                        <th>Contingut</th>
				                                    </tr>
				                                </thead>
				                                <tbody>
				                                	<c:forEach items="${entrades}" var="entrada" >
											          	<tr>							          	
											           		<td><a href="registre?tipus=E&referencia=${entrada.id}">${entrada.id}</a></td>
											            	<td>${entrada.getDataString()}</td>
											            	<td>${entrada.remDes}</td>
											            	<td>${entrada.contingut}</td>						            	
											          	</tr>
											       	</c:forEach>
				                                </tbody>
				                            </table>
				                        </div>
									</div>
								</div>
					    	</div>
					  </div>
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#sortidesRegistre">Registre sortides</a>
					      </h4>
					    </div>
					    <div id="sortidesRegistre" class="panel-collapse collapse">
					      	<div class="panel-body">
					      		<c:if test="${canCreateRegistre}">
						      		<div class="row margin_bottom10">
							    		<div class="col-lg-12 panel">
											<a href="novaSortida?idIncidencia=${incidencia.idIncidencia}" class="btn btn-primary" role="button">Nova sortida</a>
										</div>
						    		</div>
						    	</c:if>
					    		<div class="row panel-body">	
									<div class="table-responsive">                        
				                            <table class="table table-striped table-bordered">
				                                <thead>
				                                    <tr>
				                                        <th>Referència</th>
				                                        <th>Data registre</th>
				                                        <th>Destinatari</th>
				                                        <th>Contingut</th>
				                                    </tr>
				                                </thead>
				                                <tbody>
				                                	<c:forEach items="${sortides}" var="sortida" >
											          	<tr>							          	
											           		<td><a href="registre?tipus=S&referencia=${sortida.id}">${sortida.id}</a></td>
											            	<td>${sortida.getDataString()}</td>
											            	<td>${sortida.remDes}</td>
											            	<td>${sortida.contingut}</td>						            	
											          	</tr>
											       	</c:forEach>
				                                </tbody>
				                            </table>
				                        </div>
									</div>
								</div>
					    	</div>
					  </div>		
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#arxiusAdjunts">Arxius adjunts</a>
					      </h4>
					    </div>
					    <div id="arxiusAdjunts" class="panel-collapse collapse in">
					      	<div class="panel-body">					      		
					    		<div class="row panel-body">
									<c:forEach items="${actuacio.arxiusAdjunts}" var="arxiu" >
					            		<a target="_blanck" href="downloadFichero?ruta=${arxiu.ruta}">
											${arxiu.seccio} - ${arxiu.nom}
										</a>
										<br>
									</c:forEach>																		
								</div>
								<div class="row">            			
									<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadFichero">
										<div class="form-group">
											<label class="col-xs-2 control-label">Adjuntar arxius:</label>
				                            <div class="col-xs-5">   
				                                <input type="file" class="btn" name="file" /><br/>
											</div> 
											<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
											<input type="hidden" name="tipus" value="Actuacio">
											<input type="hidden" name="idTipus" value="${actuacio.referencia}">											
											<input type="hidden" name="redirect" value="/actuacionsDetalls?ref=${actuacio.referencia}">				    
											<div class="col-xs-2"> 
				         						<input type="submit" class="btn btn-primary" value="Pujar" />
				         					</div>    						
				         				</div>         				
									</form>							
				            	</div>     
							</div>
					    </div>
					  </div>
					  <div class="panel panel-default">
					    <div class="panel-heading">
					      <h4 class="panel-title">
					        <a data-toggle="collapse" data-parent="#accordion" href="#factures">Factures</a>
					      </h4>
					    </div>
					    <div id="factures" class="panel-collapse collapse">
					      	<div class="panel-body">					      		
					    		<div class="row panel-body">
					    			<div class="table-responsive">                        
			                            <table class="table table-striped table-bordered filerTable factures">
			                            	<thead>
			                                    <tr>                                        
			                                    	<th>Factura</th>
			                                        <th>Actuació</th>
			                                        <th>Data entrada</th>
			                                        <th>Data entrada</th>
			                                        <th>Data factura</th>
			                                        <th>Data factura</th>
			                                        <th>Import</th>
			                                        <th>Tipus</th>
			                                        <th>Proveïdor</th>
			                                        <th>notes</th>  
			                                    </tr>
			                                </thead>
			                                <tbody>
			                                	<c:forEach items="${factures}" var="factura" >
										          	<tr class="">							          	
										           		<td><a href="facturaDetalls?ref=${factura.idFactura}">${factura.idFactura}</a></td>
										            	<td>${factura.idActuacio}</td>
										            	<td>${factura.getDataEntradaString()}</td>
										            	<td>${factura.dataEntrada}</td>
										            	<td>${factura.getDataFacturaString()}</td>
										            	<td>${factura.dataFactura}</td>
										            	<td>${factura.valor}</td>
										            	<td>${factura.tipusFactura}</td>
										            	<td>${factura.idProveidor}</td>
										            	<td>${factura.notes}</td>	 				            	
										          	</tr>
										       	</c:forEach>
			                                </tbody>
			                            </table>
			                        </div>																	
								</div>
							</div>
					    </div>
					  </div>
					</div>
            	</div>       
				</c:if>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/actuacio/detalls.js"></script>
    <!-- /#wrapper -->
</body>
</html>