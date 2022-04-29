$(document).ready(function() {
	
	loadCentresAutoritzat();   
	loadCentresNoAutoritzat();   
	
	$('#import').on('keyup', function(){
		var valorFactura = $('#import').val().replace(',','.');
		if ($.isNumeric(valorFactura)) {			
			
		} else {
			$('#import').val('');
		}
	});		
	
	//Expedient original
	$('#centresList option[value="' + $('#idCentreActual').val() + '"]').attr('selected', 'selected');
	if ($('#idCentreActual').val() != '' && $('#idCentreActual').val() != '-1') {	
		searchIncidencies($('#idCentreActual').val());
			
	} 
	if ($('#idActuacio').val() != "-1") {
		searchExpedients($('#idActuacio').val());  
	}
		
	$('#centresList').on('change', function(){
		$('#incidencies').html('');	
		$('#expedients').html('');	
		if ($(this).val() != '-1') {
			searchIncidencies($(this).val());			
		}
	});
	
	//Expedient autoritzat
	$('#centresListAutoritzat option[value="' + $('#idCentreActualAutoritzat').val() + '"]').attr('selected', 'selected');
	if ($('#idCentreActualAutoritzat').val() != '' && $('#idCentreActualAutoritzat').val() != '-1') {	
		searchIncidenciesAutoritzat($('#idCentreActualAutoritzat').val());			
	} 
	if ($('#idActuacioAutoritzat').val() != "-1") {
		searchExpedientsAutoritzat($('#idActuacioAutoritzat').val());  
	}
		
	$('#centresListAutoritzat').on('change', function(){
		$('#incidenciesAutoritzat').html('');	
		$('#expedientsAutoritzat').html('');	
		if ($(this).val() != '-1') {
			searchIncidenciesAutoritzat($(this).val());			
		}
	});
	
	//Expedient No autoritzat
	$('#centresListNoAutoritzat option[value="' + $('#idCentreActualNoAutoritzat').val() + '"]').attr('selected', 'selected');
	if ($('#idCentreActualNoAutoritzat').val() != '' && $('#idCentreActualNoAutoritzat').val() != '-1') {	
		searchIncidenciesNoAutoritzat($('#idCentreActualNoAutoritzat').val());			
	} 
	if ($('#idActuacioNoAutoritzat').val() != "-1") {
		searchExpedientsNoAutoritzat($('#idActuacioNoAutoritzat').val());  
	}
		
	$('#centresListNoAutoritzat').on('change', function(){
		$('#incidenciesNoAutoritzat').html('');	
		$('#expedientsNoAutoritzat').html('');	
		if ($(this).val() != '-1') {
			searchIncidenciesNoAutoritzat($(this).val());			
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
             }    
             $('#expedients .loader').remove();
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}

function loadCentresAutoritzat(){
	$.ajax({
        type: "POST",
        url: "LlistatCentres",
        dataType: "json",
        async: false,
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	 $.each(data.llistatCentres, function( key, data ) {
            		 $('#centresListAutoritzat').append('<option value=' + data.idCentre + '>' + data.nom + ' (' + data.localitat + ')</option>');
            	 });    
            	 if ($('#idCentreSelected').val() != '') {		  		
         	  		$('#centresListAutoritzat option[value="' + $('#idCentreSelected').val() + '"]').attr('selected', 'selected');
           		}
               if ($('.selectpicker').size() > 0) {
             	  $('.selectpicker').selectpicker('refresh');
               }
             }             
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}

function searchIncidenciesAutoritzat(idCentre) {
	var optionDefault = '';	
	var html = '';
	$('#incidenciesAutoritzat').append('<div class="loader"></div>');
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
            	html += '     	<select class="form-control selectpicker" name="incidenciesListAutoritzat" data-live-search="true" data-size="5" id="incidenciesListAutoritzat">';
            	html += '			<option value="-1">Sense actuacio</option>';
            	html += '		</select>';
            	html += '	</div>';
            	html += '</div>';
            	$('#incidenciesAutoritzat').append(html);            	
        		$.each(data.llistatActuacions, function( key, data ) {
        			$('#incidenciesListAutoritzat').append('<option value=' + data.referencia + '>' + data.referencia + '-' + data.descripcio + '</option>');
        		});     
        		$('#incidenciesListAutoritzat option[value="' + $('#idActuacioAutoritzat').val() + '"]').attr('selected', 'selected');
        		$('.selectpicker').selectpicker('refresh');   
        		$('#incidenciesAutoritzat .loader').remove();
        		$('#incidenciesListAutoritzat').on('change', function(){
        			$('#expedientsAutoritzat').html('');	
        			$('#incidenciesAutoritzat').append('<div class="loader"></div>');
        			$('.expedientsAutoritzat').addClass('hidden');
    				if ($(this).val() != "-1") {
    					searchExpedientsAutoritzat($(this).val());    					
    				} else {
    					
    				}	
    				$('#incidenciesAutoritzat .loader').remove();
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

function searchExpedientsAutoritzat(idActuacio) {
	var optionDefault = '';	
	var html = '';
	$('#expedientsAutoritzat').append('<div class="loader"></div>');
	$.ajax({
        type: "POST",
        url: "LlistatExpedientsActuacio",
        dataType: "json",
        data:{"idActuacio":idActuacio},
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	html += '<div class="form-group expedientsAutoritzat">';
            	html += '	<label class="col-xs-3 control-label">Expedient</label>';
            	html += ' 	<div class="col-xs-3">';   
            	html += '     	<select class="form-control selectpicker" name="expedientsListAutoritzat" data-live-search="true" data-size="5" id="expedientsListAutoritzat">';
            	html += '			<option value="-1">Sense expedient</option>';
            	html += '		</select>';
            	html += '	</div>';
            	html += '</div>';
            	$('#expedientsAutoritzat').append(html);
        		$.each(data.llistatExpedients, function( key, data ) {
        			var refExt = '';
        			if (data.actuacio.refExt != '-1') refExt = ' EXP ' + data.actuacio.refExt + ' ';
        			$('#expedientsListAutoritzat').append('<option data-idactuacio="' + data.actuacio.referencia + '" data-idinf="' + data.idInf + '" data-objecte="' + data.propostaInformeSeleccionada.objecte + '" data-total="' + data.propostaInformeSeleccionada.plic + '" data-pagat="' + data.totalFacturat + '" value=' + data.idInf + '>' + refExt + '-' + data.propostaInformeSeleccionada.objecte + '</option>');
        		});     
        		$('#expedientsListAutoritzat option[value="' + $('#idInformeAutoritzat').val() + '"]').attr('selected', 'selected');
        		$('.selectpicker').selectpicker('refresh');
             }    
             $('#expedientsAutoritzat .loader').remove();
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}

function loadCentresNoAutoritzat(){
	$.ajax({
        type: "POST",
        url: "LlistatCentres",
        dataType: "json",
        async: false,
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	 $.each(data.llistatCentres, function( key, data ) {
            		 $('#centresListNoAutoritzat').append('<option value=' + data.idCentre + '>' + data.nom + ' (' + data.localitat + ')</option>');
            	 });    
            	 if ($('#idCentreSelected').val() != '') {		  		
         	  		$('#centresListNoAutoritzat option[value="' + $('#idCentreSelected').val() + '"]').attr('selected', 'selected');
           		}
               if ($('.selectpicker').size() > 0) {
             	  $('.selectpicker').selectpicker('refresh');
               }
             }             
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}

function searchIncidenciesNoAutoritzat(idCentre) {
	var optionDefault = '';	
	var html = '';
	$('#incidenciesNoAutoritzat').append('<div class="loader"></div>');
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
            	html += '     	<select class="form-control selectpicker" name="incidenciesListNoAutoritzat" data-live-search="true" data-size="5" id="incidenciesListNoAutoritzat">';
            	html += '			<option value="-1">Sense actuacio</option>';
            	html += '		</select>';
            	html += '	</div>';
            	html += '</div>';
            	$('#incidenciesNoAutoritzat').append(html);            	
        		$.each(data.llistatActuacions, function( key, data ) {
        			$('#incidenciesListNoAutoritzat').append('<option value=' + data.referencia + '>' + data.referencia + '-' + data.descripcio + '</option>');
        		});     
        		$('#incidenciesListNoAutoritzat option[value="' + $('#idActuacioNoAutoritzat').val() + '"]').attr('selected', 'selected');
        		$('.selectpicker').selectpicker('refresh');   
        		$('#incidenciesNoAutoritzat .loader').remove();
        		$('#incidenciesListNoAutoritzat').on('change', function(){
        			$('#expedientsNoAutoritzat').html('');	
        			$('#incidenciesNoAutoritzat').append('<div class="loader"></div>');
        			$('.expedientsNoAutoritzat').addClass('hidden');
    				if ($(this).val() != "-1") {
    					searchExpedientsNoAutoritzat($(this).val());    					
    				} else {
    					
    				}	
    				$('#incidenciesNoAutoritzat .loader').remove();
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

function searchExpedientsNoAutoritzat(idActuacio) {
	var optionDefault = '';	
	var html = '';
	$('#expedientsNoAutoritzat').append('<div class="loader"></div>');
	$.ajax({
        type: "POST",
        url: "LlistatExpedientsActuacio",
        dataType: "json",
        data:{"idActuacio":idActuacio},
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	html += '<div class="form-group expedientsNoAutoritzat">';
            	html += '	<label class="col-xs-3 control-label">Expedient</label>';
            	html += ' 	<div class="col-xs-3">';   
            	html += '     	<select class="form-control selectpicker" name="expedientsListNoAutoritzat" data-live-search="true" data-size="5" id="expedientsListNoAutoritzat">';
            	html += '			<option value="-1">Sense expedient</option>';
            	html += '		</select>';
            	html += '	</div>';
            	html += '</div>';
            	$('#expedientsNoAutoritzat').append(html);
        		$.each(data.llistatExpedients, function( key, data ) {
        			var refExt = '';
        			if (data.actuacio.refExt != '-1') refExt = ' EXP ' + data.actuacio.refExt + ' ';
        			$('#expedientsListNoAutoritzat').append('<option data-idactuacio="' + data.actuacio.referencia + '" data-idinf="' + data.idInf + '" data-objecte="' + data.propostaInformeSeleccionada.objecte + '" data-total="' + data.propostaInformeSeleccionada.plic + '" data-pagat="' + data.totalFacturat + '" value=' + data.idInf + '>' + refExt + '-' + data.propostaInformeSeleccionada.objecte + '</option>');
        		});     
        		$('#expedientsListNoAutoritzat option[value="' + $('#idInformeNoAutoritzat').val() + '"]').attr('selected', 'selected');
        		$('.selectpicker').selectpicker('refresh');
             }    
             $('#expedientsNoAutoritzat .loader').remove();
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}


