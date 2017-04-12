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
                            Factures <small>Factures</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Factures
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
				<div class="row">
					<form class="form-horizontal" method="POST" action="factures">						
						<div class="form-group">							
						  	<div class="col-lg-4">
						  		<span>Data factura</span>
							  	<div class="input-group input-daterange datepicker">
								    <input type="text" class="form-control" name="dataInici" value="${dataInici}">
								    <div class="input-group-addon">fins</div>
								    <input type="text" class="form-control" name="dataFi" value="${dataFi}">
								</div>                                
						  	</div>							  				  				 
						  	<div class="col-lg-2">
						    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
							</div>
						</div>	
					</form>
				</div>
				
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Factures</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Factura</th>
                                        <th>Actuació</th>
                                        <th>Data entrada</th>
                                        <th>Data entrada</th>
                                        <th>Data factura</th>
                                        <th>Data factura</th>
                                        <th>Import</th>
                                        <th>Tipus</th>
                                        <th>Proveïdor</th>
                                        <th>notes</th>                                       
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${facturesList}" var="factura" >
							          	<tr class="success">							          	
							           		<td><a href="facturaDetalls?ref=${factura.idFactura}">${factura.idFactura}</a></td>
							            	<td><a href="actuacionsDetalls?ref=${factura.idActuacio}">${factura.idActuacio}</a></td>
							            	<td>${factura.getDataEntradaString()}</td>
							            	<td>${factura.dataEntrada}</td>
							            	<td>${factura.getDataFacturaString()}</td>
							            	<td>${factura.dataFactura}</td>
							            	<td>${factura.valor}</td>
							            	<td>${factura.tipusFactura}</td>
							            	<td>${factura.idProveidor}</td>
							            	<td>${factura.notes}</td>	        	
							          	</tr>
							       	</c:forEach>                                	
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/factura/llistat.js"></script>
    <!-- /#wrapper -->
</body>
</html>