<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doEditProcediment">                		
	<h2 class="margin_bottom30">Procediment</h2>
    <div class="row">
    	<div class="col-md-12">		                    	
			<div class="form-group">	                               
				<input type="hidden" name="referenciaOriginal" value="${procedimentOriginal.referencia}">
            	<input type="hidden" name="referencia" value="${procediment.referencia}">
            	<input type="hidden" name="tipus" value="${tipus}">
          	</div>   
   		</div>
	</div>
	<div class="row">			    				    				    		
       	<div class="col-md-6">	 
       		<div class="form-group">    
           		<label class="col-xs-3 control-label">Jutjat / Tribunal</label>
              	<textarea class="col-xs-6" name="jutjat" placeholder="jutjat" rows="3">${procediment.jutjat}</textarea>
       		</div> 
       		<div class="form-group">
				<label class="col-xs-3 control-label">Demandant</label>
				<input class="col-xs-6" name="demandant" value="${procediment.demandant}">						
			</div>		                        		                    
		</div>
     	<div class="col-md-6"> 		                    	 
        	<div class="form-group">
				<label class="col-xs-3 control-label">Num Autos</label>
				<input class="col-xs-6" name="numautos" value="${procediment.numAutos}">						
			</div>	
			<div class="form-group">
				<label class="col-xs-3 control-label">Demandat</label>
				<input class="col-xs-6" name="demandat" value="${procediment.demandat}">						
			</div>	
			<div class="form-group">
				<label class="col-xs-3 control-label">Quantia</label>
				<input class="col-xs-6" name="quantia" value="${procediment.quantia}">						
			</div>	                                                                         	
      	</div>	
	</div>
  	<div class="row">
  		<div class="form-group">  			
       		<label class="col-xs-2 control-label">Objecte demanda</label>
       		<textarea class="col-xs-8" name="objecte" placeholder="objecte" rows="3">${procediment.objecteDemanda}</textarea>
      	</div>
  	</div>		                	 
  	<div class="row">
  		<div class="form-group">  			
        	<label class="col-xs-2 control-label">Notes</label>
         	<textarea class="col-xs-8" name="notes" placeholder="notes" rows="3">${procediment.notes}</textarea>
       	</div>
  	</div>	
  	<div class="row">
    	<div class="col-md-12">	                       
        	<div class="row">  
           		<label class="col-xs-2 control-label">Arxius d'inici:</label> 
            	<div class="col-xs-8">  		
                   	<c:forEach items="${procediment.documentsIniciList}" var="arxiu" >
                   		<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>				            		
					</c:forEach>	
				</div>
			</div>
       		<div class="row">
				<div class="form-group">
					<label class="col-xs-2 control-label">Adjuntar arxius d'inici:</label>
			      	<div class="col-xs-5">   
                    	<input type="file" class="btn" name="fileInici" multiple/><br/>
					</div> 									  						
    			</div> 							
        	</div>              		
   	 	</div>
	</div>
 	<div class="row">
    	<div class="col-md-12">	                       
       		<div class="row">  
            	<label class="col-xs-2 control-label">Arxius de comunicacions:</label> 
              	<div class="col-xs-8">  		
                	<c:forEach items="${procediment.documentsComunicacioList}" var="arxiu" >
                		<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>		            		
					</c:forEach>	
				</div>
			</div>
        	<div class="row">
				<div class="form-group">
					<label class="col-xs-2 control-label">Adjuntar arxius de comunicacions:</label>
                   	<div class="col-xs-5">   
                    	<input type="file" class="btn" name="fileComunicacio" multiple/><br/>
					</div> 									  						
   				</div> 							
         	</div>              		
      	</div>
    </div>
  	<c:if test="${canModificarProcediment}">
    	<div class="row">
        	<div class="form-group">
		       <div class="col-xs-offset-9 col-xs-9">
		           <input type="submit" class="btn btn-success" value="Actualitzar procediment">							            
		       </div>
   			</div> 
     	</div>
 	</c:if>
 </form>	         