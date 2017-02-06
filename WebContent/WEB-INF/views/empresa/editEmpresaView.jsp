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
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Empresa <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Empresa
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Modificar
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <div class="row">
                	<div class="col-lg-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <!-- /.row -->
                <c:if test="${not empty empresa}">
			    	<div class="row">
	                    <div class="col-lg-12">	                    	
			    			<form class="form-horizontal" method="POST" action="doEditEmpresa">
			    				<div class="form-group">
	                                <label class="col-xs-3 control-label">CIF</label>
	                                <div class="col-xs-3">
	                                	<input class="form-control" value="${empresa.cif}" disabled>
	                                	<input type="hidden" name="cif" value="${empresa.cif}">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Nom</label>
	                                <div class="col-xs-3">
	                                	<input class="form-control" name="name" placeholder="nom" value="${empresa.name}">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Direcció</label>
	                                <div class="col-xs-3">
	                                	<input class="form-control" name="direccio" placeholder="direcció" value="${empresa.direccio}">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">CP</label>
	                                <div class="col-xs-3">
	                                	<input class="form-control" name="cp" placeholder="00000" value="${empresa.getCP()}">
	                                </div>
	                            </div>	                            	
	                            <div class="form-group">
	                                <label class="col-xs-3  control-label">Provincia</label>
	                                <input  type="hidden" class="provinciaSelected" value="${empresa.provincia}">
	                                <div class="col-xs-3">
		                                <select class="form-control" name="provincia" id="provinciesList">
		                                </select>
		                             </div>
	                            </div>
	                            <div class="form-group">                            
	                                <label class="col-xs-3 control-label">Localitat</label>
	                                <input  type="hidden" class="localitatSelected" value="${empresa.ciutat}">
	                                <div class="col-xs-3">
		                                <select class="form-control" name="localitat" id="localitatsList">
		                                </select>
		                             </div>
	                            </div>	
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Teléfon</label>
	                                <div class="col-xs-3">
	                                	<input class="form-control" name="telefon" placeholder="123456789" value="${empresa.telefon}">
	                                </div>
	                            </div>	
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Fax</label>
	                                <div class="col-xs-3">
	                                	<input class="form-control" name="fax" placeholder="123456789" value="${empresa.fax}">
	                                </div>
	                            </div>	
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">email</label>
	                                <div class="col-xs-3">
	                                	<input class="form-control" required type="email" name="email" placeholder="test@test.es" value="${empresa.email}">
	                                </div>
	                            </div>	
			    				<br>
							    <div class="form-group">
							        <div class="col-xs-offset-3 col-xs-9">
							            <input type="submit" class="btn btn-primary" value="Guardar">							            
							        </div>
							    </div>    				
			    			</form>
	                    </div>
	                </div>
                </c:if>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/zones/zones.js"></script>
</body>
</html>