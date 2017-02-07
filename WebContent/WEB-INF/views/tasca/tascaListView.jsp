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
                            Tasques <small>Llistat</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Tasques
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
 
				<div class="row">
					<form class="form-horizontal" method="POST" action="tascaList">	
						<input type="hidden" id="usuariSelected" value="${usuariSelected}"/>					
						<div class="form-group">
							<div class="col-lg-offset-1  col-lg-3">
								<label>Usuari / Àrea</label>
							    <select class="form-control selectpicker" name="idUsuari" data-live-search="true" id="usuarisList">
                                	<c:forEach items="${llistaUsuaris}" var="usuari" >
                                		<option value='${usuari.idUsuari}'>${usuari.getNomComplet()}</option>
                                	</c:forEach>
                                	<option data-divider="true"></option>
                                		<option value='gerencia'>Gerència</option>
                                		<option value='juridica'>Assessoria Jurídica</option>
                                		<option value='obres'>Obres , Projectes i Supervisió</option>
                                		<option value='comptabilitat'>Administració i comptabilitat</option>
                                		<option value='instalacions'>Instal·lacions i Manteniment</option>                               
                                </select>				    
						  	</div>						  	
						 </div>
						 <div class="form-group"> 	
						  	<div class="col-lg-offset-1 col-lg-2">
						    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
							</div>
						</div>	
					</form>
				</div>

                <div class="row">
                    <div class="col-lg-12">
                        <h2>Tasques</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>                                        
                                        <th>Tasca</th>
                                        <th>id Actuació</th>
                                        <th>id Incidència</th>
                                        <th>Centre</th>                                        
                                        <th>Data creació</th>
                                        <th>Data creació</th>
                                        <th>Responsable</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${tasquesList}" var="tasca" >
							          	<tr class="${tasca.activa ? "success" : "danger"}">							          	
							           		<td><a href="tasca?id=${tasca.idTasca}">${tasca.idTasca} - ${tasca.descripcio}</a></td>
							            	<td><a href="actuacionsDetalls?ref=${tasca.actuacio.referencia}">${tasca.actuacio.referencia}</a></td>
							            	<td><a href="incidenciaDetalls?ref=${tasca.incidencia.idIncidencia}">${tasca.incidencia.idIncidencia}</a></td>
							            	<td>${tasca.actuacio.nomCentre}</td>							            	
							            	<td>${tasca.getDataCreacioString()}</td>
							            	<td>${tasca.dataCreacio}</td>	
							            	<td>${tasca.usuari.getNomComplet()}					            	
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
    <script src="js/tasca/llistat.js"></script>
    <!-- /#wrapper -->
</body>
</html>