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
                            Empresa <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Empresa
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
                <c:if test="${not empty empresa}">
                	<div class="row">
	                	<div class="col-md-12">
	               			<p style="color: red;">
								<c:choose>
	               					<c:when test="${empresa.prohibicioContractar}">
										Aquesta empresa t?? prohibida la contrataci??. Fins ${empresa.getProhibitContractarFinsString()}
										<br/>
										<c:forEach items="${empresa.documentsProhibicioContractarList}" var="arxiu" >
				                			<c:set var="arxiu" value="${arxiu}" scope="request"/>
						            		<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
										</c:forEach>										
									</c:when>
	               					<c:when test="${!empresa.activa}">
										Aquesta empresa est?? extingida.
										<c:if test="${empresa.succesora.name.isEmpty()}">
											<br/>La seva succesora ??s: ${empresa.succesora.name} (${empresa.succesora.cif})
										</c:if>
									</c:when>
	               				</c:choose>	
							</p>
	               		</div>
	               	 </div>
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditEmpresa">                		
                		<h2 class="margin_bottom30">Informaci?? b??sica</h2>
			    		<div class="row">			    				    				    		
		                    <div class="col-md-6">		                    	
			    				<div class="form-group">
	                                <label class="col-xs-4 control-label">CIF</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="newcif" value="${empresa.cif}" ${empresa.cif == empresa.name ? '' : 'disabled'}>
	                                	<input type="hidden" name="cif" value="${empresa.cif}">
	                                	<input type="hidden" name="isute" value="${empresa.isUte()}">	                                	
	                                </div>
	                            </div>
	                            <c:if test="${! empresa.isUte()}">
		                            <div class="form-group">
		                                <label class="col-xs-4 control-label">Direcci??</label>
		                                <div class="col-xs-6">
		                                	<input class="form-control" name="direccio" placeholder="direcci??" value="${empresa.direccio}">
		                                </div>
		                            </div>
		                                                       	
		                            <div class="form-group">
		                                <label class="col-xs-4  control-label">Provincia</label>
		                                <input  type="hidden" class="provinciaSelected" value="${empresa.provincia}">
		                                <div class="col-xs-6">
			                                <select class="form-control" name="provincia" id="provinciesList">
			                                </select>
			                             </div>
		                            </div>		                            
		                            <div class="form-group">
		                                <label class="col-xs-4 control-label">Tel??fon</label>
		                                <div class="col-xs-6">
		                                	<input class="form-control" name="telefon" placeholder="123456789" value="${empresa.telefon}">
		                                </div>
		                            </div>		                            
		                            <!-- <div class="form-group"> 
		                                <label class="col-xs-4 control-label">email</label>
		                                <div class="col-xs-6">
		                                	<input class="form-control" type="email" name="email" placeholder="test@test.es" value="${empresa.email}">
		                                </div>
		                            </div> --> <!--Ocult a petici?? M.Garcia en data 10/11/21--> 
		                            <div class="form-group">
		                				<label class="col-xs-4 control-label">PIME</label>
		                				<div class="col-xs-6">
			                				<div class="checkbox">
						                        <input name="pime" type="checkbox" ${empresa.isPime() ? 'checked' : ''}>
						                	</div>
						                </div>
		                			</div>
		                    	</c:if>                     
		                    </div>
		                    <div class="col-md-6">
		                    	<div class="form-group">
	                                <label class="col-xs-3 control-label">Nom</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="name" placeholder="nom" value="${empresa.name}">
	                                </div>
	                            </div>
	                            <c:if test="${! empresa.isUte()}">
		                            <div class="form-group">
		                                <label class="col-xs-3 control-label">CP</label>
		                                <div class="col-xs-6">
		                                	<input class="form-control" name="cp" placeholder="00000" value="${empresa.getCP()}">
		                                </div>
		                            </div>	 
		                            <div class="form-group">                            
		                                <label class="col-xs-3 control-label">Localitat</label>
		                                <input  type="hidden" class="localitatSelected" value="${empresa.ciutat}">
		                                <div class="col-xs-6">
			                                <select class="form-control" name="localitat" id="localitatsList">
			                                </select>
			                             </div>
		                            </div>	
		                            <div class="form-group">
		                                <label class="col-xs-3 control-label">Fax</label>
		                                <div class="col-xs-6">
		                                	<input class="form-control" name="fax" placeholder="123456789" value="${empresa.fax}">
		                                </div>
		                            </div>
		                            <div class="form-group">
		                                <label class="col-xs-3 control-label">Data constituci??</label>
		                                <div class="input-group date col-xs-6 datepicker">
										  	<input type="text" class="form-control" name="constitucio" value="${empresa.getDataConstitucioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
										</div>
		                            </div>
		                            <div class="form-group">
		                                <label class="col-xs-3 control-label">Tipus</label>
		                                <div class="col-xs-6">
		                                	<input class="form-control" name="tipus" value="${empresa.tipus}">
		                                </div>
		                            </div>
		                    	</c:if>			                                                	
		                    </div>		            	
	                	</div>	                	
	                	<div class="row">
	                		<div class="col-md-12">
		                		<div class="form-group">			                	
				                	<div class="row">
				                		<label class="col-xs-2 control-label">Document:</label>
				                		<c:forEach items="${empresa.documentsEscrituraList}" var="arxiu" >
				                			<c:set var="arxiu" value="${arxiu}" scope="request"/>
						            		<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
										</c:forEach>	
				                	</div>
				                	<div class="row">
				                		<label class="col-xs-2 control-label">Adjuntar escritura:</label>	                	
				                        <div class="col-xs-5">   
				                            <input type="file" class="btn fileEscritura" name="fileEscritura" /><br/>
										</div>						
									</div> 	                      
	                            </div>
                            </div>
	                	</div>
	                	<div class="row">
	                		<div class="col-md-12">
		                		<div class="form-group">			                	
				                	<div class="row">
				                		<label class="col-xs-2 control-label">Documentaci?? acreditativa d'estar d'alta com a constructor(REA, o ??ltim rebut d'aut??nom i model 036 0 037):</label>
				                		<c:if test="${empresa.documentREA.getEncodedRuta() != ''}">
						            		<a target="_blanck" href="downloadFichero?ruta=${empresa.documentREA.getEncodedRuta()}">${empresa.documentREA.nom}</a>
											<a href="#"><span data-ruta="${empresa.documentREA.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
											<br>
										</c:if>	
				                	</div>
				                	<div class="row">
				                		<label class="col-xs-2 control-label">Adjuntar documentaci??:</label>	                	
				                        <div class="col-xs-5">   
				                            <input type="file" class="btn fileREA" name="fileREA" /><br/>
										</div>							
									</div> 	                      
	                            </div>
                            </div>
	                	</div>
	                	<c:if test="${canViewDadesBancaries}">
		                	<div class="row">
		                		<div class="col-md-12">
			                		<div class="form-group">			                	
					                	<div class="row">
					                		<label class="col-xs-2 control-label">IBAN certificat:</label>
					                		<c:forEach items="${empresa.documentsBancList}" var="arxiu" >
					                			<c:set var="arxiu" value="${arxiu}" scope="request"/>
							            		<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
											</c:forEach>	
					                	</div>
					                	<div class="row">
					                		<label class="col-xs-2 control-label">Adjuntar IBAN certificat:</label>	                	
					                        <div class="col-xs-5">   
					                            <input type="file" class="btn fileIBAN" name="fileIBAN" /><br/>
											</div>					
										</div> 	                      
		                            </div>
	                            </div>
		                	</div>
		                </c:if>
		                <c:choose>
		                 	<c:when test="${empresa.isUte()}"> 
	                			<h2 class="margin_bottom30">Gerents</h2>
	                		</c:when>
	                		<c:otherwise>
	                			<h2 class="margin_bottom30">Representaci??</h2>
	                		</c:otherwise>
	                	</c:choose>	     		                 
	                	<div class="row">
	                		<div class="col-md-6">
		                		<div class="form-group">
	                                <label class="col-xs-3 control-label">Nom</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="nomAdmin" id="nomAdmin" placeholder="nom administrador" value="">
	                                </div>
	                            </div>	                            
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">V??lid fins</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="validAdmin" id="validAdmin"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	                            
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Notari</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="nomNotari" id="nomNotari" placeholder="nom Notari" value="">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">N??mero protocol</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="numProtocol" id="numProtocol" placeholder="xxxx" value="">
	                                </div>
	                            </div> 	
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Organ validador</label>
	                                <div class="col-xs-6">
		                                <select class="form-control" name="organValidador" id="organValidador">
		                                	<option value="">--</option>
		                                	<option value="caib">Advocacia CAIB</option>
		                                	<option value="estat">Advocacia Estat</option>
		                                	<option value="ibisec">Assessoria jur??dica IBISEC</option>
		                                </select>
		                             </div>
	                            </div>                              
	                        </div>
	                        <div class="col-md-6">
	                        	<div class="form-group">
	                                <label class="col-xs-3 control-label">DNI</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="dniAdmin" id="dniAdmin" placeholder="12345678A" value="">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Tipus</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="tipusAdmin" id="tipusAdmin" value="">
	                                </div>
	                            </div> 
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data escriptura</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataAlta" id="dataAlta"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data validaci??</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataValidacio" id="dataValidacio"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	
	                            <div class="form-group">
			                		<label class="col-xs-2 control-label">Adjuntar document:</label>	                	
			                        <div class="col-xs-5">   
			                            <input type="file" class="btn fileAdministrador" name="fileAdministrador" /><br/>
									</div>							
								</div> 	
	                        </div>
	                        <div class="row">	
	                        	<div class="col-md-offset-10">	 
	                            	<input class="btn btn-primary" type="button" data-cif="${empresa.cif}" name="afegirAdmin" id="afegirAdmin" value="Afegir">
	                            </div> 
	                        </div>  
                        	<input type="hidden" name="llistatAdministradors" id="llistatAdministradors" value="${empresa.administradorsString}">                        
				     		<div class="col-md-12">	
								<label>Administradors actius</label>							                        
				                <div class="table-responsive">							                        
				                    <table class="table table-striped table-bordered filerTable" id="administradorsTable">
				                        <thead>
				                            <tr>
				                                <th>Nom</th>
				                                <th>Tipus</th>
				                                <th>V??lid fins</th>
				                                <th>Notari</th>
				                                <th>N. protocol</th>
				                                <th>Data</th>
				                                <th>Validacio</th>
				                                <th>Organ validaci??</th>
				                                <th>Documentaci??</th>	
				                               	<th>Control</th>				                                        							                                       
				                            </tr>
				                        </thead>
				                        <tbody>
										<c:forEach items="${empresa.administradors}" var="administrador" >
								          	<tr class="${administrador.isCaducat() ? 'danger' : '' }">							          	
								           		<td><a target="_blanck" href="editAdministrador?empresa=${empresa.cif}&administrador=${administrador.dni}">${administrador.nom}</a></td>
								            	<td>${administrador.tipus}</td>
								            	<td>${administrador.getDataValidesaFinsString()}</td>
								            	<td>${administrador.notariModificacio}</td>
								            	<td>${administrador.protocolModificacio}</td>
								            	<td>${administrador.getDataModificacioString()}</td>
								            	<td>${administrador.getDataValidacioString()}</td>
								            	<td>${administrador.entitatValidacio}</td>
								            	<td><a target="_blanck" href="downloadFichero?ruta=${administrador.documentAdministrador.getEncodedRuta()}">${administrador.documentAdministrador.nom}</a></td>
								            	<td></td>
								            </tr>
							       		</c:forEach>						                                	                              	
				                        </tbody>
				                    </table>
				                </div>
				           	</div>									
	                	</div>
		                <c:if test="${! empresa.isUte()}"> 
		                	<h2 class="margin_bottom30">Solv??ncia</h2>
		                	<h4 class="margin_bottom30">Solv??ncia econ??mica</h4>
		                	<c:if test="${empresa.solEconomica.ruta != null}">
			                	<div class="row">
			                		<label class="col-xs-2 control-label">Document:</label>
			                		<a  target="_blanck" href="downloadFichero?ruta=${empresa.solEconomica.getEncodedRuta()}">${empresa.solEconomica.nom}</a>
			                		<span data-ruta="${empresa.solEconomica.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			                	</div>	                	
		                	</c:if>
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Adjuntar solv??ncia econ??mica:</label>	                	
		                        <div class="col-xs-5">   
		                            <input type="file" class="btn" name="fileEconomica" /><br/>
								</div>
							</div> 
							<div class="row">
		                		<label class="col-xs-2 control-label">Exercici</label>
	                            <div class="input-group date col-xs-2 datepicker">
								  	<input type="text" class="form-control" name="dataExerciciEconomic" id="dataExerciciEconomic" value="${empresa.getExerciciEconomicString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
		                	</div>
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Dipositat en Registre Mercantil amb data</label>
	                            <div class="input-group date col-xs-2 datepicker">
								  	<input type="text" class="form-control" name="dataRegistreMercantil" id="dataRegistreMercantil" value="${empresa.getRegistreMercantilDataString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>	                		
		                	</div>
		                	<div class="row">
		                		<div class="form-group">
	                                <label class="col-xs-2 control-label">R??tio A/P</label>
	                                <div class="col-xs-2">
	                                	<input class="form-control" name="ratioAP" id="ratioAP" value="${empresa.getRatioAP()}">
	                                </div>
	                            </div>
	                        </div>
		                	<h4 class="margin_bottom30">Solv??ncia T??cnica</h4>
		                	<c:if test="${empresa.solTecnica.ruta != null}">
			                	<div class="row">
			                		<label class="col-xs-2 control-label">Document:</label>
			                		<a target="_blanck" href="downloadFichero?ruta=${empresa.solTecnica.getEncodedRuta()}">${empresa.solTecnica.nom}</a>
			                		<span data-ruta="${empresa.solTecnica.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			                	</div>
			                </c:if>
		                	<div class="row">
			                	<label class="col-xs-2 control-label">Adjuntar solv??ncia t??cnica:</label>
		                        <div class="col-xs-5">   
		                            <input type="file" class="btn" name="FileTecnica" /><br/>
								</div>
							</div> 
		                	<h2 class="margin_bottom30">Classificacio</h2>
		                	<c:if test="${empresa.classificacioFileROLECE.ruta != null}">
			                	<div class="row">
			                		<label class="col-xs-2 control-label">Document ROLECE:</label>
			                		<div class="col-xs-5">
			                			<a target="_blanck" href="downloadFichero?ruta=${empresa.classificacioFileROLECE.getEncodedRuta()}">${empresa.classificacioFileROLECE.nom}</a>
			                			<span data-ruta="${empresa.classificacioFileROLECE.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			                		</div>
			                	</div>	                	
		                	</c:if>
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Adjuntar classificaci?? ROLECE:</label>	                	
		                        <div class="col-xs-5">   
		                            <input type="file" class="btn" name="classificacioROLECE" /><br/>
								</div>
							</div> 
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Darrera data vig??ncia ROLECE</label>
	                            <div class="input-group date col-xs-2 datepicker">
								  	<input type="text" class="form-control" name="dataVigenciaClassificacioROLECE" id="dataVigenciaClassificacio" value="${empresa.getDataVigenciaClassificacioROLECEString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>	                		
		                	</div>
		                	</br>
		                	<c:if test="${empresa.classificacioFileJCCaib.ruta != null}">
			                	<div class="row">
			                		<label class="col-xs-2 control-label">Document JCCaib:</label>
			                		<div class="col-xs-5">
			                			<a target="_blanck" href="downloadFichero?ruta=${empresa.classificacioFileJCCaib.getEncodedRuta()}">${empresa.classificacioFileJCCaib.nom}</a>
			                			<span data-ruta="${empresa.classificacioFileJCCaib.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			                		</div>
			                	</div>	                	
		                	</c:if>
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Adjuntar classificaci?? JCCaib:</label>	                	
		                        <div class="col-xs-5">   
		                            <input type="file" class="btn" name="classificacioJCCaib" /><br/>
								</div>
							</div> 
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Darrera data vig??ncia JCCaib</label>
	                            <div class="input-group date col-xs-2 datepicker">
								  	<input type="text" class="form-control" name="dataVigenciaClassificacioJCCaib" id="dataVigenciaClassificacio" value="${empresa.getDataVigenciaClassificacioJCCaibString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>	                		
		                	</div>
		                	</br>
		                	<c:if test="${empresa.classificacioFileJCA.ruta != null}">
			                	<div class="row">
			                		<label class="col-xs-2 control-label">Document JCA:</label>
			                		<div class="col-xs-5">
			                			<a target="_blanck" href="downloadFichero?ruta=${empresa.classificacioFileJCA.getEncodedRuta()}">${empresa.classificacioFileJCA.nom}</a>
			                			<span data-ruta="${empresa.classificacioFileJCA.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			                		</div>
			                	</div>	                	
		                	</c:if>
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Adjuntar classificaci?? JCA:</label>	                	
		                        <div class="col-xs-5">   
		                            <input type="file" class="btn" name="classificacioJCA" /><br/>
								</div>
							</div> 
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Darrera data vig??ncia JCA</label>
	                            <div class="input-group date col-xs-2 datepicker">
								  	<input type="text" class="form-control" name="dataVigenciaClassificacioJCA" id="dataVigenciaClassificacio" value="${empresa.getDataVigenciaClassificacioJCAString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>	                		
		                	</div>		                
		                	<h2 class="margin_bottom30">Acreditaci?? d'obligacions fiscals i de seguretat social</h2>
		                	<div class="row">
		                		<div class="col-md-12">
		                			<div class="form-group">
		                				<div class="col-xs-offset-1 col-md-10">
					                   		<p>Certificat de l'Ag??ncia Estatal d'Administraci?? Tribut??ria, 
					                          	d'estar al corrent en el comliment de les seves obligacions tribut??ries amb l'Estat.</p>
						                </div>
		                			</div>
		                			<div class="form-group">
			                			<div class="col-xs-offset-1 col-md-10">			                				
				                			<div class="col-md-4">
				                                <label class="col-xs-6 control-label">Expedit amb data</label>
				                                <div class="input-group date col-xs-4 datepicker">
												  	<input type="text" class="form-control" name="dateExpAcreditacio1" value="${empresa.getDateExpAcreditacio1String()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
												</div>	
					                        </div>
					                        <div class="col-md-4">
				                				<label class="col-xs-4 control-label">NEGATIU</label>
				                				<div class="col-xs-2">
					                				<div class="checkbox">
								                        <input name="acreditacio1negatiu" type="checkbox" ${empresa.isNegativaAcreditacio1() ? 'checked' : ''}>
								                	</div>
								                </div>
				                			</div>
					                        <div class="col-md-4">		                                
												<c:if test="${empresa.isCaducadaAcreditacio1()}">
													<label><p style="color: red;">Caducat</p></label>
												</c:if>		
					                        </div>    
				                        </div>  
				                    </div>
		                			<div class="form-group">
		                				<div class="col-xs-offset-1 col-md-10">
			                				<p>Certificat de la Tresoreria General de la Seguretat Social del Ministeri
						                          	d'Ocupaci?? i Seguretat Social, de qu?? l'empresa est?? al corren en el compliment de les obligacions
						                          	de pagamanet de la Seguretat Social.</p>
						                </div>
						               
		                			</div>
		                			<div class="form-group">
			                			<div class="col-xs-offset-1 col-md-10">			                				
				                			<div class="col-md-4">
			                                	<label class="col-xs-6 control-label">Expedit amb data</label>
				                                <div class="input-group date col-xs-4 datepicker">
												  	<input type="text" class="form-control" name="dateExpAcreditacio2" value="${empresa.getDateExpAcreditacio2String()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
												</div>
			                            	</div>
			                            	<div class="col-md-4">
				                				<label class="col-xs-4 control-label">NEGATIU</label>
				                				<div class="col-xs-2">
					                				<div class="checkbox">
								                        <input name="acreditacio2negatiu" type="checkbox" ${empresa.isNegativaAcreditacio2() ? 'checked' : ''}>
								                	</div>
								                </div>
				                			</div>
					                        <div class="col-md-4">		                                
												<c:if test="${empresa.isCaducadaAcreditacio2()}">
													<label><p style="color: red;">Caducat</p></label>
												</c:if>		
					                        </div>   
			                        	</div>
			                        </div>
			                        
		                			<div class="form-group">
		                				<div class="col-xs-offset-1 col-md-10">
			                				<p>Certificat de la secret??ria de la Junta Consultiva de Contrataci?? Administrativa
						                          	de qu?? l'empresa no t?? deutes de naturalesa tribut??ria amb la Comunitat Aut??noma de les Illes Balears, en
						                          	via de constrenyiment.</p>
						                </div>
		                			</div>
		                			<div class="form-group">
			                			<div class="col-xs-offset-1 col-md-10">			                				
				                			<div class="col-md-4">
			                                	<label class="col-xs-6 control-label">Expedit amb data</label>
				                                <div class="input-group date col-xs-4 datepicker">
												  	<input type="text" class="form-control" name="dateExpAcreditacio3" value="${empresa.getDateExpAcreditacio3String()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
												</div>
			                           		</div>
			                           		<div class="col-md-4">
				                				<label class="col-xs-4 control-label">NEGATIU</label>
				                				<div class="col-xs-2">
					                				<div class="checkbox">
								                        <input name="acreditacio3negatiu" type="checkbox" ${empresa.isNegativaAcreditacio3() ? 'checked' : ''}>
								                	</div>
								                </div>
				                			</div>
					                        <div class="col-md-4">		                                
												<c:if test="${empresa.isCaducadaAcreditacio3()}">
													<label><p style="color: red;">Caducat</p></label>
												</c:if>		
					                        </div>   
			                       		</div> 
			                       	</div>      
		                		</div>
		                	</div>
		                </c:if>
		                <c:if test="${empresa.isUte()}">
		                	<h2 class="margin_bottom30">Empreses</h2>
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
					    </c:if>
	                	<h2 class="margin_bottom30">Altra informaci??</h2>
	                	<div class="row">
	                		<div class="col-md-12">
		                		<div class="form-group">
	                                <label class="col-xs-2  control-label">informaci?? adicional</label>
	                                <div class="col-xs-8">
	                                	<textarea class="form-control" name="informacioAdicional" placeholder="Informaci?? Adicional" rows="3">${empresa.informacioAdicional}</textarea>
	                                </div>
	                            </div>
                            </div>	                		
	                	</div>	
	                	<h2 class="margin_bottom30">Modificaci?? estat empresa</h2>
	                	<div class="row">
	                		<h4>Prohibici?? contractar</h4>
	                		<c:if test="${empresa.documentsProhibicioContractarList.size() > 0}">			                	   
			                	<c:forEach items="${empresa.documentsProhibicioContractarList}" var="arxiu" >
		                			<c:set var="arxiu" value="${arxiu}" scope="request"/>
				            		<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:forEach>	         	
		                	</c:if>	                		
	                		<div class="form-group">	                		
	                			<label class="col-xs-2 control-label">Document:</label>	                	
		                        <div class="col-xs-5">   
		                            <input type="file" class="btn" name="documentProhibicioContractar" /><br/>
								</div>
							</div>
							<div class="form-group">
	                			<label class="col-md-3 control-label">Prohibici?? contractar fins:</label>
                                <div class="input-group date col-md-2 datepicker">
								  	<input type="text" class="form-control" name="dateLimitProhibicio" value="${empresa.getProhibitContractarFinsString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							</div> 
							<div class="form-group">	                            
						       	<div class="col-xs-offset-8 col-xs-4">
						            <input type="submit" class="btn btn-danger" name="prohibicio" value="Prohibici?? contractar">							            
						        </div>													        
						    </div>
	                	</div>	
	                	<div class="row">
	                		<h4>En concurs</h4>
	                		<c:if test="${empresa.documentsConcursList.size() > 0}">			                	   
			                	<c:forEach items="${empresa.documentsConcursList}" var="arxiu" >
		                			<c:set var="arxiu" value="${arxiu}" scope="request"/>
				            		<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:forEach>	         	
		                	</c:if>	                		
	                		<div class="form-group">	                		
	                			<label class="col-xs-2 control-label">Document:</label>	                	
		                        <div class="col-xs-5">   
		                            <input type="file" class="btn" name="documentConcurs" /><br/>
								</div>
							</div>
							<div class="form-group">
	                			<label class="col-md-3 control-label">Data concurs:</label>
                                <div class="input-group date col-md-2 datepicker">
								  	<input type="text" class="form-control" name="dateConcurs" value="${empresa.getDataConcursString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							</div> 
							<div class="form-group">
	                			<label class="col-xs-2  control-label">Informaci??</label>
	                			<div class="col-md-6">
	                                	<textarea class="form-control" name="infoConcurs" placeholder="Informaci??" rows="3">${empresa.infoConcurs}</textarea>
	                            </div>						       					        
						    </div> 
						    <div class="form-group">
	                			<div class="col-xs-6">
	                				<div class="checkbox">
				                        <input name="intervencio" type="checkbox" ${empresa.isIntervenida() ? 'checked' : ''}>
				                	</div>
				                </div>					       					        
						    </div> 
						    <div class="form-group">
	                			<label class="col-xs-2  control-label">Informaci?? intervenci??</label>
	                			<div class="col-md-6">
	                                	<textarea class="form-control" name="infoIntervencio" placeholder="Informaci??" rows="3">${empresa.infoIntervencio}</textarea>
	                            </div>						       					        
						    </div> 
							<div class="form-group">	                            
						       	<div class="col-xs-offset-8 col-xs-4">
						            <input type="submit" class="btn btn-danger" name="concurs" value="Actualitzar Concurs">							            
						        </div>													        
						    </div>
	                	</div>  	
	                	<div class="row">
	                		<h4>Extinci??</h4>
	                		<c:if test="${empresa.extincioFile.ruta != null}">
			                	<div class="form-group">	   
			                		<label class="col-xs-2 control-label">Escritura:</label>
			                		<div class="col-xs-5">
			                			<a target="_blanck" href="downloadFichero?ruta=${empresa.extincioFile.getEncodedRuta()}">${empresa.extincioFile.nom}</a>
			                			<span data-ruta="${empresa.extincioFile.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			                		</div>    
			                	</div>            	
		                	</c:if>	                		
	                		<div class="form-group">	                		
	                			<label class="col-md-2 control-label">Escritura:</label>	                	
		                        <div class="col-md-5">   
		                            <input type="file" class="btn" name="documentextincio" /><br/>
								</div>
							</div>
	                		<div class="form-group">
	                			<label class="col-xs-2  control-label">Motiu</label>
	                			<div class="col-md-6">
	                                	<textarea class="form-control" name="motiuextincio" placeholder="Motiu extinci??" rows="3">${empresa.motiuExtincio}</textarea>
	                            </div>
						        <div class="col-md-3">
						            <input type="submit" class="btn btn-danger" name="extincio" value="Extinci??">							            
						        </div>						        
						    </div> 
	                	</div>  	        
	                	<div class="row">
	                		<h4>Successi??</h4>
	                		<c:if test="${empresa.succesoraFile.ruta != null}">
			                	<div class="form-group">	   
			                		<label class="col-xs-2 control-label">Escritura:</label>
			                		<div class="col-xs-5">
			                			<a target="_blanck" href="downloadFichero?ruta=${empresa.succesoraFile.getEncodedRuta()}">${empresa.succesoraFile.nom}</a>
			                			<span data-ruta="${empresa.succesoraFile.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			                		</div>    
			                	</div>            	
		                	</c:if>	   
	                		<div class="form-group">	                		
	                			<label class="col-md-2 control-label">Escritura:</label>	                	
		                        <div class="col-md-5">   
		                            <input type="file" class="btn" name="documentsuccessio" /><br/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group">	  
								<label class="col-md-2 control-label">Empresa</label>
                                <div class="col-md-6">
	                                <select class="form-control" name="cifsuccesora" id="cifsuccesora" data-live-search="true" data-size="10">
	                                	<c:forEach items="${empresesList}" var="succesor">
	                                		<c:if test="${succesor.activa}">
					                   			<option value="${succesor.cif}">${succesor.name}</option>
					                   		</c:if>
					                   	</c:forEach>	
	                                </select>
	                             </div>
						        <div class="col-md-3">
						            <input type="submit" class="btn btn-warning" name="succecio" value="Succeci??">							            
						        </div>
						    </div> 
						</div> 	                	        	              	
	                	<div class="row">
	                		<div class="form-group">
						        <div class="col-xs-offset-9 col-xs-3">
						            <input type="submit" class="btn btn-primary" value="Guardar Canvis">							            
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
    <script src="js/empresa/modificar.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>