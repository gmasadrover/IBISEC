$(document).ready(function() {	
	$('#llistaEmpreses option[value="' + $('#empresaPrev').val() + '"]').attr('selected', 'selected');	
	$('.selectpicker').selectpicker('refresh');		
});
