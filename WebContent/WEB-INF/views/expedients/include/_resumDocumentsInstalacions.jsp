<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentacioInstalacions">	
	<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
	<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
	<input type="hidden" name="idInforme" value="${informePrevi.idInf}">			    
	<div class="documentacioInstalacions"></div>
	<p>
		<label>Documentació Instal·lació baixa tensió:</label>
	</p>
	<div class="row col-md-12">
		<div class="col-md-6">
			<p>
				<label>Nº Expedient:</label> ${informePrevi.instalacions.expedientBaixaTensio}
			</p>
		</div>
		<div class="col-md-6">
			<p>
				<label>Data OCA:</label> ${informePrevi.instalacions.getDataOCABaixaTensioString()}
			</p>
		</div>
	</div>	
	<div class="row col-md-12">
		<div class="documentsInstalacioBaixaTensio"></div>
		<br>					            		
	</div>
	<p>
		<label>Documentació Instal·lació fotovoltàica:</label>
	</p>
	<div class="row col-md-12">
		<div class="col-md-6">
			<p>
				<label>Nº Expedient:</label> ${informePrevi.instalacions.expedientFotovoltaica}
			</p>
		</div>
		<div class="col-md-6">
			<p>
				<label>Data OCA:</label> ${informePrevi.instalacions.getDataOCAFotovoltaicaString()}
			</p>
		</div>
	</div>	
	<div class="row col-md-12">
		<div class="documentsInstalacioFotovoltaica"></div>
		<br>					            		
	</div>
	<p>
		<label>Documentació Instal·lació contraincendis:</label>
	</p>
	<div class="row col-md-12">
		<div class="col-md-6">
			<p>
				<label>Nº Expedient:</label> ${informePrevi.instalacions.expedientContraincendis}
			</p>
		</div>		
	</div>		
	<div class="row col-md-12">
		<div class="documentsInstalacioContraincendis"></div>
		<br>					            		
	</div>
	<p>
		<label>Documentació Instal·lació de productes petrolífers:</label>
	</p>	
	<div class="row col-md-12">
		<div class="col-md-6">
			<p>
				<label>Nº Expedient:</label> ${informePrevi.instalacions.expedientPetrolifers}
			</p>
		</div>	
		<div class="col-md-6">
			<p>
				<label>Data:</label> ${informePrevi.instalacions.getDataPetrolifersString()}
			</p>
		</div>		
	</div>
	<div class="row col-md-12">
		<div class="col-md-6">
			<p>
				<label>Nº Instal·lació:</label> ${informePrevi.instalacions.instalacioPetrolifers}
			</p>
		</div>	
		<div class="col-md-3">
			<p>
				<label>Num diposits:</label> ${informePrevi.instalacions.dipositsPetrolifers}
			</p>
		</div>	
		<div class="col-md-3">
			<p>
				<label>Capacitat total:</label> ${informePrevi.instalacions.capTotalPetrolifers}
			</p>
		</div>		
	</div>	
	<div class="row col-md-12">
		<div class="documentsInstalacioPetrolifera"></div>
		<br>					            		
	</div>		
	<p>
		<label>Documentació Instal·lació Gas:</label>
	</p>	
	<div class="row col-md-12">
		<div class="col-md-4">
			<p>
				<label>Nº Expedient:</label> ${informePrevi.instalacions.expedientInstalacioGas}
			</p>
		</div>	
		<div class="col-md-4">
			<p>
				<label>Tipus de gas:</label> ${informePrevi.instalacions.tipusInstalacioGas}
			</p>
		</div>	
		<div class="col-md-4">
			<p>
				<label>Data:</label> ${informePrevi.instalacions.getDataInstalacioGasString()}
			</p>
		</div>		
	</div>	
	<div class="row col-md-12">
		<div class="documentsInstalacioGas"></div>
		<br>					            		
	</div>	
	<p>
		<label>Documentació Instal·lació tèrmica:</label>
	</p>	
	<div class="row col-md-12">
		<div class="col-md-6">
			<p>
				<label>Nº Expedient:</label> ${informePrevi.instalacions.expedientTermiques}
			</p>
		</div>		
	</div>	
	<div class="row col-md-12">
		<div class="documentsInstalacioTermica"></div>
		<br>					            		
	</div>	
	<p>
		<label>Documentació Instal·lació ascensor:</label>
	</p>
	<div class="row col-md-12">
		<div class="col-md-6">
			<p>
				<label>Nº Expedient:</label> ${informePrevi.instalacions.expedientAscensor}
			</p>
		</div>		
	</div>		
	<div class="row col-md-12">
		<div class="documentsInstalacioAscensor"></div>
		<br>					            		
	</div>
	<p>
		<label>Documentació Instal·lació alarma:</label>
	</p>	
	<div class="row col-md-12">
		<div class="documentsInstalacioAlarma"></div>
		<br>					            		
	</div>
	<p>
		<label>Documentació Instal·lació subministrament aigua:</label>
	</p>	
	<div class="row col-md-12">
		<div class="documentsInstalacioSubministreAigua"></div>
		<br>					            		
	</div>
	<p>
		<label>Documentació Certificat Eficiència Energètica:</label>
	</p>	
	<div class="row col-md-12">
		<div class="col-md-6">
			<p>
				<label>Nº Expedient:</label> ${informePrevi.instalacions.expedientEficienciaEnergetica}
			</p>
		</div>
		<div class="col-md-6">
			<p>
				<label>Data:</label> ${informePrevi.instalacions.getDataRegistreEficienciaEnergeticaString()}
			</p>
		</div>
	</div>	
	<div class="row col-md-12">
		<div class="documentsCertificatEficienciaEnergetica"></div>
		<br>					            		
	</div>	
	<p>
		<label>Documentació Instal·lació Pla Autoprotecció:</label>
	</p>	
	<div class="row col-md-12">
		<div class="col-md-6">
			<p>
				<label>Nº Expedient:</label> ${informePrevi.instalacions.expedientPlaAutoproteccio}
			</p>
		</div>		
	</div>		
	<div class="row col-md-12">
		<div class="documentsPlaAutoproteccio"></div>
		<br>					            		
	</div>
	<p>
		<label>Documentació Cedula d'habitabilitat:</label>
	</p>	
	<div class="row col-md-12">		
		<div class="col-md-6">
			<p>
				<label>Data:</label> ${informePrevi.instalacions.getDataCedulaHabitabilitatString()}
			</p>
		</div>
	</div>	
	<div class="row col-md-12">
		<div class="documentsCedulaDeHabitabilitat"></div>
		<br>					            		
	</div>
	<p>
		<label>Documentació incici activitat:</label>
	</p>
	<div class="row col-md-12">
		<div class="documentsIniciActivitat"></div>
		<br>					            		
	</div>	
	<c:if test="${isIBISEC}">
		<div class="row">
			<div class="col-md-12">
				<div class="row">	  			
					<div class="col-md-offset-9 col-md-2 margin_top30">
						<a href="editInstalacions?${informePrevi.expcontratacio.expContratacio != '-1' ? 'ref=' += informePrevi.expcontratacio.expContratacio : 'idinf=' += informePrevi.idInf}&from=${redireccio}" class="btn btn-primary" role="button">Editar</a>
					</div>
		    	</div>       
			</div>
		</div>
	</c:if>
</form>	  