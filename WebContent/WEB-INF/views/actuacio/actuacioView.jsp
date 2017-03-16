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
	                    <div class="panel panel-${actuacio.activa ? actuacio.aprovada ? "success" : "warning" : "danger"}">
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
	                        </div>
	                        <div class="panel-footer">
	                        	<div class="row">
	                        		<div class="col-lg-4">
	                        			Data creació: ${actuacio.getDataCreacioString()}
	                        		</div>
	                        		<div class="col-lg-4">
	                        			<c:if test="${actuacio.aprovada}">
	                        				Data Aprovació: ${actuacio.getDataAprovacioString()}
	                        			</c:if>
	                        		</div>
	                        		<div class="col-lg-4">
	                        			<c:if test="${!actuacio.activa}">
	                        				Data Tancament: ${actuacio.getDataTancamentString()}
	                        			</c:if>
	                        		</div>
	                        	</div>
	                        </div>
	                    </div>
	                </div>
            	</div> 
            	<c:if test="${canModificarActuacio}">  
	            	<div class="row">
	           			<div class="col-lg-12 panel ">
	                    	<div class="">		                    	
		                    	<form class="form-horizontal" method="POST" action="DoCanvisActuacio">
		                    		<input class="hidden" name="idActuacio" value=${actuacio.referencia}>
		                    		<input class="hidden" name="idIncidencia" value=${incidencia.idIncidencia}>
		                    		<div class="form-group">
	                    				<div class="col-lg-6">
	                    					<c:if test="${!actuacio.aprovada and actuacio.activa}">
	                    						<input class="btn btn-success" type="submit" name="aprovar" value="Aprovar">
	                    					</c:if>
	                    				</div>
							        	<div class="col-lg-6">
							        		<c:if test="${actuacio.activa}">
							        			<input class="btn btn-danger" type="submit" name="tancar" value="Tancar">
							        		</c:if>
							        	</div>	                    			 
		                    		</div>	                    		                       	
		                       	</form>		                       	
		                   	</div>
		                </div>
	            	</div>
	            </c:if>
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
					      		<h4>Proposta d'actuació</h4>					      		
					      		<div class="col-lg-12 panel-group" id="accordionInformes">
					      			<c:forEach items="${informes}" var="informePrevi" >					      				
					      				<div class="panel panel-default">
						      				<div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordionInformes" href="#informe${informePrevi.idInf}">Proposta d'actuació ${informePrevi.idInf} - ${informePrevi.getDataCreacioString()} ${informePrevi.partida == "" ? "Pendent de reserva de partida" : "Partida reservada" }</a>
										      </h4>
										    </div>
										    <div id="informe${informePrevi.idInf}" class="panel-collapse collapse">
										    	<c:set var="informePrevi" scope="request" value="${informePrevi}"></c:set>
										      	<jsp:include page="../tasca/include/_resumInformePrevi.jsp"></jsp:include>
										      	<div class="panel-body">
								    				<c:if test="${informePrevi.llistaOfertes.size()>0}">
								    					<c:set var="ofertes" scope="request" value="${informePrevi.llistaOfertes}"></c:set>
								    					<c:set var="ofertaSeleccionada" scope="request" value="${informePrevi.ofertaSeleccionada}"></c:set>								    					
										    			<jsp:include page="../tasca/include/_resumOfertes.jsp"></jsp:include>
										            	<c:if test="${actuacio.activa && canModificarActuacio}">				      			
												      		<div class="row margin_bottom10">
													    		<div class="col-lg-12 panel">
																	<a href="CrearDocument?tipus=autMen&idIncidencia=${incidencia.idIncidencia}&idActuacio=${actuacio.referencia}" class="btn btn-primary right" role="button">Generar autorització actuació</a>
																</div>
												    		</div>
										    			</c:if>	
										    		</c:if>	
										    	</div>	
								    		</div>
					      				</div>
					      			</c:forEach>
					      		</div>
					      		<c:if test="${actuacio.activa && canCreateInformePrevi}">					      			
						      		<div class="row margin_bottom10">
							    		<div class="col-lg-12 panel">
											<a href="createTasca?idActuacio=${actuacio.referencia}&tipus=infPrev" class="btn btn-primary right" role="button">Sol·licitar proposta d'actuació ${informes.size() > 0 ? "nova" : ""}</a>
										</div>
						    		</div>
					    		</c:if>      					          	
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
					        <a data-toggle="collapse" data-parent="#accordion" href="#arxiusAdjunts">Arxius adjunts</a>
					      </h4>
					    </div>
					    <div id="arxiusAdjunts" class="panel-collapse collapse">
					      	<div class="panel-body">					      		
					    		<div class="row panel-body">
					    			<input id="obrirPD" class="btn btn-default" value="Carpteta Actuacio" data-idactuacio="${actuacio.referencia}" data-idincidencia="${incidencia.idIncidencia}">						    		
									<br>
									<c:forEach items="${arxius}" var="arxiu" >
					            		<a  href="downloadFichero?ruta=${arxiu.ruta}">
											${arxiu.seccio} - ${arxiu.nom}
										</a>
										<br>
									</c:forEach>																		
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