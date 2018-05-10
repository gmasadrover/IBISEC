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
                    <div class="col-md-12">
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
	                <div class="col-md-12">
	                    <div class="panel panel-${actuacio.isActiva() ? actuacio.isPaAprovada() ? actuacio.isAprovada() ? "success" : "info" : "warning" : "danger"}">
	                        <div class="panel-heading">
	                           	<div class="row">
	                        		<div class="col-md-6">
	                        			 ${actuacio.referencia} - ${actuacio.centre.getNomComplet()}
	                        		</div>
	                        		<%-- <div class="col-md-6">
	                        			Darrera modificació: ${actuacio.modificacio} - ${actuacio.getDarreraModificacioString()}
	                        		</div> --%>
	                        	</div>
	                        </div>
	                        <div class="panel-body">
	                            <p>${actuacio.descripcio}</p>
	                            <br />
	                            <p>${actuacio.notes}</p>
	                        </div>
	                    </div>
	                </div>
            	</div> 
            	<div class="row">
            		<div class="col-md-offset-10 col-md-2">	
            			<c:if test="${actuacio.isActiva() && canModificarActuacio}">
            				<a href="editActuacio?idActuacio=${actuacio.referencia}" class="btn btn-primary" role="button">Modificar actuació</a>		        			
		        		</c:if>
		        	</div>
            	</div>
            	<div class="row">
	                <div class="col-md-offset-10 col-md-2">	
	                	<div class="checkbox">
	                        <label>
	                          	<input id="seguimentActuacio" data-idactuacio="${actuacio.referencia}" data-idusuari="${idUsuariLogg}" data-seguir="${!actuacio.seguiment}" type="checkbox" ${actuacio.seguiment ? 'checked' : ''}> Seguir Actuació
	                        </label>
	                	</div> 
	                </div>
	            </div>
            	<div class="row">
            		<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoCanvisActuacio">
                		<input class="hidden" name="idActuacio" value=${actuacio.referencia}>
                		<input class="hidden" name="idIncidencia" value=${incidencia.idIncidencia}>
                		<input class="hidden" name="idInforme" value="-1">
                		<div class="form-group">
               				<div class="col-md-5">
               					
               				</div>
		        			<div class="col-md-4">
				        		<c:if test="${actuacio.isActiva() && canModificarActuacio}">
				        			<input class="btn btn-danger" data-toggle="modal" data-target="#myModal" name="tancar" value="Tancar actuació">
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
									        		<textarea name="motiu" required></textarea>
									      		</div>
									      		<div class="modal-footer">
									        		<input class="btn btn-danger" type="submit" name="tancar" value="Tancar actuació">
									      		</div>
								    		</div>																	
									  	</div>
									</div> 
				        		</c:if>
				        		<c:if test="${!actuacio.isActiva() && canModificarActuacio}">
				        			<input class="btn btn-success" type="submit" name="obrir" value="Obrir actuació">
				        		</c:if>
				        	</div>
				        	<div class="col-md-2"> 
					        	<c:if test="${actuacio.activa && canCreateInformePrevi}">					      			
						      		<a href="createTasca?idActuacio=${actuacio.referencia}&tipus=infPrev" class="btn btn-primary right" role="button">Sol·licitar informe</a>
								</c:if> 
							</div>   							                 			 
                 		</div>	                    		                       	
                	</form>		  
            	</div>
            	<div class="row">
            		<div class="col-md-12 panel-group" id="accordion">
        				<c:if test="${informes.size() > 0 || canModificarActuacio}">
            		  		<div class="panel panel-default">
            		  			<div class="panel-heading">
					      			<h4 class="panel-title">
							        	<a data-toggle="collapse" data-parent="#accordion" href="#dadesTecniques">Expedient</a>
							      	</h4>
					    		</div>
					    		<div id="dadesTecniques" class="panel-collapse collapse in">					    	
					      			<div class="panel-body">
					      				<h4>Expedients</h4>					      		
					      				<div class="col-md-12 panel-group" id="accordionInformes">					      			
							      			<c:forEach items="${informes}" var="informePrevi" >	
							      				<c:set var="informePrevi" value="${informePrevi}" scope="request"/>				      				
							      				<jsp:include page="../expedients/include/_expedientView.jsp"></jsp:include>
							      			</c:forEach>
					      				</div>	
							      		<c:if test="${canModificarActuacio}">					      		
							      			<a target="_blanck" class="btn btn-primary" href="newInforme?idActuacio=${actuacio.referencia}">Obrir nou Expedient</a>	
							      		</c:if>			      		  					          	
				    				</div>
				    			</div>
            		  		</div>  
            		  	</c:if>   
           				<div class="panel panel-default">
						    <div class="panel-heading">
						      	<h4 class="panel-title">
						        	<a data-toggle="collapse" data-parent="#accordion" href="#feines">Feines</a>
						      	</h4>
						    </div>					    
						    <div id="feines" class="panel-collapse collapse">	
						    	<div class="panel-body">
									<c:if test="${canCreateFeina}">
								  		<div class="row margin_bottom10">
								 			<div class="col-md-12 panel">
												<a href="createFeina?idActuacio=${actuacio.referencia}" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova feina</a>
											</div>
										</div>
								   	</c:if>
									<div class="row panel-body">
								      	<jsp:include page="../feina/include/_resumFeina.jsp"></jsp:include>
								    </div>
								 </div>
							</div>
					  	</div> 		 
           				<div class="panel panel-default">
						    <div class="panel-heading">
								<h4 class="panel-title">
							    	<a data-toggle="collapse" data-parent="#accordion" href="#tasques">Tasques</a>
							  	</h4>
						    </div>
						    <div id="tasques" class="panel-collapse collapse">	
						      	<div class="panel-body">
						      		<c:if test="${canCreateTasca}">
							      		<div class="row margin_bottom10">
								    		<div class="col-md-12 panel">
												<a href="createTasca?idActuacio=${actuacio.referencia}&tipus=generic" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova tasca</a>
											</div>
							    		</div>
							    	</c:if>
						    		<div class="row panel-body">
										<c:set var="tasquesList" value="${tasques}" scope="request"/>
										<c:set var="idInfActual" value="-1" scope="request"/>
										<jsp:include page="../tasca/include/_tasquesListResum.jsp"></jsp:include>
				                	</div>
								</div>
						    </div>
					  	</div>	
          					<div class="panel panel-default">
           				  	<div class="panel-heading">
								<h4 class="panel-title">
							    	<a data-toggle="collapse" data-parent="#accordion" href="#registre">Registres</a>
							  	</h4>
						    </div>
						    <div id="registre" class="panel-collapse collapse">
						    	<div class="tabbable">
					               	<ul class="nav nav-tabs">
					               		<li class="active"><a data-toggle="tab" href="#entradesRegistre">Registre entrades</a></li>
					               		<li><a data-toggle="tab" href="#sortidesRegistre">Registre sortides</a></li>
					 				</ul>
								  	<div class="tab-content">								  		
								  		<div id="entradesRegistre" class="tab-pane fade in active">
								  		 	<div class="col-md-12 bordertab">
								  		 		<c:if test="${canCreateRegistre}">
										      		<div class="row margin_bottom10">
											    		<div class="col-md-12">
															<a href="novaEntrada?idIncidencia=${incidencia.idIncidencia}&idInf=${incidencia.idIncidencia}" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova entrada</a>
														</div>
										    		</div>
										    	</c:if>
									    		<div class="row panel-body">					    		
													<c:set var="registres" value="${entrades}" scope="request"/>
													<c:set var="tipus" value="entrada" scope="request"/>
			                    					<jsp:include page="../registre/include/_registresList.jsp"></jsp:include>
												</div>
										    </div>
								  	 	</div>
								  		<div id="sortidesRegistre" class="tab-pane fade">
								  		 	<div class="col-md-12 bordertab">
								  		 		<c:if test="${canCreateRegistre}">
										      		<div class="row margin_bottom10">
											    		<div class="col-md-12">
															<a href="novaSortida?idIncidencia=${incidencia.idIncidencia}" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova sortida</a>
														</div>
										    		</div>
										    	</c:if>
									    		<div class="row panel-body">	
													<c:set var="registres" value="${sortides}" scope="request"/>
													<c:set var="tipus" value="sortida" scope="request"/>
			                    					<jsp:include page="../registre/include/_registresList.jsp"></jsp:include>
												</div>
										    </div>
								  		</div>
					  				</div>
								</div>
				    		</div>
			    		</div>	
			    		<c:if test="${informes.size() == 0}">
	           				<div class="panel panel-default">
							    <div class="panel-heading">
							      <h4 class="panel-title">
							        <a data-toggle="collapse" data-parent="#accordion" href="#arxiusAdjunts">Arxius adjunts</a>
							      </h4>
							    </div>
							    <div id="arxiusAdjunts" class="panel-collapse collapse">
							      	<div class="panel-body">					      		
							    		<div class="row panel-body">
								    		<jsp:include page="include/_resumFitxers.jsp"></jsp:include>																
										</div>
										<div class="row">            			
											<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadFichero">
												<div class="form-group">
													<label class="col-xs-2 control-label">Adjuntar arxius:</label>
						                            <div class="col-xs-5">   
						                                <input type="file" class="btn" name="file" multiple/><br/>
													</div> 											
													<input type="hidden" name="idIncidencies" value="${incidencia.idIncidencia}">
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
						</c:if>
          			</div>     		
            	</div>       
				</c:if>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/actuacio/detalls.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/expedient/detalls.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/feina/detalls.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/tasca/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/registre/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>