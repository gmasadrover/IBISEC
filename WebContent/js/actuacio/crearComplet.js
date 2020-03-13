$(document).ready(function() {
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
	
	$('#pbase').on('keyup', function(){
		var pbase = $('#pbase').val().replace(',','.');
		if ($.isNumeric(pbase)) {			
			$('#iva').val((pbase * 0.21).toFixed(2));
			$('#inputIVA').val($('#iva').val());
			$('#plic').val((pbase * 1.21).toFixed(2));
		} else {
			$('#pbase').val('');
			$('#iva').val('');
			$('#plic').val('');
		}
	});
	$('#plic').on('keyup', function(){
		var plic = $('#plic').val().replace(',','.');
		if ($.isNumeric(plic)) {
			$('#pbase').val((plic / 1.21).toFixed(2));
			$('#iva').val(($('#pbase').val() * 0.21).toFixed(2));
			$('#inputIVA').val($('#iva').val());
		} else {
			$('#pbase').val('');
			$('#iva').val('');
			$('#plic').val('');
		}
	});
	$('#tipusContracte').on('change', function(){
		var tipus = $(this).val();
		if (tipus != 'obr') {
			$('.visibleObres').addClass('hidden');						
			if (tipus == 'conveni') {
				$('.visibleConveni').removeClass('hidden');
			} else {
				$('.visibleConveni').addClass('hidden');
			}
		}else{
			$('.visibleObres').removeClass('hidden');
			$('.visibleConveni').addClass('hidden');			
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
});