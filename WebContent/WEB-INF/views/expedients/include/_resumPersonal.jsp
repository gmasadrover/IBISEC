<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<br />
<div class="panel-body">
	<h4>Personal associat a l'expedient</h4>
	<div class="table-responsive">                        
		<table class="table table-striped table-bordered">
		    <thead>
		        <tr>
		            <th>Usuari</th>
		            <th>Tecnic</th>
		            <th>Funció</th>		
		            <th>Data inici</th>	 
		            <th>Data fi</th>	   
		            <th>Control</th>                                     
		        </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${informePrevi.personal}" var="persona" >
					<tr>
						<c:choose>
							<c:when test="${persona.usuari.idUsuari == '-1'}">
								<td>${persona.empresa}</td>	
							</c:when>
							<c:otherwise>
								<td>${persona.usuari.getNomCompletReal()}</td>	
							</c:otherwise>
						</c:choose>							
						<td>${persona.tecnic}</td>						          	
						<td>${persona.funcio}</td>
						<td>${persona.getDataAltaString()}</td>
						<td>${persona.getDataBaixaString()}</td>
						<td>
							<c:if test="${canModificarPersonal && persona.actiu}">
								<input class="btn btn-danger btn-sm deleteRelacioPersona" data-idrelacio="${persona.relacioID}" type="button" value="Baixa">
	                   		</c:if>	          
						</td>
		    		</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="row">
	  			<c:if test="${canModificarPersonal}">
					<div class="col-md-offset-9 col-md-2 margin_top30">
						<a href="newPersonalInforme?idactuacio=${informePrevi.actuacio.referencia}&idinf=${informePrevi.idInf}&from=${redireccio}" class="btn btn-primary" role="button">Afegir</a>
					</div>
				</c:if>
	    	</div>       
		</div>
	</div>
</div>