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
                            Informe <small>Nou informe</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Informe
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
                
    			<div class="row">
                    <div class="col-md-12">                    	                    	
		    			<form class="form-horizontal" method="POST" action="DoCreateInformeManual">
		    				<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
		    				<div class="form-group">
		    					<div class="col-md-3">
	                                <label class="control-label">Tècnic</label>
	                                <div class="">
		                                <select class="form-control selectpicker" name="idTecnic" data-live-search="true" data-size="5" id="usuarisList">
		                                </select>
		                             </div>
	                             </div>
                            </div>         
		    				<div class="form-group">   
                                <div class="col-md-12">
                                	 <label>Descripció</label>
                                	<textarea class="form-control" name="descripcio" placeholder="descripció" rows="3"></textarea>
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
						          	<input name="pbase" class="pbase" id="pbase" placeholder="0000,00" value="" required>
						          	<label class="">€</label>
						        </div>
						        <div class="col-md-4">
							     	<label>IVA</label>
							       	<input disabled id="iva" class="iva" placeholder="0000,00" value="">
							     	<input type="hidden" name="iva" class="inputIVA" id="inputIVA" value="">
							       	<label class="">€</label>
								</div>
								<div class="col-md-4">
									<label>PLic</label>
									<input name="plic" id="plic" class="plic" placeholder="0000,00" value="">						
									<label class="">€</label>
								</div>					                                
							</div>
		    				<div class="form-group">
					     		<div class="col-md-3">
					     	 		<label>Tipus de Contracte</label>
					     	 		<input type="hidden" id="tipusContractePrev" value="" >									            	 	
						            <select class="form-control selectpicker" name="tipusContracte" id="tipusContracte">
							           	<option value="obr">Obra</option>
							           	<option value="srv">Servei</option>
							           	<option value="submi">subministrament</option>
						            </select>
					        	</div>	
					       		<div class="visibleObres visibleObres">					                             	
						        	<div class="col-md-3">
							      	 	<label>Autorització urbanística</label>
							      	 	<input type="hidden" id="reqLlicenciaPrev" value="" >
							            <select class="form-control selectpicker" name="reqLlicencia" id="reqLlicencia">
							            	<option value="si">Si</option>
							            	<option value="no">No</option>
							            </select>
						            </div>	
						            <div class="col-md-3 visibleTipusLlicencia visibleTipusLlicencia">
							      	 	<label>Tipus</label>
							      	 	<input type="hidden" id="tipusLlicenciaPrev" value="" >
						                <select class="form-control selectpicker" name="tipusLlicencia" id="tipusLlicencia">
						                	<option value="major">Llicència</option>
						                	<option value="comun">Comunicació prèvia</option>
						                </select>
						           	</div>
						        </div>
						        <div class="visibleContracte">			
						           	<div class="col-md-3">
						      	 		<label>Formalització contracte</label>
						      	 		<input type="hidden" id="formContractePrev" value="" >
						                <select class="form-control selectpicker" name="formContracte" id="formContracte">
						                	<option value="si">Si</option>
						                	<option value="no">No</option>
						                </select>
						      		</div>	
						      	</div>										                       																
							</div>								
							<div class="form-group">
								<div class="col-md-6">
									<label>Termini d'execució</label>
									<input name="termini" placeholder="" value="" required>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">		
									<div class="row">	 
										<div class="col-md-12">						                    						
						          			<textarea class="form-control" name="comentariTecnic" placeholder="comentari tècnic" rows="3"></textarea> 
						            	</div>
						        	</div>	        	
								</div>						                       		
							</div>		
							<div class="form-group">
								<div class="col-md-12">		
									<div class="row">	 
										<div class="col-md-12">						                    						
						          			<textarea class="form-control" name="comentariCap" placeholder="comentari cap" rows="3"></textarea> 
						            	</div>
						        	</div>	        	
								</div>						                       		
							</div>								
                            <br>
						    <div class="form-group">
						        <div class="col-md-offset-9 col-md-3">
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
    <script src="js/usuari/usuari.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/actuacio/crearComplet.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>