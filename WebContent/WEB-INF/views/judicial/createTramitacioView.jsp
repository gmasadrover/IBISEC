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
                            Tramitació <small>Nova</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Tramitació
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
                
               	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doCreateTramitacio">                		
               		<h2 class="margin_bottom30">Tramitació</h2>
               		<div class="row">
                		<div class="col-md-6">		                    	
		    				<div class="form-group">	
								<label class="col-xs-3 control-label">Tipus</label>
								<input type="hidden" id="tipusPrev" value="${tramitacio.tipus}" >							            	 										            	 	
				                <select class="col-xs-6 selectpicker" name="llistatTipus" id="llistatTipus">
				                	<option value="dipso">Disposició Judicial</option>
				                	<option value="propis">Propis IBISEC</option>
				                	<option value="advoc">Advocacia</option>
				                	<option value="altres">Altres</option>									                						                                	
				                </select>
                                <input type="hidden" name="referencia" value="${tramitacio.idTramitacio}">
                                <input type="hidden" name="procediment" value="${procediment}">
                            </div>   
                    	</div>
               		</div>                		
			    	<div class="row">			    				    				    		
		            	<div class="col-md-6">	 
		                	<div class="form-group">    
	                        	<label class="col-xs-3 control-label">Termini</label>
	                       		<input class="col-xs-6" name="termini" value="${tramitacio.termini}">
		                    </div> 
		                  	<div class="form-group">
								<label class="col-xs-3 control-label">Quantia</label>
								<input class="col-xs-6" name="quantia" value="${tramitacio.quantia}">						
							</div>
							<div class="form-group">
                	 			<label class="col-xs-3 control-label">Data pagament</label>
                                <div class="input-group col-xs-6 date datepicker">
								  	<input type="text" class="form-control" name="dataPagament" value="${tramitacio.getDataPagamentString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                	 		</div>  		                        		                    
						</div>
	                    <div class="col-md-6"> 		 
	                    	<div class="form-group">
                	 			<label class="col-xs-3 control-label">Data</label>
                                <div class="input-group col-xs-6 date datepicker">
								  	<input type="text" class="form-control" name="data" value="${tramitacio.getDataString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                	 		</div>                   	 
                            <div class="form-group">
								<label class="col-xs-3 control-label">Recurs</label>
								<input class="col-xs-6" name="recurs" value="${tramitacio.recurs}">						
							</div>	
							<div class="form-group">
								<label class="col-xs-3 control-label">Sentència</label>
								<input class="col-xs-6" name="sentencia" value="${tramitacio.sentencia}">						
							</div>	                                                                         	
	                    </div>	
                	</div>
                	<div class="row">
                		<div class="form-group">  			
                           	<label class="col-xs-2 control-label">Notes</label>
                           	<textarea class="col-xs-8" name="notes" rows="3">${tramitacio.notes}</textarea>
                        </div>
                	</div>					                	
                	<div class="row">
                		<div class="form-group">
					        <div class="col-xs-offset-9 col-xs-9">
					            <input type="submit" class="btn btn-primary" value="Afegir tramitació">							            
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