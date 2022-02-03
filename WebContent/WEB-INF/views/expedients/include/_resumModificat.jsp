<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		

		<div class="panel-body">	
			<c:if test="${propostaModificacio.anulat}">
				<div class="panel panel-danger">
				    <div class="panel-heading">
				        <div class="row">
				    		<div class="col-md-2">
				    			Anul·lat
				    		</div>
				    		<div class="col-md-10">
				    			Motiu: ${propostaModificacio.motiuAnulat}
				   			</div>
				    	</div>
				    </div>
				</div>
			</c:if>
			<p>			                     				
				<label>Identificador:</label> ${propostaModificacio.idInfEspecific}
			</p>
			<p>			                     				
				<label>Tipus:</label> ${propostaModificacio.getTipusModificacioFormat()}
			</p>
			<p>			                     				
				<label>Objecte:</label> ${propostaModificacio.propostaInformeSeleccionada.objecte}
			</p>
			
			<c:if test="${propostaModificacio.propostaInformeSeleccionada.tipusObra=='obr' && propostaModificacio.propostaInformeSeleccionada.llicencia}">
				<div class="row">
					<div class="col-md-4">
						<label>Llicència:</label> ${propostaModificacio.propostaInformeSeleccionada.tipusLlicencia}
					</div>					
				</div>
				<p></p>
			</c:if>
			<c:if test="${propostaModificacio.propostaInformeSeleccionada.termini != ''}">
				<c:choose>
					<c:when test="${propostaModificacio.tipusModificacio == 'termini'}">
						<label>Ampliació de termini:</label> ${propostaModificacio.propostaInformeSeleccionada.termini}
					</c:when>
					<c:otherwise>
						<label>Termini d'execució:</label> ${propostaModificacio.propostaInformeSeleccionada.termini}
					</c:otherwise>
				</c:choose>				
			</c:if>
			<c:if test="${propostaModificacio.tipusModificacio != 'termini' && propostaModificacio.tipusModificacio != 'resolucioContracte' && propostaModificacio.tipusModificacio != 'informeExecucio' && propostaModificacio.tipusModificacio != 'ocupacio'}">
				<div class="row">
					<div class="col-md-12">
						<p>
							<label>Valor afegit</label>
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<p>
				       		<label>PBase:</label> ${propostaModificacio.ofertaSeleccionada.pbase}€		
				       	</p>				                                
					</div>
					<div class="col-md-4">
						<p> 
							<label>IVA:</label> ${propostaModificacio.ofertaSeleccionada.iva}€
						</p>
					</div>
					<div class="col-md-4">
						<p>
							<label>Plic:</label> ${propostaModificacio.ofertaSeleccionada.plic}€
						</p>
				   	</div>					  
				</div>
				<div class="row">
					<div class="col-md-4">
						<p>
				       		<label>% sobre adjudicació: </label> <m:formatNumber type = "number" maxIntegerDigits="2" value = "${propostaModificacio.ofertaSeleccionada.pbase * 100 / informePrevi.ofertaSeleccionada.pbase}" />	%	
				       	</p>				                                
					</div>					  
				</div>
				<c:if test="${propostaModificacio.tipusModificacio == 'certfinal'}">
					<div class="row">
						<div class="col-md-4">
							<p>
					       		<label>% total variació sobre adjudicació: </label> <m:formatNumber type = "number" maxIntegerDigits="2" value = "${(informePrevi.totalCertificat - informePrevi.ofertaSeleccionada.plic) * 100 / informePrevi.ofertaSeleccionada.plic}" />	%	
					       	</p>				                                
						</div>					  
					</div>
				</c:if>
			</c:if>
			<c:if test="${propostaModificacio.propostaTecnica.size() > 0}">	
				<div class="document">			
	               	<label>Informe Tècnic</label>
                        <c:forEach items="${propostaModificacio.propostaTecnica}" var="arxiu" >	
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
				</div>	
			</c:if>
			<p></p>
			<c:if test="${propostaModificacio.informeDF.size() > 0}">		
				 <div class="document">			
	               	<label>Informe DF</label>
                        <c:forEach items="${propostaModificacio.informeDF}" var="arxiu" >	
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
				</div>			
			</c:if>
			<p></p>	
			<c:if test="${propostaModificacio.resInici.size() > 0}">
			<div class="document">			
               	<label>Resolució inici</label>
                     <c:forEach items="${propostaModificacio.resInici}" var="arxiu" >	
					<c:set var="arxiu" value="${arxiu}" scope="request"/>
					<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
				</c:forEach>
			</div>						
			</c:if>	
			<p></p>		
			<c:if test="${propostaModificacio.conformeAreaEconomivaPropostaActuacio.size() > 0}">
				<p>
					<div class="document">
						<label>Certificat d'existència de crèdit:</label>	
						<div class="row col-md-12">
							<c:forEach items="${propostaModificacio.conformeAreaEconomivaPropostaActuacio}" var="arxiu" >
								<c:set var="arxiu" value="${arxiu}" scope="request"/>
								<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
							</c:forEach>
							<br>					            		
						</div>
					</div>																	
				</p>	
			</c:if>	
			<p></p>
			<c:if test="${propostaModificacio.tramitsModificacio.size() > 0}">
				<div class="document">			
	               	<label>Tramits</label>
	                     <c:forEach items="${propostaModificacio.tramitsModificacio}" var="arxiu" >	
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
				</div>						
			</c:if>	
			<p></p>	
			<c:if test="${propostaModificacio.informeJuridic.size() > 0}">		
				 <div class="document">			
	               	<label>Informe Jurídic</label>
                         <c:forEach items="${propostaModificacio.informeJuridic}" var="arxiu" >	
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
				</div>			
			</c:if>
			<p></p>
			<c:if test="${propostaModificacio.resolucioFinalModificacio.size() > 0}">
				<div class="document">			
	               	<label>Resolució Final</label>
	                     <c:forEach items="${propostaModificacio.resolucioFinalModificacio}" var="arxiu" >	
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
				</div>						
			</c:if>	
			<p></p>	
			<c:if test="${propostaModificacio.autoritzacioPropostaDespesa.size() > 0}">
               	<label>Resolució aprovació:</label>													                  	
           		<c:forEach items="${propostaModificacio.autoritzacioPropostaDespesa}" var="arxiu" >	
					<c:set var="arxiu" value="${arxiu}" scope="request"/>
					<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
				</c:forEach>
				<p></p>
				<p>
			 		<label>Data firma</label> ${propostaModificacio.propostaInformeSeleccionada.getDataFirmaModificacioString()}	
			 	</p>
			</c:if>		
			<p></p>			
			<c:if test="${propostaModificacio.formalitzacioSignat.ruta != null}">			 						
				<div class="document">			
	               	<label>Formalització</label>                        
					<c:set var="arxiu" value="${propostaModificacio.formalitzacioSignat}" scope="request"/>
					<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>
				</div>			
			</c:if>
		</div>
		<c:if test="${!propostaModificacio.anulat}">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
			  			<c:if test="${canModificarExpedient}">
							<div class="col-md-offset-7 col-md-2 margin_top30">
								<a href="editModificatInforme?idMod=${propostaModificacio.idInf}&idinf=${informePrevi.idInf}" class="btn btn-primary" role="button">Editar</a>
							</div>	
						</c:if>
						<c:if test="${canModificarExpedient && !informePrevi.expcontratacio.isAnulat()}">
							<div class="col-md-2">
								<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="anularModificat">
									<input class="hidden" name="expedient" value="${informePrevi.expcontratacio.expContratacio}">
					              		<input class="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
					              		<input class="hidden" name="idInforme" value="${informePrevi.idInf}">
					              		<input class="hidden" name="idMofificat" value="${propostaModificacio.idInf}">
					              		<input class="hidden" name="redireccio" value="${redireccio}">
										<div class="col-md-2 margin_top30">
											<input class="btn btn-danger" data-toggle="modal" data-target="#myModalInforme${propostaModificacio.idInf}" name="anular" value="Anul·lar">
										</div>
					       			<!-- Modal -->
									<div id="myModalInforme${propostaModificacio.idInf}" class="modal fade" role="dialog">
										<div class="modal-dialog">																	
									    <!-- Modal content-->
									    	<div class="modal-content">
									      		<div class="modal-header">
									        		<button type="button" class="close" data-dismiss="modal">&times;</button>
									        		<h4 class="modal-title">Motiu anul·lació</h4>
									      		</div>
									      		<div class="modal-body">
									        		<textarea name="motiuAnulacio" required></textarea>
									      		</div>
									      		<div class="modal-footer">
									        		<input class="btn btn-danger" type="submit" name="anular" value="Anul·lar">
									      		</div>
								    		</div>																	
									  	</div>
									</div> 
								</form>
							</div>
						</c:if>
					</div>
				</div>
			</div>	
		</c:if>
	