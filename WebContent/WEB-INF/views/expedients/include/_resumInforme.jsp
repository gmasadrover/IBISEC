<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<h4>Informe inicial</h4>	
<br />
<p>
	<label>Informe:</label> ${informePrevi.idInf}
</p>
<p>
	<label>Expedient:</label> ${informePrevi.expcontratacio.expContratacio}
</p>
<p>
	<label>Tècnic:</label> ${informePrevi.usuari.getNomComplet()}
</p>
<p>
	<label>Data:</label> ${informePrevi.getDataCreacioString()}
</p>
<div class="col-md-12 panel-group" id="accordionPropostes${informePrevi.idInf}">
	<div class="tabbable">
       	<ul class="nav nav-tabs">
       		<c:set var="numPA" value="1" scope="request" />	
       		<c:forEach items="${informePrevi.llistaPropostes}" var="propostaActuacio" >
       			<li ${propostaActuacio.seleccionada ? 'class="active"' : ''}><a data-toggle="tab" href="#propostaActuacio_${informePrevi.idInf}_${numPA}">Proposta actuació ${numPA}</a></li>
       			<c:set var="numPA" value="${numPA + 1}" scope="request"/>
       		</c:forEach>					   
	 	</ul>
	  	<div class="tab-content">
	  		<c:set var="numPA" value="1" scope="request" />		
	  		<c:forEach items="${informePrevi.llistaPropostes}" var="propostaActuacio" >  
		  		<div id="propostaActuacio_${informePrevi.idInf}_${numPA}" class="tab-pane fade ${propostaActuacio.seleccionada ? 'in active' : ''}">
		  		 	<div class="col-md-12 bordertab">
		  		 		<c:set var="informePrevi" value="${informePrevi}" scope="request"/>
			    		<c:set var="propostaActuacio" value="${propostaActuacio}" scope="request"/> 	
				    	<jsp:include page="_resumProposta.jsp"></jsp:include>
				    	<c:set var="numPA" value="${numPA + 1}" scope="request"/>						
		  		 	</div>
		  		 </div>
	  		 </c:forEach>
	  	</div>
	</div>
</div>														
<p>
	<label>Arxius adjunts:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.adjunts}" var="arxiu" >
		<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.nom}</a>
		<br>
	</c:forEach>					            		
</div>
<p>			                     				
	<label>Notes:</label> ${informePrevi.notes}
</p>
<c:if test="${informePrevi.propostaActuacio.ruta != null}">															
            	<p>
            		<div class="document">
             		<label>Proposta d'actuació signada:	</label>											                  	
          		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaActuacio.getEncodedRuta()}">
				${informePrevi.propostaActuacio.nom}
			</a>	
			<c:if test="${informePrevi.propostaActuacio.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
<%-- 																	<span data-ruta="${informePrevi.propostaActuacio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span> --%>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${informePrevi.propostaActuacio.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>
		</div>																				
	</p>	
</c:if>	
<c:if test="${informePrevi.propostaActuacio.ruta != null || informePrevi.usuariCapValidacio != null}">		
	<p>
		<label>Comentari Cap:</label> ${informePrevi.comentariCap}
	</p>
	<p>
		<label>Vistiplau:</label> ${informePrevi.usuariCapValidacio.getNomComplet()} - ${informePrevi.getDataCapValidacioString()}
	</p>															
	<c:if test="${informePrevi.vistiplauPropostaActuacio.ruta != null}">
		<p>
			<div class="document">
              		<label>Vistiplau proposta d'actuació signada:</label>													               
				<a target="_blanck" href="downloadFichero?ruta=${informePrevi.vistiplauPropostaActuacio.getEncodedRuta()}">
					${informePrevi.vistiplauPropostaActuacio.nom}
				</a>
				<c:if test="${informePrevi.vistiplauPropostaActuacio.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
				</c:if>
<%-- 																	<span data-ruta="${informePrevi.vistiplauPropostaActuacio.ruta}" class="glyphicon glyphicon-remove deleteFile"></span> --%>
				<br>
				<div class="infoSign hidden">
					<c:forEach items="${informePrevi.vistiplauPropostaActuacio.firmesList}" var="firma" >
						<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
						<br>
					</c:forEach>
				</div>
			</div>																	
		</p>
	</c:if>	
</c:if>
<c:if test="${informePrevi.vistiplauPropostaActuacio.ruta != null || partida != ''}">
	<p>
		<label>Partida:</label>
		<c:choose>
			<c:when test="${informePrevi.dataRebujada != null}">
				${informePrevi.partidaRebutjadaMotiu}
			</c:when>
			<c:otherwise>
				<a target="_black" href="partidaDetalls?codi=${informePrevi.codiPartida}">${informePrevi.codiPartida} (${informePrevi.partida})</a>
			</c:otherwise>
		</c:choose> 
	</p>
</c:if>
<c:if test="${informePrevi.informeSupervisio.ruta != null}">
	<p>
		<div class="document">
			<label>Informe supervisió / contractació:</label>										                  	
          		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.informeSupervisio.getEncodedRuta()}">
				${informePrevi.informeSupervisio.nom}
			</a>	
			<c:if test="${informePrevi.informeSupervisio.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if><br>
			<div class="infoSign hidden">
				<c:forEach items="${informePrevi.informeSupervisio.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>	
		</div>																	
	</p>	
</c:if>	
<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.ruta != null}">
	<p>
		<div class="document">
			<label>Autorització àrea econòmica signada:</label>										                  	
          		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.conformeAreaEconomivaPropostaActuacio.getEncodedRuta()}">
				${informePrevi.conformeAreaEconomivaPropostaActuacio.nom}
			</a>	
			<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if><br>
			<div class="infoSign hidden">
				<c:forEach items="${informePrevi.conformeAreaEconomivaPropostaActuacio.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>	
		</div>																	
	</p>	
</c:if>	
<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.ruta != null}">
	<p>
		<div class="document">
			<label>Autorització proposta d'actuació signada:</label>										                  	
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
		</div>
	</p>
</c:if>	
<c:if test="${informePrevi.autoritzacioConsellDeGovern.ruta != null}">
	<p>
		<div class="document">
			<label>Autorització Consell De Govern:</label>										                  	
	         		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioConsellDeGovern.getEncodedRuta()}">
				${informePrevi.autoritzacioConsellDeGovern.nom}
			</a>	
			<c:if test="${informePrevi.autoritzacioConsellDeGovern.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${informePrevi.autoritzacioConsellDeGovern.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>	
		</div>
	</p>
</c:if>	
<c:if test="${informePrevi.autoritzacioConseller.ruta != null}">
	<p>
		<div class="document">
			<label>Autorització Conseller:</label>										                  	
	         		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioConseller.getEncodedRuta()}">
				${informePrevi.autoritzacioConseller.nom}
			</a>	
			<c:if test="${informePrevi.autoritzacioConseller.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${informePrevi.autoritzacioConseller.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>	
		</div>
	</p>
</c:if>	
<div class="row">
	<div class="col-md-12">
		<div class="row">
  			<c:if test="${canModificarExpedient}">
				<div class="col-md-offset-9 col-md-2 margin_top30">
					<a href="editInforme?${informePrevi.expcontratacio.expContratacio != '-1' ? 'ref=' += informePrevi.expcontratacio.expContratacio : 'idinf=' += informePrevi.idInf}&from=${redireccio}" class="btn btn-primary" role="button">Modificar</a>
				</div>
			</c:if>
    	</div>       
	</div>
</div>