$(document).ready(function() {
	$('#pbase').on('keyup', function(){
		var pbase = $('#pbase').val().replace(',','.');
		var total = parseFloat($('#totalInforme').data('total'));
		if ($.isNumeric(pbase)) {			
			$('#iva').val((pbase * 0.21).toFixed(2));
			$('#inputIVA').val($('#iva').val());
			$('#plic').val((pbase * 1.21).toFixed(2));
			$('#totalInforme').val((total + (pbase * 1.21)).toFixed(2) + '€');
			if ($('#tipusObra').val() == 'obr' && parseFloat($('#valorpbase').val()) < 50000) {
				if ( parseFloat($('#valorpbase').val()) + parseFloat(pbase) > 50000) {
					$('#errorModificacio').html("No és posible modificar aquest expedient amb aquest import. Supera el máxim permés pel tipus de contracte");
					$('.potModificar').addClass('hidden');				
				} else {
					$('#errorModificacio').html("");
					$('.potModificar').removeClass('hidden');
				}
			} else if ($('#tipusObra').val() != 'obr' && parseFloat($('#valorPBase').val()) < 18000){
				if ( parseFloat($('#valorPBase').val()) + parseFloat(pbase) > 18000) {
					$('#errorModificacio').html("No és posible modificar aquest expedient amb aquest import. Supera el máxim permés pel tipus de contracte");
					$('.potModificar').addClass('hidden');				
				} else {
					$('#errorModificacio').html("");
					$('.potModificar').removeClass('hidden');
				}
			} else {
				$('#errorModificacio').html("");
				$('.potModificar').removeClass('hidden');
			}
		} else {
			$('#pbase').val('');
			$('#iva').val('');
			$('#plic').val('');
			$('#totalInforme').val((total).toFixed(2) + '€');
			$('#errorModificacio').html("");
			$('.potModificar').removeClass('hidden');
		}
	});
	$('#plic').on('keyup', function(){
		var plic = $('#plic').val().replace(',','.');
		var total = parseFloat($('#totalInforme').data('total'));
		if ($.isNumeric(plic)) {
			$('#pbase').val((plic / 1.21).toFixed(2));
			$('#iva').val(($('#pbase').val() * 0.21).toFixed(2));
			$('#inputIVA').val($('#iva').val());
			$('#totalInforme').val((total + parseFloat(plic)).toFixed(2) + '€');	
			if ($('#tipusObra').val() == 'obr' && parseFloat($('#valorPBase').val()) < 50000) {
				if ( parseFloat($('#valorPBase').val()) + parseFloat($('#pbase').val()) > 50000) {
					$('#errorModificacio').html("No és posible modificar aquest expedient amb aquest import. Supera el máxim permés pel tipus de contracte");
					$('.potModificar').addClass('hidden');				
				} else {
					$('#errorModificacio').html("");
					$('.potModificar').removeClass('hidden');
				}
			} else if ($('#tipusObra').val() != 'obr' && parseFloat($('#valorPBase').val()) < 18000){
				if ( parseFloat($('#valorPBase').val()) + parseFloat($('#pbase').val()) > 18000) {
					$('#errorModificacio').html("No és posible modificar aquest expedient amb aquest import. Supera el máxim permés pel tipus de contracte");
					$('.potModificar').addClass('hidden');				
				} else {
					$('#errorModificacio').html("");
					$('.potModificar').removeClass('hidden');
				}
			} else {
				$('#errorModificacio').html("");
				$('.potModificar').removeClass('hidden');
			}			
		} else {
			$('#pbase').val('');
			$('#iva').val('');
			$('#plic').val('');
			$('#totalInforme').val((total).toFixed(2) + '€');
			$('#errorModificacio').html("");
			$('.potModificar').removeClass('hidden');
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
	
	$('#reqLlicencia option[value="' + $('#reqLlicenciaPrev').val() + '"]').attr('selected', 'selected');
	if ($('#reqLlicenciaPrev').val() != 'si') {
		$('.visibleTipusLlicencia').addClass('hidden');
	}
	else {
		$('#tipusLlicencia option[value="' + $('#tipusLlicenciaPrev').val() + '"]').attr('selected', 'selected');
	}

	$('#llistaEmpreses option[value="' + $('#empresaPrev').val() + '"]').attr('selected', 'selected');	
	$('.selectpicker').selectpicker('refresh');		
});
