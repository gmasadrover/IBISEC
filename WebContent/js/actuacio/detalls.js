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
	$('.filerTable.factures').DataTable({
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		null,    		
    		{"iDataSort": 3},
    		{"bVisible": false},
    		{"iDataSort": 5},
    		{"bVisible": false},
    		null,
    		null,
    		null,
    		null
		]
	});
	$('.filerTable.taulaFeines').DataTable({
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		null,    	
    		null,
    		null,
    		{"iDataSort": 5},
    		{"bVisible": false},
    		null,
    		null
		]
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
	
	$('.deleteFeina').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DoDeleteFeina",
	        dataType: "json",
	        data: {"idFeina": $(this).data('idfeina')},
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