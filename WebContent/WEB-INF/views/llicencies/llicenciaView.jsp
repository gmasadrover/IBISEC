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
                            Llicència <small>Detalls llicència</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Llicència
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Detalls
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->				
            	<div class="row">
            		<div class="col-md-12">
            			<p> 
                        	<label>Llicència: </label> ${llicencia.codi}
                        </p>
            			<p> 
                        	<label>Expedient: </label> ${llicencia.codiExpedient}
                        </p>                       
                        <p> 
                        	<label>Tipus: </label> ${llicencia.getTipusFormat()}
                        </p>    
                        <p> 
                        	<label>Taxa: </label> ${llicencia.taxa}
                        </p> 
                        <p> 
                        	<label>ICO: </label> ${llicencia.ico}
                        </p>
                        <p> 
                        	<label>Data sol·licitud: </label> ${llicencia.getDataPeticioString()}
                        </p>   
                        <p> 
                        	<label>Data concesió: </label> ${llicencia.getDataConcesioString()}
                        </p>    
                        <p> 
                        	<label>Data pagament Taxa: </label> ${llicencia.getDataPagamentTaxaString()}
                        </p>  
                        <p> 
                        	<label>Data pagament ICO: </label> ${llicencia.getDataPagamentICOString()}
                        </p> 
                        <p>
                        	<label>Partida despesa: </label> ${llicencia.getIdPartida()}
                        </p>      
                        <p> 
                        	<label>Observacions: </label> ${llicencia.observacio}
                        </p>  
                        <p>  
	                        <c:forEach items="${llicencia.arxius}" var="arxiu" >
	                       		<c:set var="arxiu" value="${arxiu}" scope="request"/>
								<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>					            		
							</c:forEach>	
						</p>  
            		</div>            		 
            	</div>
            	<div class="row">
            		<div class="col-md-12">
            			<div class="row">
	               			<c:if test="${canModificar}">
								<div class="col-md-offset-9 col-md-2 margin_top30">
									<a href="editLlicencia?codi=${llicencia.codi}" class="btn btn-primary" role="button">Actualitzar</a>
								</div>
							</c:if>
	                	</div>       
            		</div>
            	</div>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/llicencia/detalls.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>