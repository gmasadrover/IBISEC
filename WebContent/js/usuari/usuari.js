$(document).ready(function() {
	loadUsuaris();
});

function loadUsuaris(){
	$.ajax({
        type: "POST",
        url: "LlistatUsuaris",
        dataType: "json",
        
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	 $.each(data.llistatUsuaris, function( key, data ) {
            		 $('#usuarisList').append('<option value=' + data.idUsuari + '>' + data.name + ' ' + data.llinatges + '</option>');
            	 });    
            	 $('.selectpicker').selectpicker('refresh');
             }             
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}