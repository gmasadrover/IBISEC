$(document).ready(function() {
	$('#administradorsTable').dataTable( {
		 
		} );
	$('#classificacioTable').dataTable( {
		 
	} );
	$('.eliminarSeleccionada').on('click', function(){	
		var table = $('#administradorsTable').DataTable();
		$(this).parents('tr').addClass('selected');
		var option =  table.row('.selected').data()[0] + "#" + table.row('.selected').data()[1] + "#" + table.row('.selected').data()[2] + "#" + table.row('.selected').data()[3] + "#" + table.row('.selected').data()[4] + "#" + table.row('.selected').data()[5] + "#" + table.row('.selected').data()[6] + "#" + table.row('.selected').data()[7];
		if (table.row('.selected').data() != undefined && $('#llistatAdministradors').val().indexOf(option) >= 0) {
			$('#llistatAdministradors').val($('#llistatAdministradors').val().replace(option + ";", option + '#eliminar;'));				
		}
		table.row('.selected').remove().draw( false );
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
		var admin = $('#nomAdmin').val() + "#" + $('#dniAdmin').val() + "#" + $('#tipusAdmin').val() + "#" + $('#validAdmin').val() + "#" + $('#nomNotari').val() + "#" + $('#numProtocol').val() + "#" + $('#dataAlta').val() + "#" + $('#dataValidacio').val() + ";";
		$('#llistatAdministradors').val($('#llistatAdministradors').val() + admin);
    	var table = $('#administradorsTable').DataTable();        	
    	table.row.add( [
    		 $('#nomAdmin').val(),
    		 $('#dniAdmin').val(),
    		 $('#tipusAdmin').val(),
    		 $('#validAdmin').val(),
    		 $('#nomNotari').val(),
    		 $('#numProtocol').val(),
    		 $('#dataAlta').val(),
    		 $('#dataValidacio').val(),
    		 "<input class='btn btn-danger btn-sm eliminarSeleccionada margin_left10' type='button' value='Eliminar'>"
        ] ).draw( false );
    	$('.eliminarSeleccionada').on('click', function(){	
			var table = $('#administradorsTable').DataTable();
			$(this).parents('tr').addClass('selected');
			var option =  table.row('.selected').data()[0] + "#" + table.row('.selected').data()[1] + "#" + table.row('.selected').data()[2] + "#" + table.row('.selected').data()[3] + "#" + table.row('.selected').data()[4] + "#" + table.row('.selected').data()[5] + "#" + table.row('.selected').data()[6] + "#" + table.row('.selected').data()[7];
			if (table.row('.selected').data() != undefined && $('#llistatAdministradors').val().indexOf(option) >= 0) {
				$('#llistatAdministradors').val($('#llistatAdministradors').val().replace(option + ";",  option + '#eliminar;'));				
			}
			table.row('.selected').remove().draw( false );
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
