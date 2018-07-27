<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<div class="row">
	<div class="col-md-12">
		<p> 
       		<label>Tipus: </label> ${informePrevi.llicencia.getTipusFormat()}
       	</p>
       	<c:choose>
       		<c:when test="${informePrevi.llicencia.tipus == 'comun'}">
       			<p>
					<label>Sol·licitud Comunicació Prèvia:</label>
				</p>	
				<div class="row col-md-12">
					<c:forEach items="${informePrevi.llicencia.documentSolLlicencia}" var="arxiu" >
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
					<br>					            		
				</div>
				<p> 
		       		<label>Data sol·licitud: </label> ${informePrevi.llicencia.getDataPeticioString()}
		       	</p>
       		</c:when>
       		<c:otherwise>
       			<p>
					<label>Sol·licitud llicència:</label>
				</p>	
				<div class="row col-md-12">
					<c:forEach items="${informePrevi.llicencia.documentSolLlicencia}" var="arxiu" >
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
					<br>					            		
				</div>
				<p> 
		       		<label>Data sol·licitud: </label> ${informePrevi.llicencia.getDataPeticioString()}
		       	</p>  
				<p>
		       		<label>Concessió llicència:</label>
		       	</p>
				<div class="row col-md-12">
					<c:forEach items="${informePrevi.llicencia.documentConcessioLlicencia}" var="arxiu" >
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
					<br>					            		
				</div>	    	 
		       	<p> 
		       		<label>Data concessió: </label> ${informePrevi.llicencia.getDataConcesioString()}
		       	</p> 
		       	<p> 
		       		<label>Valor taxa: </label> ${informePrevi.llicencia.getTaxaFormat()}
		       	</p>       	
		       	<p> 
		       		<label>Data pagament taxa: </label> ${informePrevi.llicencia.getDataPagamentTaxaString()}
		       	</p> 
		       	<p> 
		       		<label>Valor ICO: </label> ${informePrevi.llicencia.getIcoFormat()}
		       	</p>
		       	<p> 
		       		<label>Data pagament ICO: </label> ${informePrevi.llicencia.getDataPagamentICOString()}
		       	</p> 
		       	<p>
		       		<label>Justificant pagament:</label>
		       	</p>
				<div class="row col-md-12">
					<c:forEach items="${informePrevi.llicencia.documentPagamentLlicencia}" var="arxiu" >
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
					</c:forEach>
					<br>					            		
				</div>
				<p>
		       		<label>Títol habilitant:</label>
		       	</p>
				<div class="row col-md-12">
					<c:forEach items="${informePrevi.llicencia.documentTitolHabilitant}" var="arxiu" >
						<c:set var="arxiu" value="${arxiu}" scope="request"/>
						<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>
					</c:forEach>
					<br>					            		
				</div>		
       		</c:otherwise>   
       	</c:choose> 
       		
       	
       	<p> 
       		<label>Observacions: </label> ${informePrevi.llicencia.observacio}
       	</p>  
       	<p>
        	<label>Partida despesa: </label> ${informePrevi.llicencia.getIdPartida()}
        </p>     
	</div>            		 
</div>
<c:if test="${canModificarUrbanisme}">
	<div class="row">
		<div class="col-md-12">
			<div class="row">
	   			<c:if test="true">
					<div class="col-md-offset-9 col-md-2 margin_top30">
						<a href="editLlicencia?codi=${informePrevi.llicencia.codi}&mode=${informePrevi.llicencia.codi != null ? 'modificar' : 'crear'}&exp=${informePrevi.idInf}&from=expedient" class="btn btn-primary" role="button">Editar</a>
					</div>
				</c:if>
	 		</div>       
		</div>
	</div>
</c:if>
<h2 class="margin_bottom30">Arxius</h2>
<div class="row col-md-12 margin_bottom30">  
	<c:forEach items="${informePrevi.llicencia.arxius}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>				            		
	</c:forEach>	
</div>	
<div class="row col-md-12">
	<p>
		<label>Altre documentació autoritzacions urbanístiques:</label>
	</p>
	<c:forEach items="${informePrevi.documentsAltresAutUrbanistica}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
	</c:forEach>
	<br>					            		
</div>
<div class="row">            			
	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentsAltresAutUrbanistica">
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