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
                            Sala <small>Reserves</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Sala
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
                	<form class="form-horizontal" method="POST" action="doAddReservaSala">						
						<div class="form-group">		
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
		                                <c:forEach begin="0" end="${fn:length(horesSala) - 1}" var="i" >
		                                	<option value="${i + 1}">${horesSala[i]}</option>
		                                </c:forEach>
		                                </select>
	                             	</div>
							    </div>		
						  	</div>
						  	<div class="col-md-2">
							    <div class="col-md-12">							      
							      	<label>Hora fi</label>
							      	<div>
		                                <select class="form-control selectpicker" name="horaFi" id="horaFi">
		                                <c:forEach begin="0" end="${fn:length(horesSala) - 1}" var="i" >
		                                	<option value="${i + 1}">${horesSala[i]}</option>
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
	              			<h1>Setmana ${setmanaSala.setmana}</h1>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered">
	                                <thead>
	                                    <tr>
	                                        <th></th>
	                                        <th ${diaSetmana == 2 ? 'class="today"' : ''}>Dilluns ${setmanaSala.dilluns}</th>        
	                                        <th ${diaSetmana == 3 ? 'class="today"' : ''}>Dimarts ${setmanaSala.dimarts}</th>
	                                        <th ${diaSetmana == 4 ? 'class="today"' : ''}>Dimecres ${setmanaSala.dimecres}</th>
	                                        <th ${diaSetmana == 5 ? 'class="today"' : ''}>Dijous ${setmanaSala.dijous}</th>
	                                        <th ${diaSetmana == 6 ? 'class="today"' : ''}>Divendres ${setmanaSala.divendres}</th>                                       
	                                        <th class="weekend bloqued">Dissabte ${setmanaSala.dissabte}</th>
	                                        <th class="weekend bloqued">Diumenge ${setmanaSala.diumenge}</th>										                                
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach begin="0" end="${fn:length(horesSala) - 1}" var="i" >
	                                		<tr>							          	
								           		<td>${horesSala[i]}</td>
								           		<td class="${setmanaSala.reservesDilluns[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 2 ? ' today' : ''}">
								           			<c:if test="${i == 0 || setmanaSala.reservesDilluns[i].usuari.idUsuari != setmanaSala.reservesDilluns[i-1].usuari.idUsuari}">
									           			${setmanaSala.reservesDilluns[i].usuari.getNomCompletReal()}
									            		${setmanaSala.reservesDilluns[i].motiu}
								           			</c:if>								            		
								            	</td>	
								            	<td class="${setmanaSala.reservesDimarts[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 3 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaSala.reservesDimarts[i].usuari.idUsuari != setmanaSala.reservesDimarts[i-1].usuari.idUsuari}">
									           			${setmanaSala.reservesDimarts[i].usuari.getNomCompletReal()}
									            		${setmanaSala.reservesDimarts[i].motiu}
								           			</c:if>									            		
								            	</td>
								            	<td class="${setmanaSala.reservesDimecres[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 4 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaSala.reservesDimecres[i].usuari.idUsuari != setmanaSala.reservesDimecres[i-1].usuari.idUsuari}">
									           			${setmanaSala.reservesDimecres[i].usuari.getNomCompletReal()}
									            		${setmanaSala.reservesDimecres[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${setmanaSala.reservesDijous[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 5 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaSala.reservesDijous[i].usuari.idUsuari != setmanaSala.reservesDijous[i-1].usuari.idUsuari}">
									           			${setmanaSala.reservesDijous[i].usuari.getNomCompletReal()}
									            		${setmanaSala.reservesDijous[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${setmanaSala.reservesDivendres[i].usuari.idUsuari > 0 ? 'reserved' : ''} ${diaSetmana == 6 ? ' today' : ''}">
								            		<c:if test="${i == 0 || setmanaSala.reservesDivendres[i].usuari.idUsuari != setmanaSala.reservesDivendres[i-1].usuari.idUsuari}">
									           			${setmanaSala.reservesDivendres[i].usuari.getNomCompletReal()}
									            		${setmanaSala.reservesDivendres[i].motiu}
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
	              			<h1>Setmana ${seguentSetmanaSala.setmana}</h1>
	                        <div class="table-responsive">                        
	                            <table class="table table-striped table-bordered">
	                                <thead>
	                                    <tr>
	                                        <th></th>
	                                        <th>Dilluns ${seguentSetmanaSala.dilluns}</th>        
	                                        <th>Dimarts ${seguentSetmanaSala.dimarts}</th>
	                                        <th>Dimecres ${seguentSetmanaSala.dimecres}</th>
	                                        <th>Dijous ${seguentSetmanaSala.dijous}</th>
	                                        <th>Divendres ${seguentSetmanaSala.divendres}</th>                                       
	                                        <th class="weekend bloqued">Dissabte ${seguentSetmanaSala.dissabte}</th>
	                                        <th class="weekend bloqued">Diumenge ${seguentSetmanaSala.diumenge}</th>										                                
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach begin="0" end="${fn:length(horesSala) - 1}" var="i" >
	                                		<tr>							          	
								           		<td>${horesSala[i]}</td>
								           		<td class="${seguentSetmanaSala.reservesDilluns[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								           			<c:if test="${i == 0 || seguentSetmanaSala.reservesDilluns[i].usuari.idUsuari != seguentSetmanaSala.reservesDilluns[i-1].usuari.idUsuari}">
									           			${seguentSetmanaSala.reservesDilluns[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaSala.reservesDilluns[i].motiu}
								           			</c:if>								            		
								            	</td>	
								            	<td class="${seguentSetmanaSala.reservesDimarts[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaSala.reservesDimarts[i].usuari.idUsuari != seguentSetmanaSala.reservesDimarts[i-1].usuari.idUsuari}">
									           			${seguentSetmanaSala.reservesDimarts[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaSala.reservesDimarts[i].motiu}
								           			</c:if>									            		
								            	</td>
								            	<td class="${seguentSetmanaSala.reservesDimecres[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaSala.reservesDimecres[i].usuari.idUsuari != seguentSetmanaSala.reservesDimecres[i-1].usuari.idUsuari}">
									           			${seguentSetmanaSala.reservesDimecres[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaSala.reservesDimecres[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${seguentSetmanaSala.reservesDijous[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaSala.reservesDijous[i].usuari.idUsuari != seguentSetmanaSala.reservesDijous[i-1].usuari.idUsuari}">
									           			${seguentSetmanaSala.reservesDijous[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaSala.reservesDijous[i].motiu}
								           			</c:if>	
								            	</td>
								            	<td class="${seguentSetmanaSala.reservesDivendres[i].usuari.idUsuari > 0 ? 'reserved' : ''}">
								            		<c:if test="${i == 0 || seguentSetmanaSala.reservesDivendres[i].usuari.idUsuari != seguentSetmanaSala.reservesDivendres[i-1].usuari.idUsuari}">
									           			${seguentSetmanaSala.reservesDivendres[i].usuari.getNomCompletReal()}
									            		${seguentSetmanaSala.reservesDivendres[i].motiu}
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
