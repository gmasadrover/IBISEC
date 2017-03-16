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
                            Actuacions <small>Actuacions</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Actuacions
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
				<div class="row">
					<form class="form-horizontal" method="POST" action="actuacions">						
						<div class="form-group">						
							<input type="hidden" id="idCentreSelected" value="${idCentre}" />
							<input type="hidden" id="estatSelected" value="${estatFilter}" />
							<div class="col-lg-offset-1  col-lg-3">
							    <div class="col-lg-12">
							      <label>Filtrar per centre</label>
							      <div>
		                                <select class="form-control selectpicker" name="idCentre" data-live-search="true" id="centresList">
		                                </select>
		                             </div>
							    </div>						    
						  	</div>	
						  	<div class="col-lg-4">
						  		<div class="col-lg-12">
							  		<label>Filtrar per data petició</label>
								  	<div class="input-group input-daterange datepicker">
									    <input type="text" class="form-control" name="dataInici" value="${dataInici}">
									    <div class="input-group-addon">fins</div>
									    <input type="text" class="form-control" name="dataFi" value="${dataFi}">
									</div>
									<input type="checkbox" name="filterWithOutDate" ${filterWithOutDate ? "checked" : ""}> Filtrar fora dates
								</div>                                
						  	</div>	
						  	<div class="col-lg-2">
							    <div class="col-lg-12">
							      <label>Filtrar per estat</label>
							      <div>
							      	<select class="selectpicker" id="estatList" name="estat">
									  <option value="-1">Qualsevol</option>
									  <option value="Aprovada">Aprovada</option>
									  <option value="Pendent">Pendent</option>
									  <option value="Tancada">Tancada</option>
									</select>							      	
							      </div>
							    </div>
						  	</div>
						</div>	
						<div class="form-group">
							<div class="col-lg-offset-10 col-lg-2">
						    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-offset-1 col-lg-3 circunferencia green">${actuacionsAprovades}</div>
							<div class="col-lg-3 margin_left30 circunferencia yellow">${actuacionsPendents}</div>
							<div class="col-lg-3 margin_left30 circunferencia red">${actuacionsTancades}</div>
						</div>
					</form>
				</div>
				
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Actuacions</h2>
                        <div class="table-responsive">
                        
                            <table class="table table-striped table-bordered filerTable ${nomesActives ? "normal" : "withTancades"}">
                                <thead>
                                    <tr>
                                        <th>Referència</th>
                                        <th>Centre</th>
                                        <th>Descripció</th>
                                        <th>Data petició</th>
                                        <th>Data petició</th>
                                        <th>Darrera modificació</th>
                                        <th>Data modificació</th>
                                        <th>Data modificació</th>
                                        <c:if test="${!nomesActives}">
											<th>Estat</th>
										   	<th>Data tancament</th>
										   	<th>Data tancament</th>
										</c:if>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${actuacionsList}" var="actuacio" >
							          	<tr class=${actuacio.activa ? actuacio.aprovada ? "success" : "warning" : "danger"}>							          	
							           		<td><a href="actuacionsDetalls?ref=${actuacio.referencia}">${actuacio.referencia}</a></td>
							            	<td>${actuacio.nomCentre}</td>
							            	<td>${actuacio.descripcio}</td>
							            	<td>${actuacio.getDataCreacioString()}</td>
							            	<td>${actuacio.dataCreacio}</td>
							            	<td>${actuacio.modificacio}</td>
							            	<td>${actuacio.getDarreraModificacioString()}</td>
							            	<td>${actuacio.darreraModificacio}</td>							            	
							            	<c:if test="${!nomesActives}">
											   	<td>${actuacio.estatNom()}</td>
											   	<td>${actuacio.getDataTancamentString()}</td>
											   	<td>${actuacio.dataTancament}</td>
											</c:if>							            	
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
    <script src="js/actuacio/llistat.js"></script>
    <!-- /#wrapper -->
</body>
</html>