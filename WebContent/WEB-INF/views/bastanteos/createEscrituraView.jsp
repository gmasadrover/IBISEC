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
                            Escritura <small>Afegir</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Escritura
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Afegir
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
               	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doCreateEscritura">   
               		<input type="hidden" name="refBastanteo" value="${refBastanteo}"/>             		
               		<h2 class="margin_bottom30">Afegir escritura</h2>                		              		
		    		<div class="row">			    				    				    		
	                    <div class="col-md-6">	
                            <div class="form-group">    
                            	<label class="col-xs-3 control-label">Escritura</label>
                            	<textarea class="col-xs-6" name="escritura" placeholder="escritura" rows="3">${escritura.escritura}</textarea>
	                        </div>   
	                        <div class="form-group">
								<label class="col-xs-3 control-label">Num Protocol</label>
								<input class="col-xs-6" name="nProtocol" value="${escritura.numProtocol}">						
							</div>
	                    </div>
	                    <div class="col-md-6"> 	                    	
							<div class="form-group">
                                <label class="col-xs-3 control-label">Data escritura</label>
                                <div class="input-group date col-xs-6 datepicker">
								  	<input type="text" class="form-control" name="dataEscritura" value="${escritura.getDataEscrituraString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>            
                            <div class="form-group">
								<label class="col-xs-3 control-label">Notari</label>
								<input class="col-xs-6" name="notari" value="${escritura.notari}">						
							</div>                                                                         	
	                    </div>	
                	</div>
                	<div class="row">
                		<div class="form-group">
					        <div class="col-xs-offset-9 col-xs-9">
					            <input type="submit" class="btn btn-primary" value="Afegir escritura">							            
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