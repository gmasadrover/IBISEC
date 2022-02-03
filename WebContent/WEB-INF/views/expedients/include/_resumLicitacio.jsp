<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.ruta != null || informePrevi.llistaOfertes.size()>0 || informePrevi.memoriaOrdreInici.ruta != null}">
   	<div class="panel-body">   		
  		<h4>Preparacio Licitació</h4>
  		<div class="col-md-12">   			
			<c:if test="${informePrevi.memoriaOrdreInici.ruta != null}">
				<p>
              		<div class="document">
               			<label>Memòria més ordre d'inici signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.memoriaOrdreInici.getEncodedRuta()}">
							${informePrevi.memoriaOrdreInici.nom}
						</a>	
						<c:if test="${informePrevi.memoriaOrdreInici.signat}">
							<span data-ruta="${informePrevi.memoriaOrdreInici.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">							
						</div>
					</div>	
				</p>
			</c:if>
		</div>
		<div class="col-md-12">
			<c:if test="${informePrevi.justProcForma.ruta != null}">
				<p>
              		<div class="document">
               			<label>Justificació de procediment signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.justProcForma.getEncodedRuta()}">
							${informePrevi.justProcForma.nom}
						</a>	
						<c:if test="${informePrevi.justProcForma.signat}">
							<span data-ruta="${informePrevi.justProcForma.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">							
						</div>
					</div>	
				</p>
			</c:if>
		</div>
		<div class="col-md-12">
			<c:if test="${informePrevi.justCriterisAdjudicacio.ruta != null}">
				<p>
              		<div class="document">
               			<label>Justificació criteris d'adjudicació, condicions d'aptitud licitador i condicions especials signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.justCriterisAdjudicacio.getEncodedRuta()}">
							${informePrevi.justCriterisAdjudicacio.nom}
						</a>	
						<c:if test="${informePrevi.justCriterisAdjudicacio.signat}">
							<span data-ruta="${informePrevi.justCriterisAdjudicacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">							
						</div>
					</div>	
				</p>
			</c:if>
		</div>
		<div class="col-md-12">
			<c:if test="${informePrevi.declaracioUrgencia.ruta != null}">
				<p>
              		<div class="document">
               			<label>Declaració d'urgència signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.declaracioUrgencia.getEncodedRuta()}">
							${informePrevi.declaracioUrgencia.nom}
						</a>	
						<c:if test="${informePrevi.declaracioUrgencia.signat}">
							<span data-ruta="${informePrevi.declaracioUrgencia.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">							
						</div>
					</div>	
				</p>
			</c:if>
		</div>
		<div class="col-md-12">
			<c:if test="${informePrevi.aprovacioDispoTerrenys.ruta != null}">
				<p>
              		<div class="document">
               			<label>Resolució d'aprovació del projecte amb indicació de disponibilitat dels terrenys signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.aprovacioDispoTerrenys.getEncodedRuta()}">
							${informePrevi.aprovacioDispoTerrenys.nom}
						</a>	
						<c:if test="${informePrevi.aprovacioDispoTerrenys.signat}">
							<span data-ruta="${informePrevi.aprovacioDispoTerrenys.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">							
						</div>
					</div>	
				</p>
			</c:if>
		</div>
		<div class="col-md-12">
			<c:if test="${informePrevi.aprovacioEXPPlecsDespesa.ruta != null}">
				<p>
              		<div class="document">
               			<label>Aprovació expedient, plecs i despesa signada:</label>											                  	
	           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.aprovacioEXPPlecsDespesa.getEncodedRuta()}">
							${informePrevi.aprovacioEXPPlecsDespesa.nom}
						</a>	
						<c:if test="${informePrevi.aprovacioEXPPlecsDespesa.signat}">
							<span data-ruta="${informePrevi.aprovacioEXPPlecsDespesa.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">							
						</div>
					</div>	
				</p>
			</c:if>
		</div>
		<h4>Publicació</h4>		
		<div class="col-md-12">
			<p>			                     				
				<label>Data publicació:</label>${informePrevi.expcontratacio.getDataPublicacioPerfilContratantString()}
			</p>
		</div>
		<br>
		<c:if test="${informePrevi.llistaOfertes.size()>0}">
			<h4>Avaluació de criteris</h4>
			<c:set var="ofertes" scope="request" value="${informePrevi.llistaOfertes}"></c:set>
			<c:set var="ofertaSeleccionada" scope="request" value="${informePrevi.ofertaSeleccionada}"></c:set>								    					
 			<jsp:include page="../../tasca/include/_resumOfertes.jsp"></jsp:include> 		
 		</c:if>
		<h4>Adjudicació</h4>
			<div class="col-md-12">
				<c:if test="${informePrevi.resolucioVAD.size() > 0}">
 				<label>Resolucions VAD:</label>	
  				<c:forEach items="${informePrevi.resolucioVAD}" var="arxiu" >
  					<c:set var="arxiu" value="${arxiu}" scope="request"/>														
              		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
				</c:forEach>
			</c:if>
		</div>
		<div class="col-md-12">
			<c:if test="${informePrevi.resolucioVAD.size() > 0}">		
				<label>Resolució VAD:</label>
  				<c:forEach items="${informePrevi.resolucioVAD}" var="arxiu" >	
  					<c:set var="arxiu" value="${arxiu}" scope="request"/>															
              		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
				</c:forEach>
			</c:if>
		</div>
		<div class="col-md-12">
			<c:if test="${informePrevi.ratificacioClassificacio.size() > 0}">		
				<label>Ratificació classificació:</label>
  				<c:forEach items="${informePrevi.ratificacioClassificacio}" var="arxiu" >
  					<c:set var="arxiu" value="${arxiu}" scope="request"/>																
              		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
				</c:forEach>
			</c:if>
		</div>
		<div class="col-md-12">
			<c:forEach items="${informePrevi.propostaTecnica}" var="arxiu" >	
				<c:set var="arxiu" value="${arxiu}" scope="request"/>											
           		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
			</c:forEach>
		</div>
		<div class="col-md-12">
			<c:if test="${informePrevi.autoritzacioPropostaDespesa != null}">
				<h4>Resolució d'adjudicació:</h4>		
				<c:if test="${informePrevi.expcontratacio.getDataAdjudicacioString() != null && informePrevi.expcontratacio.getDataAdjudicacioString() != ''}">	
					<div class="col-md-12">
						<p>			                     				
							<label>Data adjudicació:</label>${informePrevi.expcontratacio.getDataAdjudicacioString()}
						</p>
					</div>
				</c:if>
				<p>
					<c:forEach items="${informePrevi.autoritzacioPropostaDespesa}" var="arxiu" >
	  					<c:set var="arxiu" value="${arxiu}" scope="request"/>																
	              		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>              		
				</p>
			</c:if>
		</div>
		<h4>Formalització contracte</h4>
		<br/>
		<c:if test="${informePrevi.expcontratacio.getDataFirmaString() != null && informePrevi.expcontratacio.getDataFirmaString() != ''}">	
			<div class="col-md-12">
				<p>			                     				
					<label>Data formalització:</label>${informePrevi.expcontratacio.getDataFirmaString()}
				</p>
			</div>
		</c:if>
		<div class="col-md-12">
			<c:if test="${informePrevi.contracteSignat.ruta != null}">
              	<p>
              		<div class="document">
	               		<label>Contracte signat:</label>											                  	
		           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.contracteSignat.getEncodedRuta()}">
							${informePrevi.contracteSignat.nom}
						</a>	
						<c:if test="${informePrevi.contracteSignat.signat}">
							<span data-ruta="${informePrevi.contracteSignat.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if>							
						<br>
						<div class="infoSign hidden">								
						</div>
					</div>
				</p>	
			</c:if>				
		</div>
	</div>
</c:if>	
<p>
	<label>Altre documentació licitació:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentsAltresLicitacio}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
	</c:forEach>
	<br>					            		
</div>
<c:if test="${isIBISEC}">
	<div class="row">            			
		<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentsAltresLicitacio">
			<div class="form-group">
				<label class="col-xs-2 control-label">Adjuntar arxius:</label>
	            <div class="col-xs-5">   
	            	<input type="file" class="btn" name="file" multiple/><br/>
				</div> 
				<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
				<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
				<input type="hidden" name="idInforme" value="${informePrevi.idInf}">			    
				<div class="col-xs-2"> 
					<input type="submit" class="btn btn-primary loadingButton" value="Pujar" />
				</div>    						
			</div>         				
		</form>							
	</div>
</c:if>	
<div class="row">
	<div class="col-md-12">
		<div class="row">
  			<c:if test="${canModificarExpedient}">
				<div class="col-md-offset-9 col-md-2 margin_top30">
					<a href="editLicitacio?${informePrevi.expcontratacio.expContratacio != '-1' ? 'ref=' += informePrevi.expcontratacio.expContratacio : 'idinf=' += informePrevi.idInf}&from=${redireccio}" class="btn btn-primary" role="button">Editar</a>
				</div>
			</c:if>
    	</div>       
	</div>
</div>