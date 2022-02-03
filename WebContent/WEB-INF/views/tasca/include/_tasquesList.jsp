<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div class="col-md-12">
	<h2>Tasques</h2>
	<div class="table-responsive">                        
	    <table class="table table-striped table-bordered filerTable tasques">
	        <thead>
	            <tr>    
	            	<th>Expedient</th>                                    
	                <th>Tasca</th>
	                <th>Responsable</th>
	                <th>Remitent</th>
	                <th>Actuació</th>
	                <th>Illa</th>    
	                <th>Centre</th>  	               
	                <th>Descripció</th>                                 
	                <th>Data creació</th>
	                <th>Data creació</th>
	                <th>Data modificació</th>
	                <th>Data modificació</th>	
	                <th>Departament</th>       
	                <th>Valor PA</th>
	                <th>Valor PD</th>
	                <th>Estat</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<c:forEach items="${tasquesList}" var="tasca" >
	        		<c:if test="${!(tasca.actuacio.centre.idCentre == '9999PERSO' && !canViewPersonal)}">
						<tr class="${tasca.activa ? tasca.tipus == 'notificacio' ? "warning" : "success" : "danger"}">							          	
							<td>
								<c:choose>	
									<c:when test="${tasca.registre != null}">
										<a href="registre?from=notificacio&idTasca=${tasca.idTasca}&tipus=E&referencia=${tasca.registre}" class="loadingButton"  data-msg="obrint registre...">${tasca.registre}</a>	
									</c:when>			
			            			<c:when test="${tasca.informe != null && tasca.informe.expcontratacio != null && tasca.informe.expcontratacio.expContratacio != '-1'}">
			            				<a href="actuacionsDetalls?ref=${tasca.actuacio.referencia}&exp=${tasca.informe.idInf}" class="loadingButton"  data-msg="obrint expedient...">${tasca.informe.expcontratacio.expContratacio}</a>
			            			</c:when>			            			
			            			<c:otherwise>
			            				${tasca.idinforme}		            				
			            			</c:otherwise>
			            		</c:choose>
			            		<c:if test="${tasca.prioritat > 0}">
			            			- Prioritat ${tasca.prioritat}
			            		</c:if>
							</td>
							<td class="col-md-5"><a href="tasca?id=${tasca.idTasca}" class="loadingButton"  data-msg="obrint tasca...">${!tasca.llegida ? '<img src="css/img/exclamation.png" class="exclamationimg">' : ''} ${tasca.idTasca} - ${tasca.descripcio}</a></td>
							<td>${tasca.usuari.getNomComplet()}</td>	
							<td>${tasca.usuCre}</td>							
			            	<c:choose>			            		
				            	<c:when test="${tasca.tipus != 'pagamentJudicial' && tasca.tipus != 'judicial'}">
				            		<td>
					            		<c:choose>
					            			<c:when test="${tasca.actuacio.referencia == '-1' || tasca.actuacio.referencia == '' || tasca.actuacio.referencia == null}">
					            				<a href="incidenciaDetalls?ref=${tasca.incidencia.idIncidencia}" class="loadingButton"  data-msg="obrint actuació...">${tasca.incidencia.idIncidencia}</a>
					            			</c:when>
					            			<c:when test="${tasca.actuacio.referencia != '-1' && tasca.actuacio.referencia != '0'}">
					            				<a href="actuacionsDetalls?ref=${tasca.actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${tasca.actuacio.referencia}</a>
					            			</c:when>
					            			<c:otherwise>
					            				${tasca.idinforme}
					            			</c:otherwise>
					            		</c:choose>
					            	</td>
					            	<c:choose>
					            		<c:when test="${tasca.actuacio.referencia == '-1' || tasca.actuacio.referencia == '' || tasca.actuacio.referencia == null}">
											<td></td>	
											<td class="col-md-5">${tasca.incidencia.nomCentre}</td>	
											<td></td>			
										</c:when>
										<c:otherwise>
											<td>${tasca.actuacio.centre.illa}</td>	
											<td class="col-md-5">${tasca.actuacio.centre.getNomComplet()}</td>	
											<td class="col-md-5">${tasca.actuacio.descripcio} - ${tasca.informe.getPropostaInformeSeleccionada().getObjecte()}</td>		
										</c:otherwise>
					            	</c:choose>	
								</c:when>
								<c:otherwise>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</c:otherwise>
							</c:choose>											            	
							<td>${tasca.getDataCreacioString()}</td>
							<td>${tasca.dataCreacio}</td>	
							<td>${tasca.getDarreraModificacioString()}</td>
							<td>${tasca.darreraModificacio}</td>
							<td>${tasca.usuari.departament}</td>
							<td>${tasca.informe.getPropostaInformeSeleccionada().getPlicFormat()}</td>
							<td>${tasca.informe.getOfertaSeleccionada().getPlicFormat()}</td>
							<td>${tasca.activa}</td>
						</tr>
					</c:if>
				</c:forEach>
	        </tbody>
	    </table>
	</div>
</div>