<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base" />
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
							Usuari <small>Perfil</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"><i class="fa fa-dashboard"></i> Usuari</li>
							<li class="active"><i class="fa fa-table"></i> Perfil</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->
				<div class="row">
					<div class="col-md-12">
						<p style="color: red;">${errorString}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<h2>Perfil</h2>
						<div class="panel-body">
							<form class="form-horizontal" method="POST"
								action="DoCanviarDades">
								<input type="hidden" name="idUsuari" value="${usuari.idUsuari}">
								<div class="form-group">
									<div class="col-md-4">
										<label class="col-xs-6">Usuari:</label> <input
											value="${usuari.usuari}" disabled>
									</div>
								</div>
								<div class="form-group">
									<div class="visibleObres">
										<div class="col-md-4">
											<label class="col-xs-6">Nom:</label> <input name="nom"
												value="${usuari.name}"
												${potModificar || isAdmin ? "" : "disabled"}>
										</div>
										<div class="col-md-4">
											<label class="col-xs-6">Cognoms:</label> <input
												name="cognoms" value="${usuari.llinatges}"
												${potModificar || isAdmin ? "" : "disabled"}>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-4">
										<label class="col-xs-6">Càrrec:</label> <input name="carreg"
											value="${usuari.carreg}"
											${potModificar || isAdmin ? "" : "disabled"}>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-4">
										<label class="col-xs-6">Rols:</label>
										<div class="checkbox">
											<label> <input name="ADMIN" type="checkbox"
												${fn:contains(usuari.rol,'ADMIN') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Administrador del
												sistema
											</label>
										</div>
										<label class="col-xs-6" style="color: white">-</label>
										<div class="checkbox">
											<label> <input name="GER" type="checkbox"
												${fn:contains(usuari.rol,'GER') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Gerència
											</label>
										</div>
										<label class="col-xs-6" style="color: white">-</label>
										<div class="checkbox">
											<label> <input name="CAP" type="checkbox"
												${fn:contains(usuari.rol,'CAP') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Cap de departament
											</label>
										</div>
										<label class="col-xs-6" style="color: white">-</label>
										<div class="checkbox">
											<label> <input name="ADM" type="checkbox"
												${fn:contains(usuari.rol,'ADM') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Administratiu
											</label>
										</div>
										<label class="col-xs-6" style="color: white">-</label>
										<div class="checkbox">
											<label> <input name="JUR" type="checkbox"
												${fn:contains(usuari.rol,'JUR') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Jurista
											</label>
										</div>
										<label class="col-xs-6" style="color: white">-</label>
										<div class="checkbox">
											<label> <input name="CONSELLERIA" type="checkbox"
												${fn:contains(usuari.rol,'CONSELLERIA') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Personal de
												Conselleria
											</label>
										</div>
										<label class="col-xs-6" style="color: white">-</label>
										<div class="checkbox">
											<label> <input name="CONTA" type="checkbox"
												${fn:contains(usuari.rol,'CONTA') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Comptabilitat
											</label>
										</div>
										<label class="col-xs-6" style="color: white">-</label>
										<div class="checkbox">
											<label> <input name="PERSO" type="checkbox"
												${fn:contains(usuari.rol,'PERSO') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Encarregat de personal
											</label>
										</div>
										<label class="col-xs-6" style="color: white">-</label>
										<div class="checkbox">
											<label> <input name="DADESBANC" type="checkbox"
												${fn:contains(usuari.rol, 'DADESBANC') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Visualització dades
												Banc
											</label>
										</div>
										<label class="col-xs-6" style="color: white">-</label>
										<div class="checkbox">
											<label> <input name="MANUAL" type="checkbox"
												${fn:contains(usuari.rol, 'MANUAL') ? 'checked' : ''}
												${isAdmin ? "" : "disabled"}> Modificació Manual
											</label>
										</div>
									</div>
								</div>
								<c:if test="${potModificar || isAdmin}">
									<div class="form-group">
										<div class="col-md-6">
											<div class="row">
												<div class="col-md-12">
													<input class="btn btn-primary" type="submit" name="guardar"
														value="Modificar dades">
												</div>
											</div>
										</div>
									</div>
								</c:if>
							</form>
						</div>
						<c:if test="${potModificar}">
							<div class="panel-body">
								<form class="form-horizontal" method="POST"
									action="DoCanviarPassword">
									<input type="hidden" name="idUsuari" value="${usuari.idUsuari}">
									<div class="form-group">
										<div class="col-md-3">
											<label>Introduir password actual:</label> <input
												name="passActual" type="password">
										</div>
									</div>
									<div class="form-group">
										<div class="visibleObres">
											<div class="col-md-3">
												<label>Introduir nou password:</label> <input
													type="password" name="nouPassword">
											</div>
											<div class="col-md-3">
												<label>Repetir nou password:</label> <input type="password"
													name="repetirNouPassword">
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-6">
											<div class="row">
												<div class="col-md-12">
													<input class="btn btn-primary" type="submit" name="guardar"
														value="Canviar password">
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</c:if>
						<c:if test="${isAdmin}">
							<div class="panel-body">
								<form class="form-horizontal" method="POST"
									action="DoResetPassword">
									<input type="hidden" name="idUsuari" value="${usuari.idUsuari}">
									<div class="form-group">
										<div class="col-md-6">
											<div class="row">
												<div class="col-md-12">
													<input class="btn btn-primary" type="submit" name="reset"
														value="Resetetjar password">
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
							<c:choose>
								<c:when test="${usuari.actiu}">
									<div class="panel-body">
										<form class="form-horizontal" method="POST"
											action="DoActivarUsuari">
											<input type="hidden" name="idUsuari"
												value="${usuari.idUsuari}">
											<div class="form-group">
												<div class="col-md-6">
													<div class="row">
														<div class="col-md-12">
															<input class="btn btn-danger" type="submit"
																name="descativar" value="Desactivar usuari">
														</div>
													</div>
												</div>
											</div>
										</form>
									</div>
								</c:when>
								<c:otherwise>
									<div class="panel-body">
										<form class="form-horizontal" method="POST"
											action="DoActivarUsuari">
											<input type="hidden" name="idUsuari"
												value="${usuari.idUsuari}">
											<div class="form-group">
												<div class="col-md-6">
													<div class="row">
														<div class="col-md-12">
															<input class="btn btn-success" type="submit"
																name="activar" value="Activar usuari">
														</div>
													</div>
												</div>
											</div>
										</form>
									</div>
								</c:otherwise>
							</c:choose>

						</c:if>
						<c:if test="${isPersonal}">
							<div class="row">
								<div class="col-md-12">
									<h2>Reserves vehicle</h2>
									<div class="col-md-offset-1 col-md-10">
										${reservesPropies}</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
	<jsp:include page="../_footer.jsp"></jsp:include>
	<script
		src="js/usuari/detalls.js?<%=application.getInitParameter("datakey")%>"></script>
	<!-- /#wrapper -->
</body>
</html>