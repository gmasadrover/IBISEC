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
							<div class="col-md-offset-1  col-md-3">
							    <div class="col-md-12">
							      <label>Filtrar per centre</label>
							      <div>
		                                <select class="form-control selectpicker" name="idCentre" data-live-search="true" id="centresList">
		                                	<option value="-1">Tots els centres</option>
		                                </select>
		                             </div>
							    </div>						    
						  	</div>	
						  	<div class="col-md-4">
						  		<div class="col-md-12">
							  		<label>Filtrar per data petició</label>
								  	<div class="input-group input-daterange datepicker">
									    <input type="text" class="form-control" name="dataInici" value="${dataInici}">
									    <div class="input-group-addon">fins</div>
									    <input type="text" class="form-control" name="dataFi" value="${dataFi}">
									</div>
									<input type="checkbox" name="filterWithOutDate" ${filterWithOutDate ? "checked" : ""}> Filtrar fora dates
								</div>                                
						  	</div>	
						  	<div class="col-md-2">
							    <div class="col-md-12">
							      <label>Filtrar per estat</label>
							      <div>
							      	<select class="selectpicker" id="estatList" name="estat">
									  <option value="-1">Qualsevol</option>
									  <option value="AprovadaPT">PT Aprovada</option>
									  <option value="AprovadaPA">PA Aprovada</option>
									  <option value="Pendent">Pendent</option>
									  <option value="Tancada">Tancada</option>
									</select>							      	
							      </div>
							    </div>
						  	</div>
						</div>	
						<div class="form-group">
							<div class="col-md-offset-10 col-md-2">
						    	<input type="submit" class="btn btn-primary loadingButton"  data-msg="Aplicant filtres..." name="filtrar" value="Aplicar Filtres">
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-offset-1 col-md-2">
									<div class="container">
								 		<div class="circunferencia yellow">${actuacionsPendents}</div>		
								 	</div>
								</div>
								<div class="col-md-2">
									<div class="container">						
										<div class="circunferencia blue">${actuacionsAprovadesPA}</div>
									</div>
								</div>
								<div class="col-md-2">
									<div class="container">
										<div class="circunferencia green">${actuacionsAprovadesPT}</div>
									</div>
								</div>
								<div class="col-md-2">
									<div class="container">
										<div class="circunferencia red">${actuacionsTancades}</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-offset-1 col-md-2">Actuacions pendents</div>							
								<div class="col-md-2">Actuacions amb proposta d'actuacions aprovades</div>
								<div class="col-md-2">Actuacions amb proposta tècnica aprovades</div>
								<div class="col-md-2">Actuacions tancades</div>
							</div>
						</div>
					</form>
				</div>
				
                <div class="row">
                    <div class="col-md-12">
                        <h2>Actuacions</h2>
                        <div class="table-responsive">
                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Referència</th>
                                        <th>Centre</th>
                                        <th>Descripció</th>
                                        <th>Data creació</th>
                                        <th>Data creació</th>                                       
                                        <th>Data PA</th>
                                        <th>Data PA</th>
										<th>Data aprovada</th>
                                        <th>Data aprovada</th>
									   	<th>Data tancament</th>
									   	<th>Data tancament</th>
									   	<th>Darrera modificació</th>
									   	<th>Data modificació</th>
									   	<th>Data modificació</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${actuacionsList}" var="actuacio" >
                                		<c:if test="${!(actuacio.centre.idCentre == '9999PERSO' && !canViewPersonal)}">
								          	<tr class=${actuacio.isActiva() ? actuacio.isPaAprovada() ? actuacio.isAprovada() ? "success" : "info" : "warning" : "danger"}>							          	
								           		<td><a href="actuacionsDetalls?ref=${actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${actuacio.referencia}</a></td>
								            	<td>${actuacio.centre.getNomComplet()}</td>
								            	<td>${actuacio.descripcio}</td>
								            	<td>${actuacio.getDataCreacioString()}</td>
								            	<td>${actuacio.dataCreacio}</td>		
								            	<td>${actuacio.getDataAprovarPaString()}</td>
								            	<td>${actuacio.dataAprovarPa}</td>		
								            	<td>${actuacio.getDataAprovacioString()}</td>
								            	<td>${actuacio.dataAprovacio}</td>
											   	<td>${actuacio.getDataTancamentString()}</td>
											   	<td>${actuacio.dataTancament}</td>
											   	<td>${actuacio.modificacio}</td>
											   	<td>${actuacio.getDarreraModificacioString()}</td>
											   	<td>${actuacio.darreraModificacio}</td>				            	
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
    <script src="js/actuacio/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>