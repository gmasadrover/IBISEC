<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
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
                            Vehicles <small>Reserves</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Vehicles
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Reserves
                            </li>
                        </ol>
                    </div>
                </div>    
                <div class="row">
                	<div class="col-md-12">
               			 <h2>Les meves reserves</h2>
               			 <div class="col-md-offset-1 col-md-10">
               			 	${reservesPropies}
               			 </div>
               		</div>
               	 </div>
               	 <div class="row">
                	<div class="col-md-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <div class="row">
                	<form class="form-horizontal" method="POST" action="doAddReservaVehicle">						
						<div class="form-group">				
							<div class="col-md-offset-1  col-md-2">
							    <div class="col-md-12">
							      	<label>Vehicle</label>
							      	<div>
		                                <select class="form-control selectpicker" name="vehicle" id="vehicle">
		                                	<option value="cotxe">Cotxe</option>
		                                	<option value="furgoneta">Furgoneta</option>
		                                </select>
	                             	</div>
							    </div>						    
						  	</div>										
							<div class=" col-md-2">
							    <div class="col-md-12">
							      	<label>Dia</label>
							      	<div>
		                                <select class="form-control selectpicker" name="diaSetmana" id="dia">
		                                	${optionDies}
		                                </select>
	                             	</div>
							    </div>						    
						  	</div>	
						  	<div class="col-md-2">
						  		<div class="col-md-12">
							      	<label>Hora inici</label>
							      	<div>
		                                <select class="form-control selectpicker" name="horaIni" id="horaIni">
		                                <c:forEach begin="0" end="${fn:length(horesCotxe) - 1}" var="i" >
		                                	<option value="${i + 1}">${horesCotxe[i]}</option>
		                                </c:forEach>
		                                </select>
	                             	</div>
							    </div>		
						  	</div>
						  	<div class="col-md-2">
							    <div class="col-md-12">							      
							      	<label>Hora tornada</label>
							      	<div>
		                                <select class="form-control selectpicker" name="horaFi" id="horaFi">
		                                <c:forEach begin="0" end="${fn:length(horesCotxe) - 1}" var="i" >
		                                	<option value="${i + 1}">${horesCotxe[i]}</option>
		                                </c:forEach>
		                                </select>
	                             	</div>
							    </div>						    
						  	</div>	
						  	<div class="col-md-2">
							    <div class="col-md-12">
							      <label>Motiu</label>
							      <div>
							      		<textarea class="form-control" name="motiu" placeholder="motiu" rows="3" required></textarea>						      	
							      </div>
							    </div>
						  	</div>						  	
						</div>	
						<div class="form-group">
							<div class="col-md-offset-10 col-md-2">
						    	<input type="submit" class="btn btn-primary" name="reservar" value="Reservar">
							</div>
						</div>			
					</form>
                </div>
                <div class="row">
                	<input class="btn btn-primary canviarSetmana seguent" value="Següent setmana">
                </div>
                <div class="row">
                    <div class="col-md-12">
                    	<div class="setmana">
	              			<h1>Setmana ${setmanaCotxe.setmana}</h1>
	                        <h2>Cotxe</h2>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered">
	                                <thead>
	                                    <tr>
	                                        <th></th>
	                                        <th ${diaSetmana == 2 ? 'class="today"' : ''}>Dilluns ${setmanaCotxe.dilluns}</th>        
	                                        <th ${diaSetmana == 3 ? 'class="today"' : ''}>Dimarts ${setmanaCotxe.dimarts}</th>
	                                        <th ${diaSetmana == 4 ? 'class="today"' : ''}>Dimecres ${setmanaCotxe.dimecres}</th>
	                                        <th ${diaSetmana == 5 ? 'class="today"' : ''}>Dijous ${setmanaCotxe.dijous}</th>
	                                        <th ${diaSetmana == 6 ? 'class="today"' : ''}>Divendres ${setmanaCotxe.divendres}</th>                                       
	                                        <th class="weekend bloqued">Dissabte ${setmanaCotxe.dissabte}</th>
	                                        <th class="weekend bloqued">Diumenge ${setmanaCotxe.diumenge}</th>										                                
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach begin="0" end="${fn:length(horesCotxe) - 1}" var="i" >
	                                		<tr>							          	
								           		<td>${horesCotxe[i]}</td>
								           		<td class="${setmanaCotxe.reservesDilluns[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 2 ? ' today' : ''}">
								           			<c:if test="${i == 0 || setmanaCotxe.reservesDilluns[i].usuari.idUsuari != setmanaCotxe.reservesDilluns[i-1].usuari.idUsuari}">
									           			${setmanaCotxe.reservesDilluns[i].usuari.getNomCompletReal()}
									            		${setmanaCotxe.reservesDilluns[i].motiu}
								           			</c:if>								            		
								            	</td>	
								            	<td class="${setmanaCotxe.reservesDimarts[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 3 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaCotxe.reservesDimarts[i].usuari.idUsuari != setmanaCotxe.reservesDimarts[i-1].usuari.idUsuari}">
									           			${setmanaCotxe.reservesDimarts[i].usuari.getNomCompletReal()}
									            		${setmanaCotxe.reservesDimarts[i].motiu}
								           			</c:if>									            		
								            	</td>
								            	<td class="${setmanaCotxe.reservesDimecres[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 4 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaCotxe.reservesDimecres[i].usuari.idUsuari != setmanaCotxe.reservesDimecres[i-1].usuari.idUsuari}">
									           			${setmanaCotxe.reservesDimecres[i].usuari.getNomCompletReal()}
									            		${setmanaCotxe.reservesDimecres[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${setmanaCotxe.reservesDijous[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 5 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaCotxe.reservesDijous[i].usuari.idUsuari != setmanaCotxe.reservesDijous[i-1].usuari.idUsuari}">
									           			${setmanaCotxe.reservesDijous[i].usuari.getNomCompletReal()}
									            		${setmanaCotxe.reservesDijous[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${setmanaCotxe.reservesDivendres[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 6 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaCotxe.reservesDivendres[i].usuari.idUsuari != setmanaCotxe.reservesDivendres[i-1].usuari.idUsuari}">
									           			${setmanaCotxe.reservesDivendres[i].usuari.getNomCompletReal()}
									            		${setmanaCotxe.reservesDivendres[i].motiu}
								           			</c:if>	
								            	</td>			
								            	<td class="weekend bloqued"></td>
								            	<td class="weekend bloqued"></td>	            	
								          	</tr>
	                                	</c:forEach>						          	                       	
	                                </tbody>
	                            </table>
                        	</div>
                        </div>
                        <div class="hidden setmanaSeguent">
	              			<h1>Setmana ${seguentSetmanaCotxe.setmana}</h1>
	                        <h2>Cotxe</h2>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered">
	                                <thead>
	                                    <tr>
	                                        <th></th>
	                                        <th>Dilluns ${seguentSetmanaCotxe.dilluns}</th>        
	                                        <th>Dimarts ${seguentSetmanaCotxe.dimarts}</th>
	                                        <th>Dimecres ${seguentSetmanaCotxe.dimecres}</th>
	                                        <th>Dijous ${seguentSetmanaCotxe.dijous}</th>
	                                        <th>Divendres ${seguentSetmanaCotxe.divendres}</th>                                       
	                                        <th class="weekend bloqued">Dissabte ${seguentSetmanaCotxe.dissabte}</th>
	                                        <th class="weekend bloqued">Diumenge ${seguentSetmanaCotxe.diumenge}</th>										                                
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach begin="0" end="${fn:length(horesCotxe) - 1}" var="i" >
	                                		<tr>							          	
								           		<td>${horesCotxe[i]}</td>
								           		<td class="${seguentSetmanaCotxe.reservesDilluns[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								           			<c:if test="${i == 0 || seguentSetmanaCotxe.reservesDilluns[i].usuari.idUsuari != seguentSetmanaCotxe.reservesDilluns[i-1].usuari.idUsuari}">
									           			${seguentSetmanaCotxe.reservesDilluns[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaCotxe.reservesDilluns[i].motiu}
								           			</c:if>								            		
								            	</td>	
								            	<td class="${seguentSetmanaCotxe.reservesDimarts[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaCotxe.reservesDimarts[i].usuari.idUsuari != seguentSetmanaCotxe.reservesDimarts[i-1].usuari.idUsuari}">
									           			${seguentSetmanaCotxe.reservesDimarts[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaCotxe.reservesDimarts[i].motiu}
								           			</c:if>									            		
								            	</td>
								            	<td class="${seguentSetmanaCotxe.reservesDimecres[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaCotxe.reservesDimecres[i].usuari.idUsuari != seguentSetmanaCotxe.reservesDimecres[i-1].usuari.idUsuari}">
									           			${seguentSetmanaCotxe.reservesDimecres[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaCotxe.reservesDimecres[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${seguentSetmanaCotxe.reservesDijous[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaCotxe.reservesDijous[i].usuari.idUsuari != seguentSetmanaCotxe.reservesDijous[i-1].usuari.idUsuari}">
									           			${seguentSetmanaCotxe.reservesDijous[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaCotxe.reservesDijous[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${seguentSetmanaCotxe.reservesDivendres[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaCotxe.reservesDivendres[i].usuari.idUsuari != seguentSetmanaCotxe.reservesDivendres[i-1].usuari.idUsuari}">
									           			${seguentSetmanaCotxe.reservesDivendres[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaCotxe.reservesDivendres[i].motiu}
								           			</c:if>	
								            	</td>			
								            	<td class="weekend bloqued"></td>
								            	<td class="weekend bloqued"></td>	            	
								          	</tr>
	                                	</c:forEach>						          	                       	
	                                </tbody>
	                            </table>
                        	</div>
                        </div>
                    </div>
                </div>       
                <div class="row">
                    <div class="col-md-12">
                    	<div class="setmana">
	                        <h2>Furgoneta</h2>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered">
	                                <thead>
	                                    <tr>
	                                        <th></th>
	                                        <th ${diaSetmana == 2 ? 'class="today"' : ''}>Dilluns ${setmanaFurgoneta.dilluns}</th>        
	                                        <th ${diaSetmana == 3 ? 'class="today"' : ''}>Dimarts ${setmanaFurgoneta.dimarts}</th>
	                                        <th ${diaSetmana == 4 ? 'class="today"' : ''}>Dimecres ${setmanaFurgoneta.dimecres}</th>
	                                        <th ${diaSetmana == 5 ? 'class="today"' : ''}>Dijous ${setmanaFurgoneta.dijous}</th>
	                                        <th ${diaSetmana == 6 ? 'class="today"' : ''}>Divendres ${setmanaFurgoneta.divendres}</th>                                       
	                                        <th class="weekend bloqued">Dissabte ${setmanaFurgoneta.dissabte}</th>
	                                        <th class="weekend bloqued">Diumenge ${setmanaFurgoneta.diumenge}</th>										                                
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach begin="0" end="${fn:length(horesFurgoneta) - 1}" var="i" >
	                                		<tr>							          	
								           		<td>${horesFurgoneta[i]}</td>
								           		<td class="${setmanaFurgoneta.reservesDilluns[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 2 ? ' today' : ''}">
								           			<c:if test="${i == 0 || setmanaFurgoneta.reservesDilluns[i].usuari.idUsuari != setmanaFurgoneta.reservesDilluns[i-1].usuari.idUsuari}">
									           			${setmanaFurgoneta.reservesDilluns[i].usuari.getNomCompletReal()}
									            		${setmanaFurgoneta.reservesDilluns[i].motiu}
								           			</c:if>								            		
								            	</td>	
								            	<td class="${setmanaFurgoneta.reservesDimarts[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 3 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaFurgoneta.reservesDimarts[i].usuari.idUsuari != setmanaFurgoneta.reservesDimarts[i-1].usuari.idUsuari}">
									           			${setmanaFurgoneta.reservesDimarts[i].usuari.getNomCompletReal()}
									            		${setmanaFurgoneta.reservesDimarts[i].motiu}
								           			</c:if>									            		
								            	</td>
								            	<td class="${setmanaFurgoneta.reservesDimecres[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 4 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaFurgoneta.reservesDimecres[i].usuari.idUsuari != setmanaFurgoneta.reservesDimecres[i-1].usuari.idUsuari}">
									           			${setmanaFurgoneta.reservesDimecres[i].usuari.getNomCompletReal()}
									            		${setmanaFurgoneta.reservesDimecres[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${setmanaFurgoneta.reservesDijous[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 5 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaFurgoneta.reservesDijous[i].usuari.idUsuari != setmanaFurgoneta.reservesDijous[i-1].usuari.idUsuari}">
									           			${setmanaFurgoneta.reservesDijous[i].usuari.getNomCompletReal()}
									            		${setmanaFurgoneta.reservesDijous[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${setmanaFurgoneta.reservesDivendres[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 6 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaFurgoneta.reservesDivendres[i].usuari.idUsuari != setmanaFurgoneta.reservesDivendres[i-1].usuari.idUsuari}">
									           			${setmanaFurgoneta.reservesDivendres[i].usuari.getNomCompletReal()}
									            		${setmanaFurgoneta.reservesDivendres[i].motiu}
								           			</c:if>	
								            	</td>								           				
								            	<td class="weekend bloqued"></td>
								            	<td class="weekend bloqued"></td>	            	
								          	</tr>
	                                	</c:forEach>						          	                       	
	                                </tbody>
	                            </table>
                        	</div>
                        </div>
                        <div class="hidden setmanaSeguent">
	                        <h2>Furgoneta</h2>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered">
	                                <thead>
	                                    <tr>
	                                        <th></th>
	                                        <th>Dilluns ${seguentSetmanaFurgoneta.dilluns}</th>        
	                                        <th>Dimarts ${seguentSetmanaFurgoneta.dimarts}</th>
	                                        <th>Dimecres ${seguentSetmanaFurgoneta.dimecres}</th>
	                                        <th>Dijous ${seguentSetmanaFurgoneta.dijous}</th>
	                                        <th>Divendres ${seguentSetmanaFurgoneta.divendres}</th>                                       
	                                        <th class="weekend bloqued">Dissabte ${seguentSetmanaFurgoneta.dissabte}</th>
	                                        <th class="weekend bloqued">Diumenge ${seguentSetmanaFurgoneta.diumenge}</th>										                                
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach begin="0" end="${fn:length(horesFurgoneta) - 1}" var="i" >
	                                		<tr>							          	
								           		<td>${horesFurgoneta[i]}</td>
								           		<td class="${seguentSetmanaFurgoneta.reservesDilluns[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								           			<c:if test="${i == 0 || seguentSetmanaFurgoneta.reservesDilluns[i].usuari.idUsuari != seguentSetmanaFurgoneta.reservesDilluns[i-1].usuari.idUsuari}">
									           			${seguentSetmanaFurgoneta.reservesDilluns[i].usuari.getNomCompletReal()}	
									           			<p>${seguentSetmanaFurgoneta.reservesDilluns[i].motiu}</p>								            		
								           			</c:if>		
								            	</td>	
								            	<td class="${seguentSetmanaFurgoneta.reservesDimarts[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaFurgoneta.reservesDimarts[i].usuari.idUsuari != seguentSetmanaFurgoneta.reservesDimarts[i-1].usuari.idUsuari}">
									           			${seguentSetmanaFurgoneta.reservesDimarts[i].usuari.getNomCompletReal()}
									           			<p>${seguentSetmanaFurgoneta.reservesDimarts[i].motiu}</p>
								           			</c:if>									           										            		
								            	</td>
								            	<td class="${seguentSetmanaFurgoneta.reservesDimecres[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaFurgoneta.reservesDimecres[i].usuari.idUsuari != seguentSetmanaFurgoneta.reservesDimecres[i-1].usuari.idUsuari}">
									           			${seguentSetmanaFurgoneta.reservesDimecres[i].usuari.getNomCompletReal()}
									           			<p>${seguentSetmanaFurgoneta.reservesDimecres[i].motiu}</p>
								           			</c:if>	
								            	</td>
								            	<td class="${seguentSetmanaFurgoneta.reservesDijous[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaFurgoneta.reservesDijous[i].usuari.idUsuari != seguentSetmanaFurgoneta.reservesDijous[i-1].usuari.idUsuari}">
									           			${seguentSetmanaFurgoneta.reservesDijous[i].usuari.getNomCompletReal()}
									           			<p>${seguentSetmanaFurgoneta.reservesDijous[i].motiu}</p>
								           			</c:if>	
								            	</td>
								            	<td class="${seguentSetmanaFurgoneta.reservesDivendres[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaFurgoneta.reservesDivendres[i].usuari.idUsuari != seguentSetmanaFurgoneta.reservesDivendres[i-1].usuari.idUsuari}">
									           			${seguentSetmanaFurgoneta.reservesDivendres[i].usuari.getNomCompletReal()}
									           			<p>${seguentSetmanaFurgoneta.reservesDivendres[i].motiu}</p>
								           			</c:if>	
								            	</td>			
								            	<td class="weekend bloqued"></td>
								            	<td class="weekend bloqued"></td>	            	
								          	</tr>
	                                	</c:forEach>						          	                       	
	                                </tbody>
	                            </table>
                        	</div>
                        </div>
                    </div>
                </div>          
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>   
    <script src="js/gestio/vistaCalendari.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>
