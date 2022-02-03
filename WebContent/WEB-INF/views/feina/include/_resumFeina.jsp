<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<div class="col-md-12">
	<h2>Feines</h2>
	<div class="table-responsive">                        
    	<table class="table table-striped table-bordered filerTable taulaFeines">
           	<thead>
           		<tr>                                        
            		<th>Feina</th>
                	<th>Remitent</th>
                	<th>Destinatari</th>
                	<th>Contingut</th>
                	<th>Data</th>
                	<th>Data</th>                                        
               		<th>Notes</th>
                	<th>Control</th>
               	</tr>
           	</thead>
        	<tbody>
               	<c:forEach items="${actuacio.getFeines()}" var="feina" >
		          	<tr>							          	
	           			<td>${feina.idFeina}</td>
		            	<td>${feina.nomRemitent}</td>	
		            	<td>${feina.nomDestinatari}</td>										            	
		            	<td>${feina.contingut}</td>	
		            	<td>${feina.getDataString()}</td>	
		            	<td>${feina.data}</td>											            						            	
		            	<td>${feina.notes}</td>		
		            	<td>
		            		<c:if test="${isIBISEC}">
	                   			<input class="btn btn-danger btn-sm deleteFeina" data-idfeina="${feina.idFeina}" data-msg="eliminant feina..." type="button" value="Eliminar">
	                   			<a href="modificarFeina?idFeina=${feina.idFeina}&idActuacio=${actuacio.referencia}" class="btn btn-primary btn-sm loadingButton"  data-msg="obrint feina..." role="button">Modificar</a>						                        			                       			
                   			</c:if>
                   		</td>	            	
		          	</tr>
	       		</c:forEach>
       		</tbody>
  		</table>
	</div>
</div>