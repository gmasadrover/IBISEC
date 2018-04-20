$(document).ready(function() {	
	$('#llistaEmpreses option[value="' + $('#empresaPrev').val() + '"]').attr('selected', 'selected');	
	$('.selectpicker').selectpicker('refresh');		
	$('.filerTable').DataTable({		
		"aoColumns": [
			null,
			{"iDataSort": 2},
    		{"bVisible": false},
    		null,
    		null,
    		null
		]
	});
});
