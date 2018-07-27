<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<div class="panel-body">
	<h4>Adjudicació</h4>	
	<p>
		<label>L'empresa adjudicataria:</label> ${informePrevi.ofertaSeleccionada.nomEmpresa} (${informePrevi.ofertaSeleccionada.cifEmpresa})
	</p>
	<p>
		<label>Amb valor:</label> ${informePrevi.ofertaSeleccionada.getPlicFormat()}
	</p>
	<p>
		<label>Termini:</label> ${informePrevi.ofertaSeleccionada.termini}
	</p>
	<p>
		<label>Tècnic:</label> ${informePrevi.usuari.getNomComplet()}
	</p>
	<p>
		<label>Proposta tècnica:</label> ${informePrevi.ofertaSeleccionada.comentari} 
	</p>
	<c:forEach items="${informePrevi.propostaTecnica}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
    </c:forEach>
    <br>
    <h4>Conforme Factura</h4>	
    <p>
		<label>Import factura:</label> ${factura.valor}
	</p>
	<p>
		<label>Concepte:</label> ${factura.concepte}
	</p>
	<p>
		<label>Proveïdor:</label> ${factura.nomProveidor}
	</p>						       	
	<p>
		<label>Factura: </label> 
		
		<c:set var="arxiu" value="${factura.factura}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		
 	</p>
 	<form class="form-horizontal" target="_blank" method="POST" enctype="multipart/form-data" action="DoAddPA"> 	
     	<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
		<input type="hidden" name="idIncidencia" value="${informePrevi.idIncidencia}">															
		<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
		<input type="hidden" name="idTasca" value="${tasca.idTasca}">		
		<input type="hidden" name="idFactura" value="${factura.idFactura}">		
		<input type="hidden" name="document" value="conformarFactura">															
		<div class="col-md-8">
			<div class="row margin_top10">
    			<div class="col-md-12">
           			Pujar factura conformada signada: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
    			</div>
    		</div>																													        			
    	</div>	
    	<div class="col-md-4">												        		
   			<div class="row">
	       		<div class="col-md-12">															        																						 				
			 		<input class="btn btn-success margin_top30 upload" type="submit" name="aprovarPD" value="Enviar factura conformada signada">
			 	</div>
    		</div>
   		</div>															     											    		
	</form>		        
</div>