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
                            Calendari <small>Vacances</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Calendari
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Vacances
                            </li>
                        </ol>
                    </div>
                </div>   
                <div class="row col-md-12">
                	<div class="tabbable">
                    	<ul class="nav nav-tabs">
						   	<li class='active'><a data-toggle="tab" href="#solicituds">Sol·licituds</a></li>
						    <li><a data-toggle="tab" href="#disfrutades">Disfrutades</a></li>
						    <li><a data-toggle="tab" href="#dies">Dies</a></li>						   
					 	</ul>
					  	<div class="tab-content">
					  		 <div id="solicituds" class="tab-pane fade in active">
					  		 	<div class="col-md-12 bordertab">
		               			 	${reservesPropiesPendents}
		               			 </div>
					  		 </div>
					  		 <div id="disfrutades" class="tab-pane fade">
					  		 	<div class="col-md-12 bordertab">
		               			 	${reservesPropiesDisfrutades}
		               			 </div>
					  		 </div>
					  		 <div id="dies" class="tab-pane fade">
					  		 	<div class="col-md-12 bordertab">
		               			 	<span>Dies disponibles vacances: ${diesDisponiblesVacances}</span>
		               			 	<br>
		               			 	<span>Dies disponibles P7: ${diesDisponiblesP7}</span>
		               			 </div>
					  		 </div>
					  	</div>					  	
					</div>                	
               	 </div>
               	 <div class="row">
                	<div class="col-md-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <div class="row">
                	<form class="form-horizontal" method="POST" action="doAddVacances">						
						<div class="col-md-12">	
							<h2>Realitzar sol·licitud</h2>			
							<div class="col-md-offset-1  col-md-2">
							    <div class="col-md-12">
							      	<label>Tipus</label>
							      	<div>
		                                <select class="form-control selectpicker" name="tipus" id="tipus">
		                                	<option value="vacances">Vacances</option>
		                                	<option value="p7">P7</option>
		                                	<option value="permis">Permis</option>
		                                </select>
	                             	</div>
							    </div>						    
						  	</div>										
							<div class=" col-md-4">
							    <div class="col-md-12">
							      	<label>Dies</label>
		                            <div class="input-group input-daterange datepicker">
									    <input type="text" class="form-control" name="dataInici" value="${dataInici}">
									    <div class="input-group-addon">fins</div>
									    <input type="text" class="form-control" name="dataFi" value="${dataFi}">
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
						    	<input type="submit" class="btn btn-primary" name="reservar" value="Sol·licitar">
							</div>
						</div>			
					</form>
                </div>               
                <div class="row">
                    <div class="col-md-12">
                    	<ul class="nav nav-tabs">
						   	<li ${actualMonth == 0 ? 'class="active"' : ''}><a data-toggle="tab" href="#gener">Gener</a></li>
						    <li ${actualMonth == 1 ? 'class="active"' : ''}><a data-toggle="tab" href="#febrer">Febrer</a></li>
						    <li ${actualMonth == 2 ? 'class="active"' : ''}><a data-toggle="tab" href="#març">Març</a></li>
						    <li ${actualMonth == 3 ? 'class="active"' : ''}><a data-toggle="tab" href="#abril">Abril</a></li>
						    <li ${actualMonth == 4 ? 'class="active"' : ''}><a data-toggle="tab" href="#maig">Maig</a></li>
						    <li ${actualMonth == 5 ? 'class="active"' : ''}><a data-toggle="tab" href="#juny">Juny</a></li>
						    <li ${actualMonth == 6 ? 'class="active"' : ''}><a data-toggle="tab" href="#juliol">Juliol</a></li>
						    <li ${actualMonth == 7 ? 'class="active"' : ''}><a data-toggle="tab" href="#agost">Agost</a></li>
						    <li ${actualMonth == 8 ? 'class="active"' : ''}><a data-toggle="tab" href="#setembre">Setembre</a></li>
						    <li ${actualMonth == 9 ? 'class="active"' : ''}><a data-toggle="tab" href="#octubre">Octubre</a></li>
						    <li ${actualMonth == 10 ? 'class="active"' : ''}><a data-toggle="tab" href="#novembre">Novembre</a></li>
						    <li ${actualMonth == 11 ? 'class="active"' : ''}><a data-toggle="tab" href="#desembre">Desembre</a></li>
					 	</ul>
					 	<br/>
					  	<div class="tab-content">
					  		<form class="form-horizontal" method="POST" action="Vacances">	
					  			<input type="hidden" name="departamentSeleccionat" id="departamentSeleccionat" value="${departament}">
					  			<select class="form-control selectpicker" name="departament" id="departament" onchange="this.form.submit()">
					  				<option value="obres">Obres</option>
                                	<option value="instalacions">Instal·lacions</option>
                                	<option value="gerencia">Gerència</option>
                                	<option value="comptabilitat">Comptabilitat</option>
                                	<option value="juridica">Jurídica</option>
					  			</select>					  			
					  		</form>
	                        
	                        <div id="gener" class="tab-pane fade ${actualMonth == 0 ? 'in active' : ''}">
		                        <div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[0], 1, actualYear, -1)}">${diesMesGener[0]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[1], 1, actualYear, -1)}">${diesMesGener[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[2], 1, actualYear, -1)}">${diesMesGener[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[3], 1, actualYear, -1)}">${diesMesGener[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[4], 1, actualYear, -1)}">${diesMesGener[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesGener[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesGener[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[7], 1, actualYear, -1)}">${diesMesGener[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[8], 1, actualYear, -1)}">${diesMesGener[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[9], 1, actualYear, -1)}">${diesMesGener[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[10], 1, actualYear, -1)}">${diesMesGener[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[11], 1, actualYear, -1)}">${diesMesGener[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesGener[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesGener[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[14], 1, actualYear, -1)}">${diesMesGener[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[15], 1, actualYear, -1)}">${diesMesGener[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[16], 1, actualYear, -1)}">${diesMesGener[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[17], 1, actualYear, -1)}">${diesMesGener[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[18], 1, actualYear, -1)}">${diesMesGener[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesGener[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesGener[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[21], 1, actualYear, -1)}">${diesMesGener[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[22], 1, actualYear, -1)}">${diesMesGener[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[23], 1, actualYear, -1)}">${diesMesGener[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[24], 1, actualYear, -1)}">${diesMesGener[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[25], 1, actualYear, -1)}">${diesMesGener[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesGener[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesGener[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[28], 1, actualYear, -1)}">${diesMesGener[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[29], 1, actualYear, -1)}">${diesMesGener[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[30], 1, actualYear, -1)}">${diesMesGener[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[31], 1, actualYear, -1)}">${diesMesGener[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[32], 1, actualYear, -1)}">${diesMesGener[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesGener[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesGener[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[35], 1, actualYear, -1)}">${diesMesGener[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesGener[36], 1, actualYear, -1)}">${diesMesGener[36]}</th>        									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[0], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[1], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[2], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[3], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[4], 1, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[7], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[8], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[9], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[10], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[11], 1, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[14], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[15], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[16], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[17], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[18], 1, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[21], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[22], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[23], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[24], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[25], 1, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[28], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[29], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[30], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[31], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[32], 1, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[35], 1, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesGener[36], 1, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="febrer" class="tab-pane fade ${actualMonth == 1 ? 'in active' : ''}">
		                        <div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[0], 2, actualYear, -1)}">${diesMesFebrer[0]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[1], 2, actualYear, -1)}">${diesMesFebrer[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[2], 2, actualYear, -1)}">${diesMesFebrer[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[3], 2, actualYear, -1)}">${diesMesFebrer[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[4], 2, actualYear, -1)}">${diesMesFebrer[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesFebrer[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesFebrer[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[7], 2, actualYear, -1)}">${diesMesFebrer[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[8], 2, actualYear, -1)}">${diesMesFebrer[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[9], 2, actualYear, -1)}">${diesMesFebrer[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[10], 2, actualYear, -1)}">${diesMesFebrer[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[11], 2, actualYear, -1)}">${diesMesFebrer[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesFebrer[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesFebrer[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[14], 2, actualYear, -1)}">${diesMesFebrer[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[15], 2, actualYear, -1)}">${diesMesFebrer[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[16], 2, actualYear, -1)}">${diesMesFebrer[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[17], 2, actualYear, -1)}">${diesMesFebrer[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[18], 2, actualYear, -1)}">${diesMesFebrer[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesFebrer[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesFebrer[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[21], 2, actualYear, -1)}">${diesMesFebrer[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[22], 2, actualYear, -1)}">${diesMesFebrer[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[23], 2, actualYear, -1)}">${diesMesFebrer[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[24], 2, actualYear, -1)}">${diesMesFebrer[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[25], 2, actualYear, -1)}">${diesMesFebrer[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesFebrer[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesFebrer[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[28], 2, actualYear, -1)}">${diesMesFebrer[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[29], 2, actualYear, -1)}">${diesMesFebrer[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[30], 2, actualYear, -1)}">${diesMesFebrer[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[31], 2, actualYear, -1)}">${diesMesFebrer[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[32], 2, actualYear, -1)}">${diesMesFebrer[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesFebrer[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesFebrer[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[35], 2, actualYear, -1)}">${diesMesFebrer[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesFebrer[36], 2, actualYear, -1)}">${diesMesFebrer[36]}</th>        									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[0], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[1], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[2], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[3], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[4], 2, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[7], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[8], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[9], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[10], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[11], 2, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[14], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[15], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[16], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[17], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[18], 2, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[21], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[22], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[23], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[24], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[25], 2, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[28], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[29], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[30], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[31], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[32], 2, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[35], 2, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesFebrer[36], 2, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="març" class="tab-pane fade ${actualMonth == 2 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[0], 3, actualYear, -1)}">${diesMesMarç[0]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[1], 3, actualYear, -1)}">${diesMesMarç[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[2], 3, actualYear, -1)}">${diesMesMarç[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[3], 3, actualYear, -1)}">${diesMesMarç[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[4], 3, actualYear, -1)}">${diesMesMarç[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesMarç[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesMarç[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[7], 3, actualYear, -1)}">${diesMesMarç[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[8], 3, actualYear, -1)}">${diesMesMarç[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[9], 3, actualYear, -1)}">${diesMesMarç[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[10], 3, actualYear, -1)}">${diesMesMarç[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[11], 3, actualYear, -1)}">${diesMesMarç[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesMarç[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesMarç[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[14], 3, actualYear, -1)}">${diesMesMarç[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[15], 3, actualYear, -1)}">${diesMesMarç[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[16], 3, actualYear, -1)}">${diesMesMarç[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[17], 3, actualYear, -1)}">${diesMesMarç[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[18], 3, actualYear, -1)}">${diesMesMarç[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesMarç[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesMarç[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[21], 3, actualYear, -1)}">${diesMesMarç[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[22], 3, actualYear, -1)}">${diesMesMarç[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[23], 3, actualYear, -1)}">${diesMesMarç[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[24], 3, actualYear, -1)}">${diesMesMarç[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[25], 3, actualYear, -1)}">${diesMesMarç[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesMarç[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesMarç[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[28], 3, actualYear, -1)}">${diesMesMarç[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[29], 3, actualYear, -1)}">${diesMesMarç[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[30], 3, actualYear, -1)}">${diesMesMarç[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[31], 3, actualYear, -1)}">${diesMesMarç[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[32], 3, actualYear, -1)}">${diesMesMarç[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesMarç[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesMarç[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[35], 3, actualYear, -1)}">${diesMesMarç[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMarç[36], 3, actualYear, -1)}">${diesMesMarç[36]}</th>        								       									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[0], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[1], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[2], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[3], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[4], 3, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[7], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[8], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[9], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[10], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[11], 3, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[14], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[15], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[16], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[17], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[18], 3, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[21], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[22], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[23], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[24], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[25], 3, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[28], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[29], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[30], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[31], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[32], 3, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[35], 3, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMarç[36], 3, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="abril" class="tab-pane fade ${actualMonth == 3 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[0], 4, actualYear, -1)}">${diesMesAbril[0]}</th>       
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[1], 4, actualYear, -1)}">${diesMesAbril[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[2], 4, actualYear, -1)}">${diesMesAbril[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[3], 4, actualYear, -1)}">${diesMesAbril[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[4], 4, actualYear, -1)}">${diesMesAbril[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesAbril[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesAbril[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[7], 4, actualYear, -1)}">${diesMesAbril[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[8], 4, actualYear, -1)}">${diesMesAbril[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[9], 4, actualYear, -1)}">${diesMesAbril[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[10], 4, actualYear, -1)}">${diesMesAbril[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[11], 4, actualYear, -1)}">${diesMesAbril[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesAbril[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesAbril[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[14], 4, actualYear, -1)}">${diesMesAbril[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[15], 4, actualYear, -1)}">${diesMesAbril[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[16], 4, actualYear, -1)}">${diesMesAbril[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[17], 4, actualYear, -1)}">${diesMesAbril[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[18], 4, actualYear, -1)}">${diesMesAbril[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesAbril[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesAbril[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[21], 4, actualYear, -1)}">${diesMesAbril[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[22], 4, actualYear, -1)}">${diesMesAbril[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[23], 4, actualYear, -1)}">${diesMesAbril[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[24], 4, actualYear, -1)}">${diesMesAbril[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[25], 4, actualYear, -1)}">${diesMesAbril[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesAbril[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesAbril[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[28], 4, actualYear, -1)}">${diesMesAbril[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[29], 4, actualYear, -1)}">${diesMesAbril[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[30], 4, actualYear, -1)}">${diesMesAbril[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[31], 4, actualYear, -1)}">${diesMesAbril[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[32], 4, actualYear, -1)}">${diesMesAbril[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesAbril[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesAbril[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[35], 4, actualYear, -1)}">${diesMesAbril[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAbril[36], 4, actualYear, -1)}">${diesMesAbril[36]}</th>        								        									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[0], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[1], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[2], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[3], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[4], 4, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[7], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[8], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[9], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[10], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[11], 4, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[14], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[15], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[16], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[17], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[18], 4, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[21], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[22], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[23], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[24], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[25], 4, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[28], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[29], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[30], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[31], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[32], 4, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[35], 4, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAbril[36], 4, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="maig" class="tab-pane fade ${actualMonth == 4 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[0], 5, actualYear, -1)}">${diesMesMaig[0]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[1], 5, actualYear, -1)}">${diesMesMaig[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[2], 5, actualYear, -1)}">${diesMesMaig[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[3], 5, actualYear, -1)}">${diesMesMaig[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[4], 5, actualYear, -1)}">${diesMesMaig[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesMaig[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesMaig[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[7], 5, actualYear, -1)}">${diesMesMaig[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[8], 5, actualYear, -1)}">${diesMesMaig[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[9], 5, actualYear, -1)}">${diesMesMaig[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[10], 5, actualYear, -1)}">${diesMesMaig[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[11], 5, actualYear, -1)}">${diesMesMaig[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesMaig[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesMaig[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[14], 5, actualYear, -1)}">${diesMesMaig[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[15], 5, actualYear, -1)}">${diesMesMaig[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[16], 5, actualYear, -1)}">${diesMesMaig[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[17], 5, actualYear, -1)}">${diesMesMaig[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[18], 5, actualYear, -1)}">${diesMesMaig[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesMaig[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesMaig[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[21], 5, actualYear, -1)}">${diesMesMaig[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[22], 5, actualYear, -1)}">${diesMesMaig[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[23], 5, actualYear, -1)}">${diesMesMaig[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[24], 5, actualYear, -1)}">${diesMesMaig[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[25], 5, actualYear, -1)}">${diesMesMaig[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesMaig[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesMaig[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[28], 5, actualYear, -1)}">${diesMesMaig[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[29], 5, actualYear, -1)}">${diesMesMaig[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[30], 5, actualYear, -1)}">${diesMesMaig[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[31], 5, actualYear, -1)}">${diesMesMaig[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[32], 5, actualYear, -1)}">${diesMesMaig[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesMaig[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesMaig[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[35], 5, actualYear, -1)}">${diesMesMaig[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesMaig[36], 5, actualYear, -1)}">${diesMesMaig[36]}</th>        								        									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[0], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[1], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[2], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[3], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[4], 5, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[7], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[8], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[9], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[10], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[11], 5, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[14], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[15], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[16], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[17], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[18], 5, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[21], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[22], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[23], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[24], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[25], 5, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[28], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[29], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[30], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[31], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[32], 5, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[35], 5, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesMaig[36], 5, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="juny" class="tab-pane fade ${actualMonth == 5 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[0], 6, actualYear, -1)}">${diesMesJuny[0]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[1], 6, actualYear, -1)}">${diesMesJuny[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[2], 6, actualYear, -1)}">${diesMesJuny[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[3], 6, actualYear, -1)}">${diesMesJuny[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[4], 6, actualYear, -1)}">${diesMesJuny[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesJuny[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuny[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[7], 6, actualYear, -1)}">${diesMesJuny[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[8], 6, actualYear, -1)}">${diesMesJuny[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[9], 6, actualYear, -1)}">${diesMesJuny[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[10], 6, actualYear, -1)}">${diesMesJuny[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[11], 6, actualYear, -1)}">${diesMesJuny[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesJuny[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuny[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[14], 6, actualYear, -1)}">${diesMesJuny[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[15], 6, actualYear, -1)}">${diesMesJuny[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[16], 6, actualYear, -1)}">${diesMesJuny[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[17], 6, actualYear, -1)}">${diesMesJuny[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[18], 6, actualYear, -1)}">${diesMesJuny[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesJuny[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuny[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[21], 6, actualYear, -1)}">${diesMesJuny[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[22], 6, actualYear, -1)}">${diesMesJuny[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[23], 6, actualYear, -1)}">${diesMesJuny[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[24], 6, actualYear, -1)}">${diesMesJuny[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[25], 6, actualYear, -1)}">${diesMesJuny[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesJuny[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuny[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[28], 6, actualYear, -1)}">${diesMesJuny[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[29], 6, actualYear, -1)}">${diesMesJuny[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[30], 6, actualYear, -1)}">${diesMesJuny[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[31], 6, actualYear, -1)}">${diesMesJuny[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[32], 6, actualYear, -1)}">${diesMesJuny[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesJuny[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuny[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[35], 6, actualYear, -1)}">${diesMesJuny[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuny[36], 6, actualYear, -1)}">${diesMesJuny[36]}</th>        								        									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[0], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[1], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[2], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[3], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[4], 6, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[7], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[8], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[9], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[10], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[11], 6, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[14], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[15], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[16], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[17], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[18], 6, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[21], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[22], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[23], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[24], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[25], 6, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[28], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[29], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[30], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[31], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[32], 6, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[35], 6, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuny[36], 6, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="juliol" class="tab-pane fade ${actualMonth == 6 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[0], 7, actualYear, -1)}">${diesMesJuliol[0]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[1], 7, actualYear, -1)}">${diesMesJuliol[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[2], 7, actualYear, -1)}">${diesMesJuliol[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[3], 7, actualYear, -1)}">${diesMesJuliol[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[4], 7, actualYear, -1)}">${diesMesJuliol[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesJuliol[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuliol[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[7], 7, actualYear, -1)}">${diesMesJuliol[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[8], 7, actualYear, -1)}">${diesMesJuliol[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[9], 7, actualYear, -1)}">${diesMesJuliol[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[10], 7, actualYear, -1)}">${diesMesJuliol[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[11], 7, actualYear, -1)}">${diesMesJuliol[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesJuliol[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuliol[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[14], 7, actualYear, -1)}">${diesMesJuliol[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[15], 7, actualYear, -1)}">${diesMesJuliol[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[16], 7, actualYear, -1)}">${diesMesJuliol[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[17], 7, actualYear, -1)}">${diesMesJuliol[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[18], 7, actualYear, -1)}">${diesMesJuliol[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesJuliol[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuliol[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[21], 7, actualYear, -1)}">${diesMesJuliol[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[22], 7, actualYear, -1)}">${diesMesJuliol[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[23], 7, actualYear, -1)}">${diesMesJuliol[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[24], 7, actualYear, -1)}">${diesMesJuliol[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[25], 7, actualYear, -1)}">${diesMesJuliol[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesJuliol[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuliol[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[28], 7, actualYear, -1)}">${diesMesJuliol[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[29], 7, actualYear, -1)}">${diesMesJuliol[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[30], 7, actualYear, -1)}">${diesMesJuliol[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[31], 7, actualYear, -1)}">${diesMesJuliol[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[32], 7, actualYear, -1)}">${diesMesJuliol[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesJuliol[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesJuliol[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[35], 7, actualYear, -1)}">${diesMesJuliol[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesJuliol[36], 7, actualYear, -1)}">${diesMesJuliol[36]}</th>        								 									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[0], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[1], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[2], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[3], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[4], 7, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[7], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[8], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[9], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[10], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[11], 7, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[14], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[15], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[16], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[17], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[18], 7, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[21], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[22], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[23], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[24], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[25], 7, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[28], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[29], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[30], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[31], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[32], 7, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[35], 7, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesJuliol[36], 7, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="agost" class="tab-pane fade ${actualMonth == 7 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[0], 8, actualYear, -1)}">${diesMesAgost[0]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[1], 8, actualYear, -1)}">${diesMesAgost[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[2], 8, actualYear, -1)}">${diesMesAgost[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[3], 8, actualYear, -1)}">${diesMesAgost[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[4], 8, actualYear, -1)}">${diesMesAgost[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesAgost[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesAgost[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[7], 8, actualYear, -1)}">${diesMesAgost[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[8], 8, actualYear, -1)}">${diesMesAgost[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[9], 8, actualYear, -1)}">${diesMesAgost[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[10], 8, actualYear, -1)}">${diesMesAgost[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[11], 8, actualYear, -1)}">${diesMesAgost[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesAgost[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesAgost[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[14], 8, actualYear, -1)}">${diesMesAgost[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[15], 8, actualYear, -1)}">${diesMesAgost[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[16], 8, actualYear, -1)}">${diesMesAgost[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[17], 8, actualYear, -1)}">${diesMesAgost[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[18], 8, actualYear, -1)}">${diesMesAgost[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesAgost[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesAgost[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[21], 8, actualYear, -1)}">${diesMesAgost[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[22], 8, actualYear, -1)}">${diesMesAgost[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[23], 8, actualYear, -1)}">${diesMesAgost[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[24], 8, actualYear, -1)}">${diesMesAgost[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[25], 8, actualYear, -1)}">${diesMesAgost[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesAgost[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesAgost[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[28], 8, actualYear, -1)}">${diesMesAgost[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[29], 8, actualYear, -1)}">${diesMesAgost[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[30], 8, actualYear, -1)}">${diesMesAgost[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[31], 8, actualYear, -1)}">${diesMesAgost[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[32], 8, actualYear, -1)}">${diesMesAgost[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesAgost[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesAgost[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[35], 8, actualYear, -1)}">${diesMesAgost[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesAgost[36], 8, actualYear, -1)}">${diesMesAgost[36]}</th>        								        									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[0], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[1], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[2], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[3], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[4], 8, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[7], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[8], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[9], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[10], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[11], 8, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[14], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[15], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[16], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[17], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[18], 8, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[21], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[22], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[23], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[24], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[25], 8, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[28], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[29], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[30], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[31], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[32], 8, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[35], 8, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesAgost[36], 8, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="setembre" class="tab-pane fade ${actualMonth == 8 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[0], 9, actualYear, -1)}">${diesMesSetembre[0]}</th>   
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[1], 9, actualYear, -1)}">${diesMesSetembre[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[2], 9, actualYear, -1)}">${diesMesSetembre[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[3], 9, actualYear, -1)}">${diesMesSetembre[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[4], 9, actualYear, -1)}">${diesMesSetembre[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesSetembre[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesSetembre[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[7], 9, actualYear, -1)}">${diesMesSetembre[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[8], 9, actualYear, -1)}">${diesMesSetembre[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[9], 9, actualYear, -1)}">${diesMesSetembre[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[10], 9, actualYear, -1)}">${diesMesSetembre[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[11], 9, actualYear, -1)}">${diesMesSetembre[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesSetembre[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesSetembre[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[14], 9, actualYear, -1)}">${diesMesSetembre[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[15], 9, actualYear, -1)}">${diesMesSetembre[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[16], 9, actualYear, -1)}">${diesMesSetembre[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[17], 9, actualYear, -1)}">${diesMesSetembre[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[18], 9, actualYear, -1)}">${diesMesSetembre[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesSetembre[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesSetembre[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[21], 9, actualYear, -1)}">${diesMesSetembre[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[22], 9, actualYear, -1)}">${diesMesSetembre[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[23], 9, actualYear, -1)}">${diesMesSetembre[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[24], 9, actualYear, -1)}">${diesMesSetembre[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[25], 9, actualYear, -1)}">${diesMesSetembre[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesSetembre[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesSetembre[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[28], 9, actualYear, -1)}">${diesMesSetembre[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[29], 9, actualYear, -1)}">${diesMesSetembre[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[30], 9, actualYear, -1)}">${diesMesSetembre[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[31], 9, actualYear, -1)}">${diesMesSetembre[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[32], 9, actualYear, -1)}">${diesMesSetembre[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesSetembre[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesSetembre[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[35], 9, actualYear, -1)}">${diesMesSetembre[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesSetembre[36], 9, actualYear, -1)}">${diesMesSetembre[36]}</th>        								        									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[0], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[1], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[2], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[3], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[4], 9, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[7], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[8], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[9], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[10], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[11], 9, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[14], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[15], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[16], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[17], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[18], 9, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[21], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[22], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[23], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[24], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[25], 9, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[28], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[29], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[30], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[31], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[32], 9, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[35], 9, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesSetembre[36], 9, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="octubre" class="tab-pane fade ${actualMonth == 9 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[0], 10, actualYear, -1)}">${diesMesOctubre[0]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[1], 10, actualYear, -1)}">${diesMesOctubre[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[2], 10, actualYear, -1)}">${diesMesOctubre[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[3], 10, actualYear, -1)}">${diesMesOctubre[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[4], 10, actualYear, -1)}">${diesMesOctubre[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesOctubre[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesOctubre[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[7], 10, actualYear, -1)}">${diesMesOctubre[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[8], 10, actualYear, -1)}">${diesMesOctubre[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[9], 10, actualYear, -1)}">${diesMesOctubre[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[10], 10, actualYear, -1)}">${diesMesOctubre[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[11], 10, actualYear, -1)}">${diesMesOctubre[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesOctubre[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesOctubre[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[14], 10, actualYear, -1)}">${diesMesOctubre[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[15], 10, actualYear, -1)}">${diesMesOctubre[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[16], 10, actualYear, -1)}">${diesMesOctubre[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[17], 10, actualYear, -1)}">${diesMesOctubre[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[18], 10, actualYear, -1)}">${diesMesOctubre[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesOctubre[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesOctubre[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[21], 10, actualYear, -1)}">${diesMesOctubre[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[22], 10, actualYear, -1)}">${diesMesOctubre[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[23], 10, actualYear, -1)}">${diesMesOctubre[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[24], 10, actualYear, -1)}">${diesMesOctubre[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[25], 10, actualYear, -1)}">${diesMesOctubre[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesOctubre[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesOctubre[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[28], 10, actualYear, -1)}">${diesMesOctubre[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[29], 10, actualYear, -1)}">${diesMesOctubre[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[30], 10, actualYear, -1)}">${diesMesOctubre[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[31], 10, actualYear, -1)}">${diesMesOctubre[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[32], 10, actualYear, -1)}">${diesMesOctubre[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesOctubre[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesOctubre[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[35], 10, actualYear, -1)}">${diesMesOctubre[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesOctubre[36], 10, actualYear, -1)}">${diesMesOctubre[36]}</th>        								    									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[0], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[1], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[2], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[3], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[4], 10, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[7], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[8], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[9], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[10], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[11], 10, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[14], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[15], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[16], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[17], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[18], 10, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[21], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[22], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[23], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[24], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[25], 10, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[28], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[29], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[30], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[31], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[32], 10, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[35], 10, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesOctubre[36], 10, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="novembre" class="tab-pane fade ${actualMonth == 10 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[0], 11, actualYear, -1)}">${diesMesNovembre[0]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[1], 11, actualYear, -1)}">${diesMesNovembre[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[2], 11, actualYear, -1)}">${diesMesNovembre[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[3], 11, actualYear, -1)}">${diesMesNovembre[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[4], 11, actualYear, -1)}">${diesMesNovembre[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesNovembre[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesNovembre[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[7], 11, actualYear, -1)}">${diesMesNovembre[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[8], 11, actualYear, -1)}">${diesMesNovembre[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[9], 11, actualYear, -1)}">${diesMesNovembre[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[10], 11, actualYear, -1)}">${diesMesNovembre[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[11], 11, actualYear, -1)}">${diesMesNovembre[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesNovembre[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesNovembre[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[14], 11, actualYear, -1)}">${diesMesNovembre[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[15], 11, actualYear, -1)}">${diesMesNovembre[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[16], 11, actualYear, -1)}">${diesMesNovembre[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[17], 11, actualYear, -1)}">${diesMesNovembre[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[18], 11, actualYear, -1)}">${diesMesNovembre[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesNovembre[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesNovembre[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[21], 11, actualYear, -1)}">${diesMesNovembre[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[22], 11, actualYear, -1)}">${diesMesNovembre[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[23], 11, actualYear, -1)}">${diesMesNovembre[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[24], 11, actualYear, -1)}">${diesMesNovembre[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[25], 11, actualYear, -1)}">${diesMesNovembre[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesNovembre[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesNovembre[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[28], 11, actualYear, -1)}">${diesMesNovembre[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[29], 11, actualYear, -1)}">${diesMesNovembre[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[30], 11, actualYear, -1)}">${diesMesNovembre[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[31], 11, actualYear, -1)}">${diesMesNovembre[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[32], 11, actualYear, -1)}">${diesMesNovembre[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesNovembre[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesNovembre[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[35], 11, actualYear, -1)}">${diesMesNovembre[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesNovembre[36], 11, actualYear, -1)}">${diesMesNovembre[36]}</th>        								        									                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[0], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[1], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[2], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[3], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[4], 11, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[7], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[8], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[9], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[10], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[11], 11, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[14], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[15], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[16], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[17], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[18], 11, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[21], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[22], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[23], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[24], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[25], 11, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[28], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[29], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[30], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[31], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[32], 11, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[35], 11, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesNovembre[36], 11, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                            </table>
	                        	</div>
	                        </div>
	                        <div id="desembre" class="tab-pane fade ${actualMonth == 11 ? 'in active' : ''}">
	                        	<div class="table-responsive">                        
		                            <table class="table table-striped table-bordered">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[0], 12, actualYear, -1)}">${diesMesDesembre[0]}</th> 
		                                       	<th class="smallDay ${vacances.getEventDay(diesMesDesembre[1], 12, actualYear, -1)}">${diesMesDesembre[1]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[2], 12, actualYear, -1)}">${diesMesDesembre[2]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[3], 12, actualYear, -1)}">${diesMesDesembre[3]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[4], 12, actualYear, -1)}">${diesMesDesembre[4]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesDesembre[5]}</th>
		                                        <th class="bloqued smallDay">${diesMesDesembre[6]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[7], 12, actualYear, -1)}">${diesMesDesembre[7]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[8], 12, actualYear, -1)}">${diesMesDesembre[8]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[9], 12, actualYear, -1)}">${diesMesDesembre[9]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[10], 12, actualYear, -1)}">${diesMesDesembre[10]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[11], 12, actualYear, -1)}">${diesMesDesembre[11]}</th>                                      
		                                        <th class="bloqued smallDay">${diesMesDesembre[12]}</th>
		                                        <th class="bloqued smallDay">${diesMesDesembre[13]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[14], 12, actualYear, -1)}">${diesMesDesembre[14]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[15], 12, actualYear, -1)}">${diesMesDesembre[15]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[16], 12, actualYear, -1)}">${diesMesDesembre[16]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[17], 12, actualYear, -1)}">${diesMesDesembre[17]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[18], 12, actualYear, -1)}">${diesMesDesembre[18]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesDesembre[19]}</th>
		                                        <th class="bloqued smallDay">${diesMesDesembre[20]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[21], 12, actualYear, -1)}">${diesMesDesembre[21]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[22], 12, actualYear, -1)}">${diesMesDesembre[22]}</th>      
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[23], 12, actualYear, -1)}">${diesMesDesembre[23]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[24], 12, actualYear, -1)}">${diesMesDesembre[24]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[25], 12, actualYear, -1)}">${diesMesDesembre[25]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesDesembre[26]}</th>
		                                        <th class="bloqued smallDay">${diesMesDesembre[27]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[28], 12, actualYear, -1)}">${diesMesDesembre[28]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[29], 12, actualYear, -1)}">${diesMesDesembre[29]}</th>        
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[30], 12, actualYear, -1)}">${diesMesDesembre[30]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[31], 12, actualYear, -1)}">${diesMesDesembre[31]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[32], 12, actualYear, -1)}">${diesMesDesembre[32]}</th>                                       
		                                        <th class="bloqued smallDay">${diesMesDesembre[33]}</th>
		                                        <th class="bloqued smallDay">${diesMesDesembre[34]}</th>			
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[35], 12, actualYear, -1)}">${diesMesDesembre[35]}</th>
		                                        <th class="smallDay ${vacances.getEventDay(diesMesDesembre[36], 12, actualYear, -1)}">${diesMesDesembre[36]}</th>        										                                
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<c:forEach items="${usuarisObres}" var="usuari" >
		                                		<tr>							          	
									           		<td class="nameUserTable">${usuari.getNomCompletReal()}</td>
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[0], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[1], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[2], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[3], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[4], 12, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[7], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[8], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[9], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[10], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[11], 12, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[14], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[15], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[16], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[17], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[18], 12, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[21], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[22], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[23], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[24], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[25], 12, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[28], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[29], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[30], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[31], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[32], 12, actualYear, usuari.idUsuari)}"></td>			
									            	<td class="bloqued smallDay"></td>
									            	<td class="bloqued smallDay"></td>	     
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[35], 12, actualYear, usuari.idUsuari)}"></td>	
									            	<td class="smallDay ${vacances.getEventDay(diesMesDesembre[36], 12, actualYear, usuari.idUsuari)}"></td>	       	
									          	</tr>
		                                	</c:forEach>						          	                       	
		                                </tbody>
		                             </table>
		                            
	                        	</div>
	                        </div>	                      	                        
                        </div>  
                    </div>
                </div>  
                <div class="row">
                	<div class="col-md-3">
                		<div class="square vacances left"></div>
                		<span>Sol·licitud vacances</span>
                	</div>
                	<div class="col-md-3">
                		<div class="square vacances autoritzat left"></div>
                		<span>Sol·licitud vacances autoritzada</span>
                	</div>
                	<div class="col-md-3">
                		<div class="square vacances vistiplau left"></div>
                		<span>Sol·licitud vacances amb vistiplau</span>
                	</div>
                </div>  
                <div class="row">
                	<div class="col-md-3">
                		<div class="square p7 left"></div>
                		<span>Sol·licitud P7</span>
                	</div>
                	<div class="col-md-3">
                		<div class="square p7 autoritzat left"></div>
                		<span>Sol·licitud P7 autoritzat</span>
                	</div>
                	<div class="col-md-3">
                		<div class="square p7 vistiplau left"></div>
                		<span>Sol·licitud P7 amb vistiplau</span>
                	</div>
                </div>  
                <div class="row">
                	<div class="col-md-3">
                		<div class="square festiu left"></div>
                		<span>Festiu</span>
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
