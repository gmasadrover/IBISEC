<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div class="col-md-12">
	<c:choose>
		<c:when test="${tipus == 'entrada'}">			
			<h2>Entrades</h2>
		</c:when>    
		<c:otherwise>
			<h2>Sortides</h2>									        
		</c:otherwise>
	</c:choose>	
	<div class="table-responsive">
		<table class="table table-striped table-bordered filerTable ${canViewIncidencies ? 'withIncidencies' : 'withOutIncidencies'}">
	        <thead>
	            <tr>
	                <th>Referència</th>
	                <th>Data registre</th>
	                <c:choose>
						<c:when test="${tipus == 'entrada'}">			
							<th>Remitent</th>
						</c:when>    
						<c:otherwise>
							<th>Destinatari</th>									        
						</c:otherwise>
					</c:choose>	
	                <th>Contingut</th>
	                <th>Centre</th>
	                <c:if test="${canViewIncidencies}">
	                	<th>Incidència relacionada</th>
	                </c:if>
	                <th>Actuació relacionada</th>
	                <th>Data</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<c:forEach items="${registres}" var="registre" >
					<tr class="${!registre.actiu ? 'danger' : ''}">
						<td><a href="registre?tipus=${tipus=='entrada' ? 'E' : 'S'}&referencia=${registre.id}" class="loadingButton"  data-msg="obrint registre...">${registre.id}</a></td>
						<td>${registre.getDataString()}</td>
						<td>${registre.remDes}</td>
						<c:choose>
							<c:when test="${registre.tipus == 'Sol·licitud Personal'}">			
								<td>Sol·licitud personal</td>	
							</c:when>    
							<c:when test="${registre.tipus == 'Resposta Sol·licitud Personal'}">			
								<td>Resposta Sol·licitud Personal</td>	
							</c:when>    
							<c:when test="${registre.tipus == 'Tramesa documentació Personal'}">			
								<td>Tramesa documentació Personal</td>		
							</c:when> 
							<c:otherwise>
								<td>${registre.contingut}</td>									        
							</c:otherwise>
						</c:choose>								            		
						<td>${registre.getNomCentresString()}</td>	
						<c:if test="${canViewIncidencies}">
							<td>
								<c:forEach items="${registre.getIdIncidenciesList()}" var="idIncidencia" >
									<c:choose>
										<c:when test="${idIncidencia == '-1' || idIncidencia == '-2'}">												        
										</c:when>    
										<c:otherwise>
											<a href="incidenciaDetalls?ref=${idIncidencia}" class="loadingButton"  data-msg="obrint incidència...">${idIncidencia}</a></br>										        
					  					</c:otherwise>
									</c:choose>	
								</c:forEach> 
							</td>	
						</c:if>
						<td>
							<c:forEach items="${registre.getIdActuacionsList()}" var="idActuacio" >
								<c:choose>
									<c:when test="${idActuacio == '-1'}">												        
									</c:when>    
									<c:otherwise>
										<a href="actuacionsDetalls?ref=${idActuacio}" class="loadingButton"  data-msg="obrint actuació...">${idActuacio}</a></br>										        
									</c:otherwise>
								</c:choose>	
							</c:forEach> 
						</td>	
						<td>${registre.data}</td>					           								            	
					</tr>
				</c:forEach>                                	
	    	</tbody>
		</table>
	</div>
</div>