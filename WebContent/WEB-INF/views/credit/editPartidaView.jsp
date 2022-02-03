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
                            Credit <small>Editar partida</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Credit
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Editar partida
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
                
    			<div class="row">
                    <div class="col-md-12">                    	
		    			<form class="form-horizontal" method="POST" action="DoEditPartida">
		    				<input type="hidden" name="codi" value="${partida.codi}">
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">Codi</label>
                                <div class="col-xs-3">
                                	<input class="form-control" value="${partida.codi}" disabled>
                                </div>
                            </div>
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">Nom</label>
                                <div class="col-xs-3">
                                	<input class="form-control" name="nom" placeholder="nom" value="${partida.nom}" required>
                                </div>
                            </div>	
                            <div class="form-group">                    			
                                <label class="col-xs-3 control-label">Import</label>
                                <div class="col-xs-3">
	                                <input class="form-control" name="import" id="import" placeholder="0000,00" value="${partida.getTotalPartidaString()}" required>	                                
	                            </div>			                                			                                
                            </div>
		    				<div class="form-group">
                                <label class="col-xs-3  control-label">Tipus</label>
                                <input type="hidden" id="tipusSelected" value="${partida.tipus}">
                                <div class="col-xs-3">
	                                <select class="form-control" name="idTipus" id="tipusPartidaList">	                                
										<c:forEach items="${tipusPartida}" var="tipus">
											<option value="${tipus}"><m:message key="${tipus}"/></option>
										</c:forEach>									
	                                </select>
	                             </div>
                            </div>  
						    <div class="form-group">
						        <div class="col-xs-offset-3 col-xs-9">
						            <input type="submit" class="btn btn-primary" name="modificar" value="Guardar">
						            <c:choose>
						            	<c:when test="${!partida.estat}">
						            		<input type="submit" class="btn btn-success" name="obrir" value="Obrir">
						            	</c:when>
						            	<c:otherwise>
						            		<input type="submit" class="btn btn-danger" name="eliminar" value="Tancar">
						            	</c:otherwise>
						            </c:choose>
						        </div>
						    </div>    				
		    			</form>
                    </div>
                </div>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/credit/partida.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>