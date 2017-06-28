package bean;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class Altres {
	private String idActuacio;
	private String descripcio;
	private Date dataCreacio;
	private Date dataAprovacio;
	private String nomCentre;
	private Empresa empresa;
	private double valor;
	private String idInforme;
	private List<Factura> factures;
	private String notes;
	private Date dataTancament;	
	
	public Altres(){
		
	}	

	public String getIdActuacio() {
		return idActuacio;
	}


	public void setIdActuacio(String idActuacio) {
		this.idActuacio = idActuacio;
	}


	public String getDescripcio() {
		return descripcio;
	}


	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}


	public Date getDataCreacio() {
		return dataCreacio;
	}

	public String getDataCreacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataCreacio != null) dataString = df.format(this.dataCreacio);
		return dataString;
	}

	public void setDataCreacio(Date dataCreacio) {
		this.dataCreacio = dataCreacio;
	}


	public Date getDataAprovacio() {
		return dataAprovacio;
	}

	public String getDataAprovacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataAprovacio != null) dataString = df.format(this.dataAprovacio);
		return dataString;
	}

	public void setDataAprovacio(Date dataAprovacio) {
		this.dataAprovacio = dataAprovacio;
	}


	public String getNomCentre() {
		return nomCentre;
	}


	public void setNomCentre(String nomCentre) {
		this.nomCentre = nomCentre;
	}


	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


	public double getValor() {
		return valor;
	}


	public void setValor(double valor) {
		this.valor = valor;
	}


	public List<Factura> getFactures() {
		return factures;
	}


	public void setFactures(List<Factura> factures) {
		this.factures = factures;
	}
	
	public String getTotalFacturat() {
		double totalFacturat = 0;
		ListIterator<Factura> iter = this.factures.listIterator(this.factures.size());
		while (iter.hasPrevious()) {
			totalFacturat += iter.previous().getValor();
		}
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(totalFacturat) + '€';
	}

	public String getIdInforme() {
		return idInforme;
	}

	public void setIdInforme(String idInforme) {
		this.idInforme = idInforme;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getDataTancament() {
		return dataTancament;
	}

	public void setDataTancament(Date dataTancament) {
		this.dataTancament = dataTancament;
	}
	
	public String getDataTancamentString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataTancament != null) dataString = df.format(this.dataTancament);
		return dataString;
	}
}
