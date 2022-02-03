$(document).ready(function() {	
	$('.filerTable.informes').DataTable({
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
        columnDefs: [ {
            targets: [ 1 ],
            orderData: [ 1, 0 ]
        } ],
        "order": [[ 1, "asc" ]],
		"aoColumns": [
    		null,
    		null,    		
    		null,
    		null,
    		null,
    		null,
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
