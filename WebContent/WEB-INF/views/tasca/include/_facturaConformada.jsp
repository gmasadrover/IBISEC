<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<div class="panel-body">
	<div class="row">
		<div class="col-md-6">
			<h4>Adjudicació</h4>	
			<p>
				<label>L'empresa adjudicataria:</label> ${informePrevi.ofertaSeleccionada.nomEmpresa} (${informePrevi.ofertaSeleccionada.cifEmpresa})
			</p>
			<p>
				<label>Amb valor:</label> ${informePrevi.ofertaSeleccionada.getPlicFormat()}
			</p>
			<p>
				<label>Termini:</label> ${informePrevi.ofertaSeleccionada.termini}
			</p>
			<p>
				<label>Tècnic:</label> ${informePrevi.ofertaSeleccionada.usuariCreacio.getNomComplet()}
			</p>
			<p>
				<label>Proposta tècnica:</label> ${informePrevi.ofertaSeleccionada.comentari} 
			</p>
			<c:forEach items="${informePrevi.propostaTecnica}" var="arxiu" >
				<c:set var="arxiu" value="${arxiu}" scope="request"/>
				<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		    </c:forEach>
		</div>	
		<div class="col-md-6">
			<h4>Informació actuació</h4>               		
	   		<div class="row">	
	   			<div class="col-md-12">    			
	    			<p>
						<label>Obejcte:</label> ${informePrevi.propostaInformeSeleccionada.objecte}
					</p>
					<p>
						<label>Total:</label> ${informePrevi.ofertaSeleccionada.getPlicFormat()}
					</p>
					<p>
						<label>Pagat:</label> ${informePrevi.getTotalFacturatFormat()}
					</p>
	       		</div>
	     	</div>
	     	<div class="row">
	     		<c:if test="${informePrevi.getEstat() != 'garantia' && informePrevi.ofertaSeleccionada.getPlic() > informePrevi.getTotalFacturat()}">
		       		<div class="col-md-6">
						<div class="form-group">
				    		<div class="col-md-12">
						       	<div class="row">
			                		<div class="form-group">
								        <div class="col-md-12">
								            <input id="marcarInformeAcabat" data-idinforme="${informePrevi.idInf}" class="btn btn-danger" value="Marcar com acabat">							            
								        </div>
								    </div> 
			                	</div>
			                </div>
			           	</div>
					</div>		
				</c:if>
	     	</div>
		</div>
	</div>
    <div class="row">
    	<div class="col-md-12">   
	    	<h4>Conforme Factura</h4>	
		    <p>
				<label>Import factura:</label> ${factura.valor}
			</p>
			<p>
				<label>Concepte:</label> ${factura.concepte}
			</p>
			<p>
				<label>Proveïdor:</label> ${factura.nomProveidor}
			</p>						       	
			<p>
				<label>Factura:	</label>	
				<c:forEach items="${factura.totsDocumentsFactura}" var="arxiu" >	
					<c:set var="arxiu" value="${arxiu}" scope="request"/>											
		        	<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
				</c:forEach>
		 	</p>
		</div> 	
    </div>
    
 	<c:if test="${canModificarFactura && factura.dataEnviatComptabilitat == null}">
 		<a href="enviarAComptabilitat?ref=${factura.idFactura}&idtasca=${tasca.idTasca}" class="btn btn-success margin_top30 upload" role="button">Enviar factura a comptabilitat</a>
	</c:if>        
</div>