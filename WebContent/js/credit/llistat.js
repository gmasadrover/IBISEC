$(document).ready(function() {
	if ($('#idPartidaSelected').val() != '') {		  		
  		$('#partidesList option[value="' + $('#idPartidaSelected').val() + '"]').attr('selected', 'selected');
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
		"order": [[ 1, "desc" ]],
		"aoColumns": [
    		null,
    		{"iDataSort": 2},
    		{"bVisible": false},
    		null,
    		null,
    		null,
    		null,
    		null,
    		{"iDataSort": 9},
    		{"bVisible": false},
    		{"iDataSort": 11},
    		{"bVisible": false},
    		null
		]
	});
});	