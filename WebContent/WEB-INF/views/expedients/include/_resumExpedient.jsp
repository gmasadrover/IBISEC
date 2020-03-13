<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
		<label>Data PA autoritzat:</label> ${informePrevi.getDataAprovacioString()}
	</p>
	<p>
		<label>Import PA:</label> ${informePrevi.propostaInformeSeleccionada.plic}€
	</p>
	<p>
		<label>Autorització urbanística:</label> ${informePrevi.propostaInformeSeleccionada.llicencia ? "Si" : "No"}
	</p>													
	<p>
		<label>Partida:</label> 
		<c:forEach items="${informePrevi.assignacioCredit}" var="assignacioCredit" >
			<a target="_black" href="partidaDetalls?codi=${assignacioCredit.partida.codi}">${assignacioCredit.partida.codi} (${assignacioCredit.partida.nom})</a><br/>
		</c:forEach>		
	</p>
	<p>
		<label>Data adjudicació:</label> ${informePrevi.getDataPDString()}
	</p>
	<p>
		<label>Import adjudicació:</label> ${informePrevi.ofertaSeleccionada.plic}€
	</p>
	<c:set var="modificacions" value="0" /> 
	<c:forEach items="${informePrevi.llistaModificacions}" var="modificacio" >
		<c:if test="${!modificacio.anulat}">
			<c:if test="${modificacio.autoritzacioPropostaDespesa.size() > 0 || modificacio.formalitzacioSignat.ruta != null}">				
				<p>
					<label>Modificació:</label> ${modificacio.ofertaSeleccionada.plic}€
					<c:set var="modificacions" value="${modificacions + modificacio.ofertaSeleccionada.plic}" />
				</p>
			</c:if>
		</c:if>		
	</c:forEach>
	<p>
		<label>Total expedient:</label> ${informePrevi.ofertaSeleccionada.plic + modificacions}€
	</p>
	<c:set var="penalitzacions" value="0" /> 
	<c:forEach items="${informePrevi.llistaPenalitzacions}" var="penalitzacio" >
		<c:if test="${!penalitzacio.propostaInformeSeleccionada.retencio}">
			<c:if test="${penalitzacio.autoritzacioPropostaDespesa.size() > 0 || penalitzacio.formalitzacioSignat.ruta != null}">				
				<c:set var="penalitzacions" value="${penalitzacions + penalitzacio.ofertaSeleccionada.plic}" />			
			</c:if>
		</c:if>		
	</c:forEach>
	<c:if test="${penalitzacions < 0}">
		<p>
			<label>Total penalitzacions:</label> ${penalitzacions}€
		</p>
	</c:if>	
	<c:set var="retencions" value="0" /> 
	<c:forEach items="${informePrevi.llistaPenalitzacions}" var="penalitzacio" >
		<c:if test="${penalitzacio.propostaInformeSeleccionada.retencio}">
			<c:if test="${penalitzacio.autoritzacioPropostaDespesa.size() > 0 || penalitzacio.formalitzacioSignat.ruta != null}">				
				<c:set var="retencions" value="${retencions + penalitzacio.ofertaSeleccionada.plic}" />			
			</c:if>
		</c:if>		
	</c:forEach>
	<c:if test="${retencions < 0}">
		<p>
			<label>Total retencions:</label> ${retencions}€
		</p>
	</c:if>	
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
			<label>Romanent:</label> ${modificacions + informePrevi.ofertaSeleccionada.plic - informePrevi.getTotalFacturat() + penalitzacions}€
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