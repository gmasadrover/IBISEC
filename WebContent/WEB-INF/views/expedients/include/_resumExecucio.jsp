<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<c:if test="${true || informePrevi.autoritzacioPropostaDespesa.ruta != null || informePrevi.contracteSignat.ruta != null || informePrevi.llistaModificacions.size() > 0}">
	<div class="panel panel-default">
    	<div class="panel-body">
        	<div class="tabbable">
               	<ul class="nav nav-tabs">
               		<c:set var="primera" value="true" scope="request"/>
               		<c:forEach items="${informePrevi.llistaModificacions}" var="propostaModificacio" >
               			<li ${primera ? 'class="active"' : ''}><a data-toggle="tab" href="#propostaModificacio_${propostaModificacio.idInf}">Incidència ${propostaModificacio.idInf}</a></li>
               		 	<c:set var="primera" value="false" scope="request"/>	
               		</c:forEach>					   
 				</ul>
			  	<div class="tab-content">
			  		<c:set var="primera" value="true" scope="request"/>
			  		<c:forEach items="${informePrevi.llistaModificacions}" var="propostaModificacio" >  
				  		<div id="propostaModificacio_${propostaModificacio.idInf}" class="tab-pane fade ${primera ? 'in active' : ''}">
				  		 	<div class="col-md-12 bordertab">
				  		 		<c:set var="propostaModificacio" value="${propostaModificacio}" scope="request"/>
				  		 		<jsp:include page="../../tasca/include/_resumModificacioInforme.jsp"></jsp:include>
						    	<c:set var="primera" value="false" scope="request"/>
						    </div>
				  		 </div>
			  		</c:forEach>
  				</div>
			</div>
      	</div>
      	<div class="panel-body">
			<div class="row panel-body">
				<h4>Incidències</h4>	
            	<a href="modificarInforme?idInforme=${informePrevi.idInf}" class="btn btn-primary" role="button">Afegir incidència</a>							                    				                       	
        	</div>
    	</div>
	</div>
</c:if>
<c:if test="${informePrevi.expcontratacio.contracte == 'major'}">
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row panel-body">
			<h4>Certificacions</h4>
			<div class="table-responsive">                        
	        	<table class="table table-striped table-bordered filerTable factures">
	            	<thead>
	                	<tr>                                        
	                    	<th>Certificació</th>
	                  		<th>Actuació</th>
	                      	<th>Data entrada</th>
	                      	<th>Data entrada</th>
	                       	<th>Data certificació</th>
	                      	<th>Data certificació</th>
	                      	<th>Data pasada a conf</th>
	                      	<th>Data pasada a conf</th>
	                      	<th>Data conformada</th>
	                      	<th>Data conformada</th>
	                      	<th>Data contabilitat</th>
	                      	<th>Data contabilitat</th>
	                       	<th>Import</th>
	                       	<th>Nombre certificació</th>
	                      	<th>Tipus</th>
	                        <th>Proveïdor</th>
	                      	<th>notes</th>  	
	                      	<th>Arxiu</th>                      	
	                   	</tr>
	               	</thead>
	              	<tbody>
	                 	<c:forEach items="${informePrevi.llistaCertificacions}" var="certificacio" >
		          			<tr class="">							          	
				           		<td><a href="certificacioDetalls?ref=${certificacio.idFactura}">${certificacio.idFactura}</a></td>
				            	<td>${certificacio.idActuacio}</td>
				            	<td>${certificacio.getDataEntradaString()}</td>
				            	<td>${certificacio.dataEntrada}</td>
				            	<td>${certificacio.getDataFacturaString()}</td>
				            	<td>${certificacio.dataFactura}</td>
				            	<td>${certificacio.getDataEnviatConformadorString()}</td>
				            	<td>${certificacio.dataEnviatConformador}</td>
				            	<td>${certificacio.getDataConformacioString()}</td>
				            	<td>${certificacio.dataConformacio}</td>
				            	<td>${certificacio.getDataEnviatComptabilitatString()}</td>
				            	<td>${certificacio.dataEnviatComptabilitat}</td>
				            	<td>${certificacio.valor}</td>
				            	<td>${certificacio.nombreFactura}</td>
				            	<td>${certificacio.tipusFactura}</td>
				            	<td>${certificacio.idProveidor}</td>
				            	<td>${certificacio.notes}</td>	 
				            	<td><a target="_blanck" href="downloadFichero?ruta=${certificacio.arxiu.getEncodedRuta()}">${certificacio.arxiu.nom}</a></td>			            				            	
				          	</tr>
				       	</c:forEach>
              		</tbody>
             	</table>
       		</div>															
		</div>
 	</div>
  	<c:if test="${informePrevi.ofertaSeleccionada != null}">
    	<div class="panel-body">
			<div class="row panel-body">	
            	<a href="registrarCertificacio?idInforme=${informePrevi.idInf}" class="btn btn-primary" role="button">Registrar certificació</a>							                    				                       	
           	</div>
      	</div>
   	</c:if>   
</div>
<br />
</c:if>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row panel-body">
			<h4>Factures</h4>
			<div class="table-responsive">                        
	        	<table class="table table-striped table-bordered filerTable factures">
	            	<thead>
	                	<tr>                                        
	                    	<th>Factura</th>
	                  		<th>Actuació</th>
	                      	<th>Data entrada</th>
	                      	<th>Data entrada</th>
	                       	<th>Data factura</th>
	                      	<th>Data factura</th>
	                      	<th>Data pasada a conf</th>
	                      	<th>Data pasada a conf</th>
	                      	<th>Data conformada</th>
	                      	<th>Data conformada</th>
	                      	<th>Data contabilitat</th>
	                      	<th>Data contabilitat</th>
	                       	<th>Import</th>
	                       	<th>Nombre factura</th>
	                      	<th>Tipus</th>
	                        <th>Proveïdor</th>
	                      	<th>notes</th>  
	                      	<th>Arxiu</th>
	                   	</tr>
	               	</thead>
	              	<tbody>
	                 	<c:forEach items="${informePrevi.llistaFactures}" var="factura" >
		          			<tr class="${factura.anulada ? 'danger' : ''}">							          	
				           		<td><a href="facturaDetalls?ref=${factura.idFactura}">${factura.idFactura}</a></td>
				            	<td>${factura.idActuacio}</td>
				            	<td>${factura.getDataEntradaString()}</td>
				            	<td>${factura.dataEntrada}</td>
				            	<td>${factura.getDataFacturaString()}</td>
				            	<td>${factura.dataFactura}</td>
				            	<td>${factura.getDataEnviatConformadorString()}</td>
				            	<td>${factura.dataEnviatConformador}</td>
				            	<td>${factura.getDataConformacioString()}</td>
				            	<td>${factura.dataConformacio}</td>
				            	<td>${factura.getDataEnviatComptabilitatString()}</td>
				            	<td>${factura.dataEnviatComptabilitat}</td>
				            	<td>${factura.valor}</td>
				            	<td>${factura.nombreFactura}</td>
				            	<td>${factura.tipusFactura}</td>
				            	<td>${factura.idProveidor}</td>
				            	<td>${factura.notes}</td>	 	
				            	<td><a target="_blanck" href="downloadFichero?ruta=${factura.arxiu.getEncodedRuta()}">${factura.arxiu.nom}</a></td>			            	
				          	</tr>
				       	</c:forEach>
              		</tbody>
             	</table>
       		</div>															
		</div>
 	</div>
  	<c:if test="${informePrevi.ofertaSeleccionada != null && canCreateFactura}">
    	<div class="panel-body">
			<div class="row panel-body">	
            	<a href="registrarFactura?idInforme=${informePrevi.idInf}" class="btn btn-primary" role="button">Registrar factura</a>							                    				                       	
           	</div>
      	</div>
   	</c:if>   
</div>
<br />
<p>
	<label>Altre documentació execució:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentsAltresExecucio}" var="arxiu" >
		<div class="document">
			<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
			<c:if test="${arxiu.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<c:if test="${canModificarExpedient && arxiu.ruta != null}">
				<span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${arxiu.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
	<br>					            		
</div>
<div class="row">            			
	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentsAltresExecucio">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
            <div class="col-xs-5">   
            	<input type="file" class="btn" name="file" multiple/><br/>
			</div> 
			<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
			<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
			<input type="hidden" name="idInforme" value="${informePrevi.idInf}">			    
			<div class="col-xs-2"> 
				<input type="submit" class="btn btn-primary" value="Pujar" />
			</div>    						
		</div>         				
	</form>							
</div>	
<div class="row">
	<div class="col-md-12">
		<div class="row">
  			<c:if test="${canModificarExpedient}">
				<div class="col-md-offset-9 col-md-2 margin_top30">
					<a href="editExecucio?${informePrevi.expcontratacio.expContratacio != '-1'  ? 'ref=' += informePrevi.expcontratacio.expContratacio : 'idinf=' += informePrevi.idInf}" class="btn btn-primary" role="button">Editar</a>
				</div>
			</c:if>
    	</div>       
	</div>
</div>