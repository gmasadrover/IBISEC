$(document).ready(function() {
	$('.filerTable').DataTable({
		"order": [[ 0, "desc" ]],
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