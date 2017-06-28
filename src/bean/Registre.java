package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Registre {
	
	private String id;
	private Date data;
	private String tipus;
	private String remDes;
	private String contingut;
	private String idIncidencies;
	private String idActuacions;
	private String idCentres;
	private String nomCentres;
	private int idUsuari;
	private Date usuMod;
	 
	public Registre() {
		 
	}
 
	public Registre(String id, Date data, String tipus, String remdes, String contingut, String idIncidencies, String idCentres, int idUsuari, Date usuMod) {
		this.setId(id);
		this.setData(data);
		this.setTipus(tipus);
		this.setRemDes(remdes);
		this.setContingut(contingut);
		this.setIdIncidencies(idIncidencies);
		this.setIdCentres(idCentres);
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

	public String getIdIncidencies() {
		return idIncidencies;
	}

	public void setIdIncidencies(String idIncidencies) {
		this.idIncidencies = idIncidencies;
	}
	
	public String getIdActuacionss() {
		return idActuacions;
	}
	
	public void setIdActuacions(String idActuacions) {
		this.idActuacions = idActuacions;
	}

	public String getIdCentres() {
		return idCentres;
	}

	public void setIdCentres(String idCentres) {
		this.idCentres = idCentres;
	}

	public String getNomCentres() {
		return nomCentres;
	}

	public void setNomCentres(String nomCentres) {
		this.nomCentres = nomCentres;
	}

	public List<String> getIdActuacionsList() {
		List<String> list = new ArrayList<String>();
		if (this.idActuacions != null && !this.idActuacions.isEmpty()) {
			for (String idActuacions: this.idActuacions.split("#")) {
				list.add(idActuacions);
			}
		}
		return list;
	}
	
	public List<String> getIdIncidenciesList() {
		List<String> list = new ArrayList<String>();
		if (this.idIncidencies != null && !this.idIncidencies.isEmpty()) {
			for (String idIncidencia: this.idIncidencies.split("#")) {
				list.add(idIncidencia);
			}
		}
		return list;
	}
	
	public String getIdIncidenciesString(){
		String list = "";
		for (String incidencia: this.idIncidencies.split("#")) {
			list += incidencia + "</br>";
		}
		return list;
	}

	public String getPrimeraIncidencia() {
		String idIncidencia = "";
		if (!this.getIdIncidenciesList().isEmpty()){
			idIncidencia = this.getIdIncidenciesList().get(0);
		}else{
			idIncidencia = "-1";
		}
		return idIncidencia;
	}
	
	public List<String> getIdCentresList() {
		List<String> list = new ArrayList<String>();
		for (String idCentre: this.idCentres.split("#")) {
			list.add(idCentre);
		}
		return list;
	}

	public List<String> getNomCentresList() {
		List<String> list = new ArrayList<String>();
		if (this.nomCentres != null) {
			for (String nomCentre: this.nomCentres.split("#")) {
				list.add(nomCentre);
			}
		}
		return list;
	}
	
	public String getNomCentresString(){
		String list = "";
		if (this.nomCentres != null && !this.nomCentres.isEmpty()) {
			for (String nomCentre: this.nomCentres.split("#")) {
				list += nomCentre + "</br>";
			}
		}
		return list;
	}
	
}
