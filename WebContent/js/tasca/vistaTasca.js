$(document).ready(function() {	
	$('#prioritatList option[value="' + $('#prioritatActual').val() + '"]').attr('selected', 'selected');	
	$('#tipusTascaList option[value="' + $('#tipusTascaPrevi').val() + '"]').attr('selected', 'selected');	
	$('.selectpicker').selectpicker('refresh');		
	
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
    		getDespesaEmpresa(table.row('.selected').data()[3], table.row('.selected').data()[5]);
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
	
	$('#marcarInformeAcabat').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DoMarcarInformeAcabat",
	        dataType: "json",
	        data: {"idInforme": $(this).data('idinforme')},
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

function initInformePrevi() {
		$('#tipusContracte option[value="' + $('#tipusContractePrev').val() + '"]').attr('selected', 'selected');	
		$('#llistaUsuaris option[value="' + $('#tecnicPrevi').val() + '"]').attr('selected', 'selected');	
		if ($('#tipusContractePrev').val() != 'obr' && $('#tipusContractePrev').val() != '') {
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
		}
		$('.selectpicker').selectpicker('refresh');		
		$('#pbase').on('keyup', function(){
			var pbase = $(this).val().replace(',','.');
			if ($.isNumeric(pbase)) {			
				$(this).closest('.form-group').find('.iva').val((pbase * 0.21).toFixed(2));
				$(this).closest('.form-group').find('.inputIVA').val($(this).closest('.form-group').find('.iva').val());
				$(this).closest('.form-group').find('.plic').val((pbase * 1.21).toFixed(2));
			} else {
				$(this).val('');
				$(this).closest('.form-group').find('.iva').val('');
				$(this).closest('.form-group').find('.plic').val('');
			}
		});
		$('#plic').on('keyup', function(){
			var plic = $(this).val().replace(',','.');
			if ($.isNumeric(plic)) {
				$(this).closest('.form-group').find('.pbase').val((plic / 1.21).toFixed(2));
				var pbase = $(this).closest('.form-group').find('.pbase').val()
				$(this).closest('.form-group').find('.iva').val((pbase * 0.21).toFixed(2));
				$(this).closest('.form-group').find('.inputIVA').val($(this).closest('.form-group').find('.iva').val());
				
			} else {
				$(this).closest('.form-group').find('.pbase').val('');
				$(this).closest('.form-group').find('.iva').val('');
				$(this).val('');
			}
		});
		$('#tipusContracte').on('change', function(){
			var tipus = $(this).val();
			if (tipus != 'obr') {
				$(this).closest('.form-group').find('.visibleObres').addClass('hidden');
			}else{
				$(this).closest('.form-group').find('.visibleObres').removeClass('hidden');
			}
		});
		$('#reqLlicencia').on('change', function(){
			var tipus = $(this).val();
			if (tipus == 'si') {
				$(this).closest('.form-group').find('.visibleTipusLlicencia').removeClass('hidden');
			}else{
				$(this).closest('.form-group').find('.visibleTipusLlicencia').addClass('hidden');
			}
		});
}

function getDespesaEmpresa(cif, valor) {
	$.ajax({
        type: "POST",
        url: "getDespesaEmpresa",
        data: {"cif":cif, "dataIni":"09/03/2018"},
        dataType: "json",	        
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display	  
        	if (parseFloat(valor) + data.importAdjudicat >= 40000) {
        		$('#superaMaximPermes').removeClass('hidden');
        	} else {
        		$('#superaMaximPermes').addClass('hidden');
        	}
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}