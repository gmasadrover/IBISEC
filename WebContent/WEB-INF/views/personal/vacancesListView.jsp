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
                            Personal <small>Vacances</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Personal
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Vacances
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
				<div class="row">
					<form class="form-horizontal" method="POST" action="vacancesList">						
						<div class="form-group">						
							<input type="hidden" id="idUsuariSelected" value="${idUsuariFilter}" />
							<input type="hidden" id="estatSelected" value="${estatFilter}" />
							<div class="col-md-offset-1  col-md-3">
							    <div class="col-md-12">
							      <label>Filtrar per usuari</label>
							      <div>
		                                <select class="form-control selectpicker" name="idUsuari" data-live-search="true" id="usuarisList">
		                                	<option value="-1">Tots els usuaris</option>
		                                	<c:forEach items="${llistaUsuaris}" var="usuari" >
		                                		<option value='${usuari.idUsuari}'>${usuari.getNomCompletReal()}</option>
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
							    <div class="col-md-12">
							      <label>Filtrar per estat</label>
							      <div>
							      	<select class="selectpicker" id="estatList" name="estat">
									  <option value="-1">Qualsevol</option>
									  <option value="Aprovades">Aprovades</option>
									  <option value="Vistiplau">Amb vistiplau</option>
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
					</form>
				</div>
				<div class="row">						
					<div class="col-md-3">
						<span>Dies disponibles vacances: ${diesDisponiblesVacances}</span>
             			<br>
             			<span>Dies disponibles P7: ${diesDisponiblesP7}</span>
					</div>
				</div>
                <div class="row">
                    <div class="col-md-12">
                        <h2>Vacances</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Usuari</th>
                                        <th>Període</th>
                                        <th>Data inici</th>
                                        <th>Tipus</th>
                                        <th>Motiu</th>
                                        <th>Data aprovació</th>
                                        <th>Data aprovació</th>
                                        <th>Data vistiplau</th> 
                                        <th>Data vistiplau</th>  
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${vacancesList}" var="reserva" >
							          	<tr>
							            	<td>${reserva.nomUsuari}</td>
							            	<td>${reserva.getDataIniciString()} a ${reserva.getDataFiString()}</td>
							            	<td>${reserva.dataInici}</td>
							            	<td>${reserva.tipus}</td>							            	
							            	<td>${reserva.motiu}</td>
							            	<td>${reserva.getAutoritzacioString()}</td>	
							            	<td>${reserva.autoritzacio}</td>		
							            	<td>${reserva.getVistiplauString()}</td>	
							            	<td>${reserva.vistiplau}</td>		            	
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
    <script src="js/personal/llistatVacances.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>