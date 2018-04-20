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
                            Control <small>Detalls</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Control
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
            </div>
            <!-- /.container-fluid -->
			<div class="row">
				<!-- -- Modificar registres mala asignació <br />
				-- Modificar Factures mala asignació <br />
				<div class="col-md-6">
					<div class="borderbox">
						<span>Projecte Binissalem</span></br>
						<span><a href="expedient?ref=004/17" target="_blanck">Expedient 004/17</a></span>
					</div>
				</div>
				<div class="col-md-6">
					<div class="borderbox">
						<span>Projecte Ses Casesnoves</span></br>
						<span><a href="expedient?ref=032/17" target="_blanck">Expedient 032/17</a></span>
					</div>
				</div>
				<div class="col-md-6"> 
					<div class="borderbox">
						<span>Projecte IES JOSEP MARIA QUADRADO</span></br>
						<span><a href="expedient?ref=005/17" target="_blanck">Expedient 005/17</a></span>
					</div>
				</div> -->
				
	           <%--  <div class="col-md-12">
	                <c:forEach items="${controlCentres}" var="control" >
	                	<h4>${control.centre.getNomComplet()}</h4>
	                	<div class="table-responsive">                        
	                    <table class="table table-striped table-bordered filerTable">
	                        <thead>
	                            <tr>
	                                <th>Tasca</th>
                                    <th>id Actuació</th>
                                    <th>Centre</th>                                        
                                    <th>Data creació</th>
                                    <th>Responsable</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                        	<c:forEach items="${control.getTasquesList()}" var="tasca" >
						         	<tr>							          	
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
						            	<td>${tasca.usuari.getNomComplet()}</td>							           	
						         	</tr>
						      	</c:forEach>                                	
	                        </tbody>
	                    </table>
	                </div>
	                </c:forEach>
	            </div> --%>
	             <div class="row">					
						<div class="col-md-12">
		                        <h2>Informes usuari</h2>
		                        <div class="table-responsive">                        
		                            <table class="table table-striped table-bordered filerTable informes">
		                                <thead>
		                                    <tr>                                        
		                                        <th>Expedient</th>
		                                        <th>Objecte</th>
		                                        <th>Responsable</th>
		                                        <th>Actuació</th>
		                                        <th>Centre</th> 
		                                        <th>Empresa</th>  
		                                        <th>Estat</th>  
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${controlExpedients}" var="informe" >	  
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
														<td>${informe.usuari.getNomComplet()}</td>
										            	<td>${informe.actuacio.descripcio}</td>										            	
										            	<td>${informe.actuacio.centre.getNomComplet()}</td>
										            	<td>${informe.ofertaSeleccionada.nomEmpresa}</td>
										            	<td>${informe.getEstatExpedientFormat()}</td>
										          	</tr>
										    	</c:if>
									       	</c:forEach>
		                                </tbody>
		                            </table>
		                        </div>
		                    </div>
	        </div>	        
        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
     <script src="js/control/control.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>