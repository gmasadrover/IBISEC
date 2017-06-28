$(document).ready(function() {
	$('.filerTable.withIncidencies').DataTable({
		"order": [[ 1, "desc" ]],
		"aoColumns": [
    		null,
    		{"iDataSort": 7},
    		null,
    		null,
    		null,
    		null, 
    		null, 
    		{"bVisible": false}
		]
	});
	$('.filerTable.withOutIncidencies').DataTable({
		"order": [[ 1, "desc" ]],
		"aoColumns": [
    		null,
    		{"iDataSort": 6},
    		null,
    		null,
    		null, 
    		null, 
    		{"bVisible": false}
		]
	});	
});	