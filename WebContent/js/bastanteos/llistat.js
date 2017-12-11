$(document).ready(function() {	
	if ($('#anySelected').val() != '') {		  		
		$('#anyList option[value="' + $('#anySelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#anyList').size() > 0) {
		$('#anyList').selectpicker('refresh');
	}
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
        "order": [[ 0, "desc" ]],
		"aoColumns": [
			null,
			{"iDataSort": 2},
    		{"bVisible": false},
    		null,
    		null,  
    		null,
    		null,  
    		{"iDataSort": 8},
    		{"bVisible": false},
    		null,    		
    		null,
    		null,
    		null,
    		null
		]
	});
});	