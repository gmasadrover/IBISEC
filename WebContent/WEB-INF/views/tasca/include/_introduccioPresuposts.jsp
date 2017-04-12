<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<input type="hidden" id="liciMenor">
<jsp:include page="_resumInformePrevi.jsp"></jsp:include>
<div class="panel-body">
	<h2>Recerca presuposts</h2>
	<p></p>
	<form class="form-horizontal margin_top30" method="POST" action="DoAddPresuposts">
		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
   		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
   		<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${informePrevi.idInf}">	
   		<input type="hidden" name="importReserva" value="${informePrevi.plic}">					                    		
   		<div class="form-group">
        	<div class="col-lg-4">	
         		<label>Empresa</label>									            	 										            	 	
            	<select class="selectpicker" name="llistaEmpreses" id="llistaEmpreses" data-live-search="true" data-size="10">						                                					                                	
               		<c:forEach items="${empresesList}" var="empresa">
                   		<option value="${empresa.cif}">${empresa.name}</option>
                   	</c:forEach>	
                </select>	
        	</div>
         	<div class="col-lg-4">
           		<label>Oferta</label>
            	<input name="oferta" id="oferta" placeholder="0000,00" required>
            	<label class="">€</label>
           	</div>	
           	<div class="col-lg-4">												        
            	<input class="btn btn-primary" type="button" name="afegirOferta" id="afegirOferta" value="Afegir oferta">
			</div>				                       		
   		</div>
  		<div class="form-group">
     		<div class="col-lg-10">	
				<label>Resum presupostos</label>							                        
                <div class="table-responsive">							                        
                    <table class="table table-striped table-bordered filerTable" id="ofertaTable">
                        <thead>
                            <tr>
                                <th>Licitador</th>
                                <th>Licitador</th>
                                <th>Licitador</th>
                                <th>Import Oferta</th>
                                <th>Import Oferta</th>
                                <th>Control</th>							                                        							                                       
                            </tr>
                        </thead>
                        <tbody>							                                	                              	
                        </tbody>
                    </table>
                </div>
           	</div>
		</div>		
     	<div class="form-group">
        	<div class="col-lg-6">
            	<h2>Proposta tècnica</h2>	
           		<label>Oferta seleccionada: </label>
           		<label id="ofertaSeleccionada"></label>
           		<input type="hidden" name="ofertaSeleccionadaNIF" id="ofertaSeleccionadaNIF" value="">
           	</div>
         	<div class="hidden" id="llistatOfertes"></div>
       	</div>					                    	
       	<div class="form-group">					                    			
        	<div class="col-lg-12">							                    			
     			<div class="row">	 
     				<div class="col-lg-12">						                    						
     					<textarea class="form-control" name="propostaTecnica" placeholder="Proposta tècnica" rows="3" required></textarea> 
       				</div>
       			</div>
       		</div>						                       		
       	</div>	
      	<div class="form-group">
        	<div class="col-lg-6">
            	<label>Termini d'execució definitiu</label>
             	<input name="termini" placeholder="1 mes" value="${informePrevi.termini}" required>
        	</div>
       	</div>
     	<div class="form-group">
        	<div class="col-lg-6">
       			<div class="row">
           			<div class="col-lg-12">
                  		<input class="btn btn-primary" type="submit" name="guardar" value="Guardar proposta tècnica">
					</div>
       			</div>
   			</div>	
   			<c:if test="${esCap}">
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