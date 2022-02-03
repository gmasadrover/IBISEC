$(document).ready(function() {
	initMap();
});	
var map;
function initMap() {
	var IllesBalears = {lat: 39.3634719, lng: 2.7843229};
	map = new google.maps.Map(document.getElementById('map'), {
		center: IllesBalears,
		zoom: 9
	});
	var centre = {lat: 0, lng: 0};
	var marker = new google.maps.Marker({
		position: centre,
	    map: map
	});
	var infowindow = new google.maps.InfoWindow();
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
	});
	
}

function loadInfoCentre(info){
	var text = "";
	$.ajax({
        type: "POST",
        url: "LlistatActuacions",
        async: false,
        dataType: "json",
        data: {"idCentre": info.data('idcentre'), "filterWithOutDate" : $('#filterWithOutDate').val(), "dataPeticioIni": $('#dataInici').val(), "dataPeticioFi": $('#dataFi').val(), "filterWithOutDateExec": $('#filterWithOutDateExec').val(), "dataExecucioIni": $('#dataIniciExec').val(), "dataExecucioFi": $('#dataFiExec').val(), "estat":$('#estatList').val(), "tipus":$('#tipusList').val()},
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	 text = '<h4>' + info.val() + '</h4>';            	
            	 $.each(data.llistatActuacions, function( key, actuacio ) {
            		 text += '<a href="actuacionsDetalls?ref=' + actuacio.referencia + '">EXP: ' + actuacio.informePrevi.expcontratacio + ' ' + actuacio.referencia + ' ' + actuacio.descripcio + '</a></br></br>';
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