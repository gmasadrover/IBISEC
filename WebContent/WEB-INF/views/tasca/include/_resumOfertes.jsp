<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<div class="panel-body">
	<h4>Proposta de despesa</h4>
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
						<td><a href='empresa?cif=${oferta.cifEmpresa}'>${oferta.nomEmpresa} (${oferta.cifEmpresa})</a></td>
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
		<input type="hidden" name="idInformePrevi" value="${tasca.idinforme}">
		<div class="form-group">  	
			<div class="col-lg-6">
		 		<div class="row">
		     		<div class="col-lg-12">
		           		<input class="btn btn-success" type="submit" name="enviar" value="Vistiplau">
					</div>
		  		</div>
			</div>											    
		</div>
	</form>	
	</c:if> 
	<c:if test="${!esCap}">
	<p>
		<label>Vistiplau:</label> ${ofertaSeleccionada.usuariCapValidacio.getNomComplet()} - ${ofertaSeleccionada.getDataCapValidacioString()}
	</p>
	</c:if>
</div>