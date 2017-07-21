$(document).ready(function() {
	$('#incidenciesTable').DataTable({
		"order": [[ 3, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		{"iDataSort": 4},
    		{"bVisible": false},
    		null,
    		{"iDataSort": 7},
    		{"bVisible": false}
		]
	});
	$('#actuacionsTable').DataTable({
		"order": [[ 3, "desc" ]],
		"aoColumns": [
			null,
    		null,
    		null,
    		{"iDataSort": 4},
    		{"bVisible": false},
    		null,
    		{"iDataSort": 7},
    		{"bVisible": false}
		]
	});
});	