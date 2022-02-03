$(document).ready(function() {	
	if ($('#estatSelected').val() != '') {		  		
		$('#estatList option[value="' + $('#estatSelected').val() + '"]').attr('selected', 'selected');
		if ($('#estatSelected').val() == 'obert') {
			$('#procedimentObert').removeClass('hidden');
			$('#procedimentExecucio').addClass('hidden');
		} else if ($('#estatSelected').val() == 'execucio') {
			$('#procedimentObert').addClass('hidden');
			$('#procedimentExecucio').removeClass('hidden');
		} else {
			$('#procedimentExecucio').addClass('hidden');
			$('#procedimentObert').addClass('hidden');
		}
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
	$('#estatList').on('change', function(){
		if ($(this).val() == 'obert') {
			$('#procedimentObert').removeClass('hidden');
			$('#procedimentExecucio').addClass('hidden');
		} else if ($(this).val() == 'execucio') {
			$('#procedimentObert').addClass('hidden');
			$('#procedimentExecucio').removeClass('hidden');
		} else {
			$('#procedimentExecucio').addClass('hidden');
			$('#procedimentObert').addClass('hidden');
		}
	});
	$('.deleteTramitacio').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DeleteTramitacio",
	        dataType: "json",
	        data: {"idtramitacio": $(this).data('ref')},
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
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 9},
    		{"bVisible": false},
    		{"iDataSort": 10},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false}
		]
	});	
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
		"order": [[ 1, "desc" ]],
		"aoColumns": [
    		null,
    		{"iDataSort": 3},
    		{"bVisible": false},
    		{"iDataSort": 5},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 9},
    		{"bVisible": false},
    		{"iDataSort": 10},
    		{"bVisible": false},
    		null,
    		null,
    		{"bVisible": false},
    		null,
    		{"bVisible": false}
		]
	});	
});	