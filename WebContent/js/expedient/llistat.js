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
			{"iDataSort": 1},
    		{"bVisible": false},
    		null,
    		{"bVisible": false},
    		{"bVisible": false},
    		null,    		
    		{"iDataSort": 7},
    		{"bVisible": false},
    		{"bVisible": false},
    		null,
    		{"bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 13},
    		{"bVisible": false},
    		{"bVisible": false},
    		null,
    		null,    	
    		{"bVisible": false},
    		{"iDataSort": 19},
    		{"bVisible": false},
    		null,
    		{"bVisible": false, "iDataSort": 22},
    		{"bVisible": false},
    		{"bVisible": false, "iDataSort": 24},
    		{"bVisible": false},
    		{"bVisible": false, "iDataSort": 26},
    		{"bVisible": false},
    		{"bVisible": false, "iDataSort": 28},
    		{"bVisible": false},
    		null,
    		null
		]
	});
});	