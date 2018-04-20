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
		"order": [[ 1, "desc" ]],
		"aoColumns": [
			null,
    		null,
    		{"iDataSort": 3},
    		{"bVisible": false},
    		null,
    		null,
    		null,
    		{"iDataSort": 8},
    		{"bVisible": false},
    		{"iDataSort": 10},
    		{"bVisible": false},
    		{"iDataSort": 12},
    		{"bVisible": false}
		]			
	});
});	