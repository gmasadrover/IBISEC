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
                            Procediment Judicial <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Procediement Judicial
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
                <c:if test="${not empty procediment}">
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditProcediment">                		
                		<h2 class="margin_bottom30">Procediment</h2>
                		<div class="row">
                			<div class="col-md-12">		                    	
			    				<div class="form-group">	                               
	                                <input type="hidden" name="referencia" value="${procediment.referencia}">
	                            </div>   
	                    	</div>
                		</div>             
                		<div class="row">
                			<div class="col-md-6"> 		                    	 
	                            <div class="form-group">
									<label class="col-xs-3 control-label">Estat</label>
									<input type="hidden" id="estatSelected" value="${procediment.estat}" />
									<div>
								    	<select class="selectpicker" id="estatList" name="estat">
										  	<option value="obert">Obert</option>
										  	<option value="execucio">En execució</option>
										  	<option value="arxiu">Arxivat</option>
										  	<option value="altres">Altes</option>
										</select>							      	
								 	</div>	
								</div>                                                             	
		                    </div>	
                		</div>   		
			    		<div class="row">			    				    				    		
		                    <div class="col-md-6">	 
		                    	<div class="form-group">    
	                            	<label class="col-xs-3 control-label">Jutjat / Tribunal</label>
	                            	<textarea class="col-xs-6" name="jutjat" placeholder="jutjat" rows="3">${procediment.jutjat}</textarea>
		                        </div> 
		                        <div class="form-group">
									<label class="col-xs-3 control-label">Demandant</label>
									<input class="col-xs-6" name="demandant" value="${procediment.demandant}">						
								</div>		                        		                    </div>
		                    <div class="col-md-6"> 		                    	 
	                            <div class="form-group">
									<label class="col-xs-3 control-label">Num Autos</label>
									<input class="col-xs-6" name="numautos" value="${procediment.numAutos}">						
								</div>	
								<div class="form-group">
									<label class="col-xs-3 control-label">Demandat</label>
									<input class="col-xs-6" name="demandat" value="${procediment.demandat}">						
								</div>	
								<div class="form-group">
									<label class="col-xs-3 control-label">Quantia</label>
									<input class="col-xs-6" name="quantia" value="${procediment.quantia}">						
								</div>	                                                                         	
		                    </div>	
	                	</div>
	                	<div class="row">
	                		<div class="form-group">  			
                            	<label class="col-xs-2 control-label">Objecte demanda</label>
                            	<textarea class="col-xs-8" name="objecte" placeholder="objecte" rows="3">${procediment.objecteDemanda}</textarea>
	                        </div>
	                	</div>		                	 
	                	<div class="row">
	                		<div class="form-group">  			
                            	<label class="col-xs-2 control-label">Notes</label>
                            	<textarea class="col-xs-8" name="notes" placeholder="notes" rows="3">${procediment.notes}</textarea>
	                        </div>
	                	</div>	
	                	<div class="row">
		                    <div class="col-md-12">	                       
		                        <div class="row">  
		                        	<label class="col-xs-2 control-label">Arxius d'inici:</label> 
					                <div class="col-xs-8">  		
				                        <c:forEach items="${procediment.documentsIniciList}" var="arxiu" >
											<div class="document">
												<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
													${arxiu.nom} - ${arxiu.getDataString()}
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
								</div>
		                		<div class="row">
									<div class="form-group">
										<label class="col-xs-2 control-label">Adjuntar arxius d'inici:</label>
			                            <div class="col-xs-5">   
			                                <input type="file" class="btn" name="fileInici" multiple/><br/>
										</div> 									  						
			         				</div> 							
				            	</div>              		
		                    </div>
		                </div>
		                <div class="row">
		                    <div class="col-md-12">	                       
		                        <div class="row">  
		                        	<label class="col-xs-2 control-label">Arxius de comunicacions:</label> 
					                <div class="col-xs-8">  		
				                        <c:forEach items="${procediment.documentsComunicacioList}" var="arxiu" >
											<div class="document">
												<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
													${arxiu.nom} - ${arxiu.getDataString()}
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
								</div>
		                		<div class="row">
									<div class="form-group">
										<label class="col-xs-2 control-label">Adjuntar arxius de comunicacions:</label>
			                            <div class="col-xs-5">   
			                                <input type="file" class="btn" name="fileComunicacio" multiple/><br/>
										</div> 									  						
			         				</div> 							
				            	</div>              		
		                    </div>
		                </div>
		                <c:if test="${canModificarProcediment}">
	                		<div class="row">
		                		<div class="form-group">
							        <div class="col-xs-offset-9 col-xs-9">
							            <input type="submit" class="btn btn-success" value="Actualitzar procediment">							            
							        </div>
							    </div> 
		                	</div>
		                </c:if>
	                </form>	                
                	<div class="row">
	                    <div class="col-md-12">
	                        <h2>Tramitacions</h2>
                        	<c:forEach items="${procediment.tramitacionsList}" var="tramitacio" >					          	
				          		<div class="row">
			                		<div class="col-md-6">		                    	
					    				<div class="col-md-12">	
											<label class="col-xs-3 control-label">Tipus</label>
											<div class="col-xs-9">  
												${tramitacio.getTipusFormat()}	
											</div>
			                            </div>   
			                    	</div>
		                		</div>                		
						    	<div class="row">	
						    		<div class="col-md-6">		 
					            		<c:if test="${tramitacio.numstcia != ''}">
						                	<div class="col-md-12">    
					                        	<label class="col-xs-3 control-label">Num Autos</label>
					                       		${tramitacio.numstcia}
						                    </div> 
					                    </c:if>
					                    <c:if test="${tramitacio.quantia != ''}">
						                  	<div class="col-md-12">
												<label class="col-xs-3 control-label">Quantia</label>
												<div class="col-xs-9">  
													${tramitacio.quantia}		
												</div>			
											</div>
										</c:if>
										<c:if test="${tramitacio.getDataPagamentString() != ''}">
											<div class="col-md-12">
				                	 			<label class="col-xs-3 control-label">Data pagament</label>
				                                <div class="col-xs-9">  
				                                	${tramitacio.getDataPagamentString()}
				                                </div>
				                	 		</div> 
			                	 		</c:if> 
			                	 		<c:if test="${tramitacio.termini != ''}">
						                  	<div class="col-md-12">
												<label class="col-xs-3 control-label">Termini</label>
												<div class="col-xs-9">  
													${tramitacio.termini}	
												</div>				
											</div>
											<input type="hidden" name="terminiOriginal" value="${tramitacio.termini}">
										</c:if>
				                   		<c:if test="${tramitacio.getDataString() != ''}">
				                    	<div class="col-md-12">
			                	 			<label class="col-xs-3 control-label">Data</label>
			                               	<div class="col-xs-9">  
			                               		${tramitacio.getDataString()}
			                               	</div>
			                	 		</div>
			                	 		</c:if>
			                	 		<c:if test="${tramitacio.recurs != ''}">                   	 
				                            <div class="col-md-12">
												<label class="col-xs-3 control-label">Recurs</label>
												<div class="col-xs-9">  
													${tramitacio.recurs}	
												</div>				
											</div>
										</c:if>
										<c:if test="${tramitacio.sentencia != ''}">	
											<div class="col-md-12">
												<label class="col-xs-3 control-label">Sentència</label>
												<div class="col-xs-9">  
													${tramitacio.sentencia}	
												</div>				
											</div>	 
										</c:if>  
										<c:if test="${tramitacio.notes != ''}">	
											<div class="col-md-12">
												<label class="col-xs-3 control-label">Notes</label>
												<div class="col-xs-9">  
													${tramitacio.notes}
												</div>				
											</div>	 
										</c:if>  
				                	</div>				                	
				                </div>
			                	<div class="row">
				                    <div class="col-md-6">
				                    	<div class="col-md-12">
					                   		<label class="col-xs-3 control-label">Arxius:</label> 
					                    	<div class="col-xs-9">  		
						                        <c:forEach items="${tramitacio.documentsList}" var="arxiu" >
													<div class="document">
														<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
															${arxiu.nom} - ${arxiu.getDataString()}
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
										</div>
									</div>
								</div>		
								<c:if test="${canModificarProcediment}">                		 				                	
				                	<div class="row">
				                		<div class="form-group">
									        <div class="col-xs-offset-9 col-xs-9">
									            <a href="editTramitacio?ref=${tramitacio.idTramitacio}&refPro=${procediment.referencia}" class="loadingButton btn btn-success"  data-msg="actualitzar tramitació...">Actualitzar tramitació</a>					            
									        </div>
									    </div> 
				                	</div>
			                	</c:if>
					          	<div class="separator"></div>
					       	</c:forEach>    
	                    </div>
	                </div> 
	                <c:if test="${canModificarProcediment}">
		                <div class="row">					
							<div class="form-group">	
							  	<div class="col-md-offset-9 col-md-3">
							    	<a href="novaTramitacio?refPro=${procediment.referencia}" class="loadingButton btn btn-primary"  data-msg="nova tramitació...">Afegir tramitació</a>
								</div>
							</div>	
						</div>
					</c:if>
					<div class="row panel-body">
						<c:set var="tasquesList" value="${tasquesList}" scope="request"/>
						<jsp:include page="../tasca/include/_tasquesList.jsp"></jsp:include>
                	</div>
                	<c:if test="${canModificarProcediment}">
	                	<div class="row">					
							<div class="form-group">	
							  	<div class="col-md-offset-9 col-md-3">
							    	<a href="createTasca?idProcediment=${procediment.numAutos}&tipus=judicial" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova tasca</a>
								</div>
							</div>							
						</div>
					</c:if>
					<div class="row panel-body">					    		
						<c:set var="registres" value="${entrades}" scope="request"/>
						<c:set var="tipus" value="entrada" scope="request"/>
                		<jsp:include page="../registre/include/_registresList.jsp"></jsp:include>
					</div>
                </c:if>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>   
    <script src="js/judicial/modificar.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>