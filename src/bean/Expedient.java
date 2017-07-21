package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expedient {
	private Actuacio actuacio;
	private InformeActuacio informe;
	private String expContratacio;
	private Date dataCreacio;
	private Date dataPublicacioBOIB;
	private Date dataLimitPresentacio;
	private Date dataAdjudicacio;
	private Date dataFormalitzacioContracte;
	private Date dataIniciExecucio;
	private Date dataRecepcio;
	private Date dataFiGarantia;
	private Date dataRetornGarantia;
	private Date dataLiquidacio;
	
	private String garantia;
	private String tipus;
	private String contracte;
	private String descripcio;
	
	public Expedient() {
		
	}
	
	public String getExpContratacio() {
		return expContratacio;
	}
	public void setExpContratacio(String expContratacio) {
		this.expContratacio = expContratacio;
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
	public String getDataLimitPresentacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataLimitPresentacio != null) dataString = df.format(this.dataLimitPresentacio);
		return dataString;
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

	public String getDataRecepcioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataRecepcio != null) dataString = df.format(this.dataRecepcio);
		return dataString;
	}
	
	public void setDataRecepcio(Date dataRecepcio) {
		this.dataRecepcio = dataRecepcio;
	}

	public Date getDataRetornGarantia() {
		return dataRetornGarantia;
	}
	
	public String getDataRetornGarantiaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataRetornGarantia != null) dataString = df.format(this.dataRetornGarantia);
		return dataString;
	}
	
	public String getDataFiGarantiaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataFiGarantia != null) dataString = df.format(this.dataFiGarantia);
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

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public Date getDataFiGarantia() {
		return dataFiGarantia;
	}

	public void setDataFiGarantia(Date dataFiGarantia) {
		this.dataFiGarantia = dataFiGarantia;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public String getContracte() {
		return contracte;
	}

	public void setContracte(String contracte) {
		this.contracte = contracte;
	}

	public InformeActuacio getInforme() {
		return informe;
	}

	public void setInforme(InformeActuacio informe) {
		this.informe = informe;
	}

	public Date getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(Date dataCreacio) {
		this.dataCreacio = dataCreacio;
	}
}
