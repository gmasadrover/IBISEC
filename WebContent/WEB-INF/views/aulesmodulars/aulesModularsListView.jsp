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
                            Aules Modulars <small>Llistat</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Aules Modulars
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <div class="row">
                	<div class="col-md-12">
                		<a href="novaAulaModular" class="btn btn-primary" role="button">Introduir nova aula</a>
                	</div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h2>Aules</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Expedient</th>
                                        <th>Centre</th>  
                                        <th>Descripció</th>
                                        <th>Empresa</th>
                                        <th>Import mensual</th>
                                        <th>Darrera factura</th>
                                        <th>Data fi contracte</th>
                                        <th>Expedient Regularització</th>
                                        <th>No autoritzada</th>  
                                        <th>Control</th>                                                    
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${aulesModularsList}" var="aulaModular" >
							          	<tr>							          	
							           		<td>
							           			<c:choose>
							           				<c:when test="${aulaModular.informe.expcontratacio.expContratacio != '-1'}">
							           					<a href="actuacionsDetalls?ref=${aulaModular.informe.actuacio.referencia}&exp=${aulaModular.informe.idInf}" class="loadingButton"  data-msg="obrint expedient...">${aulaModular.informe.expcontratacio.expContratacio}</a>
							           				</c:when>
							           				<c:otherwise>
							           					<a href="actuacionsDetalls?ref=${aulaModular.informe.actuacio.referencia}&exp=${aulaModular.informe.idInf}" class="loadingButton"  data-msg="obrint expedient...">${aulaModular.informe.idInf}</a>
							           				</c:otherwise>
							           			 </c:choose>
							           		</td>	
							           		<td>${aulaModular.informe.actuacio.centre.getNomComplet()}</td>		
							           		<td>${aulaModular.informe.propostaInformeSeleccionada.objecte}</td>
							            	<td>${aulaModular.informe.ofertaSeleccionada.nomEmpresa}</td>
							            	<td>${aulaModular.getImportPrevistFormat()}</td>
							            	<td><input class="btn btn-success carregarModal" data-idassignacio="-1" data-valorassignacio="0" data-toggle="modal" data-target="#modalAssignacio${aulaModular.informe.idInf}" name="consulta" value="Veure Factures"></td>
							            	<td>${aulaModular.getDataLimitContracteString()}</td>
							            	<td><a href="actuacionsDetalls?ref=${aulaModular.informeAutoritzacio.actuacio.referencia}&exp=${aulaModular.informeAutoritzacio.idInf}" class="loadingButton"  data-msg="obrint expedient...">${aulaModular.informeAutoritzacio.expcontratacio.expContratacio}</a></td>
							            	<td><a href="actuacionsDetalls?ref=${aulaModular.noAutoritzada.actuacio.referencia}&exp=${aulaModular.noAutoritzada.idInf}" class="loadingButton"  data-msg="obrint expedient...">${aulaModular.noAutoritzada.expcontratacio.expContratacio}</a></td>					            						            	
							          		<td><a href="editAulaModular?ref=${aulaModular.informe.idInf}" class="btn btn-primary" role="button">Modificar</a></td>
							          	</tr>
							          	<!-- Modal -->	        			
										<div id="modalAssignacio${aulaModular.informe.idInf}" class="modal fade" role="dialog">
											<div class="modal-dialog">																	
										    <!-- Modal content-->
										    	<div class="modal-content">
										      		<div class="modal-header">
										        		<button type="button" class="close" data-dismiss="modal">&times;</button>
										        		<h4 class="modal-title">Factures</h4>
										      		</div>
										      		<div class="modal-body">
										        		${aulaModular.darreraFactura}
										      		</div>
									    		</div>																	
										  	</div>
										</div> 
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
    <script src="js/aulesmodulars/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>