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
});

function initInformePrevi() {
	if ($('#idInformePrevi').val() > 0) {
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
		$('.selectpicker').selectpicker('refresh');		
	}	
}