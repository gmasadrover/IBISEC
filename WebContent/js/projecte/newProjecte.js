$(document).ready(function() {
	$(".usuarisList").selectpicker('deselectAll');
	$('.usuarisList').selectpicker('val', "-1");				
	$('.usuarisList').selectpicker('refresh');
	$('#usuarisList').on('change', function(){
		var html = '';
		$('.participants').addClass('hidden');	
		if ($(this).val() != null && ($(this).val().length == 2 || $(this).val().indexOf('-1') < 0)) {		
			$('#usuarisList option[value="-1"]').prop('selected', false);			
			$.each($(this).val(), function( key, data ) {
				if (data != "-1") {
					if ($('.participants' + data).size() > 0) {
						$('.participants'  + data).removeClass('hidden');
					} else {
						html += '<div class="form-group participants participants' + data + '">';
		            	html += '	<label class="col-xs-3 control-label">Nom: </label>';
		            	html += ' 	<div class="col-xs-3">';   
		            	html += '		<label class="col-xs-9 control-label">' + $('#usuarisList option[value="' + data + '"]').text() + '</label>'
		            	html += '	</div>';
		            	html += '</div>';
		            	$('#participants').append(html);
					}
				}				
			});				
			$('.selectpicker').selectpicker('refresh');
		} else {	
			if ($('.selectpicker').size() > 0) {
				$(".usuarisList").selectpicker('deselectAll');
				$('.usuarisList').selectpicker('val', "-1");				
	    		$('.usuarisList').selectpicker('refresh');
	    	}
		}		
	});	
});