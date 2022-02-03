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
                            Certificació <small>Detalls</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Certificació
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
                <c:if test="${not empty certificacio}">                	                		
               		<h2 class="margin_bottom30">Informació bàsica</h2>
               		<c:if test="${certificacio.anulada}">       
	               		<div class="row">
		                	<div class="col-md-12">
		               			<p style="color: red;">Certificació Anul·lada</p>
		               			<p style="color: red;">Motiu: ${certificacio.motiuAnulada}</p>
		               		</div>
		               	</div>
	               	</c:if>
		    		<div class="row">			    				    				    		
	                    <div class="col-xs-offset-1 col-md-5">
	    					<p>
								<label>Codi:</label> ${certificacio.idFactura}
							</p>
                            <input type="hidden" name="codi" value="${certificacio.idFactura}">
	                        <p>
	                        	<label>Actuacio:</label> <a href="actuacionsDetalls?ref=${certificacio.idActuacio}" target="_blank">${certificacio.idActuacio}</a>
	                        </p>    
	                        <p> 
	                        	<label>Data certificació: </label> ${certificacio.getDataFacturaString()}
                            </p>
	                        <p> 
	                        	<label>Import: </label> ${certificacio.valor}
                            </p>                           	
	                        <p> 
	                        	<label>Nombre certificació: </label> ${certificacio.nombreFactura}
                            </p> 
                            <p> 
	                        	<label>Conformador: </label> ${certificacio.usuariConformador.getNomComplet()}
                            </p> 
                           <%--  <p> 
	                        	<label>Data conformada: </label> ${certificacio.getDataConformacioString()}
                            </p> --%>    	                            
	                  	</div>
		             	<div class="col-xs-offset-1 col-md-5">
		             		<p> 
	                        	<label>Tipus: </label> ${certificacio.tipus}
                            </p> 
		             		<p> 
	                        	<label>Informe: </label> ${certificacio.idInforme}
                            </p> 
		                    <p> 
	                        	<label>Proveïdor: </label> ${certificacio.nomProveidor} (${certificacio.idProveidor})
                            </p> 	
	                        <p> 
	                        	<label>Concepte: </label> ${certificacio.concepte}
                            </p>     
	                        <p> 
	                        	<label>Data entrada: </label> ${certificacio.getDataEntradaString()}
                            </p> 
                           <%--  <p> 
	                        	<label>Data enviada a conformar: </label> ${certificacio.getDataEnviatConformadorString()}
                            </p>                             
                            <p> 
	                        	<label>Data enviada a comptabilitat: </label> ${certificacio.getDataEnviatComptabilitatString()}
                            </p> 		 --%>                                                	
	                    </div>		            	
                	</div>
                	<div class="row">
                		<div class="col-xs-offset-1 col-md-10">
                			<label>Relació Valorada: </label>
                			<c:forEach items="${certificacio.relaciovalorada}" var="arxiu" >
								<c:set var="arxiu" value="${arxiu}" scope="request"/>
								<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
							</c:forEach>		                	
						</div>
					</div>
                	<div class="row">
                		<div class="col-xs-offset-1 col-md-10">
                			<label>Certificació: </label>
                			<c:forEach items="${certificacio.certificacions}" var="arxiu" >
								<c:set var="arxiu" value="${arxiu}" scope="request"/>
								<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
							</c:forEach>		                	
						</div>
					</div>
                	<div class="row">
                		<div class="col-xs-offset-1 col-md-10 longText">
                			<p> 
	                        	<label>Notes: </label> 
	                        	${certificacio.notes}
                            </p>	                		
                        </div>
                	</div>   
                	<c:if test="${!certificacio.anulada && isIBISEC}">        
	                	<div class="row">
	                		<div class="col-md-2">
								<a href="editCertificacio?ref=${certificacio.idFactura}" class="btn btn-primary" role="button">Modificar</a>
							</div>	
		                	<div class="col-md-3">
		               			<c:if test="${canModificar}">																	
									<input class="btn btn-danger" data-toggle="modal" data-target="#myModal" value="Anul·lar">
				        			<!-- Modal -->
									<div id="myModal" class="modal fade" role="dialog">
										<div class="modal-dialog">																	
									    <!-- Modal content-->
									    	<div class="modal-content">
									      		<div class="modal-header">
									        		<button type="button" class="close" data-dismiss="modal">&times;</button>
									        		<h4 class="modal-title">Motiu anul·lació</h4>
									      		</div>
									      		<div class="modal-body">
									        		<textarea id="motiuAnulacio" required></textarea>
									      		</div>
									      		<div class="modal-footer">
									        		<a id="anularCertificacio" data-idfactura="${certificacio.idFactura}" class="btn btn-danger" role="button">Anul·lar</a>	
									      		</div>
								    		</div>																	
									  	</div>
									</div> 
								</c:if>
							</div>	
						</div> 
                	</c:if>        	
                </c:if>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/factura/view.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>