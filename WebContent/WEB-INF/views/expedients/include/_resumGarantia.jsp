<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<br />
<p>			                     				
	<label>Data inici garantia:</label> ${informePrevi.expcontratacio.getDataRecepcioString()}
</p>
<p>			                     				
	<label>Data fi garantia prevista:</label> ${informePrevi.expcontratacio.getDataFiGarantiaString()} <label>(Veure tràmits)</label>
</p>
<p>			                     				
	<label>Data devolució garantia:</label> 
	<c:choose>
		<c:when test="${informePrevi.expcontratacio.getDataRetornGarantia()!=null}">
			${informePrevi.expcontratacio.getDataRetornGarantiaString()}
		</c:when>
		<c:otherwise>
			Pendent
		</c:otherwise>
	</c:choose>
</p>
<p>			                     				
	<label>Data liquidació contracte:</label>
	<c:choose>
		<c:when test="${informePrevi.expcontratacio.getDataLiquidacio()!=null}">
			${informePrevi.expcontratacio.getDataLiquidacioString()}
		</c:when>
		<c:otherwise>
			Pendent
		</c:otherwise>
	</c:choose>
</p>

<c:if test="${informePrevi.documentSolDevolucio.size() > 0}">
<p>
	<label>Sol·licitud devolució aval:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentSolDevolucio}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
	</c:forEach>
	<br>					            		
</div>
</c:if>
<c:if test="${informePrevi.documentInformeDevolucio.size() > 0}">
<p>
	<label>Informe devolució aval:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentInformeDevolucio}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
	</c:forEach>
	<br>					            		
</div>
</c:if>
<c:if test="${informePrevi.documentLiquidacioAval.size() > 0}">
<p>
	<label>Liquidació contracte:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentLiquidacioAval}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
	</c:forEach>
	<br>					            		
</div>	
</c:if>
<c:if test="${canModificarGarantia}">
	<div class="row">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-offset-7 col-md-2">
					<a href="editGarantia?idinf=${informePrevi.idInf}&from=${redireccio}" class="btn btn-primary" role="button">Editar</a>
				</div>			
				<div class="col-md-3">
		    		<a href="novaIncidenciaGarantia?idInforme=${informePrevi.idInf}" class="loadingButton btn btn-primary"  data-msg="nova incidència...">Afegir tràmit</a>
				</div>	
	 		</div>       
		</div>
	</div>
	<div class="row">					
		<div class="form-group">	
	  		
		</div>	
	</div>
</c:if>
<div class="separator"></div>
<div class="row">
	<div class="col-md-12">
    	<h3>Tràmits</h3>
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
		    	<div class="col-md-6">
		    		<div class="col-md-12">
			        	<label class="col-xs-3 control-label">Arxius:</label> 
			            	<div class="col-xs-9">  		
			                	<c:forEach items="${incidencia.documentsList}" var="arxiu" >
			                		<c:set var="arxiu" value="${arxiu}" scope="request"/>
									<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>						            		
							</c:forEach>	
						</div>
					</div>
				</div>
			</div>		
			<c:if test="${canModificarGarantia}">                		 				                	
		  		<div class="row">
		        	<div class="form-group">
				        <div class="col-xs-offset-9 col-xs-3">
				            <a href="editIncidenciaGarantia?idIncidencia=${incidencia.idIncidencia}&idInforme=${informePrevi.idInf}" class="loadingButton btn btn-success"  data-msg="actualitzar incidència...">Actualitzar tràmit</a>					            
				        </div>
		    		</div> 
        		</div>
       		</c:if>
   			<div class="separator"></div>
  		</c:forEach>    
	</div>
	
</div> 

<%-- <p>
	<label>Altre documentació garantia:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentsAltresGarantia}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
	</c:forEach>
	<br>					            		
</div>
<c:if test="${isIBISEC}">
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
					<input type="submit" class="btn btn-primary loadingButton" value="Pujar" />
				</div>    						
			</div>         				
		</form>							
	</div>
</c:if> --%>