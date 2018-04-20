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
                        	Tasca
							<small>
								<c:choose>
								    <c:when test="${tasca.tipus=='infPrev'}">
								        Sol·licitud proposta d'actuació
								    </c:when>								   
								    <c:when test="${tasca.tipus=='vacances'}">
								    	Sol·licitud vacances
								    </c:when>
								    <c:otherwise>
								       	Detalls tasca
								    </c:otherwise>
								</c:choose>
							</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active"><i class="fa fa-dashboard"></i>Tasca</li>
                            <li class="active"><i class="fa fa-table"></i> Detalls</li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <h2>Tasca</h2>        
                <div class="row">					
	                <div class="col-md-12">	   
						<div class="panel panel-${tasca.activa ? "success" : "danger"}">
						   	<div class="panel-heading">
						    	<div class="row">
						       		<div class="col-md-4">
						       			Referència Tasca: ${tasca.idTasca}
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
					<div class="col-md-offset-6 col-md-2">
				        <c:if test="${canBorrarTasca}">
				            <div><a href="eliminarTasca?idtasca=${tasca.idTasca}" class="btn btn-danger">Borrar</a></div>
				        </c:if>	
				    </div>			       	
	                <div class="col-md-2">	
	                	<div class="checkbox">
	                        <label>
	                          	<input id="seguiment" data-idtasca="${tasca.idTasca}" data-idusuari="${idUsuariLogg}" data-seguir="${!tasca.seguiment}" type="checkbox" ${tasca.seguiment ? 'checked' : ''}> Seguir tasca
	                        </label>
	                	</div> 
	                </div>
	                <div class="col-md-2">	
	                	<c:if test="${tasca.tipus!='vacances' && tasca.tipus!='judicial' && tasca.tipus!='factura'}">
		                	<div class="checkbox">
		                        <label>
		                          	<input id="seguimentActuacio" data-idactuacio="${actuacio.referencia}" data-idusuari="${idUsuariLogg}" data-seguir="${!actuacio.seguiment}" type="checkbox" ${actuacio.seguiment ? 'checked' : ''}> Seguir Actuació
		                        </label>
		                	</div> 
	                	</c:if>
	                </div>
	            </div>
	            <div class="row">
                	<div class="col-md-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	</div>
	            <c:if test="${tasca.tipus!='vacances' && tasca.tipus!='judicial' && tasca.tipus!='factura'}">					
					<div class="row">					
		                <div class="col-md-12">	                	
		                	<jsp:include page="../actuacio/include/_resumActuacio.jsp"></jsp:include>		                	
		                </div>				            	
					</div>						
				</c:if>
				<c:if test="${tasca.tipus=='judicial'}">					
					<div class="row">					
		                <div class="col-md-12">	                	
		                	<a target="_blanck" href="procediment?ref=${tasca.idinforme}">Anar al procediment -></a><br>              	
		                </div>				            	
					</div>						
				</c:if>
				<c:if test="${tasca.tipus=='factura'}">					
					<div class="row">					
		                <div class="col-md-12">	                	
		                	<a target="_blanck" href="facturaDetalls?ref=${tasca.idinforme}">Anar a la factura -></a><br>              	
		                </div>				            	
					</div>						
				</c:if>
                <div class="row">
                    <div class="col-md-12"> 
                        <c:forEach items="${historial}" var="historic" >                        	
                        	<div class="panel panel-info"> 
	                         	<div class="panel-heading">
	                         		<div class="">${historic.usuari.getNomComplet()} - ${historic.getDataString()}</div>
	                         	</div>
	                         	<div class="panel-body">
	                         		<div class="row panel-body">${historic.comentari}</div>		
					            </div>					          	
				    		</div>
	       				</c:forEach>   
	       				<c:if test="${tasca.documents.size() > 0}">
							<div class="panel panel-info">
			                   <div class="panel-body">
									<div class="col-md-12">
										<p>Arxius adjunts:</p>
							            <c:forEach items="${tasca.documents}" var="arxiu" >
							           		<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
												${arxiu.getDataString()} - ${arxiu.nom}
											</a>
											<c:if test="${esCap}">
												<a href="#"><span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
											</c:if>
											<br>
										</c:forEach>
									</div>
								</div>
							</div>
						</c:if>	                     
                        <c:if test="${tasca.activa && tasca.tipus != 'notificacio'}">		  
                        	<div class="panel panel-info">
                        		<c:if test="${tasca.tipus != 'generic'}">
	                        		<div>
		                        		<c:choose>
										    <c:when test="${tasca.tipus=='infPrev' || tasca.tipus == 'solInfPrev' || tasca.tipus == 'vistInfPrev'}">
										   		<c:if test="${canRealitzarTasca}">
											    	<div class="panel-body">
														<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddInforme">
															<input type="hidden" id="infPrev" name=infPrev value="${propostesInformeList.size()}">
															<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
															<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
															<input type="hidden" name="idTasca" value="${tasca.idTasca}">
															<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${tasca.informe.idInf}">																		
															<div class="col-md-12" id="">											
													    		<c:set var="propostaActuacio" value="${propostesInformeList[0]}" scope="request"/> 	
														    	<jsp:include page="include/_crearInformePrevi.jsp"></jsp:include>																		    	
															</div>	
															<c:if test="${!isGerencia}">																					
																<div class="form-group">
																	<div class="col-md-12">
														 				<div class="col-md-3">
																    		<div class="row">
																        		<div class="col-md-12">
																        			<input class="btn btn-success" type="submit" name="guardar" value="Pujar informe previ">
																			 	</div>
																     		</div>																	     		
																 		</div>															 			
															 		</div>															        		
																</div>
															</c:if>	
															<c:if test="${isGerencia}">	
																<c:if test="${tasca.informe.idInf == null || tasca.informe.idInf == '' || tasca.informe.idInf == '0'}">	
																	<div class="form-group">
																		<div class="col-md-4">
																			<label>Tècnic</label>	
																			<input type="hidden" id="tecnicPrevi" value="${informePrevi.usuari.idUsuari}" >														            	 										            	 	
															                <select class="form-control selectpicker" data-live-search="true" data-size="5" name="llistaUsuaris" id="llistaUsuaris">
															                	<c:forEach items="${llistaUsuaris}" var="usuari">
															                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
															                	</c:forEach>					                                	
															                </select>	
																		</div>
																	</div>
																</c:if>			
																<div class="form-group">
																	<div class="col-md-12">		
																		<div class="row">	 
																			<div class="col-md-12">						                    						
															          			<textarea class="form-control" name="comentariGerencia" placeholder="comentari gerència" rows="3"></textarea> 
															            	</div>
															        	</div>	        	
																	</div>						                       		
																</div>
																<div class="form-group">															
																	<div class="col-md-12">
																		<div class="col-md-3">
																   	 		<label>Prioritat</label>
																   	 		<input type="hidden" id="prioritatActual" value="${tasca.prioritat}" >									            	 	
																            <select class="form-control selectpicker" name="prioritatList" id="prioritatList">
																	           	<option value="0">No prioritari</option>
																	           	<option value="1">Prioritat 1</option>
																	           	<option value="2">Prioritat 2</option>
																	           	<option value="3">Prioritat 3</option>
																            </select>
																       	</div>		
														 				<div class="col-md-3">
																    		<div class="row">
																        		<div class="col-md-12">
																        			<input class="btn btn-success margin_top25" type="submit" name="tramitar" value="Tramitar">
																			 	</div>
																     		</div>																	     		
																 		</div>															 			
															 		</div>															        		
																</div>
															</c:if>															
														</form>
													</div>
													<%-- <c:if test="${propostesInformeList.size() > 0 && informePrevi.propostaActuacio.ruta == null}">
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
													</c:if> --%>
											        <!-- <div class="separator"></div>		 -->										        	
										        	<%-- <c:if test="${informePrevi.propostaActuacio.ruta != null && esCap}">
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
													            		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.vistiplauPropostaActuacio.getEncodedRuta()}">
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
													</c:if> --%>
												</c:if>
											</c:when>
											<c:when test="${tasca.tipus=='docprelicitacio'}">
										    	<jsp:include page="include/_docprelicitacio.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='doctecnica' || tasca.tipus=='vistDocTecnica'}">
										    	<jsp:include page="include/_doctecnica.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='modificacio'}">
										    	<jsp:include page="include/_modificacioInforme.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='autoritModificacio'}">
										    	<jsp:include page="include/_modificacioInformeJur.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='resPartida' || tasca.tipus=='resPartidaModificacio'}">
										    	<jsp:include page="include/_reservaPartida.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='certificatCredit'}">
										    	<jsp:include page="include/_certificatCredit.jsp"></jsp:include>
										    </c:when>
										     <c:when test="${tasca.tipus=='certificatCreditGerencia'}">
										    	<jsp:include page="include/_certificatCreditGerencia.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='autoritzacioActuacio'}">
										    	<jsp:include page="include/_autoritzacioPropostaActuacio.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='preLicitacio'}">
										    	<jsp:include page="include/_firmaPreLicitacio.jsp"></jsp:include>
										    </c:when>
										     <c:when test="${tasca.tipus=='ratClassificacio'}">
										    	<jsp:include page="include/_firmaRatClassificacio.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='liciMenor'}">
										    	<c:if test="${canRealitzarTasca}">													    
											    	<jsp:include page="include/_introduccioPresuposts.jsp"></jsp:include>
										    	</c:if>
										    </c:when>
										    <c:when test="${tasca.tipus=='autoritzacioDespesa'}">										    	
										    	<jsp:include page="include/_autoritzacioDespesa.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='autoritzacioModificacio'}">
										    	<jsp:include page="include/_autoritzarDespesaModificacio.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='vacances'}">
										    	<jsp:include page="include/_solicitudVacances.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='conformarFactura'}">
										    	<jsp:include page="include/_conformarFactura.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='revisarCertificacio' || tasca.tipus=='firmaCertificacio' || tasca.tipus=='certificacioFirmada'}">
										    	<jsp:include page="include/_revisarCertificacio.jsp"></jsp:include>
										    </c:when>
										    <c:when test="${tasca.tipus=='facturaConformada'}">
										    	<jsp:include page="include/_facturaConformada.jsp"></jsp:include>
										    </c:when>
										</c:choose>
									</div>
								</c:if>
								<c:if test="${true}">				 	
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
					                      			<c:if test="${tasca.tipus!='infPrev' && tasca.tipus!='solInfPrev' && tasca.tipus!='vistInfPrev' && tasca.tipus!='doctecnica' && tasca.tipus!='vistDocTecnica'}">
						                      			<div class="row margin_top10">
												        	<div class="col-md-12">		
									                            <div class="col-xs-10">   
									                                <input type="file" class="btn" name="file" multiple/><br/>
																</div> 		
												        	</div>
												        </div>
											       	</c:if>										       
					                      		</div>
					                       		<div class="col-md-6">
					                       			<c:if test="${canRealitzarTasca || esCap}">
												        <div class="row">
												            <div class="col-md-12">
												            	<div class="col-md-6">
									                                <select class="form-control selectpicker" data-live-search="true" data-size="10" name="idUsuari" id="usuarisList">
										                                <option value='-1'>Seleccionar usuari</option>
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
											        <c:if test="${esCap || tasca.tipus=='vacances' || tasca.tipus=='generic'}">
												        <div class="row margin_top10">
												        	<div class="col-md-6"></div>
												            <div><input class="btn btn-danger" type="submit" name="tancar" value="Tancar"></div>
												        </div>
											        </c:if>
											    </div>
					                       	</div>		                       	
				                       	</form>	
				                       	<c:if test="${isGerencia}">	
			                    			<div class="separator"></div>		                    			
			                    			<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoCanvisTasca">
				                    			<input class="hidden" name="idTasca" value="${tasca.idTasca}">	
				                    			<div class="row">
									            	<div class="col-md-6">
									            		<input class="hidden" id="tipusTascaPrevi" value="${tasca.tipus}">
						                                <select class="form-control selectpicker" data-live-search="true" data-size="10" name="tipusTasca" id="tipusTascaList">
								                           	<option value='generic'>Genèrica</option>
								                           	<option value='solInfPrev'>Sol·licitud informe previ</option>
								                           	<option value='modificacio'>Sol·licitud modificació</option>
								                           	<option value='vistInfPrev'>Vistiplau informe previ</option>
								                           	<option value='infPrev'>Informe previ gerència</option>
								                            <option value='doctecnica'>Redacció documentació tècnia</option>
								                            <option value='vistDocTecnica'>Vistiplau documentació tècnica</option>
								                           	<option value='docprelicitacio'>Preparar documentació licitació</option>
					                                		<option value='resPartida'>Reserva de crèdit</option>
					                                		<option value='resPartidaModificacio'>Reserva de crèdit modificació</option>
					                                		<option value='autoritzacioActuacio'>Signatura documentació expedient</option>
					                                		<option value='autoritzacioModificacio'>Aprovació modificació</option>
															<option value='liciMenor'>Proposta adjudicació</option>
															<option value='autoritzacioDespesa'>Resolució adjudicació</option>
															<option value='contracte'>Redacció contractes</option>
															<option value='conformarFactura'>Conformar factura</option>
					                                		<option value='facturaConformada'>Factura conformada</option>
															<option value='judicial'>Judicial</option>
						                                </select>
					                             	</div>
					                       			<input class="btn btn-success" type="submit" name="modificar" value="Modificar">
												</div>
			                    			</form>
			                    		</c:if>	   		                		                       	
				                   	</div>
				            	</c:if>
			                </div>
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