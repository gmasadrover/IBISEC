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
                            Procediment Judicial <small>Nou</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Procediment Judicial
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Nou
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
                <form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doCreateProcediment">                		
               		<h2 class="margin_bottom30">Nou Procediment</h2>                		             		
		    		<div class="row">			    				    				    		
	                    <div class="col-md-6">	 
	                    	<div class="form-group">    
                            	<label class="col-xs-3 control-label">Jutjat / Tribunal</label>
                            	<textarea class="col-xs-6" name="jutjat" placeholder="jutjat" rows="3"></textarea>
	                        </div> 
	                        <div class="form-group">
								<label class="col-xs-3 control-label">Demandant</label>
								<input class="col-xs-6" name="demandant" value="">						
							</div>		                        		                    
						</div>
	                    <div class="col-md-6"> 		                    	 
                            <div class="form-group">
								<label class="col-xs-3 control-label">Num Autos</label>
								<input class="col-xs-6" name="numautos" value="">						
							</div>	
							<div class="form-group">
								<label class="col-xs-3 control-label">Demandat</label>
								<input class="col-xs-6" name="demandat" value="">						
							</div>	  
							<div class="form-group">
								<label class="col-xs-3 control-label">Quantia</label>
								<input class="col-xs-6" name="quantia" value="">						
							</div>                                                 	
	                    </div>	
                	</div>
                	<div class="row">
                		<div class="form-group">  			
                           	<label class="col-xs-2 control-label">Objecte demanda</label>
                           	<textarea class="col-xs-8" name="objecte" placeholder="objecte" rows="3"></textarea>
                        </div>
                	</div>	
                	<div class="row">			    				    				    		
	                    
                	</div>  
                	<div class="row">
                		<div class="form-group">  			
                           	<label class="col-xs-2 control-label">Notes</label>
                           	<textarea class="col-xs-8" name="notes" placeholder="notes" rows="3"></textarea>
                        </div>
                	</div>	
                	<div class="row">
                		<div class="form-group">
					        <div class="col-xs-offset-9 col-xs-9">
					            <input type="submit" class="btn btn-primary" value="Crear procediment">							            
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
    <jsp:include page="../_footer.jsp"></jsp:include>
</body>
</html>