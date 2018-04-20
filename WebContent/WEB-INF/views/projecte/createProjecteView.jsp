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
                           Projecte <small> Nou projecte</small>
                        </h1>
                        <ol class="breadcrumb">
					        <li class="active">
                                <i class="fa fa-dashboard"></i> Projecte
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Nou projecte
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
		    			<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoCreateProjecte">
		    				 <div class="form-group">
                                <label class="col-xs-3 control-label">Projecte</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="nomProjecte" placeholder="nom projecte" value=""/>
                                </div>
                            </div>     
		    				<div class="form-group">
                                <label class="col-xs-3  control-label">Centre</label>
                                <div class="col-xs-3">
	                                <select class="form-control selectpicker centresList" name="idCentre" data-live-search="true" data-size="5" id="centresList">
		                            	<option value="-1">No hi ha relació</option>
		                            </select>
	                             </div>
                            </div>                  
                            <div class="form-group">
                                <label class="col-xs-3  control-label">Participants</label>
                                <div class="col-xs-3">
	                                <select class="form-control selectpicker usuarisList" name="idUsuari" data-live-search="true" data-size="5" id="usuarisList" multiple>
	                                	<option value='-1'>No hi ha participants</option>
	                                	<c:forEach items="${llistaUsuaris}" var="usuari" >
	                                		<option value='${usuari.idUsuari}'>${usuari.getNomCompletReal()}</option>	                                		
	                                	</c:forEach>	                                		                                
	                                </select>
	                             </div>
                            </div>   
                            <div id="participants"></div>                    	
                            <br>
						    <div class="form-group">
						        <div class="col-xs-offset-3 col-xs-9">
						            <input type="submit" class="btn btn-primary" value="Crear">
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
    <script src="js/projecte/newProjecte.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>