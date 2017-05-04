<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}"  style="height:100%">  
<head>
	<jsp:include page="../_header.jsp"></jsp:include>	
	<script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js"></script>
	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDjjjVKq3Fyvi6XiH-Mu8veFBD49iEQJLY"></script>
</head>	
<body  style="height:100%">	
    <div id="wrapper"  style="height:100%">
    	<jsp:include page="../_menu.jsp"></jsp:include>
       	<div id="page-wrapper"  style="height:100%">
       		<div class="container-fluid" style="height:100%">
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
	       		<%-- <div class="row">
					<form class="form-horizontal" method="POST" action="actuacions">						
						<div class="form-group">						
							<input type="hidden" id="idCentreSelected" value="${idCentre}" />
							<input type="hidden" id="estatSelected" value="${estatFilter}" />
							<div class="col-lg-offset-1  col-lg-3">
							    <div class="col-lg-12">
							      <label>Filtrar per centre</label>
							      <div>
		                                <select class="form-control selectpicker" name="idCentre" data-live-search="true" id="centresList">
		                                	<option value="-1">Tots els centres</option>
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
							<div class="col-lg-offset-10 col-lg-2">
						    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-lg-offset-1 col-lg-2">
									<div class="container">
								 		<div class="circunferencia yellow">${actuacionsPendents}</div>		
								 	</div>
								</div>
								<div class="col-lg-2">
									<div class="container">						
										<div class="circunferencia blue">${actuacionsAprovadesPA}</div>
									</div>
								</div>
								<div class="col-lg-2">
									<div class="container">
										<div class="circunferencia green">${actuacionsAprovadesPT}</div>
									</div>
								</div>
								<div class="col-lg-2">
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
				</div> --%>
				<div class="row">
					<div class="col-lg-offset-9 col-lg-3">
				    	<a href='llistats?viewType=full'>Veure a pantalla completa</a>
					</div>
				</div>
				<div class="row"  style="height:500px">
					<div class="col-lg-9" style="height:100%">
			       		<div class="informacioCentres hidden">
			       			<c:forEach items="${centresList}" var="centre" >
			       				<c:if test="${centre.lat > 0 && centre.lng > 0}">
			       					<input data-idcentre="${centre.idCentre}" data-lat="${centre.lat}" data-lng="${centre.lng}" value="${centre.nom}">
			       				</c:if>
			       			</c:forEach>
			       		</div>         
						<div id="map" style="width:100%; height:100%"></div>
					</div>
					<div class="col-lg-3">
						<h4>Informació centre</h4>
						<div class="infoActuacions">
						</div>
					</div>
				</div>
			</div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/llistats/llistat.js"></script>   
    <!-- /#wrapper -->
</body>
</html>