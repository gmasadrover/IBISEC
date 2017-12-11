$(document).ready(function() {
	$('.filerTable.factures').DataTable({
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
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		{"bVisible": false},  		
    		{"iDataSort": 3, "bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 5},
    		{"bVisible": false},
    		{"iDataSort": 7, "bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 9},
    		{"bVisible": false},
    		{"iDataSort": 11},
    		{"bVisible": false},
    		null,
    		null,
    		null,
    		null,
    		null,
    		null
		]
	});
});