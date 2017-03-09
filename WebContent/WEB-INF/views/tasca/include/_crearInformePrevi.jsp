<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<input type="hidden" id="infPrev">
<div class="panel-body">
	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddInforme">
		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
		<input type="hidden" name="idIncidencia" value="${incidencia.idIncidencia}">
		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
		<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${informePrevi.idInf}">						                    		
		<div class="form-group">
	     	<div class="col-lg-2">
	     	 	<label>Tipus de Contracte</label>
	     	 	<input type="hidden" id="tipusContractePrev" value="${informePrevi.tipusObra}" >									            	 	
	            <select class="form-control selectpicker" name="tipusContracte" id="tipusContracte">
		           	<option value="obr">Obra</option>
		           	<option value="srv">Servei</option>
		           	<option value="submi">subministrament</option>
	            </select>
	        </div>	
	       	<div class="visibleObres">					                             	
	        	<div class="col-lg-2">
		      	 	<label>Llicència</label>
		      	 	<input type="hidden" id="reqLlicenciaPrev" value="${informePrevi.llicencia ? 'si' : 'no'}" >
		            <select class="form-control selectpicker" name="reqLlicencia" id="reqLlicencia">
		            	<option value="si">Si</option>
		            	<option value="no">No</option>
		            </select>
	            </div>	
	            <div class="col-lg-3 visibleTipusLlicencia">
		      	 	<label>Tipus de llicència</label>
		      	 	<input type="hidden" id="tipusLlicenciaPrev" value="${informePrevi.tipusLlicencia}" >
	                <select class="form-control selectpicker" name="tipusLlicencia" id="tipusLlicencia">
	                	<option value="major">Major</option>
	                	<option value="menor">menor</option>
	                	<option value="comun">Comunicació prèvia</option>
	                </select>
	           	</div>
	           	<div class="col-lg-3">
	      	 		<label>Formalització contracte</label>
	      	 		<input type="hidden" id="formContractePrev" value="${informePrevi.contracte ? 'si' : 'no'}" >
	                <select class="form-control selectpicker" name="formContracte" id="formContracte">
	                	<option value="si">Si</option>
	                	<option value="no">No</option>
	                </select>
	      		</div>
			</div>				                       																
		</div>					                    						                    		
		<div class="form-group">
			<div class="col-lg-12">					                    			
				<label>Objecte</label>
				<textarea class="form-control" name="objecteActuacio" placeholder="objecte de l'actuació" rows="3" required>${informePrevi.objecte}</textarea>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-12">
				<label>Pressupost</label>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-4">
	          	<label>VEC</label>
	          	<input class="" name="vec" id="vec" placeholder="0000,00" value="${informePrevi.vec}" required>
	          	<label class="">€</label>
	        </div>
	        <div class="col-lg-4">
		     	<label>IVA</label>
		       	<input disabled id="iva" placeholder="0000,00" value="${informePrevi.iva}">
		     	<input type="hidden" name="iva" id="inputIVA" value="${informePrevi.iva}">
		       	<label class="">€</label>
			</div>
			<div class="col-lg-4">
				<label>PLic</label>
				<input disabled id="plic" placeholder="0000,00" value="${informePrevi.plic}">
				<input type="hidden" name="plic" id="inputPLIC" value="${informePrevi.plic}">
				<label class="">€</label>
			</div>					                                
		</div>
		<div class="form-group">
			<div class="col-lg-6">
				<label>Termini d'execució</label>
				<input name="termini" placeholder="1 mes" value="${informePrevi.termini}" required>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-2">
				<label>Servei</label>
				<select class="form-control selectpicker" name="tipusServei">
					<option value="pos">POS</option>
					<option value="im">IM</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-12">		
				<div class="row">	 
					<div class="col-lg-12">						                    						
	          			<textarea class="form-control" name="comentariTecnic" placeholder="comentari tècnic" rows="3" required>${informePrevi.comentari}</textarea> 
	            	</div>
	        	</div>
	        	<div class="row">
	        		<div class="col-lg-12">	
	                	<p>Arxius adjunts:</p>
	                  	<c:forEach items="${informePrevi.adjunts}" var="arxiu" >
		            		<a  href="downloadFichero?ruta=${arxiu.ruta}">
								${arxiu.nom}
							</a>
							<a href="#"><span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
							<br>
						</c:forEach>
					</div>
                </div>	  
	        	<div class="row margin_top10">
	    			<div class="col-lg-12">
	    				<label>Informe</label>	
	           			<input type="file" class="btn" name="informe" /><br/>																 		
	    			</div>
	    		</div>
			</div>						                       		
		</div>	
		<div class="form-group">
			<div class="col-lg-12">		
				<div class="row">	 
					<div class="col-lg-12">						                    						
	          			<textarea class="form-control" name="comentariCap" placeholder="comentari cap" rows="3" required>${informePrevi.comentariCap}</textarea> 
	            	</div>
	        	</div>
        	</div>
       	</div>		
		<div class="form-group">
			<div class="col-lg-6">
	    		<div class="row">
	        		<div class="col-lg-12">
	              		<input class="btn btn-primary" type="submit" name="guardar" value="${informePrevi.idInf != 0 ? "Modificar" : "Guardar"} proposta d'actuació">
					</div>
	     		</div>
	 		</div>
	 		<c:if test="${esCap && (informePrevi.idInf != 0)}">
	 			<div class="col-lg-6">
		    		<div class="row">
		        		<div class="col-lg-12">
		              		<input class="btn btn-success" type="submit" name="enviar" value="Vistiplau">
						</div>
		     		</div>
		 		</div>
	 		</c:if>	 		
		</div>	                       	
	</form>
</div>