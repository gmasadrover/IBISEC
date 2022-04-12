$(document).ready(function() {	
	//Carregar els documents
	
	$('.carregarDocTecnica').on('click', function(){
		var mainContent = $(this);
		var content = $(this);
		content = mainContent.parent().parent().parent().find('.documentacioTecnica');
    	content.html('');	
    	content.append('<div class="loader"></div>');	
		var html = '';
		var isCap = $(this).data('iscap');
		$.ajax({
	        type: "POST",
	        url: "getDocumentacioTecnica",
	        headers: {
	            'Cache-Control': 'no-cache, no-store, must-revalidate', 
	            'Pragma': 'no-cache', 
	            'Expires': '0'
	          },
	        dataType: "json",
	        data: {"idIncidencia": $(this).data('idincidencia'), "idActuacio": $(this).data('idactuacio'), "idInf": $(this).data('idinf'), "idTasca": $(this).data('idtasca')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {	
	        	content.parent().find('.docTecnica').html('');
	        	$.each(data.documentacioTecnica, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.docTecnica').append(html);
	        		numDocument++;
	        	 });  
	        	content.parent().find('.informeSupervisio').html('');
	        	$.each(data.informeSupervisio, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.informeSupervisio').append(html);
	        		numDocument++;
	        	 });  
	        	content.parent().find('.projecte').html('');
	        	$.each(data.projecte, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.projecte').append(html);
	        		numDocument++;
	        	 });  
	        	content.parent().find('.nomenamentDF').html('');
	        	$.each(data.nomenamentDF, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.nomenamentDF').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentPSS').html('');
	        	$.each(data.documentPSS, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentPSS').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentPGR').html('');
	        	$.each(data.documentPGR, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentPGR').append(html);
	        		numDocument++;
	        	 });
	        	content.parent().find('.documentPlaTreball').html('');
	        	$.each(data.documentPlaTreball, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentPlaTreball').append(html);
	        		numDocument++;
	        	 }); 
	        	
	        	content.parent().find('.documentControlQualitat').html('');
	        	$.each(data.documentControlQualitat, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentControlQualitat').append(html);
	        		numDocument++;
	        	 }); 
	        	
	        	content.parent().find('.documentFinalitzacioContratista').html('');
	        	$.each(data.documentFinalitzacioContratista, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentFinalitzacioContratista').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentInformeDO').html('');
	        	$.each(data.documentInformeDO, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentInformeDO').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentCFO').html('');
	        	$.each(data.documentCFO, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentCFO').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentRepresentacioRecepcio').html('');
	        	$.each(data.documentRepresentacioRecepcio, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentRepresentacioRecepcio').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentCertificacioFinal').html('');
	        	$.each(data.documentCertificacioFinal, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentCertificacioFinal').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentDevolucioAval').html('');
	        	$.each(data.documentDevolucioAval, function( key, arxiu ) {
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
	        		content.parent().find('.documentDevolucioAval').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentCertificatsBonaExecucio').html('');
	        	$.each(data.documentCertificatsBonaExecucio, function( key, arxiu ) {
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
	        		content.parent().find('.documentCertificatsBonaExecucio').append(html);
	        		numDocument++;
	        	 }); 
	        	
	        	
	        	
	        	content.find('.loader').remove();
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	             content.find('.loader').remove();
	        }  
	    });
	});
	
	
	$('.carregarDocumentsUrbanisme').on('click', function(){
		var mainContent = $(this);
		var content = $(this);
		content = mainContent.parent().parent().parent().find('.documentsAltresAutUrbanistica');
    	content.html('');	
    	content.append('<div class="loader"></div>');	
		var html = '';
		var isCap = $(this).data('iscap');
		$.ajax({
	        type: "POST",
	        url: "getDocumentacioUrbanisme",
	        headers: {
	            'Cache-Control': 'no-cache, no-store, must-revalidate', 
	            'Pragma': 'no-cache', 
	            'Expires': '0'
	          },
	        dataType: "json",
	        data: {"idIncidencia": $(this).data('idincidencia'), "idActuacio": $(this).data('idactuacio'), "idInf": $(this).data('idinf')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {	        	        	
	        	$.each(data.documentsAltresAutUrbanistica, function( key, arxiu ) {
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
	        	content.find('.loader').remove();
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	             content.find('.loader').remove();
	        }  
	    });
	});
	
	
	$('.carregarAltresDocuments').on('click', function(){
		var mainContent = $(this);
		var content = $(this);
		content = mainContent.parent().parent().parent().find('.documentsAltres');
    	content.html('');	
    	content.append('<div class="loader"></div>');	
		var html = '';
		var isCap = $(this).data('iscap');
		$.ajax({
	        type: "POST",
	        url: "getAltreDocumentacio",
	        headers: {
	            'Cache-Control': 'no-cache, no-store, must-revalidate', 
	            'Pragma': 'no-cache', 
	            'Expires': '0'
	          },
	        dataType: "json",
	        data: {"idIncidencia": $(this).data('idincidencia'), "idActuacio": $(this).data('idactuacio'), "idInf": $(this).data('idinf'), "idTasca": $(this).data('idtasca')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {	        	        	
	        	$.each(data.documentsAtres, function( key, arxiu ) {
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
	        	content.find('.loader').remove();
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	             content.find('.loader').remove();
	        }  
	    });
	});
	
	
	$('.carregarActes').on('click', function(){
		var mainContent = $(this);
		var content = $(this);
		content = mainContent.parent().parent().parent().find('.documentacioActes');
    	content.html('');	
    	content.append('<div class="loader"></div>');	
		var html = '';
		var isCap = $(this).data('iscap');
		$.ajax({
	        type: "POST",
	        url: "getActes",
	        headers: {
	            'Cache-Control': 'no-cache, no-store, must-revalidate', 
	            'Pragma': 'no-cache', 
	            'Expires': '0'
	          },
	        dataType: "json",
	        data: {"idIncidencia": $(this).data('idincidencia'), "idActuacio": $(this).data('idactuacio'), "idInf": $(this).data('idinf')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {	
	        	content.parent().find('.actaReplanteig').html('');
	        	$.each(data.actaReplanteig, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.actaReplanteig').append(html);
	        		numDocument++;
	        	 });  
	        	content.parent().find('.actaComprovacioReplanteig').html('');
	        	$.each(data.actaComprovacioReplanteig, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.actaComprovacioReplanteig').append(html);
	        		numDocument++;
	        	 });  
	        	content.parent().find('.actaIniciObra').html('');
	        	$.each(data.actaIniciObra, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.actaIniciObra').append(html);
	        		numDocument++;
	        	 });  
	        	content.parent().find('.actaAprovacioPlaSeguretat').html('');
	        	$.each(data.actaAprovacioPlaSeguretat, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.actaAprovacioPlaSeguretat').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.actaAprovacioResidus').html('');
	        	$.each(data.actaAprovacioResidus, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.actaAprovacioResidus').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.actaAprovacioProgramaTreball').html('');
	        	$.each(data.actaAprovacioProgramaTreball, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.actaAprovacioProgramaTreball').append(html);
	        		numDocument++;
	        	 });
	        	content.parent().find('.actaRecepcio').html('');
	        	$.each(data.actaRecepcio, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.actaRecepcio').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.actaMedicioGeneral').html('');
	        	$.each(data.actaMedicioGeneral, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.actaMedicioGeneral').append(html);
	        		numDocument++;
	        	}); 
	        	
	        	content.find('.loader').remove();
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	             content.find('.loader').remove();
	        }  
	    });
	});
	
	$('.carregarDocInstalacions').on('click', function(){
		var mainContent = $(this);
		var content = $(this);
		content = mainContent.parent().parent().parent().find('.documentacioInstalacions');
    	content.html('');	
    	content.append('<div class="loader"></div>');	
		var html = '';
		var isCap = $(this).data('iscap');
		$.ajax({
	        type: "POST",
	        url: "getDocumentacioInstalacions",
	        headers: {
	            'Cache-Control': 'no-cache, no-store, must-revalidate', 
	            'Pragma': 'no-cache', 
	            'Expires': '0'
	          },
	        dataType: "json",
	        data: {"idIncidencia": $(this).data('idincidencia'), "idActuacio": $(this).data('idactuacio'), "idInf": $(this).data('idinf'), "idTasca": $(this).data('idtasca')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {	
	        	content.parent().find('.documentsInstalacioBaixaTensio').html('');
	        	$.each(data.documentsInstalacioBaixaTensio, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsInstalacioBaixaTensio').append(html);
	        		numDocument++;
	        	 });  
	        	content.parent().find('.documentsInstalacioFotovoltaica').html('');
	        	$.each(data.documentsInstalacioFotovoltaica, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsInstalacioFotovoltaica').append(html);
	        		numDocument++;
	        	 });  
	        	content.parent().find('.documentsInstalacioContraincendis').html('');
	        	$.each(data.documentsInstalacioContraincendis, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsInstalacioContraincendis').append(html);
	        		numDocument++;
	        	 });  
	        	content.parent().find('.documentsCertificatEficienciaEnergetica').html('');
	        	$.each(data.documentsCertificatEficienciaEnergetica, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsCertificatEficienciaEnergetica').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentsInstalacioTermica').html('');
	        	$.each(data.documentsInstalacioTermica, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsInstalacioTermica').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentsInstalacioAscensor').html('');
	        	$.each(data.documentsInstalacioAscensor, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsInstalacioAscensor').append(html);
	        		numDocument++;
	        	 });
	        	content.parent().find('.documentsInstalacioAlarma').html('');
	        	$.each(data.documentsInstalacioAlarma, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsInstalacioAlarma').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentsInstalacioSubministreAigua').html('');
	        	$.each(data.documentsInstalacioSubministreAigua, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsInstalacioSubministreAigua').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentsPlaAutoproteccio').html('');
	        	$.each(data.documentsPlaAutoproteccio, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsPlaAutoproteccio').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentsCedulaDeHabitabilitat').html('');
	        	$.each(data.documentsCedulaDeHabitabilitat, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsCedulaDeHabitabilitat').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentsInstalacioPetrolifera').html('');
	        	$.each(data.documentsInstalacioPetrolifera, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsInstalacioPetrolifera').append(html);
	        		numDocument++;
	        	 }); 
	        	content.parent().find('.documentsInstalacioGas').html('');
	        	$.each(data.documentsInstalacioGas, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsInstalacioGas').append(html);
	        		numDocument++;
	        	 }); 	
	        	$.each(data.documentsIniciActivitat, function( key, arxiu ) {
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
	        		
	        		content.parent().find('.documentsIniciActivitat').append(html);
	        		numDocument++;
	        	 }); 
	        	
	        	content.find('.loader').remove();
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	             content.find('.loader').remove();
	        }  
	    });
	});
	
	$('.filerTable.factures').DataTable({
		dom: 'Bfrtip',
        buttons: [ {
	            extend: 'excelHtml5',
	            customize: function( xlsx ) {
	                var sheet = xlsx.xl.worksheets['sheet1.xml']; 
	                $('row c[r^="C"]', sheet).attr( 's', '2' );
	            },
	            exportOptions: {
                    columns: ':visible'
                }
        	},
	        {
	            extend: 'collection',
	            text: 'Editar columnes',
	            buttons: [ 'columnsVisibility' ],
	            visibility: true
	        }
        ],
		"order": [[ 0, "desc" ]],
		"aoColumns": [
    		null,
    		{"bVisible": false},  		
    		{"iDataSort": 3, "bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 5},
    		{"bVisible": false},
    		{"iDataSort": 7, "bVisible": false},
    		{"bVisible": false},
    		{"iDataSort": 9},
    		{"bVisible": false},
    		{"iDataSort": 11},
    		{"bVisible": false},
    		null,
    		null,
    		null,
    		null,
    		null
		]
	});
	
	$('.deleteRelacioPersona').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "deletePersonalExpedient",
	        dataType: "json",
	        data: {"idRelacio": $(this).data('idrelacio')},
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
