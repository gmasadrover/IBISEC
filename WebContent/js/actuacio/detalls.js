$(document).ready(function() {
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
	
});