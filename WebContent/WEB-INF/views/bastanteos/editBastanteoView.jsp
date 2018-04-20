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
                            Validacions <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Validacions
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
                <c:if test="${not empty bastanteo}">
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditBastanteo">                		
                		<h2 class="margin_bottom30">Validació</h2>
                		<div class="row">
                			<div class="col-md-6">		                    	
			    				<div class="form-group">
	                                <label class="control-label">Referència ${bastanteo.ref}</label>
	                                <input type="hidden" name="referencia" value="${bastanteo.ref}">
	                            </div>   
	                    	</div>
                		</div>                		
			    		<div class="row">			    				    				    		
		                    <div class="col-md-6">	  
		                    	<div class="form-group">
	                                <label class="col-xs-3 control-label">Data validació</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataBastanteo" value="${bastanteo.getDatabastanteoString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
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
		                    </div>
		               	</div>
		               	<div class="row">
		               		<div class="col-md-12">
		                        <h4>Escriptures</h4>
		                        <div class="table-responsive">                        
		                            <table class="table table-striped table-bordered filerTable">
		                                <thead>
		                                    <tr>
		                                        <th>Escriptura</th>
		                                        <th>Data</th>                                       
		                                        <th>Data</th>
		                                        <th>Notari</th>                                       
		                                        <th>Protocol</th>
		                                        <th>Control</th>
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${bastanteo.escrituresList}" var="escritura" >
									          	<tr>
									          		<td>${escritura.escritura}</td>
									          		<td>${escritura.getDataEscrituraString()}</td>
									            	<td>${escritura.dataEscritura}</td>									            	
									            	<td>${escritura.notari}</td>
									            	<td>${escritura.numProtocol}</td>
									            	<td>
									            		<a href="doDeleteEscritura?ref=${escritura.codi}&refBastanteo=${bastanteo.ref}" class="btn btn-danger btn-sm deleteEscritura">Eliminar</a>
                        								<a href="escritura?ref=${escritura.codi}&refBastanteo=${bastanteo.ref}" class="btn btn-primary btn-sm">Modificar</a>
                        			    			</td>
									          	</tr>
									       	</c:forEach>                                	
		                                </tbody>
		                            </table>
		                        </div>
		                    </div>
		               	</div>	
		               	<div class="row">					
							<div class="form-group">	
							  	<div class="col-md-offset-10 col-md-2">
							    	<a href="afegirEscritura?ref=${bastanteo.ref}" class="loadingButton btn btn-primary"  data-msg="afegir escritura...">Afegir escriptura</a>
								</div>
							</div>	
						</div>	               
		                <div class="row">			    				    				    		
		                    <div class="col-md-6">       
		                    	<div class="form-group">  
	                            	<label class="col-xs-3 control-label">Persona facultada</label>
	                            	<textarea class="col-xs-6" name="personaFacultada" placeholder="persona Facultada" rows="3">${bastanteo.personaFacultada}</textarea>
		                        </div>
		                    </div>
		                    <div class="col-md-6"> 		                    	 
								<div class="form-group">  
	                            	<label class="col-xs-3 control-label">Càrrec</label>
	                            	<textarea class="col-xs-6" name="carrec" placeholder="carrec" rows="3">${bastanteo.carrec}</textarea>
		                        </div>	                                                                         	
		                    </div>	
	                	</div>
	                	<div class="row">
	                		<div class="form-group">
						        <div class="col-xs-offset-9 col-xs-9">
						            <input type="submit" class="btn btn-primary" value="Actualitzar validació">							            
						        </div>
						    </div> 
	                	</div>
	                </form>
                </c:if>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/bastanteos/modificar.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>