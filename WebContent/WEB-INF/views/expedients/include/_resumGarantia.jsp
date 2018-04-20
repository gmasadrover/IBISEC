<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<br />
<c:if test="${informePrevi.actaFinalitzacio.ruta != null}">
	<p>
		<div class="document">
			<label>Acta Recepció / Finalització:</label>										                  	
	        	<a target="_blanck" href="downloadFichero?ruta=${informePrevi.actaFinalitzacio.getEncodedRuta()}">
				${informePrevi.actaFinalitzacio.nom}
			</a>	
			<c:if test="${informePrevi.actaFinalitzacio.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${informePrevi.actaFinalitzacio.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>	
		</div>
	</p>
</c:if>	
<p>			                     				
	<label>Termini garantia:</label> ${informePrevi.expcontratacio.garantia}
</p>
<p>			                     				
	<label>Data inici garantia:</label> ${informePrevi.expcontratacio.getDataRecepcioString()}
</p>
<div class="row">
	<div class="col-md-12">
    	<h3>Incidències</h3>
       	<c:forEach items="${informePrevi.llistaIncidenciesGarantia}" var="incidencia" >	
            <div class="row">
         			<div class="col-md-6">		                    	
					<div class="col-md-12">	
						<label class="col-xs-3 control-label">Objecte</label>
						<div class="col-xs-9">  
							${incidencia.objecte}	
						</div>
                     	</div>   
             		</div>
        		</div>                		
				<div class="row">	
					<div class="col-md-6">		 
	        		<div class="col-md-12">
           	 			<label class="col-xs-3 control-label">Data inici</label>
                           <div class="col-xs-9">  
                           	${incidencia.getDataIniciString()}
                           </div>
           	 		</div> 
          	 	</div>
        	</div>
        	<div class="row">	
				<div class="col-md-6">		 
	        		<div class="col-md-12">
           	 			<label class="col-xs-3 control-label">Data fi</label>
                           <div class="col-xs-9">  
                           	${incidencia.getDataFiString()}
                           </div>
           	 		</div> 
          	 	</div>
        	</div>	              	
			<div class="row">					
		    	<div class="col-md-12">
		        	<label class="col-xs-3 control-label">Arxius:</label> 
		            	<div class="col-xs-9">  		
		                	<c:forEach items="${incidencia.documentsList}" var="arxiu" >
								<div class="document">
									<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
										${arxiu.getDataString()} - ${arxiu.nom} 
									</a>
									<c:if test="${arxiu.signat}">
										<span class="glyphicon glyphicon-pencil signedFile"></span>
									</c:if>
									<a href="#"><span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
									<br>
									<div class="infoSign hidden">
										<c:forEach items="${arxiu.firmesList}" var="firma" >
											<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
											<br>
										</c:forEach>
									</div>
								</div>					            		
						</c:forEach>	
					</div>
				</div>
			</div>		
			<c:if test="${canModificarGarantia}">                		 				                	
		  		<div class="row">
		        	<div class="form-group">
				        <div class="col-xs-offset-9 col-xs-3">
				            <a href="editIncidenciaGarantia?idIncidencia=${incidencia.idIncidencia}&idInforme=${informePrevi.idInf}" class="loadingButton btn btn-success"  data-msg="actualitzar incidència...">Actualitzar incidència</a>					            
				        </div>
		    		</div> 
        		</div>
       		</c:if>
   			<div class="separator"></div>
  		</c:forEach>    
	</div>
</div> 
<p>			                     				
	<label>Data retorn garantia:</label> ${informePrevi.expcontratacio.getDataRetornGarantiaString()}
</p>
<p>
	<label>Altre documentació garantia:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentsAltresGarantia}" var="arxiu" >
		<div class="document">
			<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">${arxiu.getDataString()} - ${arxiu.nom}</a>
			<c:if test="${arxiu.signat}">
					<span class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<c:if test="${canModificarExpedient && arxiu.ruta != null}">
				<span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">
				<c:forEach items="${arxiu.firmesList}" var="firma" >
					<span>Signat per: ${firma.nomFirmant} - ${firma.dataFirma}</span>
					<br>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
	<br>					            		
</div>
<div class="row">            			
	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentsAltresGarantia">
		<div class="form-group">
			<label class="col-xs-2 control-label">Adjuntar arxius:</label>
            <div class="col-xs-5">   
            	<input type="file" class="btn" name="file" multiple/><br/>
			</div> 
			<input type="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
			<input type="hidden" name="idIncidencia" value="${informePrevi.actuacio.idIncidencia}">
			<input type="hidden" name="idInforme" value="${informePrevi.idInf}">			    
			<div class="col-xs-2"> 
				<input type="submit" class="btn btn-primary" value="Pujar" />
			</div>    						
		</div>         				
	</form>							
</div>
<c:if test="${canModificarGarantia}">
	<div class="row">					
		<div class="form-group">	
	  		<div class="col-md-offset-9 col-md-3">
	    		<a href="novaIncidenciaGarantia?idInforme=${informePrevi.idInf}" class="loadingButton btn btn-primary"  data-msg="nova incidència...">Afegir incidència</a>
			</div>
		</div>	
	</div>
</c:if>