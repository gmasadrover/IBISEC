<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title">
			<a data-toggle="collapse" data-parent="#accordionInformes" href="#expedient${informePrevi.idInf}">Expedient: ${informePrevi.expcontratacio.expContratacio != '-1' ? informePrevi.expcontratacio.expContratacio : informePrevi.idInf} - ${informePrevi.expcontratacio.getDataLiquidacio()!=null ? 'Contracte liquidat' : informePrevi.estat == 'anulat' ? 'Anul·lat' : informePrevi.getEstatEconomic()}</a>
		</h4>
	</div>
    <div id="expedient${informePrevi.idInf}" class="panel-collapse collapse ${informePrevi.idInf == informeSeleccionat ? 'in' : ''}">
    	<div class="panel-body">			      		
    		<div class="row panel-body">
    			<c:if test="${informePrevi.expcontratacio.anulat}">
					<div class="panel panel-danger">
					    <div class="panel-heading">
					        <div class="row">
					    		<div class="col-md-2">
					    			Anul·lat
					    		</div>
					    		<div class="col-md-10">
					    			Motiu: ${informePrevi.expcontratacio.motiuAnulament}
					   			</div>
					    	</div>
					    </div>
					</div>
				</c:if>
	    		<div class="tabbable">
                   	<ul class="nav nav-tabs">
                   		<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'conveni' && informePrevi.propostaInformeSeleccionada.tipusObra != 'acordMarc'}">
	                   	 	<li class="${informePrevi.getEstat() == 'acabat' || informePrevi.getEstat() == null ? 'estatExp active' : '' }"><a data-toggle="tab" href="#resum${informePrevi.idInf}">Resum</a></li>	
						   	<li><a data-toggle="tab" href="#personal${informePrevi.idInf}">Personal</a></li>
					   	</c:if>
					   	<li class="${!informePrevi.expcontratacio.anulat && informePrevi.getEstat() == 'previs' ? 'estatExp active' : '' }"><a data-toggle="tab" href="#informe${informePrevi.idInf}">${informePrevi.propostaInformeSeleccionada.tipusObra != 'conveni' ? 'Previs' : 'Conveni'}</a></li>
					   	<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'conveni'}">
					   		<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'acordMarc'}">
						   		<li class="${!informePrevi.expcontratacio.anulat && informePrevi.getEstat() == 'licitacio' ? 'estatExp active' : '' }"><a data-toggle="tab" href="#licitacio${informePrevi.idInf}">Licitació</a></li>							   											    
						    </c:if>
						    <li class="${!informePrevi.expcontratacio.anulat && informePrevi.getEstat() == 'execucio' ? 'estatExp active' : '' }"><a data-toggle="tab" href="#execucio${informePrevi.idInf}">Execució</a></li>	
						    <c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'acordMarc'}">
						    	<li class="${!informePrevi.expcontratacio.anulat && informePrevi.getEstat() == 'garantia' ? 'estatExp active' : '' }"><a data-toggle="tab" href="#garantia${informePrevi.idInf}">Garantia</a></li>	
							</c:if>
						</c:if>		
					    <li><a data-toggle="tab" href="#documents${informePrevi.idInf}" class="carregarDocTecnica" data-iscap="${isCap}" data-idincidencia="${incidencia.idIncidencia}" data-idactuacio="${actuacio.referencia}" data-idinf="${informePrevi.idInf}">Documentació</a></li>			   
				 	</ul>
				 	<c:set var="informePrevi" value="${informePrevi}" scope="request"/>
				  	<div class="tab-content">	
				  		<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'conveni' && informePrevi.propostaInformeSeleccionada.tipusObra != 'acordMarc'}">				  		
							<div id="resum${informePrevi.idInf}" class="tab-pane fade ${informePrevi.getEstat() == 'acabat' || informePrevi.getEstat() == null ? 'in active' : '' }">
					  			<div class="col-md-12 bordertab">
					  				<jsp:include page="_resumExpedient.jsp"></jsp:include>
					  			</div>
					  		</div>
					  	</c:if>
				  		<div id="informe${informePrevi.idInf}" class="tab-pane fade ${informePrevi.propostaInformeSeleccionada.tipusObra == 'conveni' || (!informePrevi.expcontratacio.anulat && informePrevi.estat == 'previs') ? 'in active' : '' }">
				  		 	<div class="col-md-12 bordertab">
				  		 		<jsp:include page="_resumInforme.jsp"></jsp:include>
				  		 	</div>
				  		</div>
				  		<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'conveni'}">
				  			<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'acordMarc'}">
						  		<div id="licitacio${informePrevi.idInf}" class="tab-pane fade ${!informePrevi.expcontratacio.anulat && informePrevi.getEstat() == 'licitacio' ? 'in active' : '' }">
									<div class="col-md-12 bordertab">
										<jsp:include page="_resumLicitacio.jsp"></jsp:include>												    
									</div>
								</div>
							</c:if>
							<div id="execucio${informePrevi.idInf}" class="tab-pane fade ${!informePrevi.expcontratacio.anulat && informePrevi.getEstat() == 'execucio' ? 'in active' : '' }">
								<div class="col-md-12 bordertab">
									<jsp:include page="_resumExecucio.jsp"></jsp:include>
								</div>
							</div>
							<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'acordMarc'}">
								<div id="garantia${informePrevi.idInf}" class="tab-pane fade ${!informePrevi.expcontratacio.anulat && informePrevi.getEstat() == 'garantia' ? 'in active' : '' }">
									<div class="col-md-12 bordertab">
										<jsp:include page="_resumGarantia.jsp"></jsp:include>
									</div>
								</div>											
								<div id="personal${informePrevi.idInf}" class="tab-pane fade">
									<div class="col-md-12 bordertab">
										<jsp:include page="_resumPersonal.jsp"></jsp:include>
									</div>
								</div>	 
							</c:if>
						</c:if> 
						<div id="documents${informePrevi.idInf}" class="tab-pane fade">							
							<div class="tabbable">
			                   	<ul class="nav nav-tabs">								   	
								    <c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'conveni' && informePrevi.propostaInformeSeleccionada.tipusObra != 'acordMarc'}">
								   		<li class="active"><a data-toggle="tab" href="#documentacioTecnica${informePrevi.idInf}">Doc Tècnica</a></li>	
								   		<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == null || informePrevi.propostaInformeSeleccionada.tipusObra == 'obr'}">							   
								   			<li><a data-toggle="tab" href="#urbanisme${informePrevi.idInf}" class="carregarDocumentsUrbanisme" data-iscap="${isCap}" data-idincidencia="${incidencia.idIncidencia}" data-idactuacio="${actuacio.referencia}" data-idinf="${informePrevi.idInf}">Aut Urb</a></li>	
								  		</c:if>
								  		<li><a data-toggle="tab" href="#documentacio${informePrevi.idInf}" class="carregarAltresDocuments" data-iscap="${isCap}" data-idincidencia="${incidencia.idIncidencia}" data-idactuacio="${actuacio.referencia}" data-idinf="${informePrevi.idInf}" data-idtasca="${informePrevi.idTasca}">Altre doc</a></li>
								    	<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == 'obr'}">
									    	<li><a data-toggle="tab" href="#actes${informePrevi.idInf}" class="carregarActes" data-iscap="${isCap}" data-idincidencia="${incidencia.idIncidencia}" data-idactuacio="${actuacio.referencia}" data-idinf="${informePrevi.idInf}">Actes</a></li>
										    <li><a data-toggle="tab" href="#documentacioInstalacions${informePrevi.idInf}" class="carregarDocInstalacions" data-iscap="${isCap}" data-idincidencia="${incidencia.idIncidencia}" data-idactuacio="${actuacio.referencia}" data-idinf="${informePrevi.idInf}">Instal·lacions</a></li>
										</c:if>
									     <li><a data-toggle="tab" href="#recursAdministratiu${informePrevi.idInf}">Recurs Adm</a></li>	
									</c:if>
									<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == 'conveni' || informePrevi.propostaInformeSeleccionada.tipusObra == 'acordMarc'}">
										<li class="active"><a data-toggle="tab" href="#documentacioTecnica${informePrevi.idInf}">Tramits</a></li>	
										<li><a data-toggle="tab" href="#documentacio${informePrevi.idInf}" class="carregarAltresDocuments" data-iscap="${isCap}" data-idincidencia="${incidencia.idIncidencia}" data-idactuacio="${actuacio.referencia}" data-idinf="${informePrevi.idInf}" data-idtasca="${informePrevi.idTasca}">Altre doc</a></li>							   
									</c:if>				   
							 	</ul>
							 	<div class="tab-content">							 		
									<div id="documentacioTecnica${informePrevi.idInf}" class="tab-pane fade in active">
							  			<div class="col-md-12 bordertab">
							  				<jsp:include page="_resumDocumentacioTecnica.jsp"></jsp:include>
							  			</div>
							  		</div>
							  		<div id="urbanisme${informePrevi.idInf}" class="tab-pane fade">
						  				<div class="col-md-12 bordertab">
											<jsp:include page="_resumUrbanisme.jsp"></jsp:include>
										</div>
									</div>
							  		<div id="documentacio${informePrevi.idInf}" class="tab-pane fade">
							  			<div class="col-md-12 bordertab">
							  				<jsp:include page="_resumDocuments.jsp"></jsp:include>
							  			</div>
							  		</div>
							  		<div id="actes${informePrevi.idInf}" class="tab-pane fade">
							  			<div class="col-md-12 bordertab">
							  				<jsp:include page="_resumActes.jsp"></jsp:include>
							  			</div>
							  		</div>
							  		<div id="documentacioInstalacions${informePrevi.idInf}" class="tab-pane fade">
							  			<div class="col-md-12 bordertab">
							  				<jsp:include page="_resumDocumentsInstalacions.jsp"></jsp:include>
							  			</div>
							  		</div>
							  		<div id="recursAdministratiu${informePrevi.idInf}" class="tab-pane fade">
							  			<div class="col-md-12 bordertab">
							  				<jsp:include page="_resumRecursAdministratiu.jsp"></jsp:include>
							  			</div>
							  		</div>
								</div>	
							 </div>
						</div>
				  	</div>
				</div>
			</div>
			<div class="col-md-12 panel-group" id="accordion${informePrevi.idInf}">
				<div class="panel panel-default">
				    <div class="panel-heading">
				      	<h4 class="panel-title">
				        	<a data-toggle="collapse" data-parent="#accordion${informePrevi.idInf}" href="#feines${informePrevi.idInf}">Feines</a>
				      	</h4>
				    </div>					    
				    <div id="feines${informePrevi.idInf}" class="panel-collapse collapse">	
				    	<div class="panel-body">
							<c:if test="${canCreateFeina}">
						  		<div class="row margin_bottom10">
						 			<div class="col-md-12 panel">
										<a href="createFeina?idActuacio=${actuacio.referencia}" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova feina</a>
									</div>
								</div>
						   	</c:if>
							<div class="row panel-body">
						      	<jsp:include page="../../feina/include/_resumFeina.jsp"></jsp:include>
						    </div>
						 </div>
					</div>
			  	</div> 		 
		       	<div class="panel panel-default">
				    <div class="panel-heading">
						<h4 class="panel-title">
					    	<a data-toggle="collapse" data-parent="#accordion${informePrevi.idInf}" href="#tasques${informePrevi.idInf}">Tasques</a>
					  	</h4>
				    </div>
				    <div id="tasques${informePrevi.idInf}" class="panel-collapse collapse">	
				      	<div class="panel-body">
				      		<c:if test="${canCreateTasca}">
					      		<div class="row margin_bottom10">
						    		<div class="col-md-12 panel">
										<a href="createTasca?idActuacio=${actuacio.referencia}&tipus=${informePrevi.getEstat()}&idInf=${informePrevi.idInf}" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova tasca</a>
									</div>
					    		</div>
					    	</c:if>
				    		<div class="row panel-body">
								<c:set var="tasquesList" value="${informePrevi.tasques}" scope="request"/>
								<c:set var="idInfActual" value="${informePrevi.idInf}" scope="request"/>
								<jsp:include page="../../tasca/include/_tasquesListResum.jsp"></jsp:include>
		                	</div>
						</div>
				    </div>
			  	</div>	
		   		<div class="panel panel-default">
		    		<div class="panel-heading">
						<h4 class="panel-title">
					    	<a data-toggle="collapse" data-parent="#accordion${informePrevi.idInf}" href="#registre${informePrevi.idInf}">Registres</a>
					  	</h4>
				    </div>
				    <div id="registre${informePrevi.idInf}" class="panel-collapse collapse">
				    	<div class="tabbable">
			               	<ul class="nav nav-tabs">
			               		<li class="active"><a data-toggle="tab" href="#entradesRegistre${informePrevi.idInf}">Registre entrades</a></li>
			               		<li><a data-toggle="tab" href="#sortidesRegistre${informePrevi.idInf}">Registre sortides</a></li>
			 				</ul>
						  	<div class="tab-content">								  		
						  		<div id="entradesRegistre${informePrevi.idInf}" class="tab-pane fade in active">
						  		 	<div class="col-md-12 bordertab">
						  		 		<c:if test="${canCreateRegistre}">
								      		<div class="row margin_bottom10">
									    		<div class="col-md-12">
													<a href="novaEntrada?idIncidencia=${incidencia.idIncidencia}&idInf=${informePrevi.idInf}" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova entrada</a>
												</div>
								    		</div>
								    	</c:if>
							    		<div class="row panel-body">					    		
											<c:set var="registres" value="${informePrevi.entrades}" scope="request"/>
											<c:set var="tipus" value="entrada" scope="request"/>
			                  				<jsp:include page="../../registre/include/_registresList.jsp"></jsp:include>
										</div>
								    </div>
						  	 	</div>
						  		<div id="sortidesRegistre${informePrevi.idInf}" class="tab-pane fade">
						  		 	<div class="col-md-12 bordertab">
						  		 		<c:if test="${canCreateRegistre}">
								      		<div class="row margin_bottom10">
									    		<div class="col-md-12">
													<a href="novaSortida?idIncidencia=${incidencia.idIncidencia}&idInf=${informePrevi.idInf}" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova sortida</a>
												</div>
								    		</div>
								    	</c:if>
							    		<div class="row panel-body">	
											<c:set var="registres" value="${informePrevi.sortides}" scope="request"/>
											<c:set var="tipus" value="sortida" scope="request"/>
			                  				<jsp:include page="../../registre/include/_registresList.jsp"></jsp:include>
										</div>
								    </div>
						  		</div>
			  				</div>
						</div>
			   		</div>
	  			</div>
	  		</div>	
  		</div>
	</div>
</div>