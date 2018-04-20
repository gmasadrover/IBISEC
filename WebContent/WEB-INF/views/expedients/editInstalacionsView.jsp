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
                            Expedient <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Instal·lacions
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Modificar
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
                <c:if test="${not empty expedient}">
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditInstalacions">	
						<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
						<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
						<input type="hidden" name="idInforme" value="${informePrevi.idInf}">			    
						<p>
							<label>Documentació Instal·lació baixa tensió:</label>
						</p>
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>
									<label>Nº Expedient:</label>
									<input name="expedientBaixaTensio" placeholder="" value="${informePrevi.instalacions.expedientBaixaTensio}"> 
								</p>
							</div>
							<div class="col-md-3">
								<p>
									<label>Data OCA:</label>
									<div class="input-group date datepicker">
									  	<input type="text" class="form-control" name="dataOCABaixaTensio" value="${informePrevi.instalacions.getDataOCABaixaTensioString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>									
								</p>
							</div>
						</div>	
						<div class="row col-md-12">
							<c:forEach items="${informePrevi.documentsIntalacioBaixaTensio}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
											<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsIntalacioBaixaTensio" multiple/><br/>
							</div> 
						</div>
						<p>
							<label>Documentació Instal·lació contraincendis:</label>
						</p>
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>
									<label>Nº Expedient:</label>
									<input name="expedientContraincendis" placeholder="" value="${informePrevi.instalacions.expedientContraincendis}">
								</p>
							</div>		
						</div>		
						<div class="row col-md-12">
							<c:forEach items="${informePrevi.documentsIntalacioContraincendis}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
											<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsIntalacioContraincendis" multiple/><br/>
							</div> 
						</div>
						<p>
							<label>Documentació Certificat Eficiència Energètica:</label>
						</p>	
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>
									<label>Nº Expedient:</label>
									<input name="expedientEficienciaEnergetica" placeholder="" value="${informePrevi.instalacions.expedientEficienciaEnergetica}">
								</p>
							</div>
							<div class="col-md-3">
								<p>
									<label>Data:</label>
									<div class="input-group date datepicker">
									  	<input type="text" class="form-control" name="dataRegistreEficienciaEnergetica" value="${informePrevi.instalacions.getDataRegistreEficienciaEnergeticaString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
								</p>
							</div>
						</div>	
						<div class="row col-md-12">
							<c:forEach items="${informePrevi.documentsCertificatEficienciaEnergetica}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
										<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsCertificatEficienciaEnergetica" multiple/><br/>
							</div> 
						</div>
						<p>
							<label>Documentació Instal·lació de productes petrolífers:</label>
						</p>	
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>
									<label>Nº Expedient:</label>
									<input name="expedientPetrolifers" placeholder="" value="${informePrevi.instalacions.expedientPetrolifers}">
								</p>
							</div>	
							<div class="col-md-3">
								<p>
									<label>Data:</label>
									<div class="input-group date datepicker">
									  	<input type="text" class="form-control" name="dataPetrolifers" value="${informePrevi.instalacions.getDataPetrolifersString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
								</p>
							</div>	
						</div>	
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>
									<label>Nº Instal·lació:</label>
									<input name="instalacioPetrolifers" placeholder="" value="${informePrevi.instalacions.instalacioPetrolifers}">
								</p>
							</div>		
							<div class="col-md-3">
								<p>
									<label>Num diposits:</label>
									<input name="dipositsPetrolifers" placeholder="" value="${informePrevi.instalacions.dipositsPetrolifers}">
								</p>
							</div>		
							<div class="col-md-3">
								<p>
									<label>Capacitat total:</label>
									<input name="capTotalPetrolifers" placeholder="" value="${informePrevi.instalacions.capTotalPetrolifers}">
								</p>
							</div>		
						</div>	
						<div class="row col-md-12 margin_bottom30">
							<c:forEach items="${informePrevi.documentsInstalacioPetrolifera}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
										<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsInstalacioPetrolifera" multiple/><br/>
							</div> 
						</div>
						<p>
							<label>Documentació Instal·lació tèrmica:</label>
						</p>	
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>
									<label>Nº Expedient:</label>
									<input name="expedientTermiques" placeholder="" value="${informePrevi.instalacions.expedientTermiques}">
								</p>
							</div>		
						</div>	
						<div class="row col-md-12 margin_bottom30">
							<c:forEach items="${informePrevi.documentsIntalacioTermica}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
										<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsIntalacioTermica" multiple/><br/>
							</div> 
						</div>
						<p>
							<label>Documentació Instal·lació ascensor:</label>
						</p>
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>
									<label>Nº Expedient:</label>
									<input name="expedientAscensor" placeholder="" value="${informePrevi.instalacions.expedientAscensor}">
								</p>
							</div>		
						</div>		
						<div class="row col-md-12 margin_bottom30">
							<c:forEach items="${informePrevi.documentsIntalacioAscensor}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
										<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsIntalacioAscensor" multiple/><br/>
							</div> 
						</div>
						<p>
							<label>Documentació Instal·lació alarma:</label>
						</p>	
						<div class="row col-md-12 margin_bottom30">
							<c:forEach items="${informePrevi.documentsIntalacioAlarma}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
										<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsIntalacioAlarma" multiple/><br/>
							</div> 
						</div>
						<p>
							<label>Documentació Instal·lació subministrament aigua:</label>
						</p>	
						<div class="row col-md-12 margin_bottom30">
							<c:forEach items="${informePrevi.documentsIntalacioSubministreAigua}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
										<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsIntalacioSubministreAigua" multiple/><br/>
							</div> 
						</div>
						<p>
							<label>Documentació Instal·lació Pla Autoprotecció:</label>
						</p>	
						<div class="row col-md-12">
							<div class="col-md-6">
								<p>
									<label>Nº Expedient:</label>
									<input name="expedientPlaAutoproteccio" placeholder="" value="${informePrevi.instalacions.expedientPlaAutoproteccio}">
								</p>
							</div>		
						</div>		
						<div class="row col-md-12 margin_bottom30">
							<c:forEach items="${informePrevi.documentsPlaAutoproteccio}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
										<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsPlaAutoproteccio" multiple/><br/>
							</div> 
						</div>
						<p>
							<label>Documentació Cedula d'habitabilitat:</label>
						</p>	
						<div class="row col-md-12">		
							<div class="col-md-3">
								<p>
									<label>Data:</label>
									<div class="input-group date datepicker">
									  	<input type="text" class="form-control" name="dataCedulaHabitabilitat" value="${informePrevi.instalacions.getDataCedulaHabitabilitatString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
								</p>
							</div>
						</div>	
						<div class="row col-md-12 margin_bottom30">
							<c:forEach items="${informePrevi.documentsCedulaDeHabitabilitat}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
									<c:if test="${arxiu.signat}">
										<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<c:if test="${arxiu.ruta != null}">
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
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
					           <div class="col-xs-5">   
					           	<input type="file" class="btn" name="documentsCedulaDeHabitabilitat" multiple/><br/>
							</div> 
						</div>
						<div class="row col-md-12"> 
							<div class="form-group">
								<div class="col-xs-2"> 
									<input type="submit" class="btn btn-primary" value="Pujar" />
								</div>    						
							</div>				
						</div>   
					</form>	  
                </c:if>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/expedient/modificarLicitacio.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/zones/zones.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>