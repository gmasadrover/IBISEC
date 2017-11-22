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
                            Expedients <small>Expedients</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Expedients
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <div class="row">
					<form class="form-horizontal" method="POST" action="expedients">						
						<div class="form-group">				
							<input type="hidden" id="tipusSelected" value="${tipusFilter}" />
							<input type="hidden" id="contracteSelected" value="${contracteFilter}" />
							<input type="hidden" id="estatSelected" value="${estatFilter}" />
							<input type="hidden" id="anySelected" value="${anyFilter}"/>
							<div class="col-md-2">
							    <div class="col-md-12">
							      <label>Tipus expedient</label>
							      <div>
		                                <select class="form-control selectpicker" name="tipus" data-live-search="true" id="tipusList">
		                                	<option value="-1">Qualsevol</option>
		                                	<option value="obra">Obra</option>
		                                	<option value="servei">Servei</option>
		                                	<option value="subministrament">Subministrament</option>
		                                	<option value="mixte">Mixte</option>
		                                </select>
		                             </div>
							    </div>						    
						  	</div>	
						  	<div class="col-md-2">
							    <div class="col-md-12">
							      <label>Tipus contracte</label>
							      <div>
		                                <select class="form-control selectpicker" name="contracte" data-live-search="true" id="contracteList">
		                                	<option value="-1">Qualsevol</option>
		                                	<option value="major">Major</option>
		                                	<option value="menor">Menor</option>
		                                </select>
		                             </div>
							    </div>						    
						  	</div>	
						  	<div class="col-md-3">
							    <div class="col-md-12">
							      <label>Estat</label>
							      <div>
							      	<select class="selectpicker" id="estatList" name="estat">
									  	<option value="-1">Qualsevol</option>
									  	<option value="redaccio">En redacció</option>
									  	<option value="iniciExpedient">Inici expedient</option>
									  	<option value="publicat">Publicats</option>
									  	<option value="licitacio">Licitació</option>
									  	<option value="adjudicacio">Adjudicació</option>
									  	<option value="firmat">Contracte Firmat</option>
									  	<option value="execucio">Execució obra</option>
									  	<option value="garantia">Garantia</option>
									  	<option value="acabat">Acabat</option>
									</select>							      	
							      </div>
							    </div>
						  	</div>
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
                        <h2>Expedients</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Expedient</th>
                                        <th>Data Creacio</th>
                                        <th>Centre</th>
                                        <th>Descripció</th>
                                        <th>Publicat BOIB</th>
                                        <th>Publicat BOIB</th>
                                        <th>Preu licitació</th>
                                        <th>Adjudicació</th>
                                        <th>Adjudicació</th>
                                        <th>Adjudicatari</th>
                                        <th>Preu adjudicació</th>
                                        <th>Firma</th>
                                        <th>Firma</th>
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
							          	<tr>							          	
							           		<td><a href="expedient?ref=${informe.expcontratacio.expContratacio}" class="loadingButton"  data-msg="obrint expedient...">${informe.expcontratacio.expContratacio}</a></td>
							           		<td>${informe.expcontratacio.dataCreacio}</td>
							            	<td>${informe.actuacio.centre.getNomComplet()}</td>
							            	<td>${informe.getPropostaInformeSeleccionada().objecte}</td>
							            	<td>${informe.expcontratacio.getDataPublicacioBOIBString()}</td>
							            	<td>${informe.expcontratacio.dataPublicacioBOIB}</td>
							            	<td>${informe.getPropostaInformeSeleccionada().getPlicFormat()}</td>
							            	<td>${informe.expcontratacio.getDataAdjudicacioString()}</td>
							            	<td>${informe.expcontratacio.dataAdjudicacio}</td>
							            	<td>${informe.getOfertaSeleccionada().nomEmpresa}</td>
							            	<td>${informe.getOfertaSeleccionada().getPlicFormat()}</td>
							            	<td>${informe.expcontratacio.getDataFirmaString()}</td>
							            	<td>${informe.expcontratacio.dataFormalitzacioContracte}</td>
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