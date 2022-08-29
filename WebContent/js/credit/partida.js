$(document).ready(function() {
	$('#tipusPartidaList option[value="' + $('#tipusSelected').val() + '"]').attr('selected', 'selected');	
	$('.selectpicker').selectpicker('refresh');		
	$('#import').on('keyup', function(){
		var pbase = $('#import').val().replace(',','.');
		if ($.isNumeric(pbase)) {			
			
		} else {
			$('#import').val('');
		}
	});
	$('.filerTable.assignacionsTable').DataTable({
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
		"order": [[ 5, "desc" ]],
		"aoColumns": [			
    		null,   		
    		null,
    		null,
    		null,
    		{"iDataSort": 5},
    		{"bVisible": false},
    		{"iDataSort": 7},
    		{"bVisible": false},
    		{"iDataSort": 9},
    		{"bVisible": false},
    		{"iDataSort": 11},
    		{"bVisible": false},
    		{"iDataSort": 13},
    		{"bVisible": false}
		]			
	});
	$('.filerTable.subpartides').DataTable({
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
    		null,    		
    		null,
    		null,
    		null,
    		null,
    		null    		
		]			
	});
});	