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
	                        Tasca<small> Sol·licitud actuació derivada</small>
                        </h1>
                        <ol class="breadcrumb">                        	
					       	<li class="active">
                                <i class="fa fa-dashboard"></i> Tasca
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Nova tasca
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
		    			<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoCreateTascaDerivada">
		    				<div class="form-group">
		    					<input type="hidden" name="idInforme" value="${idInforme}">               
                            </div>
					       	<div class="form-group">
                                <label>Objecte</label>
                                <div class="col-xs-12">
	                                <input class="form-control" name="assumpte" placeholder="objecte">
	                             </div>
                            </div>
						    <div class="form-group">
                                <label>Comentari Tècnic</label>
                                <div class="col-xs-12">                                	
                               		<textarea class="form-control" name="comentari" placeholder="comentari tècnic" rows="3"></textarea>	                            
								</div>
                            </div>
                            <div class="form-group">
                            	<label>Adjuntar arxius:</label>
	                            <div class="col-xs-12">   
	                                <input type="file" class="btn" name="file" multiple/><br/>
								</div> 	
							</div>
							<div class="form-group">
								<div class="col-md-4">
									<label>Tècnic</label>	
									<input type="hidden" id="tecnicPrevi" value="${informePrevi.usuari.idUsuari}" >														            	 										            	 	
					                <select class="form-control selectpicker" data-live-search="true" data-size="5" name="llistaUsuaris" id="llistaUsuaris">
					                	<option value='-1'>Seleccionar usuari</option>
					                	<c:forEach items="${llistaUsuaris}" var="usuari">
					                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
					                	</c:forEach>					                                	
					                </select>	
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">
									<label>Pressupost</label>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-4">
						          	<label>PBase</label>
						          	<input name="pbase" class="pbase" id="pbase" placeholder="0000,00" value="${propostaActuacio.pbase}" required>
						          	<label class="">€</label>
						        </div>
						        <div class="col-md-4">
							     	<label>IVA</label>
							       	<input disabled id="iva" class="iva" placeholder="0000,00" value="${propostaActuacio.iva}">
							     	<input type="hidden" name="iva" class="inputIVA" id="inputIVA" value="${propostaActuacio.iva}">
							       	<label class="">€</label>
								</div>
								<div class="col-md-4">
									<label>PLic</label>
									<input name="plic" id="plic" class="plic" placeholder="0000,00" value="${propostaActuacio.plic}">						
									<label class="">€</label>
								</div>					                                
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label>Termini d'execució</label>
									<input name="termini" placeholder="" value="${propostaActuacio.termini}" required>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">											
									<div class="row">	
										<label>Comentari Cap</label> 
										<div class="col-md-12">						                    						
						          			<textarea class="form-control" name="comentariCap" placeholder="comentari cap" rows="3"></textarea> 
						            	</div>
						        	</div>	        	
								</div>						                       		
							</div>
						    <div class="form-group">
						        <div class="col-xs-offset-10 col-xs-2">
						            <input type="submit" class="btn btn-success" value="Enviar">
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
    <script src="js/tasca/createTasca.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>