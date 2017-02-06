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
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Tasques <small>Llistat</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Tasques
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->


                <div class="row">
                    <div class="col-lg-12">
                        <h2>Tasques</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>                                        
                                        <th>Tasca</th>
                                        <th>id Actuació</th>
                                        <th>Centre</th>                                        
                                        <th>Data creació</th>
                                        <th>Data creació</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${tasquesList}" var="tasca" >
							          	<tr>							          	
							           		<td><a href="tasca?id=${tasca.idTasca}">${tasca.idTasca} - ${tasca.name}</a></td>
							            	<td><a href="actuacionsDetalls?ref=${tasca.actuacio.referencia}">${tasca.actuacio.referencia}</a></td>
							            	<td>${tasca.actuacio.nomCentre}</td>							            	
							            	<td>${tasca.getDataCreacioString()}</td>
							            	<td>${tasca.dataCreacio}</td>						            	
							          	</tr>
							       	</c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-lg-6">
                    </div>
                </div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/tasca/llistat.js"></script>
    <!-- /#wrapper -->
</body>
</html>