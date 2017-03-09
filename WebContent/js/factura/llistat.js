$(document).ready(function() {
	$('.filerTable').DataTable({
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		null,    		
    		{"iDataSort": 3},
    		{"bVisible": false},
    		{"iDataSort": 5},
    		{"bVisible": false},
    		null,
    		null,
    		null,
    		null
		]
	});	
});	