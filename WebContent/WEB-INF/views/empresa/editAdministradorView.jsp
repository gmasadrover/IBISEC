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
                            Empresa <small>Modificar Administrador</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Empresa
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Modificar Administrador
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
                <c:if test="${not empty administrador}">
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditAdministrador"> 
                		<input type="hidden" name="cif" value="${cif}">
                		<div class="row">
	                		<div class="col-md-6">
		                		<div class="form-group">
	                                <label class="col-xs-3 control-label">Nom</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="nomAdmin" id="nomAdmin" placeholder="nom administrador" value="${administrador.nom}">
	                                </div>
	                            </div>	                            
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Vàlid fins</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="validAdmin" id="validAdmin" value="${administrador.getDataValidesaFinsString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	                            
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Notari</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="nomNotari" id="nomNotari" placeholder="nom Notari" value="${administrador.notariModificacio}">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Número protocol</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="numProtocol" id="numProtocol" placeholder="xxxx" value="${administrador.protocolModificacio}">
	                                </div>
	                            </div> 	
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Organ validador</label>
	                                <input type="hidden" id="organValidadorSelected" value="${administrador.entitatValidacio}">
	                                <div class="col-xs-6">
		                                <select class="form-control" name="organValidador" id="organValidador">
		                                	<option value="">--</option>
		                                	<option value="caib">Advocacia CAIB</option>
		                                	<option value="estat">Advocacia Estat</option>
		                                	<option value="ibisec">Assessoria jurídica IBISEC</option>
		                                </select>
		                             </div>
	                            </div>                              
	                        </div>
	                        <div class="col-md-6">
	                        	<div class="form-group">
	                                <label class="col-xs-3 control-label">DNI</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="dniAdmin" id="dniAdmin" placeholder="12345678A" value="${administrador.dni}">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Tipus</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="tipusAdmin" id="tipusAdmin" value="${administrador.tipus}">
	                                </div>
	                            </div> 
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data escriptura</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataAlta" id="dataAlta" value="${administrador.getDataModificacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data validació</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataValidacio" id="dataValidacio" value="${administrador.getDataValidacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	
	                            <div class="form-group">
			                		<label class="col-xs-2 control-label">Adjuntar document:</label>
			                		<a target="_blanck" href="downloadFichero?ruta=${administrador.documentAdministrador.getEncodedRuta()}">${administrador.documentAdministrador.nom}</a>
									<a href="#"><span data-ruta="${administrador.documentAdministrador.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
									<br>		                	
			                        <div class="col-xs-5">   
			                            <input type="file" class="btn fileAdministrador" name="fileAdministrador" /><br/>
									</div>							
								</div> 	
	                        </div>
	                    </div>
	                	<div class="row">
	                		<div class="form-group">
	                			<div class="col-xs-offset-6 col-xs-3">
						            <input type="submit" class="btn btn-danger" name="eliminar" value="Eliminar">							            
						        </div>
						        <div class="col-xs-3">
						            <input type="submit" class="btn btn-primary" name="modificar" value="Guardar Canvis">							            
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
    <script src="js/empresa/modificarAdmin.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>