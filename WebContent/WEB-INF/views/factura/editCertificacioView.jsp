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
                            Certificaci√≥ <small>Modificar certificaci√≥</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Certificaci√≥
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
                
    			<div class="row">
                    <div class="col-md-12">
						<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditCertificacio">
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">id certificaci√≥</label>
                                <div class="col-xs-3">
                                	<input class="form-control" placeholder="codi certificaci√≥" value="${certificacio.idFactura}" disabled>
                                	<input class="hidden" name="idCertificacio" value="${certificacio.idFactura}">
                                	<input class="hidden" name="idInforme" id="idInforme" value="${certificacio.idInforme}">
                                	<input class="hidden" name="idActuacio" id="idActuacio" value="${certificacio.idActuacio}">
                                </div>
                            </div>
                            <div class="form-group">
                            	 <div class="row">
                            	 	<div class="col-md-3">
				               		</div>
				                	<div class="col-md-9">
				               			<p style="color: red;">Recordau indicar si la certificaci√≥ √©s ordin√†ria o final.</p>
				               		</div>
				               	 </div>				               	 
                                <label class="col-xs-3 control-label">Tipus de certificaci√≥</label>
                                <input class="hidden" name="tipusCert" id=tipusCert value="${certificacio.tipus}">  
                                <div class="col-xs-3">
                                	<select class="selectpicker" name="tipusCertificacio" id="tipusCertificacio" ${canModificar ? '' : 'disabled'}>						                                					                                	
					               		<option value="ordinaria">Ordin√†ria</option>
					               		<option value="final">Final</option>					                   		
					                </select>	
                                </div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Proveidor</label>
                                <input class="hidden" name="nifProveidor" id=nifProveidor value="${certificacio.idProveidor}"> 
                                <div class="col-xs-3">
                                	<select class="selectpicker" name="idEmpresa" id="llistaEmpreses" data-live-search="true" data-size="5" ${canModificar ? '' : 'disabled'}>						                                					                                	
					               		<c:forEach items="${empresesList}" var="empresa">
					                   		<option value="${empresa.cif}">${empresa.name} (${empresa.cif})</option>
					                   	</c:forEach>	
					                </select>	
                                </div>
                            </div>	 
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Import</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="import" id="import" placeholder="0000.00" value="${certificacio.valor}" ${canModificar ? '' : 'disabled'}>
                                </div>
                            </div>	     
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Concepte</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="concepte" placeholder="concepte" value="${certificacio.concepte}" ${canModificar ? '' : 'disabled'}>
                                </div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data entrada</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataEntrada" value="${certificacio.getDataEntradaString()}" required ${canModificar ? '' : 'disabled'}><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data certificaci√≥</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataFactura" value="${certificacio.getDataFacturaString()}" required ${canModificar ? '' : 'disabled'}><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Nombre certficaci√≥</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="nombre" placeholder="nombre" value="${certificacio.nombreFactura}" ${canModificar ? '' : 'disabled'}>
                                </div>
                            </div>                           
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data pasada a conformar</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataPasadaConformar" value="${certificacio.getDataEnviatConformadorString()}" ${canModificar ? '' : 'disabled'}><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data conformada</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataConformada" value="${certificacio.getDataConformacioString()}" ${canModificar ? '' : 'disabled'}><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data pasada a comptabilitat</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataPasadaComptabilitat" value="${certificacio.getDataEnviatComptabilitatString()}" ${canModificar ? '' : 'disabled'}><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>
                            <div class="form-group">
                            	<label class="col-xs-3 control-label">Notes</label>
                                <div class="col-xs-3">
                                	<textarea class="form-control" name="notes" placeholder="anotacions" rows="3" ${canModificar ? '' : 'disabled'}>${certificacio.notes}</textarea>
                                </div>
                            </div>	
                             <div class="form-group">
                             	<div class="col-md-12">
				    				<label class="col-xs-3 control-label">Relaci√≥ valorada</label> 
				    				<div class="col-xs-3">
										<c:forEach items="${certificacio.relaciovalorada}" var="arxiu" >
											<c:set var="arxiu" value="${arxiu}" scope="request"/>
											<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
										</c:forEach>		
										<br>									
					           			<input type="file" class="btn uploadImage" name="relaciovalorada" multiple/><br/>	
				           			</div>															 		
				    			</div>                            	
							</div>
		    				<div class="form-group">						       	
				    			<div class="col-md-12">
				    				<label class="col-xs-3 control-label">Certificaci√≥</label> 
				    				<div class="col-xs-3">
										<c:forEach items="${certificacio.certificacions}" var="arxiu" >
											<c:set var="arxiu" value="${arxiu}" scope="request"/>
											<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
										</c:forEach>		
										<br>									
					           			<input type="file" class="btn uploadImage" name="certificacio" multiple/><br/>	
				           			</div>															 		
				    			</div>
				      		</div>	                           
		    				<br>
						    <div class="form-group">
						        <div class="col-xs-offset-3 col-xs-9">
						            <input type="submit" class="btn btn-primary" value="Modificar">
						        </div>
						    </div>    				
		    			</form>
                    </div>
                </div>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/factura/edit.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>