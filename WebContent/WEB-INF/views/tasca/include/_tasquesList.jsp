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
	                <th>Tasca</th>
	                <th>Responsable</th>
	                <th>id Actuació</th>
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
	            </tr>
	        </thead>
	        <tbody>
	        	<c:forEach items="${tasquesList}" var="tasca" >
	        		<c:if test="${!(tasca.actuacio.centre.idCentre == '9999PERSO' && !canViewPersonal)}">
						<tr class="${tasca.activa ? tasca.tipus == 'notificacio' ? "warning" : "success" : "danger"}">							          	
							<td><a href="tasca?id=${tasca.idTasca}" class="loadingButton"  data-msg="obrint tasca...">${tasca.idTasca} - ${tasca.descripcio}</a></td>
							<td>${tasca.usuari.getNomComplet()}</td>	
							<td>
			            		<c:choose>
			            			<c:when test="${tasca.actuacio.referencia != '-1'}">
			            				<a href="actuacionsDetalls?ref=${tasca.actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${tasca.actuacio.referencia}</a>
			            			</c:when>
			            			<c:otherwise>
			            				${tasca.idinforme}
			            			</c:otherwise>
			            		</c:choose>
			            	</td>
							<td>${tasca.actuacio.centre.illa}</td>	
							<td>${tasca.actuacio.centre.getNomComplet()}</td>	
							<td>${tasca.actuacio.descripcio}</td>							            	
							<td>${tasca.getDataCreacioString()}</td>
							<td>${tasca.dataCreacio}</td>	
							<td>${tasca.getDarreraModificacioString()}</td>
							<td>${tasca.darreraModificacio}</td>
							<td>${tasca.usuari.departament}</td>
							<td>${tasca.informe.getPropostaInformeSeleccionada().getPlicFormat()}</td>
							<td>${tasca.informe.getOfertaSeleccionada().getPlicFormat()}</td>
						</tr>
					</c:if>
				</c:forEach>
	        </tbody>
	    </table>
	</div>
</div>