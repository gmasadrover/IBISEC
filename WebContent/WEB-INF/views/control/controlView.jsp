<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
         		<div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            Control <small>Detalls</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Control
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->				
            </div>

			<div class="row">
				<div class="col-md-12">
                	<h2>Configuració INTRANET</h2>
                	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="updateConfiguracio">
                		<div class="form-group">
							<div class="col-md-6">
								<label>Import obra major</label>
								<input name="importObraMajor" placeholder="" value="${configuracioActual.getImportObraMajor()}" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>Import servei major</label>
								<input name="importServeiMajor" placeholder="" value="${configuracioActual.getImportServeiMajor()}" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>Import subministrament major</label>
								<input name="importSubministramentMajor" placeholder="" value="${configuracioActual.getImportSubministramentMajor()}" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label>Ruta base documents</label>
								<input name="rutaBaseDocumentacio" placeholder="" value="${configuracioActual.rutaBaseDocumentacio}" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3">
								<label>Usuari Recerca Pressuposts</label>
                                <input type="hidden" id="idUsuariRecercaPresupostsPrevi" value="${configuracioActual.idUsuariRecercaPresuposts}" >														            	 										            	 	
				                <select class="form-control selectpicker" data-live-search="true" data-size="6" name="idUsuariRecercaPresuposts" id="idUsuariRecercaPresuposts">
				                	<c:forEach items="${llistaUsuaris}" var="usuari">
				                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
				                	</c:forEach>					                                	
				                </select>	
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3">
								<label>Usuari Comprovació factures</label>
                                <input type="hidden" id="idUsuariFacturesPrevi" value="${configuracioActual.idUsuariFactures}" >														            	 										            	 	
				                <select class="form-control selectpicker" data-live-search="true" data-size="6" name="idUsuariFactures" id="idUsuariFactures">
				                	<c:forEach items="${llistaUsuaris}" var="usuari">
				                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
				                	</c:forEach>					                                	
				                </select>	
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3">
								<label>Usuari Comprovació certificacions</label>
                                <input type="hidden" id="idUsuariCertificacionsPrevi" value="${configuracioActual.idUsuariCertificacions}" >														            	 										            	 	
				                <select class="form-control selectpicker" data-live-search="true" data-size="6" name="idUsuariCertificacions" id="idUsuariCertificacions">
				                	<c:forEach items="${llistaUsuaris}" var="usuari">
				                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
				                	</c:forEach>					                                	
				                </select>	
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3">
								<label>Usuari realització Memòries d'Inici</label>
                                <input type="hidden" id="idUsuariOrdreIniciPrevi" value="${configuracioActual.idUsuariOrdreInici}" >														            	 										            	 	
				                <select class="form-control selectpicker" data-live-search="true" data-size="6" name="idUsuariOrdreInici" id="idUsuariOrdreInici">
				                	<c:forEach items="${llistaUsuaris}" var="usuari">
				                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
				                	</c:forEach>					                                	
				                </select>	
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3">
								<label>Usuari redacció contractes</label>
                                <input type="hidden" id="idUsuariRedaccioContractePrevi" value="${configuracioActual.idUsuariRedaccioContracte}" >														            	 										            	 	
				                <select class="form-control selectpicker" data-live-search="true" data-size="6" name="idUsuariRedaccioContracte" id="idUsuariRedaccioContracte">
				                	<c:forEach items="${llistaUsuaris}" var="usuari">
				                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
				                	</c:forEach>					                                	
				                </select>	
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3">
								<label>Usuari actualització dades empresa</label>
                                <input type="hidden" id="idUsuariActualitzarEmpresaPrevi" value="${configuracioActual.idUsuariActualitzarEmpresa}" >														            	 										            	 	
				                <select class="form-control selectpicker" data-live-search="true" data-size="6" name="idUsuariActualitzarEmpresa" id="idUsuariActualitzarEmpresa">
				                	<c:forEach items="${llistaUsuaris}" var="usuari">
				                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
				                	</c:forEach>					                                	
				                </select>	
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3">
								<label>Usuari tramitació llicències</label>
                                <input type="hidden" id="idUsuariLlicenciesPrevi" value="${configuracioActual.idUsuariLlicencies}" >	
                                <select class="form-control selectpicker" data-live-search="true" data-size="6" name="idUsuariLlicencies" id="idUsuariLlicencies">													            	 										            	 	
				                	<c:forEach items="${llistaUsuaris}" var="usuari">
				                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
				                	</c:forEach>					                                	
				                </select>	
							</div>
						</div>
						<div class="col-md-4">												        		
				    		<div class="row">
				        		<div class="col-md-12">															        																						 				
							 		<input class="btn btn-success margin_top30 upload" type="submit" name="modificar" value="Guardar canvis">
							 	</div>
				     		</div>
		     			</div>	
                	</form>
                </div>
			</div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
     <script src="js/control/control.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>