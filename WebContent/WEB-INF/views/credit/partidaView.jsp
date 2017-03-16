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
                            Partida <small>Detalls</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Partida
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <div class="row">
                	<div class="col-lg-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <!-- /.row -->
                <c:if test="${not empty partida}">                	                		
               		<h2 class="margin_bottom30">Informació bàsica</h2>
		    		<div class="row">			    				    				    		
	                    <div class="col-xs-offset-1 col-lg-5">
	    					<p>
								<label>Codi:</label> ${partida.codi}
							</p>
                            <input type="hidden" name="codi" value="${partida.codi}">
	                        <p>
	                        	<label>Estat:</label> ${partida.getEstatFormat()}
	                        </p>    
	                        <p> 
	                        	<label>Total: </label> ${partida.getTotalPartidaFormat()}
                            </p>
	                        <p> 
	                        	<label>Gastat: </label> ${partida.getGastadaPartidaFormat()}
                            </p>                            
	                  	</div>
		             	<div class="col-xs-offset-1 col-lg-5">
		             		<p> 
	                        	<label>Nom: </label> ${partida.nom}
                            </p> 
		                    <p> 
	                        	<label>Tipus: </label> ${partida.tipus}
                            </p> 	
	                        <p> 
	                        	<label>Reservat: </label> ${partida.getReservaPartidaFormat()}
                            </p>                                                	
	                    </div>		            	
                	</div>    
                	<h2 class="margin_bottom30">Assignacions</h2>
                	<div class="row">
	                	<div class="table-responsive col-xs-offset-1 col-lg-10">							                        
		                    <table class="table table-striped table-bordered filerTable" id="assignacionsTable">
		                        <thead>
		                            <tr>
		                                <th>Codi</th>
		                                <th>Actuacio</th>
		                                <th>valor PA</th>
		                                <th>valor PD</th>				                                        							                                       
		                            </tr>
		                        </thead>
		                        <tbody>
								<c:forEach items="${llistaAssignacions}" var="assignacio" >
						          	<tr>							          	
						           		<td>${assignacio.idAssignacio}</td>
						            	<td>${assignacio.idActuacio}</td>
						            	<td>${assignacio.valorPA}</td>
						            	<td>${assignacio.valorPD}</td>
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
</body>
</html>