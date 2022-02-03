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
	                        	<label>Total: </label> <a href="partidaDetalls?codi=${partida.codi}" class="loadingButton"  data-msg="obrint total partida...">${partida.getTotalPartidaFormat()}</a> 
                            </p>
                            <p> 
	                        	<label>Reservat (RF): </label> <a href="partidaDetalls?codi=${partida.codi}&estat=reservat" class="loadingButton"  data-msg="obrint total reservat...">${partida.getReservaPartidaFormat()}</a>
                            </p>
                            <p> 
	                        	<label>Bloquejat: </label> <a href="partidaDetalls?codi=${partida.codi}&estat=pagat" class="loadingButton"  data-msg="obrint total facturat...">${partida.getBloquejatFormat()}</a>
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
	                        	<label>Assignat / previst (Contractat): </label> <a href="partidaDetalls?codi=${partida.codi}&estat=contractat" class="loadingButton"  data-msg="obrint total contractat...">${partida.getPrevistPartidaFormat()}</a>
                            </p>                                       	
	                    </div>		            	
                	</div>   
                	<c:if test="${canEditPartida}">
			      		<div class="row margin_bottom10">
				    		<div class="col-md-offset-9 col-md-3 panel">
								<a href="editPartida?idPartida=${partida.codi}" class="btn btn-primary loadingButton"  data-msg="obrint formulari..." role="button">Modificar partida</a>
							</div>
			    		</div>
			    	</c:if> 
			    	<c:if test="${subPartidesList.size() > 0}">
				    	<h2 class="margin_bottom30">SubPartides</h2>
				    	<div class="row">
		                   <div class="table-responsive col-xs-offset-1 col-md-10">                        
	                            <table class="table table-striped table-bordered filerTable subpartides">
	                                <thead>
	                                    <tr>
	                                        <th>Codi</th>
	                                        <th>Nom</th>
	                                        <th>Tipus</th>
	                                        <th>Total</th>
	                                        <th>Per asignar</th>
	                                        <th>Reservat</th>
	                                        <th>Assignat / previst</th>     
	                                        <th>Bloquejat</th>                                
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach items="${subPartidesList}" var="subpartida" >
								          	<tr class=${subpartida.estat ? "success" : "danger"}>							          	
								           		<td><a href="partidaDetalls?codi=${subpartida.codi}" class="loadingButton"  data-msg="obrint partida...">${subpartida.codi}</a></td>
								            	<td>${subpartida.nom}</td>
								            	<td>${subpartida.tipus}</td>
								            	<td>${subpartida.getTotalPartidaFormat()}</td>
								            	<td>${subpartida.getPartidaPerAsignarFormat()}</td>
								            	<td>${subpartida.getReservaPartidaFormat()}</td>
								            	<td>${subpartida.getPrevistPartidaFormat()}</td>		
								            	<td>${subpartida.getBloquejatFormat()}</td>					            	
								          	</tr>
								       	</c:forEach>
	                                </tbody>
	                            </table>
	                        </div>
	                	</div>
			    	</c:if>
			    	
                	<h2 class="margin_bottom30">Assignacions</h2>
                	<div class="row">
	                	<div class="table-responsive col-xs-offset-1 col-md-10">							                        
		                    <table class="table table-striped table-bordered filerTable assignacionsTable" id="assignacionsTable">
		                        <thead>
		                            <tr>
		                           		<!--<th>Referència</th>-->
		                                <th>Actuacio</th>
		                                <th>Expedient</th>
		                                <th>Centre</th>
		                                <th>Objecte</th>
		                                <th>Data adjudicació</th>
		                                <th>Data adjudicació</th>
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
						          		<!--<td>${assignacio.idAssignacio}</td>-->			          	
						            	<td><a href="actuacionsDetalls?ref=${assignacio.informe.actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${assignacio.informe.actuacio.referencia}</a></td>
						            	<td>${assignacio.informe.expcontratacio.expContratacio}</td>
						            	<td>${assignacio.informe.actuacio.centre.getNomComplet()}</td>
						            	<td>${assignacio.informe.propostaInformeSeleccionada.objecte}</td>						            	
						            	<td>${assignacio.informe.expcontratacio.getDataAdjudicacioString()}</td>
						            	<td>${assignacio.informe.expcontratacio.dataAdjudicacio}</td>
						            	<td>${assignacio.getValorPAFormat()}</td>
						            	<td>${assignacio.valorPA}</td>
						            	<td>${assignacio.getValorPDFormat()}</td>
						            	<td>${assignacio.valorPD}</td>
						            	<td>${assignacio.getValorPagatFormat()}</td>
						            	<td>${assignacio.valorPagat}</td>
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