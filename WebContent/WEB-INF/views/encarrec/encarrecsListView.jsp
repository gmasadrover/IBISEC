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
                            Encàrrecs <small>Llistat</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Encàrrecs
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
					<form class="form-horizontal" method="POST" action=encarrecsList>	
						<input type="hidden" id="usuarisSeleccionats" value="${usuarisSeleccionats}"/>					
						<div class="form-group">
							<div class="col-md-offset-1  col-md-3">
								<label>Usuari / Àrea</label>
							    <select class="form-control selectpicker" name="idUsuari" data-live-search="true"  data-size="10" id="usuarisList">
                                	<c:forEach items="${llistaUsuaris}" var="usuari" >
                                		<option value='${usuari.idUsuari}'>${usuari.getNomCompletReal()}</option>
                                	</c:forEach>                                	                           
                                </select>				    
						  	</div>	
						  	<div class="col-md-2">
						    	<input type="submit" class="btn btn-primary loadingButton"  data-msg="Aplicant filtres..." name="filtrar" value="Aplicar Filtres">
							</div>				  	
						 </div>
					</form>
				</div>
				<br>
                <c:if test="${obresAssignades.size() > 0}">
	                <div class="row">					
						<div class="col-md-12">
		                        <h2>Encàrrecs</h2>
		                        <div class="table-responsive">                        
		                            <table class="table table-striped table-bordered filerTable informes">
		                                <thead>
		                                    <tr>                                        
		                                        <th>Codi</th>
		                                        <th>Centre</th> 
		                                        <th>Actuació</th>
		                                        <th>Usuari</th>
		                                        <th>Tècnic</th>
		                                        <th>Funció</th>
		                                        <th>Expedient</th>		                                       
		                                        <th>Total</th>
		                                        <th>Facturat</th> 
		                                        <th>Estat</th>  
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${obresAssignades}" var="informe" >	  		                                		                          		
										          	<tr class=${informe.propostaInformeSeleccionada != null && informe.propostaInformeSeleccionada.tipusObra == 'obr' ? "success" : ""}>							          	
										           		<td>
															<c:choose>
										            			<c:when test="${informe.expcontratacio != null && informe.expcontratacio.expContratacio != '-1'}">
										            				<a href="actuacionsDetalls?ref=${informe.actuacio.referencia}&exp=${informe.idInf}" class="loadingButton"  data-msg="obrint expedient...">${informe.expcontratacio.expContratacio}</a>
										            			</c:when>
										            			<c:otherwise>
										            				<c:choose>
												            			<c:when test="${informe.actuacio.referencia != '-1'}">
												            				<a href="actuacionsDetalls?ref=${informe.actuacio.referencia}&exp=${informe.idInf}" class="loadingButton"  data-msg="obrint actuació...">${informe.idInf}</a>
												            			</c:when>
												            			<c:otherwise>
												            				${informe.idInf}
												            			</c:otherwise>
												            		</c:choose>
										            			</c:otherwise>
										            		</c:choose>
														</td>		
														<td>${informe.actuacio.centre.getNomComplet()}</td>										
														<td>${informe.actuacio.descripcio}</td>		
														<td>${informe.personal.get(0).getUsuari().getNomComplet()}</td>
														<td>${informe.personal.get(0).tecnic}</td>		
														<td>${informe.personal.get(0).funcio}</td>										            	
										            	<td>
															<c:choose>
										            			<c:when test="${informe.propostaInformeSeleccionada != null}">
										            				${informe.propostaInformeSeleccionada.objecte}
										            			</c:when>
										            			<c:otherwise>
										            				
										            			</c:otherwise>
										            		</c:choose>
														</td>	
										            	<td>${informe.getOfertaSeleccionada().getPlicFormat()}</td>
										            	<td>${informe.getTotalFacturatFormat()}</td>
										            	<td>${informe.getEstatExpedientFormat()}</td>
										          	</tr>
									       	</c:forEach>
		                                </tbody>
		                            </table>
		                        </div>
		                    </div>
					</div>
				</c:if>
				<br/>               
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/encarrecs/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>