$(document).ready(function() {
	if ($('#organValidadorSelected').val() != '') {
  		$('#organValidador option[value="' + encodeURIComponent($('#organValidadorSelected').val()) + '"]').attr('selected', 'selected');
	}
	if ($('.selectpicker').size() > 0) {
		$('.selectpicker').selectpicker('refresh');
	}
});