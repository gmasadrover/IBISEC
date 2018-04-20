$(document).ready(function() {
	$('#conformarFactura').on('click', function() {		
		$.ajax({
	        type: "POST",
	        url: "ConformarFactura",
	        dataType: "json",
	        data: {"idFactura": $(this).data('idfactura')},
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
	});
	$('#enviarFacturaComptabilitat').on('click', function() {		
		$.ajax({
	        type: "POST",
	        url: "facturaComptabilitat",
	        dataType: "json",
	        data: {"idFactura": $(this).data('idfactura')},
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
	});
	$('#anularFactura').on('click', function() {		
		$.ajax({
	        type: "POST",
	        url: "anularFactura",
	        dataType: "json",
	        data: {"idFactura": $(this).data('idfactura'), "motiu": $('#motiuAnulacio').val()},
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
	});
});