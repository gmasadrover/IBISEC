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
                            Tramitació <small>Pagament</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Tramitació
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Pagament
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
                
               	<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="doCreateTascaPagamentTramitacio">     
               		 <input type="hidden" name="procediment" value="${procediment}">           		
               		<h2 class="margin_bottom30">Tramitació</h2>               		
                	<div class="row">
                		<div class="form-group">  			
                           	<label class="col-xs-2 control-label">Descripcio</label>
                           	<textarea class="col-xs-8" name="descripcio" rows="3">${tramitacio.descripcio}</textarea>
                        </div>
                	</div>
                	<div class="row">
                		<div class="form-group">  			
                           	<label class="col-xs-2 control-label">Valor</label>
                           	<div class="col-md-4">					          	
					          	<input name=valor class="pbase" id="valor" placeholder="0000,00" value="" required>
					          	<label class="">€</label>
					        </div>
                        </div>
                	</div>
                	<div class="row">
	                	<div class="form-group">
				    		<label class="col-xs-2 control-label">Partida asignada</label>	
				    		<div class="col-xs-8">							            	 										            	 	
				                <select class="form-control selectpicker" name="llistaPartides" id="llistaPartides">
				                	<c:forEach items="${partidesList}" var="partida">
				                		<option value="${partida.codi}">${partida.codi} (${partida.nom} - Restant: ${partida.getPartidaPerAsignarFormat()})</option>
				                	</c:forEach>					                                	
				                </select>	
			                </div>					                       		
				       	</div>	
			       	</div>	
                	<div class="row">
						<div class="form-group">
							<label class="col-xs-2 control-label">Adjuntar arxius:</label>
                            <div class="col-xs-5">   
                                <input type="file" class="btn" name="file" multiple/><br/>
							</div> 									  						
         				</div> 							
	            	</div> 							                	
                	<div class="row">
                		<div class="form-group">
					        <div class="col-xs-offset-9 col-xs-9">
					            <input type="submit" class="btn btn-primary" value="Afegir tramitació">							            
					        </div>
					    </div> 
                	</div>
                </form>
                <!-- /.row -->     
           	</div>
    		<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/judicial/pagamentJudicial.js?<%=application.getInitParameter("datakey")%>"></script>
</body>
</html>