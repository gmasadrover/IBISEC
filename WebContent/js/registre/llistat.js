$(document).ready(function() {
	$('.filerTable.withIncidencies').DataTable({
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