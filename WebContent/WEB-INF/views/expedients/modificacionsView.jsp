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
                            Modificacions <small>Expedients</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Modificacions
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <div class="row">
					
				</div>	       		
                <!-- /.row -->
                <div class="row">
                    <div class="col-md-12">
                        <h2>Modificacions</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                    	<th>Modificat</th>
                                        <th>Expedient</th>
                                        <th>Data Creacio</th>
                                        <th>Data Aprovacio</th>                                        
                                        <th>Centre</th>
                                        <th>Descripció</th>
                                        <th>Adjudicatari</th>
                                        <th>Preu modificació</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${modificacionsList}" var="informe" >
							          	<tr class=${informe.expcontratacio.anulat ? "danger" : informe.llistaModificacions.size() > 0 ? "warning" : "success"}>							          	
							           		<td>${informe.idInf}</td>
							           		<td><a href="expedient?ref=${informe.expcontratacio.expContratacio}" class="loadingButton"  data-msg="obrint expedient...">${informe.expcontratacio.expContratacio}</a></td>
							           		<td>${informe.expcontratacio.dataCreacio}</td>
							           		<td>${informe.autoritzacioPropostaDespesa.firmesList.size() >= 1 ? informe.autoritzacioPropostaDespesa.firmesList.get(0).dataFirma : ''}</td>
							            	<td>${informe.actuacio.centre.getNomComplet()}</td>
							            	<td>${informe.getPropostaInformeSeleccionada().objecte}</td>
							            	<td>${informe.getOfertaSeleccionada().nomEmpresa}</td>
							            	<td>${informe.getOfertaSeleccionada().getPlicFormatNormal()}</td>
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
    <script src="js/expedient/llistatmodificats.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>