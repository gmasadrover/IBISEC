package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	private boolean llegida;
	private String primerComentari;
	private String departament;
	private boolean seguiment;
	
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

	public boolean isLlegida() {
		return llegida;
	}

	public void setLlegida(boolean llegida) {
		this.llegida = llegida;
	}

	public String getPrimerComentari() {
		return primerComentari;
	}

	public void setPrimerComentari(String primerComentari) {
		this.primerComentari = primerComentari;
	}

	public String getDepartament() {
		return departament;
	}

	public void setDepartament(String departament) {
		this.departament = departament;
	}

	public User getUsuari() {
		return usuari;
	}

	public void setUsuari(User usuari) {
		this.usuari = usuari;
	}

	public boolean isSeguiment() {
		return seguiment;
	}

	public void setSeguiment(boolean seguiment) {
		this.seguiment = seguiment;
	}
	
	public void setSeguimentActuacio(boolean seguiment) {
		this.actuacio.setSeguiment(seguiment);
	}
}
