$(document).ready(function() {	
	if ($('#estatSelected').val() != '') {		  		
		$('#estatList option[value="' + $('#estatSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#estatList').size() > 0) {
		$('#estatList').selectpicker('refresh');
	}
	if ($('#anySelected').val() != '') {		  		
		$('#anyList option[value="' + $('#anySelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#anyList').size() > 0) {
		$('#anyList').selectpicker('refresh');
	}
	$('.filerTable.procediments').DataTable({
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
			{"iDataSort": 1},
    		{"bVisible": false},
    		null,  
    		null,
    		null,      		
    		null,    		
    		null,
    		null
		]
	});
	
	$('.filerTable.pendents').DataTable({
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
			null,  
    		null,
    		null, 
    		null
		]
	});
});	