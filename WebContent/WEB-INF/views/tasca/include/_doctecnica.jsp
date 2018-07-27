<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div class="panel-body">
 	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA"> 	
		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
		<input type="hidden" name="idIncidencia" value="${actuacio.idIncidencia}">	
		<input type="hidden" id="infPrev" name=infPrev value="${propostesInformeList.size()}">														
		<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
		<input type="hidden" name="document" value="docTecnica">	
		<c:set var="propostaActuacio" value="${propostesInformeList[0]}" scope="request"/> 	
		<c:if test="${propostaActuacio.pbase > 0}">
			<div class="col-md-12" id="">
				<div id="propostaActuacio" class="">
					<input type="hidden" name="idProposta" value="${propostaActuacio.idProposta}">	
					<div class="form-group">
						<div class="col-md-12">
							<p>Arxius adjunts:</p>
							<c:forEach items="${informePrevi.informesPrevis}" var="arxiu" >
				           		<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
									${arxiu.getDataString()} - ${arxiu.nom}
								</a>				
								<br>
							</c:forEach>				          
						</div>		
					</div>	
					<div class="form-group">
						<div class="col-md-4">
							<label>Expedient: ${informePrevi.expcontratacio.expContratacio}</label>
						</div>
					</div>			
					<div class="form-group">
						<div class="col-md-4">
							<label>Tècnic</label>	
							<input type="hidden" id="tecnicPrevi" value="${informePrevi.usuari.idUsuari}" >														            	 										            	 	
			                <select class="form-control selectpicker" data-live-search="true" data-size="5" name="llistaUsuaris" id="llistaUsuaris">
			                	<c:forEach items="${llistaUsuaris}" var="usuari">
			                		<option value="${usuari.idUsuari}">${usuari.getNomCompletReal()}</option>
			                	</c:forEach>					                                	
			                </select>	
						</div>
					</div>							                    		
					<div class="form-group">
				    	<div class="col-md-3">
				   	 		<label>Tipus de Contracte</label>
				   	 		<input type="hidden" id="tipusContractePrev" value="${propostaActuacio.tipusObra}" >									            	 	
				            <select class="form-control selectpicker" name="tipusContracte" id="tipusContracte">
					           	<option value="obr">Obra</option>
					           	<option value="srv">Servei</option>
					           	<option value="submi">subministrament</option>
				            </select>
				       	</div>	
				    	<div class="visibleObres visibleObres">					                             	
				       		<div class="col-md-3">
					      	 	<label>Autorització urbanística</label>
					      	 	<input type="hidden" id="reqLlicenciaPrev" value="${propostaActuacio.llicencia ? 'si' : 'no'}" >
					            <select class="form-control selectpicker" name="reqLlicencia" id="reqLlicencia">
					            	<option value="si">Si</option>
					            	<option value="no">No</option>
					            </select>
				           </div>	
				           <div class="col-md-3 visibleTipusLlicencia visibleTipusLlicencia">
					      	 	<label>Tipus</label>
					      	 	<input type="hidden" id="tipusLlicenciaPrev" value="${propostaActuacio.tipusLlicencia}" >
					           	<select class="form-control selectpicker" name="tipusLlicencia" id="tipusLlicencia">
					               	<option value="llicencia">Llicència</option>
					               	<option value="comun">Comunicació prèvia</option>
					         	</select>
					     	</div>
						</div>				    				                       																
					</div>					                    						                    		
					<div class="form-group">
						<div class="col-md-12">					                    			
							<label>Objecte</label>
							<textarea class="form-control" name="objecteActuacio" placeholder="objecte de l'actuació" rows="3" required>${propostaActuacio.objecte}</textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-12">
							<label>Pressupost</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4">
				          	<label>PBase</label>
				          	<input name="pbase" class="pbase" id="pbase" placeholder="0000,00" value="${propostaActuacio.pbase}" required>
				          	<label class="">€</label>
				        </div>
				        <div class="col-md-4">
					     	<label>IVA</label>
					       	<input disabled id="iva" class="iva" placeholder="0000,00" value="${propostaActuacio.iva}">
					     	<input type="hidden" name="iva" class="inputIVA" id="inputIVA" value="${propostaActuacio.iva}">
					       	<label class="">€</label>
						</div>
						<div class="col-md-4">
							<label>PLic</label>
							<input name="plic" id="plic" class="plic" placeholder="0000,00" value="${propostaActuacio.plic}">						
							<label class="">€</label>
						</div>					                                
					</div>
					<div class="form-group">
						<div class="col-md-6">
							<label>Termini d'execució</label>
							<input name="termini" placeholder="" value="${propostaActuacio.termini}" required>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-12">		
							<div class="row">	 
								<div class="col-md-12">						                    						
				          			<textarea class="form-control" name="comentariTecnic" placeholder="comentari tècnic" rows="3">${propostaActuacio.comentari}</textarea> 
				            	</div>
				        	</div>	        	
						</div>						                       		
					</div>
				</div>																		    	
			</div>		
		</c:if>	
		<c:forEach items="${informePrevi.docTecnica}" var="arxiu" >
          	<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
				${arxiu.getDataString()} - ${arxiu.nom}
			</a>				
			<br>
		</c:forEach>			
		<div class="form-group">			
			<div class="col-md-12">
				<input type="file" class="btn" name="informe" multiple/><br/>																 		
			</div>	
		</div>		
		<div class="form-group">
			<div class="col-md-12">
				<div class="col-md-3">
		    		<div class="row">
		        		<div class="col-md-12">
		        			<input class="btn btn-primary" type="submit" name="guardar" value="Actualitzar">
					 	</div>
		     		</div>																	     		
		 		</div>	
		 		<c:if test="${esCap}">
			 		<div class="col-md-offset-5 col-md-4">
			    		<div class="row">
			        		<div class="col-md-12">
			        			<input class="btn btn-success" type="submit" name="tramitar" value="Enviar documentació a gerència per licitar">
						 	</div>
			     		</div>																	     		
			 		</div>	
			 	</c:if>														 			
	 		</div>															        		
		</div>
	</form>
</div>