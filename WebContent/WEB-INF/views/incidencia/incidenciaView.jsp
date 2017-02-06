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
                            Incidència <small>Detalls incidència</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Incidència
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<c:if test="${not empty incidencia}">
				<div class="row">
	                <div class="col-lg-12">
	                    <div class="panel panel-${incidencia.activa ? "success" : "danger"}">
	                        <div class="panel-heading">
	                           	<div class="row">
	                        		<div class="col-lg-6">
	                        			 Identificador incidència: ${incidencia.idIncidencia}
	                        		</div>
	                        	</div>
	                        </div>
	                        <div class="panel-body">
	                            <p>${incidencia.nom}</p>
	                        </div>
	                        <div class="panel-footer">
	                        	<div class="row">
	                        		<div class="col-lg-4">
	                        			Data Petició: ${incidencia.getPeticioString()}
	                        		</div>
	                        		<div class="col-lg-4">
	                        			<c:if test="${!incidencia.activa}">
	                        				Data Tancament: ${actuacio.getTancamentString()}
	                        			</c:if>
	                        		</div>
	                        	</div>
	                        </div>
	                    </div>
	                </div>
            	</div>
            	<div class="row">
            		<div class="col-lg-12 panel-group" id="accordion">
            			<div class="panel panel-default">
						    <div class="panel-heading">
						      <h4 class="panel-title">
						        <a data-toggle="collapse" data-parent="#accordion" href="#actuacions">Actuacions</a>
						      </h4>
						    </div>
						    <div id="actuacions" class="panel-collapse collapse">
						      	<div class="panel-body">
						      		<c:if test="${incidencia.activa}">
							      		<div class="row margin_bottom10">
								    		<div class="col-lg-12 panel">
												<a href="createActuacio?idIncidencia=${incidencia.idIncidencia}" class="btn btn-primary" role="button">Nova actuacio</a>
											</div>
							    		</div>
							    	</c:if>
						    		<div class="row panel-body">
										<div class="table-responsive">                        
				                            <table class="table table-striped table-bordered">
				                                <thead>
				                                    <tr>
				                                        <th>Referència</th>
				                                        <th>Centre</th>
				                                        <th>Descripció</th>
				                                        <th>Data petició</th>			                                        
				                                        <th>data modificació</th>
				                                        <th>modificació</th>
				                                    </tr>
				                                </thead>
				                                <tbody>
				                                	<c:forEach items="${incidencia.getLlistaActuacions()}" var="actuacio" >
											          	<tr class=${actuacio.activa ? actuacio.aprovada? "success" : "warning" : "danger"}>							          	
											           		<td><a href="actuacionsDetalls?ref=${actuacio.referencia}">${actuacio.referencia}</a></td>	
											           		<td>${actuacio.nomCentre}</td>
											           		<td>${actuacio.descripcio}</td>	
											           		<td>${actuacio.getDataCreacioString()}</td>					            	
											            	<td>${actuacio.getDarreraModificacioString()}</td>	
											            	<td>${actuacio.modificacio}					            	
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
					      		<div class="row margin_bottom10">
						    		<div class="col-lg-12 panel">
										<a href="novaEntrada?ref=${incidencia.idIncidencia}" class="btn btn-primary" role="button">Nova entrada</a>
									</div>
					    		</div>
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
					      		<div class="row margin_bottom10">
						    		<div class="col-lg-12 panel">
										<a href="novaSortida?ref=${incidencia.idIncidencia}" class="btn btn-primary" role="button">Nova sortida</a>
									</div>
					    		</div>
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
											           		<td>${sortida.id}</td>
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
					        <a data-toggle="collapse" data-parent="#accordion" href="#tasques">
					        Tasques</a>
					      </h4>
					    </div>
					    <div id="tasques" class="panel-collapse collapse">	
					      	<div class="panel-body">
					      		<c:if test="${incidencia.activa}">
						      		<div class="row margin_bottom10">
							    		<div class="col-lg-12 panel">
											<a href="createTasca?idIncidencia=${incidencia.idIncidencia}&tipus=generic" class="btn btn-primary" role="button">Nova tasca</a>
										</div>
						    		</div>
						    	</c:if>
					    		<div class="row panel-body">
									<div class="table-responsive">                        
			                            <table class="table table-striped table-bordered">
			                                <thead>
			                                    <tr>
			                                        <th>id Tasca</th>
			                                        <th>Tasca</th>
			                                        <th>Usuari</th>
			                                    </tr>
			                                </thead>
			                                <tbody>
			                                	<c:forEach items="${tasques}" var="tasca" >
										          	<tr class=${tasca.activa ? "success" : "danger"}>							          	
										           		<td><a href="tasca?id=${tasca.idTasca}">${tasca.idTasca}</a></td>	
										           		<td>${tasca.name}</td>						            	
										            	<td>${tasca.usuari.getNomComplet()}</td>						            	
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