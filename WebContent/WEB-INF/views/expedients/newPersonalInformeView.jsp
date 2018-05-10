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
                           Expedient<small> Nou participant</small>
                        </h1>
                        <ol class="breadcrumb">
					       	<li class="active">
                                <i class="fa fa-dashboard"></i> Expedient
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Nou participant
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
		    			<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoEditPersonalExpedient">
		    				<input type="hidden" name="idInforme" value="${informe.idInf}"> 
		    			
		    				<div class="form-group">	                	 		
								<div class="col-md-6">
									<label>Tècnic</label>						            	 										            	 	
					                <select class="form-control selectpicker" data-live-search="true" data-size="5" name="llistaUsuaris" id="llistaUsuaris">
					                	<c:forEach items="${llistaUsuaris}" var="usuari">
					                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
					                	</c:forEach>					                                	
					                </select>	
								</div>
								<div class="col-md-6">
									<label>Funció</label>							            	 										            	 	
					                <select class="form-control selectpicker" name="llistaFuncions" id="llistaFuncions">
					                	<option value="Responsable contracte">Responsable contracte</option>
					                	<option value="Arquitecte">Arquitecte</option>
					                	<option value="Arquitecte Tècnic">Arquitecte Tècnic</option>	
					                	<option value="Enginyer">Enginyer</option>			                					                                	
					                </select>	
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
</body>
</html>