<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<input type="hidden" id="solVacances">
 <c:if test="${canRealitzarTasca}">
	<div class="panel-body">
		<form class="form-horizontal" method="POST" action="DoAprovarDron">
			<input type="hidden" name="idSolicitud" value="${tasca.idinforme}">	
			<input type="hidden" name="idTasca" value="${tasca.idTasca}">	
	   		<div class="form-group">
	    		<div class="col-md-12">		
	    			<div class="row">	 
	    				<div class="col-md-12">						                    						
	    					<textarea class="form-control" name="comentari" placeholder="observacions" rows="3"></textarea> 
	      				</div>
	      			</div>
	      		</div>						                       		
	       	</div>	
	       	<div class="form-group">
	       		<div class="col-md-6">
			        <div class="row">
			            <div class="col-md-12">
			            	<input class="btn btn-success" type="submit" name="aprovar" value="Autoritzar">							
						</div>
			        </div>
		    	</div>
			    <div class="col-md-6">
			        <div class="row">
			            <div class="col-md-12">
							<input class="btn btn-danger" type="submit" name="rebutjar" value="Rebutjar">
						</div>
			        </div>
			    </div>
			</div>	                       	
		</form>	
	</div>
</c:if>
