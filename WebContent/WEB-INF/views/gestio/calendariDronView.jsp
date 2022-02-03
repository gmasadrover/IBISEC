<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
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
                            Dron <small>Reserves</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Dron
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Reserves
                            </li>
                        </ol>
                    </div>
                </div>    
                <div class="row">
                	<div class="col-md-12">
               			 <h2>Les meves reserves</h2>
               			 <div class="col-md-offset-1 col-md-10">
               			 	${reservesPropies}
               			 </div>
               		</div>
               	 </div>
               	 <div class="row">
                	<div class="col-md-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
               		<div class="row">
                	<div class="col-md-12">
               			 <h2>Reserva</h2>               			
               		</div>
               	 </div>
                <div class="row">
                	<form class="form-horizontal" method="POST" action="doAddReservaDron">						
						<div class="form-group">		
							<input type="hidden" id="vehicle" value="dron"> 
							<div class=" col-md-2">
							    <div class="col-md-12">
							      	<label>Data</label>
							      	<div class="input-group date col-xs-12 datepicker">
								  		<input type="text" class="form-control" name="peticio" value="${data}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
							    </div>						    
						  	</div>						  	
						  	<div class="col-md-4">
							    <div class="col-md-12">
							      <label>Sol·licitud</label>
							      <div>
							      		<textarea class="form-control" name="motiu" placeholder="Sol·licitud" rows="3" required></textarea>						      	
							      </div>
							    </div>
						  	</div>	
						  	<div class="col-md-2">
						  		<label> </label>
							  	<div class="col-md-12">
							    	<input type="submit" class="btn btn-primary" name="reservar" value="Sol·licitar">
								</div>		
							</div>			  	
						</div>									
					</form>
                </div>
               
                <div class="row">
                    <div class="col-md-12">
               			 <h2>Reserves actuals</h2>
               			 <div class="col-md-offset-1 col-md-10">
               			 	${totesReserves}
               			 </div>
               		</div>
                </div>                  
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>   
    <script src="js/gestio/vistaCalendari.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>
