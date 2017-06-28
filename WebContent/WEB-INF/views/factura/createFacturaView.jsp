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
                            Factura <small>Registrar factura</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Factura
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Registre
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
						<form class="form-horizontal" method="POST" action="doCreateFactura">
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">id factura</label>
                                <div class="col-xs-3">
                                	<input class="form-control" placeholder="codi factura" value="${idFactura}" disabled>
                                	<input class="hidden" name="idFactura" value="${idFactura}">
                                	<input class="hidden" name="idInforme" value="${idInforme}">
                                	<input class="hidden" name="idActuacio" value="${idActuacio}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Proveidor</label>
                                <input class="hidden" name="nifProveidor" id=nifProveidor value="${nifProveidor}"> 
                                <div class="col-xs-3">
                                	<select class="selectpicker" name="idEmpresa" id="llistaEmpreses" data-live-search="true" data-size="5">						                                					                                	
					               		<c:forEach items="${empresesList}" var="empresa">
					                   		<option value="${empresa.cif}">${empresa.name} (${empresa.cif})</option>
					                   	</c:forEach>	
					                </select>	
                                </div>
                            </div>	 
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Import</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="import" id="import" placeholder="0000,00">
                                </div>
                            </div>	     
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Concepte</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="concepte" placeholder="concepte">
                                </div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data entrada</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataEntrada" value="${data}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data factura</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataFactura" value="${data}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Tipus factura</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="tipus" placeholder="tipus">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Nombre factura</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="nombre" placeholder="nombre">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Usuari conformador</label>
                                <div class="col-xs-3">
                                	<select class="form-control selectpicker" data-live-search="true" data-size="10" name="idConformador" id="usuarisList">
		                                <c:forEach items="${llistaUsuaris}" var="usuari" >
	                                		<option value='${usuari.idUsuari}'>${usuari.getNomComplet()}</option>
	                                	</c:forEach>
                                	</select>
                                </div>
                            </div>		
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Data conformació</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="dataConformacio" value="${data}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Notes</label>
                                <div class="col-xs-3">
                                	<textarea class="form-control" name="notes" placeholder="anotacions" rows="3"></textarea>
                                </div>
                            </div>	
		    				<br>
						    <div class="form-group">
						        <div class="col-xs-offset-3 col-xs-9">
						            <input type="submit" class="btn btn-primary" value="Guardar">
						            <input type="reset" class="btn btn-default" value="Reiniciar">
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
    <script src="js/factura/crear.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>