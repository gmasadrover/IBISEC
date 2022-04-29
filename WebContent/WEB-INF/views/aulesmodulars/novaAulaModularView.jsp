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
                            Aules Modulars <small>Crear</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Aules Modulars
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Crear
                            </li>
                        </ol>
                    </div>
                </div>
                <form class="form-horizontal" method="POST" action="doCreateAulaModular">                		
               		<h2 class="margin_bottom30">Informació Lloguer</h2>              		
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
                    <div class="row">
                		<div class="form-group">
					        <div class="col-md-offset-8  col-md-2">
					            <input type="submit" class="btn btn-primary" name="crear" value="Crear">							            
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
    <script src="js/aulesmodulars/crear.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>