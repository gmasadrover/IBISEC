$(document).ready(function() {
	loadTipus();
	$('#centresList').on('change', function(){
		$('.incidencies').addClass('hidden');	
		if ($(this).val() != null && ($(this).val().length == 2 || $(this).val().indexOf("-1") < 0)) {		
			$('#centresList option[value="-1"]').prop('selected', false);
			$.each($(this).val(), function( key, data ) {
				if (data != "-1") {
					if ($('.incidencies' + data).size() > 0) {
						$('.incidencies'  + data).removeClass('hidden');
					} else {
						searchIncidencies(data);
					}
				}				
			});	
			$('.selectpicker').selectpicker('refresh');
		} else {	
			if ($('.selectpicker').size() > 0) {
				$(".centresList").selectpicker('deselectAll');
				$('.centresList').selectpicker('val', "-1");				
	    		$('.centresList').selectpicker('refresh');
	    	}
		}		
	});
	$('#centresList option[value="-1"]').attr('selected', 'selected');
	$('.selectpicker').selectpicker('refresh');
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
             }                 
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}