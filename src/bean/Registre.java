package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registre {
	
	private String id;
	private Date data;
	private String tipus;
	private String remDes;
	private String contingut;
	private String idIncidencia;
	private String idCentre;
	private String nomCentre;
	private int idUsuari;
	private Date usuMod;
	 
	public Registre() {
		 
	}
 
	public Registre(String id, Date data, String tipus, String remdes, String contingut, String idIncidencia, String idCentre, String nomCentre, int idUsuari, Date usuMod) {
		this.setId(id);
		this.setData(data);
		this.setTipus(tipus);
		this.setRemDes(remdes);
		this.setContingut(contingut);
		this.setIdIncidencia(idIncidencia);
		this.setIdCentre(idCentre);
		this.setNomCentre(nomCentre);
		this.setIdUsuari(idUsuari);
		this.setUsuMod(usuMod);		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(this.data);
	}
	
	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public String getRemDes() {
		return remDes;
	}

	public void setRemDes(String remDes) {
		this.remDes = remDes;
	}

	public String getContingut() {
		return contingut;
	}

	public void setContingut(String contingut) {
		this.contingut = contingut;
	}
	
	public int getIdUsuari() {
		return idUsuari;
	}

	public void setIdUsuari(int idUsuari) {
		this.idUsuari = idUsuari;
	}

	public Date getUsuMod() {
		return usuMod;
	}

	public void setUsuMod(Date usuMod) {
		this.usuMod = usuMod;
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

	public String getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	
}
