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
               		<c:if test="${factura.anulada}">       
	               		<div class="row">
		                	<div class="col-md-12">
		               			<p style="color: red;">Factura Anul·lada</p>
		               		</div>
		               	</div>
	               	</c:if>
		    		<div class="row">			    				    				    		
	                    <div class="col-xs-offset-1 col-md-5">
	    					<p>
								<label>Codi:</label> ${factura.idFactura}
							</p>
                            <input type="hidden" name="codi" value="${factura.idFactura}">
	                        <p>
	                        	<label>Actuacio:</label> <a href="actuacionsDetalls?ref=${factura.idActuacio}" target="_blank">${factura.idActuacio}</a>
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
                            <p> 
	                        	<label>Data conformada: </label> ${factura.getDataConformacioString()}
                            </p>    	                            
	                  	</div>
		             	<div class="col-xs-offset-1 col-md-5">
		             		<p> 
	                        	<label>Informe: </label> ${factura.idInforme}
                            </p> 
		                    <p> 
	                        	<label>Proveïdor: </label> ${factura.nomProveidor} (${factura.idProveidor})
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
	                        	<label>Data enviada a conformar: </label> ${factura.getDataEnviatConformadorString()}
                            </p>                             
                            <p> 
	                        	<label>Data enviada a comptabilitat: </label> ${factura.getDataEnviatComptabilitatString()}
                            </p> 		                                                	
	                    </div>		            	
                	</div>
                	<div class="row">
                		<div class="col-xs-offset-1 col-md-10">
		                	<c:if test="${factura.arxiu.ruta != null}">															
								<p>
							    	<div class="document">
							        	<label>Factura:	</label>											                  	
						          		<a target="_blanck" href="downloadFichero?ruta=${factura.arxiu.getEncodedRuta()}">
											${factura.arxiu.nom}
										</a>	
										<c:if test="${factura.arxiu.signat}">
											<span class="glyphicon glyphicon-pencil signedFile"></span>
										</c:if>
										<br>
										<div class="infoSign hidden">
											<c:forEach items="${factura.arxiu.firmesList}" var="firma" >
												<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
												<br>
											</c:forEach>
										</div>
									</div>																				
								</p>	
							</c:if>
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
                	<div class="row">
                		<div class="col-md-4">
	               			<c:if test="${canModificar}">
								<a href="editFactura?ref=${factura.idFactura}" class="btn btn-primary" role="button">Modificar</a>									
							</c:if>
						</div>	
						<div class="col-md-4">
	               			<c:if test="${canModificar && factura.dataEnviatConformador == null}">
								<a href="enviarAConformar?ref=${factura.idFactura}" class="btn btn-success" role="button">Enviar a conformar</a>									
							</c:if>
						</div>		
						<div class="col-md-4">
	               			<c:if test="${canModificar}">
								<a id="anularFactura" data-idfactura="${factura.idFactura}" class="btn btn-danger" role="button">Anul·lar</a>									
							</c:if>
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
    <script src="js/factura/view.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>