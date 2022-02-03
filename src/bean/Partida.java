package bean;

import java.text.DecimalFormat;

public class Partida {
	public enum PartidaTipus{
		lliure, afectat 
	}

	private String codi;
	private String nom;
	private double totalPartida;
	private double reservaPartida;
	private double contractatPartida;
	private double pagatPartida;
	private double bloquejat;
	private String tipus;
	private boolean estat;
	private boolean perdefecte;
	private String subpartidaDe; 
	
	public Partida() {
 
	}
 
	public Partida(String codi,String nom, double totalPartida, String tipus, boolean estat, String partidaPare) {
		this.codi = codi;
		this.nom = nom;
		this.totalPartida = totalPartida;
		this.reservaPartida = 0;
		this.contractatPartida = 0;
		this.tipus = tipus;
		this.estat = estat;
		this.subpartidaDe = partidaPare;
	}
	 
	public String getCodi() {
	    return codi;
	}
	 
	public void setCodi(String codi) {
		this.codi = codi;
	}
		
	public String getNom() {
	    return nom;
	}
	 
	public void setNom(String nom) {
	    this.nom = nom;
	}
	
	public String getTipus(){
		return tipus;
	}
	 
	public void setTipus(String tipus) {
		 this.tipus = tipus;
	}
	
	public boolean getEstat() {
		return estat;
	}
	
	public String getEstatFormat(){
		if (estat) {
			return "Oberta";
		}else {
			return "Tancada";
		}		
	}
	
	public void setEstat(boolean estat) {
		this.estat = estat;
	}
	
	public double getTotalPartida(){		
	    return totalPartida;
	}
	
	public String getTotalPartidaFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(totalPartida) + '�';
	}
	
	public String getTotalPartidaString(){
		DecimalFormat num = new DecimalFormat("###0.00");
	    return num.format(totalPartida);
	}
	
	public void setTotalPartida(double totalPartida){		
		this.totalPartida = totalPartida;
	}
	
	public double getReservaPartida(){		
		return this.reservaPartida;
	}
	
	public String getReservaPartidaFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(getReservaPartida()) + '�';
	}
	
	public void setReservaPartida(double reservaPartida){
		this.reservaPartida = reservaPartida;
	}
	
	public double getContractatPartida(){
		return contractatPartida;
	}
	
	public String getPrevistPartidaFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(contractatPartida) + '�';
	}
	
	public void setPrevistPartida(double previstPartida){
		this.contractatPartida = previstPartida;
	}
	
	public double getPartidaPerAsignar(){
		double partidaPerAsignar = this.totalPartida - this.getContractatPartida() - this.getReservaPartida() - this.getPagatPartida()- this.getBloquejat();
		return partidaPerAsignar;
	}
	
	public String getPartidaPerAsignarFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(getPartidaPerAsignar()) + '�';
	}

	public double getPagatPartida() {
		return pagatPartida;
	}

	public void setPagatPartida(double pagatPartida) {
		this.pagatPartida = pagatPartida;
	}
	
	public String getPartidaPagatFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.pagatPartida ) + '�';
	}

	public boolean isPerdefecte() {
		return perdefecte;
	}

	public void setPerdefecte(boolean perdefecte) {
		this.perdefecte = perdefecte;
	}

	public String getSubpartidaDe() {
		return subpartidaDe;
	}

	public void setSubpartidaDe(String subpartidaDe) {
		this.subpartidaDe = subpartidaDe;
	}

	public double getBloquejat() {
		return bloquejat;
	}
	
	public String getBloquejatFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.bloquejat ) + '�';
	}

	public void setBloquejat(double bloquejat) {
		this.bloquejat = bloquejat;
	}
	
}
