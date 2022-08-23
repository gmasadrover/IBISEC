<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}"  style="height:100%">  
<head>
	<jsp:include page="../_header.jsp"></jsp:include>	
	
</head>	
<body  style="height:100%">	
    <div id="wrapper"  style="height:100%">
    	<jsp:include page="../_menu.jsp"></jsp:include>
       	<div id="page-wrapper"  style="height:100%">
       		<div class="container-fluid" style="height:100%">
       			<!-- Page Heading -->
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            Actuacions <small>Actuacions</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Actuacions
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
	       		
				<div class="row"  style="height:700px">
					<iframe src="https://www.google.com/maps/d/embed?mid=1XpLCSAVHzsmN_dMqZaspN5b9BjGTL0RJ&ehbc=2E312F" width="640" height="480"></iframe>
				</div>
			</div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/llistats/llistat.js?<%=application.getInitParameter("datakey")%>"></script>   
    <!-- /#wrapper -->
</body>
</html>