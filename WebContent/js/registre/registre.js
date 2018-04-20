$(document).ready(function() {
	loadTipus();
	$(".centresList").selectpicker('deselectAll');
	$('.centresList').selectpicker('val', "-1");				
	$('.centresList').selectpicker('refresh');
	$('#centresList').on('change', function(){
		$('.incidencies').addClass('hidden');	
		$('.expedients').addClass('hidden');	
		if ($(this).val() != null && ($(this).val().length == 2 || $(this).val().indexOf("-1") < 0)) {		
			$('#centresList option[value="-1"]').prop('selected', false);
			if (decodeURIComponent($('#tipusList').val()) != 'Procediment judicial') {
				$.each($(this).val(), function( key, data ) {
					if (data != "-1") {
						if ($('.incidencies' + data).size() > 0) {
							$('.incidencies'  + data).removeClass('hidden');
							$('.expedients'  + data).removeClass('hidden');
						} else {
							searchIncidencies(data);
						}
					}				
				});	
			}
			$('.selectpicker').selectpicker('refresh');
		} else {	
			if ($('.selectpicker').size() > 0) {
				$(".centresList").selectpicker('deselectAll');
				$('.centresList').selectpicker('val', "-1");				
	    		$('.centresList').selectpicker('refresh');
	    	}
		}		
	});
	$('#tipusList').on('change', function(){		
		if (decodeURIComponent($(this).val()) == 'Procediment judicial') {	
			$(".centresList").selectpicker('deselectAll');
			$('.centresList').selectpicker('val', "-1");				
    		$('.centresList').selectpicker('refresh');    		
    		$('.incidencies').addClass('hidden');	
    		$('.procedimentdiv').removeClass('hidden');
		} else {
			$('.procedimentdiv').addClass('hidden');
			
		}		
	});	
	$('#procedimentList').on('change', function(){		
		if ($(this).val() == '-2') {	
    		$('.procedimentnoudiv').removeClass('hidden');
		} else {
			$('.procedimentnoudiv').addClass('hidden');
			
		}		
	});
});

function loadTipus(){
	var tipusAPI = "json/tipusRegistre.json";
	$.getJSON( tipusAPI, {
		})
    .done(function( data ) {
    	$.each( data, function( key, data ) {
    		$('#tipusList').append('<option value=' + encodeURIComponent(data.NOM) + '>' + data.NOM + '</option>');
    	});
    	if ($('#tipusSelected').val() != '') {
      		$('#tipusList option[value="' + encodeURIComponent($('#tipusSelected').val()) + '"]').attr('selected', 'selected');
    	}
    	if ($('.selectpicker').size() > 0) {
    		$('.selectpicker').selectpicker('refresh');
    	}
    });
}

function searchIncidencies(idCentre) {
	var optionDefault = '';
	if ($('#tipusRegistre').val() == 'E') {
		
		optionDefault = "<option value='-1'>Nova incidència</option>";	
		optionDefault += "<option value='-2'>Sense incidència</option>";
	} else {
		optionDefault = "<option value='-1'>Sense incidència</option>";
	}
	var html = '';
	$.ajax({
        type: "POST",
        url: "LlistatActuacionsActives",
        dataType: "json",
        data:{"idCentre":idCentre},
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	html += '<div class="form-group incidencies incidencies' + idCentre + '">';
            	html += '	<label class="col-xs-3 control-label">Incidència ' + data.nomCentre + '</label>';
            	html += ' 	<div class="col-xs-3">';   
            	html += '     	<select class="form-control selectpicker" name="incidenciesList' + idCentre + '" data-live-search="true" data-size="5" id="incidenciesList' + idCentre + '">';
            	html += 			optionDefault;
            	html += '		</select>';
            	html += '	</div>';
            	html += '</div>';
            	$('#incidencies').append(html);
        		$.each(data.llistatActuacions, function( key, data ) {
        			var refExt = '';
        			if (data.refExt != '') refExt = ' <b>(EXP ' + data.refExt + ')</b> ';
        			$('#incidenciesList' + idCentre).append('<option value=' + data.referencia + '>' + data.referencia + refExt + '-' + data.descripcio + '</option>');
        		});     
        		$('.selectpicker').selectpicker('refresh');
        		$('#incidenciesList' + idCentre).on('change', function(){
        			$('.expedients' + idCentre).remove();
    				if ($(this).val() != "-1") {
    					searchExpedients($(this).val(), idCentre);    					
    				} else {
    					$('#seleccionarInforme .loader').remove();
    				}	
        			$('.selectpicker').selectpicker('refresh');	
        			
        		});
             }                 
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}
function searchExpedients(idActuacio, idCentre) {
	var optionDefault = '';	
	var html = '';
	$.ajax({
        type: "POST",
        url: "LlistatExpedientsActuacio",
        dataType: "json",
        data:{"idActuacio":idActuacio, "idCentre":idCentre},
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	html += '<div class="form-group expedients expedients' + idCentre + '">';
            	html += '	<label class="col-xs-3 control-label">Expedient ' + data.nomCentre + '</label>';
            	html += ' 	<div class="col-xs-3">';   
            	html += '     	<select class="form-control selectpicker" name="expedientsList' + idCentre + '" data-live-search="true" data-size="5" id="expedientsList' + idCentre + '">';
            	html += '			<option value="-1">Sense expedient</option>';
            	html += '		</select>';
            	html += '	</div>';
            	html += '</div>';
            	$('#expedients').append(html);
        		$.each(data.llistatExpedients, function( key, data ) {
        			var refExt = '';
        			if (data.expcontratacio.expContratacio != '-1') refExt = ' EXP ' + data.expcontratacio.expContratacio + ' ';
        			if (data.ofertaSeleccionada != null) {
        				$('#expedientsList' + idCentre).append('<option data-idactuacio="' + data.actuacio.referencia + '" data-idinf="' + data.idInf + '" data-objecte="' + data.propostaInformeSeleccionada.objecte + '" data-total="' + data.propostaInformeSeleccionada.plic + '" data-pagat="' + data.totalFacturat + '" value=' + data.idInf + '>' + refExt + '-' + data.propostaInformeSeleccionada.objecte + '</option>');
        			}
        		});     
        		$('.selectpicker').selectpicker('refresh');   
             } 
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}