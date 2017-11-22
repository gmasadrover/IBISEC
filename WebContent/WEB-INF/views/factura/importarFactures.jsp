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
	                            Factures <small>Importar</small>
	                        </h1>
	                        <ol class="breadcrumb">
	                            <li class="active">
	                                <i class="fa fa-dashboard"></i> Factures
	                            </li>
	                            <li class="active">
	                                <i class="fa fa-table"></i> Importar
	                            </li>
	                        </ol>
	                    </div>
	                </div>
	                <!-- /.row -->
	                <div class="row">
		                <div class="col-md-12">
							<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="importFactures">
								<input type="hidden" name="idIncidencies" value="-1">
								<input type="hidden" name="tipus" value="facturacio">
								<input type="hidden" name="idTipus" value="-1">											
								<input type="hidden" name="redirect" value="/importarFactures">		
								<div class="form-group">
									<label class="col-xs-3 control-label">Adjuntar factures noves:</label>
		                            <div class="col-xs-5">   
		                                <input type="file" class="btn" name="file" multiple/><br/>
									</div> 
									<div class="col-xs-2"> 
		         						<input type="submit" class="btn btn-primary" value="Importar" />
		         					</div>    						
		         				</div>         				
							</form>	
		                </div>
	            	</div>            	            	
	            	<div class="row">
	                    <div class="col-md-12">
	                        <h2>Factures</h2>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered filerTable">
	                                <thead>
	                                    <tr>
	                                        <th>Factura</th>
	                                        <th>Actuació</th>
	                                        <th>Data factura</th>
	                                        <th>Data factura No Format</th>
	                                        <th>Concepte</th>
	                                        <th>Nombre fact</th>
	                                        <th>Import</th>
	                                        <th>Tipus</th>
	                                        <th>Proveïdor</th>
	                                        <th>Usuari Conformador</th>
	                                        <th>Data Conformació No Format</th>
	                                        <th>Data Conformació</th>
	                                        <th>Partida</th>
	                                        <th>notes</th>   
	                                        <th>Centre</th> 
	                                        <th>Data creació actuació No Format</th>
	                                        <th>Data creació actuació</th>    
	                                        <th>tipo PD</th>
	                                        <th>Proposta</th>
	                                        <th>data PD No Format</th>
	                                        <th>data PD</th>
	                                        <th>data aut No Format</th>
	                                        <th>data aut</th>
	                                        <th>despesa prevista</th>   
	                                        <th>Factura</th>                                                        
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach items="${facturesList}" var="factura" >
								          	<tr class="success">							          	
									            <td><a href="facturaDetalls?ref=${factura.idFactura}" class="loadingButton"  data-msg="obrint factura...">${factura.idFactura}</a></td>
								            	<td><a href="actuacionsDetalls?ref=${factura.idActuacio}" class="loadingButton"  data-msg="obrint actuació...">${factura.idActuacio} - ${factura.actuacio.descripcio}</a></td>
								            	<td>${factura.getDataFacturaString()}</td>
								            	<td>${factura.dataFactura}</td>
								            	<td>${factura.concepte}</td>
								            	<td>${factura.nombreFactura}</td>
								            	<td>${factura.valor}</td>
								            	<td>${factura.tipusFactura}</td>
								            	<td>${factura.idProveidor}</td>
								            	<td>${factura.usuariConformador.getNomCompletReal()}</td>
								            	<td>${factura.dataConformacio}</td>
								            	<td>${factura.getDataConformacioString()}</td>
								            	<td>${factura.informe.codiPartida}</td>
								            	<td>${factura.notes}</td>	 
								            	<td>${factura.actuacio.centre.getNomComplet()}</td>    
								            	<td>${factura.actuacio.dataCreacio}</td> 
								            	<td>${factura.actuacio.getDataCreacioString()}</td> 
								            	<td>${factura.informe.tipoPD}</td>      
								            	<td>${factura.informe.idInf}</td>      
								            	<td>${factura.informe.dataPD}</td>   
								            	<td>${factura.informe.getDataPDString()}</td>      
								            	<td>${factura.informe.dataAprovacio}</td> 
								            	<td>${factura.informe.getDataAprovacioString()}</td>      
								            	<td>${factura.informe.ofertaSeleccionada.getPlicFormat()}</td> 
								            	<td><a target="_blanck" href="downloadFichero?ruta=${factura.arxiu.getEncodedRuta()}">${factura.arxiu.nom}</a></td>      	     	
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
	    <script src="js/factura/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
	    <!-- /#wrapper -->
	</body>
</html>