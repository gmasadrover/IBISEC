package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.Fitxers;

public class Instalacions {
	private String idInf;
	private String idActuacio;
	private String expedient;
	private String expedientBaixaTensio;
	private Date dataOCABaixaTensio;
	private String expedientFotovoltaica;
	private Date dataOCAFotovoltaica;
	private String expedientContraincendis;
	private String expedientTermiques;
	private String expedientPetrolifers;
	private Date dataPetrolifers;
	private String instalacioPetrolifers;
	private String dipositsPetrolifers;
	private String capTotalPetrolifers;
	private String expedientGas;
	private String expedientAscensor;
	private String expedientEficienciaEnergetica;
	private Date dataRegistreEficienciaEnergetica;
	private String expedientPlaAutoproteccio;
	private Date dataCedulaHabitabilitat;
	private String expedientInstalacioGas;
	private String tipusInstalacioGas;
	private Date dataInstalacioGas;
	
	private List<Fitxers.Fitxer> documentsIntalacioBaixaTensio;
	private List<Fitxers.Fitxer> documentsIntalacioFotovoltaica;
	private List<Fitxers.Fitxer> documentsIntalacioContraincendis;
	private List<Fitxers.Fitxer> documentsInstalacioPetrolifera;
	private List<Fitxers.Fitxer> documentsInstalacioGas;
	private List<Fitxers.Fitxer> documentsIntalacioTermica;
	private List<Fitxers.Fitxer> documentsIntalacioAscensor;
	private List<Fitxers.Fitxer> documentsIntalacioAlarma;
	private List<Fitxers.Fitxer> documentsIntalacioSubministreAigua;	
	private List<Fitxers.Fitxer> documentsCertificatEficienciaEnergetica;	
	private List<Fitxers.Fitxer> documentsPlaAutoproteccio;	
	private List<Fitxers.Fitxer> documentsCedulaDeHabitabilitat;
	private List<Fitxers.Fitxer> documentsIniciActivitat;
	
	public String getIdInf() {
		return idInf;
	}
	public void setIdInf(String idInf) {
		this.idInf = idInf;
	}
	public String getExpedientBaixaTensio() {
		return expedientBaixaTensio;
	}
	public void setExpedientBaixaTensio(String expedientBaixaTensio) {
		this.expedientBaixaTensio = expedientBaixaTensio;
	}
	public Date getDataOCABaixaTensio() {
		return dataOCABaixaTensio;
	}
	public String getDataOCABaixaTensioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataOCABaixaTensio != null) dataString = df.format(this.dataOCABaixaTensio);
		return dataString;
	}
	public void setDataOCABaixaTensio(Date dataOCABaixaTensio) {
		this.dataOCABaixaTensio = dataOCABaixaTensio;
	}
	public String getExpedientFotovoltaica() {
		return expedientFotovoltaica;
	}
	public void setExpedientFotovoltaica(String expedientFotovoltaica) {
		this.expedientFotovoltaica = expedientFotovoltaica;
	}
	public Date getDataOCAFotovoltaica() {
		return dataOCAFotovoltaica;
	}
	public String getDataOCAFotovoltaicaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataOCAFotovoltaica != null) dataString = df.format(this.dataOCAFotovoltaica);
		return dataString;
	}
	public void setDataOCAFotovoltaica(Date dataOCAFotovoltaica) {
		this.dataOCAFotovoltaica = dataOCAFotovoltaica;
	}
	public String getExpedientContraincendis() {
		return expedientContraincendis;
	}
	public void setExpedientContraincendis(String expedientContraincendis) {
		this.expedientContraincendis = expedientContraincendis;
	}
	public String getExpedientTermiques() {
		return expedientTermiques;
	}
	public void setExpedientTermiques(String expedientTermiques) {
		this.expedientTermiques = expedientTermiques;
	}
	public String getExpedientPetrolifers() {
		return expedientPetrolifers;
	}
	public void setExpedientPetrolifers(String expedientPetrolifers) {
		this.expedientPetrolifers = expedientPetrolifers;
	}
	public String getExpedientGas() {
		return expedientGas;
	}
	public void setExpedientGas(String expedientGas) {
		this.expedientGas = expedientGas;
	}
	public String getExpedientAscensor() {
		return expedientAscensor;
	}
	public void setExpedientAscensor(String expedientAscensor) {
		this.expedientAscensor = expedientAscensor;
	}
	public String getExpedientEficienciaEnergetica() {
		return expedientEficienciaEnergetica;
	}
	public void setExpedientEficienciaEnergetica(String expedientEficienciaEnergetica) {
		this.expedientEficienciaEnergetica = expedientEficienciaEnergetica;
	}
	public Date getDataRegistreEficienciaEnergetica() {
		return dataRegistreEficienciaEnergetica;
	}
	public String getDataRegistreEficienciaEnergeticaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataRegistreEficienciaEnergetica != null) dataString = df.format(this.dataRegistreEficienciaEnergetica);
		return dataString;
	}
	public void setDataRegistreEficienciaEnergetica(Date dataRegistreEficienciaEnergetica) {
		this.dataRegistreEficienciaEnergetica = dataRegistreEficienciaEnergetica;
	}
	public String getExpedientPlaAutoproteccio() {
		return expedientPlaAutoproteccio;
	}
	public void setExpedientPlaAutoproteccio(String expedientPlaAutoproteccio) {
		this.expedientPlaAutoproteccio = expedientPlaAutoproteccio;
	}
	public Date getDataCedulaHabitabilitat() {
		return dataCedulaHabitabilitat;
	}
	public String getDataCedulaHabitabilitatString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataCedulaHabitabilitat != null) dataString = df.format(this.dataCedulaHabitabilitat);
		return dataString;
	}
	public void setDataCedulaHabitabilitat(Date dataCedulaHabitabilitat) {
		this.dataCedulaHabitabilitat = dataCedulaHabitabilitat;
	}
	public Date getDataPetrolifers() {
		return dataPetrolifers;
	}
	public String getDataPetrolifersString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataPetrolifers != null) dataString = df.format(this.dataPetrolifers);
		return dataString;
	}
	public void setDataPetrolifers(Date dataPetrolifers) {
		this.dataPetrolifers = dataPetrolifers;
	}
	public String getInstalacioPetrolifers() {
		return instalacioPetrolifers;
	}
	public void setInstalacioPetrolifers(String instalacioPetrolifers) {
		this.instalacioPetrolifers = instalacioPetrolifers;
	}
	public String getDipositsPetrolifers() {
		return dipositsPetrolifers;
	}
	public void setDipositsPetrolifers(String dipositsPetrolifers) {
		this.dipositsPetrolifers = dipositsPetrolifers;
	}
	public String getCapTotalPetrolifers() {
		return capTotalPetrolifers;
	}
	public void setCapTotalPetrolifers(String capTotalPetrolifers) {
		this.capTotalPetrolifers = capTotalPetrolifers;
	}
	public String getExpedientInstalacioGas() {
		return expedientInstalacioGas;
	}
	public void setExpedientInstalacioGas(String expedientInstalacioGas) {
		this.expedientInstalacioGas = expedientInstalacioGas;
	}
	public String getTipusInstalacioGas() {
		return tipusInstalacioGas;
	}
	public void setTipusInstalacioGas(String tipusInstalacioGas) {
		this.tipusInstalacioGas = tipusInstalacioGas;
	}
	public Date getDataInstalacioGas() {
		return dataInstalacioGas;
	}
	
	public String getDataInstalacioGasString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataInstalacioGas != null) dataString = df.format(this.dataInstalacioGas);
		return dataString;
	}
	
	public void setDataInstalacioGas(Date dataInstalacioGas) {
		this.dataInstalacioGas = dataInstalacioGas;
	}
	public String getIdActuacio() {
		return idActuacio;
	}
	public void setIdActuacio(String idActuacio) {
		this.idActuacio = idActuacio;
	}
	public String getExpedient() {
		return expedient;
	}
	public void setExpedient(String expedient) {
		this.expedient = expedient;
	}
	public List<Fitxers.Fitxer> getDocumentsIntalacioBaixaTensio() {
		return documentsIntalacioBaixaTensio;
	}
	public void setDocumentsIntalacioBaixaTensio(List<Fitxers.Fitxer> documentsIntalacioBaixaTensio) {
		this.documentsIntalacioBaixaTensio = documentsIntalacioBaixaTensio;
	}
	public List<Fitxers.Fitxer> getDocumentsIntalacioFotovoltaica() {
		return documentsIntalacioFotovoltaica;
	}
	public void setDocumentsIntalacioFotovoltaica(List<Fitxers.Fitxer> documentsIntalacioFotovoltaica) {
		this.documentsIntalacioFotovoltaica = documentsIntalacioFotovoltaica;
	}
	public List<Fitxers.Fitxer> getDocumentsIntalacioContraincendis() {
		return documentsIntalacioContraincendis;
	}
	public void setDocumentsIntalacioContraincendis(List<Fitxers.Fitxer> documentsIntalacioContraincendis) {
		this.documentsIntalacioContraincendis = documentsIntalacioContraincendis;
	}
	public List<Fitxers.Fitxer> getDocumentsInstalacioPetrolifera() {
		return documentsInstalacioPetrolifera;
	}
	public void setDocumentsInstalacioPetrolifera(List<Fitxers.Fitxer> documentsInstalacioPetrolifera) {
		this.documentsInstalacioPetrolifera = documentsInstalacioPetrolifera;
	}
	public List<Fitxers.Fitxer> getDocumentsInstalacioGas() {
		return documentsInstalacioGas;
	}
	public void setDocumentsInstalacioGas(List<Fitxers.Fitxer> documentsInstalacioGas) {
		this.documentsInstalacioGas = documentsInstalacioGas;
	}
	public List<Fitxers.Fitxer> getDocumentsIntalacioTermica() {
		return documentsIntalacioTermica;
	}
	public void setDocumentsIntalacioTermica(List<Fitxers.Fitxer> documentsIntalacioTermica) {
		this.documentsIntalacioTermica = documentsIntalacioTermica;
	}
	public List<Fitxers.Fitxer> getDocumentsIntalacioAscensor() {
		return documentsIntalacioAscensor;
	}
	public void setDocumentsIntalacioAscensor(List<Fitxers.Fitxer> documentsIntalacioAscensor) {
		this.documentsIntalacioAscensor = documentsIntalacioAscensor;
	}
	public List<Fitxers.Fitxer> getDocumentsIntalacioAlarma() {
		return documentsIntalacioAlarma;
	}
	public void setDocumentsIntalacioAlarma(List<Fitxers.Fitxer> documentsIntalacioAlarma) {
		this.documentsIntalacioAlarma = documentsIntalacioAlarma;
	}
	public List<Fitxers.Fitxer> getDocumentsIntalacioSubministreAigua() {
		return documentsIntalacioSubministreAigua;
	}
	public void setDocumentsIntalacioSubministreAigua(List<Fitxers.Fitxer> documentsIntalacioSubministreAigua) {
		this.documentsIntalacioSubministreAigua = documentsIntalacioSubministreAigua;
	}
	public List<Fitxers.Fitxer> getDocumentsCertificatEficienciaEnergetica() {
		return documentsCertificatEficienciaEnergetica;
	}
	public void setDocumentsCertificatEficienciaEnergetica(List<Fitxers.Fitxer> documentsCertificatEficienciaEnergetica) {
		this.documentsCertificatEficienciaEnergetica = documentsCertificatEficienciaEnergetica;
	}
	public List<Fitxers.Fitxer> getDocumentsPlaAutoproteccio() {
		return documentsPlaAutoproteccio;
	}
	public void setDocumentsPlaAutoproteccio(List<Fitxers.Fitxer> documentsPlaAutoproteccio) {
		this.documentsPlaAutoproteccio = documentsPlaAutoproteccio;
	}
	public List<Fitxers.Fitxer> getDocumentsCedulaDeHabitabilitat() {
		return documentsCedulaDeHabitabilitat;
	}
	public void setDocumentsCedulaDeHabitabilitat(List<Fitxers.Fitxer> documentsCedulaDeHabitabilitat) {
		this.documentsCedulaDeHabitabilitat = documentsCedulaDeHabitabilitat;
	}
	public List<Fitxers.Fitxer> getDocumentsIniciActivitat() {
		return documentsIniciActivitat;
	}
	public void setDocumentsIniciActivitat(List<Fitxers.Fitxer> documentsIniciActivitat) {
		this.documentsIniciActivitat = documentsIniciActivitat;
	}
}
