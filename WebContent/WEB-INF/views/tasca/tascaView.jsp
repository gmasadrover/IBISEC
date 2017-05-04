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
                    <div class="col-lg-12">
                        <h1 class="page-header">
                        	<c:choose>
							    <c:when test="${tasca.tipus=='notificacio'}">
							    	Notificació
							    </c:when>
							    <c:otherwise>
							       	Tasca
							    </c:otherwise>
							</c:choose>
                            <small>
								<c:choose>
								    <c:when test="${tasca.tipus=='infPrev'}">
								        Sol·licitud proposta d'actuació
								    </c:when>
								    <c:when test="${tasca.tipus=='notificacio'}">
								    	Notificació
								    </c:when>
								    <c:otherwise>
								       	Detalls tasca
								    </c:otherwise>
								</c:choose>
							</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i>
                                <c:choose>
								    <c:when test="${tasca.tipus=='notificacio'}">
								    	Notificació
								    </c:when>
								    <c:otherwise>
								       	Tasca
								    </c:otherwise>
								</c:choose> 
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <c:choose>
				    <c:when test="${tasca.tipus=='notificacio'}">
				    	<h2>Notificació</h2>
				    </c:when>
				    <c:otherwise>
				       	<h2>Tasca</h2>
				    </c:otherwise>
				</c:choose>                 
                <div class="row">					
	                <div class="col-lg-12">	   
						<div class="panel panel-${tasca.activa ? "success" : "danger"}">
						   	<div class="panel-heading">
						    	<div class="row">
						       		<div class="col-lg-4">
						       			<c:choose>
										    <c:when test="${tasca.tipus=='notificacio'}">
										    	Referència notificació: ${tasca.idTasca}
										    </c:when>
										    <c:otherwise>
										       	Referència Tasca: ${tasca.idTasca}
										    </c:otherwise>
										</c:choose> 						       			
						       		</div>
						       		<div class="col-lg-4">
						       			Assumpte: ${tasca.descripcio}
						      		</div>
						       		<div class="col-lg-4">
						       			Responsable: ${tasca.usuari.getNomComplet()}
						      		</div>
						       	</div>
						   	</div>
						</div>
					</div>
				</div>
				<c:choose>
					<c:when test="${actuacio != null}">
						<div class="row">					
			                <div class="col-lg-12">	                	
			                	<jsp:include page="../actuacio/include/_resumActuacio.jsp"></jsp:include>
			                </div>				            	
						</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${incidencia != null}">							
								<div class="row">					
					                <div class="col-lg-12">	                	
					                	<jsp:include page="../incidencia/include/_resumIncidencia.jsp"></jsp:include>
					                </div>				            	
								</div>
							</c:when>
							<c:otherwise>
								
							</c:otherwise>
						</c:choose>						
					</c:otherwise>
				</c:choose>
                <div class="row">
                    <div class="col-lg-12"> 
                        <c:forEach items="${historial}" var="historic" >
                        	<c:if test="${!historic.comentari.trim().isEmpty()}">
	                        	<div class="panel panel-info"> 
		                         	<div class="panel-heading">
		                         		<div class="col-lg-10">${historic.usuari.getNomComplet()}</div>
		                         		<div class="">${historic.getDataString()}</div>
		                         	</div>
		                         	<div class="panel-body">
		                         		<div class="row panel-body">${historic.comentari}</div>		
						            	<div class="row panel-body">
							            	<c:forEach items="${historic.adjunts}" var="arxiu" >
							            		<a target="_blanck" href="downloadFichero?ruta=${arxiu.ruta}">${arxiu.nom}</a><br>
											</c:forEach>					            		
						            	</div>			          		
						          	</div>					          	
					    		</div>
					    	</c:if>
	       				</c:forEach>                        
                        <c:if test="${tasca.activa && tasca.tipus != 'notificacio'}">		  
                        	<div class="panel panel-info">
                        		<c:if test="${tasca.tipus != 'generic' && canRealitzarTasca}">
	                        		<div class="panel-body">
			                        		
											      <h4 class="panel-title">
												      	<c:choose>
														    <c:when test="${tasca.tipus=='infPrev'}">Informe</c:when>
														    <c:when test="${tasca.tipus=='resPartida'}">Reservar partida</c:when>
														    <c:when test="${tasca.tipus=='liciMenor'}">Proposta tècnica</c:when>
														</c:choose>
											      </h4>
											    </div>										    
											    <div>
					                        		<c:choose>
													    <c:when test="${tasca.tipus=='infPrev'}">
													    	<jsp:include page="include/_crearInformePrevi.jsp"></jsp:include>
													    </c:when>
													    <c:when test="${tasca.tipus=='resPartida'}">
													    	<jsp:include page="include/_reservaPartida.jsp"></jsp:include>
													    </c:when>
													    <c:when test="${tasca.tipus=='liciMenor'}">
													    	<c:if test="${esCap}">
													    		<jsp:include page="include/_resumOfertes.jsp"></jsp:include>
													    	</c:if>
													    	<c:if test="${!esCap}">
													    		<jsp:include page="include/_introduccioPresuposts.jsp"></jsp:include>
													    	</c:if>
													    </c:when>
													</c:choose>
												
									</div>
								</c:if>				 	
	                    		<div class="panel-footer">	                    		                  	
			                    	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddHistoric">
			                    		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
			                    		<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
			                    		<div class="row">
				                    		<div class="col-lg-6">		
				                    			<div class="row">	 
				                    				<div class="col-lg-12">
				                    					<input class="hidden" name="idTasca" value=${tasca.idTasca}>		
				                    					<textarea class="form-control" name="comentari" placeholder="comentari" rows="3"></textarea> 
				                      				</div>
				                      			</div>
				                      			<div class="row margin_top10">
										        	<div class="col-lg-12">		
							                            <div class="col-xs-10">   
							                                <input type="file" class="btn" name="file" /><br/>
														</div> 		
										        	</div>
										        </div>
				                      		</div>
				                       		<div class="col-lg-6">
				                       			<c:if test="${canRealitzarTasca}">
											        <div class="row">
											            <div class="col-lg-12">
											            	<div class="col-lg-6">
								                                <select class="form-control selectpicker" data-live-search="true" data-size="10" name="idUsuari" id="usuarisList">
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
							                       			<input class="btn btn-success" type="submit" name="reasignar" value="Reassignar">
														</div>
											        </div>
										        </c:if>
										        <div class="row margin_top10">
										        	<div class="col-lg-6"></div>
										            <div><input class="btn btn-primary" type="submit" name="afegirComentari" value="Només afegir comentari"></div>
										        </div>
										        <c:if test="${esCap}">
											        <div class="row margin_top10">
											        	<div class="col-lg-6"></div>
											            <div><input class="btn btn-danger" type="submit" name="tancar" value="Tancar"></div>
											        </div>
										        </c:if>
										    </div>
				                       	</div>		                       	
			                       	</form>			                		                       	
			                   	</div>
			                </div>
		                </c:if>	
		                <c:if test="${tasca.activa && tasca.tipus == 'notificacio'}">
		                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddHistoric">
	                    		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
	                    		<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
	                    		<input class="hidden" name="idTasca" value="${tasca.idTasca}">	
	                    		<div class="row">
		                    		<div class="col-lg-8">		
		                    			
		                      		</div>
		                       		<div class="col-lg-4">		                       			
								        <div class="row margin_top10">
								        	<div class="col-lg-6"></div>
								            <div><input class="btn btn-danger" type="submit" name="tancar" value="Borrar notificació"></div>
								        </div>
								    </div>
		                       	</div>		                       	
	                       	</form>
		                </c:if>
                    </div>                    
                </div>
				
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/tasca/vistaTasca.js"></script>
    <!-- /#wrapper -->
</body>
</html>