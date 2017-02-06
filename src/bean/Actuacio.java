package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Actuacio {
	 private int referencia;
	 private String descripcio;
	 private Date dataCreacio;
	 private int idUsuariCreacio;
	 private String idCentre;	
	 private String nomCentre;
	 private boolean activa;
	 private Date dataTancament;
	 private boolean aprovada;
	 private Date dataAprovacio;
	 private int idInformePrevi;
	 private int idIncidencia;
	 private Date darreraModificacio;
	 private String modificacio;
	 
	 public Actuacio() {
 
	 }
 
	 public int getReferencia() {
		 return referencia;
	 }
 
	 public void setReferencia(int referencia) {
		 this.referencia = referencia;
	 }
	 
	 public String getIdCentre() {
		 return idCentre;
	 }
	 
	 public void setIdCentre(String idCentre) {
		 this.idCentre = idCentre;
	 }
	 	 
	 public String getDescripcio() {
		 return descripcio;
	 }
 
	 public void setDescripcio(String descripcio) {
		 this.descripcio = descripcio;
	 }
	 	 
	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	
	public String estatNom(){
		if (this.activa) {
			return "Activa";
		}else{
			return "Tancada";
		}
	}

	public Date getDataTancament() {
		return dataTancament;
	}
	
	public String getDataTancamentString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataTancament != null) dataString = df.format(this.dataTancament);
		return dataString;
	}

	public void setDataTancament(Date dataTancament) {
		this.dataTancament = dataTancament;
	}

	public boolean isAprovada() {
		return aprovada;
	}

	public void setAprovada(boolean aprovada) {
		this.aprovada = aprovada;
	}

	public Date getDataAprovacio() {
		return dataAprovacio;
	}
	
	public String getDataAprovacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataAprovacio != null) dataString = df.format(this.dataAprovacio);
		return dataString;
	}

	public void setDataAprovacio(Date dataAprovacio) {
		this.dataAprovacio = dataAprovacio;
	}

	public int getIdInformePrevi() {
		return idInformePrevi;
	}

	public void setIdInformePrevi(int idInformePrevi) {
		this.idInformePrevi = idInformePrevi;
	}

	public int getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public Date getDarreraModificacio() {
		return darreraModificacio;
	}
	
	public String getDarreraModificacioString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.darreraModificacio != null) dataString = df.format(this.darreraModificacio);
		return dataString;		 
	}

	public void setDarreraModificacio(Date darreraModificacio) {
		this.darreraModificacio = darreraModificacio;
	}

	public String getModificacio() {
		return modificacio;
	}

	public void setModificacio(String modificacio) {
		this.modificacio = modificacio;
	}

	public Date getDataCreacio() {
		return this.dataCreacio;
	}

	public void setDataCreacio(Date creacio) {
		this.dataCreacio = creacio;
	}
	
	public String getDataCreacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataCreacio != null) dataString = df.format(this.dataCreacio);
		return dataString;		 
	}

	public int getIdUsuariCreacio() {
		return idUsuariCreacio;
	}

	public void setIdUsuariCreacio(int idUsuariCreacio) {
		this.idUsuariCreacio = idUsuariCreacio;
	}

	public String getNomCentre() {
		return nomCentre;
	}

	public void setNomCentre(String nomCentre) {
		this.nomCentre = nomCentre;
	}
}
