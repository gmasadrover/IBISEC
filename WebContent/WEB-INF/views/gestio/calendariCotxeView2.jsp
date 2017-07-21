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
	<link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
	<link href="css/calendari.css" rel="stylesheet">
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
                            Cotxe <small>Reserves Cotxe</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Cotxe
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Reserves
                            </li>
                        </ol>
                    </div>
                </div>
               <div id='wrap'>

				<div id='calendar'></div>
				
				<div style='clear:both'></div>
				</div>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="js/gestio/calendari.js?<%=application.getInitParameter("datakey")%>"></script>
    <script src="js/gestio/vistaCalendari.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>
