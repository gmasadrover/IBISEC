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
				<label>Data:</label> ${propostaModificacio.getDataCreacioString()}
			</p>				
			<div class="row">				
				<div class="col-md-4">
					<p>
						<label>Valor:</label> ${propostaModificacio.ofertaSeleccionada.plic}€
					</p>
			   	</div>					  
			</div>
			<div class="row">				
				<div class="col-md-4">
					<c:choose>
						<c:when test="${propostaModificacio.propostaInformeSeleccionada.retencio}">
							<p>
								<label>Tramitació:</label> Retenció
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<label>Tramitació:</label> Execució
							</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<p>
				<label>Data:</label> ${propostaModificacio.getDataCreacioString()}
			</p>
			<p>			                     				
				<label>Comentari tècnic:</label> ${propostaModificacio.ofertaSeleccionada.comentari}
			</p>
			<p>			                     				
				<label>Informe:</label>
			</p>			
			<c:forEach items="${propostaModificacio.propostaTecnica}" var="arxiu" >	
				<c:set var="arxiu" value="${arxiu}" scope="request"/>
				<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
			</c:forEach>
			<p></p>		
			<p>			                     				
				<label>Autorització:</label>
			</p>
			<c:forEach items="${propostaModificacio.autoritzacioPropostaDespesa}" var="arxiu" >	
				<c:set var="arxiu" value="${arxiu}" scope="request"/>
				<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
			</c:forEach>
			<p></p>		
			<label>Tramits:</label>	
			<c:forEach items="${propostaModificacio.tramitsModificacio}" var="arxiu" >		
				<c:set var="arxiu" value="${arxiu}" scope="request"/>
				<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
			</c:forEach>	
			<p></p>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="row">
		  			<c:if test="${true}">
						<div class="col-md-offset-7 col-md-2 margin_top30">
							<a href="editPenalitzacioInforme?idMod=${propostaModificacio.idInf}&idinf=${informePrevi.idInf}" class="btn btn-primary" role="button">Editar</a>
						</div>	
					</c:if>
				</div>
			</div>
		</div>	