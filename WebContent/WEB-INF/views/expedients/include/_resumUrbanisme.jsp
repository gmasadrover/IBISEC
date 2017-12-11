<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<div class="row">
	<div class="col-md-12">
		<p> 
        	<label>Llicència: </label> ${informePrevi.llicencia.codi}
     	</p>
		<p> 
       		<label>Tipus: </label> ${informePrevi.llicencia.getTipusFormat()}
       	</p> 
       	<p> 
       		<label>Data sol·licitud: </label> ${informePrevi.llicencia.getDataPeticioString()}
       	</p>   
       	<p> 
       		<label>Data concesió: </label> ${informePrevi.llicencia.getDataConcesioString()}
       	</p>    
       	<p> 
       		<label>Data pagament: </label> ${informePrevi.llicencia.getDataPagamentString()}
       	</p>  
	</div>            		 
</div>
<div class="row">
	<div class="col-md-12">
		<div class="row">
   			<c:if test="${canModificarUrbanisme}">
				<div class="col-md-offset-9 col-md-2 margin_top30">
					<a href="editLlicencia?codi=${informePrevi.llicencia.codi}&from=expedient" class="btn btn-primary" role="button">Actualitzar</a>
				</div>
			</c:if>
 		</div>       
	</div>
</div>