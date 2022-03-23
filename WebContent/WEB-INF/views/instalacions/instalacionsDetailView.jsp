<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
	<jsp:include page="../_header.jsp"></jsp:include>
</head>
<body>
 	<div id="wrapper">
    	<jsp:include page="../_menu.jsp"></jsp:include>
    	<div id="page-wrapper">

            <div class="container-fluid">
            	<!-- Page Heading -->
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            Instal·lacions <small>Detalls</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Instal·lacions
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <div class="row">
                	<div class="col-md-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <!-- /.row -->
               <c:forEach items="${instalacionsList}" var="instalacions" >
	           	   	<div class="documentacioInstalacions"></div>
	              	<p>
	               		<label>Dades introduïdes a l'expedient <a href="actuacionsDetalls?ref=${instalacions.idActuacio}&exp=${instalacions.idInf}" target="_blanck">${instalacions.expedient}</a></label>
	               	</p>
	               	<c:if test="${instalacions.documentsIntalacioBaixaTensio.size() > 0}">
						<p>
							<label>Documentació Instal·lació baixa tensió:</label>
						</p>
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>						
									<label>Nº Expedient:</label> ${instalacions.expedientBaixaTensio}
								</p>
							</div>
							<div class="col-md-6">
								<p>							
									<label>Data OCA:</label> ${instalacions.getDataOCABaixaTensioString()} 
								</p>
							</div>
						</div>	
						<div class="row col-md-12">
							<div class="documentsIntalacioBaixaTensio">
								<c:forEach items="${instalacions.documentsIntalacioBaixaTensio}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>								
							</div>
							<br>					            		
						</div>
					</c:if>
					<c:if test="${instalacions.documentsIntalacioFotovoltaica.size() > 0}">
						<p>
							<label>Documentació Instal·lació fotovoltàica:</label>
						</p>
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>							
									<label>Nº Expedient:</label> ${instalacions.expedientFotovoltaica}
								</p>
							</div>
							<div class="col-md-6">
								<p>							
									<label>Data OCA:</label> ${instalacions.getDataOCAFotovoltaicaString()} 
								</p>
							</div>
						</div>	
						<div class="row col-md-12">
							<div class="documentsIntalacioFotovoltaica">
								<c:forEach items="${instalacions.documentsIntalacioFotovoltaica}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>	
							</div>
							<br>					            		
						</div>
					</c:if>
					<c:if test="${instalacions.documentsIntalacioContraincendis.size() > 0}">
						<p>
							<label>Documentació Instal·lació contraincendis:</label>
						</p>
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>					
									<label>Nº Expedient:</label> ${instalacions.expedientContraincendis}
								</p>
							</div>		
						</div>		
						<div class="row col-md-12">
							<div class="documentsIntalacioContraincendis">
								<c:forEach items="${instalacions.documentsIntalacioContraincendis}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>
					</c:if>
					<c:if test="${instalacions.documentsInstalacioPetrolifera.size() > 0}">
						<p>
							<label>Documentació Instal·lació de productes petrolífers:</label>
						</p>	
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>						
									<label>Nº Expedient:</label> ${instalacions.expedientPetrolifers} 
								</p>
							</div>	
							<div class="col-md-6">
								<p>				
									<label>Data:</label> ${instalacions.getDataPetrolifersString()} 
								</p>
							</div>		
						</div>
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>							
									<label>Nº Instal·lació:</label> ${instalacions.instalacioPetrolifers}
								</p>
							</div>	
							<div class="col-md-3">
								<p>				
									<label>Num diposits:</label> ${instalacions.dipositsPetrolifers}
								</p>
							</div>	
							<div class="col-md-3">
								<p>						
									<label>Capacitat total:</label> ${instalacions.capTotalPetrolifers}
								</p>
							</div>		
						</div>	
						<div class="row col-md-12">
							<div class="documentsInstalacioPetrolifera">
								<c:forEach items="${instalacions.documentsInstalacioPetrolifera}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>	
					</c:if>	
					<c:if test="${instalacions.documentsInstalacioGas.size() > 0}">
						<p>
							<label>Documentació Instal·lació Gas:</label>
						</p>	
						<div class="row col-md-12">
							<div class="col-md-4">
								<p>							
									<label>Nº Expedient:</label> ${instalacions.expedientInstalacioGas}
								</p>
							</div>	
							<div class="col-md-4">
								<p>					
									<label>Tipus de gas:</label> ${instalacions.tipusInstalacioGas} 
								</p>
							</div>	
							<div class="col-md-4">
								<p>					
									<label>Data:</label> ${instalacions.getDataInstalacioGasString()}
								</p>
							</div>		
						</div>	
						<div class="row col-md-12">
							<div class="documentsInstalacioGas">
								<c:forEach items="${instalacions.documentsInstalacioGas}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>
					</c:if>	
					<c:if test="${instalacions.documentsIntalacioTermica.size() > 0}">
						<p>
							<label>Documentació Instal·lació tèrmica:</label>
						</p>	
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>						
									<label>Nº Expedient:</label> ${instalacions.expedientTermiques}
								</p>
							</div>		
						</div>	
						<div class="row col-md-12">
							<div class="documentsIntalacioTermica">
								<c:forEach items="${instalacions.documentsIntalacioTermica}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>	
					</c:if>
					<c:if test="${instalacions.documentsIntalacioAscensor.size() > 0}">
						<p>
							<label>Documentació Instal·lació ascensor:</label>
						</p>
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>			
									<label>Nº Expedient:</label> ${instalacions.expedientAscensor}
								</p>
							</div>		
						</div>		
						<div class="row col-md-12">
							<div class="documentsIntalacioAscensor">
								<c:forEach items="${instalacions.documentsIntalacioAscensor}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>		
							</div>
							<br>					            		
						</div>
					</c:if>
					<c:if test="${instalacions.documentsIntalacioAlarma.size() > 0}">
						<p>
							<label>Documentació Instal·lació alarma:</label>
						</p>	
						<div class="row col-md-12">
							<div class="documentsIntalacioAlarma">
								<c:forEach items="${instalacions.documentsIntalacioAlarma}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>
					</c:if>
					<c:if test="${instalacions.documentsIntalacioSubministreAigua.size() > 0}">
						<p>
							<label>Documentació Instal·lació subministrament aigua:</label>
						</p>	
						<div class="row col-md-12">
							<div class="documentsIntalacioSubministreAigua">
								<c:forEach items="${instalacions.documentsIntalacioSubministreAigua}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>
					</c:if>
					<c:if test="${instalacions.documentsCertificatEficienciaEnergetica.size() > 0}">
						<p>
							<label>Documentació Certificat Eficiència Energètica:</label>
						</p>	
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>					
									<label>Nº Expedient:</label> ${instalacions.expedientEficienciaEnergetica}
								</p>
							</div>
							<div class="col-md-6">
								<p>			
									<label>Data:</label> ${instalacions.getDataRegistreEficienciaEnergeticaString()}
								</p>
							</div>
						</div>	
						<div class="row col-md-12">
							<div class="documentsCertificatEficienciaEnergetica">
								<c:forEach items="${instalacions.documentsCertificatEficienciaEnergetica}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>	
					</c:if>
					<c:if test="${instalacions.documentsPlaAutoproteccio.size() > 0}">
						<p>
							<label>Documentació Instal·lació Pla Autoprotecció:</label>
						</p>	
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>						
									<label>Nº Expedient:</label> ${instalacions.expedientPlaAutoproteccio}
								</p>
							</div>		
						</div>		
						<div class="row col-md-12">
							<div class="documentsPlaAutoproteccio">
								<c:forEach items="${instalacions.documentsPlaAutoproteccio}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>
					</c:if>
					<c:if test="${instalacions.documentsCedulaDeHabitabilitat.size() > 0}">
						<p>
							<label>Documentació Cedula d'habitabilitat:</label>
						</p>	
						<div class="row col-md-12">		
							<div class="col-md-6">
								<p>
									<label>Data:</label> ${instalacions.getDataCedulaHabitabilitatString()}
								</p>
							</div>
						</div>	
						<div class="row col-md-12">
							<div class="documentsCedulaDeHabitabilitat">
								<c:forEach items="${instalacions.documentsCedulaDeHabitabilitat}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>
					</c:if>
					<c:if test="${instalacions.documentsIniciActivitat.size() > 0}">
						<p>
							<label>Documentació incici activitat:</label>
						</p>
						<div class="row col-md-12">
							<div class="documentsIniciActivitat">
								<c:forEach items="${instalacions.documentsIniciActivitat}" var="arxiu" >
				            		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	         		
								</c:forEach>
							</div>
							<br>					            		
						</div>	
					</c:if>
				</c:forEach>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/centres/detalls.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>