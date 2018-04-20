$(document).ready(function() {
	
	// Variable to store your files
	var files;

	// Add events
	$('.fileEscritura').on('change', function (event){
		// Grab the files and set them to our variable
		files = event.target.files;
	});
	$('.fileAdministrador').on('change', function (event){
		// Grab the files and set them to our variable
		files = event.target.files;
	});
	
	$('#uploadEscritura').on('click', function(event){
		event.stopPropagation(); // Stop stuff happening
	    event.preventDefault(); // Totally stop stuff happening
	    // START A LOADING SPINNER HERE

	    // Create a formdata object and add the files
	    var data = new FormData();
	    if (files != null) {
		    $.each(files, function(key, value)
		    {
		    	data.append('fileEscritura', value);
		    });
	    }
	    data.append('cif', $(this).data('cif'));
		$.ajax({
	        type: "POST",
	        url: "uploadEscritura",
	        data: data,
	        cache: false,
	        dataType: "json",
	        processData: false, // Don't process the files
	        contentType: false, // Set content type to false as jQuery will tell the server its a query string request
	        
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
	
	$('#administradorsTable').dataTable( {
		 
		} );
	$('#classificacioTable').dataTable( {
		 
	} );
	$('#empresesUTETable').dataTable( {
		 
	} );
	$('#ofertesTable').dataTable( {
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
		"order": [[ 6, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		null,
    		null,
    		{"iDataSort": 6},
    		{"bVisible": false},
    		null
		]
	} );
	$('#ratioAP').on('keyup', function(){
		var ratio = $('#ratioAP').val().replace(',','.');
		if ($.isNumeric(ratio)) {			
			$('#ratioAP').val(ratio);
		} else {
			$('#ratioAP').val('');
		}
	});
	$("#dataExerciciEconomic").datepicker( {
	    format: " yyyy", // Notice the Extra space at the beginning
	    viewMode: "years", 
	    minViewMode: "years"
	});	
	$('.eliminarClassificacioSeleccionada').on('click', function(){
		var table = $('#classificacioTable').DataTable();
		$(this).parents('tr').addClass('selected');
		var option = table.row('.selected').data()[0] + "#" + table.row('.selected').data()[1]+ "#" + table.row('.selected').data()[2] + ";";
		if (table.row('.selected').data() != undefined && $('#llistatClassificacio').val().indexOf(option) >= 0) {
			$('#llistatClassificacio').val($('#llistatClassificacio').val().replace(option, ''));
		}
		table.row('.selected').remove().draw( false );
	});
	$('#afegirAdmin').on('click', function(){
		
		event.stopPropagation(); // Stop stuff happening
	    event.preventDefault(); // Totally stop stuff happening
	    // START A LOADING SPINNER HERE
	    if (! $.isNumeric($('#numProtocol').val())) {			
			$('#numProtocol').val('');
		}
	    
	    // Create a formdata object and add the files
	    var data = new FormData();
	    if (files != null) {
		    $.each(files, function(key, value)
		    {
		    	data.append('fileAdministrador', value);
		    });
	    }
	    data.append('nomAdmin', $('#nomAdmin').val());
	    data.append('dniAdmin',  $('#dniAdmin').val());
	    data.append('tipusAdmin',  $('#tipusAdmin').val());
	    data.append('validAdmin',  $('#validAdmin').val());
	    data.append('nomNotari',  $('#nomNotari').val());
	    data.append('numProtocol',  $('#numProtocol').val());
	    data.append('dataAlta',  $('#dataAlta').val());
	    data.append('dataValidacio',  $('#dataValidacio').val());
	    data.append('organValidador',  $('#organValidador').val());
	    data.append('cif', $(this).data('cif'));
		$.ajax({
	        type: "POST",
	        url: "addAdministrador",
	        data: data,
	        cache: false,
	        dataType: "json",
	        processData: false, // Don't process the files
	        contentType: false, // Set content type to false as jQuery will tell the server its a query string request
	        
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
	$('#afegirClassificacio').on('click', function(){
		var clas = $('#grupList').val() + "#" + $('#subGrupList').val() + "#" + $('#categoriaList').val() + ";";
		$('#llistatClassificacio').val($('#llistatClassificacio').val() + clas);
    	var table = $('#classificacioTable').DataTable();        	
    	table.row.add( [
    		 $('#grupList').val(),
    		 $('#subGrupList').val(),
    		 $('#categoriaList').val(),
    		 "<input class='btn btn-danger btn-sm eliminarClassificacioSeleccionada margin_left10' type='button' value='Eliminar'>"
        ] ).draw( false );
    	$('.eliminarClassificacioSeleccionada').on('click', function(){	
			var table = $('#classificacioTable').DataTable();
			$(this).parents('tr').addClass('selected');
			var option = table.row('.selected').data()[0] + "#" + table.row('.selected').data()[1]+ "#" + table.row('.selected').data()[2] + ";";
			if (table.row('.selected').data() != undefined && $('#llistatClassificacio').val().indexOf(option) >= 0) {
				$('#llistatClassificacio').val($('#llistatClassificacio').val().replace(option, ''));
			}
			table.row('.selected').remove().draw( false );
    	});
	});
	$('#grupList').on('change', function(){
		var grup = $(this).val();
		$('#subGrupList').html('');
		$('#subGrupList').append('<option value="-">-</option>');
		$('#subGrupList').append('<option value="1">1</option>');
		$('#subGrupList').append('<option value="2">2</option>');
		switch (grup) {
	        case "A":
	        	$('#subGrupList').append('<option value="3">3</option>');
	        	$('#subGrupList').append('<option value="4">4</option>');
	        	$('#subGrupList').append('<option value="5">5</option>');
	            break;
	        case "B":
	        	$('#subGrupList').append('<option value="3">3</option>');
	        	$('#subGrupList').append('<option value="4">4</option>');
	            break;
	        case "C":
	        	$('#subGrupList').append('<option value="3">3</option>');
        		$('#subGrupList').append('<option value="4">4</option>');
        		$('#subGrupList').append('<option value="5">5</option>');
	        	$('#subGrupList').append('<option value="6">6</option>');
	        	$('#subGrupList').append('<option value="7">7</option>');
	        	$('#subGrupList').append('<option value="8">8</option>');
	        	$('#subGrupList').append('<option value="9">9</option>');
	            break;
	        case "D":
	        	$('#subGrupList').append('<option value="3">3</option>');
	        	$('#subGrupList').append('<option value="4">4</option>');
	        	$('#subGrupList').append('<option value="5">5</option>');
	            break;
	        case "E":
	        	$('#subGrupList').append('<option value="3">3</option>');
	        	$('#subGrupList').append('<option value="4">4</option>');
	        	$('#subGrupList').append('<option value="5">5</option>');
	        	$('#subGrupList').append('<option value="6">6</option>');
	        	$('#subGrupList').append('<option value="7">7</option>');
	            break;
	        case "F":
	        	$('#subGrupList').append('<option value="3">3</option>');
	        	$('#subGrupList').append('<option value="4">4</option>');
	        	$('#subGrupList').append('<option value="5">5</option>');
	        	$('#subGrupList').append('<option value="6">6</option>');
	        	$('#subGrupList').append('<option value="7">7</option>');
	        	$('#subGrupList').append('<option value="8">8</option>');
	            break;
	        case "G":
	        	$('#subGrupList').append('<option value="3">3</option>');
	        	$('#subGrupList').append('<option value="4">4</option>');
	        	$('#subGrupList').append('<option value="5">5</option>');
	        	$('#subGrupList').append('<option value="6">6</option>');
	            break;
	        case "H":
	            break;
	        case "I":
	        	$('#subGrupList').append('<option value="3">3</option>');
        		$('#subGrupList').append('<option value="4">4</option>');
        		$('#subGrupList').append('<option value="5">5</option>');
	        	$('#subGrupList').append('<option value="6">6</option>');
	        	$('#subGrupList').append('<option value="7">7</option>');
	        	$('#subGrupList').append('<option value="8">8</option>');
	        	$('#subGrupList').append('<option value="9">9</option>');
	            break;
	        case "J":
	        	$('#subGrupList').append('<option value="3">3</option>');
	        	$('#subGrupList').append('<option value="4">4</option>');
	        	$('#subGrupList').append('<option value="5">5</option>');
	            break;
	        case "K":
	        	$('#subGrupList').append('<option value="3">3</option>');
	        	$('#subGrupList').append('<option value="4">4</option>');
	        	$('#subGrupList').append('<option value="5">5</option>');
	        	$('#subGrupList').append('<option value="6">6</option>');
	        	$('#subGrupList').append('<option value="7">7</option>');
	        	$('#subGrupList').append('<option value="8">8</option>');
	        	$('#subGrupList').append('<option value="9">9</option>');
	            break;
	        default:
	        	$('#subGrupList').append('<option value="3">3</option>');
        		$('#subGrupList').append('<option value="4">4</option>');
        		$('#subGrupList').append('<option value="5">5</option>');
	        	$('#subGrupList').append('<option value="6">6</option>');
	        	$('#subGrupList').append('<option value="7">7</option>');
	        	$('#subGrupList').append('<option value="8">8</option>');
	        	$('#subGrupList').append('<option value="9">9</option>');
	    }
	});
});
