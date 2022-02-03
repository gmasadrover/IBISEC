$(document).ready(function() {	
	$('#valor').on('keyup', function(){
		var oferta = $('#valor').val().replace(',','.');
		if ($.isNumeric(oferta)) {			
			
		} else {
			$('#valor').val('');
		}
	});
});