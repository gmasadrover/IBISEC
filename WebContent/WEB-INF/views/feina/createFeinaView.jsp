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
                            Feines <small>Nova feina</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Feines
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
		    			<form class="form-horizontal" method="POST" action="DoCreateFeina">
		    				<input type="hidden" name="idActuacio" value="${idActuacio}">		    				
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Remitent</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="remitent" placeholder="remitent" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Destinatari</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="destinatari" placeholder="destinatari" required>
                                </div>
                            </div>	
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">Contingut</label>
                                <div class="col-xs-3">
                                	<textarea class="form-control" name="contingut" placeholder="contingut" rows="3"></textarea>
                                </div>
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3 control-label">Notes</label>
                                <div class="col-xs-3">
                                	<textarea class="form-control" name="notes" placeholder="notes" rows="3"></textarea>
                                </div>
                            </div>		
                            <br>
						    <div class="form-group">
						        <div class="col-xs-offset-3 col-xs-9">
						            <input type="submit" class="btn btn-primary loadingButton"  data-msg="guardant feina..." value="Guardar">
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
</body>
</html>