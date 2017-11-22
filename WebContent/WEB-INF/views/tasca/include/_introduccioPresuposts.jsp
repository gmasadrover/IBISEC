<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<div id="liciMenor"></div>
<div class="panel-body">
	<h2>Informe inicial</h2>	
	<br />
	<p>
		<label>Informe:</label> ${informePrevi.idInf}
	</p>
	<p>
		<label>Tècnic:</label> ${informePrevi.usuari.getNomComplet()}
	</p>
	<p>
		<label>Data:</label> ${informePrevi.getDataCreacioString()}
	</p>
	<p>			                     				
		<label>Objecte:</label> ${informePrevi.propostaInformeSeleccionada.objecte}
	</p>	
	<p>			                     				
		<label>Comentari tècnic:</label> ${informePrevi.propostaInformeSeleccionada.comentari}
	</p>				                         	
	<p>
		<label>Tipus de contracte:</label> <m:message key="${informePrevi.propostaInformeSeleccionada.tipusObra}"/>
	</p>
	<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == 'obr'}">
		<div class="row">
			<div class="col-md-4">
				<label>Requereix llicència:</label> ${informePrevi.propostaInformeSeleccionada.llicencia ? informePrevi.propostaInformeSeleccionada.tipusLlicencia : "No"}
			</div>			
		</div>
		<p></p>
	</c:if>
	<p>
		<label>Requereix formalització contracte:</label> ${informePrevi.propostaInformeSeleccionada.contracte ? "Si" : "No"}
	</p>
	<p>
		<label>Termini d'execució:</label> ${informePrevi.propostaInformeSeleccionada.termini}
	</p>	
	<div class="row">
		<div class="col-md-4">
	       	<label>Pbase:</label> ${informePrevi.propostaInformeSeleccionada.pbase}€						                                
		</div>
		<div class="col-md-4"> 
			<label>IVA:</label> ${informePrevi.propostaInformeSeleccionada.iva}€
		</div>
		<div class="col-md-4">
			<label>Plic:</label> ${informePrevi.propostaInformeSeleccionada.plic}€
	   	</div>					  
	</div>	
	<p></p>
	<p>
		<label>Arxius ajunts:</label>
	</p>	
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.adjunts}" var="arxiu" >
			<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.nom}</a>
			<br>
		</c:forEach>					            		
	</div>
	<p>
		<label>Comentari Cap:</label> ${informePrevi.comentariCap}
	</p>
	<p>
		<label>Vistiplau:</label> ${informePrevi.usuariCapValidacio.getNomComplet()} - ${informePrevi.getDataCapValidacioString()}
	</p>
	<p>			                     				
		<label>Notes:</label> ${informePrevi.notes}
	</p>
	<p>
		<label>Partida:</label> ${informePrevi.partida}
	</p>
</div>
<div class="panel-body">
	<div id="afegirOfertes"></div>
	<h2>Recerca presuposts</h2>
	<p></p>
	<form class="form-horizontal margin_top30" enctype="multipart/form-data" method="POST" action="DoAddOferta">
		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
		<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
		<input type="hidden" name="idProposta" value="${informePrevi.idInf}">
   		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
   		<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${informePrevi.idInf}">				                    		
   		<div class="form-group">
        	<div class="col-md-4">	
         		<label>Empresa</label>									            	 										            	 	
            	<select class="selectpicker" name="llistaEmpreses" id="llistaEmpreses" data-live-search="true" data-size="10">						                                					                                	
               		<c:forEach items="${empresesList}" var="empresa">
                   		<option value="${empresa.cif}">${empresa.name}</option>
                   	</c:forEach>	
                </select>	
        	</div>
         	<div class="col-md-4">
           		<label>Oferta (amb IVA)</label>
            	<input name="oferta" id="oferta" placeholder="0000.00">
            	<label class="">€</label>
           	</div>	
           	<div class="col-md-4">												        
            	<input class="btn btn-primary" type="submit" name="afegirOferta" value="Afegir oferta">
			</div>				                       		
   		</div>
   		<div class="form.group">
   			<div class="col-md-2">
           		<label>Presupost:</label>
           	</div>
            <div class="col-md-5">   
                <input type="file" class="btn" name="file" /><br/>
			</div> 
   		</div>
   	</form>
   	
 		<div class="form-group">
 			<div class="hidden" id="llistatOfertes">
 				<c:forEach items="${informePrevi.llistaOfertes}" var="oferta" >
 					<input name="ofertes" value="${oferta.cifEmpresa}#${oferta.plic}">
 				</c:forEach>
 			</div>  			
    		<div class="col-md-12">	
			<label>Resum presupostos</label>							                        
               <div class="table-responsive">							                        
                   <table class="table table-striped table-bordered filerTable" id="ofertaTable">
                       <thead>
                           <tr>
                           	<th>Oferta</th>
                               <th>Licitador</th>
                               <th>Licitador</th>
                               <th>Licitador</th>
                               <th>Import Oferta</th>
                               <th>Import Oferta</th>
                               <th>Presupost</th>
                               <th>Control</th>							                                        							                                       
                           </tr>
                       </thead>
                       <tbody>	
                       	<c:forEach items="${informePrevi.llistaOfertes}" var="oferta" >
                       		<tr>
                       			<td>${oferta.idOferta}</td>
                        		<td><a target="_blanck" href="empresa?cif=${oferta.cifEmpresa}">${oferta.nomEmpresa} (${oferta.cifEmpresa})</a></td>
                        		<td>${oferta.nomEmpresa} (${oferta.cifEmpresa})</td>
                        		<td>${oferta.cifEmpresa}</td>
                        		<td>${oferta.plic} €</td>
                        		<td>${oferta.plic}</td>
                        		<td><a target="_blanck" href="downloadFichero?ruta=${oferta.presupost.getEncodedRuta()}">${oferta.presupost.nom}</a></td>
                        		<td>
                        			<input class="btn btn-danger btn-sm deleteOferta" data-idoferta="${oferta.idOferta}" type="button" value="Eliminar">
                        			<c:if test="${canRealitzarPropostaTecnica}">
                        				<input class="btn btn-primary btn-sm ofertaSeleccionada" type="button" value="Seleccionar">
                        			</c:if>	                        			
                        		</td>
                       		</tr>
                       	</c:forEach>						                                	                              	
                       </tbody>
                   </table>
               </div>
          	</div>
	</div>
	<form class="form-horizontal margin_top30" method="POST" action="DoAddPropostaTecnica">
		<input type="hidden" name="idOfertaSeleccionada" id="idOfertaSeleccionada" value="">
   		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
   		<input type="hidden" name="idIncidencia" value="${actuacio.idIncidencia}">
   		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
   		<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${informePrevi.idInf}">	
   		<input type="hidden" name="importReserva" value="${informePrevi.propostaInformeSeleccionada.plic}">		
		<c:if test="${canRealitzarPropostaTecnica}">
			<div class="form-group">
	        	<div class="col-md-6">
	            	<h2>Proposta tècnica</h2>	
	           		<label>Oferta seleccionada: </label>
	           		<label id="ofertaSeleccionada">${informePrevi.ofertaSeleccionada.nomEmpresa} (${informePrevi.ofertaSeleccionada.cifEmpresa})</label>	           		
	           	</div>	         	
	       	</div>					                    	
	       	<div class="form-group">					                    			
	        	<div class="col-md-12">							                    			
	     			<div class="row">	 
	     				<div class="col-md-12">						                    						
	     					<textarea class="form-control" name="propostaTecnica" placeholder="Proposta tècnica" rows="3" required>${informePrevi.ofertaSeleccionada.comentari}</textarea> 
	       				</div>
	       			</div>
	       		</div>						                       		
	       	</div>	
	      	<div class="form-group">
	        	<div class="col-md-6">
	            	<label>Termini d'execució definitiu</label>
	             	<input name="termini" placeholder="1 mes" value="${informePrevi.ofertaSeleccionada.termini == '' ? informePrevi.propostaInformeSeleccionada.termini : informePrevi.ofertaSeleccionada.termini}" required>
	        	</div>
	       	</div>
	       	<div class="form-group">
	        	<div class="col-md-6">
	       			<div class="row">
	           			<div class="col-md-12">
	                  		<input class="btn btn-primary" type="submit" name="guardar" value="Generar proposta tècnica">
						</div>
	       			</div>
	   			</div>		   										    
    		</div> 
		</c:if>	     	                     	
	</form>
	<div class="separator"></div>												        	
	<div class="panel-body">
     	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA">
	     	<input type="hidden" name="document" value="propostaTecnica">
			<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
			<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
			<input type="hidden" name="idTasca" value="${tasca.idTasca}">
			<input type="hidden" name="idInforme" value="${informePrevi.idInf}">																	
	       	<c:if test="${informePrevi.propostaTecnica.ruta != null}">
				<div class="col-md-12">	
	               	<p>Proposta Tècnica signada:</p>													                  	
	           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaTecnica.getEncodedRuta()}">
						${informePrevi.propostaTecnica.nom}
					</a>																			
				</div>
			</c:if>																	
			<div class="col-md-8">
				<div class="row margin_top10">
	    			<div class="col-md-12">
	           			Pujar proposta tècnica signada: <input type="file" class="btn" name="informe" /><br/>																 		
	    			</div>
	    		</div>																													        			
      		</div>	
      		<div class="col-md-4">												        		
    		<div class="row">
        		<div class="col-md-12">															        																						 				
			 		<input class="btn btn-success margin_top30" type="submit" name="guardar" value="Enviar proposta tècnica signada">
			 	</div>
     		</div>
    		</div>
  		</form>	
  	</div>  	
</div>