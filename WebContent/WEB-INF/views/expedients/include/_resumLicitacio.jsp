<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.ruta != null || informePrevi.llistaOfertes.size()>0}">
   	<div class="panel-body">
		<c:if test="${informePrevi.llistaOfertes.size()>0}">
			<c:set var="ofertes" scope="request" value="${informePrevi.llistaOfertes}"></c:set>
			<c:set var="ofertaSeleccionada" scope="request" value="${informePrevi.ofertaSeleccionada}"></c:set>								    					
 			<jsp:include page="../../tasca/include/_resumOfertes.jsp"></jsp:include>
 			<div class="col-md-12">
  				<c:if test="${informePrevi.propostaTecnica.ruta != null}">													
              		<p>
              			<div class="document">
	               			<label>Proposta tècnica signada:</label>											                  	
		           			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaTecnica.getEncodedRuta()}">
								${informePrevi.propostaTecnica.nom}
							</a>	
							<c:if test="${informePrevi.propostaTecnica.signat}">
								<span class="glyphicon glyphicon-pencil signedFile"></span>
							</c:if>
	<%-- 																	<span data-ruta="${informePrevi.propostaTecnica.ruta}" class="glyphicon glyphicon-remove deleteFile"></span> --%>
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
	               			<label>Autorització proposta despesa:</label>											                  	
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
							<c:if test="${canModificarExpedient && informePrevi.contracteSignat.ruta != null}">
								<span data-ruta="${informePrevi.contracteSignat.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
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
				<c:if test="${informePrevi.contracteSignat.ruta == null}">
		           	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA">
				     	<input type="hidden" name="document" value="contracteSignat">
						<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
						<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">															
						<input type="hidden" name="idInforme" value="${informePrevi.idInf}">																		
						<div class="col-md-8">
							<div class="row margin_top10">
				    			<div class="col-md-12">
				           			Contracte signat: <input type="file" class="btn uploadImage" name="contracte" /><br/>																 		
				    			</div>
				    		</div>																													        			
			      		</div>	
			      		<div class="col-md-4">												        		
				    		<div class="row">
				        		<div class="col-md-12">															        																						 				
							 		<input class="btn btn-success margin_top30 upload" type="submit" name="guardar" value="Pujar contracte signat">
							 	</div>
				     		</div>
			     		</div>															     											    		
			  		</form>									            	
	           	</c:if>	
			</div>
 		</c:if>
	</div>
</c:if>		
<div class="row">
	<div class="col-md-12">
		<div class="row">
  			<c:if test="${canModificarExpedient}">
				<div class="col-md-offset-9 col-md-2 margin_top30">
					<a href="editLicitacio?${informePrevi.expcontratacio.expContratacio != '-1' ? 'ref=' += informePrevi.expcontratacio.expContratacio : 'idinf=' += informePrevi.idInf}&from=${redireccio}" class="btn btn-primary" role="button">Modificar</a>
				</div>
			</c:if>
    	</div>       
	</div>
</div>