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
							<span class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">
							<c:forEach items="${informePrevi.memoriaOrdreInici.firmesList}" var="firma" >
								<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
								<br>
							</c:forEach>
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
							<span class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">
							<c:forEach items="${informePrevi.justProcForma.firmesList}" var="firma" >
								<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
								<br>
							</c:forEach>
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
							<span class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">
							<c:forEach items="${informePrevi.justCriterisAdjudicacio.firmesList}" var="firma" >
								<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
								<br>
							</c:forEach>
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
							<span class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">
							<c:forEach items="${informePrevi.declaracioUrgencia.firmesList}" var="firma" >
								<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
								<br>
							</c:forEach>
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
							<span class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">
							<c:forEach items="${informePrevi.aprovacioDispoTerrenys.firmesList}" var="firma" >
								<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
								<br>
							</c:forEach>
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
							<span class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">
							<c:forEach items="${informePrevi.aprovacioEXPPlecsDespesa.firmesList}" var="firma" >
								<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
								<br>
							</c:forEach>
						</div>
					</div>	
				</p>
			</c:if>
		</div>
		<c:if test="${informePrevi.llistaOfertes.size()>0}">
			<h4>Avaluació de criteris</h4>
			<c:set var="ofertes" scope="request" value="${informePrevi.llistaOfertes}"></c:set>
			<c:set var="ofertaSeleccionada" scope="request" value="${informePrevi.ofertaSeleccionada}"></c:set>								    					
 			<jsp:include page="../../tasca/include/_resumOfertes.jsp"></jsp:include>
 			<h4>Adjudicació</h4>
 			<div class="col-md-12">
  				<c:if test="${informePrevi.propostaTecnica.ruta != null}">													
              		<p>
              			<div class="document">
	               			<label>Proposta d'adjudicació signada:</label>											                  	
		           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaTecnica.getEncodedRuta()}">
								${informePrevi.propostaTecnica.nom}
							</a>	
							<c:if test="${informePrevi.propostaTecnica.signat}">
								<span class="glyphicon glyphicon-pencil signedFile"></span>
							</c:if>
							<br>
							<div class="infoSign hidden">
								<c:forEach items="${informePrevi.propostaTecnica.firmesList}" var="firma" >
									<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
									<br>
								</c:forEach>
							</div>
						</div>																				
					</p>
				</c:if>	
			</div>
			<div class="col-md-12">
				<c:if test="${informePrevi.autoritzacioPropostaDespesa.ruta != null}">
					<p>
	              		<div class="document">
	               			<label>Resolució d'adjudicació:</label>											                  	
		           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaDespesa.getEncodedRuta()}">
								${informePrevi.autoritzacioPropostaDespesa.nom}
							</a>	
							<c:if test="${informePrevi.autoritzacioPropostaDespesa.signat}">
								<span class="glyphicon glyphicon-pencil signedFile"></span>
							</c:if><br>
							<div class="infoSign hidden">
								<c:forEach items="${informePrevi.autoritzacioPropostaDespesa.firmesList}" var="firma" >
									<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
									<br>
								</c:forEach>
							</div>
						</div>	
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
								<span class="glyphicon glyphicon-pencil signedFile"></span>
							</c:if>							
							<br>
							<div class="infoSign hidden">
								<c:forEach items="${informePrevi.contracteSignat.firmesList}" var="firma" >
									<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
									<br>
								</c:forEach>
							</div>
						</div>
					</p>	
				</c:if>				
			</div>
 		</c:if>
	</div>
</c:if>	
<p>
	<label>Altre documentació licitació:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentsAltresLicitacio}" var="arxiu" >
		<div class="document">
			<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
			<c:if test="${arxiu.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<c:if test="${canModificarExpedient && arxiu.ruta != null}">
				<span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${arxiu.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
	<br>					            		
</div>
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
				<input type="submit" class="btn btn-primary" value="Pujar" />
			</div>    						
		</div>         				
	</form>							
</div>	
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