package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.Fitxers;

public class Historic {
	private int idHistoric;
	private int idTasca;
	private String comentari;
	private Actuacio actuacio;
	private Tasca tasca;
	private User usuari;
	private Date data;
	private String ipRemota;
	private String tipus;
	private List<Fitxers.Fitxer> adjunts;
	public Historic() {

	}

	

	public int getIdHistoric() {
		return idHistoric;
	}

	public void setIdHistoric(int idHistoric) {
		this.idHistoric = idHistoric;
	}

	public int getIdTasca() {
		return idTasca;
	}

	public void setIdTasca(int idTasca) {
		this.idTasca = idTasca;
	}

	public String getComentari() {
		return comentari;
	}

	public void setComentari(String comentari) {
		this.comentari = comentari;
	}

	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return df.format(this.data);
	}

	public User getUsuari() {
		return usuari;
	}

	public void setUsuari(User usuari) {
		this.usuari = usuari;
	}

	public List<Fitxers.Fitxer> getAdjunts() {
		return adjunts;
	}

	public void setAdjunts(List<Fitxers.Fitxer> adjunts) {
		this.adjunts = adjunts;
	}

	public String getIpRemota() {
		return ipRemota;
	}

	public void setIpRemota(String ipRemota) {
		this.ipRemota = ipRemota;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public Actuacio getActuacio() {
		return actuacio;
	}

	public void setActuacio(Actuacio actuacio) {
		this.actuacio = actuacio;
	}

	public Tasca getTasca() {
		return tasca;
	}

	public void setTasca(Tasca tasca) {
		this.tasca = tasca;
	}
}
