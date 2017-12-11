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
                            Bastanteos <small>Bastanteos</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Bastanteos
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <div class="row">
					<form class="form-horizontal" method="POST" action="bastanteos">						
						<div class="form-group">
							<input type="hidden" id="anySelected" value="${anyFilter}"/>							
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
						</div>	
						<div class="form-group">	
						  	<div class="col-md-offset-9 col-md-3">
						    	<input type="submit" class="btn btn-primary loadingButton"  data-msg="Aplicant filtres..." name="filtrar" value="Aplicar Filtres">
							</div>
						</div>					
					</form>
				</div>	
				<div class="row">					
					<div class="form-group">	
					  	<div class="col-md-offset-9 col-md-3">
					    	<a href="nouBastanteo" class="loadingButton btn btn-primary"  data-msg="nou bastanteo...">Crear nou bastanteo</a>
						</div>
					</div>	
				</div>	       		
                <!-- /.row -->
                <div class="row">
                    <div class="col-md-12">
                        <h2>Bastanteos</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Ref</th>
                                        <th>Data bastanteo</th>
                                        <th>Data bastanteo</th>
                                        <th>Empresa</th>
                                        <th>Tipo escritura</th>
                                        <th>Persona facultada</th>
                                        <th>Càrrec</th>
                                        <th>Data escritura</th>
                                        <th>Data escritura</th>
                                        <th>Nº Protocol</th>
                                        <th>Notari i col·legi</th>
                                        <th>Procedència</th>
                                        <th>Destí</th>
                                        <th>Any</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${bastanteosList}" var="bastanteo" >
							          	<tr>							          	
							           		<td><a href="bastanteo?ref=${bastanteo.ref}" class="loadingButton"  data-msg="obrint bastanteo...">${bastanteo.ref}</a></td>
							           		<td>${bastanteo.getDatabastanteoString()}</td>
							            	<td>${bastanteo.databastanteo}</td>
							            	<td><a href="empresa?cif=${bastanteo.empresa.cif}" class="loadingButton"  data-msg="obrint empresa...">${bastanteo.empresa.name} (${bastanteo.empresa.cif})</a></td>
							            	<td>${bastanteo.escritura}</td>
							            	<td>${bastanteo.personaFacultada}</td>
							            	<td>${bastanteo.carrec}</td>
							            	<td>${bastanteo.getDataEscrituraString()}</td>
							            	<td>${bastanteo.dataEscritura}</td>
							            	<td>${bastanteo.numProtocol}</td>
							            	<td>${bastanteo.notari}</td>
							            	<td>${bastanteo.procedencia}</td>
							            	<td>${bastanteo.desti}</td>
							            	<td>${bastanteo.anyBastanteo}</td>
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
    <script src="js/bastanteos/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>