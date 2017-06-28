$(document).ready(function() {
	$('.filerTable').DataTable({
		dom: 'Bfrtip',
        buttons: [ {
	            extend: 'excelHtml5',
	            customize: function( xlsx ) {
	                var sheet = xlsx.xl.worksheets['sheet1.xml']; 
	                $('row c[r^="C"]', sheet).attr( 's', '2' );
	            },
	            exportOptions: {
                    columns: ':visible'
                }
        	},
	        {
	            extend: 'collection',
	            text: 'Editar columnes',
	            buttons: [ 'columnsVisibility' ],
	            visibility: true
	        }
        ],
        "order": [[ 4, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,    		
    		{"iDataSort": 4},
    		{"bVisible": false},
    		{"iDataSort": 6},
    		{"bVisible": false},
    		{"iDataSort": 8},
    		{"bVisible": false},
    		{"iDataSort": 10},
    		{"bVisible": false},
    		{"iDataSort": 12},
    		{"bVisible": false},
    		{"iDataSort": 14},
    		{"bVisible": false}
		]
	});
});	