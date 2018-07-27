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
                                <i class="fa fa-dashboard"></i> Garantia
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
                <c:if test="${not empty informePrevi}">
                	<div class="col-md-12">
	                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditGarantia"> 
				   			<input type="hidden" name="redireccio" value="${redireccio}">  
				   			<input type="hidden" name="expedient" value="${informePrevi.expcontratacio.expContratacio}">
	                	 	<input type="hidden" name="idInforme" value="${informePrevi.idInf}">           
				   			<div class="form-group">	
								<div class="form-group">
									<label>Termini garantia:</label>
									<input name="terminiGarantia" placeholder="" value="${informePrevi.expcontratacio.garantia}"> 
								</div>
								<div class="form-group">
									<div class="col-md-3">
										<label>Data inici garantia:</label>
										<div class="input-group date datepicker">
										  	<input type="text" class="form-control" name="dataIniciGarantia" value="${informePrevi.expcontratacio.getDataRecepcioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
										</div>	
									</div>								
								</div>
								<div class="form-group">
									<label>Sol·licitud devolució aval:</label>
								</div>	
								<div class="form-group">
									<c:forEach items="${informePrevi.documentSolDevolucio}" var="arxiu" >
										<c:set var="arxiu" value="${arxiu}" scope="request"/>
										<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
									</c:forEach>
									<br>					            		
								</div>
								<div class="form-group">
									<label class="col-xs-2 control-label">Adjuntar arxius:</label>
							           <div class="col-xs-5">   
							           	<input type="file" class="btn" name="documentSolDevolucio" multiple/><br/>
									</div> 
								</div>
								<div class="form-group">
									<label>Informe devolució aval:</label>								
								</div>
								<div class="form-group">
									<c:forEach items="${informePrevi.documentInformeDevolucio}" var="arxiu" >
										<c:set var="arxiu" value="${arxiu}" scope="request"/>
										<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
									</c:forEach>
									<br>					            		
								</div>
								<div class="form-group">
									<label class="col-xs-2 control-label">Adjuntar arxius:</label>
							           <div class="col-xs-5">   
							           	<input type="file" class="btn" name="documentInformeDevolucio" multiple/><br/>
									</div> 
								</div>
								<div class="form-group">
									<label>Liquidació aval:</label>
								</div>	
								<div class="form-group">
									<c:forEach items="${informePrevi.documentLiquidacioAval}" var="arxiu" >
										<c:set var="arxiu" value="${arxiu}" scope="request"/>
										<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
									</c:forEach>
									<br>					            		
								</div>	
								<div class="form-group">
									<label class="col-xs-2 control-label">Adjuntar arxius:</label>
							           <div class="col-xs-5">   
							           	<input type="file" class="btn" name="documentLiquidacioAval" multiple/><br/>
									</div> 
								</div>
								<div class="form-group">
									<p>
										<div class="col-md-3">
											<label>Data retorn aval:</label>
											<div class="input-group date datepicker">
											  	<input type="text" class="form-control" name="dataRetornGarantia" value="${informePrevi.expcontratacio.getDataRetornGarantiaString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
											</div>	
										</div>								
									</p>
								</div>
			   				</div>	
						    <div class="form-group">
					    		<div class="col-md-12">
							       	<div class="row">
				                		<div class="form-group">
									        <div class="col-xs-offset-9 col-xs-9">
									            <input type="submit" class="btn btn-primary" value="Actualitzar expedient">							            
									        </div>
									    </div> 
				                	</div>
				                </div>
				           	</div>
		                </form>
		        	</div>
                </c:if>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>