$(document).ready(function() {
	$('#llistaUsuaris').on('change', function(){	
		if (decodeURIComponent($(this).val()) == '-1') {	
			$("#llistaEmpreses").selectpicker('deselectAll');
			$('#llistaEmpreses').selectpicker('val', "-1");				
    		$('#llistaEmpreses').selectpicker('refresh');   
    		$('.llistaEmpresesDiv').removeClass('hidden');
		} else {
			$('.llistaEmpresesDiv').addClass('hidden');
			
		}		
	});		
});
