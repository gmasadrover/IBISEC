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
                            Previsió Liquidacions <small>Urbanístiques</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Previsió Liquidacions Urbanístiques
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>       		
                <!-- /.row -->
                <div class="row">
                    <div class="col-md-12">
                        <h2>Previsió liquidacions Urbanístiques</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                    	<th>Expedient</th>
                                        <th>Descripció</th>
                                        <th>Centre</th>
                                        <th>Tipus</th>
                                        <th>Sol·licitud</th>
                                        <th>Sol·licitud</th>
                                        <th>Concesió</th>
                                        <th>Concesió</th>
                                        <th>Pagada Taxa</th>
                                        <th>Pagada Taxa</th>  
                                        <th>Taxa</th>
                                        <th>ICO</th>    
                                        <th>Partida</th>                                  
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${informeList}" var="informe" >
							          	<tr>
							           		<td><a href="actuacionsDetalls?ref=${informe.actuacio.referencia}" class="loadingButton"  data-msg="obrint expedient...">${informe.expcontratacio.expContratacio != '' && informe.expcontratacio.expContratacio != null  ? informe.expcontratacio.expContratacio : informe.idInf}</a></td>
							           		<td>${informe.propostaInformeSeleccionada.objecte}</td>
							           		<td>${informe.actuacio.centre.getNomComplet()}</td>
							            	<td>${informe.llicencia.getTipusFormat()}</td>
							            	<td>${informe.llicencia.peticio}</td>
							            	<td>${informe.llicencia.getDataPeticioString()}</td>
							            	<td>${informe.llicencia.concesio}</td>
							            	<td>${informe.llicencia.getDataConcesioString()}</td>
							            	<td>${informe.llicencia.pagamentTaxa}</td>
							            	<td>${informe.llicencia.getDataPagamentTaxaString()}</td>
							            	<td>${informe.llicencia.taxa}</td>
							            	<td>${informe.llicencia.ico}</td>
							            	<td>${informe.llicencia.pagamentICO}</td>
							          	</tr>
							       	</c:forEach>                                	
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/llicencia/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>