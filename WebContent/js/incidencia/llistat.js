$(document).ready(function() {
	$('.filerTable.normal').DataTable({
		"aoColumns": [
    		null,
    		null, 
    		null, 
    		{"iDataSort": 4},
    		{"bVisible": false},
    		null
		]
	});
	$('.filerTable.withTancades').DataTable({
		"aoColumns": [
    		null,
    		null,  
    		null,  		
    		{"iDataSort": 4},
    		{"bVisible": false},
    		null,
    		{"iDataSort": 6},
    		{"bVisible": false}
		]
	});
});	