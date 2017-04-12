$(document).ready(function() {
	loadTipus();
	searchIncidencies('07000000');
	$('#centresList').on('change', function(){
		if ($(this).val() != -1) {
			$('.incidencies').removeClass('hidden');
			searchIncidencies($(this).val().split("_")[0]);
		} else {
			$('.incidencies').addClass('hidden');
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
	if ($('#tipusRegistre').val() == 'E') {
		$('#incidenciesList').html("<option value='-1'>Nova incidència</option>");
	} else {
		$('#incidenciesList').html("<option value='-1'>Sense incidència</option>");
	}
	$.ajax({
        type: "POST",
        url: "LlistatIncidencies",
        dataType: "json",
        data:{"idCentre":idCentre},
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	 $.each(data.llistatIncidencies, function( key, data ) {
            		 $('#incidenciesList').append('<option value=' + data.idIncidencia + '>' + data.idIncidencia + '-' + data.descripcio + '</option>');
            	 });          
             }         
             $('.selectpicker').selectpicker('refresh');
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}