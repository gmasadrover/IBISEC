$(document).ready(function() {
	$('.datepicker').datepicker({
	    language: "es"    	
	});
	loadCentres();
	
	$('.deleteFile').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DeleteDocument",
	        dataType: "json",
	        data: {"ruta": $(this).data('ruta')},
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
	
	$('.openFolder').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "OpenFolder",
	        dataType: "json",
	        data: {"idIncidencia": $(this).data('idincidencia')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {
	            //our country code was correct so we have some information to display
	        	//location.reload();      
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	        }  
	    });
	});
});

function loadCentres(){
	var centresAPI = "json/centres.json";
	$.getJSON( centresAPI, {
	})
    .done(function( data ) {
      $.each( data, function( key, data ) {
    	  $('#centresList').append('<option value=' + data.CODI + '>' + data.NOM + '</option>');
      });
      	if ($('#idCentreSelected').val() != '') {		  		
	  		$('#centresList option[value="' + $('#idCentreSelected').val() + '"]').attr('selected', 'selected');
  		}
      if ($('.selectpicker').size() > 0) {
    	  $('.selectpicker').selectpicker('refresh');
      }
    });
}