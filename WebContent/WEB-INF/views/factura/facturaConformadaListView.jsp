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
                            Factures per comptabilitzar<small></small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Factures per comptabilitzar
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" method="POST" action="doDownloadFactures">
							<div class="form-group">					  				 
							  	<div class="col-md-2">
							    	<input type="submit" class="btn btn-primary margin_top30"  value="Descarregar">
								</div>
							</div>	
						</form>
					</div>
				</div>
				
                <div class="row">
                    <div class="col-md-12">
                        <h2>Factures per comptabilitzar</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Factura</th>
                                        <th>Actuació</th>
                                        <th>Expedient</th>
                                        <th>Data factura</th>
                                        <th>Data factura No Format</th>
                                        <th>Data entrada</th>
                                      	<th>Data entrada No Format</th>
                                        <th>Concepte</th>
                                        <th>Nombre fact</th>
                                        <th>Import</th>
                                        <th>Tipus</th>
                                        <th>Proveïdor</th>
                                        <th>Nom proveïdor</th>
                                        <th>Usuari Conformador</th>
                                        <th>Data Conformació No Format</th>
                                        <th>Data Conformació</th>
                                        <th>Data Descarregada No Format</th>
                                        <th>Data Descarregada</th>
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
                                        <th>Estat</th>                              
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${facturesList}" var="factura" >
                                		<c:if test="${factura.factura != null && factura.factura.getEncodedRuta() != ''}">
								          	<tr class="success">							          	
								           		<td><a href="facturaDetalls?ref=${factura.idFactura}" class="loadingButton"  data-msg="obrint factura...">${factura.idFactura}</a></td>
								            	<td><a href="actuacionsDetalls?ref=${factura.idActuacio}" class="loadingButton"  data-msg="obrint actuació...">${factura.idActuacio} - ${factura.actuacio.descripcio}</a></td>
								            	<td>${factura.informe.expcontratacio.expContratacio}</td>
								            	<td>${factura.getDataFacturaString()}</td>
								            	<td>${factura.dataFactura}</td>
								            	<td>${factura.getDataEntradaString()}</td>
							            		<td>${factura.dataEntrada}</td>
								            	<td>${factura.concepte}</td>
								            	<td>${factura.nombreFactura}</td>
								            	<td>${factura.valor}</td>
								            	<td>${factura.tipusFactura}</td>
								            	<td>${factura.idProveidor}</td>
								            	<td>${factura.nomProveidor}</td>
								            	<td>${factura.usuariConformador.getNomCompletReal()}</td>
								            	<td>${factura.dataConformacio}</td>
								            	<td>${factura.getDataConformacioString()}</td>
								            	<td>${factura.getDataDescarregadaConformada()}</td>
							            		<td>${factura.getDataDescarregadaConformadaString()}</td>
								            	<td>${factura.informe.assignacioCredit[0].partida.codi}</td>
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
								            	<td>${factura.anulada ? 'Anul·lada' : 'Correcte'}</td>
								            </tr>
								    	</c:if>
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