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
                        <h1 class="page-header">Usuari <small>Perfil</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><i class="fa fa-dashboard"></i> Usuari</li>
                            <li class="active"> <i class="fa fa-table"></i> Perfil</li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                	<div class="col-lg-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Perfil</h2>   
                        <div class="panel-body">                        	
							<form class="form-horizontal" method="POST" action="DoCanviarDades">
								<input type="hidden" name="idUsuari" value="${usuari.idUsuari}">								
								<div class="form-group">
							     	<div class="col-lg-4">
							     	 	<label class="col-xs-6">Usuari:</label>
							     	 	<input value="${usuari.usuari}" disabled>
							        </div>	
							   	</div>
							   	<div class="form-group">
							       	<div class="visibleObres">					                             	
							        	<div class="col-lg-4">
								      	 	<label class="col-xs-6">Nom:</label>
								      	 	<input name="nom" value="${usuari.name}" ${potModificar ? "" : "disabled"}>
								        </div>	
							            <div class="col-lg-4">
								      	 	<label class="col-xs-6">Cognoms:</label>
								      	 	<input name="cognoms" value="${usuari.llinatges}" ${potModificar ? "" : "disabled"}>							                
							           	</div>							           	
									</div>				                       																
								</div>
								<div class="form-group">
							     	<div class="col-lg-4">
							     	 	<label class="col-xs-6">Càrreg:</label>
							     	 	<input name="carreg" value="${usuari.carreg}" ${potModificar ? "" : "disabled"}>
							        </div>	
							   	</div>
							   	<c:if test="${potModificar}">
									<div class="form-group">
										<div class="col-lg-6">
								    		<div class="row">
								        		<div class="col-lg-12">
								              		<input class="btn btn-primary" type="submit" name="guardar" value="Modificar dades">
												</div>
								     		</div>
								 		</div>							 		
									</div>
								</c:if>		                       	
							</form>
						</div>
						<c:if test="${potModificar}">  
	                        <div class="panel-body">
								<form class="form-horizontal" method="POST" action="DoCanviarPassword">
									<input type="hidden" name="idUsuari" value="${usuari.idUsuari}">								
									<div class="form-group">
								     	<div class="col-lg-3">
								     	 	<label>Introduir password actual:</label>
								     	 	<input name="passActual" type="password">
								        </div>	
								   </div>
								   <div class="form-group">
								       	<div class="visibleObres">					                             	
								        	<div class="col-lg-3">
									      	 	<label>Introduir nou password:</label>
									      	 	<input type="password" name="nouPassword">
									        </div>	
								            <div class="col-lg-3">
									      	 	<label>Repetir nou password:</label>
									      	 	<input type="password" name="repetirNouPassword">							                
								           	</div>							           	
										</div>				                       																
									</div>	
									<div class="form-group">
										<div class="col-lg-6">
								    		<div class="row">
								        		<div class="col-lg-12">
								              		<input class="btn btn-primary" type="submit" name="guardar" value="Canviar password">
												</div>
								     		</div>
								 		</div>							 		
									</div>	                       	
								</form>
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
    <!-- /#wrapper -->
</body>
</html>