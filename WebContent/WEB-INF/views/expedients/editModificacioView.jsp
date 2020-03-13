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
                            Modificar Informe <small>Modificar informe</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Modificar Informe
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
		    			<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoEditModificacioInforme">
		    				<input type="hidden"  id="valorPBase" value="${informePrevi.ofertaSeleccionada.pbase}">
		    				<input type="hidden" id="tipusObra" value="${informePrevi.propostaInformeSeleccionada.tipusObra}">
		    				<input type="hidden" id="tipusIncidenciaPrev" value="${informeModificacio.tipusModificacio}">
		    				<input type="hidden"  name="idInforme" value="${informePrevi.idInf}">
		    				<input type="hidden"  name="idModificacio" value="${informeModificacio.idInf}">
                            <div class="form-group">
                            	<div class="col-md-6">					        			
					         		<label>Tipus incidència</label>									            	 										            	 	
					            	<select class="selectpicker" name="tipusIncidencia" id="tipusIncidencia">						                                					                                	
							        	<option value="modificacio">Modificació</option>
							        	<option value="certfinal">Certificació final</option>			
							        	<option value="preusContradictoris">Preus contradictoris</option>							        	
							        	<option value="penalitzacio">Penalització</option>								        	
							        	<option value="termini">Ampliació termini</option>							        								        	
							        	<option value="resolucioContracte">Resolució contracte</option>	
							        	<option value="enriquimentInjust">Enriquiment injust</option>							        		
							        	<option value="informeExecucio">Incidència genèrica</option>				                   	
					                </select>	
					        	</div>	
                            </div>
                            <div class="form-group">					                    			
					        	<div class="col-md-12">							                    			
					     			<div class="row">	 
					     				<div class="col-md-12">						                    						
					     					<textarea class="form-control" name="objecteModificacio" placeholder="Objecte incidència" rows="3" required>${informeModificacio.propostaInformeSeleccionada.objecte}</textarea> 
					       				</div>
					       			</div>
					       		</div>						                       		
					       	</div>	
					       	<div id="seccioLlicendia">					      		
	                            <c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == 'obr'}">
		                            <div class="form-group">
							     		<div class="visibleObres visibleObres">					                             	
								        	<div class="col-md-3">
									      	 	<label>Llicència modificació</label>
									      	 	<input type="hidden" id="reqLlicenciaPrev" value="${informeModificacio.propostaInformeSeleccionada.llicencia ? 'si' : 'no'}" >
									            <select class="form-control selectpicker" name="reqLlicencia" id="reqLlicencia">
									            	<option value="si">Si</option>
									            	<option value="no">No</option>
									            </select>
								            </div>	
								            <div class="col-md-3 visibleTipusLlicencia visibleTipusLlicencia">
									      	 	<label>Tipus de llicència</label>
									      	 	<input type="hidden" id="tipusLlicenciaPrev" value="${informeModificacio.propostaInformeSeleccionada.tipusLlicencia}" >
								                <select class="form-control selectpicker" name="tipusLlicencia" id="tipusLlicencia">
								                	<option value="major">Major</option>
								                	<option value="menor">menor</option>
								                	<option value="comun">Comunicació prèvia</option>
								                </select>
								           	</div>
										</div>				                       																
									</div>
								</c:if>	
							</div>
							<div id="seccioPressupost">
								<div class="form-group">
									<div class="col-md-12">
										<label>Pressupost modificació</label> (Valor afegit al preu original)
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-4">
							          	<label>PBase</label>
							          	<input name="pbase" class="pbase" id="pbase" placeholder="0000,00" value="${informeModificacio.ofertaSeleccionada.pbase}">
							          	<label class="">€</label>
							        </div>
							        <div class="col-md-4">
								     	<label>IVA</label>
								       	<input disabled id="iva" class="iva" placeholder="0000,00" value="${informeModificacio.ofertaSeleccionada.iva}">
								     	<input type="hidden" name="iva" class="inputIVA" id="inputIVA" value="${informeModificacio.ofertaSeleccionada.iva}">
								       	<label class="">€</label>
									</div>
									<div class="col-md-4">
										<label>PLic</label>
										<input name="plic" id="plic" class="plic" placeholder="0000,00" value="${informeModificacio.ofertaSeleccionada.plic}">						
										<label class="">€</label>
									</div>					                                
								</div>
								<div class="form-group">
									<div class="col-md-12">
										<label>Total informe: </label>
										<input id="totalInforme" data-total="${informePrevi.ofertaSeleccionada.plic}" value="${informePrevi.ofertaSeleccionada.plic + informeModificacio.ofertaSeleccionada.plic}" disabled>
									</div>
								</div>
							</div>	
							<div id="seccioTermini">	
								<div class="form-group">
									<div class="col-md-6">
										<label>Nou termini d'execució</label>
										<input name="termini" placeholder="" value="${informeModificacio.propostaInformeSeleccionada.termini}">
									</div>
								</div>
							</div>		
							<div class="form-group">
								<div class="col-md-6">
									<label>Hi haurà parts ocultes</label>
									<input type="checkbox" name="partsOcultes">
								</div>
							</div>	
							<div id="seccioEmpresa">						
						       	<div class="form-group">
						        	<div class="col-md-6">
						        		<input type="hidden" id="empresaPrev" value="${informeModificacio.ofertaSeleccionada.cifEmpresa}" >	
						         		<label>Empresa modificació</label>									            	 										            	 	
						            	<select class="selectpicker" name="llistaEmpreses" id="llistaEmpreses" data-live-search="true" data-size="10">						                                					                                	
						               		<c:forEach items="${empresesList}" var="empresa">
						                   		<option value="${empresa.cif}">${empresa.name}</option>
						                   	</c:forEach>	
						                </select>	
						        	</div>					         			                       		
						   		</div>	
						   	</div>						    
						    <div id="seccioPenalitzacio">						    						      		
						    	<div class="form-group">									
									<div class="col-md-4">
										<label>Valor</label>
										<input name="plicPenalitzacio" id="plicPenalitzacio" class="plicPenalitzacio" placeholder="0000,00" value="${informeModificacio.ofertaSeleccionada.plic}">						
										<label class="">€</label>
									</div>
									<div class="col-md-4">
										<label>Retenció</label>
										<input type="checkbox" name="retencio">
									</div>	
									<div class="col-md-4">
										<label>Execució</label>
										<input type="checkbox" name="execucio" checked>
									</div>				                                
								</div>
						    </div>
					        <br>
					        <div class="document">			
				               	<label>Informe DF</label>
	                            <c:forEach items="${informeModificacio.informeDF}" var="arxiu" >	
									<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:forEach>
							</div>						
							<div class="col-md-12">
								<div class="row margin_top10">
					    			<div class="col-md-12">
					           			<input type="file" class="btn uploadImage" name="informeDF" multiple/><br/>																 		
					    			</div>
					    		</div>																													        			
				      		</div>
							<p></p>			
                            <div class="document">			
				               	<label>Informe tècnic d'aprovació</label>
	                            <c:forEach items="${informeModificacio.propostaTecnica}" var="arxiu" >	
									<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:forEach>
							</div>						
							<div class="col-md-12">
								<div class="row margin_top10">
					    			<div class="col-md-12">
					           			<input type="file" class="btn uploadImage" name="informe" multiple/><br/>																 		
					    			</div>
					    		</div>																													        			
				      		</div>
							<p></p>											                    	
					       	<div class="form-group">					                    			
					        	<div class="col-md-12">							                    			
					     			<div class="row">	 
					     				<div class="col-md-12">						                    						
					     					<textarea class="form-control" name="propostaTecnica" placeholder="Proposta tècnica" rows="3">${informeModificacio.ofertaSeleccionada.comentari}</textarea> 
					       				</div>
					       			</div>
					       		</div>						                       		
					       	</div>	
							<div class="document">			
				               	<label>Resolució inici</label>
	                            <c:forEach items="${informeModificacio.resInici}" var="arxiu" >	
									<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:forEach>
							</div>						
							<div class="col-md-12">
								<div class="row margin_top10">
					    			<div class="col-md-12">
					           			<input type="file" class="btn uploadImage" name="resinici" multiple/><br/>																 		
					    			</div>
					    		</div>																													        			
				      		</div>							
					      	<p></p>						
							<div class="document">			
				               	<label>Autorització àrea econòmica signada:</label>
				               	<c:if test="${informeModificacio.conformeAreaEconomivaPropostaActuacio.ruta != null}">
									<c:set var="arxiu" value="${informeModificacio.conformeAreaEconomivaPropostaActuacio}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:if>
							</div>
							<div class="col-md-12">
								<div class="row margin_top10">
					    			<div class="col-md-12">
					           			<input type="file" class="btn uploadImage" name="certificatEconomic" multiple/><br/>																 		
					    			</div>
					    		</div>																													        			
				      		</div>
				      		<p></p>			      		
							<div class="document">			
				               	<label>Tràmits</label>
	                            <c:forEach items="${informeModificacio.tramitsModificacio}" var="arxiu" >
									<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:forEach>
							</div>						
							<div class="col-md-12">
								<div class="row margin_top10">
					    			<div class="col-md-12">
					           			<input type="file" class="btn uploadImage" name="tramits" multiple/><br/>																 		
					    			</div>
					    		</div>																													        			
				      		</div>
							<p></p>	
				      		<div class="document">			
				               	<label>Informe Jurídic</label>
	                            <c:forEach items="${informeModificacio.informeJuridic}" var="arxiu" >	
									<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:forEach>
							</div>						
							<div class="col-md-12">
								<div class="row margin_top10">
					    			<div class="col-md-12">
					           			<input type="file" class="btn uploadImage" name="informeJuridic" multiple/><br/>																 		
					    			</div>
					    		</div>																													        			
				      		</div>
							<p></p>	
							<div class="document">			
				               	<label>Resolució aprovació:</label>	
				               	 <c:forEach items="${informeModificacio.autoritzacioPropostaDespesa}" var="arxiu" >	
									<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:forEach>				               
							</div>
							<div class="col-md-12">
								<div class="row margin_top10">
					    			<div class="col-md-12">
					           			<input type="file" class="btn uploadImage" name="autoritzacioDespesa" multiple/><br/>																 		
					    			</div>
					    		</div>																													        			
				      		</div>
				      		<p></p>		
				      		<div class="document">			
				               	<label>Resolució Final</label>
	                            <c:forEach items="${informeModificacio.resFinal}" var="arxiu" >	
									<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
								</c:forEach>
							</div>						
							<div class="col-md-12">
								<div class="row margin_top10">
					    			<div class="col-md-12">
					           			<input type="file" class="btn uploadImage" name="resfinal" multiple/><br/>																 		
					    			</div>
					    		</div>																													        			
				      		</div>							
					      	<p></p>			      		
	   					 	<div class="form-group">  
								<div class="col-md-3">
									<label>Data firma resolució</label>
	                                <div class="input-group date datepicker">
									  	<input type="text" class="form-control" name="dataContracte" value="${informeModificacio.propostaInformeSeleccionada.getDataFirmaModificacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
								</div>
							</div>
		   					<div class="form-group">				       		
					       		<div class="col-md-6">
					       			<div class="document">
				               			<label>Formalització:</label>											                  	
					           			<a target="_blanck" href="downloadFichero?ruta=${informeModificacio.formalitzacioSignat.getEncodedRuta()}">
											${informeModificacio.formalitzacioSignat.nom}
										</a>	
										<c:if test="${informeModificacio.formalitzacioSignat.signat}">
											<span data-ruta="${informeModificacio.formalitzacioSignat.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informeModificacio.formalitzacioSignat.ruta != null}">
											<span data-ruta="${informeModificacio.formalitzacioSignat.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">										
										</div>
									</div>	
									<input type="file" class="btn uploadImage" name="contracte" /><br/>	
					       		</div>				       		
					       	</div>
                            <div class="form-group">
                            	<div class="col-md-8">
                            		<p id="errorModificacio" style="color: red;"></p>
                          		</div>
                            </div>	
						    <div class="form-group potModificar">
						        <div class="col-md-offset-9 col-md-3">
						            <input type="submit" class="btn btn-primary" value="Modificar">
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
    <script src="js/expedient/editModificacioInforme.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>