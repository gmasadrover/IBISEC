$(document).ready(function() {
	$('.filerTable.tasques').DataTable({
		"order": [[ 5, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		{"iDataSort": 4},
    		{"bVisible": false},
    		{"iDataSort": 6},
    		{"bVisible": false},
    		null
		]
	});
	$('.filerTable.seguiment').DataTable({
		"order": [[ 3, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		{"iDataSort": 4},
    		{"bVisible": false},    		
    		null
		]
	});
	$('.filerTable.actuacioSeguiment').DataTable({
		"aoColumns": [
    		null,
    		null,
    		null,    		
    		null
		]
	});
	if ($('#usuarisSeleccionats').val() != '') {	
		var usuaris = $('#usuarisSeleccionats').val().split('#');
		$.each(usuaris, function( key, usuari) {
	  		$('#usuarisList option[value="' + usuari + '"]').attr('selected', 'selected');	  		
  		});
		if ($('.selectpicker').size() > 0) {
  			$('.selectpicker').selectpicker('refresh');
  		}
	}	 
});	