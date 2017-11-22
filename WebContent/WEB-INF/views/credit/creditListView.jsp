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
                            Crèdit <small>Partides creades</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Crèdit
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Partides
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->


                <div class="row">
                    <div class="col-md-12">
                        <h2>Partides</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Codi</th>
                                        <th>Nom</th>
                                        <th>Tipus</th>
                                        <th>Total</th>
                                        <th>Per asignar</th>
                                        <th>Reservat</th>
                                        <th>Assignat / previst</th>    
                                        <th>Pagat</th>                                 
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${partidesList}" var="partida" >
							          	<tr class=${partida.estat ? "success" : "danger"}>							          	
							           		<td><a href="partidaDetalls?codi=${partida.codi}" class="loadingButton"  data-msg="obrint partida...">${partida.codi}</a></td>
							            	<td>${partida.nom}</td>
							            	<td>${partida.tipus}</td>
							            	<td>${partida.getTotalPartidaFormat()}</td>
							            	<td>${partida.getPartidaPerAsignarFormat()}</td>
							            	<td>${partida.getReservaPartidaFormat()}</td>
							            	<td>${partida.getPrevistPartidaFormat()}</td>
							            	<td>${partida.getPartidaPagatFormat()}</td>							            	
							          	</tr>
							       	</c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                    </div>
                </div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/credit/creditLlistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>