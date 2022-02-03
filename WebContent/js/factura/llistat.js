$(document).ready(function() {
	if ($('#estatFacturaSelected').val() != '') {		  		
  		$('#estatFacturaList option[value="' + $('#estatFacturaSelected').val() + '"]').attr('selected', 'selected');
	}	
	if ($('.selectpicker').size() > 0) {
		$('.selectpicker').selectpicker('refresh');
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
		"order": [[ 2, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		{"iDataSort": 4},
    		{"bVisible": false},
    		{"iDataSort": 6, "bVisible": false},
    		{"bVisible": false},
    		null,
    		null,
    		null,
    		{"bVisible": false},
    		null,
    		null,
    		null,    		
    		{"bVisible": false},
    		{"iDataSort": 14},
    		{"bVisible": false},
    		{"iDataSort": 16},
    		null,
    		{"bVisible": false},
    		null,
    		{"iDataSort": 21, "bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 24, "bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 26, "bVisible": false},
    		{"bVisible": false},
    		null
		]
	});	
});	