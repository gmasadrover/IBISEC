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
                            Tasca 
                            <small>
								<c:choose>
								    <c:when test="${tasca.tipus=='infPrev'}">
								        Sol·licitud proposta d'actuació
								    </c:when>
								    <c:otherwise>
								       Detalls tasca
								    </c:otherwise>
								</c:choose>
							</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Tasca
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <h2>Tasca</h2>
                <div class="row">					
	                <div class="col-lg-12">	   
						<div class="panel panel-${tasca.activa ? "success" : "danger"}">
						   	<div class="panel-heading">
						    	<div class="row">
						       		<div class="col-lg-4">
						       			Referència Tasca: ${tasca.idTasca}
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
				<div class="row">
					<div class="col-lg-12 panel-group" id="accordion">	
						<c:if test="${not empty incidencia}">
							<div class="panel panel-default">
            		  			<div class="panel-heading">
					      			<h4 class="panel-title">                        										      		
			        					<a data-toggle="collapse" data-parent="#accordion" href="#incidencia">Incidència</a>
			        				</h4>
			        			</div>
			        		</div>
						    <div id="incidencia" class="panel-collapse collapse">
		                    	<div class="row">					
					                <div class="col-lg-12">	                	
					                	<jsp:include page="../incidencia/include/_resumIncidencia.jsp"></jsp:include>
					                </div>
				            	</div> 
							</div>
						</c:if>	
						<c:if test="${not empty actuacio}">
							<div class="panel panel-default">
            		  			<div class="panel-heading">
					      			<h4 class="panel-title">     	                        										      		
			        					<a data-toggle="collapse" data-parent="#accordion" href="#actuacio">Actuació</a>
			        				</h4>
			        			</div>
			        		</div>
						    <div id="actuacio" class="panel-collapse collapse">
		                    	<div class="row">					
					                <div class="col-lg-12">	                	
					                	<jsp:include page="../actuacio/include/_resumActuacio.jsp"></jsp:include>
					                </div>
				            	</div> 
							</div>
						</c:if>	
					</div>
				</div>
                <div class="row">
                    <div class="col-lg-12"> 
                        <c:forEach items="${historial}" var="historic" >
                        	<div class="panel panel-info"> 
	                         	<div class="panel-heading">
	                         		<div class="col-lg-10">${historic.usuari.getNomComplet()}</div>
	                         		<div class="">${historic.getDataString()}</div>
	                         	</div>
	                         	<div class="panel-body">
	                         		<div class="row panel-body">${historic.comentari}</div>		
					            	<div class="row panel-body">
						            	<c:forEach items="${historic.adjunts}" var="arxiu" >
						            		<a  href="downloadFichero?ruta=${arxiu.ruta}">${arxiu.nom}</a><br>
										</c:forEach>					            		
					            	</div>			          		
					          	</div>					          	
				    		</div>
	       				</c:forEach>                        
                        <c:if test="${tasca.activa}">		  
                        	<div class="panel panel-info">
                        		<c:if test="${tasca.tipus != 'generic' && canRealitzarTasca}">
	                        		<div class="panel-body">
		                        		<div class="col-lg-12 panel-group" id="accordion">		                        										      		
							        		<a data-toggle="collapse" data-parent="#accordion" href="#entradesRegistre" class="btn btn-success">
												<c:choose>
												    <c:when test="${tasca.tipus=='infPrev'}">
												    	<c:if test="${esCap}">
												    		Proposta d'actuació
												    	</c:if>
												    	<c:if test="${!esCap}">
												    		Introduir proposta d'actuació
												    	</c:if>
												    </c:when>
												    <c:when test="${tasca.tipus=='resPartida'}">Reservar partida</c:when>
												    <c:when test="${tasca.tipus=='liciMenor'}">Introduir proposta tècnica</c:when>
												</c:choose>
											</a>
										    <div id="entradesRegistre" class="panel-collapse collapse">
				                        		<c:choose>
												    <c:when test="${tasca.tipus=='infPrev'}">
												    	<jsp:include page="include/_crearInformePrevi.jsp"></jsp:include>
												    </c:when>
												    <c:when test="${tasca.tipus=='resPartida'}">
												    	<jsp:include page="include/_reservaPartida.jsp"></jsp:include>
												    </c:when>
												    <c:when test="${tasca.tipus=='liciMenor'}">
												    	<jsp:include page="include/_introduccioPresuposts.jsp"></jsp:include>
												    </c:when>
												</c:choose>
											</div>	
										</div>
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
				                    					<textarea class="form-control" name="comentari" placeholder="comentari" rows="3" required></textarea> 
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
										        <div class="row">
										            <div class="col-lg-12">
										            	<div class="col-lg-6">
							                                <select class="form-control selectpicker" data-live-search="true" data-size="10" name="idUsuari" id="usuarisList">
							                                <c:forEach items="${llistaUsuaris}" var="usuari" >
						                                		<option value='${usuari.idUsuari}'>${usuari.getNomComplet()}</option>
						                                	</c:forEach>
						                                	<option data-divider="true"></option>
						                                	<c:forEach items="${llistaCaps}" var="usuari" >
						                                		<option value='${usuari.idUsuari}'>${usuari.getNomComplet()}</option>
						                                	</c:forEach>	
							                                </select>
						                             	</div>
						                       			<input class="btn btn-success" type="submit" name="reasignar" value="Reassignar">
													</div>
										        </div>
										        <div class="row margin_top10">
										        	<div class="col-lg-6"></div>
										            <div><input class="btn btn-primary" type="submit" name="afegirComentari" value="Només afegir comentari"></div>
										        </div>
										        <div class="row margin_top10">
										        	<div class="col-lg-6"></div>
										            <div><input class="btn btn-danger" type="submit" name="tancar" value="Tancar"></div>
										        </div>
										    </div>
				                       	</div>		                       	
			                       	</form>			                		                       	
			                   	</div>
			                </div>
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