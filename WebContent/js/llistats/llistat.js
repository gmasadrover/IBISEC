$(document).ready(function() {
	if ($('#tipusSelected').val() != '') {		  		
  		$('#tipusList option[value="' + $('#tipusSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#tipusList').size() > 0) {
		  $('#tipusList').selectpicker('refresh');
	}
	if ($('#estatSelected').val() != '') {		  		
			$('#estatList option[value="' + $('#estatSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#estatList').size() > 0) {
		$('#estatList').selectpicker('refresh');
	}
	initMap();
});	
var map;
function initMap() {
	var IllesBalears = {lat: 39.3634719, lng: 2.7843229};
	map = new google.maps.Map(document.getElementById('map'), {
		center: IllesBalears,
		zoom: 8
	});
	var centre = {lat: 0, lng: 0};
	var marker = new google.maps.Marker({
		position: centre,
	    map: map
	});
	var infowindow = new google.maps.InfoWindow();
	var markers = []; 
	$.each($('.informacioCentres input'), function( key, info ) {
		marker = new google.maps.Marker({
			position: new google.maps.LatLng($(info).data('lat'), $(info).data('lng')),
	        map: map
	 	});

	  	google.maps.event.addListener(marker, 'click', (function(marker, info) {
	  		return function() { 
	        	infowindow.setContent(loadInfoCentre(info));
	        	infowindow.open(map, marker);
	        	      	
	        }
	  	})(marker, $(info)));
	  	
	  	markers.push(marker);
	});
	
	var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
}

function loadInfoCentre(info){
	var text = "";
	$.ajax({
        type: "POST",
        url: "LlistatInformes",
        async: false,
        dataType: "json",
        data: {"idCentre": info.data('idcentre'), "filterWithOutDate" : $('#filterWithOutDate').is(':checked'), "dataPeticioIni": $('#dataInici').val(), "dataPeticioFi": $('#dataFi').val(), "filterWithOutDateExec": $('#filterWithOutDateExec').is(':checked'), "dataExecucioIni": $('#dataIniciExec').val(), "dataExecucioFi": $('#dataFiExec').val(), "estat":$('#estatList').val(), "tipus":$('#tipusList').val()},
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	 text = '<h4>' + info.val() + '</h4>';            	
            	 $.each(data.llistatInformes, function( key, informe) {
            		 text += '<a href="actuacionsDetalls?ref=' + informe.actuacio.referencia + '&exp=' + informe.idInf + '">EXP: ' + informe.expcontratacio.expContratacio + ' ' + informe.actuacio.referencia + ' ' + informe.actuacio.descripcio + ': ' + informe.propostaInformeSeleccionada.objecte + '</a></br></br>';
            	 });
             }             
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
	return text;
}