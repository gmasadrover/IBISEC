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
                                <i class="fa fa-dashboard"></i> Procediment Judicial
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
                	<div class="row">				    		
			    		<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditProcediment">
			    			<input type="hidden" name="referencia" value="${procediment.referencia}">
			    			<input type="hidden" name="canvi" value="canviEstat">                        	 
				        	<div class="col-md-6"> 	
					        	<div class="form-group">
									<label class="col-xs-3 control-label">Estat</label>
									<input type="hidden" id="estatSelected" value="${procediment.estat}" />
									<div>
								    	<select class="selectpicker" id="estatList" name="estat">
										  	<option value="obert">Obert</option>
										  	<option value="execucio">En execuci??</option>
										  	<option value="arxiu">Arxivat</option>
										  	<option value="altres">Altres</option>
										</select>							      	
								 	</div>		
								 </div>
							</div>						                                                        	
				      		<c:if test="${canModificarProcediment}">
				               	<div class="col-md-6"> 	
						        	<div class="form-group">						        
								        <div class="col-xs-3">
								             <input type="submit" class="btn btn-success" value="Actualitzar estat procediment">					            
								        </div>
								   	</div>
								</div>									   			                	
			                </c:if>	
		       			</form>
				 	</div> 
				 	<div id="procedimentObert"> 				 		
						<c:set var="procedimentOriginal" value="${procediment}" scope="request"/>
	                	<div class="tabbable">
	                    	<ul class="nav nav-tabs">
							   	<li class='active'><a data-toggle="tab" href="#1estancia${procedimentOriginal.referencia}">1A Inst??ncia</a></li>
							   	<li class=""><a data-toggle="tab" href="#2instancia${procedimentOriginal.referencia}">2A Inst??ncia</a></li>
							   	<li class=""><a data-toggle="tab" href="#altres${procedimentOriginal.referencia}">Altres Recursos</a></li>
							   	<li class=""><a data-toggle="tab" href="#cautelar${procedimentOriginal.referencia}">Mesures cautelars</a></li>
							</ul>						 	
						  	<div class="tab-content">
						  		<div id="1estancia${procedimentOriginal.referencia}" class="tab-pane fade in active">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tipus" value="1ainstancia" scope="request"/>
										<c:set var="procediment" value="${procedimentOriginal}" scope="request"/>
						  				<jsp:include page="include/_formulariProcediment.jsp"></jsp:include>
						  			</div>
						  		</div>
						  		<div id="2instancia${procedimentOriginal.referencia}" class="tab-pane fade">
						  		 	<div class="col-md-12 bordertab">
						  		 		<c:set var="tipus" value="2ainstancia" scope="request"/>
						  		 		<c:set var="procediment" value="${procedimentOriginal.segonaInstancia}" scope="request"/>
						  		 		<jsp:include page="include/_formulariProcediment.jsp"></jsp:include>
						  		 	</div>
						  		</div>
						  		<div id="altres${procedimentOriginal.referencia}" class="tab-pane fade">
									<div class="col-md-12 bordertab">
										<c:set var="tipus" value="altresrecursosobert" scope="request"/>
										<c:set var="procediment" value="${procedimentOriginal.altresRecursosObert}" scope="request"/>
										<jsp:include page="include/_formulariProcediment.jsp"></jsp:include>												    
									</div>
								</div>			
								<div id="cautelar${procedimentOriginal.referencia}" class="tab-pane fade">
									<div class="col-md-12 bordertab">
										<c:set var="tipus" value="mesurescautelars" scope="request"/>
										<c:set var="procediment" value="${procedimentOriginal.mesuresCautelars}" scope="request"/>
										<jsp:include page="include/_formulariProcediment.jsp"></jsp:include>												    
									</div>
								</div>						
						  	</div>
						</div>
					</div>
                	<div id="procedimentExecucio" class="hidden"> 
                		<div class="tabbable">
	                    	<ul class="nav nav-tabs">
							   	<li class='active'><a data-toggle="tab" href="#execucio${procedimentOriginal.referencia}">Execuci??</a></li>
							   	<li class=""><a data-toggle="tab" href="#recurs${procedimentOriginal.referencia}">Recurs</a></li>
							   	<li class=""><a data-toggle="tab" href="#cautelarExecucio${procedimentOriginal.referencia}">Mesures cautelars</a></li>
							</ul>
						  	<div class="tab-content">
						  		<div id="execucio${procedimentOriginal.referencia}" class="tab-pane fade in active">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tipus" value="execucio" scope="request"/>
						  				${procedimentOriginal.execucio.referencia}
										<c:set var="procediment" value="${procedimentOriginal.execucio}" scope="request"/>
						  				<jsp:include page="include/_formulariProcediment.jsp"></jsp:include>
						  			</div>
						  		</div>
						  		<div id="recurs${procedimentOriginal.referencia}" class="tab-pane fade">
						  		 	<div class="col-md-12 bordertab">
						  		 		<c:set var="tipus" value="recursexecucio" scope="request"/>
										<c:set var="procediment" value="${procedimentOriginal.recursExecucio}" scope="request"/>			
						  		 		<jsp:include page="include/_formulariProcediment.jsp"></jsp:include>
						  		 	</div>
						  		</div>	
						  		<div id="cautelarExecucio${procedimentOriginal.referencia}" class="tab-pane fade">
									<div class="col-md-12 bordertab">
										<c:set var="tipus" value="mesurescautelars" scope="request"/>
										<c:set var="procediment" value="${procedimentOriginal.mesuresCautelars}" scope="request"/>
										<jsp:include page="include/_formulariProcediment.jsp"></jsp:include>												    
									</div>
								</div>						  						
						  	</div>
						</div> 
                	</div>       
                	<div class="row">
	                    <div class="col-md-12">
	                        <h2>Tramitacions</h2>
                        	<c:forEach items="${procedimentOriginal.tramitacionsList}" var="tramitacio" >	
                        		<c:if test="${tramitacio.pendentTercers}">
                        			<div class="col-md-12">		                    	
					    				<div class="col-md-10">	
											<label class="col-xs-4 control-label red">Tramit pendent de tercers</label>
										</div>
									</div>
									<div class="col-md-12">		                    	
					    				<div class="col-md-10">	
											<label class="col-xs-4 control-label">${tramitacio.notes}</label>
										</div>
									</div>
                        		</c:if>		
                        		<c:if test="${tramitacio.pendentProvisio}">
                        			<div class="col-md-12">		                    	
					    				<div class="col-md-10">	
											<label class="col-xs-4 control-label red">Tramit pendent de provisi??</label>
										</div>
									</div>
									<div class="col-md-12">		                    	
					    				<div class="col-md-10">	
											<label class="col-xs-4 control-label">${tramitacio.notes}</label>
										</div>
									</div>
                        		</c:if>				          	
				          		<div class="row">
			                		<div class="col-md-6">		                    	
					    				<div class="col-md-12">	
											<label class="col-xs-4 control-label">Tipus</label>
											<div class="col-xs-8">  
												${tramitacio.getTipusFormat()}	
											</div>
			                            </div>   
			                    	</div>
		                		</div>                		
						    	<div class="row">	
						    		<div class="col-md-6">		 
					            		<c:if test="${tramitacio.numstcia != null && tramitacio.numstcia != ''}">
						                	<div class="col-md-12">    
					                        	<label class="col-xs-4 control-label">Num Autos</label>
					                       		${tramitacio.numstcia}
						                    </div> 
					                    </c:if>
					                    <c:if test="${tramitacio.quantia != null && tramitacio.quantia != ''}">
						                  	<div class="col-md-12">
												<label class="col-xs-4 control-label">Quantia</label>
												<div class="col-xs-8">  
													${tramitacio.quantia}		
												</div>			
											</div>
										</c:if>
										<c:if test="${tramitacio.getDataDocument() != null && tramitacio.getDataDocumentString() != ''}">
											<div class="col-md-12">
				                	 			<label class="col-xs-4 control-label">Data document</label>
				                                <div class="col-xs-8">  
				                                	${tramitacio.getDataDocumentString()}
				                                </div>
				                	 		</div> 
			                	 		</c:if> 
			                	 		<c:if test="${tramitacio.termini != null && tramitacio.termini != ''}">
						                  	<div class="col-md-12">
												<label class="col-xs-4 control-label">Termini</label>
												<div class="col-xs-8">  
													${tramitacio.termini}	
												</div>				
											</div>
											<input type="hidden" name="terminiOriginal" value="${tramitacio.termini}">
										</c:if>
				                   		<c:if test="${tramitacio.getDataRegistre() != null && tramitacio.getDataRegistreString() != ''}">
				                    	<div class="col-md-12">
			                	 			<label class="col-xs-4 control-label">Data registre</label>
			                               	<div class="col-xs-8">  
			                               		${tramitacio.getDataRegistreString()}
			                               	</div>
			                	 		</div>
			                	 		</c:if>
			                	 		<c:if test="${tramitacio.recurs != null && tramitacio.recurs != ''}">                   	 
				                            <div class="col-md-12">
												<label class="col-xs-4 control-label">Recurs</label>
												<div class="col-xs-8">  
													${tramitacio.recurs}	
												</div>				
											</div>
										</c:if>
										<c:if test="${tramitacio.sentencia != null && tramitacio.sentencia != ''}">	
											<div class="col-md-12">
												<label class="col-xs-4 control-label">Sent??ncia</label>
												<div class="col-xs-8">  
													${tramitacio.sentencia}	
												</div>				
											</div>	 
										</c:if>  
										<c:if test="${tramitacio.descripcio != null && tramitacio.descripcio != ''}">	
											<div class="col-md-12">
												<label class="col-xs-4 control-label">Descripcio</label>
												<div class="col-xs-8">  
													${tramitacio.descripcio}
												</div>				
											</div>	 
										</c:if>  
				                	</div>				                	
				                </div>
			                	<div class="row">
				                    <div class="col-md-6">
				                    	<div class="col-md-12">
					                   		<label class="col-xs-4 control-label">Arxius:</label> 
					                    	<div class="col-xs-8">  		
						                        <c:forEach items="${tramitacio.documentsList}" var="arxiu" >
						                        	<c:set var="arxiu" value="${arxiu}" scope="request"/>
													<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>				            		
												</c:forEach>	
											</div>
										</div>
									</div>
								</div>		
								<c:if test="${canModificarProcediment && tramitacio.tipus != 'Pagament'}">                	
				                	<div class="row">
				                		<div class="form-group">
									        <div class="col-xs-offset-6 col-xs-3">
									            <a href="#" class="deleteTramitacio loadingButton btn btn-danger" data-ref="${tramitacio.idTramitacio}">Eliminar tramitaci??</a>					            
									        </div>
									        <div class="col-xs-3">
									            <a href="editTramitacio?ref=${tramitacio.idTramitacio}&refPro=${procedimentOriginal.referencia}" class="loadingButton btn btn-success"  data-msg="actualitzar tramitaci??...">Actualitzar tramitaci??</a>					            
									        </div>
									    </div> 
				                	</div>
			                	</c:if>
					          	<div class="separator"></div>
					       	</c:forEach>    
	                    </div>
	                </div> 
	               
	                <div class="row">					
						<div class="form-group">
							<div class="col-md-offset-6 col-md-3">
						    	<a href="pagamentJudicial?refPro=${procedimentOriginal.referencia}" class="loadingButton btn btn-primary"  data-msg="nova tramitaci??...">Tramitaci?? pagament</a>
							</div>	
							<c:if test="${canModificarProcediment}">
							  	<div class="col-md-3">
							    	<a href="novaTramitacio?refPro=${procedimentOriginal.referencia}" class="loadingButton btn btn-success"  data-msg="nova tramitaci??...">Afegir tramitaci??</a>
								</div>
							</c:if>
						</div>	
					</div>
					
					<div class="row panel-body">
						<c:set var="tasquesList" value="${tasquesList}" scope="request"/>
						<jsp:include page="../tasca/include/_tasquesList.jsp"></jsp:include>
                	</div>                	
                	<div class="row">					
						<div class="form-group">	
						  	<div class="col-md-offset-9 col-md-3">
						    	<a href="createTasca?idProcediment=${procedimentOriginal.referencia}&tipus=judicial" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova tasca</a>
							</div>
						</div>							
					</div>
					<div class="row panel-body">					    		
						<c:set var="registres" value="${entrades}" scope="request"/>
						<c:set var="tipus" value="entrada" scope="request"/>
                		<jsp:include page="../registre/include/_registresList.jsp"></jsp:include>
					</div>
					<div class="row panel-body">					    		
						<c:set var="registres" value="${sortides}" scope="request"/>
						<c:set var="tipus" value="sortida" scope="request"/>
                		<jsp:include page="../registre/include/_registresList.jsp"></jsp:include>
					</div>
					<div class="row panel-body">
						<h4>Factures</h4>
						<div class="table-responsive">                        
				        	<table class="table table-striped table-bordered filerTable factures">
				            	<thead>
				                	<tr>                                        
				                    	<th>Factura</th>				                  		
				                      	<th>Data entrada</th>
				                      	<th>Data entrada</th>
				                       	<th>Data factura</th>
				                      	<th>Data factura</th>
				                      	<th>Data pasada a conf</th>
				                      	<th>Data pasada a conf</th>
				                      	<th>Data conformada</th>
				                      	<th>Data conformada</th>
				                      	<th>Data contabilitat</th>
				                      	<th>Data contabilitat</th>
				                       	<th>Import</th>
				                       	<th>Nombre factura</th>
				                      	<th>Tipus</th>				                        
				                      	<th>notes</th>  
				                      	<th>Arxiu</th>
				                   	</tr>
				               	</thead>
				              	<tbody>
				                 	<c:forEach items="${llistaFactures}" var="factura" >
					          			<tr class="${factura.anulada ? 'danger' : ''}">							          	
							           		<td><a href="facturaDetalls?ref=${factura.idFactura}">${factura.idFactura}</a></td>							            	
							            	<td>${factura.getDataEntradaString()}</td>
							            	<td>${factura.dataEntrada}</td>
							            	<td>${factura.getDataFacturaString()}</td>
							            	<td>${factura.dataFactura}</td>
							            	<td>${factura.getDataEnviatConformadorString()}</td>
							            	<td>${factura.dataEnviatConformador}</td>
							            	<td>${factura.getDataConformacioString()}</td>
							            	<td>${factura.dataConformacio}</td>
							            	<td>${factura.getDataEnviatComptabilitatString()}</td>
							            	<td>${factura.dataEnviatComptabilitat}</td>
							            	<td>${factura.valor}</td>
							            	<td>${factura.nombreFactura}</td>
							            	<td>${factura.tipusFactura}</td>
							            	<td>${factura.notes}</td>	 	
							            	<td><a target="_blanck" href="downloadFichero?ruta=${factura.factura.getEncodedRuta()}">${factura.factura.nom}</a></td>				            	
							          	</tr>
							       	</c:forEach>
			              		</tbody>
			             	</table>
			       		</div>															
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
    <script src="js/registre/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>