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
		            <th>Funció</th>		
		            <th>Data alta</th>	 
		            <th>Data baixa</th>	                                        
		        </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${informePrevi.personal}" var="persona" >
					<tr>	
						<td>${persona.getPlicFormat()}</td>							          	
						<td>${persona}</td>
						<td>${persona}</td>
						<td>${persona}</td>
		    		</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>	
</div>