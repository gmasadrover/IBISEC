$(document).ready(function() {
	$('#pbase').on('keyup', function(){
		var pbase = $('#pbase').val().replace(',','.');
		if ($.isNumeric(pbase)) {			
			$('#iva').val((pbase * 0.21).toFixed(2));
			$('#inputIVA').val($('#iva').val());
			$('#plic').val((pbase * 1.21).toFixed(2));
			if ($('#contracte').val() == 'major') {
				$('.informacioMenors').addClass('hidden');
				$('.informacioMajors').removeClass('hidden');
			} else {
				$('.informacioMenors').removeClass('hidden');
				$('.informacioMajors').addClass('hidden');
			}
			if ($('#plic').val() > 500000) {
				$('.autoritzacioConsellGovern').removeClass('hidden');
				$('.autoritzacioConseller').addClass('hidden');
			} else if ($('#plic').val() > 50000) {
				$('.autoritzacioConseller').removeClass('hidden');
				$('.autoritzacioConsellGovern').addClass('hidden');
			} else {
				$('.autoritzacioConsellGovern').addClass('hidden');
				$('.autoritzacioConseller').addClass('hidden');
			}
		} else {
			$('#pbase').val('');
			$('#iva').val('');
			$('#plic').val('');
			$('.informacioMenors').removeClass('hidden');
			$('.informacioMajors').addClass('hidden');
			$('.autoritzacioConsellGovern').addClass('hidden');
			$('.autoritzacioConseller').addClass('hidden');
		}
	});
	$('#plic').on('keyup', function(){
		var plic = $('#plic').val().replace(',','.');
		if ($.isNumeric(plic)) {
			$('#pbase').val((plic / 1.21).toFixed(2));
			$('#iva').val(($('#pbase').val() * 0.21).toFixed(2));
			$('#inputIVA').val($('#iva').val());
			if ($('#contracte').val() == 'major') {
				$('.informacioMenors').addClass('hidden');
				$('.informacioMajors').removeClass('hidden');
			} else {
				$('.informacioMenors').removeClass('hidden');
				$('.informacioMajors').addClass('hidden');
			}
			if (plic > 500000) {
				$('.autoritzacioConsellGovern').removeClass('hidden');
				$('.autoritzacioConseller').addClass('hidden');
			} else if (plic > 50000) {
				$('.autoritzacioConseller').removeClass('hidden');
				$('.autoritzacioConsellGovern').addClass('hidden');
			} else {
				$('.autoritzacioConsellGovern').addClass('hidden');
				$('.autoritzacioConseller').addClass('hidden');
			}
		} else {
			$('#pbase').val('');
			$('#iva').val('');
			$('#plic').val('');
			$('.informacioMenors').removeClass('hidden');
			$('.informacioMajors').addClass('hidden');
			$('.autoritzacioConsellGovern').addClass('hidden');
			$('.autoritzacioConseller').addClass('hidden');
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
			$('.visibleConveni').addClass('hidden');			
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
	
	if ($('#contracte').val() == 'major') {
		$('.informacioMenors').addClass('hidden');
		$('.informacioMajors').removeClass('hidden');
	} else {
		$('.informacioMenors').removeClass('hidden');
		$('.informacioMajors').addClass('hidden');
	}
		
	if ($('#plic').val() >= 500000) {		
		$('.autoritzacioConseller').addClass('hidden');
	} else if ($('#plic').val() >= 50000) {		
		$('.autoritzacioConsellGovern').addClass('hidden');
	} else {
		$('.autoritzacioConseller').addClass('hidden');
		$('.autoritzacioConsellGovern').addClass('hidden');
	}
	
	$('#tipusContracte option[value="' + $('#tipusContractePrev').val() + '"]').attr('selected', 'selected');	
	if ($('#tipusContractePrev').val() != '') {
		if ($('#tipusContractePrev').val() != 'obr') {
			$('.visibleObres').addClass('hidden');
			if ($('#tipusContractePrev').val() == 'conveni') {
				$('.visibleConveni').removeClass('hidden');
			} else {
				$('.visibleConveni').addClass('hidden');
			}
		}
		else {		
			$('#reqLlicencia option[value="' + $('#reqLlicenciaPrev').val() + '"]').attr('selected', 'selected');
			if ($('#reqLlicenciaPrev').val() != 'si') {
				$('.visibleTipusLlicencia').addClass('hidden');
			}
			else {
				$('#tipusLlicencia option[value="' + $('#tipusLlicenciaPrev').val() + '"]').attr('selected', 'selected');
			}			
		}
	}
	$('#llistaPartides option[value="' + $('#partidaPrev').val() + '"]').attr('selected', 'selected');	
	$('#llistaUsuaris option[value="' + $('#tecnicPrev').val() + '"]').attr('selected', 'selected');	
	$('#llistaCaps option[value="' + $('#capVistiplau').val() + '"]').attr('selected', 'selected');	
	$('.selectpicker').selectpicker('refresh');		
	
	$('#createTascaConsellDeGovern').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DoCreateTascaConsellDeGovern",
	        dataType: "json",
	        data: {"informe": $(this).data('informe')},
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
	$('#createTascaConseller').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DoCreateTascaConseller",
	        dataType: "json",
	        data: {"informe": $(this).data('informe')},
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
	$('#createTascaReservaCredit').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DoCreateTascaReservaCredit",
	        dataType: "json",
	        data: {"informe": $(this).data('informe')},
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
	
	
});
