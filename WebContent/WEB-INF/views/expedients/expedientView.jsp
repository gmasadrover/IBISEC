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
                            Expedient <small>Detalls expedient</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Expedient
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				<c:if test="${not empty actuacio}">					
					<div class="panel panel-${actuacio.activa ? actuacio.aprovada ? "success" : "warning" : "danger"}">
					    <div class="panel-heading">
					        <div class="row">
					    		<div class="col-md-6">
					    			id actuació: <a href="actuacionsDetalls?ref=${actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${actuacio.referencia}</a>
					    		</div>
					    		<div class="col-md-6">
					    			Centre: ${actuacio.centre.getNomComplet()}
					   			</div>
					    	</div>
					    </div>
					</div>
					<c:if test="${informePrevi.expcontratacio.anulat}">
						<div class="panel panel-danger">
						    <div class="panel-heading">
						        <div class="row">
						    		<div class="col-md-2">
						    			Anul·lat
						    		</div>
						    		<div class="col-md-10">
						    			Motiu: ${informePrevi.expcontratacio.motiuAnulament}
						   			</div>
						    	</div>
						    </div>
						</div>
					</c:if>
					<div class="row">
		                <div class="col-md-12">  
	                        <c:set var="ofertes" scope="request" value="${informePrevi.llistaOfertes}"></c:set>
	    					<c:set var="ofertaSeleccionada" scope="request" value="${informePrevi.ofertaSeleccionada}"></c:set>
			    			<div class="tabbable">
		                    	<ul class="nav nav-tabs">
								   	<li class='active'><a data-toggle="tab" href="#informe">Informe</a></li>
								   	<li><a data-toggle="tab" href="#licitacio">Licitació</a></li>							   											    
								    <li><a data-toggle="tab" href="#execucio">Execució</a></li>	
								    <li><a data-toggle="tab" href="#garantia">Garantia</a></li>						   
							 	</ul>
							 	<div class="tab-content">
									<div id="informe" class="tab-pane fade in active">	
										<div class="col-md-12 bordertab">
											<jsp:include page="../expedients/include/_resumInforme.jsp"></jsp:include>
										</div>
									</div>
									<div id="licitacio" class="tab-pane fade">
										<div class="col-md-12 bordertab">
											<jsp:include page="../expedients/include/_resumLicitacio.jsp"></jsp:include>
										</div>
									</div>
									<div id="execucio" class="tab-pane fade">
										<div class="col-md-12 bordertab">
											<jsp:include page="../expedients/include/_resumExecucio.jsp"></jsp:include>       
										</div>
									</div>
									<div id="garantia" class="tab-pane fade">
										<div class="col-md-12 bordertab">
										</div>
									</div>
								</div>							
							</div>	
		                </div>
	            	</div>            	
				</c:if>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/expedient/detalls.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>