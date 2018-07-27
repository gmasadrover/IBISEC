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
                            Expedients <small>Convenis</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Convenis
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <div class="row">
					<form class="form-horizontal" method="POST" action="convenis">						
						<div class="form-group">	
							<input type="hidden" id="anySelected" value="${anyFilter}"/>							
						  	<div class="col-md-2">
							    <div class="col-md-12">
							      <label>Any</label>
							      <div>
		                                <select class="form-control selectpicker" name="any" id="anyList">
		                                	<option value="-1">Tots</option>
		                                	<c:forEach items="${anysList}" var="any" >
		                                		<option value="${any}">${any}</option>
		                                	</c:forEach>
		                                </select>
		                             </div>
							    </div>						    
						  	</div>	
						</div>	
						<div class="form-group">	
						  	<div class="col-md-offset-9 col-md-3">
						    	<input type="submit" class="btn btn-primary loadingButton"  data-msg="Aplicant filtres..." name="filtrar" value="Aplicar Filtres">
							</div>
						</div>					
					</form>
				</div>	       		
                <!-- /.row -->
                <div class="row">
                    <div class="col-md-12">
                        <h2>Convenis</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Expedient</th>
                                        <th>Data Creacio</th>
                                        <th>Centre</th>
                                        <th>Descripció</th>
                                        <th>Publicació</th>
                                        <th>Publicació</th>
                                        <th>Preu licitació</th>
                                        <th>Adjudicació</th>
                                        <th>Adjudicació</th>
                                        <th>Adjudicatari</th>
                                        <th>Preu adjudicació</th>
                                        <th>Firma</th>
                                        <th>Firma</th>
                                        <th>Facturat</th>
                                        <th>Inici d'obra</th>
                                        <th>Inici d'obra</th>
                                        <th>Data recepció</th>
                                        <th>Data recepció</th>
                                        <th>Fi garantia</th>
                                        <th>Fi garantia</th>
                                        <th>Liquidació</th>
                                        <th>Liquidació</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${informesList}" var="informe" >
							          	<tr class=${informe.expcontratacio.anulat ? "danger" : informe.llistaModificacions.size() > 0 ? "warning" : "success"}>							          	
							           		<td>
							           			<c:choose>
							           				<c:when test="${informe.expcontratacio.expContratacio != '-1'}">
							           					<a href="actuacionsDetalls?ref=${informe.actuacio.referencia}&exp=${informe.idInf}" class="loadingButton"  data-msg="obrint expedient...">${informe.expcontratacio.expContratacio}</a></td>
							           				</c:when>
							           				<c:otherwise>
							           					<a href="actuacionsDetalls?ref=${informe.actuacio.referencia}&exp=${informe.idInf}" class="loadingButton"  data-msg="obrint expedient...">${informe.idInf}</a></td>
							           				</c:otherwise>
							           			 </c:choose>
							           		<td>${informe.expcontratacio.dataCreacio}</td>
							            	<td>${informe.actuacio.centre.getNomComplet()}</td>
							            	<td>${informe.getPropostaInformeSeleccionada().objecte}</td>
							            	<td>${informe.getPublicacioBOIBString()}</td>
							            	<td>${informe.publicacioBOIB}</td>
							            	<td>${informe.getPropostaInformeSeleccionada().getPlicFormat()}</td>
							            	<td>${informe.expcontratacio.getDataAdjudicacioString()}</td>
							            	<td>${informe.expcontratacio.dataAdjudicacio}</td>
							            	<td>${informe.getOfertaSeleccionada().nomEmpresa}</td>
							            	<td>
							            		<c:choose>
								            		<c:when test="${informe.llistaModificacions.size() > 0}">
								            			<a href="modificacions?exp=${informe.expcontratacio.expContratacio}">${informe.getOfertaSeleccionada().getPlicFormat()} *</a>
								            		</c:when>
								            		<c:otherwise>
								            			${informe.getOfertaSeleccionada().getPlicFormat()}
								            		</c:otherwise>
							            		</c:choose>
							            	</td>
							            	<td>${informe.expcontratacio.getDataFirmaString()}</td>
							            	<td>${informe.expcontratacio.dataFormalitzacioContracte}</td>
							            	<td>${informe.getTotalFacturatFormat()}</td>
							            	<td>${informe.expcontratacio.getDataIniciObratring()}</td>
							            	<td>${informe.expcontratacio.dataIniciExecucio}</td>
							            	<td>${informe.expcontratacio.getDataRecepcioString()}</td>
							            	<td>${informe.expcontratacio.dataRecepcio}</td>
							            	<td>${informe.expcontratacio.getDataRetornGarantiaString()}</td>
							            	<td>${informe.expcontratacio.dataRetornGarantia}</td>
							            	<td>${informe.expcontratacio.getDataLiquidacioString()}</td>
							            	<td>${informe.expcontratacio.dataLiquidacio}</td>
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
    <script src="js/expedient/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>