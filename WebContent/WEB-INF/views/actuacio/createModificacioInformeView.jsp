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
                            Informe <small>Modificació informe</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Informe
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Modificació
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
		    			<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoModificacioInforme">
		    				<input type="hidden"  id="valorPBase" value="${informePrevi.ofertaSeleccionada.pbase}">
		    				<input type="hidden" id="tipusObra" value="${informePrevi.propostaInformeSeleccionada.tipusObra}">
		    				<div class="form-group">                                
                                <div class="col-md-3">
                                	<label>Informe</label>
                                	<input class="form-control" value="${informePrevi.idInf}" disabled>
                                	<input type="hidden"  class="form-control" name="idInforme" value="${informePrevi.idInf}">
                                </div>
                            </div>
                            <div class="form-group">					                    			
					        	<div class="col-md-12">							                    			
					     			<div class="row">	 
					     				<div class="col-md-12">						                    						
					     					<textarea class="form-control" name="objecteModificacio" placeholder="Objecte modificació" rows="3" required></textarea> 
					       				</div>
					       			</div>
					       		</div>						                       		
					       	</div>	
                            <c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == 'obr'}">
	                            <div class="form-group">
						     		<div class="visibleObres visibleObres">					                             	
							        	<div class="col-md-3">
								      	 	<label>Llicència modificació</label>
								      	 	<input type="hidden" id="reqLlicenciaPrev" value="${informePrevi.propostaInformeSeleccionada.llicencia ? 'si' : 'no'}" >
								            <select class="form-control selectpicker" name="reqLlicencia" id="reqLlicencia">
								            	<option value="si">Si</option>
								            	<option value="no">No</option>
								            </select>
							            </div>	
							            <div class="col-md-3 visibleTipusLlicencia visibleTipusLlicencia">
								      	 	<label>Tipus de llicència</label>
								      	 	<input type="hidden" id="tipusLlicenciaPrev" value="${informePrevi.propostaInformeSeleccionada.tipusLlicencia}" >
							                <select class="form-control selectpicker" name="tipusLlicencia" id="tipusLlicencia">
							                	<option value="major">Major</option>
							                	<option value="menor">menor</option>
							                	<option value="comun">Comunicació prèvia</option>
							                </select>
							           	</div>
									</div>				                       																
								</div>
							</c:if>	
							<div class="form-group">
								<div class="col-md-12">
									<label>Pressupost modificació</label> (Valor afegit al preu original)
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
								<div class="col-md-12">
									<label>Total informe: </label>
									<input id="totalInforme" data-total="${informePrevi.ofertaSeleccionada.plic}" value="${informePrevi.ofertaSeleccionada.getPlicFormat()}" disabled>
								</div>
							</div>		
							<div class="form-group">
								<div class="col-md-6">
									<label>Nou termini d'execució</label>
									<input name="termini" placeholder="" value="${informePrevi.propostaInformeSeleccionada.termini}" required>
								</div>
							</div>							
					       	<div class="form-group">
					        	<div class="col-md-6">
					        		<input type="hidden" id="empresaPrev" value="${informePrevi.ofertaSeleccionada.cifEmpresa}" >	
					         		<label>Empresa modificació</label>									            	 										            	 	
					            	<select class="selectpicker" name="llistaEmpreses" id="llistaEmpreses" data-live-search="true" data-size="10">						                                					                                	
					               		<c:forEach items="${empresesList}" var="empresa">
					                   		<option value="${empresa.cif}">${empresa.name}</option>
					                   	</c:forEach>	
					                </select>	
					        	</div>					         			                       		
					   		</div>				                    	
					       	<div class="form-group">					                    			
					        	<div class="col-md-12">							                    			
					     			<div class="row">	 
					     				<div class="col-md-12">						                    						
					     					<textarea class="form-control" name="propostaTecnica" placeholder="Proposta tècnica" rows="3" required></textarea> 
					       				</div>
					       			</div>
					       		</div>						                       		
					       	</div>	
					       	<div class="form-group">	
						       	<div class="col-md-8">
									<div class="row margin_top10">
						    			<div class="col-md-12">
						           			Informe justificatiu: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
						    			</div>
						    		</div>																													        			
					      		</div>
				      		</div>	
                            <br>
                            <div class="form-group">
                            	<div class="col-md-8">
                            		<p id="errorModificacio" style="color: red;"></p>
                          		</div>
                            </div>	
						    <div class="form-group potModificar">
						        <div class="col-md-offset-9 col-md-3">
						            <input type="submit" class="btn btn-primary" value="Afegir modificació">
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
    <script src="js/actuacio/crearModificacioInforme.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>