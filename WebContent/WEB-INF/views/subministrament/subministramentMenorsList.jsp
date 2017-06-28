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
                            Contractes <small>Menors</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Contractes Menors
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
				<div class="row">
					<form class="form-horizontal" method="POST" action="subministramentMenors">						
						<div class="form-group">
							<div class="form-group">						
								<input type="hidden" id="idCentreSelected" value="${idCentre}" />
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
							</div>	
							<div class="form-group">
								<div class="col-md-offset-10 col-md-2">
							    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
								</div>
							</div>
						</div>	
					</form>
				</div>
				
                <div class="row">
                    <div class="col-md-12">
                        <h2>Contractes menors</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Actuació</th>                                        
                                        <th>Centre</th>
                                        <th>Descripció</th>
                                        <th>Preu adjudicació</th>  
                                        <th>informe</th>
                                        <th>Empresa</th>    
                                        <th>Valor Pagat</th>  
                                        <th>Notes</th>                                  
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${obresList}" var="obra" >
							          	<tr class=${obra.dataTancament != null ? "danger" : "info"}>
							            	<td>${obra.idActuacio}</td>
							            	<td>${obra.nomCentre}</td>
							            	<td>${obra.descripcio}</td>
							            	<td>${obra.valor}</td>
							            	<td>${obra.idInforme}</td>
							            	<td>${obra.empresa.name}</td>
							            	<td>${obra.getTotalFacturat()}</td>		
							            	<td>${obra.notes}</td>							            				            	
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
    <script src="js/subministrament/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>