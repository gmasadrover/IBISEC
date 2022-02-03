<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<div class="panel-body">
	<div class="row">
    	<div class="col-md-12">   
	    	<h4>Certificació</h4>
	    	<p>
	    		<label>Certificació: </label> <a target="_blanck" href="certificacioDetalls?ref=${factura.idFactura}">${factura.idFactura}</a>
	    	</p>	
	    	<p>
	    		<label>Tipus:</label>  ${factura.tipus}
	    	</p>
		    <p>
				<label>Import certificació:</label> ${factura.valor}
			</p>
			<p>
				<label>Concepte:</label> ${factura.concepte}
			</p>
			<p>
				<label>Proveïdor:</label> ${factura.nomProveidor}
			</p>						       	
			<p>				
	            <label>Certificació: </label>		           
	            <c:forEach items="${factura.certificacions}" var="arxiu" >
					<c:set var="arxiu" value="${arxiu}" scope="request"/>
					<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
				</c:forEach>
		 	</p>
		</div>
    </div>
    <c:choose>
    	<c:when test="${canModificarFactura && tasca.tipus=='revisarCertificacio'}">
			<form class="form-horizontal" target="_blank" method="POST" enctype="multipart/form-data" action="DoTasca" onsubmit="setTimeout(function () { window.location.reload(); }, 10)"> 	
		     	<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
				<input type="hidden" name="idIncidencia" value="${informePrevi.idIncidencia}">															
				<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
				<input type="hidden" name="idTasca" value="${tasca.idTasca}">		
				<input type="hidden" name="idFactura" value="${factura.idFactura}">		
				<input type="hidden" name="document" value="certificacioValidada">
		    	<div class="col-md-4">												        		
		   			<div class="row">
			       		<div class="col-md-12">															        																						 				
					 		<input class="btn btn-success margin_top30 upload" type="submit" name="enviar" value="Enviar certificació al cap">
					 	</div>
		    		</div>
		   		</div>															     											    		
			</form>    
		</c:when>
		<c:when test="${tasca.tipus=='firmaCertificacio'}">
			<form class="form-horizontal" target="_blank" method="POST" enctype="multipart/form-data" action="DoTasca" onsubmit="setTimeout(function () { window.location.reload(); }, 10)"> 	
		     	<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
				<input type="hidden" name="idIncidencia" value="${informePrevi.idIncidencia}">															
				<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
				<input type="hidden" name="idTasca" value="${tasca.idTasca}">		
				<input type="hidden" name="idFactura" value="${factura.idFactura}">		
				<input type="hidden" name="document" value="conformarCertificacio">															
				<div class="col-md-8">
					<div class="row margin_top10">
		    			<div class="col-md-12">
		           			Pujar certificació signada: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
		    			</div>
		    		</div>																													        			
		    	</div>	
		    	<div class="col-md-4">												        		
		   			<div class="row">
			       		<div class="col-md-12">															        																						 				
					 		<input class="btn btn-success margin_top30 upload" type="submit" name="aprovarPD" value="Enviar certificació signada">
					 	</div>
		    		</div>
		   		</div>															     											    		
			</form>		
		</c:when>
		<c:when test="${tasca.tipus=='certificacioFirmada'}">
		</c:when>
    </c:choose>
</div>