<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>			                         	
<div class="panel-body">
	<h4>Procediment</h4>	
	<br />		      	
	<p>			                     				
		<label>Objecte demanda:</label> ${procediment.objecteDemanda}
	</p>
	<p>
		<label>Demandant:</label> ${procediment.demandant}
	</p>	
	<p>
		<label>Demandat:</label> ${procediment.demandat}
	</p>		                         	
	<h4>Tramitació</h4>	
	<br />	
	<p>			                     				
		<label>Descripció:</label> ${tramitacioJudicial.descripcio}
	</p>
	<p>
		<label>Quantia:</label> ${tramitacioJudicial.quantia}	
	</p>	
	<c:forEach items="${tramitacioJudicial.documentsList}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	       		
	</c:forEach>	
	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoTasca">														
		<input type="hidden" name="idTramitacio" value="${tramitacioJudicial.idTramitacio}">
		<input type="hidden" name="idTasca" value="${tasca.idTasca}">		
		<input type="hidden" name="idProcediment" value="${procediment.referencia}">		
		<input type="hidden" name="document" value="pagamentJudicial">															
		<div class="col-md-8">
			<div class="row margin_top10">
    			<div class="col-md-12">
           			Pujar documentació: <input type="file" class="btn uploadImage" name="informe" multiple/><br/>																 		
    			</div>
    		</div>																													        			
    	</div>	
    	<div class="col-md-4">												        		
   			<div class="row">
	       		<div class="col-md-12">															        																						 				
			 		<input class="btn btn-success margin_top30 upload" type="submit" name="aprovarPD" value="Pujar documentacio">
			 	</div>
    		</div>
   		</div>															     											    		
	</form>		        
 </div>
 <div class="panel-body">
 	<form class="form-horizontal" target="_blank" method="POST" action="DoAddReserva" onsubmit="setTimeout(function () { window.location.reload(); }, 10)">
		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
   		<input type="hidden" name="idprocediment" id="idprocediment" value="${procediment.referencia}">	
   		<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${procediment.referencia}">   
   		<input type="hidden" name="idTramit" id="idTramit" value="${tramitacioJudicial.idTramitacio}">   		
   		<input type="hidden" name="importReserva" value="${tramitacioJudicial.quantia}">	   						                    		
   		<div class="form-group">
    		<div class="col-md-4">	
      			<label>Partida asignada</label>									            	 										            	 	
                <select class="form-control selectpicker" name="llistaPartides" id="llistaPartides">
                	<c:forEach items="${partidesList}" var="partida">
                		<option value="${partida.codi}">${partida.codi} (${partida.nom} - Restant: ${partida.getPartidaPerAsignarFormat()})</option>
                	</c:forEach>					                                	
                </select>	
            </div>					                       		
       	</div>	
   		<div class="form-group">
    		<div class="col-md-12">		
    			<div class="row">	 
    				<div class="col-md-12">						                    						
    					<textarea class="form-control" name="comentariFinancer" placeholder="observacions" rows="3"></textarea> 
      				</div>
      			</div>
      		</div>						                       		
       	</div>	
       	<div class="form-group">
       		<div class="col-md-6">
		        <div class="row">
		            <div class="col-md-12">
						<input class="btn btn-success" type="submit" name="reservar" value="Generar conforme àrea econòmico-financera">
					</div>
		        </div>
	    	</div>
		    <div class="col-md-6">
		        <div class="row">
		            <div class="col-md-12">
						<input class="btn btn-danger" type="submit" name="rebutjar" value="Generar no conforme">
					</div>
		        </div>
		    </div>
		</div>	                       	
	</form>	
	<div class="separator"></div>												        	
	<div class="panel-body">
     	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoTasca">
	     	<input type="hidden" name="document" value="autoritzacioAreaFinanceraJudicial">
			<input type="hidden" name="idTasca" value="${tasca.idTasca}">
   			<input type="hidden" name="idprocediment" id="idprocediment" value="${procediment.referencia}">	
   			<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${procediment.referencia}">   
   			<input type="hidden" name="idTramit" id="idTramit" value="${tramitacioJudicial.idTramitacio}">   		
   			<input type="hidden" name="importReserva" value="${tramitacioJudicial.quantia}">																		
	       	<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.ruta}">
				<div class="col-md-12">	
	               	<p>Vistiplau proposta d'actuació signada:</p>													                  	
	           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.conformeAreaEconomivaPropostaActuacio.getEncodedRuta()}">
						${informePrevi.conformeAreaEconomivaPropostaActuacio.nom}
					</a>																			
				</div>
			</c:if>																	
			<div class="col-md-8">
				<div class="row margin_top10">
	    			<div class="col-md-12">
	           			Pujar Autorització àrea econòmica signada: <input type="file" class="btn uploadImage" name="informe" /><br/>																 		
	    			</div>
	    		</div>																													        			
      		</div>	
      		<div class="col-md-4">												        		
    		<div class="row">
        		<div class="col-md-12">															        																						 				
			 		<input class="btn btn-success margin_top30 upload" type="submit" name="guardar" value="Enviar Autorització àrea econòmica signat">
			 	</div>
     		</div>
    		</div>
  		</form>	
  	</div>
 </div>