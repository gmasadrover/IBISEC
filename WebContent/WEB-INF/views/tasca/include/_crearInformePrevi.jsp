<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div class="panel panel-default">
   	<div class="panel-heading">
		<h4 class="panel-title">
			<a data-toggle="collapse" data-parent="#accordion" href="#propostaActuacio${numPA}">Proposta actuació ${numPA}</a>
		</h4>
	</div>
	<div id="propostaActuacio${numPA}" class="panel-collapse collapse">					    	
		<div class="panel-body">				      	
			<input type="hidden" name="idProposta${numPA}" value="${propostaActuacio.idProposta}">		
			<div class="col-md-12 panel-group" id="accordionInformes">											                    		
				<div class="form-group">
		     		<div class="col-md-2">
		     	 		<label>Tipus de Contracte</label>
		     	 		<input type="hidden" id="tipusContractePrev${numPA}" value="${propostaActuacio.tipusObra}" >									            	 	
			            <select class="form-control selectpicker" name="tipusContracte${numPA}" id="tipusContracte${numPA}">
				           	<option value="obr">Obra</option>
				           	<option value="srv">Servei</option>
				           	<option value="submi">subministrament</option>
			            </select>
		        	</div>	
		       		<div class="visibleObres visibleObres${numPA}">					                             	
			        	<div class="col-md-2">
				      	 	<label>Llicència</label>
				      	 	<input type="hidden" id="reqLlicenciaPrev${numPA}" value="${propostaActuacio.llicencia ? 'si' : 'no'}" >
				            <select class="form-control selectpicker" name="reqLlicencia${numPA}" id="reqLlicencia${numPA}">
				            	<option value="si">Si</option>
				            	<option value="no">No</option>
				            </select>
			            </div>	
			            <div class="col-md-3 visibleTipusLlicencia visibleTipusLlicencia${numPA}">
				      	 	<label>Tipus de llicència</label>
				      	 	<input type="hidden" id="tipusLlicenciaPrev${numPA}" value="${propostaActuacio.tipusLlicencia}" >
			                <select class="form-control selectpicker" name="tipusLlicencia${numPA}" id="tipusLlicencia${numPA}">
			                	<option value="major">Major</option>
			                	<option value="menor">menor</option>
			                	<option value="comun">Comunicació prèvia</option>
			                </select>
			           	</div>
			           	<div class="col-md-3">
			      	 		<label>Formalització contracte</label>
			      	 		<input type="hidden" id="formContractePrev${numPA}" value="${propostaActuacio.contracte ? 'si' : 'no'}" >
			                <select class="form-control selectpicker" name="formContracte${numPA}" id="formContracte${numPA}">
			                	<option value="si">Si</option>
			                	<option value="no">No</option>
			                </select>
			      		</div>
					</div>				                       																
				</div>					                    						                    		
				<div class="form-group">
					<div class="col-md-12">					                    			
						<label>Objecte</label>
						<textarea class="form-control" name="objecteActuacio${numPA}" placeholder="objecte de l'actuació" rows="3" required>${propostaActuacio.objecte}</textarea>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-12">
						<label>Pressupost</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-4">
			          	<label>VEC</label>
			          	<input name="vec${numPA}" class="vec" id="vec${numPA}" placeholder="0000,00" value="${propostaActuacio.vec}" required>
			          	<label class="">€</label>
			        </div>
			        <div class="col-md-4">
				     	<label>IVA</label>
				       	<input disabled id="iva${numPA}" class="iva" placeholder="0000,00" value="${propostaActuacio.iva}">
				     	<input type="hidden" name="iva${numPA}" class="inputIVA" id="inputIVA${numPA}" value="${propostaActuacio.iva}">
				       	<label class="">€</label>
					</div>
					<div class="col-md-4">
						<label>PLic</label>
						<input name="plic${numPA}" id="plic${numPA}" class="plic" placeholder="0000,00" value="${propostaActuacio.plic}">						
						<label class="">€</label>
					</div>					                                
				</div>
				<div class="form-group">
					<div class="col-md-6">
						<label>Termini d'execució</label>
						<input name="termini${numPA}" placeholder="" value="${propostaActuacio.termini}" required>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-12">		
						<div class="row">	 
							<div class="col-md-12">						                    						
			          			<textarea class="form-control" name="comentariTecnic${numPA}" placeholder="comentari tècnic" rows="3">${propostaActuacio.comentari}</textarea> 
			            	</div>
			        	</div>	        	
					</div>						                       		
				</div>					
			</div>
		</div>
	</div>
</div>
	