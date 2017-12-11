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
                            Bastanteo <small>Crear</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Bastanteo
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Crear
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
               	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doCreateBastanteo">                		
               		<h2 class="margin_bottom30">Nou Bastanteo</h2>                		              		
		    		<div class="row">			    				    				    		
	                    <div class="col-md-6">	  
	                    	<div class="form-group">
                                <label class="col-xs-3 control-label">Data bastanteo</label>
                                <div class="input-group date col-xs-6 datepicker">
								  	<input type="text" class="form-control" name="dataBastanteo" value="${bastanteo.getDatabastanteoString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>
                            <div class="form-group">    
                            	<label class="col-xs-3 control-label">Escritura</label>
                            	<textarea class="col-xs-6" name="escritura" placeholder="escritura" rows="3">${bastanteo.escritura}</textarea>
	                        </div>
	                        <div class="form-group">  
                            	<label class="col-xs-3 control-label">Càrrec</label>
                            	<textarea class="col-xs-6" name="carrec" placeholder="carrec" rows="3">${bastanteo.carrec}</textarea>
	                        </div>   
	                        <div class="form-group">
								<label class="col-xs-3 control-label">Num Protocol</label>
								<input class="col-xs-6" name="nProtocol" value="${bastanteo.numProtocol}">						
							</div>	
							<div class="form-group">
								<label class="col-xs-3 control-label">Procedència</label>
								<input class="col-xs-6" name="procedencia" value="${bastanteo.procedencia}">						
							</div>	
	                    </div>
	                    <div class="col-md-6"> 
	                    	 <div class="form-group">  
								<label class="col-xs-3 control-label">Empresa</label>
								<input type="hidden" id="empresaPrev" value="${bastanteo.empresa.cif}" >							            	 										            	 	
				                <select class="selectpicker col-xs-6" name="llistaEmpreses" id="llistaEmpreses" data-live-search="true" data-size="10">						                                					                                	
				               		<c:forEach items="${empresesList}" var="empresa">
				                   		<option value="${empresa.cif}">${empresa.name}</option>
				                   	</c:forEach>	
				                </select>	
							</div> 
							<div class="form-group">  
                            	<label class="col-xs-3 control-label">Persona facultada</label>
                            	<textarea class="col-xs-6" name="personaFacultada" placeholder="persona Facultada" rows="3">${bastanteo.personaFacultada}</textarea>
	                        </div> 
	                        <div class="form-group">
                                <label class="col-xs-3 control-label">Data escritura</label>
                                <div class="input-group date col-xs-6 datepicker">
								  	<input type="text" class="form-control" name="dataEscritura" value="${bastanteo.getDataEscrituraString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>   
                            <div class="form-group">
								<label class="col-xs-3 control-label">Notari</label>
								<input class="col-xs-6" name="notari" value="${bastanteo.notari}">						
							</div>	
							<div class="form-group">
								<label class="col-xs-3 control-label">Destí</label>
								<input class="" name="desti" value="${bastanteo.desti}">						
							</div>	                                                                         	
	                    </div>	
                	</div>
                	<div class="row">
                		<div class="form-group">
					        <div class="col-xs-offset-9 col-xs-9">
					            <input type="submit" class="btn btn-primary" value="Crear bastanteo">							            
					        </div>
					    </div> 
                	</div>
                </form>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
	<script src="js/bastanteos/modificar.js?<%=application.getInitParameter("datakey")%>"></script>
    <jsp:include page="../_footer.jsp"></jsp:include>
</body>
</html>