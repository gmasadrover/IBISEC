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
				<div class="row">
                	<div class="col-md-12">
                		${infoLog}
                	</div>
                </div>
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            Tasques <small>Llistat</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Tasques
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
 
				<div class="row">
					
				</div> 
 
				<div class="row">
					<form class="form-horizontal" method="POST" action="tascaList">	
						<input type="hidden" id="usuarisSeleccionats" value="${usuarisSeleccionats}"/>					
						<div class="form-group">
							<div class="col-md-offset-1  col-md-3">
								<label>Usuari / Àrea</label>
							    <select class="form-control selectpicker" name="idUsuari" data-live-search="true"  data-size="10" id="usuarisList">
                                	<c:if test="${veureTotes}">
	                                	<option value='totes'>Totes les tasques</option>
	                                	<option data-divider="true"></option>
                                	</c:if>                                	
                                	<c:forEach items="${llistaUsuaris}" var="usuari" >
                                		<option value='${usuari.idUsuari}'>${usuari.getNomCompletReal()}</option>
                                	</c:forEach>
                                	<option data-divider="true"></option>
                                		<option value='gerencia'>Gerència</option>
                                		<option value='juridica'>Assessoria Jurídica</option>
                                		<option value='obres'>Obres , Projectes i Supervisió</option>
                                		<option value='comptabilitat'>Administració i comptabilitat</option>
                                		<option value='instalacions'>Instal·lacions i Manteniment</option>                               
                                </select>				    
						  	</div>	
						  	<div class="col-md-2">
						  		<input type="checkbox" name="filterWithClosed" ${filterWithClosed ? "checked" : ""}> Mostrar tancades
						  	</div>	
						  	<div class="col-md-2">
						    	<input type="submit" class="btn btn-primary loadingButton"  data-msg="Aplicant filtres..." name="filtrar" value="Aplicar Filtres">
							</div>				  	
						 </div>
					</form>
				</div>
				<br>				
                <div class="row">
                	<div class="tabbable">
	                   	<ul class="nav nav-tabs">		
	                   		<c:set var="primera" value="true" scope="request"/>							   
							<c:if test="${solInfPrevList.size() > 0}"> 
								<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#solInfPrev">Sol·licitud informes previs<span class="notif">${solInfPrevList.size()}</span></a></li>
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						    <c:if test="${vistInfPrevList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#vistInfPrev">Vist-i-plau informes previs<span class="notif">${vistInfPrevList.size()}</span></a></li>
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						    <c:if test="${infPrevList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#infPrev">Informes previs<span class="notif">${infPrevList.size()}</span></a></li>
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						    <c:if test="${redacDocTecnicaList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#redacDocTecnica">Redacció doc tècnica<span class="notif">${redacDocTecnicaList.size()}</span></a></li>
						   		<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						    <c:if test="${vistDocTecnicaList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#vistDocTecnica">Vist-i-plau doc tècnica<span class="notif">${vistDocTecnicaList.size()}</span></a></li>
						   		<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						   	</c:if>
						   	<c:if test="${resCreditList.size() > 0}">
						   		<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#resCredit">Reserves de crèdit<span class="notif">${resCreditList.size()}</span></a></li>
						   		<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						   	</c:if>
						   	<c:if test="${docPreLicitacioList.size() > 0}">
						   		<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#docPreLicitacio">Preparar documentació licitació<span class="notif">${docPreLicitacioList.size()}</span></a></li>							   											    
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						   	<c:if test="${sigDocExpList.size() > 0}">
						   		<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#sigDocExp">Signatura documentació EXP<span class="notif">${sigDocExpList.size()}</span></a></li>							   											    
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						    <c:if test="${propAdjList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#propAdj">Propostes d'adjudicació<span class="notif">${propAdjList.size()}</span></a></li>	
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						    <c:if test="${resAdjList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#resAdj">Resolucions adjudicació<span class="notif">${resAdjList.size()}</span></a></li>						    
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						    <c:if test="${redContracteList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#redContracte">Redacció de contracte<span class="notif">${redContracteList.size()}</span></a></li>	
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						    <c:if test="${judicialList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#judicialList">Judicial<span class="notif">${judicialList.size()}</span></a></li>	
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>
						    <c:if test="${conformarFacturaList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#conformarFacturaList">Factures per conformar<span class="notif">${conformarFacturaList.size()}</span></a></li>	
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>		
						    <c:if test="${revisarCertificacioList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#revisarCertificacioList">Noves certificacions<span class="notif">${revisarCertificacioList.size()}</span></a></li>	
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>		
						    <c:if test="${contractesList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#contractesList">Redacció contractes<span class="notif">${contractesList.size()}</span></a></li>	
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>		
						    <c:if test="${contractesFirmaList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#contractesFirmaList">Contractes firmats<span class="notif">${contractesFirmaList.size()}</span></a></li>	
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>			    
						   	<c:if test="${altresList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#altres">Altres<span class="notif">${altresList.size()}</span></a></li>
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>	
						    <c:if test="${notificacionsList.size() > 0}">
						    	<li class="${primera ? 'active' : ''}"><a data-toggle="tab" href="#notificacios">Notificacions<span class="notif">${notificacionsList.size()}</span></a></li>
						    	<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						    </c:if>					   
					 	</ul>					 	
					  	<div class="tab-content">
					  		<c:set var="primera" value="true" scope="request"/>	
					  		<c:if test="${solInfPrevList.size() > 0}"> 	
						  		<div id="solInfPrev" class="tab-pane fade ${primera ? 'in active' : ''}">
									<div class="col-md-12 bordertab">
										<c:set var="tasquesList" value="${solInfPrevList}" scope="request"/>
										<jsp:include page="include/_tasquesList.jsp"></jsp:include>		
										<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 									    
									</div>
								</div>
							</c:if>
							<c:if test="${vistInfPrevList.size() > 0}"> 
								<div id="vistInfPrev" class="tab-pane fade ${primera ? 'in active' : ''}">
									<div class="col-md-12 bordertab">
										<c:set var="tasquesList" value="${vistInfPrevList}" scope="request"/>
										<jsp:include page="include/_tasquesList.jsp"></jsp:include>
										<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
									</div>
								</div>
							</c:if>
							<c:if test="${infPrevList.size() > 0}"> 
								<div id="infPrev" class="tab-pane fade ${primera ? 'in active' : ''}">
						  		 	<div class="col-md-12 bordertab">
						  		 		<c:set var="tasquesList" value="${infPrevList}" scope="request"/>
						  		 		<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  		 		<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  		 	</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${redacDocTecnicaList.size() > 0}"> 
								<div id="redacDocTecnica" class="tab-pane fade ${primera ? 'in active' : ''}">
									<div class="col-md-12 bordertab">
										<c:set var="tasquesList" value="${redacDocTecnicaList}" scope="request"/>
										<jsp:include page="include/_tasquesList.jsp"></jsp:include>
										<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
									</div>
								</div>
							</c:if>
							<c:if test="${vistDocTecnicaList.size() > 0}"> 
								<div id="vistDocTecnica" class="tab-pane fade ${primera ? 'in active' : ''}">
									<div class="col-md-12 bordertab">
										<c:set var="tasquesList" value="${vistDocTecnicaList}" scope="request"/>
										<jsp:include page="include/_tasquesList.jsp"></jsp:include>
										<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
									</div>
								</div>
							</c:if>
							<c:if test="${resCreditList.size() > 0}"> 
								<div id="resCredit" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${resCreditList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${docPreLicitacioList.size() > 0}"> 
								<div id="docPreLicitacio" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${docPreLicitacioList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${sigDocExpList.size() > 0}"> 
								<div id="sigDocExp" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${sigDocExpList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${propAdjList.size() > 0}"> 
						  		<div id="propAdj" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${propAdjList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${resAdjList.size() > 0}"> 
						  		<div id="resAdj" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${resAdjList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${redContracteList.size() > 0}"> 
						  		<div id="redContracte" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${redContracteList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>	
						  	</c:if>
						  	<c:if test="${judicialList.size() > 0}"> 				  		
						  		<div id="judicialList" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${judicialList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${conformarFacturaList.size() > 0}"> 				  		
						  		<div id="conformarFacturaList" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${conformarFacturaList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${revisarCertificacioList.size() > 0}"> 				  		
						  		<div id="revisarCertificacioList" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${revisarCertificacioList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${contractesList.size() > 0}"> 				  		
						  		<div id="contractesList" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${contractesList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${contractesFirmaList.size() > 0}"> 				  		
						  		<div id="contractesFirmaList" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${contractesFirmaList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${altresList.size() > 0}"> 				  		
						  		<div id="altres" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${altresList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
						  	<c:if test="${notificacionsList.size() > 0}"> 				  		
						  		<div id="notificacios" class="tab-pane fade ${primera ? 'in active' : ''}">
						  			<div class="col-md-12 bordertab">
						  				<c:set var="tasquesList" value="${notificacionsList}" scope="request"/>
						  				<jsp:include page="include/_tasquesList.jsp"></jsp:include>
						  				<c:if test="${primera}"><c:set var="primera" value="false" scope="request"/></c:if> 
						  			</div>
						  		</div>
						  	</c:if>
					  	</div>
					</div>                    
                </div>
                <br/>
                <c:if test="${obresAssignades.size() > 0}">
	                <div class="row">					
						<div class="col-md-12">
		                        <h2>Obres assignades</h2>
		                        <div class="table-responsive">                        
		                            <table class="table table-striped table-bordered filerTable informes">
		                                <thead>
		                                    <tr>                                        
		                                        <th>Expedient</th>
		                                        <th>Objecte</th>
		                                        <th>Responsable</th>
		                                        <th>Actuació</th>
		                                        <th>Centre</th>  
		                                        <th>Estat</th>  
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${obresAssignades}" var="informe" >	  
		                                		<c:if test="${informe.getEstatExpedientFormat() != 'Garantia'}">                              		
										          	<tr>							          	
										           		<td>
															<c:choose>
										            			<c:when test="${informe.expcontratacio != null && informe.expcontratacio.expContratacio != '-1'}">
										            				<a href="actuacionsDetalls?ref=${informe.actuacio.referencia}&exp=${informe.idInf}" class="loadingButton"  data-msg="obrint expedient...">${informe.expcontratacio.expContratacio}</a>
										            			</c:when>
										            			<c:otherwise>
										            				<c:choose>
												            			<c:when test="${informe.actuacio.referencia != '-1'}">
												            				<a href="actuacionsDetalls?ref=${informe.actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${informe.actuacio.referencia}</a>
												            			</c:when>
												            			<c:otherwise>
												            				${informe.idInf}
												            			</c:otherwise>
												            		</c:choose>
										            			</c:otherwise>
										            		</c:choose>
														</td>												
														<td>
															<c:choose>
										            			<c:when test="${informe.propostaInformeSeleccionada != null}">
										            				${informe.propostaInformeSeleccionada.objecte}
										            			</c:when>
										            			<c:otherwise>
										            				
										            			</c:otherwise>
										            		</c:choose>
														</td>
														<td>${informe.usuari.getNomComplet()} (${informe.personal.get(0).funcio})</td>
										            	<td>${informe.actuacio.descripcio}</td>										            	
										            	<td>${informe.actuacio.centre.getNomComplet()}</td>
										            	<td>${informe.getEstatExpedientFormat()}</td>
										          	</tr>
										    	</c:if>
									       	</c:forEach>
		                                </tbody>
		                            </table>
		                        </div>
		                    </div>
					</div>
				</c:if>
				<br/>
                <c:if test="${seguimentList.size() > 0}">
	                <div class="row">
	                    <div class="col-md-12">
	                        <h2>Tasques en seguiment</h2>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered filerTable seguiment">
	                                <thead>
	                                    <tr>                                        
	                                        <th>Tasca</th>
	                                        <th>id Actuació</th>
	                                        <th>Centre</th>                                        
	                                        <th>Data creació</th>
	                                        <th>Data creació</th>
	                                        <th>Responsable</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach items="${seguimentList}" var="tasca" >	                                		
								          	<tr class="${tasca.activa ? tasca.tipus == 'notificacio' ? "warning" : "success" : "danger"}">							          	
								           		<td><a href="tasca?id=${tasca.idTasca}">${tasca.idTasca} - ${tasca.descripcio}</a></td>
								            	<td>
								            		<c:choose>
								            			<c:when test="${tasca.actuacio.referencia != '-1'}">
								            				<a href="actuacionsDetalls?ref=${tasca.actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${tasca.actuacio.referencia}</a>
								            			</c:when>
								            			<c:otherwise>
								            				${tasca.idinforme}
								            			</c:otherwise>
								            		</c:choose>
								            	</td>
								            	<td>${tasca.actuacio.centre.getNomComplet()}</td>							            	
								            	<td>${tasca.getDataCreacioString()}</td>
								            	<td>${tasca.dataCreacio}</td>	
								            	<td>${tasca.usuari.getNomComplet()}</td>					            	
								          	</tr>
								       	</c:forEach>
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
	                </div>
				</c:if>
				<c:if test="${seguimentActuacionsList.size() > 0}">
	                <div class="row">
	                    <div class="col-md-12">
	                        <h2>Actuacions en seguiment</h2>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered filerTable actuacioSeguiment">
	                                <thead>
	                                    <tr>
	                                        <th>id Actuació</th>
	                                        <th>Descripció</th>
	                                        <th>Centre</th>                                        
	                                        <th>Estat</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach items="${seguimentActuacionsList}" var="actuacio" >
								          	<tr class=${actuacio.isActiva() ? actuacio.isPaAprovada() ? actuacio.isAprovada() ? "success" : "info" : "warning" : "danger"}>							          	
								           		<td><a href="actuacionsDetalls?ref=${actuacio.referencia}">${actuacio.referencia}</a></td>
								            	<td>${actuacio.descripcio}</td>
								            	<td>${actuacio.centre.getNomComplet()}</td>	
								            	<td>${actuacio.getEstat()}</td>					            	
								          	</tr>
								       	</c:forEach>
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
	                </div>
				</c:if>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/tasca/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>