$(document).ready(function() {
	$('.filerTable.factures').DataTable({
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
    		{"bVisible": false},  		
    		{"iDataSort": 3, "bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 5},
    		{"bVisible": false},
    		{"iDataSort": 7, "bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 9},
    		{"bVisible": false},
    		{"iDataSort": 11},
    		{"bVisible": false},
    		null,
    		null,
    		null,
    		null,
    		null,
    		null
		]
	});
	
	$('.deleteRelacioPersona').on('click', function(){
		var table = $('.filerTable').DataTable();
		$(this).parents('tr').addClass('selected');
		if (table.row('.selected').data() != undefined && $('#llistatOfertes input[value="' + table.row('.selected').data()[2] + "#" + table.row('.selected').data()[4] + '"]').size() > 0) {
			$('#llistatOfertes input[value="' + table.row('.selected').data()[2] + "#" + table.row('.selected').data()[4] + '"]').remove();
			if ($('#ofertaSeleccionadaNIF').val() == table.row('.selected').data()[2]) {
				$('#ofertaSeleccionada').text('');  
        		$('#ofertaSeleccionadaNIF').val('');  
			}
		}
		table.row('.selected').remove().draw( false );
		$.ajax({
	        type: "POST",
	        url: "DoDeleteOferta",
	        dataType: "json",
	        data: {"idOferta": $(this).data('idoferta')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {
	            //our country code was correct so we have some information to display
	        	location.reload();      
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	        }  
	    });
	});
});
