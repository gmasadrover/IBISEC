$(document).ready(function() {
	$('.filerTable.normal').DataTable({
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
	$('.filerTable.withTancades').DataTable({
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		{"iDataSort": 4},
    		{"bVisible": false},
    		null,
    		{"iDataSort": 7},
    		{"bVisible": false},
    		null,
    		{"iDataSort": 9},
    		{"bVisible": false}
		]
	});
});	