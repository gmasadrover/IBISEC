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
		<label>Proposta tècnica: ${estatActuacio}</label> ${ofertaSeleccionada.comentari} 
	</p>
	<c:if test="${informePrevi.propostaTecnica.ruta != null}">
		<div class="panel-body">										    		
    		<div class="col-md-12">
    			<c:if test="${informePrevi.autoritzacioPropostaDespesa.ruta != null}">
               		<div class="document">
	               		<label>Autorització proposta despesa:</label>											                  	
		           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaDespesa.getEncodedRuta()}">
							${informePrevi.autoritzacioPropostaDespesa.nom}
						</a>	
						<c:if test="${informePrevi.autoritzacioPropostaDespesa.signat}">
								<span class="glyphicon glyphicon-pencil signedFile"></span>
						</c:if><br>
						<div class="infoSign hidden">
							<c:forEach items="${informePrevi.autoritzacioPropostaDespesa.firmesList}" var="firma" >
								<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
								<br>
							</c:forEach>
						</div>
					</div>	
				</c:if>            	
    		</div>
    	</div>
    </c:if>	
    <c:if test="${informePrevi.autoritzacioPropostaDespesa.ruta == null && actuacio.activa}">		
        <div class="row margin_bottom10">
	   		<div class="col-md-12 panel">
				<a target="_blanck" href="CrearDocument?tipus=autMen&idIncidencia=${incidencia.idIncidencia}&idActuacio=${actuacio.referencia}&idInforme=${informePrevi.idInf}" class="btn btn-success right" role="button">Generar autorització despesa actuació</a>
			</div>
  		</div>		
	    <form class="form-horizontal" target="_blank" method="POST" enctype="multipart/form-data" action="DoCanvisActuacio" onsubmit="setTimeout(function () { window.location.reload(); }, 10)"> 	
	     	<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
			<input type="hidden" name="idIncidencia" value="${informePrevi.idIncidencia}">															
			<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
			<input type="hidden" name="idTasca" value="${tasca.idTasca}">																		
			<div class="col-md-8">
				<div class="row margin_top10">
	    			<div class="col-md-12">
	           			Pujar autorització proposta despesa signada: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
	    			</div>
	    		</div>																													        			
     		</div>	
     		<div class="col-md-4">												        		
    		<div class="row">
        		<div class="col-md-12">															        																						 				
			 		<input class="btn btn-success margin_top30 upload" type="submit" name="aprovarPD" value="Autorització proposta despesa signat">
			 	</div>
     		</div>
    		</div>															     											    		
 		</form>	
	</c:if>	
	<c:if test="${informePrevi.autoritzacioPropostaDespesa.ruta != null && actuacio.activa}">		
		 <form class="form-horizontal" target="_blank" method="POST" enctype="multipart/form-data" action="DoCanvisActuacio" onsubmit="setTimeout(function () { window.location.reload(); }, 10)"> 	
	     	<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
			<input type="hidden" name="idIncidencia" value="${informePrevi.idIncidencia}">															
			<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
			<input type="hidden" name="idTasca" value="${tasca.idTasca}">	
			<div class="document">
           		<label>Resolució d'adjudicació:</label>											                  	
         			<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaDespesa.getEncodedRuta()}">
					${informePrevi.autoritzacioPropostaDespesa.nom}
				</a>	
				<c:if test="${informePrevi.autoritzacioPropostaDespesa.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
				</c:if>
				<c:if test="${informePrevi.autoritzacioPropostaDespesa.ruta != null}">
					<span data-ruta="${informePrevi.autoritzacioPropostaDespesa.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
				</c:if>
				<br>
				<div class="infoSign hidden">
					<c:forEach items="${informePrevi.autoritzacioPropostaDespesa.firmesList}" var="firma" >
						<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
						<br>
					</c:forEach>
				</div>	
			</div>																
			<div class="col-md-8">
				<div class="row margin_top10">
	    			<div class="col-md-12">
	           			Pujar resolució d'adjudicació signada: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
	    			</div>
	    		</div>																													        			
     		</div>	
     		<div class="col-md-4">												        		
    		<div class="row">
        		<div class="col-md-12">															        																						 				
			 		<input class="btn btn-success margin_top30 upload" type="submit" name="aprovarPD" value="Autorització adjudicació signada">
			 	</div>
     		</div>
    		</div>															     											    		
 		</form>	
	</c:if>	
</div>