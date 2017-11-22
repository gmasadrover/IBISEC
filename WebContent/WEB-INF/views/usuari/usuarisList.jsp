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
                            Usuaris <small>Usuaris</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Usuaris
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
				<div class="row">
					<form class="form-horizontal" method="POST" action="usuarisList">						
													
					  	<div class="col-md-4">
					  		<input type="checkbox" name="filterWithActius" ${filterWithActius ? "checked" : ""}> Filtrar només actius
							                                
					  	</div>						  				 
					  	<div class="col-md-2">
					    	<input type="submit" class="btn btn-primary loadingButton"  data-msg="Aplicant filtres..." name="filtrar" value="Aplicar Filtres">
						</div>
					</form>
				</div>	
				<div class="row">
					<div class="col-md-2">
						<a href="nouUsuari" class="btn btn-primary" role="button">Afegir usuari</a>
					</div>
				</div>		
				<br/>	
                <div class="row">
                    <div class="col-md-12">                      	
					    <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Usuari</th>
                                        <th>Códi</th>
                                        <th>Departament</th>
                                        <th>Càrreg</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${llistaUsuaris}" var="usuari" >
							          	<tr class=${usuari.actiu ? "success" : "danger"}>							          	
							           		<td><a href="UsuariDetails?id=${usuari.idUsuari}" class="loadingButton"  data-msg="obrint usuari...">${usuari.getNomCompletReal()}</a></td>
							            	<td>${usuari.usuari}</td>
							            	<td>${usuari.departament}</td>
							            	<td>${usuari.carreg}</td>																            	
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
    <script src="js/usuari/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>