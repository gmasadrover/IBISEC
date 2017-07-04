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
                        	<c:choose>
							    <c:when test="${tasca.tipus=='notificacio'}">
							    	Notificació
							    </c:when>
							    <c:otherwise>
							       	Tasca
							    </c:otherwise>
							</c:choose>
                            <small>
								<c:choose>
								    <c:when test="${tasca.tipus=='infPrev'}">
								        Sol·licitud proposta d'actuació
								    </c:when>
								    <c:when test="${tasca.tipus=='notificacio'}">
								    	Notificació
								    </c:when>
								    <c:otherwise>
								       	Detalls tasca
								    </c:otherwise>
								</c:choose>
							</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i>
                                <c:choose>
								    <c:when test="${tasca.tipus=='notificacio'}">
								    	Notificació
								    </c:when>
								    <c:otherwise>
								       	Tasca
								    </c:otherwise>
								</c:choose> 
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <c:choose>
				    <c:when test="${tasca.tipus=='notificacio'}">
				    	<h2>Notificació</h2>
				    </c:when>
				    <c:otherwise>
				       	<h2>Tasca</h2>
				    </c:otherwise>
				</c:choose>                 
                <div class="row">					
	                <div class="col-md-12">	   
						<div class="panel panel-${tasca.activa ? "success" : "danger"}">
						   	<div class="panel-heading">
						    	<div class="row">
						       		<div class="col-md-4">
						       			<c:choose>
										    <c:when test="${tasca.tipus=='notificacio'}">
										    	Referència notificació: ${tasca.idTasca}
										    </c:when>
										    <c:otherwise>
										       	Referència Tasca: ${tasca.idTasca}
										    </c:otherwise>
										</c:choose> 						       			
						       		</div>
						       		<div class="col-md-4">
						       			Assumpte: ${tasca.descripcio}
						      		</div>
						       		<div class="col-md-4">
						       			Responsable: ${tasca.usuari.getNomComplet()}
						      		</div>
						       	</div>
						   	</div>
						</div>
					</div>
				</div>
				<div class="row">					
	                <div class="col-md-offset-8 col-md-2">	
	                	<div class="checkbox">
	                        <label>
	                          	<input id="seguiment" data-idtasca="${tasca.idTasca}" data-idusuari="${idUsuariLogg}" data-seguir="${!tasca.seguiment}" type="checkbox" ${tasca.seguiment ? 'checked' : ''}> Seguir tasca
	                        </label>
	                	</div> 
	                </div>
	                <div class="col-md-2">	
	                	<div class="checkbox">
	                        <label>
	                          	<input id="seguimentActuacio" data-idactuacio="${actuacio.referencia}" data-idusuari="${idUsuariLogg}" data-seguir="${!actuacio.seguiment}" type="checkbox" ${actuacio.seguiment ? 'checked' : ''}> Seguir Actuació
	                        </label>
	                	</div> 
	                </div>
	            </div>
				<c:choose>
					<c:when test="${actuacio != null}">
						<div class="row">					
			                <div class="col-md-12">	                	
			                	<jsp:include page="../actuacio/include/_resumActuacio.jsp"></jsp:include>
			                </div>				            	
						</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${incidencia != null}">							
								<div class="row">					
					                <div class="col-md-12">	                	
					                	<jsp:include page="../incidencia/include/_resumIncidencia.jsp"></jsp:include>
					                </div>				            	
								</div>
							</c:when>
							<c:otherwise>
								
							</c:otherwise>
						</c:choose>						
					</c:otherwise>
				</c:choose>
                <div class="row">
                    <div class="col-md-12"> 
                        <c:forEach items="${historial}" var="historic" >                        	
                        	<div class="panel panel-info"> 
	                         	<div class="panel-heading">
	                         		<div class="col-md-10">${historic.usuari.getNomComplet()}</div>
	                         		<div class="">${historic.getDataString()}</div>
	                         	</div>
	                         	<div class="panel-body">
	                         		<div class="row panel-body">${historic.comentari}</div>		
					            	<div class="row panel-body">
						            	<c:forEach items="${historic.adjunts}" var="arxiu" >
						            		<a target="_blanck" href="downloadFichero?ruta=${arxiu.ruta}">${arxiu.nom}</a><br>
										</c:forEach>					            		
					            	</div>			          		
					          	</div>					          	
				    		</div>
	       				</c:forEach>                        
                        <c:if test="${tasca.activa && tasca.tipus != 'notificacio'}">		  
                        	<div class="panel panel-info">
                        		<c:if test="${tasca.tipus != 'generic' && canRealitzarTasca}">
	                        		<div class="panel-body">			                        		
											      <h4 class="panel-title">
											      	<c:choose>
													    <c:when test="${tasca.tipus=='infPrev'}">Informe</c:when>
													</c:choose>
											      </h4>
											    </div>										    
											    <div>
					                        		<c:choose>
													    <c:when test="${tasca.tipus=='infPrev'}">
													    	<c:set var="numPA" value="1" scope="request" />
													    	<div class="panel-body">
																<form class="form-horizontal" target="_blank" method="POST" enctype="multipart/form-data" action="DoAddInforme" onsubmit="setTimeout(function () { window.location.reload(); }, 30)">
																	<input type="hidden" id="infPrev" name=infPrev value="${propostesInformeList.size()}">
																	<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
																	<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
																	<input type="hidden" name="idTasca" value="${tasca.idTasca}">
																	<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${informePrevi.idInf}">	
																	<div class="form-group">
																			<div class="col-md-12">
																				<div class="row">
																	        		<div class="col-md-12">	
																	                	<p>Arxius adjunts:</p>
																	                  	<c:forEach items="${informePrevi.adjunts}" var="arxiu" >
																		            		<a target="_blanck" href="downloadFichero?ruta=${arxiu.ruta}">
																								${arxiu.nom}
																							</a>
																							<a href="#"><span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
																							<br>
																						</c:forEach>
																					</div>
																	            </div>	  
																	        	<div class="row margin_top10">
																	    			<div class="col-md-12">
																	           			<input type="file" class="btn" name="informe" /><br/>																 		
																	    			</div>
																	    		</div>
																	    	</div>
																		</div>
																	<div class="col-md-12 panel-group" id="accordion">																		
																    	<c:forEach items="${propostesInformeList}" var="propostaActuacio" >  
																    		<c:set var="propostaActuacio" value="${propostaActuacio}" scope="request"/> 	
																	    	<jsp:include page="include/_crearInformePrevi.jsp"></jsp:include>
																	    	<c:set var="numPA" value="${numPA + 1}" scope="request"/>
																	    </c:forEach>
																	</div>	
																	<c:if test="${informePrevi.propostaActuacio.ruta != null}">
																		<div class="col-md-12">	
																			<p>
																				<div class="document">
																               		<label>Vistiplau proposta d'actuació signada:</label>													               
																					<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaActuacio.ruta}">
																						${informePrevi.propostaActuacio.nom}
																					</a>
																					<c:if test="${informePrevi.propostaActuacio.signat}">
																						<span class="glyphicon glyphicon-pencil signedFile"></span>
																					</c:if>
				<%-- 																	<span data-ruta="${informePrevi.propostaActuacio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span> --%>
																					<br>
																					<div class="infoSign hidden">
																						<c:forEach items="${informePrevi.propostaActuacio.firmesList}" var="firma" >
																							<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
																							<br>
																						</c:forEach>
																					</div>
																				</div>																	
																			</p>																
																		</div>
																	</c:if>		
																	<c:if test="${informePrevi.propostaActuacio.ruta != null}">	
																	<div class="form-group">
																		<div class="col-md-12">	
																			<div class="row">	 
																				<div class="col-md-12">						                    						
																          			<textarea class="form-control" name="comentariCap" placeholder="comentari cap" rows="3">${informePrevi.comentariCap}</textarea> 
																            	</div>
																        	</div>
															        	</div>		
															        </div>	
															        </c:if>										
																	<div class="form-group">
																		<div class="col-md-12">
																			<div class="col-md-9">	
																				<c:if test="${informePrevi.propostaActuacio.ruta == null}">																					
																		    		<div class="row">
																		        		<div class="col-md-12">
																		              		<input class="btn btn-primary" id="novaProposta" value="Nova proposta">
																						</div>
																		     		</div>
																	     		</c:if>
																	 		</div>	
																	 		<c:choose>
																	 			<c:when test="${informePrevi.propostaActuacio.ruta == null}">
																	 				<div class="col-md-3">
																			    		<div class="row">
																			        		<div class="col-md-12">
																			        			<input class="btn btn-success" type="submit" name="guardar" value="Generar proposta">
																						 	</div>
																			     		</div>																	     		
																			 		</div>
																	 			</c:when> 
																	 			<c:when test="${informePrevi.propostaActuacio.ruta != null}">
																	 				<div class="col-md-3">
																			    		<div class="row">
																			        		<div class="col-md-12">
																			        			<input class="btn btn-success" type="submit" name="vistiplau" value="Generar vistiplau proposta">
																						 	</div>
																			     		</div>																	     		
																			 		</div>
																	 			</c:when>
																	 		</c:choose>
																 		</div>															        		
																	</div>																	
																</form>
															</div>
															<c:if test="${propostesInformeList.size() > 0 && informePrevi.propostaActuacio.ruta == null}">
																<div class="separator"></div>
																<div class="panel-body">
																	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA">
																		<input type="hidden" name="document" value="paTecnic">
																		<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
																		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
																		<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
																		<input type="hidden" name="idTasca" value="${tasca.idTasca}">	
																		<div class="col-md-8">
																			<div class="row margin_top10">
																    			<div class="col-md-12">
																           			Pujar proposta d'actuació signada: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
																    			</div>
																    		</div>																													        			
														        		</div>	
														        		<div class="col-md-4">												        		
																    		<div class="row">
																        		<div class="col-md-12">															        																						 				
																			 		<input class="btn btn-success margin_top30 upload" type="submit" name="guardar" value="Enviar proposta actuació signada">
																			 	</div>
																     		</div>
															     		</div>
													        		</form>
													        	</div>
												        	</c:if>
												        	<div class="separator"></div>												        	
												        	<c:if test="${informePrevi.propostaActuacio.ruta != null && esCap}">
												        	<div class="panel-body">
												        		<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA">
												        			<input type="hidden" name="document" value="autoritzacioCap">
																	<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
																	<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
																	<input type="hidden" name="idTasca" value="${tasca.idTasca}">
																	<input type="hidden" name="idInforme" value="${informePrevi.idInf}">																	
														        	<c:if test="${informePrevi.vistiplauPropostaActuacio.ruta != null}">
																		<div class="col-md-12">	
														                	<p>Vistiplau proposta d'actuació signada:</p>													                  	
														            		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.vistiplauPropostaActuacio.ruta}">
																				${informePrevi.vistiplauPropostaActuacio.nom}
																			</a>																			
																		</div>
																	</c:if>																	
																	<div class="col-md-8">
																		<div class="row margin_top10">
															    			<div class="col-md-12">
															           			Pujar Vistiplau proposta d'actuació signada: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
															    			</div>
															    		</div>																													        			
													        		</div>	
													        		<div class="col-md-4">												        		
															    		<div class="row">
															        		<div class="col-md-12">															        																						 				
																		 		<input class="btn btn-success margin_top30 upload" type="submit" name="guardar" value="Enviar vistiplau signat">
																		 	</div>
															     		</div>
														     		</div>
												        		</form>	
												        	</div>															
													       	</c:if>
													    </c:when>
													    <c:when test="${tasca.tipus=='resPartida'}">
													    	<jsp:include page="include/_reservaPartida.jsp"></jsp:include>
													    </c:when>
													    <c:when test="${tasca.tipus=='liciMenor'}">
													    	<c:if test="${esCap}">
													    		<jsp:include page="include/_resumOfertes.jsp"></jsp:include>
													    	</c:if>
													    	<c:if test="${!esCap}">
													    		<jsp:include page="include/_introduccioPresuposts.jsp"></jsp:include>
													    	</c:if>
													    </c:when>
													</c:choose>
												
									</div>
								</c:if>				 	
	                    		<div class="panel-footer">	                    		                  	
			                    	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddHistoric">
			                    		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
			                    		<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
			                    		<div class="row">
				                    		<div class="col-md-6">		
				                    			<div class="row">	 
				                    				<div class="col-md-12">
				                    					<input class="hidden" name="idTasca" value=${tasca.idTasca}>		
				                    					<textarea class="form-control" name="comentari" placeholder="comentari" rows="3"></textarea> 
				                      				</div>
				                      			</div>
				                      			<div class="row margin_top10">
										        	<div class="col-md-12">		
							                            <div class="col-xs-10">   
							                                <input type="file" class="btn" name="file" /><br/>
														</div> 		
										        	</div>
										        </div>
				                      		</div>
				                       		<div class="col-md-6">
				                       			<c:if test="${canRealitzarTasca}">
											        <div class="row">
											            <div class="col-md-12">
											            	<div class="col-md-6">
								                                <select class="form-control selectpicker" data-live-search="true" data-size="10" name="idUsuari" id="usuarisList">
									                                <c:forEach items="${llistaUsuaris}" var="usuari" >
								                                		<option value='${usuari.idUsuari}'>${usuari.getNomCompletReal()}</option>
								                                	</c:forEach>
								                                	<option data-divider="true"></option>
							                                		<option value='gerencia'>Gerència</option>
							                                		<option value='juridica'>Assessoria Jurídica</option>
							                                		<option value='obres'>Obres , Projectes i Supervisió</option>
							                                		<option value='comptabilitat'>Administració i comptabilitat</option>
							                                		<option value='instalacions'>Instal·lacions i Manteniment</option>      
								                                </select>
							                             	</div>
							                       			<input class="btn btn-success" type="submit" name="reasignar" value="Reassignar">
														</div>
											        </div>
										        </c:if>
										        <div class="row margin_top10">
										        	<div class="col-md-6"></div>
										            <div><input class="btn btn-primary" type="submit" name="afegirComentari" value="Només afegir comentari"></div>
										        </div>
										        <c:if test="${esCap}">
											        <div class="row margin_top10">
											        	<div class="col-md-6"></div>
											            <div><input class="btn btn-danger" type="submit" name="tancar" value="Tancar"></div>
											        </div>
										        </c:if>
										    </div>
				                       	</div>		                       	
			                       	</form>			                		                       	
			                   	</div>
			                </div>
		                </c:if>	
		                <c:if test="${tasca.activa && tasca.tipus == 'notificacio'}">
		                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddHistoric">
	                    		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
	                    		<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
	                    		<input class="hidden" name="idTasca" value="${tasca.idTasca}">	
	                    		<div class="row">
		                    		<div class="col-md-8">		
		                    			
		                      		</div>
		                       		<div class="col-md-4">		                       			
								        <div class="row margin_top10">
								        	<div class="col-md-6"></div>
								            <div><input class="btn btn-danger" type="submit" name="tancar" value="Borrar notificació"></div>
								        </div>
								    </div>
		                       	</div>		                       	
	                       	</form>
		                </c:if>
                    </div>                    
                </div>
				
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/tasca/vistaTasca.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>