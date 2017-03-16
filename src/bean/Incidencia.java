package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Incidencia {
	private int idIncidencia;
	private String descripcio;
	private String idCentre;
	private String nomCentre;
	private Resultat llistaActuacions;
	private User usuCre;
	private Date usuMod;
	private boolean activa;
	private Date dataTancament;
	private String solicitant;
	
	public Incidencia() {
		 
	}

	public int getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(int idEstudi) {
		this.idIncidencia = idEstudi;
	}

	public Resultat getLlistaActuacions() {
		return llistaActuacions;
	}
	
	public String getLlistaIdActuacions(){
		String llistaIds = "";
		String estat = "danger";
		for (Actuacio actuacio: this.llistaActuacions.getLlistaActuacions()) {
			if (actuacio.isActiva()) estat = "warning";
			if (actuacio.isActiva() && actuacio.isAprovada()) estat = "success";
			llistaIds += "<a style='margin:2px;' class='btn btn-" + estat + "' target='_blank' href='actuacionsDetalls?ref=" + actuacio.getReferencia() + "'>" + actuacio.getReferencia() + "</a>";
		}
		return llistaIds;
	}

	public void setLlistaActuacions(Resultat llistaActuacions) {
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
	
	public String getPeticioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(this.usuMod);		  
	}

	public Date getDataTancament() {
		return dataTancament;
	}
	
	public String getTancamentString() {
		String dataString = "";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (this.dataTancament != null) dataString = df.format(this.dataTancament);
		return dataString;		 
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

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getSolicitant() {
		return solicitant;
	}

	public void setSolicitant(String solicitant) {
		this.solicitant = solicitant;
	}
}
