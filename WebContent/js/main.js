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