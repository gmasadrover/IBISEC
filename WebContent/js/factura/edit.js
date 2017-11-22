$(document).ready(function() {
	$('#import').on('keyup', function(){
		var valorFactura = $('#import').val().replace(',','.');
		if ($.isNumeric(valorFactura)) {			
			
		} else {
			$('#import').val('');
		}
	});		
	$('#llistaEmpreses option[value="' + $('#nifProveidor').val() + '"]').attr('selected', 'selected');	
	$('#usuarisList option[value="' + $('#idUsuariInforme').val() + '"]').attr('selected', 'selected');	

	$('#centresList').on('change', function(){
		$('#seleccionarInforme').append('<div class="loader"></div>');
		$('#incidencies').html('');	
		$('#expedients').html('');	
		if ($(this).val() != '-1') {		
			searchIncidencies($(this).val());
		} else {
			$('#seleccionarInforme .loader').remove();
		}
	});
	$('#centresList option[value="-1"]').attr('selected', 'selected');	
	$('.selectpicker').selectpicker('refresh');
	
});

function searchIncidencies(idCentre) {
	var optionDefault = '';	
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
            	html += '<div class="form-group incidencies">';
            	html += '	<label class="col-xs-3 control-label">Actuació</label>';
            	html += ' 	<div class="col-xs-3">';   
            	html += '     	<select class="form-control selectpicker" name="incidenciesList" data-live-search="true" data-size="5" id="incidenciesList">';
            	html += '			<option value="-1">Sense actuacio</option>';
            	html += '		</select>';
            	html += '	</div>';
            	html += '</div>';
            	$('#incidencies').append(html);            	
        		$.each(data.llistatActuacions, function( key, data ) {
        			var refExt = '';
        			if (data.refExt != '') refExt = ' <b>(EXP ' + data.refExt + ')</b> ';
        			$('#incidenciesList').append('<option value=' + data.referencia + '>' + data.referencia + refExt + '-' + data.descripcio + '</option>');
        		});     
        		$('.selectpicker').selectpicker('refresh');   
        		$('#seleccionarInforme .loader').remove();
        		$('#incidenciesList').on('change', function(){
        			$('#expedients').html('');	
        			$('#seleccionarInforme').append('<div class="loader"></div>');
        			$('.expedients').addClass('hidden');
    				if ($(this).val() != "-1") {
    					searchExpedients($(this).val());    					
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

function searchExpedients(idActuacio) {
	var optionDefault = '';	
	var html = '';
	$.ajax({
        type: "POST",
        url: "LlistatExpedientsActuacio",
        dataType: "json",
        data:{"idActuacio":idActuacio},
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	html += '<div class="form-group expedients">';
            	html += '	<label class="col-xs-3 control-label">Expedient</label>';
            	html += ' 	<div class="col-xs-3">';   
            	html += '     	<select class="form-control selectpicker" name="expedientsList" data-live-search="true" data-size="5" id="expedientsList">';
            	html += '			<option value="-1">Sense expedient</option>';
            	html += '		</select>';
            	html += '	</div>';
            	html += '</div>';
            	$('#expedients').append(html);
        		$.each(data.llistatExpedients, function( key, data ) {
        			var refExt = '';
        			if (data.expcontratacio.expContratacio != '-1') refExt = ' <b>(EXP ' + data.expcontratacio.expContratacio + ')</b> ';
        			$('#expedientsList').append('<option value=' + data.idInf + '>' + data.idInf + refExt + '-' + data.propostaInformeSeleccionada.objecte + '</option>');
        		});     
        		$('.selectpicker').selectpicker('refresh');        		
             }    
             $('#seleccionarInforme .loader').remove();
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}