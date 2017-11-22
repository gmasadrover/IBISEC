$(document).ready(function() {
	$('.filerTable').DataTable({
		"order": [[ 5, "desc" ]],
		"aoColumns": [
    		null,
    		null,
    		null,
    		{"iDataSort": 4},
    		{"bVisible": false},
    		{"iDataSort": 6},
    		{"bVisible": false},
    		null
		]
	});	
	$('.ofertaSeleccionada').on('click', function(){
		var table = $('.filerTable').DataTable();      
		$(this).parents('tr').addClass('selected');
		$('#ofertaSeleccionada').text(table.row('.selected').data()[2]);  
		$('#idOfertaSeleccionada').val(table.row('.selected').data()[0]);  
		$(this).parents('tr').removeClass('selected');
	});		

	$('.deleteOferta').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DoDeleteOferta",
	        dataType: "json",
	        data: {"idOferta": $(this).data('idoferta')},
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
	$('#createTascaLicitacio').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DoCreateTascaLicitacio",
	        dataType: "json",
	        data: {"informe": $(this).data('informe')},
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
	$('#createTascaAutoDespesa').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "DoCreateTascaAutoritzacioDespesa",
	        dataType: "json",
	        data: {"informe": $(this).data('informe')},
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