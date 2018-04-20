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
                            Llicència <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Llicència
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
                <c:if test="${not empty llicencia}">
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditLlicencia">                		
                		<h2 class="margin_bottom30">Informació bàsica</h2>
                		<div class="row">
                			<div class="col-md-6">		                    	
			    				<div class="form-group">
	                                <label class="col-xs-3 control-label">Llicencia</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="newLlicencia" value="${llicencia.codi}" disabled>
	                                	<input type="hidden" name="llicencia" value="${llicencia.codi}">  
	                                	<input type="hidden" name="idActuacio" value="${actuacio.referencia}"> 
	                                	<input type="hidden" name="from" value="${from}">                         	
	                                </div>
	                            </div>   
	                    	</div>
                		</div>                		
			    		<div class="row">			    				    				    		
		                    <div class="col-md-6">	  
		                    	<div class="form-group">
	                                <label class="col-xs-3 control-label">Taxa</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="taxa" value="${llicencia.taxa}">                    	
	                                </div>
	                            </div>
		                    	<div class="form-group">
	                                <label class="col-xs-3 control-label">Data sol·licitud</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataSolicitud" value="${llicencia.getDataPeticioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>	
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data pagament Taxa</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataPagamentTaxa" value="${llicencia.getDataPagamentTaxaString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data pagament ICO</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataPagamentICO" value="${llicencia.getDataPagamentICOString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>
		                    </div>
		                    <div class="col-md-6"> 
		                    	<div class="form-group">
	                                <label class="col-xs-3 control-label">ICO</label>
	                                <div class="col-xs-6">
	                                	<input class="form-control" name="ico" value="${llicencia.ico}">                    	
	                                </div>
	                            </div> 
	                            <div class="form-group">
	                                <label class="col-xs-3 control-label">Data concesió</label>
	                                <div class="input-group date col-xs-6 datepicker">
									  	<input type="text" class="form-control" name="dataConcesio" value="${llicencia.getDataConcesioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
	                            </div>		                                                 	                                                                        	
		                    </div>	
	                	</div>
	                	<div class="row">
	                		<div class="col-md-12">
		                		<div class="form-group">
	                                <label class="col-xs-2  control-label">Observacions</label>
	                                <div class="col-xs-8">
	                                	<textarea class="form-control" name="observacions" placeholder="observacions" rows="3">${llicencia.observacio}</textarea>
	                                </div>
	                            </div>
                            </div>
	                	</div>	
	                	<div class="row">
		                    <div class="col-md-12">
		                        <h2 class="margin_bottom30">Arxius</h2>
		                        <div class="row">  
			                        <c:forEach items="${llicencia.arxius}" var="arxiu" >
										<div class="document">
											<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
												${arxiu.getDataString()} - ${arxiu.nom}
											</a>
											<c:if test="${arxiu.signat}">
												<span class="glyphicon glyphicon-pencil signedFile"></span>
											</c:if>
											<a href="#"><span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
											<br>
											<div class="infoSign hidden">
												<c:forEach items="${arxiu.firmesList}" var="firma" >
													<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
													<br>
												</c:forEach>
											</div>
										</div>					            		
									</c:forEach>	
								</div>
		                		<div class="row">            			
									<div class="form-group">
										<label class="col-xs-2 control-label">Adjuntar arxius:</label>
			                            <div class="col-xs-5">   
			                                <input type="file" class="btn" name="file" multiple/><br/>
										</div>					
			         				</div>						
				            	</div>              		
		                    </div>
		                </div>                	           	
	                	<div class="row">
	                		<div class="form-group">
						        <div class="col-xs-offset-9 col-xs-9">
						            <input type="submit" class="btn btn-primary" value="Actualitzar llicència">							            
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
    <script src="js/llicencia/modificar.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>