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
    		{"iDataSort": 3},
    		{"bVisible": false},
    		null,
    		null,
    		null,
    		{"bVisible": false},
    		null,
    		null,    		
    		{"bVisible": false},
    		{"iDataSort": 10},
    		null,
    		{"bVisible": false},
    		null,
    		{"iDataSort": 15, "bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 18, "bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 20, "bVisible": false},
    		{"bVisible": false},
    		null
		]
	});	
});	