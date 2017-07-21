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
                            Expedient <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Expedient
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
                <c:if test="${not empty expedient}">
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditExpedient">                		
                		<h2 class="margin_bottom30">Informació bàsica</h2>
                		<div class="row">
                			<div class="col-md-6">		                    	
			    				<div class="form-group">
	                                <label class="col-xs-3 control-label">Expedient</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="newExpedient" value="${expedient.expContratacio}" disabled>
	                                	<input type="hidden" name="expedient" value="${expedient.expContratacio}">                            	
	                                </div>
	                            </div>   
	                    	</div>
                		</div>                		
			    		<div class="row">			    				    				    		
		                    <div class="col-md-6">	  
		                    	<div class="form-group">
	                                <label class="col-xs-3 control-label">Data publicació BOIB</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataBOIB" value="${expedient.getDataPublicacioBOIBString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data adjudicació</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataAdjudicacio" value="${expedient.getDataAdjudicacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	 
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data inici obra</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataIniciObra" value="${expedient.getDataIniciObratring()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	  
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data retorn garantia</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataRetornGarantia" value="${expedient.getDataRetornGarantiaString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	          
		                    </div>
		                    <div class="col-md-6">  
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data límit ofertes</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataLimitOfertes" value="${expedient.getDataLimitPresentacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data firma contracte</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataFirma" value="${expedient.getDataFirmaString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	   
	                             <div class="form-group">
	                                <label class="col-xs-3 control-label">Data recepció</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataRecepcio" value="${expedient.getDataRecepcioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data liquidació obra</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataLiquidacioObra" value="${expedient.getDataLiquidacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	   		                            	                                                                        	
		                    </div>	
	                	</div>
	                	<div class="row">
	                		<div class="col-md-12">
		                		<div class="form-group">
	                                <label class="col-xs-2  control-label">Descripció</label>
	                                <div class="col-xs-8">
	                                	<textarea class="form-control" name="descripcio" placeholder="descripcio" rows="3">${expedient.descripcio}</textarea>
	                                </div>
	                            </div>
                            </div>
	                	</div>
	                	<div class="row">
	                		<div class="form-group">
                                <label class="col-xs-2 control-label">Garantia</label>
                                <div class="col-xs-8">
                                	<input class="form-control" name="garantia" placeholder="garantia" value="${expedient.garantia}">
                                </div>
                            </div>
	                	</div>	                	
	                	<div class="row">
	                		<div class="form-group">
						        <div class="col-xs-offset-9 col-xs-9">
						            <input type="submit" class="btn btn-primary" value="Actualitzar expedient">							            
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
    <script src="js/expedient/modificar.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>