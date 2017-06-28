$(document).ready(function() {
	if ($('#tipoContracteSelected').val() != '') {		  		
  		$('#tipoContracteList option[value="' + $('#tipoContracteSelected').val() + '"]').attr('selected', 'selected');
	}
	if ($('#tipoPDSelected').val() != '') {		  		
  		$('#tipoPDList option[value="' + $('#tipoPDSelected').val() + '"]').attr('selected', 'selected');
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
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		null,    		
    		{"iDataSort": 3},
    		{"bVisible": false},
    		null,
    		{"bVisible": false},
    		null,
    		{"bVisible": false},
    		null,
    		null,    		
    		{"bVisible": false},
    		{"iDataSort": 10},
    		null,
    		null,
    		{"iDataSort": 15, "bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 18, "bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 20, "bVisible": false},
    		{"bVisible": false}
		]
	});	
});	