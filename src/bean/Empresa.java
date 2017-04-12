package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import utils.Fitxers;

public class Empresa {
 
   public class Classificacio {
	   private String grup;
	   private String subGrup;
	   private String categoria;
	public String getGrup() {
		return grup;
	}
	public void setGrup(String grup) {
		this.grup = grup;
	}
	public String getSubGrup() {
		return subGrup;
	}
	public void setSubGrup(String subGrup) {
		this.subGrup = subGrup;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
   }
	
   public class Administrador{
	   private String nom;
	   private String dni;
	   private Date dataValidesaFins;
	   private Date dataModificacio;
	   private String notariModificacio;
	   private int protocolModificacio;
	   private Date dataValidacio;
	   private String entitatValidacio;
	   private String tipus;
	   private boolean eliminar;
	   
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public String getDni() {
			return dni;
		}
		public void setDni(String dni) {
			this.dni = dni;
		}
		public Date getDataValidesaFins() {
			return dataValidesaFins;
		}
		public String getDataValidesaFinsString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.dataValidesaFins != null) dataString = df.format(this.dataValidesaFins);
			return dataString;
		}
		public void setDataValidesaFins(Date dataValidesaFins) {
			this.dataValidesaFins = dataValidesaFins;
		}
		
		public boolean isCaducat(){
			boolean caducat = false;
			Calendar cad = Calendar.getInstance();
			cad.setTime(this.dataValidesaFins);			
			if (cad.before(Calendar.getInstance())) caducat = true;
			return caducat;
		}
		
		public Date getDataModificacio() {
			return dataModificacio;
		}
		public String getDataModificacioString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.dataModificacio != null) dataString = df.format(this.dataModificacio);
			return dataString;
		}
		public void setDataModificacio(Date dataModificacio) {
			this.dataModificacio = dataModificacio;
		}
		public String getNotariModificacio() {
			return notariModificacio;
		}
		public void setNotariModificacio(String notariModificacio) {
			this.notariModificacio = notariModificacio;
		}
		public int getProtocolModificacio() {
			return protocolModificacio;
		}
		public void setProtocolModificacio(int protocolModificacio) {
			this.protocolModificacio = protocolModificacio;
		}
		public String getTipus() {
			return tipus;
		}
		public void setTipus(String tipus) {
			this.tipus = tipus;
		}
		public Date getDataValidacio() {
			return dataValidacio;
		}
		public void setDataValidacio(Date dataValidacio) {
			this.dataValidacio = dataValidacio;
		}
		public String getDataValidacioString(){
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.dataValidacio != null) dataString = df.format(this.dataValidacio);
			return dataString;
		}
		public boolean isEliminar() {
			return eliminar;
		}
		public void setEliminar(boolean eliminar) {
			this.eliminar = eliminar;
		}
		public String getEntitatValidacio() {
			return entitatValidacio;
		}
		public void setEntitatValidacio(String entitatValidacio) {
			this.entitatValidacio = entitatValidacio;
		}
   }
   
   private String cif;
   private String name;
   private String direccio;
   private String cp;
   private String ciutat;
   private String provincia;
   private String telefon;
   private String fax;
   private String email;
   private Date dataConstitucio;
   private String objecteSocial;
   private String classificacioString;
   private String administradorsString;
   private List<Administrador> administradors;   
   private boolean acreditacio1;
   private Date dateExpAcreditacio1;
   private boolean acreditacio2;
   private Date dateExpAcreditacio2;
   private boolean acreditacio3;
   private Date dateExpAcreditacio3;
   private Fitxers.Fitxer solEconomica;
   private Date exerciciEconomic;
   private Date registreMercantilData;
   private Fitxers.Fitxer solTecnica;
   private double ratioAP;
   private String informacioAdicional;
   
   
   public Empresa() {
 
   }
 
   public String getCif() {
       return cif;
   }
 
   public void setCif(String cif) {
       this.cif = cif;
   }
 
   public String getName() {
       return name;
   }
 
   public void setName(String name) {
       this.name = name;
   }
   
   public String getDireccio() {
       return direccio;
   }
 
   public void setDireccio(String direccio) {
       this.direccio = direccio;
   }
   
   public String getCP() {
       return cp;
   }
 
   public void setCP(String cp) {
       this.cp = cp;
   }
   
   public String getCiutat() {
       return ciutat;
   }
 
   public void setCiutat(String ciutat) {
       this.ciutat = ciutat;
   }
   
   public String getProvincia() {
       return provincia;
   }
 
   public void setProvincia(String provincia) {
       this.provincia = provincia;
   }
   
   public String getTelefon() {
       return telefon;
   }
 
   public void setTelefon(String telefon) {
       this.telefon = telefon;
   }
   
   public String getEmail() {
       return email;
   }
 
   public void setEmail(String email) {
       this.email = email;
   }

public String getFax() {
	return fax;
}

public void setFax(String fax) {
	this.fax = fax;
}

public Date getDataConstitucio() {
	return dataConstitucio;
}

public String getDataConstitucioString() {
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
	String dataString = "";
	if (this.dataConstitucio != null) dataString = df.format(this.dataConstitucio);
	return dataString;
}

public void setDataConstitucio(Date dataConstitucio) {
	this.dataConstitucio = dataConstitucio;
}

public String getObjecteSocial() {
	return objecteSocial;
}

public void setObjecteSocial(String objecteSocial) {
	this.objecteSocial = objecteSocial;
}

public List<Classificacio> getClassificacio() {
	List<Empresa.Classificacio> classificacioList = new ArrayList<Empresa.Classificacio>();
	if (this.getClassificacioString() != null && !this.getClassificacioString().isEmpty()) {
		String[] classificacioString = this.getClassificacioString().split(";");
		Empresa.Classificacio classificacio = new Classificacio();
		for (String element : classificacioString) {
			classificacio = new Classificacio();
		    classificacio.setGrup(element.split("#")[0]);
		    classificacio.setSubGrup(element.split("#")[1]);
		    classificacio.setCategoria(element.split("#")[2]);
		    classificacioList.add(classificacio);
		}
	}
	return classificacioList;
}

public List<Administrador> getAdministradors() {
	return administradors;
}

public void setAdministradors(List<Administrador> administradors) {
	this.administradors = administradors;
}

public boolean isAcreditacio1() {
	return acreditacio1;
}

public void setAcreditacio1(boolean acreditacio1) {
	this.acreditacio1 = acreditacio1;
}

public Date getDateExpAcreditacio1() {
	return dateExpAcreditacio1;
}

public String getDateExpAcreditacio1String() {
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
	String dataString = "";
	if (this.dateExpAcreditacio1 != null) dataString = df.format(this.dateExpAcreditacio1);
	return dataString;
}

public void setDateExpAcreditacio1(Date dateExpAcreditacio1) {
	this.dateExpAcreditacio1 = dateExpAcreditacio1;
}

public boolean isCaducadaAcreditacio1() {
	boolean caducat = false;
	if (this.dateExpAcreditacio1 != null) {
		Calendar cad = Calendar.getInstance();
		cad.setTime(this.dateExpAcreditacio1);
		cad.add(Calendar.MONTH, 6);
		if (cad.before(Calendar.getInstance())) caducat = true;
	}
	return caducat;
}

public boolean isAcreditacio2() {
	return acreditacio2;
}

public void setAcreditacio2(boolean acreditacio2) {
	this.acreditacio2 = acreditacio2;
}

public Date getDateExpAcreditacio2() {
	return dateExpAcreditacio2;
}

public String getDateExpAcreditacio2String() {
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
	String dataString = "";
	if (this.dateExpAcreditacio2 != null) dataString = df.format(this.dateExpAcreditacio2);
	return dataString;
}

public void setDateExpAcreditacio2(Date dateExpAcreditacio2) {
	this.dateExpAcreditacio2 = dateExpAcreditacio2;
}

public boolean isCaducadaAcreditacio2() {
	boolean caducat = false;
	if (this.dateExpAcreditacio2 != null) {
		Calendar cad = Calendar.getInstance();
		cad.setTime(this.dateExpAcreditacio2);
		cad.add(Calendar.MONTH, 6);
		if (cad.before(Calendar.getInstance())) caducat = true;
	}
	return caducat;
}

public boolean isAcreditacio3() {
	return acreditacio3;
}

public void setAcreditacio3(boolean acreditacio3) {
	this.acreditacio3 = acreditacio3;
}

public Date getDateExpAcreditacio3() {
	return dateExpAcreditacio3;
}

public String getDateExpAcreditacio3String() {
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
	String dataString = "";
	if (this.dateExpAcreditacio3 != null) dataString = df.format(this.dateExpAcreditacio3);
	return dataString;
}

public void setDateExpAcreditacio3(Date dateExpAcreditacio3) {
	this.dateExpAcreditacio3 = dateExpAcreditacio3;
}

public boolean isCaducadaAcreditacio3() {
	boolean caducat = false;
	if (this.dateExpAcreditacio3 != null) {
		Calendar cad = Calendar.getInstance();
		cad.setTime(this.dateExpAcreditacio3);
		cad.add(Calendar.MONTH, 6);
		if (cad.before(Calendar.getInstance())) caducat = true;
	}
	return caducat;
}

public String getClassificacioString() {
	return classificacioString;
}

public void setClassificacioString(String getClassificacioString) {
	this.classificacioString = getClassificacioString;
}

public Fitxers.Fitxer getSolEconomica() {
	return solEconomica;
}

public void setSolEconomica(Fitxers.Fitxer solEconomica) {
	this.solEconomica = solEconomica;
}

public Fitxers.Fitxer getSolTecnica() {
	return solTecnica;
}

public void setSolTecnica(Fitxers.Fitxer solTecnica) {
	this.solTecnica = solTecnica;
}

public String getAdministradorsString() {
	return administradorsString;
}

public void setAdministradorsString(String administradorsString) {
	this.administradorsString = administradorsString;
}

public String getInformacioAdicional() {
	return informacioAdicional;
}

public void setInformacioAdicional(String informacioAdicional) {
	this.informacioAdicional = informacioAdicional;
}

public Date getExerciciEconomic() {
	return exerciciEconomic;
}

public String getExerciciEconomicString() {
	DateFormat df = new SimpleDateFormat("yyyy");	
	String dataString = "";
	if (this.exerciciEconomic != null) dataString = df.format(this.exerciciEconomic);
	return dataString;
}

public void setExerciciEconomic(Date exerciciEconomic) {
	this.exerciciEconomic = exerciciEconomic;
}

public Date getRegistreMercantilData() {
	return registreMercantilData;
}

public String getRegistreMercantilDataString() {
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
	String dataString = "";
	if (this.registreMercantilData != null) dataString = df.format(this.registreMercantilData);
	return dataString;
}

public void setRegistreMercantilData(Date registreMercantilData) {
	this.registreMercantilData = registreMercantilData;
}

public double getRatioAP() {
	return ratioAP;
}

public void setRatioAP(double ratioAP) {
	this.ratioAP = ratioAP;
}
 
}