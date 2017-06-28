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
	       		<div class="row">
					<form class="form-horizontal" method="POST" action="llistats">						
						<div class="form-group">				
							<input type="hidden" id="tipusSelected" value="${tipusFilter}" />
							<input type="hidden" id="estatSelected" value="${estatFilter}" />
							<div class="col-md-offset-1  col-md-3">
							    <div class="col-md-12">
							      <label>Tipus actuació</label>
							      <div>
		                                <select class="form-control selectpicker" name="tipus" data-live-search="true" id="tipusList">
		                                	<option value="-1">Qualsevol</option>
		                                	<option value="obra menor">Obra Menor</option>
		                                	<option value="obra major">Obra Major</option>
		                                </select>
		                             </div>
							    </div>						    
						  	</div>	
						  	<div class="col-md-4">
						  		<div class="col-md-12">
							  		<label>Data petició del centre</label>
								  	<div class="input-group input-daterange datepicker">
									    <input type="text" class="form-control" id="dataInici" name="dataInici" value="${dataInici}">
									    <div class="input-group-addon">fins</div>
									    <input type="text" class="form-control" id="dataFi" name="dataFi" value="${dataFi}">
									</div>
									<input type="checkbox" id="filterWithOutDate" name="filterWithOutDate" ${filterWithOutDate ? "checked" : ""}> Filtrar fora dates
								</div>                                
						  	</div>	
						  	<div class="col-md-2">
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
						</div>	
						<div class="form-group">
							<div class="col-md-offset-1 col-md-3">
						  		<div class="col-md-12">
							  		<label>Data execució</label>
								  	<div class="input-group input-daterange datepicker">
									    <input type="text" class="form-control" id="dataIniciExec" name="dataIniciExec" value="${dataIniciExec}">
									    <div class="input-group-addon">fins</div>
									    <input type="text" class="form-control" id="dataFiExec" name="dataFiExec" value="${dataFiExec}">
									</div>
									<input type="checkbox" id="filterWithOutDateExec" name="filterWithOutDateExec" ${filterWithOutDateExec ? "checked" : ""}> Filtrar fora dates
								</div>                                
						  	</div>	
						  	<div class="col-md-offset-5 col-md-2">
						    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
							</div>
						</div>
						<div class="form-group">
							
						</div>						
					</form>
				</div>	       		
				<div class="row">
					<div class="col-md-offset-9 col-md-3">
				    	<a href='llistats?viewType=full&filtrar=filtrar&estat=${estatFilter}&tipus=${tipusFilter}${filterWithOutDate ? "&filterWithOutDate=on" : ""}&dataInici=${dataInici}&dataFi=${dataFi}${filterWithOutDateExec ? "&filterWithOutDateExec=on" : ""}&dataIniciExec=${dataIniciExec}&dataFiExec=${dataFiExec}'>Veure a pantalla completa</a>
					</div>
				</div>
				<div class="row"  style="height:500px">
					<div class="col-md-12" style="height:100%">
			       		<div class="informacioCentres hidden">
			       			<c:forEach items="${centresList}" var="centre" >
			       				<c:if test="${centre.lat > 0 && centre.lng > 0}">
			       					<input data-idcentre="${centre.idCentre}" data-lat="${centre.lat}" data-lng="${centre.lng}" value="${centre.nom}">
			       				</c:if>
			       			</c:forEach>
			       		</div>         
						<div id="map" style="width:100%; height:100%"></div>
					</div>					
				</div>
			</div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/llistats/llistat.js?<%=application.getInitParameter("datakey")%>"></script>   
    <!-- /#wrapper -->
</body>
</html>