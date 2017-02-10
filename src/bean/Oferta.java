package bean;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Oferta {
	private int idOferta;
	private int idActuacio;
	private String cifEmpresa;
	private String nomEmpresa;
	private double vec;
	private double iva;
	private double plic;
	private String termini;
	private boolean seleccionada;
	private boolean descalificada;
	private String comentari;
	private User usuariCreacio;
	private Date dataCreacio;
	private User usuariAprovacio;
	private Date dataAprovacio;
	
	public Oferta(){
		
	}

	public int getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(int idOferta) {
		this.idOferta = idOferta;
	}

	public int getIdActuacio() {
		return idActuacio;
	}

	public void setIdActuacio(int idActuacio) {
		this.idActuacio = idActuacio;
	}

	public String getCifEmpresa() {
		return cifEmpresa;
	}

	public void setCifEmpresa(String cifEmpresa) {
		this.cifEmpresa = cifEmpresa;
	}

	public double getVec() {
		return vec;
	}

	public String getVecFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.vec) + '€';
	}
	
	public void setVec(double vec) {
		this.vec = vec;
	}

	public double getIva() {
		return iva;
	}

	public String getIvaFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.iva) + '€';
	}
	
	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getPlic() {
		return plic;
	}

	public void setPlic(double plic) {
		this.plic = plic;
	}
	
	public String getPlicFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.plic) + '€';
	}

	public String getTermini() {
		return termini;
	}

	public void setTermini(String termini) {
		this.termini = termini;
	}

	public boolean isSeleccionada() {
		return seleccionada;
	}

	public void setSeleccionada(boolean seleccionada) {
		this.seleccionada = seleccionada;
	}

	public boolean isDescalificada() {
		return descalificada;
	}

	public void setDescalificada(boolean descalificada) {
		this.descalificada = descalificada;
	}

	public String getComentari() {
		return comentari;
	}

	public void setComentari(String comentari) {
		this.comentari = comentari;
	}

	public String getNomEmpresa() {
		return nomEmpresa;
	}

	public void setNomEmpresa(String nomEmpresa) {
		this.nomEmpresa = nomEmpresa;
	}

	public Date getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(Date dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	public User getUsuariCreacio() {
		return usuariCreacio;
	}

	public void setUsuariCreacio(User usuariCreacio) {
		this.usuariCreacio = usuariCreacio;
	}

	public User getUsuariAprovacio() {
		return usuariAprovacio;
	}

	public void setUsuariAprovacio(User usuariAprovacio) {
		this.usuariAprovacio = usuariAprovacio;
	}

	public Date getDataAprovacio() {
		return dataAprovacio;
	}
	
	public String getDataAprovacioString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataAprovacio != null) dataString = df.format(this.dataAprovacio);
		return dataString;		 
	}

	public void setDataAprovacio(Date dataAprovacio) {
		this.dataAprovacio = dataAprovacio;
	}
}
