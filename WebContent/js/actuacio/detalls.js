$(document).ready(function() {
	
	//Carregar els documents
	$('.carregarDocumentsActuacio').on('click', function(){
		var mainContent = $(this);
		var content = $(this);
		content = mainContent.parent().parent().parent().find('.documentsActuacio');
    	content.html('');	
		$('#arxiusAdjuntsActuacio').append('<div class="loader"></div>');	
		var html = '';
		var isCap = $(this).data('iscap');
		$.ajax({
	        type: "POST",
	        url: "getDocumentacioActuacio",
	        headers: {
	            'Cache-Control': 'no-cache, no-store, must-revalidate', 
	            'Pragma': 'no-cache', 
	            'Expires': '0'
	          },
	        dataType: "json",
	        data: {"idIncidencia": $(this).data('idincidencia'), "idActuacio": $(this).data('idactuacio')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {	        		        	
	        	$.each(data.documentsActuacio, function( key, arxiu ) {
	        		html = '';
	        		html += '<div id="' + numDocument + '" class="document">';
	        		html += '<a target="_blanck" href="downloadFichero?ruta=' + arxiu.encodedRuta + '">';
	        		html += arxiu.dataString + ' - ' + arxiu.nom;
	        		html += '</a>';
	        		if (isCap && arxiu.ruta != null) {
	        			html += '<span data-ruta="' + arxiu.ruta + '" onclick="deleteFile(' + numDocument + ')" class="glyphicon glyphicon-remove deleteFile"></span>';
	        		}
	        		if (arxiu.signat) {
	        			html += '<span data-ruta="' + arxiu.ruta + '" onclick="signedFile(' + numDocument + ')" class="glyphicon glyphicon-pencil signedFile"></span>';
	        		}	        			
	        		html += '<br>';
	        		html += '<div class="infoSign hidden"></div>';
	        		html += '</div>';
	        		content.append(html);
	        		numDocument++;
	        	 });  
	        	$('#arxiusAdjuntsActuacio .loader').remove();
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	             $('#arxiusAdjuntsActuacio .loader').remove();
	        }  
	    });
	});
	
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