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
               		<h2 class="margin_bottom30">Informació bàsica</h2>
		    		<div class="row">			    				    				    		
	                    <div class="col-xs-offset-1 col-lg-5">
	    					<p>
								<label>CIF:</label> ${empresa.cif}
							</p>
                            <input type="hidden" name="cif" value="${empresa.cif}">
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
	                  	</div>
		             	<div class="col-xs-offset-1 col-lg-5">
		             		<p> 
	                        	<label>Nom: </label> ${empresa.name}
                            </p> 
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
	                    </div>		            	
                	</div>
                	<div class="row">
                		<div class="col-xs-offset-1 col-lg-10 longText">
                			<p> 
	                        	<label>Objecte social: </label> 
	                        	${empresa.objecteSocial}
                            </p>	                		
                        </div>
                	</div>
                	<h2 class="margin_bottom30">Administradors</h2>
                	<div class="row">
	                	<div class="table-responsive col-xs-offset-1 col-lg-10">							                        
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
						            </tr>
					       		</c:forEach>						                                	                              	
		                        </tbody>
		                    </table>
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
                	<h4 class="margin_bottom30">Solvència Tècnica</h4>
                	<c:if test="${empresa.solTecnica != null}">
	                	<div class="row">
	                		<label class="col-xs-2 control-label">Document:</label>
	                		<a  href="downloadFichero?ruta=${empresa.solTecnica.ruta}">${empresa.solTecnica.nom}</a>
	                	</div>
	                </c:if>                	
                	<h4 class="margin_bottom30">Classificació</h4>
	                <div class="row">	                		
                        <div class="col-xs-offset-2 col-lg-7">	
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
	                <h2 class="margin_bottom30">Acreditació d'obligacions fiscals i de seguretat social</h2>
	                <div class="row">
                		<div class="col-lg-12">
                			<div class="form-group">
                				<div class="col-xs-offset-1 col-lg-10">
	                				<div class="checkbox">
				                        <label>
				                          	<input name="acreditacio1" type="checkbox" ${empresa.acreditacio1 ? 'checked' : ''} disabled> Certificat positiu de l'Agència Estatal d'Administració Tributària, 
				                          	d'estar al corrent en el comliment de les seves obligacions tributàries amb l'Estat.
				                        </label>
				                	</div>
				                </div>
                			</div>
                			<div class="col-xs-offset-2 col-lg-4">
	                			<div class="form-group">
	                                <div class="form-group">
		                                <label>Expedit amb data: </label> ${empresa.getDateExpAcreditacio1String()}
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
				                          	<input name="acreditacio2" type="checkbox" ${empresa.acreditacio2 ? 'checked' : ''} disabled> Certificat de la Tresoreria General de la Seguretat Social del Ministeri
				                          	d'Ocupació i Seguretat Social, de què l'empresa està al corren en el compliment de les obligacions
				                          	de pagamanet de la Seguretat Social.
				                        </label>
				                	</div>
				                </div>
                			</div>
                			<div class="col-xs-offset-2 col-lg-4">
	                			<div class="form-group">
	                                <label>Expedit amb data: </label> ${empresa.getDateExpAcreditacio2String()}
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
				                          	<input name="acreditacio3" type="checkbox" ${empresa.acreditacio3 ? 'checked' : ''} disabled> Certificat de la secretària de la Junta Consultiva de Contratació Administrativa
				                          	de què l'empresa no té deutes de naturalesa tributària amb la Comunitat Autònoma de les Illes Balears, en
				                          	via de constrenyiment.
				                        </label>
				                	</div>
				                </div>
                			</div>
                			<div class="col-xs-offset-2 col-lg-4">
	                			<div class="form-group">
	                                <div class="form-group">
		                                <label>Expedit amb data: </label> ${empresa.getDateExpAcreditacio3String()}
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
                		<div class="col-xs-offset-1 col-lg-10 longText">
                			<p> 
	                        	<label>Informació adicional: </label> 
	                        	${empresa.informacioAdicional}
                            </p>	                		
                        </div>
                	</div>
                	<div class="row">
               			<c:if test="${canModificar}">
							<div class="col-md-offset-9 col-lg-2">
								<a href="editEmpresa?cif=${empresa.cif}" class="btn btn-primary" role="button">Modificar</a>
							</div>
						</c:if>
                	</div>                	
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