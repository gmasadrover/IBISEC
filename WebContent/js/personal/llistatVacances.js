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
        "order": [[ 2, "asc" ]],
		"aoColumns": [
    		null,
    		{"iDataSort": 2},
    		{"bVisible": false},
    		null,
    		null,
    		{"iDataSort": 6},
    		{"bVisible": false},
    		{"iDataSort": 8},
    		{"bVisible": false}
		]
	});
	if ($('#estatSelected').val() != '') {		  		
  		$('#estatList option[value="' + $('#estatSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#estatList').size() > 0) {
		  $('#estatList').selectpicker('refresh');
	}
	if ($('#idUsuariSelected').val() != '') {		  		
	  		$('#usuarisList option[value="' + $('#idUsuariSelected').val() + '"]').attr('selected', 'selected');
		}
    if ($('#usuarisList').size() > 0) {
  	  $('#usuarisList').selectpicker('refresh');
    }
});	