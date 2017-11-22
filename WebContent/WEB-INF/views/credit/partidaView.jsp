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
                	<div class="col-md-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <!-- /.row -->
                <c:if test="${not empty partida}">                	                		
               		<h2 class="margin_bottom30">Informació bàsica</h2>
		    		<div class="row">			    				    				    		
	                    <div class="col-xs-offset-1 col-md-5">
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
	                        	<label>Reservat (RF): </label> ${partida.getReservaPartidaFormat()}
                            </p>
                            <p> 
	                        	<label>Pagat: </label> ${partida.getPartidaPagatFormat()} 
                            </p>                       
	                  	</div>
		             	<div class="col-xs-offset-1 col-md-5">
		             		<p> 
	                        	<label>Nom: </label> ${partida.nom}
                            </p> 
		                    <p> 
	                        	<label>Tipus: </label> ${partida.tipus}
                            </p> 	
                            <p>
                            	<label>Disponible: </label> ${partida.getPartidaPerAsignarFormat()}
                            </p>
	                        <p> 
	                        	<label>Assignat / previst (Contractat): </label> ${partida.getPrevistPartidaFormat()}
                            </p>                                       	
	                    </div>		            	
                	</div>    
                	<h2 class="margin_bottom30">Assignacions</h2>
                	<div class="row">
	                	<div class="table-responsive col-xs-offset-1 col-md-10">							                        
		                    <table class="table table-striped table-bordered filerTable" id="assignacionsTable">
		                        <thead>
		                            <tr>
		                           		<th>Referència</th>
		                                <th>Actuacio</th>
		                                <th>Data aprovació</th>
		                                <th>Data aprovació</th>
		                                <th>Centre</th>
		                                <th>Objecte</th>
		                                <th>Expedient</th>
		                                <th>valor PA</th>
		                                <th>valor PA</th>
		                                <th>valor PD</th>	
		                                <th>valor PD</th>	
		                                <th>Pagat</th>
		                                <th>Pagat</th>			                                        							                                       
		                            </tr>
		                        </thead>
		                        <tbody>
								<c:forEach items="${llistaAssignacions}" var="assignacio" >
						          	<tr>		
						          		<td>${assignacio.idAssignacio}</td>					          	
						            	<td><a href="actuacionsDetalls?ref=${assignacio.informe.actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${assignacio.informe.actuacio.referencia}</a></td>
						            	<td>${assignacio.informe.getDataAprovacioString()}</td>
						            	<td>${assignacio.informe.dataAprovacio}</td>
						            	<td>${assignacio.informe.actuacio.centre.getNomComplet()}</td>
						            	<td>${assignacio.informe.actuacio.descripcio}</td>
						            	<td>${assignacio.informe.expcontratacio.expContratacio}</td>
						            	<td>${assignacio.getValorPAFormat()}</td>
						            	<td>${assignacio.valorPA}</td>
						            	<td>${assignacio.getValorPDFormat()}</td>
						            	<td>${assignacio.valorPD}</td>
						            	<td>${assignacio.informe.getTotalFacturatFormat()}</td>
						            	<td>${assignacio.informe.getTotalFacturat()}</td>
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
    <script src="js/credit/partida.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>