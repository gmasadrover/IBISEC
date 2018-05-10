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
                            Expedient <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Informe
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Modificar
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <div class="row">
                	<div class="col-md-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <!-- /.row -->
                <c:if test="${not empty expedient}">               	      
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditInforme">   
                		<input type="hidden" name="redireccio" value="${redireccio}">    
                		<input type="hidden" name="expedient" value="${expedient.expContratacio}">
                	 	<input type="hidden" name="informe" value="${expedient.idInforme}">  
                	 	<input type="hidden" id="contracte" value="${expedient.contracte}">    
                	 	<div class="form-group">
                	 		<div class="col-md-6">
								<label>Expedient</label>
								<input name="expedientNou" placeholder="" value="${expedient.expContratacio}" required>
							</div>
                	 	</div>
                	 	<div class="form-group">
                	 		<div class="col-md-3">
                	 			<label>Data creació</label>
                                <div class="input-group date datepicker">
								  	<input type="text" class="form-control" name="dataCreacio" value="${informePrevi.getDataCreacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                	 		</div>
							<div class="col-md-4">
								<label>Tècnic</label>
								<input type="hidden" id="tecnicPrev" value="${informePrevi.usuari.idUsuari}" >							            	 										            	 	
				                <select class="form-control selectpicker" data-live-search="true" data-size="5" name="llistaUsuaris" id="llistaUsuaris">
				                	<c:forEach items="${llistaUsuaris}" var="usuari">
				                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
				                	</c:forEach>					                                	
				                </select>	
							</div>
						</div>       		
                		<div class="form-group">                               
                            <div class="col-md-12">
                            	<label>Descripció</label>
                            	<textarea class="form-control" name="descripcio" placeholder="descripció" rows="3">${informePrevi.propostaInformeSeleccionada.objecte}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
							<div class="col-md-12">
								<label>Pressupost</label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-4">
					          	<label>PBase</label>
					          	<input name="pbase" class="pbase" id="pbase" placeholder="0000,00" value="${informePrevi.propostaInformeSeleccionada.pbase}" required>
					          	<label class="">€</label>
					        </div>
					        <div class="col-md-4">
						     	<label>IVA</label>
						       	<input disabled id="iva" class="iva" placeholder="0000,00" value="${informePrevi.propostaInformeSeleccionada.iva}">
						     	<input type="hidden" name="iva" class="inputIVA" id="inputIVA" value="${informePrevi.propostaInformeSeleccionada.iva}">
						       	<label class="">€</label>
							</div>
							<div class="col-md-4">
								<label>PLic</label>
								<input name="plic" id="plic" class="plic" placeholder="0000,00" value="${informePrevi.propostaInformeSeleccionada.plic}">						
								<label class="">€</label>
							</div>					                                
						</div>
                        <div class="form-group">
				     		<div class="col-md-3">
				     	 		<label>Tipus de Contracte</label>
				     	 		<input type="hidden" id="tipusContractePrev" value="${informePrevi.propostaInformeSeleccionada.tipusObra}" >									            	 	
					            <select class="form-control selectpicker" name="tipusContracte" id="tipusContracte">
						           	<option value="obr">Obra</option>
						           	<option value="srv">Servei</option>
						           	<option value="submi">subministrament</option>
					            </select>
				        	</div>	
				       		<div class="visibleObres visibleObres">					                             	
					        	<div class="col-md-3">
						      	 	<label>Autorització urbanística</label>
						      	 	<input type="hidden" id="reqLlicenciaPrev" value="${informePrevi.propostaInformeSeleccionada.llicencia ? 'si' : 'no'}" >
						            <select class="form-control selectpicker" name="reqLlicencia" id="reqLlicencia">
						            	<option value="si">Si</option>
						            	<option value="no">No</option>
						            </select>
					            </div>	
					            <div class="col-md-3 visibleTipusLlicencia visibleTipusLlicencia">
						      	 	<label>Tipus</label>
						      	 	<input type="hidden" id="tipusLlicenciaPrev" value="${informePrevi.propostaInformeSeleccionada.tipusLlicencia}" >
					                <select class="form-control selectpicker" name="tipusLlicencia" id="tipusLlicencia">
					                	<option value="major">Llicència</option>
					                	<option value="comun">Comunicació prèvia</option>
					                </select>
					           	</div>					           	
							</div>				                       																
						</div>							
						<div class="form-group">
							<div class="col-md-6">
								<label>Termini d'execució</label>
								<input name="termini" placeholder="" value="${informePrevi.propostaInformeSeleccionada.termini}" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">		
								<div class="row">	 
									<div class="col-md-12">						                    						
					          			<textarea class="form-control" name="comentariTecnic" placeholder="comentari tècnic" rows="3">${informePrevi.propostaInformeSeleccionada.comentari}</textarea> 
					            	</div>
					        	</div>	        	
							</div>						                       		
						</div>	
						<div class="form-group">						       	
			    			<div class="col-md-12">
			    				<label>Arxius adjunts</label> 
			    				<c:forEach items="${informePrevi.adjunts}" var="arxiu" >
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
									<br>
								</c:forEach>
			           			<input type="file" class="btn uploadImage" name="informe" /><br/>																 		
			    			</div>
			      		</div>	
			      		<div class="form-group">                               
                            <div class="col-md-12">
                            	<label>Notes</label>
                            	<textarea class="form-control" name="notes" placeholder="notes" rows="3">${informePrevi.notes}</textarea>
                            </div>
                        </div>
                        <div class="informacioMenors">
	                       	<div class="form-group">    
	                       		<div class="col-md-12"> 
					           		<div class="document">
					             		<label>Proposta d'actuació signada:	</label>											                  	
					          			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaActuacio.getEncodedRuta()}">
											${informePrevi.propostaActuacio.nom}
										</a>	
										<c:if test="${informePrevi.propostaActuacio.signat}">
											<span class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.propostaActuacio.ruta != null}">
											<span data-ruta="${informePrevi.propostaActuacio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
											<c:forEach items="${informePrevi.propostaActuacio.firmesList}" var="firma" >
												<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
												<br>
											</c:forEach>
										</div>
									</div>	
									<input type="file" class="btn uploadImage" name="propostaActuacio" /><br/>	
								</div>																			
							</div>										
							<%-- <div class="form-group">    
			                	<div class="col-md-6"> 
									<label>Vistiplau:</label>
									<input type="hidden" id="capVistiplau" value="${informePrevi.usuariCapValidacio.idUsuari}" >							            	 										            	 	
					                <select class="form-control selectpicker" data-live-search="true" data-size="5" name="llistaCaps" id="llistaCaps">
					                	<option value="-1">Sense vistiplau</option>
					                	<c:forEach items="${llistaCaps}" var="usuari">
					                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
					                	</c:forEach>					                                	
					                </select>	
								</div>
								<div class="col-md-3">
									<label>Data vistiplau</label>
	                                <div class="input-group date datepicker">
									  	<input type="text" class="form-control" name="dataVistiplau" value="${informePrevi.getDataCapValidacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
								</div>
							</div>
							<div class="form-group">    
	                       		<div class="col-md-12"> 
									<label>Comentari Cap</label>
									<textarea class="form-control" name="comentariCap" placeholder="Comentari Cap" rows="3">${informePrevi.comentariCap}</textarea>
								</div>
							</div>																
							<div class="form-group">    
	                       		<div class="col-md-12"> 
									<div class="document">
						              		<label>Vistiplau proposta d'actuació signada:</label>													               
										<a target="_blanck" href="downloadFichero?ruta=${informePrevi.vistiplauPropostaActuacio.getEncodedRuta()}">
											${informePrevi.vistiplauPropostaActuacio.nom}
										</a>
										<c:if test="${informePrevi.vistiplauPropostaActuacio.signat}">
											<span class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.vistiplauPropostaActuacio.ruta != null}">
											<span data-ruta="${informePrevi.vistiplauPropostaActuacio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
											<c:forEach items="${informePrevi.vistiplauPropostaActuacio.firmesList}" var="firma" >
												<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
												<br>
											</c:forEach>
										</div>
									</div>																	
									<input type="file" class="btn uploadImage" name="vistiplauProposta" /><br/>	
	                        	</div>
	                        </div> --%>
                        </div>
                        <div class="informacioMajors">
                        	<div class="form-group">
	                        	<div class="col-md-12">
					       			<div class="document">
										<label>Informe supervisió / contractació:</label>										                  	
						           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.informeSupervisio.getEncodedRuta()}">
											${informePrevi.informeSupervisio.nom}
										</a>	
										<c:if test="${informePrevi.informeSupervisio.signat}">
												<span class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.informeSupervisio.ruta != null}">
											<span data-ruta="${informePrevi.informeSupervisio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
											<c:forEach items="${informePrevi.informeSupervisio.firmesList}" var="firma" >
												<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
												<br>
											</c:forEach>
										</div>	
									</div>	
									<input type="file" class="btn uploadImage" name="informeSupervisio" /><br/>		
					       		</div>
					       	</div>
                        </div>
						<div class="form-group">
				    		<div class="col-md-4">	
				      			<label>Partida asignada</label>		
				      			<input type="hidden" id="partidaPrev" value="${informePrevi.assignacioCredit.partida.codi}" >							            	 										            	 	
				                <select class="form-control selectpicker" name="llistaPartides" id="llistaPartides">
				                	<option value="-1">No assignar partida</option>
				                	<c:forEach items="${partidesList}" var="partida">
				                		<option value="${partida.codi}">${partida.codi} (${partida.nom} - Restant: ${partida.getPartidaPerAsignarFormat()})</option>
				                	</c:forEach>					                                	
				                </select>	
				            </div>					                       		
				       	</div>	                		
		                <div class="row">
	                		<div class="form-group">
	                			<label class="col-xs-2 control-label">Afectat BEI</label> 
		                		<div class="checkbox">
			                        <label>
			                          	<input name="bei" type="checkbox" ${informePrevi.assignacioCredit.bei ? 'checked' : ''}>
			                        </label>
			                	</div> 
			                </div>
                		</div>
	                	<div class="row">
	                		<div class="form-group">
	                			<label class="col-xs-2 control-label">Afectat FEDER</label> 
		                		<div class="checkbox">
			                        <label>
			                          	<input name="feder" type="checkbox" ${informePrevi.assignacioCredit.feder ? 'checked' : ''}>
			                        </label>
			                	</div> 
			                </div>
	                	</div>
				   		<div class="form-group">
				    		<div class="col-md-12">		
				    			<div class="row">	 
				    				<div class="col-md-12">						                    						
				    					<textarea class="form-control" name="comentariFinancer" placeholder="comentari àrea econòmica" rows="3">${informePrevi.comentariPartida}</textarea> 
				      				</div>
				      			</div>
				      		</div>						                       		
				       	</div>	
				       	<div class="form-group">
				       		<div class="col-md-6">
				       			<div class="document">
									<label>Autorització àrea econòmica signada:</label>										                  	
					           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.conformeAreaEconomivaPropostaActuacio.getEncodedRuta()}">
										${informePrevi.conformeAreaEconomivaPropostaActuacio.nom}
									</a>	
									<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.signat}">
											<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.ruta != null}">
										<span data-ruta="${informePrevi.conformeAreaEconomivaPropostaActuacio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
									</c:if>
									<br>
									<div class="infoSign hidden">
										<c:forEach items="${informePrevi.conformeAreaEconomivaPropostaActuacio.firmesList}" var="firma" >
											<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
											<br>
										</c:forEach>
									</div>	
								</div>	
								<input type="file" class="btn uploadImage" name="conformeAreaEconomica" /><br/>		
				       		</div>
				       		<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.ruta == null}">
					       		<div class="col-md-6">
									<div class="form-group">
							    		<div class="col-md-12">
									       	<div class="row">
						                		<div class="form-group">
											        <div class="col-md-12">
											            <input id="createTascaReservaCredit" data-informe="${informePrevi.idInf}" class="btn btn-success" value="Sol. reserva partida">							            
											        </div>
											    </div> 
						                	</div>
						                </div>
						           	</div>
								</div>		
							</c:if>
				       	</div>	
				       	<c:if test="${expedient.contracte != 'major'}">
					       	<div class="form-group">  
								<div class="col-md-3">
									<label>Data aprovació</label>
	                                <div class="input-group date datepicker">
									  	<input type="text" class="form-control" name="dataAprovacio" value="${informePrevi.getDataAprovacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
								</div>
							</div>
					       	<div class="form-group">
					       		<div class="col-md-12">
					       			<div class="document">
										<label>Autorització proposta d'actuació signada:</label>										                  	
							          		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaAutoritzacio.getEncodedRuta()}">
											${informePrevi.autoritzacioPropostaAutoritzacio.nom}
										</a>	
										<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.signat}">
												<span class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.ruta != null}">
											<span data-ruta="${informePrevi.autoritzacioPropostaAutoritzacio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
											<c:forEach items="${informePrevi.autoritzacioPropostaAutoritzacio.firmesList}" var="firma" >
												<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
												<br>
											</c:forEach>
										</div>	
									</div>
									<input type="file" class="btn uploadImage" name="autoritzacioActuacio" /><br/>	
					       		</div>
					       	</div>
				       	</c:if>
				       	<div class="autoritzacioConsellGovern">
				       		<div class="form-group">
					       		<div class="col-md-6">
					       			<div class="document">
										<label>Autorització Consell de Govern:</label>										                  	
							          		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioConsellDeGovern.getEncodedRuta()}">
											${informePrevi.autoritzacioConsellDeGovern.nom}
										</a>	
										<c:if test="${informePrevi.autoritzacioConsellDeGovern.signat}">
												<span class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.autoritzacioConsellDeGovern.ruta != null}">
											<span data-ruta="${informePrevi.autoritzacioConsellDeGovern.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
											<c:forEach items="${informePrevi.autoritzacioConsellDeGovern.firmesList}" var="firma" >
												<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
												<br>
											</c:forEach>
										</div>	
									</div>
									<input type="file" class="btn uploadImage" name="autoritzacioConsellDeGovern" /><br/>	
					       		</div>
					       		<c:if test="${informePrevi.autoritzacioConsellDeGovern.ruta == null}">
						       		<div class="col-md-6">
										<div class="form-group">
								    		<div class="col-md-12">
										       	<div class="row">
							                		<div class="form-group">
												        <div class="col-md-12">
												            <input id="createTascaConsellDeGovern" data-informe="${expedient.idInforme}" class="btn btn-success" value="Sol. aut. Govern">							            
												        </div>
												    </div> 
							                	</div>
							                </div>
							           	</div>
									</div>		
								</c:if>
					       	</div>
				       	</div>
				       	<div class="autoritzacioConseller">
				       		<div class="form-group">
					       		<div class="col-md-6">
					       			<div class="document">
										<label>Autorització Conseller:</label>										                  	
							          		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioConseller.getEncodedRuta()}">
											${informePrevi.autoritzacioConseller.nom}
										</a>	
										<c:if test="${informePrevi.autoritzacioConseller.signat}">
												<span class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.autoritzacioConseller.ruta != null}">
											<span data-ruta="${informePrevi.autoritzacioConseller.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
											<c:forEach items="${informePrevi.autoritzacioConseller.firmesList}" var="firma" >
												<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
												<br>
											</c:forEach>
										</div>	
									</div>
									<input type="file" class="btn uploadImage" name="autoritzacioConseller" /><br/>	
					       		</div>
					       		<c:if test="${informePrevi.autoritzacioConseller.ruta == null}">
						       		<div class="col-md-6">
										<div class="form-group">
								    		<div class="col-md-12">
										       	<div class="row">
							                		<div class="form-group">
												        <div class="col-md-12">
												            <input id="createTascaConseller" data-informe="${expedient.idInforme}" class="btn btn-success" value="Sol·licitar aut. Conseller">							            
												        </div>
												    </div> 
							                	</div>
							                </div>
							           	</div>
									</div>	
								</c:if>	
					       	</div>
				       	</div>
				       	<c:choose>
					       	<c:when test="${expedient.contracte == 'major'}">
						       	<div class="form-group">  
									<div class="col-md-3">
										<label>Data publicació</label>
		                                <div class="input-group date datepicker">
										  	<input type="text" class="form-control" name="dataPublicacioBOIB" value="${informePrevi.getPublicacioBOIBString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
										</div>
									</div>
								</div>
								<c:if test="${informePrevi.documentBOIB.ruta != null}">
							       	<div class="form-group">
							       		<div class="col-md-12">
							       			<div class="document">
												<label>Document enviar BOIB:</label>										                  	
									          		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.documentBOIB.getEncodedRuta()}">
													${informePrevi.documentBOIB.nom}
												</a>	
												<c:if test="${informePrevi.documentBOIB.signat}">
														<span class="glyphicon glyphicon-pencil signedFile"></span>
												</c:if>
												<c:if test="${informePrevi.documentBOIB.ruta != null}">
													<span data-ruta="${informePrevi.documentBOIB.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
												</c:if>
												<br>
												<div class="infoSign hidden">
													<c:forEach items="${informePrevi.documentBOIB.firmesList}" var="firma" >
														<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
														<br>
													</c:forEach>
												</div>	
											</div>
											<input type="file" class="btn uploadImage" name="documentBOIB" /><br/>	
							       		</div>
							       	</div>
								</c:if>
					       	</c:when>
					       	<c:otherwise>
					       		<div class="form-group">
						       		<div class="col-md-12">
						       			<div class="document">
											<label>Correu invitació licitació:</label>										                  	
								          		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.correuInvitacio.getEncodedRuta()}">
												${informePrevi.correuInvitacio.nom}
											</a>	
											<c:if test="${informePrevi.correuInvitacio.signat}">
													<span class="glyphicon glyphicon-pencil signedFile"></span>
											</c:if>
											<c:if test="${informePrevi.correuInvitacio.ruta != null}">
												<span data-ruta="${informePrevi.correuInvitacio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
											</c:if>
											<br>
											<div class="infoSign hidden">
												<c:forEach items="${informePrevi.correuInvitacio.firmesList}" var="firma" >
													<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
													<br>
												</c:forEach>
											</div>	
										</div>
										<input type="file" class="btn uploadImage" name="correuInvitacio" /><br/>	
						       		</div>
						       	</div>
					       	</c:otherwise>
				       	</c:choose>				       	
				       	<div class="form-group">
				    		<div class="col-md-12">
						       	<div class="row">
			                		<div class="form-group">
								        <div class="col-xs-offset-9 col-xs-9">
								            <input type="submit" class="btn btn-primary" value="Actualitzar expedient">							            
								        </div>
								    </div> 
			                	</div>
			                </div>
			           	</div>
	                </form>
                </c:if>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/expedient/modificarInforme.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>