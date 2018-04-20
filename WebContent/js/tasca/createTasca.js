$(document).ready(function() {	
	$('#pbase').on('keyup', function(){
		var pbase = $(this).val().replace(',','.');
		if ($.isNumeric(pbase)) {			
			$(this).closest('.form-group').find('.iva').val((pbase * 0.21).toFixed(2));
			$(this).closest('.form-group').find('.inputIVA').val($(this).closest('.form-group').find('.iva').val());
			$(this).closest('.form-group').find('.plic').val((pbase * 1.21).toFixed(2));
		} else {
			$(this).val('');
			$(this).closest('.form-group').find('.iva').val('');
			$(this).closest('.form-group').find('.plic').val('');
		}
	});
	$('#plic').on('keyup', function(){
		var plic = $(this).val().replace(',','.');
		if ($.isNumeric(plic)) {
			$(this).closest('.form-group').find('.pbase').val((plic / 1.21).toFixed(2));
			var pbase = $(this).closest('.form-group').find('.pbase').val()
			$(this).closest('.form-group').find('.iva').val((pbase * 0.21).toFixed(2));
			$(this).closest('.form-group').find('.inputIVA').val($(this).closest('.form-group').find('.iva').val());
			
		} else {
			$(this).closest('.form-group').find('.pbase').val('');
			$(this).closest('.form-group').find('.iva').val('');
			$(this).val('');
		}
	});
});