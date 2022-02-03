<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div class="col-md-12">
	<c:choose>
		<c:when test="${tipus=='notificacio'}">
			<h2>Notificació</h2>
		</c:when>
		<c:otherwise>
			<h2>Tasques</h2>
			<div class="">
		  		<input type="checkbox" data-idactuacio="${actuacio.referencia}" data-idinforme="${idInfActual}" class="filterWithClosed"> Mostrar tancades
		  	</div>	
		</c:otherwise>
	</c:choose>
  	<br />
	<div class="table-responsive">                        
	    <div class="table-responsive">                        
		    <table class="table table-striped table-bordered filerTable tasquesResum" id="tasquesResum${idInfActual}">
		        <thead>
		            <tr>                                        
		                <th>Tasca</th>
		                <th>Responsable</th>
		                <th>Data creació</th>
		                <th>Data modificació</th>
		            </tr>
		        </thead>
		        <tbody>
		        	<c:forEach items="${tasquesList}" var="tasca" >
		        		<c:if test="${!(tasca.actuacio.centre.idCentre == '9999PERSO' && !canViewPersonal)}">
							<tr class="${tasca.activa ? "success" : "danger"}">		
								<c:choose>
									<c:when test="${isIBISEC}">
										<td class="col-md-5"><a href="tasca?id=${tasca.idTasca}" class="loadingButton"  data-msg="obrint tasca...">${tasca.idTasca} - ${tasca.descripcio}</a></td>
									</c:when>
									<c:otherwise>
										<td class="col-md-5">${tasca.idTasca} - ${tasca.descripcio}</td>
									</c:otherwise>
								</c:choose>
								<td class="col-md-3">${tasca.usuari.getNomComplet()}</td>
								<td class="col-md-2">${tasca.getDataCreacioString()}</td>								
								<td class="col-md-2">${tasca.getDarreraModificacioString()}</td>						
							</tr>
						</c:if>
					</c:forEach>
		        </tbody>
		    </table>
		</div>
	</div>
</div>