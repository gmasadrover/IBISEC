$(document).ready(function() {
	if ($('#idPartidaSelected').val() != '') {		  		
  		$('#partidesList option[value="' + $('#idPartidaSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('.selectpicker').size() > 0) {
		$('.selectpicker').selectpicker('refresh');
	}
	$('.filerTable').DataTable({
		"order": [[ 0, "desc" ]],
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
});	