<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>	
<div class="panel panel-${actuacio.activa ? actuacio.aprovada ? "success" : "warning" : "danger"}">
    <div class="panel-heading">
        <div class="row">
    		<div class="col-md-6">
    			id actuació: <a href="actuacionsDetalls?ref=${actuacio.referencia}" class="loadingButton"  data-msg="obrint actuació...">${actuacio.referencia}</a>
    		</div>
    		<div class="col-md-6">
    			Centre: ${actuacio.centre.getNomComplet()}
   			</div>
    	</div>
    </div>
    <div class="panel-body">
        <p>${actuacio.descripcio}</p>
        <a href="#" data-toggle="modal" data-target="#ModalDocuments">Veure documents</a>
        <div id="ModalDocuments" class="modal fade" role="dialog">
			<div class="modal-dialog">																	
		    <!-- Modal content-->
		    	<div class="modal-content">
		    		<div class="documentsModal">
			      		<p>Documents adjunts:</p>
			      		<jsp:include page="_resumFitxers.jsp"></jsp:include>
					</div>	
	    		</div>																	
		  	</div>
		</div>
    </div>
    <div class="panel-footer">
    	<div class="row">
    		<div class="col-md-6">
    			Data Creació: ${actuacio.getDataCreacioString()}
    		</div>
    	</div>
    </div>
</div>