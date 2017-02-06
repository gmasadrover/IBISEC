$(document).ready(function() {
	$('.filerTable').DataTable({
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		{"iDataSort": 4},
    		{"bVisible": false}
		]
	});
});	