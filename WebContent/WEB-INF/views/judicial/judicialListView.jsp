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
                            Judicial <small>Procediments</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Procediments
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <c:if test="${canViewPendentsTercers}">
	                <div class="row">
	                    <div class="col-md-12">
	                        <h2>Tramitacions pendent tercers</h2>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered filerTable pendents">
	                                <thead>
	                                    <tr>
	                                        <th>Num Autos</th>
	                                        <th>Estat</th>                                                                          
	                                        <th>Data document</th>
	                                        <th>Termini</th>
	                                        <th>Descripcio</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach items="${tramitacionsPendentsTercersList}" var="procediment" >
								          	<tr>							          	
								           		<td><a href="procediment?ref=${procediment.referencia}" class="loadingButton"  data-msg="obrint procediment...">${procediment.numAutos}</a></td>
								           		<td>${procediment.estat}</td>	
								           		<td>${procediment.getTramitacionsList()[0].getDataDocumentString()}</td>
								           		<td>${procediment.getTramitacionsList()[0].termini}</td>
								           		<td>${procediment.getTramitacionsList()[0].descripcio}</td>
								          	</tr>
								       	</c:forEach>                                	
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
	                </div>
                	<div class="margin_top30"></div>
                </c:if>
                <c:if test="${canViewPendentsProvisio}">
	                <div class="row">
	                    <div class="col-md-12">
	                        <h2>Tramitacions pendent provisió</h2>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered filerTable pendents">
	                                <thead>
	                                    <tr>
	                                        <th>Num Autos</th>
	                                        <th>Estat</th>                                                                          
	                                        <th>Data document</th>
	                                        <th>Termini</th>
	                                        <th>Descripcio</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach items="${tramitacionsPendentsProvisioList}" var="procediment" >
								          	<tr>							          	
								           		<td><a href="procediment?ref=${procediment.referencia}" class="loadingButton"  data-msg="obrint procediment...">${procediment.numAutos}</a></td>
								           		<td>${procediment.estat}</td>	
								           		<td>${procediment.getTramitacionsList()[0].getDataDocumentString()}</td>
								           		<td>${procediment.getTramitacionsList()[0].termini}</td>
								           		<td>${procediment.getTramitacionsList()[0].descripcio}</td>
								          	</tr>
								       	</c:forEach>                                	
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
	                </div>
                	<div class="margin_top30"></div>
                </c:if>
                <div class="row">
					<form class="form-horizontal" method="POST" action="judicials">						
						<div class="form-group">				
							<input type="hidden" id="estatSelected" value="${estatFilter}" />
							<input type="hidden" id="anySelected" value="${anyFilter}"/>							
						  	<div class="col-md-3">
							    <div class="col-md-12">
							      <label>Estat</label>
							      <div>
							      	<select class="selectpicker" id="estatList" name="estat">
									  	<option value="-1">Qualsevol</option>
									  	<option value="obert">Obert</option>
									  	<option value="execucio">En execució</option>
									  	<option value="arxiu">Arxivat</option>
									  	<option value="altres">Altes</option>
									</select>							      	
							      </div>
							    </div>
						  	</div>
						  	<div class="col-md-2">
							    <div class="col-md-12">
							      <label>Any</label>
							      <div>
		                                <select class="form-control selectpicker" name="any" id="anyList">
		                                	<option value="-1">Tots</option>
		                                	<c:forEach items="${anysList}" var="any" >
		                                		<option value="${any}">${any}</option>
		                                	</c:forEach>
		                                </select>
		                             </div>
							    </div>						    
						  	</div>							  	
						  	<div class="col-md-3 margin_top30">
						    	<input type="submit" class="btn btn-primary loadingButton"  data-msg="Aplicant filtres..." name="filtrar" value="Aplicar Filtres">
							</div>
						</div>		
					</form>
				</div>	
				<c:if test="${canCreateProcediment}">
	                <div class="row">					
						<div class="form-group">	
						  	<div class="col-md-3">
						    	<a href="nouProcediment" class="loadingButton btn btn-primary"  data-msg="nou procediment...">Crear nou procediment</a>
							</div>
						</div>	
					</div>	
				</c:if>       		
                <!-- /.row -->
                <div class="row">
                    <div class="col-md-12">
                        <h2>Procediments</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable procediments">
                                <thead>
                                    <tr>
                                        <th>Num Autos</th>
                                        <th>Any</th>
                                        <th>Jutjat</th>                                      
                                        <th>Demandant</th>
                                        <th>Demandat</th>
                                        <th>Objecte Demanda</th>
                                        <th>Quantia</th>
                                        <th>Notes</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${procedimentsList}" var="procediment" >
							          	<tr>							          	
							           		<td><a href="procediment?ref=${procediment.referencia}" class="loadingButton"  data-msg="obrint procediment...">${procediment.numAutos}</a></td>
							           		<td>${procediment.anyProcediment}</td>	
							           		<td>${procediment.jutjat}</td>							            	
							            	<td>${procediment.demandant}</td>
							            	<td>${procediment.demandat}</td>
							            	<td>${procediment.objecteDemanda}</td>
							            	<td>${procediment.quantia}</td>
							            	<td>${procediment.notes}</td>
							          	</tr>
							       	</c:forEach>                                	
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/judicial/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>