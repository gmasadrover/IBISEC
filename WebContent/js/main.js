$(document).ready(function() {  	
	$('.datepicker').datepicker({
	    language: "es"    	
	});
	loadCentres();
	
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
			$.ajax({
		        type: "POST",
		        url: "DeleteDocument",
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