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
						                        <p> 
						                        	<label>email: </label> ${empresa.email}
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
					                    	</c:if> 		                                                	
					                    </div>		            	
				                	</div>
				                	<c:if test="${! empresa.isUte()}"> 
					                	<div class="row">
					                		<div class="col-xs-offset-1 col-md-10 longText">
					                			<p> 
						                        	<label>Objecte social: </label> 
						                        	${empresa.objecteSocial}
					                            </p>	                		
					                        </div>
					                	</div> 
					                </c:if> 
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
												        <td>${administrador.entitatValidacio}</td>
										            </tr>
									       		</c:forEach>						                                	                              	
						                        </tbody>
						                    </table>
						                </div>
							    	</div>
							    </div>
							</div>
						</div>
					    <c:if test="${! empresa.isUte()}"> 
					    	<div class="panel panel-default">
		            		  	<div class="panel-heading">
							    	<h4 class="panel-title">
							        	<a data-toggle="collapse" data-parent="#accordion" href="#solv">Solvència</a>
							      	</h4>
							    </div>
							    <div id="solv" class="panel-collapse collapse">					    	
						      		<div class="panel-body">        	               
					                	<h4 class="margin_bottom30">Solvència econòmica</h4>
					                	<c:if test="${empresa.solEconomica != null}">
						                	<div class="row">
						                		<label class="col-xs-2 control-label">Document:</label>
						                		<a target="_blanck" href="downloadFichero?ruta=${empresa.solEconomica.ruta}">${empresa.solEconomica.nom}</a>
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
						                		<a target="_blanck" href="downloadFichero?ruta=${empresa.solTecnica.ruta}">${empresa.solTecnica.nom}</a>
						                	</div>
						                </c:if>
						            </div>
						        </div>
						    </div>
						    <div class="panel panel-default">      
							    <div class="panel-heading">
							    	<h4 class="panel-title">
							        	<a data-toggle="collapse" data-parent="#accordion" href="#classificacio">Classificació</a>
							      	</h4>
							    </div>
							    <div id="classificacio" class="panel-collapse collapse">					    	
						      		<div class="panel-body">    
						      			<div class="row">
					                		<div class="col-md-10">
					                			<p>
						                        	<label>Darrera data vigència:</label> ${empresa.getDataVigenciaClassificacioString()}
						                        </p> 	                		
				                        	</div>
					                	</div>    	                
						                <div class="row">	                		
					                        <div class="col-xs-offset-2 col-md-7">	
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
								           	</div>									
					                	</div>
					                </div>
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
									                          	<input name="acreditacio1" type="checkbox" ${empresa.acreditacio1 ? 'checked' : ''} disabled> Certificat positiu de l'Agència Estatal d'Administració Tributària, 
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
									                          	<input name="acreditacio2" type="checkbox" ${empresa.acreditacio2 ? 'checked' : ''} disabled> Certificat de la Tresoreria General de la Seguretat Social del Ministeri
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
									                          	<input name="acreditacio3" type="checkbox" ${empresa.acreditacio3 ? 'checked' : ''} disabled> Certificat de la secretària de la Junta Consultiva de Contratació Administrativa
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
					                        <div class="col-xs-offset-2 col-md-7">	
												<label>Ofertes</label>							                        
								                <div class="table-responsive">							                        
								                    <table class="table table-striped table-bordered filerTable" id="ofertesTable">
								                        <thead>
								                            <tr>
								                                <th>Actuacio</th>
								                                <th>Detalls</th>
								                                <th>Centre</th>
								                                <th>Valor</th>
								                                <th>Data</th>				                                        							                                       
								                            </tr>
								                        </thead>
								                        <tbody>	
									                        <c:forEach items="${empresaOfertes}" var="oferta" >
													          	<tr class=${oferta.isSeleccionada() ? "success" : "warning"}>								          	
													           		<td><a href="actuacionsDetalls?ref=${oferta.idActuacio}">${oferta.idActuacio}</a></td>
													            	<td>${oferta.actuacio.descripcio}</td>
													            	<td>${oferta.actuacio.nomCentre}</td>
													            	<td>${oferta.getPlicFormat()}</td>
													            	<td>${oferta.getDataAprovacioString()}</td>
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
	               			<c:if test="${canModificar}">
								<div class="col-md-offset-9 col-md-2 margin_top30">
									<a href="editEmpresa?cif=${empresa.cif}" class="btn btn-primary" role="button">Modificar</a>
								</div>
							</c:if>
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