$(document).ready(function() {
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
	$('.deleteFeina').on('click', function(e){
		e.preventDefault();
		waitingDialog.show($(this).data('msg'));
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