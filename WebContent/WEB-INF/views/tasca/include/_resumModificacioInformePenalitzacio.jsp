<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		

		<div class="panel-body">	
			<p>			                     				
				<label>Tipus:</label> ${propostaModificacio.getTipusModificacioFormat()}
			</p>			      	
			<p>			                     				
				<label>Objecte:</label> ${propostaModificacio.propostaInformeSeleccionada.objecte}
			</p>
			<p>
				<label>Tècnic:</label> ${propostaModificacio.usuari.getNomComplet()}
			</p>	
			<p>
				<label>Data:</label> ${propostaModificacio.getDataCreacioString()}
			</p>				
			<div class="row">				
				<div class="col-md-4">
					<p>
						<label>Plic:</label> ${propostaModificacio.ofertaSeleccionada.plic}€
					</p>
			   	</div>					  
			</div>
			<p>			                     				
				<label>Comentari tècnic:</label> ${propostaModificacio.ofertaSeleccionada.comentari}
			</p>			
			<c:forEach items="${propostaModificacio.propostaTecnica}" var="arxiu" >	
				<c:set var="arxiu" value="${arxiu}" scope="request"/>
				<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
			</c:forEach>
			<p></p>			
			<c:if test="${propostaModificacio.autoritzacioPropostaDespesa.ruta != null}">		
				<div class="document">			
	               	<label>Autorització proposta despesa:</label>													                  	
	           		<a target="_blanck" href="downloadFichero?ruta=${propostaModificacio.autoritzacioPropostaDespesa.getEncodedRuta()}">
						${propostaModificacio.autoritzacioPropostaDespesa.nom}
					</a>
					<c:if test="${propostaModificacio.autoritzacioPropostaDespesa.signat}">
							<span data-ruta="${propostaModificacio.autoritzacioPropostaDespesa.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
					</c:if><br>
					<div class="infoSign hidden">
						<c:forEach items="${propostaModificacio.autoritzacioPropostaDespesa.firmesList}" var="firma" >
							<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
							<br>
						</c:forEach>
					</div>
				</div>
			</c:if>		
			<p></p>
		</div>
	