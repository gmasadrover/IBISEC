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
                            Empresa <small>Afegir UTE</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Empresa
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Registre UTE
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
		    			<form class="form-horizontal" method="POST" action="doCreateUTE">
		    				<h2 class="margin_bottom30">Informació UTE</h2>
		    				<div class="row">
			    				<div class="col-md-6">	
			    				 	<div class="form-group">
		                                <label class="col-xs-3 control-label">CIF</label>
		                                <div class="col-xs-6">
		                                	<input class="form-control" name="cif" placeholder="cif">
		                                </div>
		                            </div>			                            
			    				</div>
			    				<div class="col-md-6">	
			    					<div class="form-group">
		                                <label class="col-xs-3 control-label">Nom</label>
		                                <div class="col-xs-6">
		                                	<input class="form-control" name="name" placeholder="nom">
		                                </div>
		                            </div>
			    				</div> 
			    			</div>  
			    			<h2 class="margin_bottom30">Empreses UTE</h2>
			    			<div class="row">
			    				<div class="col-md-12">	
		                            <div class="form-group">
		                            	<div class="hidden" id="llistatEmpreses"></div>
							        	<div class="col-md-offset-1 col-md-4">	
							         		<label>Empresa</label>									            	 										            	 	
							            	<select class="selectpicker" name="llistaEmpreses" id="llistaEmpreses" data-live-search="true" data-size="10">						                                					                                	
							               		<c:forEach items="${empresesList}" var="empresa">
							                   		<option value="${empresa.cif}">${empresa.name}</option>
							                   	</c:forEach>	
							                </select>	
							        	</div>					         	
							           	<div class="col-md-4">												        
							            	<input class="btn btn-primary" type="button" name="afegir" id="afegir" value="Afegir">
										</div>				                       		
							   		</div>	
							   	</div>
						   	</div>
						   	<div class="row">
			    				<div class="form-group">
				   					<div class="col-md-offset-1 col-md-10">						     						                        
						                <div class="table-responsive">							                        
						                    <table class="table table-striped table-bordered filerTable" id="empresesTable">
						                        <thead>
						                            <tr>
						                                <th>Empresa</th>
						                                <th>Empresa</th>
						                                <th>Empresa</th>
						                                <th>Control</th>							                                        							                                       
						                            </tr>
						                        </thead>
						                        <tbody>							                                	                              	
						                        </tbody>
						                    </table>
						                </div>
						           	</div>
								</div>	
							</div>
		    				<br>
						    <div class="form-group">
						        <div class="col-xs-offset-9 col-xs-3">
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
    <script src="js/empresa/crearUTE.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>