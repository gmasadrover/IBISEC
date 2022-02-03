<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<p>
	<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra != 'conveni' && informePrevi.propostaInformeSeleccionada.tipusObra != 'acordMarc'}">
		<label>Documentació tècnica:</label>
	</c:if>
	<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == 'conveni' || informePrevi.propostaInformeSeleccionada.tipusObra == 'acordMarc'}">
		<label>Tramits:</label>
	</c:if>
</p>          			
<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentacioTecnica">
	<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
	<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
	<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
	<div class="documentacioTecnica"></div>
	<div class="row col-md-12">		
		<div class="docTecnica"></div>
		<br>					            		
	</div>
	<c:if test="${isIBISEC}">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	           <div class="col-xs-5">   
	           	<input type="file" class="btn" name="documentAltre" multiple/><br/>
			</div>				
		</div>
	</c:if>
	<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == null || informePrevi.propostaInformeSeleccionada.tipusObra == 'obr'}">		
		<div class="documentacioEBSS"></div>
		<div class="redaccioProjecteExtern"></div>
		<div class="estudiGeotecnic"></div>
		<div class="estudiTopografic"></div>
		<div class="projecteActivitat"></div>
		<div class="certificacioEnergetica"></div>
		<p>
			<label>Informe de supervisió:</label>
		</p>	
		<div class="row col-md-12">
			<div class="informeSupervisio"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentInformeSupervisio" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Projecte o DGE:</label>
		</p>	
		<div class="row col-md-12">
			<div class="projecte"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentProjecte" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Nomenament DF:</label>
		</p>	
		<div class="row col-md-12">
			<div class="nomenamentDF"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentNomenamentDF" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Pla Seguretat i Salut:</label>
		</p>	
		<div class="row col-md-12">
			<div class="documentPSS"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentPSS" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Pla de Gestió Residus:</label>
		</p>	
		<div class="row col-md-12">
			<div class="documentPGR"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentPGR" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Programa de treball:</label>
		</p>	
		<div class="row col-md-12">
			<div class="documentPlaTreball"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentPlaTreball" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Programa control de qualitat:</label>
		</p>	
		<div class="row col-md-12">
			<div class="documentControlQualitat"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentControlQualitat" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Comunicació finalització obres (Contratista):</label>
		</p>	
		<div class="row col-md-12">
			<div class="documentFinalitzacioContratista"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentFinalitzacioContratista" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Informe DF finalització obra:</label>
		</p>	
		<div class="row col-md-12">			
			<div class="documentInformeDO"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentInformeDO" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>CFO (Certificat final obra):</label>
		</p>	
		<div class="row col-md-12">			
			<div class="documentCFO"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentCFO" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Nomenament facultatiu per a recepció:</label>
		</p>	
		<div class="row col-md-12">
			<div class="documentRepresentacioRecepcio"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentRepresentacioRecepcio" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Informe certificació final:</label>
		</p>	
		<div class="row col-md-12">
			<div class="documentCertificacioFinal"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentCertificacioFinal" multiple/><br/>
				</div> 
			</div>
		</c:if>
		<p>
			<label>Informe devolució aval</label>
		</p>	
		<div class="row col-md-12">			
			<div class="documentDevolucioAval"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentDevolucioAval" multiple/><br/>
				</div> 
			</div>
		</c:if>	
		
		<p>
			<label>Certificats bona execució</label>
		</p>	
		<div class="row col-md-12">			
			<div class="documentCertificatsBonaExecucio"></div>
			<br>					            		
		</div>
		<c:if test="${isIBISEC}">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
		           <div class="col-xs-5">   
		           	<input type="file" class="btn" name="documentCertificatsBonaExecucio" multiple/><br/>
				</div> 
			</div>	
		</c:if>		
	</c:if>		
	<c:if test="${isIBISEC}">
		<div class="row">
			<div class="col-md-12">
				<div class="row">	  			
					<div class="col-md-offset-9 col-md-2 margin_top30">
						<input type="submit" class="btn btn-primary loadingButton" value="Actualitzar" />
					</div>
		    	</div>       
			</div>
		</div>  
	</c:if>       				
</form>	  