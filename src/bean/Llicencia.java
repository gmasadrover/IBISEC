package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.Fitxers;

public class Llicencia {
	private String codiExpedient;
	private String codi;
	private String tipus;
	private double taxa;
	private double ico;
	private String observacio;
	private Date peticio;
	private Date concesio;
	private Date pagament;
	private List<Fitxers.Fitxer> arxius;
	
	public Llicencia() {
		
	}

	public String getCodiExpedient() {
		return codiExpedient;
	}

	public void setCodiExpedient(String codiExpedient) {
		this.codiExpedient = codiExpedient;
	}

	public String getCodi() {
		return codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
	}

	public String getTipus() {
		return tipus;
	}
	
	public String getTipusFormat(){		
		if (this.tipus != null) {
			switch(this.tipus) {
			case "llicencia":
				return "llic�ncia";
			case "comun":
				return "comunicaci� pr�via";
			}		
		}
		
		return "";
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public String getObservacio() {
		return observacio;
	}

	public void setObservacio(String observacio) {
		this.observacio = observacio;
	}

	public Date getPeticio() {
		return peticio;
	}
	
	public String getDataPeticioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.peticio != null) dataString = df.format(this.peticio);
		return dataString;
	}

	public void setPeticio(Date peticio) {
		this.peticio = peticio;
	}

	public Date getPagament() {
		return pagament;
	}
	
	public String getDataPagamentString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.pagament != null) dataString = df.format(this.pagament);
		return dataString;
	}

	public void setPagament(Date pagamanent) {
		this.pagament = pagamanent;
	}

	public Date getConcesio() {
		return concesio;
	}
	
	public String getDataConcesioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.concesio != null) dataString = df.format(this.concesio);
		return dataString;
	}

	public void setConcesio(Date concesio) {
		this.concesio = concesio;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

	public double getIco() {
		return ico;
	}

	public void setIco(double ico) {
		this.ico = ico;
	}

	public List<Fitxers.Fitxer> getArxius() {
		return arxius;
	}

	public void setArxius(List<Fitxers.Fitxer> arxius) {
		this.arxius = arxius;
	}
}
