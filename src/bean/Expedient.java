package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expedient {
	private Actuacio actuacio;
	private String expContratacio;
	private double importLicitacio;
	private String terminiExecucio;
	private Date dataPublicacioBOIB;
	private Date dataLimitPresentacio;
	private Date dataAdjudicacio;
	private double importAdjudicacio;
	private Date dataFormalitzacioContracte;
	private Date dataIniciExecucio;
	private Date dataRecepcio;
	private Date dataRetornGarantia;
	private Date dataLiquidacio;
	private double garantia;
	
	public Expedient() {
		
	}
	
	public String getExpContratacio() {
		return expContratacio;
	}
	public void setExpContratacio(String expContratacio) {
		this.expContratacio = expContratacio;
	}
	public double getImportLicitacio() {
		return importLicitacio;
	}
	public void setImportLicitacio(double importLicitacio) {
		this.importLicitacio = importLicitacio;
	}
	public String getTerminiExecucio() {
		return terminiExecucio;
	}
	public void setTerminiExecucio(String terminiExecucio) {
		this.terminiExecucio = terminiExecucio;
	}
	public Date getDataPublicacioBOIB() {
		return dataPublicacioBOIB;
	}
	
	public String getDataPublicacioBOIBString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataPublicacioBOIB != null) dataString = df.format(this.dataPublicacioBOIB);
		return dataString;
	}	
	
	public void setDataPublicacioBOIB(Date dataPublicacioBOIB) {
		this.dataPublicacioBOIB = dataPublicacioBOIB;
	}
	public Date getDataLimitPresentacio() {
		return dataLimitPresentacio;
	}
	public void setDataLimitPresentacio(Date dataLimitPresentacio) {
		this.dataLimitPresentacio = dataLimitPresentacio;
	}
	public Date getDataAdjudicacio() {
		return dataAdjudicacio;
	}
	
	public String getDataAdjudicacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataAdjudicacio != null) dataString = df.format(this.dataAdjudicacio);
		return dataString;
	}
	
	
	public void setDataAdjudicacio(Date dataAdjudicacio) {
		this.dataAdjudicacio = dataAdjudicacio;
	}
	public double getImportAdjudicacio() {
		return importAdjudicacio;
	}
	public void setImportAdjudicacio(double importAdjudicacio) {
		this.importAdjudicacio = importAdjudicacio;
	}
	public Date getDataFormalitzacioContracte() {
		return dataFormalitzacioContracte;
	}
	
	public String getDataFirmaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataFormalitzacioContracte != null) dataString = df.format(this.dataFormalitzacioContracte);
		return dataString;
	}
	
	public void setDataFormalitzacioContracte(Date dataFormalitzacioContracte) {
		this.dataFormalitzacioContracte = dataFormalitzacioContracte;
	}

	public Actuacio getActuacio() {
		return actuacio;
	}

	public void setActuacio(Actuacio actuacio) {
		this.actuacio = actuacio;
	}

	public Date getDataIniciExecucio() {
		return dataIniciExecucio;
	}
	
	public String getDataIniciObratring() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataIniciExecucio != null) dataString = df.format(this.dataIniciExecucio);
		return dataString;
	}
	
	public void setDataIniciExecucio(Date dataIniciExecucio) {
		this.dataIniciExecucio = dataIniciExecucio;
	}

	public Date getDataRecepcio() {
		return dataRecepcio;
	}

	public void setDataRecepcio(Date dataRecepcio) {
		this.dataRecepcio = dataRecepcio;
	}

	public Date getDataRetornGarantia() {
		return dataRetornGarantia;
	}
	
	public String getDataFiGarantiaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataRetornGarantia != null) dataString = df.format(this.dataRetornGarantia);
		return dataString;
	}
	
	public void setDataRetornGarantia(Date dataRetornGarantia) {
		this.dataRetornGarantia = dataRetornGarantia;
	}

	public Date getDataLiquidacio() {
		return dataLiquidacio;
	}

	public String getDataLiquidacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataLiquidacio != null) dataString = df.format(this.dataLiquidacio);
		return dataString;
	}

	
	public void setDataLiquidacio(Date dataLiquidacio) {
		this.dataLiquidacio = dataLiquidacio;
	}

	public double getGarantia() {
		return garantia;
	}

	public void setGarantia(double garantia) {
		this.garantia = garantia;
	}
}
