$(document).ready(function() {
	var zonesAPI = "json/ciutats.json";
	$.getJSON( zonesAPI, {
	})
    .done(function( data ) {
    	$.each( data.lista.provincia, function( key, zona ) {
    		$('#provinciesList').append('<option value=' + zona._id + "_" + encodeURIComponent(zona.nombre.__cdata) + '>' + zona.nombre.__cdata + '</option>');
    	});
    	$('#provinciesList option:contains("Illes Balears")').attr('selected', 'selected');
    	$('#provinciesList').trigger('change');
    	if ($('.provinciaSelected').val() !== undefined) {
    		var provincia = $('.provinciaSelected').val();
    		$('#provinciesList option:contains(' + provincia + ')').attr('selected', 'selected');
        	$('#provinciesList').trigger('change');        	
    	}
    });
	
});

$('#provinciesList').change(function(){
	var codeZone = this.value.split('_')[0];
	var zonesAPI = "json/ciutats.json";
	$('#localitatsList').html('');
	$.getJSON( zonesAPI, {
	})
    .done(function( data ) {
    	$.each( data.lista.provincia, function( key, zona ) {
    		if (zona._id == codeZone) {
    			$.each( zona.localidades.localidad, function( key, localitat ) {    	    		
    	    		$('#localitatsList').append('<option value=' + encodeURIComponent(localitat.__cdata) + '>' + localitat.__cdata + '</option>');
    	    	});
    		}    		
    	});
    	if ($('.localitatSelected').val() !== undefined) {
    		var localitat = $('.localitatSelected').val();
    		$('#localitatsList option:contains(' + localitat + ')').attr('selected', 'selected');
    	}
    });
})