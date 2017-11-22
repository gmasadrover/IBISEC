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
        	<label>Llicència: </label> ${llicencia.codi}
     	</p>
		<p> 
         	<label>Expedient: </label> ${llicencia.expedient.expContratacio}
       	</p>
       	<p> 
       		<label>Descripció: </label> ${llicencia.expedient.informe.getPropostaInformeSeleccionada().objecte}
       	</p>    
       	<p> 
       		<label>Tipus: </label> ${llicencia.getTipusFormat()}
       	</p>    
       	<p> 
       		<label>Taxa: </label> ${llicencia.taxa}
       	</p> 
       	<p> 
       		<label>ICO: </label> ${llicencia.ico}
       	</p>    
       	<p> 
       		<label>Recàrreg ATIB: </label> ${llicencia.valorATIB}
       	</p>
       	<p> 
       		<label>Data sol·licitud: </label> ${llicencia.getDataPeticioString()}
       	</p>   
       	<p> 
       		<label>Data concesió: </label> ${llicencia.getDataConcesioString()}
       	</p>    
       	<p> 
       		<label>Data pagament: </label> ${llicencia.getDataPagamentString()}
       	</p>       
       	<p> 
       		<label>Observacions: </label> ${llicencia.observacio}
     	</p>    
	</div>            		 
</div>
<div class="row">
	<div class="col-md-12">
		<div class="row">
   			<c:if test="${canModificar}">
				<div class="col-md-offset-9 col-md-2 margin_top30">
					<a href="editLlicencia?codi=${llicencia.codi}" class="btn btn-primary" role="button">Actualitzar</a>
				</div>
			</c:if>
 		</div>       
	</div>
</div>