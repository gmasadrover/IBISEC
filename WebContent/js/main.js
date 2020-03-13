$(document).ready(function() {  	
	$('.datepicker').datepicker({
	    language: "es"    	
	});
	loadCentres();   
	setInterval(function() {
		$.ajax({
	        type: "POST",
	        url: "NewNotificacio",
	        dataType: "json",
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {
	            //our country code was correct so we have some information to display
	        	if (data.novesTasques) {
	        		//prueba_notificacion();
	        		alert('Hi ha noves tasques')
	        	}	        	
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	        }  
	    });
	}, 5 * 60 * 1000); // 60 * 1000 milsec
	
	$('.upload').on("click",function() { 
        var imgVal = $(this).parents('.form-horizontal').find('.uploadImage').val(); 
        if(imgVal=='') { 
            alert("S'ha d'adjuntar algun document"); 
            return false; 
        } else {
        	 return true; 
        }    
    }); 
	
	$('.loadingButton').on("click", function() {
		waitingDialog.show($(this).data('msg'));
	});
	$('.processingButton').on("submit", function() {
		waitingDialog.show($(this).data('msg'));
	});
	
	$('.deleteFile').on('click', function(){
		if(confirm("Segur que voleu eliminar aquest document?")) {
			waitingDialog.show('Eliminar');
			$.ajax({
		        type: "GET",
		        url: "DeleteDocument",
		        async: false,
		        dataType: "json",
		        data: {"ruta": $(this).data('ruta')},
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
	
	$('.signedFile').on('click', function(){
		if ($(this).parent().find('.infoSign').hasClass('hidden')){
			$(this).parent().find('.infoSign').html('');
			var content = $(this).parent().find('.infoSign');
			$.ajax({
		        type: "GET",
		        url: "getSignaruresDocument",
		        async: false,
		        dataType: "json",
		        data: {"ruta": $(this).data('ruta')},
		        //if received a response from the server
		        success: function( data, textStatus, jqXHR) {		        	
		        	 $.each(data.firmesList, function( key, firma ) {
		        		 content.append('<span>Signat per: ' + firma.nomFirmant + ' - ' + firma.dataFirma + '</span><br>');
	            	 });
		        },        
		        //If there was no resonse from the server
		        error: function(jqXHR, textStatus, errorThrown){
		        	content.html("error");
		        }  
		    });
			$(this).parent().find('.infoSign').removeClass('hidden');
		} else {
			$(this).parent().find('.infoSign').addClass('hidden');
		}
	});
	
	$('.openFolder').on('click', function(){
		$.ajax({
	        type: "POST",
	        url: "OpenFolder",
	        dataType: "json",
	        data: {"idIncidencia": $(this).data('idincidencia')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {
	            //our country code was correct so we have some information to display
	        	//location.reload();      
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	             console.log("Something really bad happened " + jqXHR.responseText);
	        }  
	    });
	});
});

function loadCentres(){
	$.ajax({
        type: "POST",
        url: "LlistatCentres",
        dataType: "json",
        async: false,
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
             if(data.success){
            	 $.each(data.llistatCentres, function( key, data ) {
            		 $('#centresList').append('<option value=' + data.idCentre + '>' + data.nom + ' (' + data.localitat + ')</option>');
            	 });    
            	 if ($('#idCentreSelected').val() != '') {		  		
         	  		$('#centresList option[value="' + $('#idCentreSelected').val() + '"]').attr('selected', 'selected');
           		}
               if ($('.selectpicker').size() > 0) {
             	  $('.selectpicker').selectpicker('refresh');
               }
             }             
        },        
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + jqXHR.responseText);
        }  
    });
}

var numDocument = 1;

function deleteFile(numDocument){
	var id = '#' + numDocument;
	if(confirm("Segur que voleu eliminar aquest document?")) {
		waitingDialog.show('Eliminar');
		$.ajax({
	        type: "GET",
	        url: "DeleteDocument",
	        async: false,
	        dataType: "json",
	        data: {"ruta": $(id).find('.deleteFile').data('ruta')},
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
};

function signedFile(numDocument){
	var id = '#' + numDocument;
	if ($(id).find('.infoSign').hasClass('hidden')){
		$(id).find('.infoSign').html('');
		var content = $(id).find('.infoSign');
		$.ajax({
	        type: "GET",
	        url: "getSignaruresDocument",
	        async: false,
	        dataType: "json",
	        data: {"ruta": $(id).find('.signedFile').data('ruta')},
	        //if received a response from the server
	        success: function( data, textStatus, jqXHR) {		        	
	        	 $.each(data.firmesList, function( key, firma ) {
	        		 content.append('<span>Signat per: ' + firma.nomFirmant + ' - ' + firma.dataFirma + '</span><br>');
            	 });
	        },        
	        //If there was no resonse from the server
	        error: function(jqXHR, textStatus, errorThrown){
	        	content.html("error");
	        }  
	    });
		$(id).find('.infoSign').removeClass('hidden');
	} else {
		$(id).find('.infoSign').addClass('hidden');
	}
};
function prueba_notificacion() {
	if (Notification) {
		//if (Notification.permission !== "granted") {
		//Notification.requestPermission()
		//}
		var title = "Notificació intranet"
		var extra = {
		icon: "favicon.png",
		body: "Nova tasca"
		}
		var noti = new Notification( title, extra)
		noti.onclick = {
		// Al hacer click
		}
		noti.onclose = {
		// Al cerrar
		}
		//setTimeout( function() { noti.close() }, 10000)
	}
}
var waitingDialog = waitingDialog || (function ($) {
    'use strict';

	// Creating modal dialog's DOM
	var $dialog = $(
		'<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
		'<div class="modal-dialog modal-m">' +
		'<div class="modal-content">' +
			'<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
			'<div class="modal-body">' +
				'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
			'</div>' +
		'</div></div></div>');

	return {
		/**
		 * Opens our dialog
		 * @param message Custom message
		 * @param options Custom options:
		 * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
		 * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
		 */
		show: function (message, options) {
			// Assigning defaults
			if (typeof options === 'undefined') {
				options = {};
			}
			if (typeof message === 'undefined') {
				message = 'Loading';
			}
			var settings = $.extend({
				dialogSize: 'm',
				progressType: '',
				onHide: null // This callback runs after the dialog was hidden
			}, options);

			// Configuring dialog
			$dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
			$dialog.find('.progress-bar').attr('class', 'progress-bar');
			if (settings.progressType) {
				$dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
			}
			$dialog.find('h3').text(message);
			// Adding callbacks
			if (typeof settings.onHide === 'function') {
				$dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
					settings.onHide.call($dialog);
				});
			}
			// Opening dialog
			$dialog.modal();
		},
		/**
		 * Closes dialog
		 */
		hide: function () {
			$dialog.modal('hide');
		}
	};

})(jQuery);