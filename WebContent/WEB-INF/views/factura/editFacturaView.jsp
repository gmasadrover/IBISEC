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
                            Factura <small>Modificar factura</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Factura
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
                    <div class="col-md-9">
						<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditFactura">
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">id factura</label>
                                <div class="col-xs-3">
                                	<input class="form-control" placeholder="codi factura" value="${factura.idFactura}" disabled>
                                	<input class="hidden" name="idFactura" value="${factura.idFactura}">
                                	<input class="hidden" name="idInforme" id="idInforme" value="${factura.idInforme}">
                                	<input class="hidden" name="idActuacio" id="idActuacio" value="${factura.idActuacio}">
                                </div>
                            </div>
                           	<div id="seleccionarInforme">                            		
	                            <div class="form-group">
	                                <label class="col-xs-3  control-label">Centre</label>
	                                <input class="hidden" name="idCentreActual" id=idCentreActual value="${idCentre}"> 
	                                <div class="col-xs-3">
		                                <select class="form-control selectpicker centresList" name="idCentre" data-live-search="true" data-size="5" id="centresList">
			                            	<option value="-1">No hi ha relació</option>
			                            	<option value="procediment">Procediment</option>
			                            </select>
		                             </div>
	                            </div> 
	                            <div id="incidencies"></div>   
	                            <div id="procediments"></div>   
	                            <div id="expedients"></div>
	                    	</div>	                    	
                            <div class="form-group proveidor">
                                <label class="col-xs-3 control-label">Proveidor</label>
                                <input class="hidden" name="nifProveidor" id=nifProveidor value="${factura.idProveidor}"> 
                                <div class="col-xs-3">
                                	<select class="selectpicker" name="idEmpresa" id="llistaEmpreses" data-live-search="true" data-size="5">						                                					                                	
					               		<option value="-1">No hi ha relació</option>
					               		<c:forEach items="${empresesList}" var="empresa">
					                   		<option value="${empresa.cif}">${empresa.name} (${empresa.cif})</option>
					                   	</c:forEach>	
					                </select>	
                                </div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3  control-label">Usuari conformador</label>
                                <input class="hidden" name="idUsuariInforme" id=idUsuariInforme value="${factura.usuariConformador.idUsuari}"> 
                                <div class="col-xs-3">
	                                 <select class="form-control selectpicker" data-live-search="true" data-size="5" name="usuarisList" id="usuarisList">
					                	<c:forEach items="${llistaUsuaris}" var="usuari">
					                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
					                	</c:forEach>					                                	
					                </select>	
	                             </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Import</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="import" id="import" placeholder="0000.00" value="${factura.valor}">
                                </div>
                            </div>	     
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Concepte</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="concepte" placeholder="concepte" value="${factura.concepte}">
                                </div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data entrada</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataEntrada" value="${factura.getDataEntradaString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data factura</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataFactura" value="${factura.getDataFacturaString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Tipus factura</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="tipus" placeholder="tipus" value="${factura.tipusFactura}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Nombre factura</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="nombre" placeholder="nombre" value="${factura.nombreFactura}">
                                </div>
                            </div>
                            <div class="form-group hidden">
                                <label class="col-xs-3 control-label">Data pasada a conformar</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataPasadaConformar" value="${factura.getDataEnviatConformadorString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>                             
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data conformada</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataConformada" value="${factura.getDataConformacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>  
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data pasada a comtabilitat</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataPasadaComptabilitat" value="${factura.getDataEnviatComptabilitatString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>          
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data descarregada</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataDescarregadaComptabilitat" value="${factura.getDataDescarregadaConformadaString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>            
                            <div class="form-group">
                            	<label class="col-xs-3 control-label">Notes</label>
                                <div class="col-xs-3">
                                	<textarea class="form-control" name="notes" placeholder="anotacions" rows="3">${factura.notes}</textarea>
                                </div>
                            </div>	
                            <div class="form-group">						       	
				    			<div class="col-md-12">
				    				<label class="col-xs-3 control-label">Factura</label> 
				    				<div class="col-xs-3">
										<c:set var="arxiu" value="${factura.factura}" scope="request"/>
										<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>											
										<br>									
					           			<input type="file" class="btn uploadImage" name="factura" /><br/>	
				           			</div>															 		
				    			</div>
				      		</div>	  
				      		<div class="form-group">						       	
				    			<div class="col-md-12">
				    				<label class="col-xs-3 control-label">Altres</label> 				    				
				    				<div class="col-xs-3">
				    					<c:forEach items="${factura.altres}" var="arxiu" >
											<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
											<c:if test="${arxiu.ruta != null && arxiu.ruta != ''}">
												<span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
											</c:if>
											<br>	
										</c:forEach>								
					           			<input type="file" class="btn uploadImage" name="altres"  multiple/><br/>	
				           			</div>															 		
				    			</div>
				      		</div>	                           
		    				<br>
		    				<c:if test="${errorString==null}">
							    <div class="form-group">
							        <div class="col-xs-offset-3 col-xs-9">
							            <input type="submit" class="btn btn-primary" value="Modificar">
							        </div>
							    </div>    	
						    </c:if>			
		    			</form>
                    </div>
                    <div class="col-md-3 infoExpedient">                    	
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