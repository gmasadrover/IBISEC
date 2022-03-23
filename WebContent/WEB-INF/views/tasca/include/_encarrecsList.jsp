<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div class="col-md-12">
	<h2>Encàrrecs</h2>
	<div class="table-responsive">                        
	    <table class="table table-striped table-bordered filerTable encarrecs">
	        <thead>
	            <tr>    
	            	 <th>Codi</th>
                     <th>Centre</th> 
                     <th>Actuació</th>
                     <th>Usuari</th>
                     <th>Tècnic</th>
                     <th>Funció</th>
                     <th>Expedient</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<c:forEach items="${encarrecsList}" var="encarrec" >
	        		<tr class="">							          	
		           		<td>
							<c:choose>
		            			<c:when test="${encarrec.expcontratacio != null && encarrec.expcontratacio.expContratacio != '-1'}">
		            				<a href="actuacionsDetalls?ref=${encarrec.actuacio.referencia}&exp=${encarrec.idInf}" class="loadingButton"  data-msg="obrint expedient...">${encarrec.expcontratacio.expContratacio}</a>
		            			</c:when>
		            			<c:otherwise>
		            				<c:choose>
				            			<c:when test="${encarrec.actuacio.referencia != '-1'}">
				            				<a href="actuacionsDetalls?ref=${encarrec.actuacio.referencia}&exp=${encarrec.idInf}" class="loadingButton"  data-msg="obrint actuació...">${encarrec.idInf}</a>
				            			</c:when>
				            			<c:otherwise>
				            				${encarrec.idInf}
				            			</c:otherwise>
				            		</c:choose>
		            			</c:otherwise>
		            		</c:choose>
						</td>		
						<td>${encarrec.actuacio.centre.getNomComplet()}</td>										
						<td>${encarrec.actuacio.descripcio}</td>		
						<td>${encarrec.personal.get(0).getUsuari().getNomComplet()}</td>
						<td>${encarrec.personal.get(0).tecnic}</td>		
						<td>${encarrec.personal.get(0).funcio}</td>										            	
		            	<td>
							<c:choose>
		            			<c:when test="${encarrec.propostaInformeSeleccionada != null}">
		            				${encarrec.propostaInformeSeleccionada.objecte}
		            			</c:when>
		            			<c:otherwise>
		            				
		            			</c:otherwise>
		            		</c:choose>
						</td>
	          		</tr>
				</c:forEach>
	        </tbody>
	    </table>
	</div>
</div>