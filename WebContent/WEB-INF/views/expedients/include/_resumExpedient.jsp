<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<h4>Resum</h4>	
<br />
<div class="col-md-6"> 
	<p>
		<label>Estat:</label> ${informePrevi.getEstatExpedientFormat()}
	</p>
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
		<label>Partida:</label> <a target="_black" href="partidaDetalls?codi=${informePrevi.assignacioCredit.partida.codi}">${informePrevi.assignacioCredit.partida.codi} (${informePrevi.assignacioCredit.partida.nom})</a>
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
	<c:if test="${informePrevi.expcontratacio.contracte == 'major'}">
		<p>
			<label>Import certificat:</label> ${informePrevi.getTotalCertificatFormat()}
		</p>
	</c:if>
	<p>
		<label>Import facturat:</label> ${informePrevi.getTotalFacturatFormat()}
	</p>
	<c:if test="${informePrevi.estat != 'garantia'}">
		<p>
			<label>Romanent:</label> ${modificacions + informePrevi.ofertaSeleccionada.plic - informePrevi.getTotalFacturat()}€
		</p>
	</c:if>
</div>
<div class="col-md-6">
    <p> 
    	<label>Data publicació perfil contratant: </label> ${informePrevi.expcontratacio.getDataPublicacioPerfilContratantString()}
    </p>
  
    <p> 
    	<label>Termini d'execució: </label> ${informePrevi.ofertaSeleccionada.getTermini()}
    </p>
    <p> 
    	<label>Data Inici obra: </label> ${informePrevi.expcontratacio.getDataIniciObratring()}
    </p>
    <p> 
    	<label>Duració de la garantia: </label> ${informePrevi.expcontratacio.garantia}
    </p>
    <p> 
    	<label>Data liquidació obra: </label> ${informePrevi.expcontratacio.getDataLiquidacioString()}
    </p> 
    <c:if test="${informePrevi.expcontratacio.contracte=='major'}">
	    <p> 
	    	<label>Data límit presentació ofertes: </label> ${informePrevi.expcontratacio.getDataLimitPresentacioString()}
	    </p>
    </c:if>    
    <p> 
    	<label>Data Recepció obra: </label> ${informePrevi.expcontratacio.getDataRecepcioString()}
    </p>
    <p> 
    	<label>Data retorn garantia: </label> ${informePrevi.expcontratacio.getDataRetornGarantiaString()}
   </p> 
</div>  
<div class="col-md-offset-10 col-md-2">
	<form class="form-horizontal" method="POST" action="doDownloadExpedient">
		<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
		<div class="form-group">					  				 
		  	<div class="col-md-2">
		    	<input type="submit" class="btn btn-primary margin_top30"  value="Descarregar">
			</div>
		</div>	
	</form>
</div>