<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>			                         	
<div class="panel-heading">
	Resum Informe
</div>
<div class="panel-body">	
	<p>
		<label>Tècnic:</label> ${informePrevi.usuari.getNomComplet()}
	</p>
	<p>
		<label>Data:</label> ${informePrevi.getDataCreacioString()}
	</p>
	<p>			                     				
		<label>Objecte:</label> ${informePrevi.objecte}
	</p>	
	<p>			                     				
		<label>Comentari tècnic:</label> ${informePrevi.comentari}
	</p>				                         	
	<p>
		<label>Tipus de contracte:</label> <m:message key="${informePrevi.tipusObra}"/>
	</p>
	<c:if test="${informePrevi.tipusObra} == 'obr'">
		<div class="row">
			<div class="col-lg-4">
				<label>Requereix llicència:</label> ${informePrevi.llicencia ? "Si" : "No"}
			</div>
			<c:if test="${informePrevi.llicencia}">
				<div class="col-lg-4">
					<label>Tipus llicència:</label> ${informePrevi.tipusLlicencia}
				</div>
			</c:if>
		</div>
		<p></p>
	</c:if>
	<p>
		<label>Requereix formalització contracte:</label> ${informePrevi.contracte ? "Si" : "No"}
	</p>
	<p>
		<label>Termini d'execució:</label> ${informePrevi.termini}
	</p>	
	<div class="row">
		<div class="col-lg-4">
	       	<label>VEC:</label> ${informePrevi.vec}€						                                
		</div>
		<div class="col-lg-4"> 
			<label>IVA:</label> ${informePrevi.iva}€
		</div>
		<div class="col-lg-4">
			<label>Plic:</label> ${informePrevi.plic}€
	   	</div>					  
	</div>	
	<p></p>
	<p>
		<label>Arxius ajunts:</label>
	</p>	
	<div class="row col-lg-12">
		<c:forEach items="${informePrevi.adjunts}" var="arxiu" >
			<a  href="downloadFichero?ruta=${arxiu.ruta}">${arxiu.nom}</a>
			<br>
		</c:forEach>					            		
	</div>
	<p>
		<label>Aprovació:</label> ${informePrevi.usuariAprovacio.getNomComplet()} - ${informePrevi.getDataAprovacioString()}
	</p>
	<p>
		<label>Partida:</label> ${informePrevi.partida}
	</p>
</div>