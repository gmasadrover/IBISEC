package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tasca {
	private int idTasca;
	private User usuari;
	private Actuacio actuacio;
	private Incidencia incidencia;
	private String descripcio;
	private String tipus;
	private boolean activa;
	private Date dataCreacio;
	private String idinforme;
	
	public Tasca() {

	}
	
	public int getIdTasca() {
		return idTasca;
	}
	
	public void setIdTasca(int idTasca) {
		this.idTasca = idTasca;
	}
	
	public Actuacio getActuacio() {
		return actuacio;
	}

	public void setActuacio(Actuacio actuacio) {
		this.actuacio = actuacio;
	}

	public User getUsuari() {
		return usuari;
	}

	public void setUsuari(User usuari) {
		this.usuari = usuari;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public Date getDataCreacio() {
		return dataCreacio;
	}
	
	public String getDataCreacioString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return df.format(this.dataCreacio);
	}

	public void setDataCreacio(Date dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getIdinforme() {
		return idinforme;
	}

	public void setIdinforme(String idinforme) {
		this.idinforme = idinforme;
	}
}
