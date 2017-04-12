$(document).ready(function() {
	$('.filerTable').DataTable({
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