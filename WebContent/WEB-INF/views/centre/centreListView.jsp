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
                            Centres <small>Centres</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Centres
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				
				<%-- <div class="row">
					<form class="form-horizontal" method="POST" action="centres">						
						<div class="form-group">
						  	<div class="col-md-2">
							    <div class="checkbox">
							      <label>
							        <input type="checkbox" name="ambIncidencia" ${ambIncidencia ? "checked" : ""}> Amb incidències actives
							      </label>
							    </div>
						  	</div>						  				 
						  	<div class="col-md-2">
						    	<input type="submit" class="btn btn-primary" name="filtrar" value="Aplicar Filtres">
							</div>
						</div>	
					</form>
				</div>			 --%>	
                <div class="row">
                    <div class="col-md-12">
                        <h2>Centres</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered filerTable">
                                <thead>
                                    <tr>
                                        <th>Codi</th>
                                        <th>Nom</th>   
                                        <th>Tipus</th>                                                       
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${centresList}" var="centre" >
							          	<tr>							          	
							           		<td><a href="centreDetalls?codi=${centre.idCentre}" class="loadingButton"  data-msg="obrint centre...">${centre.idCentre}</a></td>
							            	<td>${centre.getNomComplet()}</td>		
							            	<td>${centre.tipo}</td>					            						            	
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
    <script src="js/centres/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>