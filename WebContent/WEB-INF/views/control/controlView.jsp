<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

			<div class="row">
				<div class="col-md-12">
                	<h2>Configuració INTRANET</h2>
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="updateConfiguracio">
                		<div class="form-group">
							<div class="col-md-6">
								<label>Import obra major</label>
								<input name="importObraMajor" placeholder="" value="${configuracioActual.getImportObraMajor()}" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>Import servei major</label>
								<input name="importServeiMajor" placeholder="" value="${configuracioActual.getImportServeiMajor()}" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>Import subministrament major</label>
								<input name="importSubministramentMajor" placeholder="" value="${configuracioActual.getImportSubministramentMajor()}" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>Ruta base documents</label>
								<input name="rutaBaseDocumentacio" placeholder="" value="${configuracioActual.rutaBaseDocumentacio}" required>
							</div>
						</div>
						<div class="col-md-4">												        		
				    		<div class="row">
				        		<div class="col-md-12">															        																						 				
							 		<input class="btn btn-success margin_top30 upload" type="submit" name="modificar" value="Guardar canvis">
							 	</div>
				     		</div>
		     			</div>	
                	</form>
                </div>
			</div>
		
	       	<div class="row">				
				<div class="col-md-12">
                	<h2>Historial usuari</h2>
                 	<div class="table-responsive">                        
                         <table class="table table-striped table-bordered filerTable informes">
                             <thead>
                                 <tr> 
                                 	<th>Data</th> 
                                 	<th>Expedient/Registre</th>
                                 	<th>Actuacio</th>                              	
                                     <th>Comentari</th>
                                     <th>Descripcio tasca</th> 
                                     <th>Centre</th>  
                                     <th>Responsable</th>
                                 </tr>
                             </thead>
                             <tbody>
                             	<c:forEach items="${controlHistoric}" var="historic" >
				          	<tr>
				          		<td>${historic.getDataString()}</td>
				          		<c:choose>
					          		<c:when test="${historic.tasca.registre != null}">
					          			<td>${historic.tasca.registre}</td>	
					          		</c:when>
					          		<c:otherwise>
					          			<td>${historic.actuacio.refExt}</td>	
					          		</c:otherwise>
				          		</c:choose>
				            	<td>${historic.actuacio.descripcio}</td>		
				            	<td>${historic.comentari}</td>	
				            	<td>${historic.tasca.descripcio}</td>									            										            	
				            	<td>${historic.actuacio.centre.getNomComplet()}</td>	
				            	<td>${historic.usuari.getNomComplet()}</td>					            	
				          	</tr>
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