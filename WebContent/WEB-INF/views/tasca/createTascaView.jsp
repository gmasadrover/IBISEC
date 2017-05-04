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
                            <c:choose>
							    <c:when test="${tipus=='notificacio'}">
							        Notificació
							    </c:when>
							    <c:otherwise>
							       Tasca
							    </c:otherwise>
							</c:choose> 
                            <small>
                            	<c:choose>
								    <c:when test="${tipus=='infPrev'}">
								        Sol·licitud informe
								    </c:when>
								    <c:when test="${tipus=='notificacio'}">
								        Nova notificació
								    </c:when>
								    <c:otherwise>
								       Nova tasca
								    </c:otherwise>
								</c:choose>
                            </small>
                        </h1>
                        <ol class="breadcrumb">
                        	<c:choose>
							    <c:when test="${tipus=='notificacio'}">
							        <li class="active">
		                                <i class="fa fa-dashboard"></i> Notificació
		                            </li>
		                            <li class="active">
		                                <i class="fa fa-table"></i> Nova notificació
		                            </li>
							    </c:when>
							    <c:otherwise>
							       	<li class="active">
		                                <i class="fa fa-dashboard"></i> Tasca
		                            </li>
		                            <li class="active">
		                                <i class="fa fa-table"></i> Nova tasca
		                            </li>
							    </c:otherwise>
							</c:choose>                             
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
                
    			<div class="row">
                    <div class="col-lg-12">                    	
		    			<form class="form-horizontal" method="POST" action="DoCreateTasca">
		    				<div class="form-group">
		    					<input type="hidden" name="idActuacio" value="${idActuacio}">
		    					<input type="hidden" name="idIncidencia" value="${idIncidencia}">
		    					<input type="hidden" name="tipus" value="${tipus}">
		    					<input type="hidden" name="referencia" value="${nouCodi}">                       
                            </div>                            
                            <c:choose>
							    <c:when test="${tipus=='infPrev'}">	
							    </c:when>
							    <c:otherwise>
							       	<div class="form-group">
		                                <label class="col-xs-3  control-label">Assumpte</label>
		                                <div class="col-xs-3">
			                                <input class="form-control" name="assumpte" placeholder="assumpte">
			                             </div>
		                            </div>		                            
							    </c:otherwise>
							</c:choose> 
						    <div class="form-group">
                                <label class="col-xs-3 control-label">Assumpte</label>
                                <div class="col-xs-3">
                                	<textarea class="form-control" name="comentari" placeholder="comentari inter" rows="3">Revisar</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3  control-label">Asignar</label>
                                <div class="col-xs-3">
	                                <select class="form-control selectpicker" name="idUsuari" data-live-search="true" id="usuarisList">
	                                	<c:forEach items="${llistaUsuaris}" var="usuari" >
	                                		<option value='${usuari.idUsuari}'>${usuari.getNomCompletReal()}</option>	                                		
	                                	</c:forEach>
	                                	<option data-divider="true"></option>
                                		<option value='gerencia'>Gerència</option>
                                		<option value='juridica'>Assessoria Jurídica</option>
                                		<option value='obres'>Obres , Projectes i Supervisió</option>
                                		<option value='comptabilitat'>Administració i comptabilitat</option>
                                		<option value='instalacions'>Instal·lacions i Manteniment</option>              	                                
	                                </select>
	                             </div>
                            </div>                     	
                            <br>
						    <div class="form-group">
						        <div class="col-xs-offset-3 col-xs-9">
						            <input type="submit" class="btn btn-primary" value="Enviar">
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