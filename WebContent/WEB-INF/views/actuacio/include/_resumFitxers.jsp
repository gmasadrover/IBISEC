<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div class="tabbable">
	<ul class="nav nav-tabs">
	   	<li class='active'><a data-toggle="tab" href="#arxiusRE">Registre</a></li>
	   	<c:forEach items="${informes}" var="informePrevi" >	
	   		<li><a data-toggle="tab" href="#arxius${informePrevi.idInf}">Informe ${informePrevi.idInf}</a></li>
	   	</c:forEach>										    
	    <li><a data-toggle="tab" href="#arxiusALT">Altres</a></li>						   
	</ul>
  	<div class="tab-content">
  		 <div id="arxiusRE" class="tab-pane fade in active">
  		 	<div class="col-md-12 bordertab">
            	<c:forEach items="${actuacio.arxiusAdjunts.arxiusRegistre}" var="arxiu" >
            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
					<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	         		
				</c:forEach>	
      		</div>
  		 </div>
  		 <c:forEach items="${informes}" var="informePrevi" >	
  		 	<div id="arxius${informePrevi.idInf}" class="tab-pane fade">
	  		 	<div class="col-md-12 bordertab">
	  		 		<c:if test="${informePrevi.propostaActuacio.ruta != null}">			
		  		 		<div class="document">
		               		<label>Proposta d'actuació signada:</label>											                  	
			           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaActuacio.getEncodedRuta()}">
								${informePrevi.propostaActuacio.nom}
							</a>	
							<c:if test="${informePrevi.propostaActuacio.signat}">
									<span data-ruta="${informePrevi.propostaActuacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
							</c:if>
							<br>
							<div class="infoSign hidden">								
							</div>
						</div>
					</c:if>	
					<c:if test="${informePrevi.vistiplauPropostaActuacio.ruta != null}">	
						<div class="document">
		               		<label>Vistiplau proposta d'actuació signada:</label>											                  	
			           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.vistiplauPropostaActuacio.getEncodedRuta()}">
								${informePrevi.vistiplauPropostaActuacio.nom}
							</a>	
							<c:if test="${informePrevi.vistiplauPropostaActuacio.signat}">
									<span data-ruta="${informePrevi.vistiplauPropostaActuacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
							</c:if>
							<br>
							<div class="infoSign hidden">								
							</div>
						</div>
					</c:if>		
					<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.ruta != null}">
						<div class="document">
		               		<label>Autorització àrea econòmica signada:</label>											                  	
			           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.conformeAreaEconomivaPropostaActuacio.getEncodedRuta()}">
								${informePrevi.conformeAreaEconomivaPropostaActuacio.nom}
							</a>	
							<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.signat}">
									<span data-ruta="${informePrevi.conformeAreaEconomivaPropostaActuacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
							</c:if>
							<br>
							<div class="infoSign hidden">								
							</div>
						</div>	
					</c:if>	
					<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.ruta != null}">
              			 	<div class="document">
		               		<label>Autorització proposta d'actuació signada:</label>											                  	
			           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaAutoritzacio.getEncodedRuta()}">
								${informePrevi.autoritzacioPropostaAutoritzacio.nom}
							</a>	
							<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.signat}">
									<span data-ruta="${informePrevi.autoritzacioPropostaAutoritzacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
							</c:if>
							<br>
							<div class="infoSign hidden">								
							</div>
						</div>
					</c:if>	
					<c:forEach items="${informePrevi.propostaTecnica}" var="arxiu" >
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
	               		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
					<c:if test="${informePrevi.autoritzacioPropostaDespesa.ruta != null}">
	               		<div class="document">
		               		<label>Resolució d'adjuducació:</label>											                  	
			           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaDespesa.getEncodedRuta()}">
								${informePrevi.autoritzacioPropostaDespesa.nom}
							</a>	
							<c:if test="${informePrevi.autoritzacioPropostaDespesa.signat}">
									<span data-ruta="${informePrevi.autoritzacioPropostaDespesa.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
							</c:if>
							<br>
							<div class="infoSign hidden">								
							</div>
						</div>		
					</c:if>
					<c:if test="${informePrevi.contracteSignat.ruta != null}">
	               		<div class="document">
		               		<label>Contracte:</label>											                  	
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
					</c:if>
             			 </div>
	  		 </div>	
  		 </c:forEach>									  		 
  		 <div id="arxiusALT" class="tab-pane fade">
  		 	<div class="col-md-12 bordertab">
            	<c:forEach items="${actuacio.arxiusAdjunts.arxiusAltres}" var="arxiu" >
            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
					<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>				            		
				</c:forEach>	
      		</div>
  		 </div>
  	</div>					  	
</div>