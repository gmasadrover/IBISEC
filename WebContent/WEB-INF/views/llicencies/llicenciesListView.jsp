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
                            Autoritzacions <small>Urbanístiques</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Autoritzacions Urbanístiques
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <div class="row">
					<form class="form-horizontal" method="POST" action="llicencies">						
						<div class="form-group">				
							<input type="hidden" id="tipusSelected" value="${tipusFilter}" />
							<input type="hidden" id="estatSelected" value="${estatFilter}" />
							<div class="col-md-offset-1  col-md-3">
							    <div class="col-md-12">
							      <label>Tipus Autorització</label>
							      <div>
		                                <select class="form-control selectpicker" name="tipus" id="tipusList">
		                                	<option value="">Qualsevol</option>
		                                	<option value="comun">Comunicació prèvia</option>
		                                	<option value="llicencia">Llicencia</option>
		                                </select>
		                             </div>
							    </div>						    
						  	</div>	
						  	<div class="col-md-2">
							    <div class="col-md-12">
							      <label>Estat</label>
							      <div>
							      	<select class="selectpicker" id="estatList" name="estat">
									  	<option value="">Qualsevol</option>
									  	<option value="pendent">Pendent</option>
									  	<option value="solicitad">Sol·licitud</option>
									  	<option value="concedida">Concedida</option>
									  	<option value="pagada">Pagada</option>
									</select>							      	
							      </div>
							    </div>
						  	</div>
						</div>	
						<div class="form-group">	
						  	<div class="col-md-offset-5 col-md-2">
						    	<input type="submit" class="btn btn-primary loadingButton"  data-msg="Aplicant filtres..." name="filtrar" value="Aplicar Filtres">
							</div>
						</div>					
					</form>
				</div>	       		
                <!-- /.row -->
                <div class="row">
                    <div class="col-md-12">
                        <h2>Autoritzacions Urbanístiques</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Codi</th>
                                        <th>Expedient</th>
                                        <th>Descripció</th>
                                        <th>Centre</th>
                                        <th>Tipus</th>
                                        <th>Sol·licitud</th>
                                        <th>Sol·licitud</th>
                                        <th>Concesió</th>
                                        <th>Concesió</th>
                                        <th>Pagada Taxa</th>
                                        <th>Pagada Taxa</th>                                        
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${informeList}" var="informe" >
							          	<tr>							          	
							           		<td><a href="llicencia?codi=${informe.llicencia.codi}" class="loadingButton"  data-msg="obrint llicència...">${informe.llicencia.codi}</a></td>
							           		<td><a href="actuacionsDetalls?ref=${informe.actuacio.referencia}" class="loadingButton"  data-msg="obrint expedient...">${informe.expcontratacio.expContratacio}</a></td>
							           		<td>${informe.propostaInformeSeleccionada.objecte}</td>
							           		<td>${informe.actuacio.centre.getNomComplet()}</td>
							            	<td>${informe.llicencia.getTipusFormat()}</td>
							            	<td>${informe.llicencia.peticio}</td>
							            	<td>${informellicencia.getDataPeticioString()}</td>
							            	<td>${informe.llicencia.concesio}</td>
							            	<td>${informe.llicencia.getDataConcesioString()}</td>
							            	<td>${informe.llicencia.pagamentTaxa}</td>
							            	<td>${informe.llicencia.getDataPagamentTaxaString()}</td>
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
    <script src="js/llicencia/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>