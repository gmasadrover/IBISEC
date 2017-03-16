$(document).ready(function() {
	$('#incidenciesTable').DataTable({
		"order": [[ 0, "desc" ]],
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
		"order": [[ 0, "desc" ]],
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