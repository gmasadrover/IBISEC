package bean;

import java.text.DecimalFormat;

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
}
