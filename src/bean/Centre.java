package bean;

public class Centre {
	private String idCentre;
	private String nom;
	
	public Centre() {
		 
	}

	public Centre(String idCentre, String nom) {		 
		this.idCentre = idCentre;
		this.nom = nom;		 
	}
	
	public String getIdCentre() {
		return idCentre;
	}
	public void setIdCentre(String idCentre) {
		this.idCentre = idCentre;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
}
