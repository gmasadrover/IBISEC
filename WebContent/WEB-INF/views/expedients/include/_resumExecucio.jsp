<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<c:if test="${informePrevi.autoritzacioPropostaDespesa.ruta != null || informePrevi.contracteSignat.ruta != null}">
	<div class="panel panel-default">
    	<div class="panel-body">
        	<div class="tabbable">
               	<ul class="nav nav-tabs">
               		<c:set var="primera" value="true" scope="request"/>
               		<c:forEach items="${informePrevi.llistaModificacions}" var="propostaModificacio" >
               			<li ${primera ? 'class="active"' : ''}><a data-toggle="tab" href="#propostaModificacio_${propostaModificacio.idInf}">Proposta modificacio ${propostaModificacio.idInf}</a></li>
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
				<h4>Modificacions</h4>	
            	<a href="modificarInforme?idInforme=${informePrevi.idInf}" class="btn btn-primary" role="button">Modificar informe</a>							                    				                       	
        	</div>
    	</div>
	</div>
</c:if>
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
  	<c:if test="${informePrevi.ofertaSeleccionada != null && canCreateFactura}">
    	<div class="panel-body">
			<div class="row panel-body">	
            	<a href="registrarCertificacio?idInforme=${informePrevi.idInf}" class="btn btn-primary" role="button">Registrar certificació</a>							                    				                       	
           	</div>
      	</div>
   	</c:if>   
</div>
<br />
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
<div class="col-md-6">
	<p> 
		<label>Expedient: </label> ${expedient.expContratacio}
 	</p>
    <p> 
    	<label>Import licitació: </label> ${informePrevi.propostaInformeSeleccionada.getPlicFormat()}
    </p>
    <c:if test="${expedient.contracte=='major'}">
	    <p> 
	    	<label>Data publicació BOIB: </label> ${expedient.getDataPublicacioBOIBString()}
	    </p>
    </c:if> 
    <p> 
    	<label>Data publicació perfil contratant: </label> ${expedient.getDataPublicacioPerfilContratantString()}
    </p>
    <p> 
    	<label>Adjudicatari: </label> <a href="empresa?cif=${informePrevi.ofertaSeleccionada.cifEmpresa}">${informePrevi.ofertaSeleccionada.nomEmpresa} (${informePrevi.ofertaSeleccionada.cifEmpresa})</a> 
    </p>
    <p> 
    	<label>Termini d'execució: </label> ${informePrevi.ofertaSeleccionada.getTermini()}
    </p>
    <p> 
    	<label>Data Inici obra: </label> ${expedient.getDataIniciObratring()}
    </p>
    <p> 
    	<label>Duració de la garantia: </label> ${expedient.garantia}
    </p>
    <p> 
    	<label>Data liquidació obra: </label> ${expedient.getDataLiquidacioString()}
    </p> 
</div>
<div class="col-md-6">
    <c:if test="${llicencia.codi != null}">
    <p> 
    	<label>Llicència: </label> <a target="_blanck" href="llicencia?codi=${llicencia.codi}">${llicencia.codi}</a> 
    </p>
    </c:if>
    <c:if test="${expedient.contracte=='major'}">
    <p> 
    	<label>Data límit presentació ofertes: </label> ${expedient.getDataLimitPresentacioString()}
    </p>
    </c:if> 
    <p> 
    	<label>Data adjudicació: </label> ${expedient.getDataAdjudicacioString()}
    </p> 
    <p> 
    	<label>Import adjudicació: </label> ${informePrevi.ofertaSeleccionada.getPlicFormat()}
    </p>
    <p> 
    	<label>Data Firma contracte: </label> ${expedient.getDataFirmaString()}
    </p>
    <p> 
    	<label>Data Recepció obra: </label> ${expedient.getDataRecepcioString()}
    </p>
    <p> 
    	<label>Data retorn garantia: </label> ${expedient.getDataRetornGarantiaString()}
   </p> 
</div>  
<div class="row">
	<div class="col-md-12">
		<div class="row">
  			<c:if test="${canModificarExpedient}">
				<div class="col-md-offset-9 col-md-2 margin_top30">
					<a href="editExecucio?${informePrevi.expcontratacio.expContratacio != '-1'  ? 'ref=' += informePrevi.expcontratacio.expContratacio : 'idinf=' += informePrevi.idInf}" class="btn btn-primary" role="button">Modificar</a>
				</div>
			</c:if>
    	</div>       
	</div>
</div>