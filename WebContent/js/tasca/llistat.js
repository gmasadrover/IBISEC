$(document).ready(function() {
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
	$('.filerTable.seguiment').DataTable({
		"order": [[ 3, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		{"iDataSort": 4},
    		{"bVisible": false},    		
    		null
		]
	});
	$('.filerTable.actuacioSeguiment').DataTable({
		"aoColumns": [
    		null,
    		null,
    		null,    		
    		null
		]
	});
	if ($('#usuarisSeleccionats').val() != undefined && $('#usuarisSeleccionats').val() != '') {	
		var usuaris = $('#usuarisSeleccionats').val().split('#');
		$.each(usuaris, function( key, usuari) {
	  		$('#usuarisList option[value="' + usuari + '"]').attr('selected', 'selected');	  		
  		});
		if ($('.selectpicker').size() > 0) {
  			$('.selectpicker').selectpicker('refresh');
  		}
	}	
});	