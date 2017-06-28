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
                            Expedients <small>Expedients</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Expedients
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
                        <h2>Expedients</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Expedient</th>
                                        <th>Centre</th>
                                        <th>Descripció</th>
                                        <th>Publicat BOIB</th>
                                        <th>Publicat BOIB</th>
                                        <th>Adjuducació</th>
                                        <th>Adjuducació</th>
                                        <th>Firma</th>
                                        <th>Firma</th>
                                        <th>Inici d'obra</th>
                                        <th>Inici d'obra</th>
                                        <th>Fi garantia</th>
                                        <th>Fi garantia</th>
                                        <th>Liquidació</th>
                                        <th>Liquidació</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${expedientsList}" var="expedient" >
							          	<tr>							          	
							           		<td><a href="expedient?ref=${expedient.expContratacio}">${expedient.expContratacio}</a></td>
							            	<td>${expedient.actuacio.nomCentre}</td>
							            	<td>${expedient.actuacio.descripcio}</td>
							            	<td>${expedient.getDataPublicacioBOIBString()}</td>
							            	<td>${expedient.dataPublicacioBOIB}</td>
							            	<td>${expedient.getDataAdjudicacioString()}</td>
							            	<td>${expedient.dataAdjudicacio}</td>
							            	<td>${expedient.getDataFirmaString()}</td>
							            	<td>${expedient.dataFormalitzacioContracte}</td>
							            	<td>${expedient.getDataIniciObratring()}</td>
							            	<td>${expedient.dataIniciExecucio}</td>
							            	<td>${expedient.getDataFiGarantiaString()}</td>
							            	<td>${expedient.dataRetornGarantia}</td>
							            	<td>${expedient.getDataLiquidacioString()}</td>
							            	<td>${expedient.dataLiquidacio}</td>
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
    <script src="js/expedient/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>