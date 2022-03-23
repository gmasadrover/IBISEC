$(document).ready(function() {
	$('#import').on('keyup', function(){
		var valorFactura = $('#import').val().replace(',','.');
		if ($.isNumeric(valorFactura)) {			
			
		} else {
			$('#import').val('');
		}
	});		
	$('#centresList option[value="' + $('#idCentreActual').val() + '"]').attr('selected', 'selected');
	if ($('#idCentreActual').val() != '' && $('#idCentreActual').val() != '-1') {	
		if ($('#idCentreActual').val() == 'procediment') {	
			$('.proveidor').addClass('hidden');
			searchProcediments();
		} else {
			searchIncidencies($('#idCentreActual').val());
		}		
	} 
	if ($('#idActuacio').val() != "-1") {
		if ($('#idActuacio').val().indexOf("PRO-") !== -1) {
			
		} else {
			searchExpedients($('#idActuacio').val());    	
		}		
	}
		
	$('#llistaEmpreses option[value="' + $('#nifProveidor').val() + '"]').attr('selected', 'selected');	
	$('#usuarisList option[value="' + $('#idUsuariInforme').val() + '"]').attr('selected', 'selected');	
	$('#tipusCertificacio option[value="' + $('#tipusCert').val() + '"]').attr('selected', 'selected');	

	$('#centresList').on('change', function(){
		$('.proveidor').removeClass('hidden');
		$('#incidencies').html('');	
		$('#expedients').html('');	
		$('#procediments').html('');
		if ($(this).val() != '-1') {	
			if ($(this).val() == 'procediment') {	
				$('.proveidor').addClass('hidden');
				searchProcediments();
			} else {
				searchIncidencies($(this).val());
			}			
		} else {
		}
	});
	$('.selectpicker').selectpicker('refresh');	
});

function searchIncidencies(idCentre) {
	var optionDefault = '';	
	var html = '';
	$('#incidencies').append('<div class="loader"></div>');
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
        			$('#incidenciesList').append('<option value=' + data.referencia + '>' + data.referencia + '-' + data.descripcio + '</option>');
        		});     
        		$('#incidenciesList option[value="' + $('#idActuacio').val() + '"]').attr('selected', 'selected');
        		$('.selectpicker').selectpicker('refresh');   
        		$('#incidencies .loader').remove();
        		$('#incidenciesList').on('change', function(){
        			$('#expedients').html('');	
        			$('#incidencies').append('<div class="loader"></div>');
        			$('.expedients').addClass('hidden');
    				if ($(this).val() != "-1") {
    					searchExpedients($(this).val());    					
    				} else {
    					
    				}	
    				$('#incidencies .loader').remove();
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

function searchProcediments() {
	var optionDefault = '';	
	var html = '';
	$('#procediments').append('<div class="loader"></div>');
	$.ajax({
        type: "POST",
        url: "LlistatProcediments",
        dataType: "json",
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	html += '<div class="form-group procediments">';
            	html += '	<label class="col-xs-3 control-label">Procediment</label>';
            	html += ' 	<div class="col-xs-3">';   
            	html += '     	<select class="form-control selectpicker" name="procedimentsList" data-live-search="true" data-size="5" id="procedimentsList">';
            	html += '		</select>';
            	html += '	</div>';
            	html += '</div>';
            	$('#procediments').append(html);            	
        		$.each(data.llistatProcediments, function( key, data ) {
        			var demanda = data.objecteDemanda;
        			if (demanda == null) demanda = '';
        			$('#procedimentsList').append('<option value=' + data.referencia + '>' + data.numAutos + '-' + demanda + '</option>');
        		});     
        		$('#procedimentsList option[value="' + $('#idInforme').val() + '"]').attr('selected', 'selected');
        		$('.selectpicker').selectpicker('refresh');   
        		$('#procediments .loader').remove();        		
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
	$('#expedients').append('<div class="loader"></div>');
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
        			if (data.actuacio.refExt != '-1') refExt = ' EXP ' + data.actuacio.refExt + ' ';
        			$('#expedientsList').append('<option data-idactuacio="' + data.actuacio.referencia + '" data-idinf="' + data.idInf + '" data-objecte="' + data.propostaInformeSeleccionada.objecte + '" data-total="' + data.propostaInformeSeleccionada.plic + '" data-pagat="' + data.totalFacturat + '" value=' + data.idInf + '>' + refExt + '-' + data.propostaInformeSeleccionada.objecte + '</option>');
        		});     
        		$('#expedientsList option[value="' + $('#idInforme').val() + '"]').attr('selected', 'selected');
        		$('.selectpicker').selectpicker('refresh');      
        		$('#expedientsList').on('change', function () {
        			$('.infoExpedient').html('');
        			$('.infoExpedient').append('<h3>Resum expedient</h3>');
        			$('.infoExpedient').append('<span>' + $('#expedientsList option:selected').data('objecte') + '</span></br>');
        			$('.infoExpedient').append('<span>Total: ' + $('#expedientsList option:selected').data('total') + '€</span></br>');
        			$('.infoExpedient').append('<span>Pagat: ' + $('#expedientsList option:selected').data('pagat') + '€</span></br>');
        			$('.infoExpedient').append('<span><a href="actuacionsDetalls?ref=' + $('#expedientsList option:selected').data('idactuacio') + '&exp=' + $('#expedientsList option:selected').data('idinf') + '" target="_blank">Anar a l\'actuació</a></span>');
        		});
             }    
             $('#expedients .loader').remove();
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}