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
		} else if(pbase != '-') {
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
		} else if(plic != '-') {
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
	
	$('#tipusIncidencia').on('change', function(){
		if ($('#tipusIncidencia').val() == 'penalitzacio') { //Penalitzacions			
			$('#seccioLlicendia').addClass('hidden');
			$('#seccioPressupost').addClass('hidden');
			$('#seccioTermini').addClass('hidden');
			$('#seccioEmpresa').addClass('hidden');
			$('#seccioPenalitzacio').removeClass('hidden');
		} else if ($('#tipusIncidencia').val() == 'certfinal') { //Certificació Final
			$('#seccioLlicendia').addClass('hidden');
			$('#seccioPressupost').removeClass('hidden');
			$('#seccioTermini').addClass('hidden');
			$('#seccioEmpresa').removeClass('hidden');
			$('#seccioPenalitzacio').addClass('hidden');
		} else if ($('#tipusIncidencia').val() == 'termini'){ //Ampliacions termini
			$('#seccioLlicendia').addClass('hidden');
			$('#seccioPressupost').addClass('hidden');
			$('#seccioTermini').removeClass('hidden');
			$('#seccioEmpresa').addClass('hidden');
			$('#seccioPenalitzacio').addClass('hidden');
		} else if ($('#tipusIncidencia').val() == 'resolucioContracte'){ //Resolució Contracte
			$('#seccioLlicendia').addClass('hidden');
			$('#seccioPressupost').addClass('hidden');
			$('#seccioTermini').addClass('hidden');
			$('#seccioEmpresa').addClass('hidden');
			$('#seccioPenalitzacio').addClass('hidden');
		} else if ($('#tipusIncidencia').val() == 'informeExecucio'){ //Informe Execució
			$('#seccioLlicendia').addClass('hidden');
			$('#seccioPressupost').addClass('hidden');
			$('#seccioTermini').addClass('hidden');
			$('#seccioEmpresa').addClass('hidden');
			$('#seccioPenalitzacio').addClass('hidden');
		} else if ($('#tipusIncidencia').val() == 'enriquimentInjust'){ // Enriquiment execució	
			$('#seccioLlicendia').addClass('hidden');
			$('#seccioPressupost').removeClass('hidden');
			$('#seccioTermini').addClass('hidden');
			$('#seccioEmpresa').removeClass('hidden');
			$('#seccioPenalitzacio').addClass('hidden');
		} else { // Modificacions i preus contradictoris
			$('#seccioLlicendia').removeClass('hidden');
			$('#seccioPressupost').removeClass('hidden');
			$('#seccioTermini').removeClass('hidden');
			$('#seccioEmpresa').removeClass('hidden');
			$('#seccioPenalitzacio').addClass('hidden');
		}
	});
	
	$('#plicPenalitzacio').on('keyup', function(){
		var plic = $('#plicPenalitzacio').val().replace(',','.');
		if ($.isNumeric(plic)) {
			
		} else if(plic != '-') {
			$('#plicPenalitzacio').val('');
			$('#errorModificacio').html("");
			$('.potModificar').removeClass('hidden');
		}
	});
	
});
