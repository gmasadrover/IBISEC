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
                            Aules Modulars <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Aules Modulars
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Modificar
                            </li>
                        </ol>
                    </div>
                </div>
                <form class="form-horizontal" method="POST" action="doEditAulaModular">                		
               		<h2 class="margin_bottom30">Informació Lloguer</h2>
               		<input class="hidden" name="idInforme" id="idInforme" value="${aulaModular.informe.idInf}">     
                    <input class="hidden" name="idActuacio" id="idActuacio" value="${aulaModular.informe.actuacio.referencia}">    
                    <input class="hidden" name="idInformeAutoritzat" id="idInformeAutoritzat" value="${aulaModular.informeAutoritzacio.idInf}">     
                    <input class="hidden" name="idActuacioAutoritzat" id="idActuacioAutoritzat" value="${aulaModular.informeAutoritzacio.actuacio.referencia}">   
                   	<input class="hidden" name="idInformeNoAutoritzat" id="idInformeNoAutoritzat" value="${aulaModular.noAutoritzada.idInf}">     
                    <input class="hidden" name="idActuacioNoAutoritzat" id="idActuacioNoAutoritzat" value="${aulaModular.noAutoritzada.actuacio.referencia}">         		
               		<div id="seleccionarInforme">                            		
	                    <div class="form-group">
	                        <label class="col-xs-3  control-label">Centre</label>
	                        <input class="hidden" name="idCentreActual" id=idCentreActual value="${aulaModular.informe.actuacio.centre.idCentre}"> 
	                        <div class="col-xs-3">
	                         	<select class="form-control selectpicker centresList" name="idCentre" data-live-search="true" data-size="5" id="centresList">
	                      			<option value="-1">No hi ha relació</option>
	                      		</select>
	                      	</div>
	                    </div> 
	                    <div id="incidencies"></div>    
	                    <div id="expedients"></div>
                   	</div>	
               		<div class="form-group">
                     	<label class="col-xs-3 control-label">Import previst</label>
                        <div class="col-xs-3">
                        	<input class="form-control" name="import" id="import" placeholder="0000.00" value="${aulaModular.getImportPrevist()}">
                        </div>
                    </div>	
                    <div class="form-group">
                        <label class="col-xs-3 control-label">Data límit contracte</label>
                        <div class="input-group date col-xs-3 datepicker">
						  	<input type="text" class="form-control" name="dataLimitContracte" value="${aulaModular.getDataLimitContracteString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
						</div>
                    </div>	
                    <h2 class="margin_bottom30">Informació Expedient autorització</h2>
                    <div id="seleccionarInformeAutoritzat">                            		
	                    <div class="form-group">
	                        <label class="col-xs-3  control-label">Centre</label>
	                        <input class="hidden" name="idCentreActualAutoritzat" id=idCentreActualAutoritzat value="${aulaModular.informeAutoritzacio.actuacio.centre.idCentre}"> 
	                        <div class="col-xs-3">
	                         	<select class="form-control selectpicker centresList" name="idCentreAutoritzat" data-live-search="true" data-size="5" id="centresListAutoritzat">
	                      			<option value="-1">No hi ha relació</option>
	                      		</select>
	                      	</div>
	                    </div> 
	                    <div id="incidenciesAutoritzat"></div>    
	                    <div id="expedientsAutoritzat"></div>
                   	</div>	
	            	
                	<h2 class="margin_bottom30">No autoritzat</h2>
                    <div id="seleccionarInformeNoAutoritzat">                            		
	                    <div class="form-group">
	                        <label class="col-xs-3  control-label">Centre</label>
	                        <input class="hidden" name="idCentreActualNoAutoritzat" id=idCentreActualNoAutoritzat value="${aulaModular.noAutoritzada.actuacio.centre.idCentre}"> 
	                        <div class="col-xs-3">
	                         	<select class="form-control selectpicker centresList" name="idCentreNoAutoritzat" data-live-search="true" data-size="5" id="centresListNoAutoritzat">
	                      			<option value="-1">No hi ha relació</option>
	                      		</select>
	                      	</div>
	                    </div> 
	                    <div id="incidenciesNoAutoritzat"></div>    
	                    <div id="expedientsNoAutoritzat"></div>
                   	</div>	
	            	<div class="row">
                		<div class="form-group">
					        <div class="col-md-offset-6  col-md-2">
					            <input type="submit" class="btn btn-primary" name="actualitzar" value="Actualitzar">							            
					        </div>
					        <div class="col-md-2">
					            <input type="submit" class="btn btn-danger" name="eliminar" value="Eliminar">							            
					        </div>
					    </div> 
                	</div>
                </form>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/aulesmodulars/edit.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>