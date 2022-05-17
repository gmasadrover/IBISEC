$(document).ready(function() {
	$('#idUsuariRecercaPresuposts').selectpicker('val', $('#idUsuariRecercaPresupostsPrevi').val());
	$('#idUsuariFactures').selectpicker('val', $('#idUsuariFacturesPrevi').val());
	$('#idUsuariCertificacions').selectpicker('val', $('#idUsuariCertificacionsPrevi').val());
	$('#idUsuariOrdreInici').selectpicker('val', $('#idUsuariOrdreIniciPrevi').val());
	$('#idUsuariRedaccioContracte').selectpicker('val', $('#idUsuariRedaccioContractePrevi').val());
	$('#idUsuariActualitzarEmpresa').selectpicker('val', $('#idUsuariActualitzarEmpresaPrevi').val());
	$('#idUsuariLlicencies').selectpicker('val', $('#idUsuariLlicenciesPrevi').val());
	$('#idUsuariDron').selectpicker('val', $('#idUsuariDronPrevi').val());
	
	$('.selectpicker').selectpicker('refresh');
});