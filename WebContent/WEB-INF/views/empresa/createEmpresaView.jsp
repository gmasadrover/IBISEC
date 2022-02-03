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
                            Empresa <small>Afegir empresa</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Empresa
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Registre
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
                    	
		    			<form class="form-horizontal" method="POST" action="doCreateEmpresa">
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">CIF</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="cif" placeholder="cif">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Nom</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="name" placeholder="nom" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Direcció</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="direccio" placeholder="direcció">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">CP</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="cp" placeholder="00000">
                                </div>
                            </div>	                            	
                            <div class="form-group">
                                <label class="col-xs-3  control-label">Provincia</label>
                                <div class="col-xs-3">
	                                <select class="form-control" name="provincia" id="provinciesList">
	                                </select>
	                             </div>
                            </div>
                            <div class="form-group">                            
                                <label class="col-xs-3 control-label">Localitat</label>
                                <div class="col-xs-3">
	                                <select class="form-control" name="localitat" id="localitatsList">
	                                </select>
	                             </div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Teléfon</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="telefon" placeholder="123456789">
                                </div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Fax</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="fax" placeholder="123456789">
                                </div>
                            </div>	
                           <!-- <div class="form-group">
                                <label class="col-xs-3 control-label">email</label>
                                <div class="col-xs-3">
                                	<input class="form-control" type="email" name="email" placeholder="test@test.es">
                                </div>
                            </div>	--> <!--Ocult a petició M.Garcia en data 10/11/21--> 
                             <div class="form-group">
                                <label class="col-xs-3 control-label">Tipus</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="tipus" >
                                </div>
                            </div>	
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
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>