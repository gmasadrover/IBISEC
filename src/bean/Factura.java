package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Factura {
	private String idFactura;
	private String idInforme;
	private InformeActuacio informe;
	private String idActuacio;
	private Actuacio actuacio;
	private String idProveidor;
	private Date dataFactura;
	private String concepte;
	private double valor;
	private String tipusFactura;
	private String nombreFactura;
	private Date dataEntrada;
	private User usuariConformador;
	private Date dataConformacio;
	private String notes;

	public Factura() {
	
	}

	public String getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}

	public String getIdInforme() {
		return idInforme;
	}

	public void setIdInforme(String idInforme) {
		this.idInforme = idInforme;
	}

	public Actuacio getActuacio() {
		return actuacio;
	}

	public void setActuacio(Actuacio actuacio) {
		this.actuacio = actuacio;
	}

	public String getIdProveidor() {
		return idProveidor;
	}

	public void setIdProveidor(String idProveidor) {
		this.idProveidor = idProveidor;
	}

	public Date getDataFactura() {
		return dataFactura;
	}
	
	public String getDataFacturaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataFactura != null) dataString = df.format(this.dataFactura);
		return dataString;
	}

	public void setDataFactura(Date dataFactura) {
		this.dataFactura = dataFactura;
	}

	public String getConcepte() {
		return concepte;
	}

	public void setConcepte(String concempte) {
		this.concepte = concempte;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getTipusFactura() {
		return tipusFactura;
	}

	public void setTipusFactura(String tipusFactura) {
		this.tipusFactura = tipusFactura;
	}

	public String getNombreFactura() {
		return nombreFactura;
	}

	public void setNombreFactura(String nombreFactura) {
		this.nombreFactura = nombreFactura;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}
	
	public String getDataEntradaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataEntrada != null) dataString = df.format(this.dataEntrada);
		return dataString;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public User getUsuariConformador() {
		return usuariConformador;
	}

	public void setUsuariConformador(User usuariConformador) {
		this.usuariConformador = usuariConformador;
	}

	public Date getDataConformacio() {
		return dataConformacio;
	}
	
	public String getDataConformacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataConformacio != null) dataString = df.format(this.dataConformacio);
		return dataString;
	}

	public void setDataConformacio(Date dataConformacio) {
		this.dataConformacio = dataConformacio;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getIdActuacio() {
		return idActuacio;
	}

	public void setIdActuacio(String idActuacio) {
		this.idActuacio = idActuacio;
	}

	public InformeActuacio getInforme() {
		return informe;
	}

	public void setInforme(InformeActuacio informe) {
		this.informe = informe;
	}
	
}
