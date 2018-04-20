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
                            Registre <small>Detalls registre</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Registre
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
               			<p style="color: red;">
						<c:if test="${!registre.actiu}">
							Anul·lat
						</c:if>
						</p>
               		</div>
               	 </div>      
                
				<div class="row">
	                <div class="col-md-12">
	                    <div class="panel panel-${registre.actiu ? 'success' : 'danger' }">
	                        <div class="panel-heading">
	                           	<div class="row">
	                        		<div class="col-md-4">
	                        			 Referència: ${registre.id} 
	                        	    </div>
	                        	    <div class="col-md-4">
	                        			 Data entrada: ${registre.getDataString()} 
	                        	    </div>	  
	                        	    <div class="col-md-4">
	                        			 Centre: </br>
	                        			 <c:forEach items="${registre.getNomCentresList()}" var="nomCentre" >
	                        			 	${nomCentre} </br>
	                        			 </c:forEach>	                        			 
	                        	    </div>	                        		
	                        	</div>
	                        </div>
	                        <div class="panel-body">
	                        	<c:choose>
								    <c:when test="${registre.tipus == 'Sol·licitud Personal' && ! canViewPersonal}">			
								    	<div class="row panel-body">
			                        		<p>Sol·licitud personal</p>
			                        	</div>		
								   	</c:when>    
								   	 <c:when test="${registre.tipus == 'Resposta Sol·licitud Personal' && ! canViewPersonal}">			
								    	<div class="row panel-body">
			                        		<p>Resposta Sol·licitud Personal</p>
			                        	</div>		
								   	</c:when>    
								   	 <c:when test="${registre.tipus == 'Tramesa documentació Personal' && ! canViewPersonal}">			
								    	<div class="row panel-body">
			                        		<p>Tramesa documentació Personal</p>
			                        	</div>		
								   	</c:when>    
								    <c:otherwise>
								    	<div class="row panel-body">
			                        		<p>${registre.contingut}</p>
			                        	</div>	
			                        	<div class="row panel-body">
			                        		<p>Arxius adjunts:</p>
			                        		<c:forEach items="${arxius}" var="arxiu" >
							            		<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
													${arxiu.getDataString()} - ${arxiu.nom}
												</a>
												<a href="#"><span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
												<br>
											</c:forEach>
			                        	</div>								        
				            	    </c:otherwise>
								</c:choose>		                        	
	                        	<c:if test="${canCreateRegistre}">	  
		                        	<div class="row">            			
										<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadFichero">
											<div class="form-group">
												<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					                            <div class="col-xs-5">   
					                                <input type="file" class="btn" name="file" /><br/>
												</div> 
												<input type="hidden" name="idIncidencies" value="${registre.idIncidencies}">
												<input type="hidden" name="tipus" value="RegistreE">
												<input type="hidden" name="idTipus" value="${registre.id}">
												<input type="hidden" name="redirect" value="/registre?tipus=${tipus}&referencia=${registre.id}">				    
												<div class="col-xs-2"> 
					         						<input type="submit" class="btn btn-primary" value="Pujar" />
					         					</div>    						
					         				</div>         				
										</form>							
					            	</div>     
					            </c:if>                     
	                        </div>
	                        <div class="panel-footer">
	                        	<div class="row">
	                        		<div class="col-md-4">
	                        			${tipus == "E" ? "Remitent" : "Destinatari"}: ${registre.remDes}	                        			
	                        		</div>
	                        		<div class="col-md-4">            			
	                        		</div>
	                        		<c:if test="${registre.idIncidencies != '-1#' && registre.idIncidencies != '-1' && registre.idIncidencies != '-2#' && registre.idIncidencies != '-2'}">
		                        		<div class="col-md-4">
		                        			<c:choose>
		                        				<c:when test="${registre.tipus == 'Procediment judicial'}">
		                        					Procediment relacionat: </br>
		                        					<c:forEach items="${registre.getIdIncidenciesList()}" var="idIncidencia" >
			                        			 		<a href="procediment?ref=${idIncidencia}" class="loadingButton"  data-msg="obrint procediment...">${numAutosProcediment}</a></br>
			                        			 	</c:forEach>   
		                        				</c:when>
		                        				<c:otherwise>
		                        					Incidències relacionades: </br>
				                        			<c:forEach items="${registre.getIdIncidenciesList()}" var="idIncidencia" >
			                        			 		<a href="incidenciaDetalls?ref=${idIncidencia}" class="loadingButton"  data-msg="obrint incidència...">${idIncidencia}</a></br>
			                        			 	</c:forEach>     
		                        				</c:otherwise>
		                        			</c:choose>	
		                        		</div>
		                        	</c:if>
	                        	</div>
	                        </div>
	                    </div>
	                </div>
            	</div>            	
            	<c:if test="${canCreateRegistre}">
	            	<div class="row">
	            		<div class="col-xs-offset-10 col-xs-2"> 
	   						<a href="editRegistre?id=${registre.id}&tipus=${tipus}" class="btn btn-success">Modificar registre</a>
	   					</div>
	            	</div>	            	
            	</c:if>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <!-- /#wrapper -->
</body>
</html>