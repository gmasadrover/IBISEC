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
                            Factura <small>Detalls</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Factura
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
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <!-- /.row -->
                <c:if test="${not empty factura}">                	                		
               		<h2 class="margin_bottom30">Informació bàsica</h2>
		    		<div class="row">			    				    				    		
	                    <div class="col-xs-offset-1 col-md-5">
	    					<p>
								<label>Codi:</label> ${factura.idFactura}
							</p>
                            <input type="hidden" name="codi" value="${factura.idFactura}">
	                        <p>
	                        	<label>Actuacio:</label> ${factura.idActuacio}
	                        </p>    
	                        <p> 
	                        	<label>Data factura: </label> ${factura.getDataFacturaString()}
                            </p>
	                        <p> 
	                        	<label>Import: </label> ${factura.valor}
                            </p>                           	
	                        <p> 
	                        	<label>Nombre factura: </label> ${factura.nombreFactura}
                            </p> 
                            <p> 
	                        	<label>Conformador: </label> ${factura.usuariConformador.getNomComplet()}
                            </p>    	                            
	                  	</div>
		             	<div class="col-xs-offset-1 col-md-5">
		             		<p> 
	                        	<label>Informe: </label> ${factura.idInforme}
                            </p> 
		                    <p> 
	                        	<label>Proveïdor: </label> ${factura.idProveidor}
                            </p> 	
	                        <p> 
	                        	<label>Concepte: </label> ${factura.concepte}
                            </p>      
	                        <p> 
	                        	<label>Tipus factura: </label> ${factura.tipusFactura}
                            </p>     
	                        <p> 
	                        	<label>Data entrada: </label> ${factura.getDataEntradaString()}
                            </p> 
                             <p> 
	                        	<label>Data conformada: </label> ${factura.getDataConformacioString()}
                            </p> 		                                                	
	                    </div>		            	
                	</div>
                	<div class="row">
                		<div class="col-xs-offset-1 col-md-10 longText">
                			<p> 
	                        	<label>Notes: </label> 
	                        	${factura.notes}
                            </p>	                		
                        </div>
                	</div>      	
                </c:if>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
</body>
</html>