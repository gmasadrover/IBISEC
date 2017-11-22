$(document).ready(function() {
	//funcionalitat seguiment actuació
	$('#seguimentActuacio').on('change', function(){
		$.ajax({
	        type: "POST",
	        url: "seguirActuacio",
	        data: {"idActuacio":$(this).data('idactuacio'), "idUsuari":$(this).data('idusuari'), "seguir":$(this).data('seguir')},
	        dataType: "html",	        
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
	$('#obrirPD').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "openFile",
	        data: {"idincidencia":$(this).data('idincidencia'), "idactuacio":$(this).data('idactuacio')},
	        dataType: "html",
	        
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {
	            //our country code was correct so we have some information to display
	             
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	        }  
	    });
	});	
	$('.seleccionarProposta').on('click', function() {
		$.ajax({
	        type: "POST",
	        url: "seleccionarProposta",
	        data: {"idproposta":$(this).data('proposta'), "idinforme":$(this).data('informe')},
	        dataType: "html",	        
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