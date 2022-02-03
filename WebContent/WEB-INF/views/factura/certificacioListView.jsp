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
                            Certificacions <small>Certificacions</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Certificacions
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
						<form class="form-horizontal" method="POST" action="certificacions">
							<input type="hidden" id="idCentreSelected" value="${idCentre}" />	
							<div class="form-group">					
							  	<div class="col-md-offset-1 col-md-4">
							  		<label>Data certificació</label>
								  	<div class="input-group input-daterange datepicker">
									    <input type="text" class="form-control" name="dataInici" value="${dataInici}">
									    <div class="input-group-addon">fins</div>
									    <input type="text" class="form-control" name="dataFi" value="${dataFi}">
									</div>                                
							  	</div>	
							  	<div class="col-md-3">
								    <div class="col-md-12">
								      	<label>Filtrar per centre</label>
								      	<div>
		                              		<select class="form-control selectpicker" name="idCentre" data-live-search="true" id="centresList">
		                               			<option value="-1">Tots els centres</option>
		                             		</select>
		                           		</div>
								    </div>						    
							  	</div>			  				  				 
							  	<div class="col-md-2">
							    	<input type="submit" class="btn btn-primary margin_top30 loadingButton"  data-msg="Aplicant filtres..." name="filtrar" value="Aplicar Filtres">
								</div>
							</div>	
						</form>
					</div>
				</div>
				
                <div class="row">
                    <div class="col-md-12">
                        <h2>Certificacions</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Certificació</th>
                                        <th>Actuació</th>
                                        <th>Data certificació</th>
                                        <th>Data certificació No Format</th>
                                        <th>Concepte</th>
                                        <th>Nombre cert</th>
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
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${certificacionsList}" var="certificacio" >
							         	 <tr class="${certificacio.anulada ? 'danger' : ''}">								          	
							           		<td><a href="certificacioDetalls?ref=${certificacio.idFactura}" class="loadingButton"  data-msg="obrint certificació...">${certificacio.idFactura}</a></td>
							            	<td><a href="actuacionsDetalls?ref=${certificacio.idActuacio}" class="loadingButton"  data-msg="obrint actuació...">${certificacio.idActuacio} - ${certificacio.actuacio.descripcio}</a></td>
							            	<td>${certificacio.getDataFacturaString()}</td>
							            	<td>${certificacio.dataFactura}</td>
							            	<td>${certificacio.concepte}</td>
							            	<td>${certificacio.nombreFactura}</td>
							            	<td>${certificacio.valor}</td>
							            	<td>${certificacio.tipusFactura}</td>
							            	<td>${certificacio.idProveidor}</td>
							            	<td>${certificacio.usuariConformador.getNomCompletReal()}</td>
							            	<td>${certificacio.dataConformacio}</td>
							            	<td>${certificacio.getDataConformacioString()}</td>
							            	<td>${certificacio.informe.assignacioCredit[0].partida.codi}</td>	 
							            	<td>${certificacio.notes}</td>	 
							            	<td>${certificacio.actuacio.centre.getNomComplet()}</td>    
							            	<td>${certificacio.actuacio.dataCreacio}</td> 
							            	<td>${certificacio.actuacio.getDataCreacioString()}</td> 
							            	<td>${certificacio.informe.tipoPD}</td>      
							            	<td>${certificacio.informe.idInf}</td>      
							            	<td>${certificacio.informe.dataPD}</td>   
							            	<td>${certificacio.informe.getDataPDString()}</td>      
							            	<td>${certificacio.informe.dataAprovacio}</td> 
							            	<td>${certificacio.informe.getDataAprovacioString()}</td>      
							            	<td>${certificacio.informe.ofertaSeleccionada.getPlicFormat()}</td>
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
    <script src="js/factura/llistatCertificacions.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>