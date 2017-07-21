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
                            Crèdit <small>despeses</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Despeses
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
				<div class="row">
					<form class="form-horizontal" method="POST" action="despeses">						
						<div class="form-group">
							<input type="hidden" id="idPartidaSelected" value="${idPartida}" />
							<div class="col-md-offset-1  col-md-3">
							    <div class="col-md-12">
							      <label>Filtrar per centre</label>
							      <div>
		                                <select class="form-control selectpicker" name="idPartida" data-size="10" id="partidesList" data-live-search="true" >
		                                	<option value="-1">No filtrar partida</option>
		                                	<c:forEach items="${llistaPartides}" var="partida" >		                                		
		                                		<option value="${partida.codi}">${partida.nom}</option>
		                                	</c:forEach>
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
						    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
							</div>
						</div>	
					</form>
				</div>							
                <div class="row">
                    <div class="col-md-12">
                        <h2>Despeses</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Referència</th>
                                        <th>Descripció</th>
                                        <th>Actuacio</th>
                                        <th>Valor PA</th>
                                        <th>Valor PA</th>
                                        <th>Valor PD</th>
                                        <th>Valor PD</th>
                                        <th>Partida</th>                                       
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${despesesList}" var="despesa" >
							          	<tr>							          	
							           		<td>${despesa.idAssignacio}</td>
							            	<td>${despesa.comentari}</td>
							            	<td><a href="actuacionsDetalls?ref=${despesa.idActuacio}">${despesa.idActuacio}</a></td>
							            	<td>${despesa.getValorPAFormat()}</td>
							            	<td>${despesa.valorPA}</td>
							            	<td>${despesa.getValorPDFormat()}</td>
							            	<td>${despesa.valorPD}</td>
							            	<td><a href="partidaDetalls?codi=${despesa.idPartida}">${despesa.idPartida}</a></td>								            				            	
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
    <script src="js/credit/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>