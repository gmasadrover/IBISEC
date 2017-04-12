$(document).ready(function() {
	$('#import').on('keyup', function(){
		var valorFactura = $('#import').val().replace(',','.');
		if ($.isNumeric(valorFactura)) {			
			
		} else {
			$('#import').val('');
		}
	});		
	$('#llistaEmpreses option[value="' + $('#nifProveidor').val() + '"]').attr('selected', 'selected');	
	$('.selectpicker').selectpicker('refresh');	
});