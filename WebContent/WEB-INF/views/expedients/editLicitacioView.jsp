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
                                <i class="fa fa-dashboard"></i> Licitació
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
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditLicitacio"> 
			   			<input type="hidden" name="redireccio" value="${redireccio}">  
			   			<input type="hidden" name="expedient" value="${expedient.expContratacio}">
                	 	<input type="hidden" name="informe" value="${informePrevi.idInf}">           
			   			
				   		<div class="form-group">			   				
		   					<div class="col-md-6">
		   						<h2>Preparacio Licitació</h2>
		   					</div>	
				       		<div class="col-md-12">				       			
				       			<div class="document">
			               			<label>Memòria més ordre d'inici signada:</label>											                  	
				           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.memoriaOrdreInici.getEncodedRuta()}">
										${informePrevi.memoriaOrdreInici.nom}
									</a>	
									<c:if test="${informePrevi.memoriaOrdreInici.signat}">
										<span data-ruta="${informePrevi.memoriaOrdreInici.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${informePrevi.memoriaOrdreInici.ruta != null}">
										<span data-ruta="${informePrevi.memoriaOrdreInici.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
									</c:if>
									<br>
									<div class="infoSign hidden">										
									</div>
								</div>	
								<input type="file" class="btn uploadImage" name="memoriaOrdreInici" /><br/>	
				       		</div>	
					      	<c:if test="${expedient.contracte == 'major'}">
					       		<div class="col-md-12">				       			
					       			<div class="document">
				               			<label>Justificació de procediment signada:</label>											                  	
					           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.justProcForma.getEncodedRuta()}">
											${informePrevi.justProcForma.nom}
										</a>	
										<c:if test="${informePrevi.justProcForma.signat}">
											<span data-ruta="${informePrevi.justProcForma.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.justProcForma.ruta != null}">
											<span data-ruta="${informePrevi.justProcForma.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">											
										</div>
									</div>	
									<input type="file" class="btn uploadImage" name="justProcForma" /><br/>	
					       		</div>	
					       		<div class="col-md-12">				       			
					       			<div class="document">
				               			<label>Justificació criteris d'adjudicació, condicions d'aptitud licitador i condicions especials signada:</label>											                  	
					           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.justCriterisAdjudicacio.getEncodedRuta()}">
											${informePrevi.justCriterisAdjudicacio.nom}
										</a>	
										<c:if test="${informePrevi.justCriterisAdjudicacio.signat}">
											<span data-ruta="${informePrevi.justCriterisAdjudicacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.justCriterisAdjudicacio.ruta != null}">
											<span data-ruta="${informePrevi.justCriterisAdjudicacio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">											
										</div>
									</div>	
									<input type="file" class="btn uploadImage" name="justCriterisAdjudicacio" /><br/>	
					       		</div>	
					       		<div class="col-md-12">				       			
					       			<div class="document">
				               			<label>Declaració d'urgència signada:</label>											                  	
					           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.declaracioUrgencia.getEncodedRuta()}">
											${informePrevi.declaracioUrgencia.nom}
										</a>	
										<c:if test="${informePrevi.declaracioUrgencia.signat}">
											<span data-ruta="${informePrevi.declaracioUrgencia.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.declaracioUrgencia.ruta != null}">
											<span data-ruta="${informePrevi.declaracioUrgencia.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">											
										</div>
									</div>	
									<input type="file" class="btn uploadImage" name="declaracioUrgencia" /><br/>	
					       		</div>	
					       		<div class="col-md-12">				       			
					       			<div class="document">
				               			<label>Resolució d'aprovació del projecte amb indicació de disponibilitat dels terrenys signada:</label>											                  	
					           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.aprovacioDispoTerrenys.getEncodedRuta()}">
											${informePrevi.aprovacioDispoTerrenys.nom}
										</a>	
										<c:if test="${informePrevi.aprovacioDispoTerrenys.signat}">
											<span data-ruta="${informePrevi.aprovacioDispoTerrenys.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.aprovacioDispoTerrenys.ruta != null}">
											<span data-ruta="${informePrevi.aprovacioDispoTerrenys.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
										</div>
									</div>	
									<input type="file" class="btn uploadImage" name="aprovacioDispoTerrenys" /><br/>	
					       		</div>	
					       		<div class="col-md-12">				       			
					       			<div class="document">
				               			<label>Informe insuficiència de mitjans signada:</label>											                  	
					           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.informeInsuficienciaMitjans.getEncodedRuta()}">
											${informePrevi.informeInsuficienciaMitjans.nom}
										</a>	
										<c:if test="${informePrevi.informeInsuficienciaMitjans.signat}">
											<span data-ruta="${informePrevi.informeInsuficienciaMitjans.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.informeInsuficienciaMitjans.ruta != null}">
											<span data-ruta="${informePrevi.informeInsuficienciaMitjans.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
										</div>
									</div>	
									<input type="file" class="btn uploadImage" name="insuficienciaMitjans" /><br/>	
					       		</div>			
					       		<div class="col-md-12">				       			
					       			<div class="document">
				               			<label>Aprovació expedient, plecs i despesa signada:</label>											                  	
					           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.aprovacioEXPPlecsDespesa.getEncodedRuta()}">
											${informePrevi.aprovacioEXPPlecsDespesa.nom}
										</a>	
										<c:if test="${informePrevi.aprovacioEXPPlecsDespesa.signat}">
											<span data-ruta="${informePrevi.aprovacioEXPPlecsDespesa.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<c:if test="${informePrevi.aprovacioEXPPlecsDespesa.ruta != null}">
											<span data-ruta="${informePrevi.aprovacioEXPPlecsDespesa.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
										</div>
									</div>	
									<input type="file" class="btn uploadImage" name="aprovacioEXPPlecsDespesa" /><br/>	
					       		</div>				       		
					       	</div>	
					   	</c:if>
				       	<div class="form-group">
				    		<div class="col-md-12">
						       	<div class="row">
			                		<div class="form-group">
								        <div class="col-xs-offset-6 col-xs-3">
								            <input type="submit" class="btn btn-primary" value="Actualitzar expedient">							            
								        </div>
								       	<div class="col-xs-3">
								        	<input id="createTascaPreLicitacio" data-informe="${informePrevi.idInf}" class="btn btn-success" value="Enviar documents a firma">							            
										</div>
									</div> 
			                	</div>
			                </div>
			           	</div>
					</form>	
					<div class="separator"></div>			
                	<form class="form-horizontal margin_top30" enctype="multipart/form-data" method="POST" action="DoAddOferta">
	               		<input type="hidden" name="expedient" value="${expedient.expContratacio}">  
						<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
						<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
						<input type="hidden" name="idTasca" value="-1">
						<input type="hidden" name="idInformePrevi" value="${informePrevi.idInf}">
						<div class="form-group">			   				
		   					<div class="col-md-6">
		   						<h2>Empreses presentades</h2>
		   					</div>	
			   			</div>										                    		
				   		<div class="form-group">
				        	<div class="col-md-4">	
				         		<label>Empresa</label>									            	 										            	 	
				            	<select class="selectpicker" name="llistaEmpreses" id="llistaEmpreses" data-live-search="true" data-size="10">						                                					                                	
				               		<c:forEach items="${empresesList}" var="empresa">
				                   		<option value="${empresa.cif}">${empresa.name}</option>
				                   	</c:forEach>	
				                </select>	
				        	</div>
				         	<div class="col-md-4">
				           		<label>Oferta (amb IVA)</label>
				            	<input name="oferta" id="oferta" placeholder="0000.00" required>
				            	<label class="">€</label>
				           	</div>	
				           	<div class="col-md-4">												        
				            	<input class="btn btn-primary" type="submit" name="afegirOfertaExpedient" value="Afegir oferta">
							</div>				                       		
				   		</div>
				   		<div class="form.group">
				   			<div class="col-md-2">
				           		<label>Presupost:</label>
				           	</div>
				            <div class="col-md-5">   
				                <input type="file" class="btn" name="file" /><br/>
							</div> 
				   		</div>
				   	</form>				   	
				   	<div class="row">		 				
			    		<div class="col-md-12">	
							<label>Resum presupostos</label>							                        
			               	<div class="table-responsive">							                        
			                	<table class="table table-striped table-bordered filerTable" id="ofertaTable">
			                       <thead>
			                           <tr>
			                           	<th>Oferta</th>
			                               <th>Licitador</th>
			                               <th>Licitador</th>
			                               <th>Licitador</th>
			                               <th>Import Oferta</th>
			                               <th>Import Oferta</th>
			                               <th>Presupost</th>
			                               <th>Control</th>							                                        							                                       
			                           </tr>
			                       </thead>
			                       <tbody>	
			                       	<c:forEach items="${informePrevi.llistaOfertes}" var="oferta" >
			                       		<tr>
			                       			<td>${oferta.idOferta}</td>
			                        		<td><a target="_blanck" href="empresa?cif=${oferta.cifEmpresa}">${oferta.nomEmpresa} (${oferta.cifEmpresa})</a></td>
			                        		<td>${oferta.nomEmpresa} (${oferta.cifEmpresa})</td>
			                        		<td>${oferta.cifEmpresa}</td>
			                        		<td>${oferta.plic}</td>
			                        		<td>${oferta.getPlicFormat()}</td>
			                        		<td><a target="_blanck" href="downloadFichero?ruta=${oferta.presupost.getEncodedRuta()}">${oferta.presupost.nom}</a></td>
			                        		<td>
			                        			<input class="btn btn-danger btn-sm deleteOferta" data-idoferta="${oferta.idOferta}" type="button" value="Eliminar">
			                        			<c:if test="${oferta.plic > 0}">
			                        				<input class="btn btn-primary btn-sm ofertaSeleccionada" type="button" value="Seleccionar">	
			                        			</c:if>		                        			                       			
			                        		</td>
			                       		</tr>
			                       	</c:forEach>						                                	                              	
			                       </tbody>
			                   	</table>
		               		</div>
          				</div>
					</div>						
					<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditLicitacio"> 
						<input type="hidden" name="redireccio" value="${redireccio}">  
			   			<input type="hidden" name="expedient" value="${expedient.expContratacio}">
                	 	<input type="hidden" name="informe" value="${informePrevi.idInf}">           
			   			<c:if test="${expedient.contracte == 'major'}">
			   				<div class="separator"></div>
				   			<div class="form-group">			   				
			   					<div class="col-md-6">
			   						<h2>Avaluació de criteris</h2>
			   					</div>	
			   					<div class="col-md-12">
			               			<label>Resolucions VAD:</label>		
			               			<c:forEach items="${informePrevi.resolucioVAD}" var="arxiu" >	
			               				<c:set var="arxiu" value="${arxiu}" scope="request"/>
			               				<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
									</c:forEach>										
									<input type="file" class="btn uploadImage" name="resoluciVAD" /><br/>	
					       		</div>	
					       		<div class="col-md-12">
			               			<label>Ratificació classificació:</label>		
			               			<c:forEach items="${informePrevi.ratificacioClassificacio}" var="arxiu" >	
			               				<c:set var="arxiu" value="${arxiu}" scope="request"/>
			               				<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
									</c:forEach>
										
									<input type="file" class="btn uploadImage" name="ratificacioClassificacio" /><br/>	
					       		</div>						       					       		
					       	</div>	
					       	<div class="form-group">
					    		<div class="col-md-12">
							       	<div class="row">
				                		<div class="form-group">
									        <div class="col-xs-offset-6 col-xs-3">
									            <input type="submit" class="btn btn-primary" value="Actualitzar expedient">							            
									        </div>
									       	<div class="col-xs-3">
									        	<input id="createTascaAvaluacioCriteris" data-informe="${informePrevi.idInf}" class="btn btn-success" value="Enviar documents a firma">							            
											</div>
										</div> 
				                	</div>
				                </div>
				           	</div>						
					    </c:if>
					</form>	
					<div class="separator"></div>		
			   		<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditLicitacio"> 
			   			<input type="hidden" name="redireccio" value="${redireccio}">  
			   			<input type="hidden" name="expedient" value="${expedient.expContratacio}">
                	 	<input type="hidden" name="informe" value="${informePrevi.idInf}">   
                	 	<input type="hidden" id="idOfertaSeleccionada" name="idOfertaSeleccionada" value="${informePrevi.ofertaSeleccionada.idOferta}">            
			   			<div class="form-group">
				        	<div class="col-md-6">
				            	<h2>Adjudicació</h2>	
				           		<label>Oferta seleccionada: </label>
				           		<label id="ofertaSeleccionada">${informePrevi.ofertaSeleccionada.nomEmpresa} (${informePrevi.ofertaSeleccionada.cifEmpresa})</label>	           		
				           	</div>	         	
				       	</div>					                    	
				       	<div class="form-group">					                    			
				        	<div class="col-md-12">							                    			
				     			<div class="row">	 
				     				<div class="col-md-12">						                    						
				     					<textarea class="form-control" name="propostaTecnica" placeholder="Motivació adjudicació" rows="3" required>${informePrevi.ofertaSeleccionada.comentari}</textarea> 
				       				</div>
				       			</div>
				       		</div>						                       		
				       	</div>	
				      	<div class="form-group">
				        	<div class="col-md-6">
				            	<label>Termini d'execució definitiu</label>
				             	<input name="termini" placeholder="1 mes" value="${informePrevi.ofertaSeleccionada.termini == '' ? informePrevi.propostaInformeSeleccionada.termini : informePrevi.ofertaSeleccionada.termini}" required>
				        	</div>
				       	</div>
				       	<c:if test="${expedient.tipus == 'obra'}">  
					       	<div class="form-group">
					        	<div class="col-md-6">
					            	<label>Cap d'Obra designat</label>
					             	<input name="capDobra" placeholder="" value="${informePrevi.ofertaSeleccionada.capDobra}">
					        	</div>
					       	</div>
				       	</c:if> 
				       	<div class="form-group">
				       		<div class="col-md-6">
				       			<div class="document">
			               			<label>Personal adscrit:</label>											                  	
				           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.ofertaSeleccionada.personalInscrit.getEncodedRuta()}">
										${informePrevi.ofertaSeleccionada.personalInscrit.nom}
									</a>
									<c:if test="${informePrevi.ofertaSeleccionada.personalInscrit.ruta != null}">
										<span data-ruta="${informePrevi.ofertaSeleccionada.personalInscrit.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
									</c:if>
									<br>									
								</div>	
								<input type="file" class="btn uploadImage" name="personalInscrit" /><br/>	
				       		</div>	
				       	</div>
				       	<c:if test="${expedient.contracte != 'major'}">
					       	<div class="form-group">
					       		<div class="col-md-6">					       		
			               			<label>Proposta tècnica signada:</label>	
			               			<c:forEach items="${informePrevi.propostaTecnica}" var="arxiu" >
			               				<c:set var="arxiu" value="${arxiu}" scope="request"/>
			               				<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
									</c:forEach>
									<input type="file" class="btn uploadImage" name="propostaTecnica" /><br/>	
					       		</div>
					       		<div class="col-md-6">
									<div class="form-group">
							    		<div class="col-md-12">
									       	<div class="row">
						                		<div class="form-group">
											        <div class="col-md-12">
											            <input id="createTascaLicitacio" data-informe="${informePrevi.idInf}" class="btn btn-success" value="Sol·licitar proposta tècnica">							            
											        </div>
											    </div> 
						                	</div>
						                </div>
						           	</div>
								</div>		
					       	</div>	
					    </c:if>		
					    <div class="form-group">  
							<div class="col-md-3">
								<label>Data resolució adjudicació</label>
                                <div class="input-group date datepicker">
								  	<input type="text" class="form-control" name="dataAdjudicacio" value="${informePrevi.expcontratacio.getDataAdjudicacioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
						</div>	       	
				       	<div class="form-group">				       		
				       		<div class="col-md-6">
				       			<div class="document">
			               			<label>Resolució d'adjudicació:</label>											                  	
				           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaDespesa.getEncodedRuta()}">
										${informePrevi.autoritzacioPropostaDespesa.nom}
									</a>	
									<c:if test="${informePrevi.autoritzacioPropostaDespesa.signat}">
										<span data-ruta="${informePrevi.autoritzacioPropostaDespesa.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${informePrevi.autoritzacioPropostaDespesa.ruta != null}">
										<span data-ruta="${informePrevi.autoritzacioPropostaDespesa.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
									</c:if>
									<br>
									<div class="infoSign hidden">										
									</div>
								</div>	
								<input type="file" class="btn uploadImage" name="autoritzacioDespesa" /><br/>	
				       		</div>				       		
				       	</div>
				       	<div class="form-group">
				    		<div class="col-md-12">
						       	<div class="row">
			                		<div class="form-group">
								        <div class="col-xs-offset-6 col-xs-3">
								            <input type="submit" class="btn btn-primary" value="Actualitzar expedient">							            
								        </div>
								        <c:if test="${expedient.contracte == 'major'}">
									       	<div class="col-xs-3">
									        	<input id="createTascaAutoDespesa" data-informe="${informePrevi.idInf}" class="btn btn-success" value="Enviar documents a firma">							            
											</div>
										</c:if>
										<c:if test="${expedient.contracte != 'major'}">
									       	<div class="col-xs-3">
									        	<input id="createTascaAutoDespesa" data-informe="${informePrevi.idInf}" class="btn btn-success" value="Sol·licitar resolució">							            
											</div>
										</c:if>
									</div> 
			                	</div>
			                </div>
			           	</div>			
				       	<div class="separator"></div>
						<div class="form-group">
				       		<div class="col-md-6">
				            	<h2>Formalització contracte</h2>	
			               	</div>
	   					</div>	
   					 	<div class="form-group">  
							<div class="col-md-3">
								<label>Data firma contracte</label>
                                <div class="input-group date datepicker">
								  	<input type="text" class="form-control" name="dataContracte" value="${informePrevi.expcontratacio.getDataFirmaString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
						</div>
	   					<div class="form-group">				       		
				       		<div class="col-md-6">
				       			<div class="document">
			               			<label>Contracte signat:</label>											                  	
				           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.contracteSignat.getEncodedRuta()}">
										${informePrevi.contracteSignat.nom}
									</a>	
									<c:if test="${informePrevi.contracteSignat.signat}">
										<span data-ruta="${informePrevi.contracteSignat.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${informePrevi.contracteSignat.ruta != null}">
										<span data-ruta="${informePrevi.contracteSignat.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
									</c:if>
									<br>
									<div class="infoSign hidden">										
									</div>
								</div>	
								<input type="file" class="btn uploadImage" name="contracte" /><br/>	
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
                </c:if>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/expedient/modificarLicitacio.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>