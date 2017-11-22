$(document).ready(function() {
	
	if ($('#departamentSeleccionat').val() != '') {		  		
  		$('#departament option[value="' + $('#departamentSeleccionat').val() + '"]').attr('selected', 'selected');
	}
	if ($('#departament').size() > 0) {
		  $('#departament').selectpicker('refresh');
	}
	
	$('.canviarSetmana').on('click', function() {
		if ($(this).hasClass('seguent')) {
			$('.setmana').addClass('hidden');
			$('.setmanaSeguent').removeClass('hidden');
			$(this).removeClass('seguent');
			$(this).addClass('anterior');
			$(this).val('Setmana anterior');
		} else {
			$('.setmanaSeguent').addClass('hidden');
			$('.setmana').removeClass('hidden');
			$(this).removeClass('anterior');
			$(this).addClass('seguent');
			$(this).val('Següent setmana');
		}
		
	});;
	
	$('.deleteReservaVehicle').on('click', function() {
		if(confirm("Segur que voleu eliminar aquesta reserva?")) {
			$.ajax({
		        type: "POST",
		        url: "DeleteReservaVehicle",
		        dataType: "json",
		        data: {"any": $(this).data('any'), "setmana": $(this).data('setmana'), "dia": $(this).data('dia'), "vehicle": $(this).data('vehicle'), "idusuari": $(this).data('idusuari')},
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
	
	$('.deleteReservaVacances').on('click', function() {
		if(confirm("Segur que voleu eliminar aquests dies?")) {
			$.ajax({
		        type: "POST",
		        url: "DeleteReservaVacances",
		        dataType: "json",
		        data: {"idsolicitud": $(this).data('idsolicitud')},
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
	
	$('#vehicle').on('change', function () {
		$('#horaIni').html('');
		$('#horaFi').html('');
		if ($('#vehicle').val() == 'cotxe') {			
			$('#horaIni').append('<option value="1">9:00</option>');
			$('#horaIni').append('<option value="2">9:30</option>');
			$('#horaIni').append('<option value="3">10:00</option>');
			$('#horaIni').append('<option value="4">10:30</option>');
			$('#horaIni').append('<option value="5">11:00</option>');
			$('#horaIni').append('<option value="6">11:30</option>');
			$('#horaIni').append('<option value="7">12:00</option>');
			$('#horaIni').append('<option value="8">12:30</option>');
			$('#horaIni').append('<option value="9">13:00</option>');
			$('#horaIni').append('<option value="10">13:30</option>');
			
			$('#horaFi').append('<option value="1">9:00</option>');
			$('#horaFi').append('<option value="2">9:30</option>');
			$('#horaFi').append('<option value="3">10:00</option>');
			$('#horaFi').append('<option value="4">10:30</option>');
			$('#horaFi').append('<option value="5">11:00</option>');
			$('#horaFi').append('<option value="6">11:30</option>');
			$('#horaFi').append('<option value="7">12:00</option>');
			$('#horaFi').append('<option value="8">12:30</option>');
			$('#horaFi').append('<option value="9">13:00</option>');
			$('#horaFi').append('<option value="10">13:30</option>');
		} else {
			$('#horaIni').append('<option value="1">7:30</option>');
			$('#horaIni').append('<option value="2">8:00</option>');
			$('#horaIni').append('<option value="3">8:30</option>');
			$('#horaIni').append('<option value="4">9:00</option>');
			$('#horaIni').append('<option value="5">9:30</option>');
			$('#horaIni').append('<option value="6">10:00</option>');
			$('#horaIni').append('<option value="7">10:30</option>');
			$('#horaIni').append('<option value="8">11:00</option>');
			$('#horaIni').append('<option value="9">11:30</option>');
			$('#horaIni').append('<option value="10">12:00</option>');
			$('#horaIni').append('<option value="11">12:30</option>');
			$('#horaIni').append('<option value="12">13:00</option>');
			$('#horaIni').append('<option value="13">13:30</option>');
			$('#horaIni').append('<option value="14">14:00</option>');
			$('#horaIni').append('<option value="15">14:30</option>');
			$('#horaIni').append('<option value="16">15:00</option>');
			$('#horaIni').append('<option value="17">15:30</option>');
			$('#horaIni').append('<option value="18">16:00</option>');
			$('#horaIni').append('<option value="19">16:30</option>');
			
			$('#horaFi').append('<option value="1">7:30</option>');
			$('#horaFi').append('<option value="2">8:00</option>');
			$('#horaFi').append('<option value="3">8:30</option>');
			$('#horaFi').append('<option value="4">9:00</option>');
			$('#horaFi').append('<option value="5">9:30</option>');
			$('#horaFi').append('<option value="6">10:00</option>');
			$('#horaFi').append('<option value="7">10:30</option>');
			$('#horaFi').append('<option value="8">11:00</option>');
			$('#horaFi').append('<option value="9">11:30</option>');
			$('#horaFi').append('<option value="10">12:00</option>');
			$('#horaFi').append('<option value="11">12:30</option>');
			$('#horaFi').append('<option value="12">13:00</option>');
			$('#horaFi').append('<option value="13">13:30</option>');
			$('#horaFi').append('<option value="14">14:00</option>');
			$('#horaFi').append('<option value="15">14:30</option>');
			$('#horaFi').append('<option value="16">15:00</option>');
			$('#horaFi').append('<option value="17">15:30</option>');
			$('#horaFi').append('<option value="18">16:00</option>');
			$('#horaFi').append('<option value="19">16:30</option>');
		}
		if ($('.selectpicker').size() > 0) {
       	  $('.selectpicker').selectpicker('refresh');
        }
	});
	   
});