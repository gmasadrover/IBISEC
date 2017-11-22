$(document).ready(function() {
	if ($('#tipusSelected').val() != '') {		  		
  		$('#tipusList option[value="' + $('#tipusSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#tipusList').size() > 0) {
		  $('#tipusList').selectpicker('refresh');
	}
	if ($('#contracteSelected').val() != '') {		  		
  		$('#contracteList option[value="' + $('#contracteSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#contracteList').size() > 0) {
		  $('#contracteList').selectpicker('refresh');
	}
	if ($('#estatSelected').val() != '') {		  		
			$('#estatList option[value="' + $('#estatSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#estatList').size() > 0) {
		$('#estatList').selectpicker('refresh');
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
    		null,    		
    		null,    		
    		{"bVisible": false},
    		{"iDataSort": 4},
    		{"bVisible": false},
    		{"iDataSort": 6},
    		{"bVisible": false},
    		{"iDataSort": 8}
		]
	});
});	