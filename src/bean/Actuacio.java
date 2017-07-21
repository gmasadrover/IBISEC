package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.Fitxers;

public class Actuacio {
	 public class Feina {
		 private String idFeina;
		 private String nomRemitent;
		 private String nomDestinatari;
		 private Date data;
		 private String contingut;
		 private String notes;
		 
		 public Feina() {
			 
		 }

		public String getIdFeina() {
			return idFeina;
		}

		public void setIdFeina(String idFeina) {
			this.idFeina = idFeina;
		}

		public String getNomRemitent() {
			return nomRemitent;
		}

		public void setNomRemitent(String nomRemitent) {
			this.nomRemitent = nomRemitent;
		}

		public String getNomDestinatari() {
			return nomDestinatari;
		}

		public void setNomDestinatari(String nomDestinatari) {
			this.nomDestinatari = nomDestinatari;
		}

		public Date getData() {
			return data;
		}
		
		public String getDataString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.data != null) dataString = df.format(this.data);
			return dataString;
		}

		public void setData(Date data) {
			this.data = data;
		}

		public String getContingut() {
			return contingut;
		}

		public void setContingut(String contingut) {
			this.contingut = contingut;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}
	 }
	
	 private String referencia;
	 private String descripcio;
	 private String notes;
	 private Date dataCreacio;
	 private int idUsuariCreacio;
	 private String idCentre;	
	 private String nomCentre;
	 private Date dataTancament;
	 private Date dataAprovacio;
	 private InformeActuacio informePrevi;
	 private String idIncidencia;
	 private Date darreraModificacio;
	 private String modificacio;
	 private Date dataAprovarPa;
	 private String refExt;
	 private List<Fitxers.Fitxer> arxiusAdjunts = new ArrayList<Fitxers.Fitxer>();
	 private List<Feina> Feines = new ArrayList<Feina>();
	 private boolean seguiment;
	 
	 public Actuacio() {
 
	 }
 
	 public String getReferencia() {
		 return referencia;
	 }
 
	 public void setReferencia(String referencia) {
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
		return this.dataTancament == null;
	}
	
	public String estatNom(){
		if (this.dataTancament == null) {
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
		return this.dataAprovacio != null;
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

	public String getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
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

	public InformeActuacio getInformePrevi() {
		return informePrevi;
	}

	public void setInformePrevi(InformeActuacio informePrevi) {
		this.informePrevi = informePrevi;
	}

	public boolean isPaAprovada() {
		return this.dataAprovarPa != null;
	}
	
	public Date getDataAprovarPa() {
		return dataAprovarPa;
	}

	public String getDataAprovarPaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataAprovarPa != null) dataString = df.format(this.dataAprovarPa);
		return dataString;
	}
	
	public void setDataAprovarPa(Date dataAprovarPa) {
		this.dataAprovarPa = dataAprovarPa;
	}

	public List<Fitxers.Fitxer> getArxiusAdjunts() {
		return arxiusAdjunts;
	}

	public void setArxiusAdjunts(List<Fitxers.Fitxer> arxiusAdjunts) {
		this.arxiusAdjunts = arxiusAdjunts;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getRefExt() {
		return refExt;
	}

	public void setRefExt(String refExt) {
		this.refExt = refExt;
	}

	public List<Feina> getFeines() {
		return Feines;
	}

	public void setFeines(List<Feina> feines) {
		Feines = feines;
	}
	
	public String getEstat() {
		return "";
	}

	public boolean isSeguiment() {
		return seguiment;
	}

	public void setSeguiment(boolean seguiment) {
		this.seguiment = seguiment;
	}
}
