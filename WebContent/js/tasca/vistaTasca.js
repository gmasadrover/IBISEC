$(document).ready(function() {
	//funcionalitats informe previ
	if ($('#infPrev').size() == 1) {
		initInformePrevi();
		$('#vec').on('keyup', function(){
			var vec = $('#vec').val().replace(',','.');
			if ($.isNumeric(vec)) {			
				$('#iva').val((vec * 0.21).toFixed(2));
				$('#inputIVA').val($('#iva').val());
				$('#plic').val((vec * 1.21).toFixed(2));
				$('#inputPLIC').val($('#plic').val());
			} else {
				$('#vec').val('');
				$('#iva').val('');
				$('#plic').val('');
			}
		});
		$('#plic').on('keyup', function(){
			var plic = $('#plic').val().replace(',','.');
			if ($.isNumeric(plic)) {			
				$('#iva').val((plic * 0.21).toFixed(2));
				$('#inputIVA').val($('#iva').val());
				$('#vec').val((plic / 1.21).toFixed(2));
			} else {
				$('#vec').val('');
				$('#iva').val('');
				$('#plic').val('');
			}
		});
		$('#tipusContracte').on('change', function(){
			var tipus = $(this).val();
			if (tipus != 'obr') {
				$('.visibleObres').addClass('hidden');
			}else{
				$('.visibleObres').removeClass('hidden');
			}
		});
		$('#reqLlicencia').on('change', function(){
			var tipus = $(this).val();
			if (tipus == 'si') {
				$('.visibleTipusLlicencia').removeClass('hidden');
			}else{
				$('.visibleTipusLlicencia').addClass('hidden');
			}
		});
	}
	//funcionalitats recerca presuposts
	if ($('#liciMenor').size() == 1) {
		var numOferta = 1;
		$('.filerTable').DataTable({
			"order": [[ 3, "asc" ]],
			"aoColumns": [
	    		null,
	    		{"bVisible": false},
	    		{"bVisible": false},
	    		{"iDataSort": 3},
	    		{"bVisible": false},
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
		$('#afegirOferta').on('click', function(){
			var oferta = "<input name='ofertes' value='" + $('#llistaEmpreses').val() + "#" + $('#oferta').val().replace(',','.') + "'>";
			numOferta += 1;
			$('#llistatOfertes').append(oferta);
        	var table = $('.filerTable').DataTable();        	
        	table.row.add( [
        		 "<a href='editEmpresa?cif=" + $('#llistaEmpreses').val() + "'>" + $("#llistaEmpreses option[value='" + $('#llistaEmpreses').val() + "']").text() + ' (' + $('#llistaEmpreses').val() + ")</a>",
        		 $("#llistaEmpreses option[value='" + $('#llistaEmpreses').val() + "']").text() + ' (' + $('#llistaEmpreses').val() + ")",
        		 $('#llistaEmpreses').val(),
        		 $('#oferta').val().replace(',','.') + "€",
        		 $('#oferta').val().replace(',','.'),
        		 "<input class='btn btn-primary btn-sm ofertaSeleccionada' type='button' value='Seleccionar'><input class='btn btn-danger btn-sm eliminarSeleccionada margin_left10' type='button' value='Eliminar'>"
            ] ).draw( false );
        	$('.ofertaSeleccionada').on('click', function(){
        		var table = $('.filerTable').DataTable();      
        		$(this).parents('tr').addClass('selected');
        		$('#ofertaSeleccionada').text(table.row('.selected').data()[1]);  
        		$('#ofertaSeleccionadaNIF').val(table.row('.selected').data()[2]);  
        		$(this).parents('tr').removeClass('selected');
        	});
			$('.eliminarSeleccionada').on('click', function(){	
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
        	});
		});
	}
	
	$('#novaProposta').on('click', function(){
		var numPA = $('#infPrev').data('pa') + 1;
		var newForm = '<div class="panel panel-default">';
		newForm += '	<div class="panel-heading">';
		newForm += '		<h4 class="panel-title">';
		newForm += '			<a data-toggle="collapse" data-parent="#accordion" href="#propostaActuacio' + numPA + '">Proposta actuació ' + numPA +  '</a>';
		newForm += '		</h4>';
		newForm += '	</div>';
		newForm += '	<div id="propostaActuacio' + numPA + '" class="panel-collapse collapse">';				    	
		newForm += '		<div class="panel-body">';				      		
		newForm += '			<div class="col-lg-12 panel-group" id="accordionInformes">';
		newForm += '				<input type="hidden" name="idInformePrevi" id="idInformePrevi" value="">';								                    		
		newForm += '				<div class="form-group">';
		newForm += '		     		<div class="col-lg-2">';
		newForm += '		     	 		<label>Tipus de Contracte</label>';							            	 	
		newForm += '		            	<select class="form-control selectpicker" name="tipusContracte" id="tipusContracte">';
		newForm += '			           		<option value="obr">Obra</option>';
		newForm += '			           		<option value="srv">Servei</option>';
		newForm += '			           		<option value="submi">subministrament</option>';
		newForm += '			            </select>';
		newForm += '			        </div>';	
		newForm += '			       	<div class="visibleObres">';					                             	
		newForm += '			        	<div class="col-lg-2">';
		newForm += '				      	 	<label>Llicència</label>';
		newForm += '				            <select class="form-control selectpicker" name="reqLlicencia" id="reqLlicencia">';
		newForm += '				            	<option value="si">Si</option>';
		newForm += '				            	<option value="no">No</option>';
		newForm += '				            </select>';
		newForm += '		    	        </div>';	
		newForm += '		       	     	<div class="col-lg-3 visibleTipusLlicencia">';
		newForm += '			   		   	 	<label>Tipus de llicència</label>';
		newForm += '		                	<select class="form-control selectpicker" name="tipusLlicencia" id="tipusLlicencia">';
		newForm += '		                		<option value="major">Major</option>';
		newForm += '		                		<option value="menor">menor</option>';
		newForm += '		                		<option value="comun">Comunicació prèvia</option>';
		newForm += '		                	</select>';
		newForm += '			           	</div>';
		newForm += '			           	<div class="col-lg-3">';
		newForm += '			      	 		<label>Formalització contracte</label>';
		newForm += '			                <select class="form-control selectpicker" name="formContracte" id="formContracte">';
		newForm += '			                	<option value="si">Si</option>';
		newForm += '			                	<option value="no">No</option>';
		newForm += '			                </select>';
		newForm += '			      		</div>';
		newForm += '					</div>';				                       																
		newForm += '				</div>';					                    						                    		
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-lg-12">';					                    			
		newForm += '						<label>Objecte</label>';
		newForm += '						<textarea class="form-control" name="objecteActuacio" placeholder="objecte" rows="3" required></textarea>';
		newForm += '					</div>';
		newForm += '				</div>';
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-lg-12">';
		newForm += '						<label>Pressupost</label>';
		newForm += '					</div>';
		newForm += '				</div>';
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-lg-4">';
		newForm += '			          	<label>VEC</label>';
		newForm += '			          	<input class="" name="vec" id="vec" placeholder="0000,00"required>';
		newForm += '			          	<label class="">€</label>';
		newForm += '			        </div>';
		newForm += '			        <div class="col-lg-4">';
		newForm += '				     	<label>IVA</label>';
		newForm += '				       	<input disabled id="iva" placeholder="0000,00">';
		newForm += '				     	<input type="hidden" name="iva" id="inputIVA">';
		newForm += '				       	<label class="">€</label>';
		newForm += '					</div>';
		newForm += '					<div class="col-lg-4">';
		newForm += '						<label>PLic</label>';
		newForm += '						<input id="plic" placeholder="0000,00">';
		newForm += '						<input type="hidden" name="plic" id="inputPLIC">';
		newForm += '						<label class="">€</label>';
		newForm += '					</div>';				                                
		newForm += '				</div>';
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-lg-6">';
		newForm += '						<label>Termini d\'execució</label>';
		newForm += '						<input name="termini" placeholder="" required>';
		newForm += '					</div>';
		newForm += '				</div>';
		newForm += '				<div class="form-group">';
		newForm += '					<div class="col-lg-12">';	
		newForm += '						<div class="row">';	 
		newForm += '							<div class="col-lg-12">';					                    						
		newForm += '		       		   			<textarea class="form-control" name="comentariTecnic" placeholder="comentari tècnic" rows="3"></textarea>'; 
		newForm += '		           		 	</div>';
		newForm += '		        		</div>';	        	
		newForm += '					</div>';						                       		
		newForm += '				</div>';
		newForm += '			</div>';
		newForm += '		</div>';
		newForm += '	</div>';
		newForm += '</div>';
		$('#accordion').append(newForm);
		$('#infPrev').data('pa', numPA);
	});
});

function initInformePrevi() {
	if ($('#idInformePrevi').val() != '') {
		$('#tipusContracte option[value="' + $('#tipusContractePrev').val() + '"]').attr('selected', 'selected');	
		if ($('#tipusContractePrev').val() != 'obr') {
			$('.visibleObres').addClass('hidden');
		}
		else {
			$('#reqLlicencia option[value="' + $('#reqLlicenciaPrev').val() + '"]').attr('selected', 'selected');
			if ($('#reqLlicenciaPrev').val() != 'si') {
				$('.visibleTipusLlicencia').addClass('hidden');
			}
			else {
				$('#tipusLlicencia option[value="' + $('#tipusLlicenciaPrev').val() + '"]').attr('selected', 'selected');
			}
			$('#formContracte option[value="' + $('#formContractePrev').val() + '"]').attr('selected', 'selected');
		}
		$('#tipusServei option[value="' + $('#tipusServeiPrev').val() + '"]').attr('selected', 'selected');	
		$('.selectpicker').selectpicker('refresh');		
	}	
}