$(document).ready(function() {	
	if ($('#estatSelected').val() != '') {		  		
		$('#estatList option[value="' + $('#estatSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#estatList').size() > 0) {
		$('#estatList').selectpicker('refresh');
	}
	
	if ($('#tipusPrev').val() != '') {		  		
		$('#llistatTipus option[value="' + $('#tipusPrev').val() + '"]').attr('selected', 'selected');
	}
	if ($('#llistatTipus').size() > 0) {
		$('#llistatTipus').selectpicker('refresh');
	}
	
	$('.filerTable.tasques').DataTable({
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
		"order": [[ 9, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		{"bVisible": false},
    		null,
    		{"bVisible": false},
    		{"iDataSort": 7},
    		{"bVisible": false},
    		{"iDataSort": 9},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false}
		]
	});	
});	