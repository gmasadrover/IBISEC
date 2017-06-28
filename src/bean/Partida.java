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
	private double previstPartida;
	private double pagatPartida;
	private String tipus;
	private boolean estat;
	 
	public Partida() {
 
	}
 
	public Partida(String codi,String nom, double totalPartida, String tipus, boolean estat) {
		this.codi = codi;
		this.nom = nom;
		this.totalPartida = totalPartida;
		this.reservaPartida = 0;
		this.previstPartida = 0;
		this.tipus = tipus;
		this.estat = estat;
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
	    return num.format(totalPartida) + '€';
	}
	
	public void setTotalPartida(double totalPartida){		
		this.totalPartida = totalPartida;
	}
	
	public double getReservaPartida(){		
		return this.reservaPartida;
	}
	
	public String getReservaPartidaFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(getReservaPartida()) + '€';
	}
	
	public void setReservaPartida(double reservaPartida){
		this.reservaPartida = reservaPartida;
	}
	
	public double getPrevistPartida(){
		return previstPartida;
	}
	
	public String getPrevistPartidaFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(previstPartida) + '€';
	}
	
	public void setPrevistPartida(double previstPartida){
		this.previstPartida = previstPartida;
	}
	
	public double getPartidaPerAsignar(){
		return this.totalPartida - this.getPrevistPartida() - this.getReservaPartida();
	}
	
	public String getPartidaPerAsignarFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(getPartidaPerAsignar()) + '€';
	}

	public double getPagatPartida() {
		return pagatPartida;
	}

	public void setPagatPartida(double pagatPartida) {
		this.pagatPartida = pagatPartida;
	}
	
	public String getPartidaPagatFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.pagatPartida ) + '€';
	}
}
