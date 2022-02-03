<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<m:setLocale value="es_ES"/>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>		
<h4>Informe inicial</h4>	
<br />
<div class="row">
	<div class="col-md-4">
		<p class="hidden">
			<label>Informe:</label> ${informePrevi.idInf}
		</p>
		<p>
			<label>Expedient:</label> ${informePrevi.expcontratacio.expContratacio}
		</p>
	</div>
	<div class="col-md-4">
		<p>
			<label>Data:</label> ${informePrevi.getDataCreacioString()}
		</p>
	</div>
</div>
<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == 'conveni'}">
	<div class="row col-md-12">
		<c:if test="${informePrevi.contracteSignat.ruta != null}">
             <p>
             	<div class="document">
               		<label>Contracte signat:</label>											                  	
	           		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.contracteSignat.getEncodedRuta()}">
						${informePrevi.contracteSignat.nom}
					</a>	
					<c:if test="${informePrevi.contracteSignat.signat}">
						<span data-ruta="${informePrevi.contracteSignat.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
					</c:if>							
					<br>
					<div class="infoSign hidden">								
					</div>
				</div>
			</p>	
		</c:if>					            		
	</div>	
	<div class="row">
		<div class="col-md-4">
			<p>
				<label>Data firma:</label> ${informePrevi.expcontratacio.getDataFirmaString()}
			</p>
		</div>
	</div>
</c:if>
<c:set var="informePrevi" value="${informePrevi}" scope="request"/>
<c:set var="propostaActuacio" value="${informePrevi.llistaPropostes[0]}" scope="request"/> 	
<c:set var="expedient" value="${informePrevi.expcontratacio}" scope="request"/>
<jsp:include page="_resumProposta.jsp"></jsp:include>	
<c:if test="${informePrevi.informesPrevis.size() > 0}">										
	<p>
		<label>Informe tècnic:</label>
	</p>	
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.informesPrevis}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>		
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>
</c:if>								
<c:if test="${informePrevi.informeSupervisio.size() > 0}">
	<p>
		<label>Informe supervisió:</label>
	</p>	
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.informeSupervisio}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>	
</c:if>	
<c:if test="${informePrevi.notes != null && informePrevi.notes != ''}">	
	<p>			                     				
		<label>Notes:</label>${informePrevi.notes}
	</p>
</c:if>
<c:if test="${informePrevi.propostaActuacio.ruta != null}">															
	<p>
    	<div class="document">
        	<label>Proposta d'actuació signada:	</label>											                  	
         	<a target="_blanck" href="downloadFichero?ruta=${informePrevi.propostaActuacio.getEncodedRuta()}">
				${informePrevi.propostaActuacio.nom}
			</a>	
			<c:if test="${informePrevi.propostaActuacio.signat}">
					<span data-ruta="${informePrevi.propostaActuacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">				
			</div>
		</div>																				
	</p>	
</c:if>	
<p>
	<label>Comentari Cap:</label> ${informePrevi.comentariCap}
</p>
<c:if test="${informePrevi.vistiplauPropostaActuacio.ruta != null || informePrevi.usuariCapValidacio != null}">		
	
	<p>
		<label>Vistiplau:</label> ${informePrevi.usuariCapValidacio.getNomComplet()} - ${informePrevi.getDataCapValidacioString()}
	</p>															
	<c:if test="${informePrevi.vistiplauPropostaActuacio.ruta != null}">
		<p>
			<div class="document">
              		<label>Vistiplau proposta d'actuació signada:</label>													               
				<a target="_blanck" href="downloadFichero?ruta=${informePrevi.vistiplauPropostaActuacio.getEncodedRuta()}">
					${informePrevi.vistiplauPropostaActuacio.nom}
				</a>
				<c:if test="${informePrevi.vistiplauPropostaActuacio.signat}">
					<span data-ruta="${informePrevi.vistiplauPropostaActuacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
				</c:if>
				<br>
				<div class="infoSign hidden">					
				</div>
			</div>																	
		</p>
	</c:if>	
</c:if>
<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == 'conveni'}">
	<div class="row">
		<div class="col-md-4">
		    <label class="left margin_right10">Tramitació IBISEC</label> 
		   	<div class="checkbox inline">
		    	<label><input name="bei" type="checkbox" ${fn:contains(informePrevi.organismeDependencia, 'IBISEC#') ? 'checked' : ''} disabled></label>
		   	</div> 
	   	</div>
	   	<div class="col-md-4">
		    <label class="left margin_right10">Tramitació Conselleria</label> 
		 	<div class="checkbox inline">
		     	<label><input name="feder" type="checkbox" ${fn:contains(informePrevi.organismeDependencia, 'Conselleria#') ? 'checked' : ''} disabled></label>
		  	</div>
	  	</div>
	  	<div class="col-md-4">
		    <label class="left margin_right10">Tramitació Altres</label> 
		 	<div class="checkbox inline">
		     	<label><input name="feder" type="checkbox" ${fn:contains(informePrevi.organismeDependencia, 'Altres#') ? 'checked' : ''} disabled></label>
		  	</div>
	  	</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<label class="left margin_right10">Cessió de crèdit</label> 
		   	<div class="checkbox inline">
		    	<label><input name="bei" type="checkbox" ${informePrevi.cessioCredit ? 'checked' : ''} disabled></label>
		   	</div>
		</div>
	</div>
</c:if>
<div class="row">
	<div class="col-md-4">
		<p>
			<label>Partida:</label>
			<c:choose>
				<c:when test="${informePrevi.dataRebujada != null}">
					${informePrevi.partidaRebutjadaMotiu}
				</c:when>
				<c:otherwise>
					<div class="table-responsive col-xs-offset-1 col-md-10">							                        
	                    <table class="table table-striped table-bordered filerTable">
	                    	<thead>
	                            <tr>
	                           		<th>Partida</th>	
	                                <th>Valor</th>			                                        							                                       
	                            </tr>
	                        </thead>
	                        <tbody>
	                        <c:set var="total" value="0" />
							<c:forEach items="${informePrevi.assignacioCredit}" var="assignacioCredit" >
					          	<tr>		
					          		<td><a target="_black" href="partidaDetalls?codi=${assignacioCredit.partida.codi}">${assignacioCredit.partida.codi} (${assignacioCredit.partida.nom})</a></td>	
					          		<td>${assignacioCredit.getValorPAFormat()}</td>	
					          		<c:set var="total" value="${total + assignacioCredit.valorPA}" />
					          	</tr>
					      	</c:forEach>  	
					      		<tr>
					      			<td>Total</td>
					      			<td><m:formatNumber pattern= "#,##0.00" type = "number" value ="${total}"/>€</td>
					      		</tr>	
					      	</tbody>					      		     
	                    </table>
	                </div>
				</c:otherwise>
			</c:choose> 
		</p>
	</div>		
</div>
<c:if test="${informePrevi.conformeAreaEconomivaPropostaActuacio.size() > 0}">
	<p>
		<div class="document">
			<label>Certificat d'existència de crèdit:</label>	
			<div class="row col-md-12">
				<c:forEach items="${informePrevi.conformeAreaEconomivaPropostaActuacio}" var="arxiu" >
					<c:set var="arxiu" value="${arxiu}" scope="request"/>
					<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
				</c:forEach>
				<br>					            		
			</div>
		</div>																	
	</p>	
</c:if>	
<div class="row">
	<div class="col-md-4">
	    <label class="left margin_right10">Afectat BEI</label> 
	   	<div class="checkbox inline">
	    	<label><input name="bei" type="checkbox" ${informePrevi.assignacioCredit[0].bei ? 'checked' : ''} disabled></label>
	   	</div> 
   	</div>
   	<div class="col-md-4">
	    <label class="left margin_right10">Afectat FEDER</label> 
	 	<div class="checkbox inline">
	     	<label><input name="feder" type="checkbox" ${informePrevi.assignacioCredit[0].feder ? 'checked' : ''} disabled></label>
	  	</div>
  	</div>
</div>	
<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.ruta != null}">
	<p>
		<div class="document">
			<label>Autorització proposta d'actuació signada:</label>										                  	
	         		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioPropostaAutoritzacio.getEncodedRuta()}">
				${informePrevi.autoritzacioPropostaAutoritzacio.nom}
			</a>	
			<c:if test="${informePrevi.autoritzacioPropostaAutoritzacio.signat}">
					<span data-ruta="${informePrevi.autoritzacioPropostaAutoritzacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">				
			</div>	
		</div>
	</p>
</c:if>	
<c:if test="${informePrevi.autoritzacioConsellDeGovern.size() > 0}">
	<p>
		<label>Autorització Consell De Govern:</label>
	</p>	
	<div class="row col-md-12">
		<c:forEach items="${informePrevi.autoritzacioConsellDeGovern}" var="arxiu" >
			<c:set var="arxiu" value="${arxiu}" scope="request"/>
			<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
		</c:forEach>
		<br>					            		
	</div>
</c:if>	
<c:if test="${informePrevi.autoritzacioConseller.ruta != null}">
	<p>
		<div class="document">
			<label>Autorització Conseller:</label>										                  	
	         		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.autoritzacioConseller.getEncodedRuta()}">
				${informePrevi.autoritzacioConseller.nom}
			</a>	
			<c:if test="${informePrevi.autoritzacioConseller.signat}">
					<span data-ruta="${informePrevi.autoritzacioConseller.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
			</c:if>
			<br>
			<div class="infoSign hidden">				
			</div>	
		</div>
	</p>
</c:if>	
<c:choose>
	<c:when test="${informePrevi.expcontratacio.contracte == 'major'}">
		<c:if test="${informePrevi.documentBOIB.ruta != null}">
			<p>
				<div class="document">
					<label>Document enviar BOIB:</label>										                  	
			         		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.documentBOIB.getEncodedRuta()}">
						${informePrevi.autoritzacioConseller.nom}
					</a>	
					<c:if test="${informePrevi.documentBOIB.signat}">
							<span data-ruta="${informePrevi.documentBOIB.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
					</c:if>
					<br>
					<div class="infoSign hidden">						
					</div>	
				</div>
			</p>
		</c:if>	
	</c:when>
	<c:otherwise>
		<c:if test="${informePrevi.correuInvitacio.ruta != null}">
			<p>
				<div class="document">
					<label>Correu invitació licitació:</label>										                  	
			         		<a target="_blanck" href="downloadFichero?ruta=${informePrevi.correuInvitacio.getEncodedRuta()}">
						${informePrevi.correuInvitacio.nom}
					</a>	
					<c:if test="${informePrevi.correuInvitacio.signat}">
							<span data-ruta="${informePrevi.correuInvitacio.ruta}" class="glyphicon glyphicon-pencil signedFile"></span>
					</c:if>
					<br>
					<div class="infoSign hidden">						
					</div>	
				</div>
			</p>
		</c:if>
	</c:otherwise>
</c:choose>
<c:if test="${informePrevi.propostaInformeSeleccionada.tipusObra == 'conveni'}">	
	<div class="row">
		<div class="col-md-4">
			<p>
				<label>Publicació registre convenis:</label> ${informePrevi.getDataPublicacioRegitreConvenisString()}
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<p>
				<label>Publicació BOIB:</label> ${informePrevi.getPublicacioBOIBString()}
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<p>
				<label>Publicació Perfil del contractant:</label> ${informePrevi.getDataPublicacioPerfilContractantString()}
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<p>
				<label>Informació DG Tresoreria:</label> ${informePrevi.getDataPublicacioDGTresoreriaString()}
			</p>
		</div>
	</div>
</c:if>
<p>
	<label>Altre documentació prèvia:</label>
</p>	
<div class="row col-md-12">
	<c:forEach items="${informePrevi.documentsAltresPrevis}" var="arxiu" >
		<c:set var="arxiu" value="${arxiu}" scope="request"/>
		<jsp:include page="../../utils/_renderDocument.jsp"></jsp:include>	
	</c:forEach>	
	<br>					            		
</div>
<c:if test="${isIBISEC}">
	<div class="row">            			
		<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="uploadDocumentsAltresPrevis">
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
</c:if>
<div class="row">
	<div class="col-md-12">
		<div class="row">
  			<c:if test="${canModificarExpedient}">
				<div class="col-md-offset-7 col-md-2 margin_top30">
					<a href="editInforme?${informePrevi.expcontratacio.expContratacio != '-1' ? 'ref=' += informePrevi.expcontratacio.expContratacio : 'idinf=' += informePrevi.idInf}&from=${redireccio}" class="btn btn-primary" role="button">Editar</a>
				</div>				
				<c:if test="${!informePrevi.expcontratacio.isAnulat()}">
					<form class="form-horizontal" method="POST" enctype="multipart/form-data" action="anularExpedient">
						<input class="hidden" name="expedient" value="${informePrevi.expcontratacio.expContratacio}">
                		<input class="hidden" name="idActuacio" value="${informePrevi.actuacio.referencia}">
                		<input class="hidden" name="idInforme" value="${informePrevi.idInf}">
                		<input class="hidden" name="redireccio" value="${redireccio}">
						<div class="col-md-2 margin_top30">
							<input class="btn btn-danger" data-toggle="modal" data-target="#myModalInforme${informePrevi.idInf}" name="anular" value="Anul·lar">
						</div>
	        			<!-- Modal -->
						<div id="myModalInforme${informePrevi.idInf}" class="modal fade" role="dialog">
							<div class="modal-dialog">																	
						    <!-- Modal content-->
						    	<div class="modal-content">
						      		<div class="modal-header">
						        		<button type="button" class="close" data-dismiss="modal">&times;</button>
						        		<h4 class="modal-title">Motiu anul·lació</h4>
						      		</div>
						      		<div class="modal-body">
						        		<textarea name="motiuAnulacio" required></textarea>
						      		</div>
						      		<div class="modal-footer">
						        		<input class="btn btn-danger" type="submit" name="anular" value="Anul·lar">
						      		</div>
					    		</div>																	
						  	</div>
						</div> 
					</form>
        		</c:if>
			</c:if>
    	</div>       
	</div>
</div>