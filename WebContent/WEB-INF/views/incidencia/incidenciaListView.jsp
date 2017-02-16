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
                            Incidències <small>Incidències</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Incidències
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
				<div class="row">
					<form class="form-horizontal" method="POST" action="incidencies">						
						<div class="form-group">
							<input type="hidden" id="idCentreSelected" value="${idCentre}" />
							<div class="col-lg-offset-1  col-lg-3">
							    <div class="checkbox">
							      <label>
							        <input type="checkbox" name="filterCentre" ${idCentre != "" ? "checked" : ""}> Filtrar per centre
							      </label>
							      <div>
		                                <select class="form-control selectpicker" name="idCentre" data-live-search="true" data-size="5" id="centresList">
		                                </select>
		                             </div>
							    </div>						    
						  	</div>		
						  	<div class="col-lg-4">
						  		<span>Data registre</span>
							  	<div class="input-group input-daterange datepicker">
								    <input type="text" class="form-control" name="dataInici" value="${dataInici}">
								    <div class="input-group-addon">fins</div>
								    <input type="text" class="form-control" name="dataFi" value="${dataFi}">
								</div>                                
						  	</div>
						  	<div class="col-lg-2">
							    <div class="checkbox">
							      <label>
							        <input type="checkbox" name="nomesActives" ${nomesActives ? "checked" : ""}> Només actives
							      </label>
							    </div>
						  	</div>						  				 
						  	<div class="col-lg-2">
						    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
							</div>
						</div>	
					</form>
				</div>				
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Incidencies</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable ${nomesActives ? "normal" : "withTancades"}">
                                <thead>
                                    <tr>
                                        <th>Referència</th>
                                        <th>Descripció</th>
                                        <th>Centre</th>
                                        <th>Data petició</th>
                                        <th>Data petició</th>
                                        <th>Actuacions derivades</th>
                                        <c:if test="${!nomesActives}">											
										   	<th>Data tancament</th>
										   	<th>Data tancament</th>
										</c:if>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${incidenciesList}" var="incidencia" >
							          	<tr class=${incidencia.activa ? "success" : "danger"}>							          	
							           		<td><a href="incidenciaDetalls?ref=${incidencia.idIncidencia}">${incidencia.idIncidencia}</a></td>
							            	<td>${incidencia.descripcio}</td>
							            	<td>${incidencia.nomCentre}</td>
							            	<td>${incidencia.getPeticioString()}</td>
							            	<td>${incidencia.usuMod}</td>
							            	<td>${incidencia.getLlistaIdActuacions()}</td>
							            	<c:if test="${!nomesActives}">
											   	<td>${incidencia.getTancamentString()}</td>
											   	<td>${incidencia.dataTancament}</td>
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
    <script src="js/incidencia/llistat.js"></script>
    <!-- /#wrapper -->
</body>
</html>