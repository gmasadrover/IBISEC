<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<h4>Proposta tècnica</h4>
<div class="table-responsive">                        
	<table class="table table-striped table-bordered">
	    <thead>
	        <tr>
	            <th>Oferta</th>
	            <th>Licitador</th>			                                        
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${ofertes}" var="oferta" >
				<tr ${oferta.seleccionada ? "class='success'" : ""}>	
					<td>${oferta.getPlicFormat()}</td>							          	
					<td><a href='editEmpresa?cif=${oferta.cifEmpresa}'>${oferta.nomEmpresa} (${oferta.cifEmpresa})</a></td>
	    		</tr>
			</c:forEach>
		</tbody>
	</table>
 </div>
<p>
	<label>L'empresa adjudicataria:</label> ${ofertaSeleccionada.nomEmpresa} (${ofertaSeleccionada.cifEmpresa})
</p>
<p>
	<label>Amb valor:</label> ${ofertaSeleccionada.getPlicFormat()}
</p>
<p>
	<label>Termini:</label> ${ofertaSeleccionada.termini}
</p>
<p>
	<label>Tècnic:</label> ${ofertaSeleccionada.usuariCreacio.getNomComplet()}
</p>
<p>
	<label>Proposta tècnica:</label> ${ofertaSeleccionada.comentari}
</p>
<c:if test="${esCap}">
<form class="form-horizontal margin_top30" method="POST" action="DoAddPresuposts">
	<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
	<input type="hidden" name="idTasca" value="${tasca.idTasca}">
	<div class="form-group">  	
		<div class="col-lg-6">
	 		<div class="row">
	     		<div class="col-lg-12">
	           		<input class="btn btn-success" type="submit" name="enviar" value="Aprovar">
				</div>
	  		</div>
		</div>											    
	</div>
</form>	
</c:if> 
<c:if test="${!esCap}">
<p>
	<label>Aprovada:</label> ${ofertaSeleccionada.usuariAprovacio.getNomComplet()} - ${ofertaSeleccionada.getDataAprovacioString()}
</p>
</c:if>