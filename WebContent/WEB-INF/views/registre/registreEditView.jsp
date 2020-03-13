<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
	<jsp:include page="../_header.jsp"></jsp:include>
</head>
<body>
 	<div id="wrapper">
    	<jsp:include page="../_menu.jsp"></jsp:include>
    	<div id="page-wrapper">

            <div class="container-fluid">
            	<!-- Page Heading -->
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            Registre <small>Modificar</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Registre
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Modificar
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                
                <div class="row">
                	<div class="col-md-12">
               			<p style="color: red;">${errorString}</p>
               		</div>
               	 </div>
                <!-- /.row -->
                
    			<div class="row">
                    <div class="col-md-12">                    	
		    			<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="DoEditRegistre">
		    				<div class="form-group">
		    					<input type="hidden" id="tipusRegistre" value="${entradaSortida}"> 
		    					<input type="hidden" name="idCodiRegistre" value="${registre.id}">        
		    					<input type="hidden" name="entradaSortida" value="${entradaSortida}">   
		    					<input type="hidden" id="idCentresSeleccionats" name="idCentresSeleccionats" value="">                              
                            </div>		  
                            <div class="form-group">
                            	<c:if test="${entradaSortida == 'E'}">
	                                <label class="col-xs-3 control-label">Remitent</label>
	                            </c:if>
	                            <c:if test="${entradaSortida == 'S'}">
	                                <label class="col-xs-3 control-label">Destinatari</label>
	                            </c:if>
                                <div class="col-xs-3">
                                	<input class="form-control" name="remitent" placeholder="remitent" value="${registre.remDes}" required>
                                </div>                                
                            </div>	
                            <div class="form-group">
                                <label class="col-xs-3  control-label">Tipus</label>
                                <div class="col-xs-3">
                                	<input type="hidden" name="tipusSelected" id="tipusSelected" value="${registre.tipus}">  
	                                <select class="form-control selectpicker" name="tipus" id="tipusList"  data-live-search="true" data-size="5" >
	                                </select>
	                             </div>
                            </div>										
		    				<div class="form-group">
                                <label class="col-xs-3  control-label">Contingut</label>
                                <div class="col-xs-3">
                                	<textarea class="form-control" name="contingut" placeholder="contingut" rows="3" required>${registre.contingut}</textarea>
                                </div>
                            </div>	
							<div class="form-group">
								<label class="col-xs-3 control-label">Arxius:</label>								
	                            <div class="col-xs-3">  	                            	
	                                <input type="file" class="btn" name="file" multiple/><br/>
								</div>					
	         				</div>    
	         				<div class="form-group">
	         					<label class="col-xs-3 control-label"></label>								
	                            <div class="col-xs-6">  	
		         					<c:forEach items="${registre.documents}" var="arxiu" >
						            	<c:set var="arxiu" value="${arxiu}" scope="request"/> 	
						            	<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
									</c:forEach> 
								</div>
	         				</div>				
		    				<div class="form-group">
                                <label class="col-xs-3 control-label">Data petició</label>
                                <div class="input-group date col-xs-3 datepicker">
								  	<input type="text" class="form-control" name="peticio" value="${registre.getDataString()}"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
                            </div>   
                            <div class="form-group">
								<label class="col-xs-3 control-label">Confirmacions de recepció:</label>
	                            <div class="col-xs-3">   
	                                <input type="file" class="btn" name="confirmacioRecepcio" multiple/><br/>
								</div>					
	         				</div>	
	         				<div class="form-group">
	         					<label class="col-xs-3 control-label"></label>								
	                            <div class="col-xs-6">  	
		         					<c:forEach items="${registre.confirmacioRecepcio}" var="arxiu" >
						            	<c:set var="arxiu" value="${arxiu}" scope="request"/> 	
						            	<jsp:include page="../utils/_renderDocument.jsp"></jsp:include>	
									</c:forEach> 
								</div>
	         				</div>	         				
	         				<div class="form-group">
                                <label class="col-xs-3  control-label">Centre</label>
                                <input type="hidden" id="centrePrev" value="${registre.idCentres}" >
                                <input type="hidden" id="incidenciesPrev" value="${registre.getIdActuacionss()}" >
                                <div class="col-xs-3">
	                                <select class="form-control selectpicker centresList" name="idCentre" data-live-search="true" data-size="5" id="centresList" multiple>
		                            	<option value="-1">No hi ha relació</option>
		                            </select>
	                             </div>
                            </div>
                           	<div id="procediments"></div> 
                            <div id="incidencies"></div>		
                            <div id="expedients"></div>		                           	
                            <br>
						    <div class="form-group">
						    	<div class="col-xs-offset-8 col-xs-2">
						            <input type="submit" class="btn btn-danger" name="anular" value="Anul·lar">
						        </div>
						        <div class=" col-xs-2">
						            <input type="submit" class="btn btn-primary loadingButton" name="modificar" value="Modificar">
						        </div>
						    </div>    				
		    			</form>		    			
                    </div>
                </div>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/registre/editRegistre.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>