$(document).ready(function() {
	//funcionalitat seguiment tasca
	$('#seguiment').on('change', function(){
		$.ajax({
	        type: "POST",
	        url: "seguirTasca",
	        data: {"idTasca":$(this).data('idtasca'), "idUsuari":$(this).data('idusuari'), "seguir":$(this).data('seguir')},
	        dataType: "html",	        
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {
	            //our country code was correct so we have some information to display	  
	        	location.reload();
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	        }  
	    });
	});
	//funcionalitat seguiment actuació
	$('#seguimentActuacio').on('change', function(){
		$.ajax({
	        type: "POST",
	        url: "seguirActuacio",
	        data: {"idActuacio":$(this).data('idactuacio'), "idUsuari":$(this).data('idusuari'), "seguir":$(this).data('seguir')},
	        dataType: "html",	        
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {
	            //our country code was correct so we have some information to display	  
	        	location.reload();
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	        }  
	    });
	});
	
	//funcionalitats informe previ
	if ($('#infPrev').size() == 1) {
		console.log('entra');
		initInformePrevi();
	}
	//funcionalitats recerca presuposts
	if ($('#liciMenor').size() == 1) {
		$('.filerTable').DataTable({
			"order": [[ 4, "asc" ]],
			"aoColumns": [
				null,
	    		null,
	    		{"bVisible": false},
	    		{"bVisible": false},
	    		{"iDataSort": 4},
	    		{"bVisible": false},
	    		null,
	    		null
			]
		});
		$('#oferta').on('keyup', function(){
			var oferta = $('#oferta').val().replace(',','.');
			if ($.isNumeric(oferta)) {			
				
			} else {
				$('#oferta').val('');
			}
		});
		
		$('.ofertaSeleccionada').on('click', function(){
    		var table = $('.filerTable').DataTable();      
    		$(this).parents('tr').addClass('selected');
    		$('#ofertaSeleccionada').text(table.row('.selected').data()[2]);  
    		$('#idOfertaSeleccionada').val(table.row('.selected').data()[0]);  
    		$(this).parents('tr').removeClass('selected');
    	});		
	}
	
	$('.deleteOferta').on('click', function(){
		var table = $('.filerTable').DataTable();
		$(this).parents('tr').addClass('selected');
		if (table.row('.selected').data() != undefined && $('#llistatOfertes input[value="' + table.row('.selected').data()[2] + "#" + table.row('.selected').data()[4] + '"]').size() > 0) {
			$('#llistatOfertes input[value="' + table.row('.selected').data()[2] + "#" + table.row('.selected').data()[4] + '"]').remove();
			if ($('#ofertaSeleccionadaNIF').val() == table.row('.selected').data()[2]) {
				$('#ofertaSeleccionada').text('');  
        		$('#ofertaSeleccionadaNIF').val('');  
			}
		}
		table.row('.selected').remove().draw( false );
		$.ajax({
	        type: "POST",
	        url: "DoDeleteOferta",
	        dataType: "json",
	        data: {"idOferta": $(this).data('idoferta')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {
	            //our country code was correct so we have some information to display
	        	location.reload();      
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	        }  
	    });
	});
	
	$('#novaProposta').on('click', function(){
		var numPA = parseInt($('#infPrev').val()) + 1;
		var newForm = '<div class="panel panel-default">';
		newForm += '	<div class="panel-heading">';
		newForm += '		<h4 class="panel-title">';
		newForm += '			<a data-toggle="collapse" data-parent="#accordion" href="#propostaActuacio' + numPA + '">Proposta actuació ' + numPA +  '</a>';
		newForm += '		</h4>';
		newForm += '	</div>';
		newForm += '	<div id="propostaActuacio' + numPA + '" class="panel-collapse collapse">';				    	
		newForm += '		<div class="panel-body">';				      		
		newForm += '			<div class="col-md-12 panel-group" id="accordionInformes">';
		newForm += '				<input type="hidden" name="idInformePrevi' + numPA + '" value="">';								                    		
		newForm += '				<div class="form-group">';
		newForm += '		     		<div class="col-md-2">';
		newForm += '		     	 		<label>Tipus de Contracte</label>';							            	 	
		newForm += '		            	<select class="form-control selectpicker" name="tipusContracte' + numPA + '" id="tipusContracte' + numPA + '">';
		newForm += '			           		<option value="obr">Obra</option>';
		newForm += '			           		<option value="srv">Servei</option>';
		newForm += '			           		<option value="submi">subministrament</option>';
		newForm += '			            </select>';
		newForm += '			        </div>';	
		newForm += '			       	<div class="visibleObres' + numPA + '">';					                             	
		newForm += '			        	<div class="col-md-2">';
		newForm += '				      	 	<label>Llicència</label>';
		newForm += '				            <select class="form-control selectpicker" name="reqLlicencia' + numPA + '" id="reqLlicencia' + numPA + '">';
		newForm += '				            	<option value="si">Si</option>';
		newForm += '				            	<option value="no">No</option>';
		newForm += '				            </select>';
		newForm += '		    	        </div>';	
		newForm += '		       	     	<div class="col-md-3 visibleTipusLlicencia' + numPA + '">';
		newForm += '			   		   	 	<label>Tipus de llicència</label>';
		newForm += '		                	<select class="form-control selectpicker" name="tipusLlicencia' + numPA + '" id="tipusLlicencia' + numPA + '">';
		newForm += '		                		<option value="major">Major</option>';
		newForm += '		                		<option value="menor">menor</option>';
		newForm += '		                		<option value="comun">Comunicació prèvia</option>';
		newForm += '		                	</select>';
		newForm += '			           	</div>';
		newForm += '			           	<div class="col-md-3">';
		newForm += '			      	 		<label>Formalització contracte</label>';
		newForm += '			                <select class="form-control selectpicker" name="formContracte' + numPA + '" id="formContracte' + numPA + '">';
		newForm += '			                	<option value="si">Si</option>';
		newForm += '			                	<option value="no">No</option>';
		newForm += '			                </select>';
		newForm += '			      		</div>';
		newForm += '					</div>';				                       																
		newForm += '				</div>';					                    						                    		
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-md-12">';					                    			
		newForm += '						<label>Objecte</label>';
		newForm += '						<textarea class="form-control" name="objecteActuacio' + numPA + '" placeholder="objecte" rows="3" required></textarea>';
		newForm += '					</div>';
		newForm += '				</div>';
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-md-12">';
		newForm += '						<label>Pressupost</label>';
		newForm += '					</div>';
		newForm += '				</div>';
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-md-4">';
		newForm += '			          	<label>VEC</label>';
		newForm += '			          	<input class="" name="vec' + numPA + '" id="vec' + numPA + '" placeholder="0000,00" required>';
		newForm += '			          	<label class="">€</label>';
		newForm += '			        </div>';
		newForm += '			        <div class="col-md-4">';
		newForm += '				     	<label>IVA</label>';
		newForm += '				       	<input disabled id="iva' + numPA + '" placeholder="0000,00">';
		newForm += '				     	<input type="hidden" name="iva' + numPA + '" id="inputIVA' + numPA + '">';
		newForm += '				       	<label class="">€</label>';
		newForm += '					</div>';
		newForm += '					<div class="col-md-4">';
		newForm += '						<label>PLic</label>';
		newForm += '						<input name="plic' + numPA + '" id="plic' + numPA + '" placeholder="0000,00">';
		newForm += '						<label class="">€</label>';
		newForm += '					</div>';				                                
		newForm += '				</div>';
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-md-6">';
		newForm += '						<label>Termini d\'execució</label>';
		newForm += '						<input name="termini' + numPA + '" placeholder="" required>';
		newForm += '					</div>';
		newForm += '				</div>';
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-md-12">';	
		newForm += '						<div class="row">';	 
		newForm += '							<div class="col-md-12">';					                    						
		newForm += '		       		   			<textarea class="form-control" name="comentariTecnic' + numPA + '" placeholder="comentari tècnic" rows="3"></textarea>'; 
		newForm += '		           		 	</div>';
		newForm += '		        		</div>';	        	
		newForm += '					</div>';						                       		
		newForm += '				</div>';
		newForm += '			</div>';
		newForm += '		</div>';
		newForm += '	</div>';
		newForm += '</div>';
		$('#accordion').append(newForm);
		$('#infPrev').val(numPA);
		$('.selectpicker').selectpicker('refresh');	
		
		$('#vec' + numPA).on('keyup', function(){
			var vec = $('#vec' + numPA).val().replace(',','.');
			if ($.isNumeric(vec)) {			
				$('#iva' + numPA).val((vec * 0.21).toFixed(2));
				$('#inputIVA' + numPA).val($('#iva' + numPA).val());
				$('#plic' + numPA).val((vec * 1.21).toFixed(2));
			} else {
				$('#vec' + numPA).val('');
				$('#iva' + numPA).val('');
				$('#plic' + numPA).val('');
			}
		});
		$('#plic' + numPA).on('keyup', function(){
			var plic = $('#plic' + numPA).val().replace(',','.');
			if ($.isNumeric(plic)) {
				$('#vec' + numPA).val((plic / 1.21).toFixed(2));
				$('#iva' + numPA).val(($('#vec' + numPA).val() * 0.21).toFixed(2));
				$('#inputIVA' + numPA).val($('#iva' + numPA).val());
			} else {
				$('#vec' + numPA).val('');
				$('#iva' + numPA).val('');
				$('#plic' + numPA).val('');
			}
		});
		$('#tipusContracte' + numPA).on('change', function(){
			var tipus = $(this).val();
			if (tipus != 'obr') {
				$('.visibleObres' + numPA).addClass('hidden');
			}else{
				$('.visibleObres' + numPA).removeClass('hidden');
			}
		});
		$('#reqLlicencia' + numPA).on('change', function(){
			var tipus = $(this).val();
			if (tipus == 'si') {
				$('.visibleTipusLlicencia' + numPA).removeClass('hidden');
			}else{
				$('.visibleTipusLlicencia' + numPA).addClass('hidden');
			}
		});
	});
});

function initInformePrevi() {
	var numPA = parseInt($('#infPrev').val());
	for (i = 1; i <= numPA; i++) { 
		console.log(i);
		$('#tipusContracte' + i + ' option[value="' + $('#tipusContractePrev' + i).val() + '"]').attr('selected', 'selected');	
		if ($('#tipusContractePrev' + i).val() != 'obr') {
			$('.visibleObres' + i).addClass('hidden');
		}
		else {
			$('#reqLlicencia' + i + ' option[value="' + $('#reqLlicenciaPrev' + i).val() + '"]').attr('selected', 'selected');
			if ($('#reqLlicenciaPrev' + i).val() != 'si') {
				$('.visibleTipusLlicencia' + i).addClass('hidden');
			}
			else {
				$('#tipusLlicencia' + i + ' option[value="' + $('#tipusLlicenciaPrev' + i).val() + '"]').attr('selected', 'selected');
			}
			$('#formContracte' + i + ' option[value="' + $('#formContractePrev' + i).val() + '"]').attr('selected', 'selected');
		}
		$('.selectpicker').selectpicker('refresh');		
		$('#vec' + i).on('keyup', function(){
			var vec = $(this).val().replace(',','.');
			if ($.isNumeric(vec)) {			
				$(this).closest('.form-group').find('.iva').val((vec * 0.21).toFixed(2));
				$(this).closest('.form-group').find('.inputIVA').val($(this).closest('.form-group').find('.iva').val());
				$(this).closest('.form-group').find('.plic').val((vec * 1.21).toFixed(2));
			} else {
				$(this).val('');
				$(this).closest('.form-group').find('.iva').val('');
				$(this).closest('.form-group').find('.plic').val('');
			}
		});
		$('#plic' + i).on('keyup', function(){
			var plic = $(this).val().replace(',','.');
			if ($.isNumeric(plic)) {
				$(this).closest('.form-group').find('.vec').val((plic / 1.21).toFixed(2));
				var vec = $(this).closest('.form-group').find('.vec').val()
				$(this).closest('.form-group').find('.iva').val((vec * 0.21).toFixed(2));
				$(this).closest('.form-group').find('.inputIVA').val($(this).closest('.form-group').find('.iva').val());
				
			} else {
				$(this).closest('.form-group').find('.vec').val('');
				$(this).closest('.form-group').find('.iva').val('');
				$(this).val('');
			}
		});
		$('#tipusContracte' + i).on('change', function(){
			var tipus = $(this).val();
			if (tipus != 'obr') {
				$(this).closest('.form-group').find('.visibleObres').addClass('hidden');
			}else{
				$(this).closest('.form-group').find('.visibleObres').removeClass('hidden');
			}
		});
		$('#reqLlicencia' + i).on('change', function(){
			var tipus = $(this).val();
			if (tipus == 'si') {
				$(this).closest('.form-group').find('.visibleTipusLlicencia').removeClass('hidden');
			}else{
				$(this).closest('.form-group').find('.visibleTipusLlicencia').addClass('hidden');
			}
		});
	}
}