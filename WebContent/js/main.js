$(document).ready(function() {
	$('.datepicker').datepicker({
	    language: "es"    	
	});
	loadCentres();	
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