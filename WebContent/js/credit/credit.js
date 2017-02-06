$(document).ready(function() {
	$('#import').on('keyup', function(){
		var vec = $('#import').val().replace(',','.');
		if ($.isNumeric(vec)) {			
			
		} else {
			$('#import').val('');
		}
	});
});

function loadCredits(){
	$.ajax({
        type: "POST",
        url: "LlistatCredits",
        dataType: "json",
        
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	 $.each(data.llistaCredits, function( key, data ) {
            		 $('#creditList').append('<option value=' + data.codi + '>' + data.codi + '</option>');
            	 });          
             }             
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}