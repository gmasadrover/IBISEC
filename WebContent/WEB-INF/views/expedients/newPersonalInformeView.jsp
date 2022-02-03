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
		    				<input type="hidden" name="idInforme" value="${idInforme}"> 		
		    				<input type="hidden" name="idActuacio" value="${idActuacio}"> 		    			
		    				<div class="form-group">	                	 		
								<label class="col-xs-3  control-label">Usuari</label>	
								<div class="col-xs-3">					            	 										            	 	
					                <select class="form-control selectpicker" data-live-search="true" data-size="5" name="llistaUsuaris" id="llistaUsuaris">
					                	<option value="0">Seleccionar usuari</option>
					                	<c:forEach items="${llistaUsuaris}" var="usuari">
					                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
					                	</c:forEach>
					                	<option value="-1">Extern</option>					                                	
					                </select>
				                </div>
							</div>
							<div class="form-group llistaEmpresesDiv hidden">
								<label class="col-xs-3  control-label">Empresa</label>							            	 										            	 	
				                <div class="col-xs-3">	
					                <select class="selectpicker" name="llistaEmpreses" id="llistaEmpreses" data-live-search="true" data-size="5">						                                					                                	
					               		<option value="-1">Seleccionar opció</option>
					               		<c:forEach items="${empresesList}" var="empresa">
					                   		<option value="${empresa.cif}">${empresa.name}</option>
					                   	</c:forEach>	
					                </select>	
					        	</div>	
							</div>
							<div class="form-group">
								<label class="col-xs-3  control-label">Tècnic</label>							            	 										            	 	
				                <div class="col-xs-3">	
					                <select class="form-control selectpicker" name="llistaTecnic" id="llistaTecnic">
					                	<option value="-1">Seleccionar opció</option>
					                	<option value="Arquitecte">Arquitecte</option>
					                	<option value="Arquitecte tècnic">Arquitecte tècnic</option>	
					                	<option value="Enginyer">Enginyer</option>	
					                	<option value="Ajudant d'instal·lacions">Ajudant d'instal·lacions</option>		
					                	<option value="Delineant">Delineant</option>			   					                			                					                                	
					                </select>
					        	</div>	
							</div>
							<div class="form-group">
								<label class="col-xs-3  control-label">Funció</label>							            	 										            	 	
				                <div class="col-xs-3">	
					                <select class="form-control selectpicker" name="llistaFuncions" id="llistaFuncions">
					                	<option value="-1">Seleccionar opció</option>
					                	<option value="Director">Director projecte</option>
					                	<option value="Redactor">Redactor</option>	
					                	<option value="Supervisor">Supervisor</option>			   
					                	<option value="Direcció">Direcció obra</option>			   
					                	<option value="Responsable contracte">Responsable contracte</option>
					                		                					                                	
					                </select>
					        	</div>	
							</div>
                            <br>
						    <div class="form-group">
						        <div class="col-xs-offset-3 col-xs-2">
						            <input type="submit" name="afegir" class="btn btn-success" value="Guardar i afegir més">
						        </div>
						        <div class=" col-xs-3">
						            <input type="submit" name="guardar" class="btn btn-primary" value="Guardar i sortir">
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
 	<script src="js/personalinforme/create.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>