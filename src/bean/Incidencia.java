package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Incidencia {
	private int idIncidencia;
	private String nom;
	private String idCentre;
	private String nomCentre;
	private List<Actuacio> llistaActuacions;
	private User usuCre;
	private Date usuMod;
	private boolean activa;
	private Date dataCreacio;
	private Date dataTancament;
	 
	public Incidencia() {
		 
	}

	public int getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(int idEstudi) {
		this.idIncidencia = idEstudi;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Actuacio> getLlistaActuacions() {
		return llistaActuacions;
	}
	
	public String getLlistaIdActuacions(){
		String llistaIds = "";
		String estat = "danger";
		for (Actuacio actuacio: this.llistaActuacions) {
			if (actuacio.isActiva()) estat = "warning";
			if (actuacio.isActiva() && actuacio.isAprovada()) estat = "success";
			llistaIds += "<a style='margin:2px;' class='btn btn-" + estat + "' target='_blank' href='actuacionsDetalls?ref=" + actuacio.getReferencia() + "'>" + actuacio.getReferencia() + "</a>";
		}
		return llistaIds;
	}

	public void setLlistaActuacions(List<Actuacio> llistaActuacions) {
		this.llistaActuacions = llistaActuacions;
	}

	public User getUsuCre() {
		return usuCre;
	}

	public void setUsuCre(User usuCre) {
		this.usuCre = usuCre;
	}

	public Date getUsuMod() {
		return usuMod;
	}

	public void setUsuMod(Date usuMod) {
		this.usuMod = usuMod;
	}
	
	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public Date getDataCreacio() {
		return dataCreacio;
	}
	
	public String getPeticioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(this.dataCreacio);		  
	}

	public void setDataCreacio(Date dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	public Date getDataTancament() {
		return dataTancament;
	}
	
	public String getTancamentString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(this.dataTancament);		  
	}

	public void setDataTancament(Date dataTancament) {
		this.dataTancament = dataTancament;
	}

	public String getIdCentre() {
		return idCentre;
	}

	public void setIdCentre(String idCentre) {
		this.idCentre = idCentre;
	}

	public String getNomCentre() {
		return nomCentre;
	}

	public void setNomCentre(String nomCentre) {
		this.nomCentre = nomCentre;
	}
}
