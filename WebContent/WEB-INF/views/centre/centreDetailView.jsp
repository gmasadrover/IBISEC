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
                            Centre <small>Detalls</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Centre
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
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
                <c:if test="${not empty centre}">                	                		
               		<h2 class="margin_bottom30">Informació bàsica</h2>
		    		<div class="row">			    				    				    		
	                    <div class="col-xs-offset-1 col-md-5">
	    					<p>
								<label>Codi:</label> ${centre.idCentre}
							</p>
                            <input type="hidden" name="codi" value="${centre.idCentre}">
	                        <p>
	                        	<label>Direcció:</label> ${centre.adreca}
	                        </p>    
	                        <p> 
	                        	<label>Municipi: </label> ${centre.municipi}
                            </p>
	                        <p> 
	                        	<label>Illa: </label> ${centre.illa}
                            </p>                          	
	                         	                            
	                  	</div>
		             	<div class="col-xs-offset-1 col-md-5">
		             		<p> 
	                        	<label>Nom: </label> ${centre.nom}
                            </p> 
		                    <p> 
	                        	<label>CP: </label> ${centre.getCp()}
                            </p> 	
	                        <p> 
	                        	<label>Localitat: </label> ${centre.localitat}
                            </p>                                            	
	                    </div>		            	
                	</div>
                	<div class="row margin_bottom10">  
	                	<c:if test="${canCreateTasca && isIBISEC}">
				      		<div class="col-md-6 panel">
								<a href="createTasca?centre=${centre.idCentre}&tipus=centre" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Nova incidència</a>
							</div>
				    	</c:if>
				    	<c:if test="${canEditCentre}">
						    <div class="col-md-6 panel">
								<a href="editCentre?codi=${centre.idCentre}" class="btn btn-success loadingButton"  data-msg="obrint centre..." role="button">Editar Centre</a>
							</div>
				    	</c:if>
			    	</div>
                	<c:if test="${canViewIncidencies}">              	
	                	<h2 class="margin_bottom30">Incidencies</h2>
	                	<div class="row">
		                	<div class="table-responsive col-xs-offset-1 col-md-10">							                        
			                    <table class="table table-striped table-bordered filerTable" id="incidenciesTable">
			                        <thead>
			                            <tr>
			                                <th>Referència</th>
	                                        <th>Descripció</th>
	                                        <th>Centre</th>
	                                        <th>Data petició</th>
	                                        <th>Data petició</th>
	                                        <th>Actuacions derivades</th>	
	                                        <th>Data tancament</th>
											<th>Data tancament</th>				                                        							                                       
			                            </tr>
			                        </thead>
			                        <tbody>
									<c:forEach items="${centre.llistaIncidencies}" var="incidencia" >
							          	<tr class=${incidencia.activa ? "success" : "danger"}>							          	
							           		<td><a href="incidenciaDetalls?ref=${incidencia.idIncidencia}" class="loadingButton"  data-msg="obrint incidència...">${incidencia.idIncidencia}</a></td>
							            	<td>${incidencia.descripcio}</td>
							            	<td>${incidencia.nomCentre}</td>
							            	<td>${incidencia.getPeticioString()}</td>
							            	<td>${incidencia.usuMod}</td>
							            	<td>${incidencia.getLlistaIdActuacions()}</td>
										   	<td>${incidencia.getTancamentString()}</td>
										   	<td>${incidencia.dataTancament}</td>				            	
							          	</tr>
						       		</c:forEach>						                                	                              	
			                        </tbody>
			                    </table>
			                </div>
				    	</div>
				    </c:if>
	                <h2 class="margin_bottom30">Actuacions</h2>
                	<div class="row">
	                	<div class="table-responsive col-xs-offset-1 col-md-10">							                        
		                    <table class="table table-striped table-bordered filerTable" id="actuacionsTable">
		                        <thead>
		                            <tr>
		                                <th>Referència</th>
                                        <th>Centre</th>
                                        <th>Descripció</th>
                                        <th>Data petició</th>
                                        <th>Data petició</th>
										<th>Estat</th>
									   	<th>Data tancament</th>
									   	<th>Data tancament</th>															                                        							                                       
		                            </tr>
		                        </thead>
		                        <tbody>
								<c:forEach items="${centre.llistaActuacions}" var="actuacio" >
						          	<tr class=${actuacio.activa ? actuacio.aprovada ? "success" : "warning" : "danger"}>							          	
						           		<td><a href="actuacionsDetalls?ref=${actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${actuacio.referencia}</a></td>
						            	<td>${actuacio.centre.getNomComplet()}</td>
						            	<td>${actuacio.descripcio}</td>
						            	<td>${actuacio.getDataCreacioString()}</td>
						            	<td>${actuacio.dataCreacio}</td>						            	
									   	<td>${actuacio.getEstatNom()}</td>
									   	<td>${actuacio.getDataTancamentString()}</td>
									   	<td>${actuacio.dataTancament}</td>																            	
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
    <script src="js/centres/detalls.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>