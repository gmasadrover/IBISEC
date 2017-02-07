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
	                        			 Estat: ${actuacio.activa ? actuacio.aprovada ? "Aprovada" : "Pendent aprovació" : "Tantada"} 	                        			 
	                        			 / ${informes.size() > 0 ? ofertes.size() > 0 ? "Amb proposta tècnica" : "Informe previ realitzat" : "Pendent informe previ" } 
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
            	<div class="row">
           			<div class="col-lg-12 panel ">
                    	<div class="">		                    	
	                    	<form class="form-horizontal" method="POST" action="DoCanvisActuacio">
	                    		<input class="hidden" name="idActuacio" value=${actuacio.referencia}>
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
					      		<h4>Informes previs</h4>					      		
					      		<div class="col-lg-12 panel-group" id="accordionInformes">
					      			<c:forEach items="${informes}" var="informe" >
					      				<div class="panel panel-default">
						      				<div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordionInformes" href="#informe${informe.idInf}">Informe previ ${informe.idInf} - ${informe.getDataCreacioString()} ${informe.partida == "" ? "Pendent de reserva de partida" : "Partida reservada" }</a>
										      </h4>
										    </div>
										    <div id="informe${informe.idInf}" class="panel-collapse collapse">
										      	<div class="panel-body">
										      		<p>
										      			<label>Tècnic:</label> ${informe.usuari.getNomComplet()}
										      		</p>
										      		<p>
										      			<label>Servei:</label> <m:message key="${informe.servei}"/>
										      		</p>
										      		<p>			                     				
					                         			<label>Objecte:</label> ${informe.objecte}
					                         		</p>	
					                         		<p>			                     				
					                         			<label>Comentari tècnic:</label> ${informe.comentari}
					                         		</p>
									            	<div class="row">
									            		<div class="col-lg-4">
									            			<label>Tipus de contracte:</label> <m:message key="${informe.tipusObra}"/>
									            		</div>
									            		<c:if test="${informe.tipusObra == 'obr'}">
										            		<div class="col-lg-4">
										            			<label>Requereix llicència:</label> ${informe.llicencia ? "Si" : "No"}
										            		</div>
										            		<c:if test="${informe.llicencia}">
											            		<div class="col-lg-4">
											            			<label>Tipus llicència:</label> ${informe.tipusLlicencia}
											            		</div>
										            		</c:if>
									            		</c:if>
									            	</div>
									            	<p></p>
									            	<p>
									            		<label>Requereix formalització contracte:</label> ${informe.contracte ? "Si" : "No"}
									            	</p>
									            	<p>
									            		<label>Termini d'execució:</label> ${informe.termini}
									            	</p>	
					                         		<div class="row">
									            		<div class="col-lg-4">
							                                <label>VEC:</label> ${informe.vec}€						                                
						                                </div>
						                                <div class="col-lg-4"> 
						                                	<label>IVA:</label> ${informe.iva}€
						                                </div>
						                                <div class="col-lg-4">
						                                	<label>Plic:</label> ${informe.plic}€
						                                </div>					  
									            	</div>	
									            	<p></p>
									            	<p>
									            		<label>Partida:</label> ${informe.partida}
									            	</p>	
									            	<p></p>
									            	<p>
									            		<label>Arxius ajunts:</label>
									            	</p>	
									            	<div class="row col-lg-12">
										            	<c:forEach items="${informe.adjunts}" var="arxiu" >
										            		<a  href="downloadFichero?ruta=${arxiu.ruta}">
																${arxiu.nom}
															</a>
															<br>
														</c:forEach>					            		
									            	</div>	
								    			</div>
								    		</div>
					      				</div>
					      			</c:forEach>
					      		</div>
					      		<c:if test="${actuacio.activa}">					      			
						      		<div class="row margin_bottom10">
							    		<div class="col-lg-12 panel">
											<a href="createTasca?idActuacio=${actuacio.referencia}&tipus=infPrev" class="btn btn-primary right" role="button">Sol·licitar informe previ ${informes.size() > 0 ? "nou" : ""}</a>
										</div>
						    		</div>
					    		</c:if>
					    		<c:if test="${ofertes.size()>0}">
					    			<h4>Proposta tècnica</h4>
					    			<div class="table-responsive">                        
			                            <table class="table table-striped table-bordered">
			                                <thead>
			                                    <tr>
			                                        <th>Oferta</th>
			                                        <th>Licitador</th>			                                        
			                                    </tr>
			                                </thead>
			                                <tbody>
			                                	<c:forEach items="${ofertes}" var="oferta" >
										          	<tr ${oferta.seleccionada ? "class='success'" : ""}>	
										          		<td>${oferta.getPlicFormat()}</td>							          	
										           		<td><a href='editEmpresa?cif=${oferta.cifEmpresa}'>${oferta.nomEmpresa} (${oferta.cifEmpresa})</a></td>
										            </tr>
										       	</c:forEach>
			                                </tbody>
			                            </table>
			                        </div>
			                        <p>
					            		<label>L'empresa adjudicataria:</label> ${ofertaSeleccionada.nomEmpresa} (${ofertaSeleccionada.cifEmpresa})
					            	</p>
					            	<p>
					            		<label>Amb valor:</label> ${ofertaSeleccionada.getPlicFormat()}
					            	</p>
					            	<p>
					            		<label>Termini:</label> ${ofertaSeleccionada.termini}
					            	</p>
					            	<p>
					            		<label>Proposta tècnica:</label> ${ofertaSeleccionada.comentari}
					            	</p>
					            	<c:if test="${actuacio.activa}">					      			
							      		<div class="row margin_bottom10">
								    		<div class="col-lg-12 panel">
												<a href="CrearDocument?tipus=autMen&idActuacio=${actuacio.referencia}" class="btn btn-primary right" role="button">Generar autorització actuació</a>
											</div>
							    		</div>
					    			</c:if>	
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
					      		<c:if test="${actuacio.activa}">
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
										           		<td><a href="tasca?id=${tasca.idTasca}">${tasca.idTasca} - ${tasca.name}</a></td>
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
					</div>
            	</div>       
				</c:if>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/usuari/usuari.js"></script>
    <!-- /#wrapper -->
</body>
</html>