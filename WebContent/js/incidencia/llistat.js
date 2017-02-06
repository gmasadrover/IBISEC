$(document).ready(function() {
	$('.filerTable.normal').DataTable({
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		null,    		
    		{"iDataSort": 3},
    		{"bVisible": false},
    		null
		]
	});
	$('.filerTable.withTancades').DataTable({
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		null,    		
    		{"iDataSort": 3},
    		{"bVisible": false},
    		null,
    		{"iDataSort": 6},
    		{"bVisible": false}
		]
	});
});	