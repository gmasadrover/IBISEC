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
	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA">														
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