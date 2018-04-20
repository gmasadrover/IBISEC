$(document).ready(function() {
	//prueba_notificacion();
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
		"order": [[ 10, "desc" ]],
		"aoColumns": [
			null,
    		null,
    		null,
    		null,
    		{"bVisible": false},
    		null,
    		{"bVisible": false},
    		{"iDataSort": 8},
    		{"bVisible": false},
    		{"iDataSort": 10},
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
        "order": [[ 0, "desc" ]],
		"aoColumns": [
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
	$('.filerTable.tasquesResum').DataTable({
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
		"aoColumns": [
    		null,
    		null,    		
    		null,
    		null
		]
	});
});	
$('.filterWithClosed').on('change', function() {
	$.ajax({
        type: "POST",
        url: "TasquesActuacio",
        dataType: "json",
        data: {"idActuacio":$(this).data('idactuacio'), "idInforme":$(this).data('idinforme'), "withCancelades":$(this).is(':checked')},
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	 var table = $('#tasquesResum' + data.idInforme).DataTable();
            	 table.clear();
            	 $.each(data.llistatTasques, function( key, data ) {
            		 var nom = data.usuari.name + ' ' + data.usuari.llinatges;
            		 if (data.usuari.alias != undefined && data.usuari.alias != '') nom = data.usuari.alias;
            		 var dataCreacio = new Date(data.dataCreacio);
            		 var dataModificacio = new Date(data.darreraModificacio);
            		 var rowNode = table.row.add([					          	
						'<a href="tasca?id=' + data.idTasca + '" class="loadingButton"  data-msg="obrint tasca...">' + data.idTasca + ' - ' + data.descripcio + '</a>',
						nom,
						dataCreacio.getDate() + '/' + dataCreacio.getMonth() + '/' + dataCreacio.getFullYear() + ' ' + dataCreacio.getHours() + ':' + dataCreacio.getMinutes() ,						
						dataModificacio.getDate() + '/' + dataModificacio.getMonth() + '/' + dataModificacio.getFullYear() + ' ' + dataModificacio.getHours() + ':' + dataModificacio.getMinutes() ]).draw().node();
            		 if (data.activa) {
            			 $(rowNode).addClass('success');
            		 } else {
            			 $(rowNode).addClass('danger');
            		 }
            	 });    
             }             
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
});