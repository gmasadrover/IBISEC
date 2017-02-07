$(document).ready(function() {
	$('.filerTable').DataTable({
		"order": [[ 4, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		null,
    		{"iDataSort": 5},
    		{"bVisible": false},
    		null
		]
	});
	if ($('#usuariSelected').val() != '') {		  		
  		$('#usuarisList option[value="' + $('#usuariSelected').val() + '"]').attr('selected', 'selected');
  		if ($('.selectpicker').size() > 0) {
  			$('.selectpicker').selectpicker('refresh');
  		}
	}	 
});	