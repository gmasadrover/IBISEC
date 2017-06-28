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
                            Registre <small>Sortides</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Registre
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Sortides
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<div class="row">
					<form class="form-horizontal" method="POST" action="registreSortida">						
						<div class="form-group">
							<input type="hidden" id="idCentreSelected" value="${idCentre}" />
							<div class="col-md-offset-1  col-md-3">
							    <div class="col-md-12">
							      <label>Filtrar per centre</label>
							      <div>
		                                <select class="form-control selectpicker" name="idCentre" data-live-search="true" id="centresList">
		                                </select>
		                             </div>
							    </div>						    
						  	</div>		
						  	<div class="col-md-4">
						  		<div class="col-md-12">
							  		<label>Filtrar per data petició</label>
								  	<div class="input-group input-daterange datepicker">
									    <input type="text" class="form-control" name="dataInici" value="${dataInici}">
									    <div class="input-group-addon">fins</div>
									    <input type="text" class="form-control" name="dataFi" value="${dataFi}">
									</div>
									<input type="checkbox" name="filterWithOutDate" ${filterWithOutDate ? "checked" : ""}> Filtrar fora dates
								</div>                                
						  	</div>
						  	<div class="col-md-4">
							  	<div class="col-md-2">
							    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
								</div>
							</div>
						</div>
						<div class="form-group">	
							<c:if test="${canCreateRegistre}">
								<div class="col-md-offset-1 col-md-2">
									<a href="novaSortida" class="btn btn-primary" role="button">Nova sortida</a>
								</div>
							</c:if>
						</div>							
					</form>
				</div>
                <div class="row">
                    <div class="col-md-12">
                        <h2>Sortides</h2>
                        <div class="table-responsive">
                        
                            <table class="table table-striped table-bordered filerTable ${canViewIncidencies ? 'withIncidencies' : 'withOutIncidencies'}">
                                <thead>
                                    <tr>
                                        <th>Referència</th>
                                        <th>Data registre</th>
                                        <th>Destinatari</th>
                                        <th>Contingut</th>
                                        <th>Centre</th>
                                        <c:if test="${canViewIncidencies}">
                                        	<th>Incidència relacionada</th>
                                        </c:if>
                                        <th>Actuació relacionada</th>
                                        <th>Data</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${sortides}" var="sortida" >
							          	<tr>
							          		<td><a href="registre?tipus=S&referencia=${sortida.id}">${sortida.id}</a></td>
							            	<td>${sortida.getDataString()}</td>
							            	<td>${sortida.remDes}</td>
							            	<c:choose>
											    <c:when test="${sortida.tipus == 'Resposta Sol·licitud Personal'}">												        
											    	<td>Resposta Sol·licitud personal</td>	
											   </c:when>   
											    <c:when test="${sortida.tipus == 'Tramesa documentació Personal'}">												        
											    	<td>Tramesa documentació Personal</td>	
											   </c:when>    
											    <c:otherwise>
											    	<td>${sortida.contingut}</td>								        
							            	    </c:otherwise>
											</c:choose>				            	
							                <td>${sortida.getNomCentresString()}</td>
							            	<c:if test="${canViewIncidencies}">
								            	<td>
								            	<c:forEach items="${sortida.getIdIncidenciesList()}" var="idIncidencia" >
									             	<c:choose>
													    <c:when test="${idIncidencia == '-1'}">												        
													    </c:when>    
													    <c:otherwise>
													    	<a href="incidenciaDetalls?ref=${idIncidencia}">${idIncidencia}</a></br>										        
									            	    </c:otherwise>
													</c:choose>	
												</c:forEach> 
												</td>	
											</c:if>
											<td>
							            	<c:forEach items="${sortida.getIdActuacionsList()}" var="idActuacio" >
								             	<c:choose>
												    <c:when test="${idActuacio == '-1'}">												        
												    </c:when>    
												    <c:otherwise>
												    	<a href="actuacionsDetalls?ref=${idActuacio}">${idActuacio}</a></br>										        
								            	    </c:otherwise>
												</c:choose>	
											</c:forEach> 
											</td>	
											<td>${sortida.data}</td>					           								            	
							          	</tr>
							       	</c:forEach>                                	
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                    </div>
                </div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/registre/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>