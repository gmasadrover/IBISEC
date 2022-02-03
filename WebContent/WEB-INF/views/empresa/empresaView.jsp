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
                            Empresa <small>${empresa.name}</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Empresa
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
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
                <div class="col-md-12 panel-group" id="accordion">
	                <c:if test="${not empty empresa}">   
	                	<div class="row">
		                	<div class="col-md-12">
		               			<p style="color: red;">
		               				<c:choose>
		               					<c:when test="${empresa.prohibicioContractar}">
											Aquesta empresa té prohibida la contratació. Fins ${empresa.getProhibitContractarFinsString()}
											<br/>
											<c:forEach items="${empresa.documentsProhibicioContractarList}" var="arxiu" >
					                			<c:set var="arxiu" value="${arxiu}" scope="request"/>
							            		<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
											</c:forEach>										
										</c:when>
		               					<c:when test="${!empresa.activa}">
											Aquesta empresa está extingida.
											<c:if test="${empresa.succesora.name.isEmpty()}">
												<br/>La seva succesora és: ${empresa.succesora.name} (${empresa.succesora.cif})
											</c:if>
										</c:when>
		               				</c:choose>									
								</p>
		               		</div>
		               	 </div>     
	                	<div class="panel panel-default">
	            		  	<div class="panel-heading">
						    	<h4 class="panel-title">
						        	<a data-toggle="collapse" data-parent="#accordion" href="#informacioBasica">Informació bàsica</a>
						      	</h4>
						    </div>
						    <div id="informacioBasica" class="panel-collapse collapse">					    	
						      	<div class="panel-body">  
						    		<div class="row">			    				    				    		
					                    <div class="col-xs-offset-1 col-md-5">
					    					<p>
												<label>CIF:</label> ${empresa.cif}
											</p>
				                            <input type="hidden" name="cif" value="${empresa.cif}">
				                            <c:if test="${! empresa.isUte()}"> 
						                        <p>
						                        	<label>Direcció:</label> ${empresa.direccio}
						                        </p>    
						                        <p> 
						                        	<label>Provincia: </label> ${empresa.provincia}
					                            </p>
						                        <p> 
						                        	<label>Teléfon: </label> ${empresa.telefon}
					                            </p>                           	
						                        <!--<p> 
						                        	<label>email: </label> ${empresa.email}
					                            </p>--> <!--Ocult a petició M.Garcia en data 10/11/21--> 
					                            <p>
					                            	<label>Pime: </label> ${empresa.isPime() ? "Si" : "No"}
					                            </p>
					                    	</c:if> 	                    		                            
					                  	</div>
						             	<div class="col-xs-offset-1 col-md-5">
						             		<p> 
					                        	<label>Nom: </label> ${empresa.name}
				                            </p>
				                            <c:if test="${! empresa.isUte()}"> 
							                    <p> 
						                        	<label>CP: </label> ${empresa.getCP()}
					                            </p> 	
						                        <p> 
						                        	<label>Localitat: </label> ${empresa.ciutat}
					                            </p>      
						                        <p> 
						                        	<label>Fax: </label> ${empresa.fax}
					                            </p>     
						                        <p> 
						                        	<label>Data constitució: </label> ${empresa.getDataConstitucioString()}
					                            </p>
					                            <p> 
						                        	<label>Tipus: </label> ${empresa.tipus}
					                            </p>
					                    	</c:if> 		                                                	
					                    </div>		            	
				                	</div>				                	
				                	<div class="row">
				                		<label class="col-xs-offset-1 col-xs-2 control-label">Escritura:</label>
				                		<c:forEach items="${empresa.documentsEscrituraList}" var="arxiu" >
				                			<c:set var="arxiu" value="${arxiu}" scope="request"/>
						            		<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
										</c:forEach>	
				                	</div>
				                	<div class="row">
				                		<label class="col-xs-offset-1 col-xs-5 control-label">Documentació acreditativa d'estar d'alta com a constructor(REA, o últim rebut d'autònom i model 036 0 037):</label>
				                		<c:if test="${empresa.documentREA.getEncodedRuta() != ''}">
					                		<a target="_blanck" href="downloadFichero?ruta=${empresa.documentREA.getEncodedRuta()}">${empresa.documentREA.nom}</a>
											<br>
										</c:if>
				                	</div>
		                		</div>
		                	</div>
		                </div>
		                <div class="panel panel-default">
	            		  	<div class="panel-heading">
						    	<h4 class="panel-title">
						        	<a data-toggle="collapse" data-parent="#accordion" href="#administradors">
										<c:choose>
						                 	<c:when test="${empresa.isUte()}">Gerents</c:when>
					                		<c:otherwise>Representació</c:otherwise>
					                	</c:choose>	 
									</a>
						      	</h4>
						    </div>
						    <div id="administradors" class="panel-collapse collapse">					    	
						      	<div class="panel-body">  
				                	<div class="row">
					                	<div class="table-responsive col-md-12">							                        
						                    <table class="table table-striped table-bordered filerTable" id="administradorsTable">
						                        <thead>
						                            <tr>
						                                <th>Nom</th>
						                                <th>DNI</th>
						                                <th>Tipus</th>
						                                <th>Vàlid fins</th>
						                                <th>Notari</th>
						                                <th>N. protocol</th>
						                                <th>Data</th>		
						                                <th>Validacio</th>
								                        <th>Organ validació</th>	
								                        <th>Documentació</th>			                                        							                                       
						                            </tr>
						                        </thead>
						                        <tbody>
													<c:forEach items="${empresa.administradors}" var="administrador" >
											          	<tr class="${administrador.isCaducat() ? 'danger' : '' }">							          	
											           		<td>${administrador.nom}</td>
											            	<td>${administrador.dni}</td>
											            	<td>${administrador.tipus}</td>
											            	<td>${administrador.getDataValidesaFinsString()}</td>
											            	<td>${administrador.notariModificacio}</td>
											            	<td>${administrador.protocolModificacio}</td>
											            	<td>${administrador.getDataModificacioString()}</td>
											            	<td>${administrador.getDataValidacioString()}</td>
													        <td>${administrador.getEntitatValidacioString()}</td>
													        <td><a target="_blanck" href="downloadFichero?ruta=${administrador.documentAdministrador.getEncodedRuta()}">${administrador.documentAdministrador.nom}</a></td>
											            </tr>
										       		</c:forEach>								                                	                              	
						                        </tbody>
						                    </table>
						                </div>
							    	</div>
							    </div>
							</div>
						</div>
						<%-- <div class="panel panel-default">
	            		  	<div class="panel-heading">
						    	<h4 class="panel-title">
						        	<a data-toggle="collapse" data-parent="#accordion" href="#validacions">Validacions</a>
						      	</h4>
						    </div>
						    <div id="validacions" class="panel-collapse collapse">					    	
						      	<div class="panel-body">  
				                	<div class="row">
					                	<div class="table-responsive col-md-12">							                        
						                    <table class="table table-striped table-bordered filerTable" id="validacionsTable">
						                        <thead>
						                            <tr>
						                                <th>Ref</th>
				                                        <th>Data validació</th>				                                                                 
				                                        <th>Persona facultada</th>
				                                        <th>Càrrec</th>                                       
				                                        <th>Any</th>	                                        							                                       
						                            </tr>
						                        </thead>
						                        <tbody>
												<c:forEach items="${validacions}" var="bastanteo" >
										          	<tr>							          	
										           		<td>
										           			<c:choose>
										           				<c:when test="${canModificar}">
										           					<a href="bastanteo?ref=${bastanteo.ref}" class="loadingButton"  data-msg="obrint validació...">${bastanteo.ref}</a></td>
										           				</c:when>
										           				<c:otherwise>
										           					${bastanteo.ref}
										           				</c:otherwise>
										           			</c:choose>
										           		<td>${bastanteo.getDatabastanteoString()}</td>
										            	<td>${bastanteo.personaFacultada}</td>
										            	<td>${bastanteo.carrec}</td>
										            	<td>${bastanteo.anyBastanteo}</td>
										            </tr>
									       		</c:forEach>						                                	                              	
						                        </tbody>
						                    </table>
						                </div>
							    	</div>
							    </div>
							</div>
						</div> --%>
					    <c:if test="${! empresa.isUte()}"> 
					    	<div class="panel panel-default">
		            		  	<div class="panel-heading">
							    	<h4 class="panel-title">
							        	<a data-toggle="collapse" data-parent="#accordion" href="#solv">Solvència</a>
							      	</h4>
							    </div>
							    <div id="solv" class="panel-collapse collapse">
							     	<c:if test="${empresa.solEconomica.getEncodedRuta() != '' || empresa.solTecnica.getEncodedRuta() != ''}">					    	
							      		<div class="panel-body">        	               
						                	<h4 class="margin_bottom30">Solvència econòmica</h4>
						                	<c:if test="${empresa.solEconomica != null}">
							                	<div class="row">
							                		<label class="col-xs-2 control-label">Document:</label>
							                		<a target="_blanck" href="downloadFichero?ruta=${empresa.solEconomica.getEncodedRuta()}">${empresa.solEconomica.nom}</a>
							                	</div>
							                	<div class="row">
							                		<div class="col-md-10">
							                			<p>
								                        	<label>Exercici:</label> ${empresa.getExerciciEconomicString()}
								                        </p> 	                		
						                        	</div>
							                	</div>
							                	<div class="row">
							                		<div class="col-md-10">
							                			<p>
								                        	<label>Dipositat en Registre Mercantil amb data:</label> ${empresa.getRegistreMercantilDataString()}
								                        </p> 	                		
						                        	</div>					                        	
							                	</div>
							                	<div class="row">
							                		<div class="col-md-10">
							                			<p>
								                        	<label>Ràtio A/P:</label> ${empresa.getRatioAP()}
								                        </p> 	                		
						                        	</div>
							                	</div>
						                	</c:if>
						                	<h4 class="margin_bottom30">Solvència Tècnica</h4>
						                	<c:if test="${empresa.solTecnica != null}">
							                	<div class="row">
							                		<label class="col-xs-2 control-label">Document:</label>
							                		<a target="_blanck" href="downloadFichero?ruta=${empresa.solTecnica.getEncodedRuta()}">${empresa.solTecnica.nom}</a>
							                	</div>
							                </c:if>
							            </div>
							        </c:if>
						            <c:if test="${empresa.classificacioFileROLECE.getEncodedRuta() != '' || empresa.classificacioFileJCCaib.getEncodedRuta() != '' || empresa.classificacioFileJCA.getEncodedRuta() != ''}">						            
							            <div class="panel-body"> 
											<h4 class="margin_bottom30">Classificació</h4>  
							      			<div class="row">
						                		<div class="col-md-10">
						                			<p>
							                        	<label>Darrera data vigència ROLECE:</label> ${empresa.getDataVigenciaClassificacioROLECEString()}
							                        </p> 	                		
					                        	</div>
						                	</div>
						                	<div class="row">
						                		<label class="col-xs-2 control-label">Document:</label>
						                		<a target="_blanck" href="downloadFichero?ruta=${empresa.classificacioFileROLECE.getEncodedRuta()}">${empresa.classificacioFileROLECE.nom}</a>
						                	</div> 
						                	</br>
						                	<div class="row">
						                		<div class="col-md-10">
						                			<p>
							                        	<label>Darrera data vigència JCCaib:</label> ${empresa.getDataVigenciaClassificacioJCCaibString()}
							                        </p> 	                		
					                        	</div>
						                	</div>
						                	<div class="row">
						                		<label class="col-xs-2 control-label">Document:</label>
						                		<a target="_blanck" href="downloadFichero?ruta=${empresa.classificacioFileJCCaib.getEncodedRuta()}">${empresa.classificacioFileJCCaib.nom}</a>
						                	</div> 
						                	</br>
						                	<div class="row">
						                		<div class="col-md-10">
						                			<p>
							                        	<label>Darrera data vigència JCA:</label> ${empresa.getDataVigenciaClassificacioJCAString()}
							                        </p> 	                		
					                        	</div>
						                	</div>
						                	<div class="row">
						                		<label class="col-xs-2 control-label">Document:</label>
						                		<a target="_blanck" href="downloadFichero?ruta=${empresa.classificacioFileJCA.getEncodedRuta()}">${empresa.classificacioFileJCA.nom}</a>
						                	</div>           		
						                      <%--   <div class="col-xs-offset-2 col-md-7">	
													<label>Classificació</label>							                        
									                <div class="table-responsive">							                        
									                    <table class="table table-striped table-bordered filerTable" id="classificacioTable">
									                        <thead>
									                            <tr>
									                                <th>Grup</th>
									                                <th>Subgrup</th>
									                                <th>Categoria</th>				                                        							                                       
									                            </tr>
									                        </thead>
									                        <tbody>	
										                        <c:forEach items="${empresa.getClassificacio()}" var="classificacio" >
														          	<tr>							          	
														           		<td>${classificacio.grup}</td>
														            	<td>${classificacio.subGrup}</td>
														            	<td>${classificacio.categoria}</td>
														            </tr>
													       		</c:forEach>							                                	                              	
									                        </tbody>
									                    </table>
									                </div>
									           	</div>	 --%>		
						                </div>
					                </c:if>
						        </div>
						    </div>
					        <div class="panel panel-default">      
							    <div class="panel-heading">
							    	<h4 class="panel-title">
							        	<a data-toggle="collapse" data-parent="#accordion" href="#acreditacions">Acreditació d'obligacions fiscals i de seguretat social</a>
							      	</h4>
							    </div>
							    <div id="acreditacions" class="panel-collapse collapse">					    	
						      		<div class="panel-body">        	                
						                <div class="row">
					                		<div class="col-md-12">
					                			<div class="form-group">
					                				<div class="col-xs-offset-1 col-md-10">
						                				<div class="checkbox">
									                        <label>
									                          	Certificat positiu de l'Agència Estatal d'Administració Tributària, 
									                          	d'estar al corrent en el comliment de les seves obligacions tributàries amb l'Estat.
									                        </label>
									                	</div>
									                </div>
					                			</div>
					                			<div class="col-xs-offset-2 col-md-4">
						                			<div class="form-group">
						                                <div class="form-group">
							                                <label>Expedit amb data: </label> ${empresa.getDateExpAcreditacio1String()}
							                            </div>			
						                            </div>
						                        </div>
						                        <div class="col-md-6">
						                			<div class="form-group">		                                
														<c:if test="${empresa.isCaducadaAcreditacio1()}">
															<label class="col-xs-1 control-label">Caducat</label>
														</c:if>								
						                            </div>
						                        </div>      
					                			<div class="form-group">
					                				<div class="col-xs-offset-1 col-md-10">
						                				<div class="checkbox">
									                        <label>
									                          	Certificat de la Tresoreria General de la Seguretat Social del Ministeri
									                          	d'Ocupació i Seguretat Social, de què l'empresa està al corren en el compliment de les obligacions
									                          	de pagamanet de la Seguretat Social.
									                        </label>
									                	</div>
									                </div>
					                			</div>
					                			<div class="col-xs-offset-2 col-md-4">
						                			<div class="form-group">
						                                <label>Expedit amb data: </label> ${empresa.getDateExpAcreditacio2String()}
						                            </div>
						                        </div>
						                        <div class="col-md-6">
						                			<div class="form-group">		                                
														<c:if test="${empresa.isCaducadaAcreditacio2()}">
															<label class="col-xs-1 control-label">Caducat</label>
														</c:if>								
						                            </div>
						                        </div>        
					                			<div class="form-group">
					                				<div class="col-xs-offset-1 col-md-10">
						                				<div class="checkbox">
									                        <label>
									                          	Certificat de la secretària de la Junta Consultiva de Contratació Administrativa
									                          	de què l'empresa no té deutes de naturalesa tributària amb la Comunitat Autònoma de les Illes Balears, en
									                          	via de constrenyiment.
									                        </label>
									                	</div>
									                </div>
					                			</div>
					                			<div class="col-xs-offset-2 col-md-4">
						                			<div class="form-group">
						                                <div class="form-group">
							                                <label>Expedit amb data: </label> ${empresa.getDateExpAcreditacio3String()}
							                            </div>
						                            </div>
						                        </div>
						                        <div class="col-md-6">
						                			<div class="form-group">		                                
														<c:if test="${empresa.isCaducadaAcreditacio3()}">
															<label class="col-xs-1 control-label">Caducat</label>
														</c:if>								
						                            </div>
						                        </div>        
					                		</div>
					                	</div>
					                </div>
					            </div>
					       	</div>					     
		                </c:if>
		                <c:if test="${empresa.isUte()}">
		                	<div class="panel panel-default">      
							    <div class="panel-heading">
							    	<h4 class="panel-title">
							        	<a data-toggle="collapse" data-parent="#accordion" href="#empreses">Empreses</a>
							      	</h4>
							    </div>
							    <div id="empreses" class="panel-collapse collapse">					    	
						      		<div class="panel-body">        
					                	<div class="row">
						                	<div class="table-responsive col-md-12">							                        
							                    <table class="table table-striped table-bordered filerTable" id="empresesUTETable">
							                        <thead>
							                            <tr>
							                                <th>CIF</th>
							                                <th>Nom</th>			                               		                                        							                                       
							                            </tr>
							                        </thead>
							                        <tbody>
													<c:forEach items="${empresa.getUte().empreses}" var="empresaUTE" >
											          	<tr>							          	
											           		<td><a href="empresa?cif=${empresaUTE.cif}">${empresaUTE.cif}</a></td>
											            	<td>${empresaUTE.name}</td>							            	
											            </tr>
										       		</c:forEach>						                                	                              	
							                        </tbody>
							                    </table>
							                </div>
								    	</div>
							    	</div>
						    	</div>
					    	</div>
					    </c:if>
					    <div class="panel panel-default">      
						    <div class="panel-heading">
						    	<h4 class="panel-title">
						        	<a data-toggle="collapse" data-parent="#accordion" href="#ofertes">Licitacions</a>
						      	</h4>
						    </div>
						    <div id="ofertes" class="panel-collapse collapse">					    	
					      		<div class="panel-body">
				                	<div class="row">	                		
					                        <div class="col-md-10">	
												<label>Ofertes</label>							                        
								                <div class="table-responsive">							                        
								                    <table class="table table-striped table-bordered filerTable" id="ofertesTable">
								                        <thead>
								                            <tr>
								                                <th>Expedient</th>
								                                <th>Actuació</th>
								                                <th>Detalls</th>
								                                <th>Centre</th>
								                                <th>Valor</th>
								                                <th>Data</th>	
								                                <th>Data</th>			                                        							                                       
								                            </tr>
								                        </thead>
								                        <tbody>	
									                        <c:forEach items="${informesEmpresa}" var="informe" >									                        	
													          	<tr class=${informe.expcontratacio != null && ! informe.expcontratacio.anulat ? informe.ofertaSeleccionada.cifEmpresa == empresa.cif ? "success" : "warning" : "danger"}>								          	
													           		<td><a href="actuacionsDetalls?ref=${informe.actuacio.referencia}&exp=${informe.idInf}">
													           			<c:choose>
													            			<c:when test="${informe.expcontratacio.expContratacio != '-1'}">
													            				${informe.expcontratacio.expContratacio}
													            			</c:when>
													            			<c:otherwise>
													            				${informe.idInf}
													            			</c:otherwise>
													            		</c:choose>
													           			</a>
													           		</td>
													           		<td><a href="actuacionsDetalls?ref=${informe.actuacio.referencia}&exp=${informe.idInf}">${informe.actuacio.referencia}</a></td>
													            	<td>${informe.actuacio.descripcio}</td>
													            	<td>${informe.actuacio.centre.getNom()}</td>
													            	<td>${informe.ofertaSeleccionada.getPlicFormat()}</td>
													            	<td>${informe.ofertaSeleccionada.getDataAprovacioString()}</td>
													            	<td>${informe.ofertaSeleccionada.getDataAprovacio()}</td>
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
					    <div class="panel panel-default">      
						    <div class="panel-heading">
						    	<h4 class="panel-title">
						        	<a data-toggle="collapse" data-parent="#accordion" href="#infoadicional">Altra informació</a>
						      	</h4>
						    </div>
						    <div id="infoadicional" class="panel-collapse collapse">					    	
					      		<div class="panel-body">
				                	<div class="row">
				                		<div class="col-xs-offset-1 col-md-10 longText">
				                			<p> 
					                        	<label>Informació adicional: </label> 
					                        	${empresa.informacioAdicional}
				                            </p>	                		
				                        </div>
				                	</div>
			                	</div>
		                	</div>
	                	</div>			                	
	                	<div class="row">
	                		<div class="col-md-offset-7 col-md-2 margin_top30">
		               			<c:if test="${canModificar}">
									<a href="editEmpresa?cif=${empresa.cif}" class="btn btn-primary" role="button">Modificar</a>									
								</c:if>
							</div>
							<div class="col-md-2 margin_top30">
								<form class="form-horizontal" target="_blank" method="POST" action="CrearDocument" onsubmit="setTimeout(function () { window.location.reload(); }, 30)">
									<input type="hidden" id="cif" name=cif value="${empresa.cif}">
									<input type="hidden" id="tipus" name=tipus value="empresa">
									<input class="btn btn-warning" type="submit" name="vistiplau" value="Generar informe empresa">
								</form>
							</div>
	                	</div>                	
	                </c:if>
                <!-- /.row -->     
           		</div>
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/empresa/modificar.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>