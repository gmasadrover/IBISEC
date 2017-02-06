<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<input type="hidden" id="resPartida">				                         	
<jsp:include page="_resumInformePrevi.jsp"></jsp:include>
<div class="panel-body">
	<form class="form-horizontal" method="POST" action="DoAddReserva">
   		<input type="hidden" name="idActuacio" value="${actuacio.referencia}">
   		<input type="hidden" name="idTasca" value="${tasca.idTasca}">
   		<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="${informePrevi.idInf}">	
   		<input type="hidden" name="importReserva" value="${informePrevi.plic}">					                    		
   		<div class="form-group">
    		<div class="col-lg-4">	
      			<label>Partida asignada</label>									            	 										            	 	
                <select class="form-control selectpicker" name="llistaPartides" id="llistaPartides">
                	<c:forEach items="${partidesList}" var="partida">
                		<option value="${partida.codi}">${partida.nom} - Restant: ${partida.getPartidaPerAsignarFormat()}</option>
                	</c:forEach>					                                	
                </select>	
            </div>					                       		
       	</div>	
   		<div class="form-group">
    		<div class="col-lg-12">		
    			<div class="row">	 
    				<div class="col-lg-12">						                    						
    					<textarea class="form-control" name="comentariFinancer" placeholder="observacions" rows="3" required></textarea> 
      				</div>
      			</div>
      		</div>						                       		
       	</div>	
       	<div class="form-group">
       		<div class="col-lg-6">
		        <div class="row">
		            <div class="col-lg-12">
						<input class="btn btn-success" type="submit" name="reservar" value="Conforme àrea econòmico-financera">
					</div>
		        </div>
	    	</div>
		    <div class="col-lg-6">
		        <div class="row">
		            <div class="col-lg-12">
						<input class="btn btn-danger" type="submit" name="rebutjar" value="rebutjar reserva de crèdit">
					</div>
		        </div>
		    </div>
		</div>	                       	
	</form>
</div>