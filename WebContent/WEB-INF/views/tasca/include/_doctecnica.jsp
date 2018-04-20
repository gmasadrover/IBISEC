<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<div class="panel-body">
 	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoAddPA"> 	
		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
		<input type="hidden" name="idIncidencia" value="${actuacio.idIncidencia}">															
		<input type="hidden" name="idInforme" value="${informePrevi.idInf}">
		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
		<input type="hidden" name="document" value="docTecnica">							
		<div class="form-group">
			<div class="col-md-12">
				<p>Arxius documentació tècnica:</p>
	            <c:forEach items="${informePrevi.docTecnica}" var="arxiu" >
	           		<a target="_blanck" href="downloadFichero?ruta=${arxiu.getEncodedRuta()}">
						${arxiu.getDataString()} - ${arxiu.nom}
					</a>
					<c:if test="${!isGerencia}">
						<a href="#"><span data-ruta="${arxiu.ruta}" class="glyphicon glyphicon-remove deleteFile"></span></a>
					</c:if>
					<br>
				</c:forEach>
			</div>	
			<div class="col-md-12">
				<input type="file" class="btn" name="informe" multiple/><br/>																 		
			</div>	
		</div>		
		<div class="form-group">
			<div class="col-md-12">
				<div class="col-md-3">
		    		<div class="row">
		        		<div class="col-md-12">
		        			<input class="btn btn-success" type="submit" name="guardar" value="Guardar documentació">
					 	</div>
		     		</div>																	     		
		 		</div>	
		 		<c:if test="${esCap}">
			 		<div class="col-md-3">
			    		<div class="row">
			        		<div class="col-md-12">
			        			<input class="btn btn-success" type="submit" name="tramitar" value="Enviar documentació per tramitar">
						 	</div>
			     		</div>																	     		
			 		</div>	
			 	</c:if>														 			
	 		</div>															        		
		</div>
	</form>
</div>