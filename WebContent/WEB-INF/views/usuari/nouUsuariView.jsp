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
                            Usuari <small>Nou usuari</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Usuari
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Nou usuari
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
		    			<form class="form-horizontal" method="POST" action="DoCreateUsuari">		    						  
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Usuari</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="usuari" placeholder="usuari" required>
                                </div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3  control-label">Nom</label>
                                <div class="col-xs-3">
	                                <input class="form-control" name="nom" placeholder="nom">
	                             </div>
                            </div>		
                            <div class="form-group">
                                <label class="col-xs-3  control-label">Cognoms</label>
                                <div class="col-xs-3">
	                                <input class="form-control" name="cognoms" placeholder="cognoms">
	                             </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3  control-label">Departament</label>
                                <div class="col-xs-3">
	                               <select class="form-control selectpicker" name="departament" id="departament">
	                                	<option value='gerencia'>Gerència</option>
	                                	<option value='juridica'>Assessoria Jurídica</option>
	                                	<option value='obres'>Projectes, Obres i Supervisió</option>
	                                	<option value='comptabilitat'>Administració i comptabilitat</option>
	                                	<option value='instalacions'>Instal·lacions i Manteniment</option> 
	                                </select>
	                             </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3  control-label">Càrreg</label>
                                <div class="col-xs-3">
	                                <input class="form-control" name="carreg" placeholder="càrreg">
	                             </div>
                            </div>
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
</body>
</html>