<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<h4>Resum</h4>	
<br />
<p>	
	<div class="document">
		<label>Data PA autoritzat:</label> ${informePrevi.getDataAprovacioString()}
		<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.ruta != null}">
			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaAutoritzacio.getEncodedRuta()}">
				${informePrevi.autoritzacioPropostaAutoritzacio.nom}
			</a>	
			<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${informePrevi.autoritzacioPropostaAutoritzacio.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>
		</c:if>	
	</div>
</p>
<p>
	<label>Import PA:</label> ${informePrevi.propostaInformeSeleccionada.plic}€
</p>
<p>
	<label>Autorització urbanística:</label> ${informePrevi.propostaInformeSeleccionada.llicencia ? "Si" : "No"}
</p>													
<p>
	<label>Partida:</label> <a target="_black" href="partidaDetalls?codi=${informePrevi.codiPartida}">${informePrevi.codiPartida} (${informePrevi.partida})</a>
</p>
<p>
	<div class="document">
		<label>Data adjudicació:</label> ${informePrevi.getDataPDString()}
		<c:if test="${informePrevi.autoritzacioPropostaDespesa.ruta != null}">
			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaDespesa.getEncodedRuta()}">
				${informePrevi.autoritzacioPropostaDespesa.nom}
			</a>	
			<c:if test="${informePrevi.autoritzacioPropostaDespesa.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${informePrevi.autoritzacioPropostaDespesa.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>
		</c:if>	
	</div>
</p>
<p>
	<label>Import adjudicació:</label> ${informePrevi.ofertaSeleccionada.plic}€
</p>
<c:set var="modificacions" value="0" /> 
<c:forEach items="${informePrevi.llistaModificacions}" var="modificacio" >
	<c:if test="${modificacio.autoritzacioPropostaDespesa.ruta != null}">				
		<p>
			<label>Modificació:</label> ${modificacio.ofertaSeleccionada.plic}€
			<c:set var="modificacions" value="${modificacions + modificacio.ofertaSeleccionada.plic}" />
		</p>
	</c:if>
</c:forEach>
<p>
	<label>Total expedient:</label> ${informePrevi.ofertaSeleccionada.plic + modificacions}€
</p>
<p>
	<label>Import facturat:</label> ${informePrevi.getTotalFacturat()}€
</p>
<p>
	<label>Romanent:</label> ${modificacions + informePrevi.ofertaSeleccionada.plic - informePrevi.getTotalFacturat()}€
</p>
<p>
	<label>Data recepció:</label> ${informePrevi.getDataRecepcioString()}
</p>