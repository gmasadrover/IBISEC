$(document).ready(function() {	
	$('.deleteReservaElement').on('click', function() {
		if(confirm("Segur que voleu eliminar aquesta reserva?")) {
			$.ajax({
		        type: "POST",
		        url: "DeleteReservaElement",
		        dataType: "json",
		        data: {"any": $(this).data('any'), "setmana": $(this).data('setmana'), "dia": $(this).data('dia'), "element": $(this).data('element'), "idusuari": $(this).data('idusuari')},
		        //if received a response from the server
		        success: function( data, textStatus, jqXHR) {
		            //our country code was correct so we have some information to display
		        	location.reload();      
		        },        
		        //If there was no resonse from the server
		        error: function(jqXHR, textStatus, errorThrown){
		             console.log("Something really bad happened " + jqXHR.responseText);
		        }  
		    });
		}		
	});
});