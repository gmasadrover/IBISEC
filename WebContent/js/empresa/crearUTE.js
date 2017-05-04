$(document).ready(function() {
	$('.filerTable').DataTable({
		"aoColumns": [
    		null,
    		{"bVisible": false},
    		{"bVisible": false},
    		null
		]
	});
	$('#afegir').on('click', function(){
		var empresa = "<input name='empreses' value='" + $('#llistaEmpreses').val() + "'>";		
		$('#llistatEmpreses').append(empresa);
    	var table = $('.filerTable').DataTable();        	
    	table.row.add( [
    		 "<a href='editEmpresa?cif=" + $('#llistaEmpreses').val() + "'>" + $("#llistaEmpreses option[value='" + $('#llistaEmpreses').val() + "']").text() + ' (' + $('#llistaEmpreses').val() + ")</a>",
    		 $("#llistaEmpreses option[value='" + $('#llistaEmpreses').val() + "']").text() + ' (' + $('#llistaEmpreses').val() + ")",
    		 $('#llistaEmpreses').val(),    		
    		 "<input class='btn btn-danger btn-sm eliminarSeleccionada margin_left10' type='button' value='Eliminar'>"
        ] ).draw( false );    	
		$('.eliminarSeleccionada').on('click', function(){	
			var table = $('.filerTable').DataTable();
			$(this).parents('tr').addClass('selected');
			if (table.row('.selected').data() != undefined && $('#llistatEmpreses input[value="' + table.row('.selected').data()[2] + '"]').size() > 0) {
				$('#llistatEmpreses input[value="' + table.row('.selected').data()[2]  + '"]').remove();				
			}
			table.row('.selected').remove().draw( false );
    	});
	});
});