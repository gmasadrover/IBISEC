<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div id="propostaActuacio" class="">
	<input type="hidden" name="idProposta" value="${propostaActuacio.idProposta}">	
	<div class="form-group">
		<div class="col-md-6">
			<p>Arxius adjunts:</p>
                	<c:forEach items="${informePrevi.adjunts}" var="arxiu" >
           		<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
					${arxiu.nom}
				</a>
				<a href="#"><span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
				<br>
			</c:forEach>
		</div>
  			<div class="col-md-6">
         			<input type="file" class="btn" name="informe" /><br/>																 		
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
                	<option value="major">Llicència</option>
                	<option value="comun">Comunicació prèvia</option>
                </select>
           	</div>
           	<div class="col-md-3">
      	 		<label>Formalització contracte</label>
      	 		<input type="hidden" id="formContractePrev" value="${propostaActuacio.contracte ? 'si' : 'no'}" >
                <select class="form-control selectpicker" name="formContracte" id="formContracte">
                	<option value="si">Si</option>
                	<option value="no">No</option>
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
	