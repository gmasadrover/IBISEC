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
		            <th>Arxiu</th>	                                        
		        </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${ofertes}" var="oferta" >
					<tr ${oferta.seleccionada ? "class='success'" : ""}>	
						<td>${oferta.getPlicFormat()}</td>							          	
						<td><a href='empresa?cif=${oferta.cifEmpresa}'>${oferta.nomEmpresa} (${oferta.cifEmpresa})</a></td>
						<td><a target="_blanck" href="downloadFichero?ruta=${oferta.presupost.getEncodedRuta()}">${oferta.presupost.nom}</a></td>
		    		</tr>
				</c:forEach>
			</tbody>
		</table>
	 </div>
	<p>
		<label>L'empresa adjudicataria:</label> ${ofertaSeleccionada.nomEmpresa} (${ofertaSeleccionada.cifEmpresa})
	</p>
	<c:if test="${ofertaSeleccionada.capDobra != null && ofertaSeleccionada.capDobra != ''}">
		<p>
			<label>Cap d'obres designat:</label> ${ofertaSeleccionada.capDobra}
		</p>
	</c:if>	
	<c:if test="${ofertaSeleccionada.personalInscrit.ruta != null}">
		<p>
			<div class="document">
	        	<label>Personal inscrit:</label>											                  	
         		<a target="_blanck" href="downloadFichero?ruta=${ofertaSeleccionada.personalInscrit.getEncodedRuta()}">
					${ofertaSeleccionada.personalInscrit.nom}
				</a>	
			</div>	
		</p>
	</c:if>
	<p>
		<label>Amb valor:</label> ${ofertaSeleccionada.getPlicFormat()}
	</p>
	<p>
		<label>Termini:</label> ${ofertaSeleccionada.termini}
	</p>
	<p>
		<label>Tècnic:</label> ${informePrevi.usuari.getNomComplet()}
	</p>
	<p>
		<label>Motivació adjudicació:</label> ${ofertaSeleccionada.comentari} 
	</p>
	<c:if test="${informePrevi.propostaTecnica.ruta != null && esCap}">
		<div class="separator"></div>												        	
		<div class="panel-body">
	     	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA">
		     	<input type="hidden" name="document" value="vitiplauPropostaTecnica">
				<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
				<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
				<input type="hidden" name="idTasca" value="${tasca.idTasca}">
				<input type="hidden" name="idInforme" value="${informePrevi.idInf}">																	
		       	<c:if test="${informePrevi.propostaTecnica.ruta != null}">
					<div class="col-md-12">	
		               	<p>Proposta Tècnica:</p>													                  	
		           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaTecnica.getEncodedRuta()}">
							${informePrevi.propostaTecnica.nom}
						</a>																			
					</div>
				</c:if>																	
				<div class="col-md-8">
					<div class="row margin_top10">
		    			<div class="col-md-12">
		           			Pujar vistiplau proposta tècnica signada: <input type="file" class="btn" name="informe" /><br/>																 		
		    			</div>
		    		</div>																													        			
	      		</div>	
	      		<div class="col-md-4">												        		
	    		<div class="row">
	        		<div class="col-md-12">															        																						 				
				 		<input class="btn btn-success margin_top30" type="submit" name="guardar" value="Enviar vistiplau proposta tècnica signada">
				 	</div>
	     		</div>
	    		</div>
	  		</form>	
	  	</div>	
	</c:if>	
</div>