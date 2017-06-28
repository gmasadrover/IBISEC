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
                            Actuacions <small>Nova actuació</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Actuacions
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Nova
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <div class="row">
                	<div class="col-md-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <!-- /.row -->
                
    			<div class="row">
                    <div class="col-md-12">                    	                    	
		    			<form class="form-horizontal" method="POST" action="DoCreateActuacio">
		    				<input type="hidden" id="idCentreSelected" value="${incidencia.idCentre}">
		    				<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">Referència</label>
                                <div class="col-xs-3">
                                	<input class="form-control" value="${nouCodi}" disabled>
                                	<input type="hidden"  class="form-control" name="referencia" value="${nouCodi}">
                                </div>
                            </div>  
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Centre Incidencia</label>
                                <div class="col-xs-3">
                                	<input class="form-control" id="" value="${incidencia.nomCentre}" disabled>                                	
                                </div>
                            </div>  			
		    				<div class="form-group">
                                <label class="col-xs-3  control-label">Centre Actuació</label>
                                <div class="col-xs-3">
	                                <select class="form-control selectpicker" name="idCentre" data-live-search="true" data-size="5" id="centresList">
	                                </select>
	                             </div>
                            </div>
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">Descripció</label>
                                <div class="col-xs-3">
                                	<textarea class="form-control" name="descripcio" placeholder="descripció" rows="3">${incidencia.descripcio}</textarea>
                                </div>
                            </div>			    				
                            <!-- <div class="form-group">
                                <label class="col-xs-3  control-label">Asignar</label>
                                <div class="col-xs-3">
	                                <select class="form-control selectpicker" name="idUsuari" data-live-search="true" id="usuarisList">
	                                </select>
	                             </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Comentari asignació</label>
                                <div class="col-xs-3">
                                	<textarea class="form-control" name="comentari" placeholder="comentari inter" rows="3">Revisar</textarea>
                                </div>
                            </div>	 -->
                            <br>
						    <div class="form-group">
						        <div class="col-xs-offset-3 col-xs-9">
						            <input type="submit" class="btn btn-primary" value="Guardar">
						            <input type="reset" class="btn btn-default" value="Reiniciar">
						        </div>
						    </div>    				
		    			</form>
                    </div>
                </div>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/usuari/usuari.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/actuacio/crear.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>