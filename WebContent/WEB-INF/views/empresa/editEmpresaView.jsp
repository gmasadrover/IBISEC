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
                	<div class="col-lg-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <!-- /.row -->
                <c:if test="${not empty empresa}">
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditEmpresa">                		
                		<h2 class="margin_bottom30">Informació bàsica</h2>
			    		<div class="row">			    				    				    		
		                    <div class="col-lg-6">		                    	
			    				<div class="form-group">
	                                <label class="col-xs-4 control-label">CIF</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" value="${empresa.cif}" disabled>
	                                	<input type="hidden" name="cif" value="${empresa.cif}">
	                                </div>
	                            </div>
	                            
	                            <div class="form-group">
	                                <label class="col-xs-4 control-label">Direcció</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="direccio" placeholder="direcció" value="${empresa.direccio}">
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
	                                <label class="col-xs-4 control-label">Teléfon</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="telefon" placeholder="123456789" value="${empresa.telefon}">
	                                </div>
	                            </div>		                            
	                            <div class="form-group">
	                                <label class="col-xs-4 control-label">email</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" type="email" name="email" placeholder="test@test.es" value="${empresa.email}">
	                                </div>
	                            </div>	                            
		                    </div>
		                    <div class="col-lg-6">
		                    	<div class="form-group">
	                                <label class="col-xs-3 control-label">Nom</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="name" placeholder="nom" value="${empresa.name}">
	                                </div>
	                            </div>
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
	                                <label class="col-xs-3 control-label">Data constitució</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="constitucio" value="${empresa.getDataConstitucioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>			                                                	
		                    </div>		            	
	                	</div>
	                	<div class="row">
	                		<div class="col-lg-12">
		                		<div class="form-group">
	                                <label class="col-xs-2  control-label">Objecte social</label>
	                                <div class="col-xs-8">
	                                	<textarea class="form-control" name="objSocial" placeholder="Objecte Social" rows="3">${empresa.objecteSocial}</textarea>
	                                </div>
	                            </div>
                            </div>
	                	</div>
	                	<h2 class="margin_bottom30">Representant</h2>
	                	<div class="row">
	                		<div class="col-lg-6">
		                		<div class="form-group">
	                                <label class="col-xs-4 control-label">Nom</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="nomAdmin" id="nomAdmin" placeholder="nom administrador" value="">
	                                </div>
	                            </div>	                            
	                            <div class="form-group">
	                                <label class="col-xs-4 control-label">Vàlid fins</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="validAdmin" id="validAdmin"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	                            
	                            <div class="form-group">
	                                <label class="col-xs-4 control-label">Notari</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="nomNotari" id="nomNotari" placeholder="nom Notari" value="">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-4 control-label">Número protocol</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="numProtocol" id="numProtocol" placeholder="xxxx" value="">
	                                </div>
	                            </div> 	                              
	                        </div>
	                        <div class="col-lg-6">
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
	                                <label class="col-xs-3 control-label">Data validació</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataValidacio" id="dataValidacio"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>
	                             <div class="form-group">
	                                <label class="col-xs-3 control-label">Organ validador</label>
	                                <div class="col-xs-6">
		                                <select class="form-control" name="organValidador" id="organValidador">
		                                	<option value="caib">Advocacia CAIB</option>
		                                	<option value="estat">Advocacia Estat</option>
		                                	<option value="ibisec">Assessoria jurídica IBISEC</option>
		                                </select>
		                             </div>
	                            </div>
	                            <div class="col-xs-offset-2">	 
	                            	<input class="btn btn-primary" type="button" name="afegirAdmin" id="afegirAdmin" value="Afegir">
	                            </div>     			
	                        </div>	
                        	<input type="hidden" name="llistatAdministradors" id="llistatAdministradors" value="${empresa.administradorsString}">                        
				     		<div class="col-lg-12">	
								<label>Administradors actius</label>							                        
				                <div class="table-responsive">							                        
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
				                                <th>Control</th>						                                        							                                       
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
								            	<td><input class='btn btn-danger btn-sm eliminarSeleccionada margin_left10' type='button' value='Eliminar'></td>					            	
								          	</tr>
							       		</c:forEach>						                                	                              	
				                        </tbody>
				                    </table>
				                </div>
				           	</div>									
	                	</div>
	                	<h2 class="margin_bottom30">Solvència</h2>
	                	<h4 class="margin_bottom30">Solvència econòmica</h4>
	                	<c:if test="${empresa.solEconomica != null}">
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Document:</label>
		                		<a  href="downloadFichero?ruta=${empresa.solEconomica.ruta}">${empresa.solEconomica.nom}</a>
		                	</div>
	                	</c:if>
	                	<div class="row">
	                		<label class="col-xs-2 control-label">Adjuntar solvència econòmica:</label>	                	
	                        <div class="col-xs-5">   
	                            <input type="file" class="btn" name="fileEconomica" /><br/>
							</div>
						</div> 
	                	<h4 class="margin_bottom30">Solvència Tècnica</h4>
	                	<c:if test="${empresa.solTecnica != null}">
		                	<div class="row">
		                		<label class="col-xs-2 control-label">Document:</label>
		                		<a  href="downloadFichero?ruta=${empresa.solTecnica.ruta}">${empresa.solTecnica.nom}</a>
		                	</div>
		                </c:if>
	                	<div class="row">
		                	<label class="col-xs-2 control-label">Adjuntar solvència tècnica:</label>
	                        <div class="col-xs-5">   
	                            <input type="file" class="btn" name="FileTecnica" /><br/>
							</div>
						</div> 
	                	<h4 class="margin_bottom30">Classificació</h4>
	                	<div class="row">
	                		<div class="col-lg-3">
		                		<div class="form-group">
	                                <label class="col-xs-8 control-label">Grup</label>
	                                <div class="col-xs-4">
	                                	<select class="form-control" name="grup" id="grupList">
	                                		<option value="A">A</option>
	                                		<option value="B">B</option>
	                                		<option value="C">C</option>
	                                		<option value="D">D</option>
	                                		<option value="E">E</option>
	                                		<option value="F">F</option>
	                                		<option value="G">G</option>
	                                		<option value="H">H</option>
	                                		<option value="I">I</option>
	                                		<option value="J">J</option>
	                                		<option value="K">K</option>
		                                </select>
	                                </div>
	                            </div>	
	                        </div>
	                        <div class="col-lg-3">
		                		<div class="form-group">
	                                <label class="col-xs-8 control-label">Subgrup</label>
	                                <div class="col-xs-4">
	                                	<select class="form-control" name="subGrup" id="subGrupList">
	                                		<option value="-">-</option>
	                                		<option value="1">1</option>
	                                		<option value="2">2</option>
	                                		<option value="3">3</option>
	                                		<option value="4">4</option>
	                                		<option value="5">5</option>
		                                </select>
	                                </div>
	                            </div>	
	                        </div>
	                        <div class="col-lg-3">
		                		<div class="form-group">
	                                <label class="col-xs-8 control-label">Categoria</label>
	                                <div class="col-xs-4">
	                                	<select class="form-control" name="categoria" id="categoriaList">
	                                		<option value="A">A</option>
	                                		<option value="B">B</option>
	                                		<option value="C">C</option>
	                                		<option value="D">D</option>
	                                		<option value="E">E</option>
	                                		<option value="F">F</option>
	                                		<option value="1">1</option>
	                                		<option value="2">2</option>
	                                		<option value="3">3</option>
	                                		<option value="4">4</option>
	                                		<option value="5">5</option>
	                                		<option value="6">6</option>
		                                </select>
	                                </div>
	                            </div>	
	                        </div>
	                        <div class="col-lg-3">
		                		<div class="form-group">
	                                <input class="btn btn-primary" type="button" name="afegirClassificacio" id="afegirClassificacio" value="Afegir">
	                            </div>	
	                        </div>
                        	<input type="hidden" name="llistatClassificacio" id="llistatClassificacio" value="${empresa.classificacioString}">                        
				     		<div class="col-xs-offset-2 col-lg-7">	
								<label>Classificació</label>							                        
				                <div class="table-responsive">							                        
				                    <table class="table table-striped table-bordered filerTable" id="classificacioTable">
				                        <thead>
				                            <tr>
				                                <th>Grup</th>
				                                <th>Subgrup</th>
				                                <th>Categoria</th>
				                                <th>Control</th>					                                        							                                       
				                            </tr>
				                        </thead>
				                        <tbody>	
					                        <c:forEach items="${empresa.getClassificacio()}" var="classificacio" >
									          	<tr>							          	
									           		<td>${classificacio.grup}</td>
									            	<td>${classificacio.subGrup}</td>
									            	<td>${classificacio.categoria}</td>
									            	<td><input class='btn btn-danger btn-sm eliminarClassificacioSeleccionada margin_left10' type='button' value='Eliminar'></td>					            	
									          	</tr>
								       		</c:forEach>							                                	                              	
				                        </tbody>
				                    </table>
				                </div>
				           	</div>									
	                	</div> 
	                	<h2 class="margin_bottom30">Acreditació d'obligacions fiscals i de seguretat social</h2>
	                	<div class="row">
	                		<div class="col-lg-12">
	                			<div class="form-group">
	                				<div class="col-xs-offset-1 col-lg-10">
		                				<div class="checkbox">
					                        <label>
					                          	<input name="acreditacio1" type="checkbox" ${empresa.acreditacio1 ? 'checked' : ''}> Certificat positiu de l'Agència Estatal d'Administració Tributària, 
					                          	d'estar al corrent en el comliment de les seves obligacions tributàries amb l'Estat.
					                        </label>
					                	</div>
					                </div>
	                			</div>
	                			<div class="col-lg-6">
		                			<div class="form-group">
		                                <label class="col-xs-6 control-label">Expedit amb data</label>
		                                <div class="input-group date col-xs-4 datepicker">
										  	<input type="text" class="form-control" name="dateExpAcreditacio1" value="${empresa.getDateExpAcreditacio1String()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
										</div>				
		                            </div>
		                        </div>
		                        <div class="col-lg-6">
		                			<div class="form-group">		                                
										<c:if test="${empresa.isCaducadaAcreditacio1()}">
											<label class="col-xs-1 control-label">Caducat</label>
										</c:if>								
		                            </div>
		                        </div>      
	                			<div class="form-group">
	                				<div class="col-xs-offset-1 col-lg-10">
		                				<div class="checkbox">
					                        <label>
					                          	<input name="acreditacio2" type="checkbox" ${empresa.acreditacio2 ? 'checked' : ''}> Certificat de la Tresoreria General de la Seguretat Social del Ministeri
					                          	d'Ocupació i Seguretat Social, de què l'empresa està al corren en el compliment de les obligacions
					                          	de pagamanet de la Seguretat Social.
					                        </label>
					                	</div>
					                </div>
	                			</div>
	                			<div class="col-lg-6">
		                			<div class="form-group">
		                                <label class="col-xs-6 control-label">Expedit amb data</label>
		                                <div class="input-group date col-xs-4 datepicker">
										  	<input type="text" class="form-control" name="dateExpAcreditacio2" value="${empresa.getDateExpAcreditacio2String()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
										</div>
		                            </div>
		                        </div>
		                        <div class="col-lg-6">
		                			<div class="form-group">		                                
										<c:if test="${empresa.isCaducadaAcreditacio2()}">
											<label class="col-xs-1 control-label">Caducat</label>
										</c:if>								
		                            </div>
		                        </div>        
	                			<div class="form-group">
	                				<div class="col-xs-offset-1 col-lg-10">
		                				<div class="checkbox">
					                        <label>
					                          	<input name="acreditacio3" type="checkbox" ${empresa.acreditacio3 ? 'checked' : ''}> Certificat de la secretària de la Junta Consultiva de Contratació Administrativa
					                          	de què l'empresa no té deutes de naturalesa tributària amb la Comunitat Autònoma de les Illes Balears, en
					                          	via de constrenyiment.
					                        </label>
					                	</div>
					                </div>
	                			</div>
	                			<div class="col-lg-6">
		                			<div class="form-group">
		                                <label class="col-xs-6 control-label">Expedit amb data</label>
		                                <div class="input-group date col-xs-4 datepicker">
										  	<input type="text" class="form-control" name="dateExpAcreditacio3" value="${empresa.getDateExpAcreditacio3String()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
										</div>
		                            </div>
		                        </div>
		                        <div class="col-lg-6">
		                			<div class="form-group">		                                
										<c:if test="${empresa.isCaducadaAcreditacio3()}">
											<label class="col-xs-1 control-label">Caducat</label>
										</c:if>								
		                            </div>
		                        </div>        
	                		</div>
	                	</div>
	                	<h2 class="margin_bottom30">Altra informació</h2>
	                	<div class="row">
	                		<div class="col-lg-12">
		                		<div class="form-group">
	                                <label class="col-xs-2  control-label">informació adicional</label>
	                                <div class="col-xs-8">
	                                	<textarea class="form-control" name="informacioAdicional" placeholder="Informació Adicional" rows="3">${empresa.informacioAdicional}</textarea>
	                                </div>
	                            </div>
                            </div>	                		
	                	</div>	    	                	        	              	
	                	<div class="row">
	                		<div class="form-group">
						        <div class="col-xs-offset-9 col-xs-9">
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
    <script src="js/empresa/modificar.js"></script>
    <script src="js/zones/zones.js"></script>
</body>
</html>