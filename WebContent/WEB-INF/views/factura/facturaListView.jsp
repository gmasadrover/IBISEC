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
					<div class="col-md-12">
						<form class="form-horizontal" method="POST" action="factures">						
							<div class="form-group">
								<div class="col-md-offset-1  col-md-3">								    
								      <label>Concepte</label>
								      <input type="text"  class="form-control" name="concepte" value="${concepte}">				    
							  	</div>
							  	<div class="col-md-3">								    
								      <label>Nombre factura</label>
								      <input type="text"  class="form-control" name="nombreFact" value="${nombreFact}">				    
							  	</div>
							  	<div class="col-md-3">								    
								      <label>Descripció Actuació</label>
								      <input type="text"  class="form-control" name="descActuacio" value="${descAct}">				    
							  	</div>
							</div>
							<div class="form-group">
								<div class="col-md-offset-1  col-md-4">								    
									<div class="col-md-12">
										<input type="hidden" id="tipoContracteSelected" value="${tipoContracte}" />
										<label>Tipo Contracte</label>
										<div>
			                                <select class="form-control selectpicker" name="tipoContracte" data-size="10" id="tipoContracteList" data-live-search="true" >
			                                	<option value="-1">No filtrar</option>
			                                	<option value="obraMenor">Obra Menor</option>
			                                	<option value="obraMajor">Obra Major</option>
			                                	<option value="altres">Altres</option>
			                                </select>
			                             </div>
									</div>				    
							  	</div>
							  	<div class="col-md-4">								    
									<div class="col-md-12">
										<input type="hidden" id="tipoPDSelected" value="${tipoPD}" />
										<label>Tipo PD</label>
										<div>
			                           		<select class="form-control selectpicker" name="tipoPD" data-size="10" id="tipoPDList" data-live-search="true" >
			                                	<option value="-1">No filtrar</option>
			                                	<c:forEach items="${llistaTipoPD}" var="tipoPD" >		                                		
			                                		<option value="${tipoPD}">${tipoPD}</option>
			                                	</c:forEach>
			                                </select>
			                          	</div>
									</div>			    
							  	</div>
							</div>		
							<div class="form-group">					
							  	<div class="col-md-offset-1 col-md-4">
							  		<label>Data factura</label>
								  	<div class="input-group input-daterange datepicker">
									    <input type="text" class="form-control" name="dataInici" value="${dataInici}">
									    <div class="input-group-addon">fins</div>
									    <input type="text" class="form-control" name="dataFi" value="${dataFi}">
									</div>                                
							  	</div>		
							  	<div class="col-md-4">
							  		<label>Data PD</label>
								  	<div class="input-group input-daterange datepicker">
									    <input type="text" class="form-control" name="dataIniciIdPD" value="${dataIniciIdPD}">
									    <div class="input-group-addon">fins</div>
									    <input type="text" class="form-control" name="dataFiIdPD" value="${dataFiIdPD}">
									</div>                                
							  	</div>							  				  				 
							  	<div class="col-md-2">
							    	<input type="submit" class="btn btn-primary margin_top30" name="filtrar" value="Aplicar Filtres">
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
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${facturesList}" var="factura" >
							          	<tr class="success">							          	
							           		<td><a href="facturaDetalls?ref=${factura.idFactura}">${factura.idFactura}</a></td>
							            	<td><a href="actuacionsDetalls?ref=${factura.idActuacio}">${factura.idActuacio} - ${factura.actuacio.descripcio}</a></td>
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
							            	<td>${factura.notes}</td>	 
							            	<td>${factura.actuacio.nomCentre}</td>    
							            	<td>${factura.actuacio.dataCreacio}</td> 
							            	<td>${factura.actuacio.getDataCreacioString()}</td> 
							            	<td>${factura.informe.tipoPD}</td>      
							            	<td>${factura.informe.idInf}</td>      
							            	<td>${factura.informe.dataPD}</td>   
							            	<td>${factura.informe.getDataPDString()}</td>      
							            	<td>${factura.informe.dataAprovacio}</td> 
							            	<td>${factura.informe.getDataAprovacioString()}</td>      
							            	<td>${factura.informe.ofertaSeleccionada.getPlicFormat()}</td>       	
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