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
                            Centre <small>Crear</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Centre
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Modificar
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
              	<h2 class="margin_bottom30">Modificar dades centre</h2>
	    		<form class="form-horizontal" method="POST" action="DoEditCentre">		
	    			<input type="hidden" name="codi" value="${centre.idCentre}">
	    			<div class="form-group">
                        <label class="col-xs-3 control-label">Nom</label>
                        <div class="col-xs-3">                        
                        	<input class="form-control" name="nom" value="${centre.nom}">
                        </div>
                    </div>   
                    <div class="form-group">
                        <label class="col-xs-3 control-label">Tipus</label>
                        <div class="col-xs-3">                        
                        	<input class="form-control" name="tipo" value="${centre.tipo}">
                        </div>
                    </div>    
                    <div class="form-group">
                        <label class="col-xs-3 control-label">Direcció</label>
                        <div class="col-xs-3">                        
                        	<input class="form-control" name="direccio" value="${centre.adreca}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">CP</label>
                        <div class="col-xs-3">                        
                        	<input class="form-control" name="cp" value="${centre.getCp()}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">Illa</label>
                        <div class="col-xs-3">                        
                        	<input class="form-control" name="illa" value="${centre.illa}">
                        </div>
                    </div>                   
                    <div class="form-group">
                        <label class="col-xs-3 control-label">Municipi</label>
                        <div class="col-xs-3">                        
                        	<input class="form-control" name="municipi" value="${centre.municipi}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">Localitat</label>
                        <div class="col-xs-3">                        
                        	<input class="form-control" name="localitat" value="${centre.localitat}">
                        </div>
                    </div>
                    <br>
				    <div class="form-group">
				        <div class="col-xs-offset-3 col-xs-9">
				            <input type="submit" class="btn btn-primary" value="Guardar">
				        </div>
				    </div>    		            	
               	</form>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/centres/detalls.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>