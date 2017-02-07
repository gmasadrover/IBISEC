package bean;

public class User {
	private int idUsuari;
	private String usuari;
	private String name;
	private String llinatges;
	private String rol;
	private String password;
	private String carreg;
	private String departament;
	 
	   public User() {
	 
	   }

	   public void setIdUsuari(int idUsuari){
		   this.idUsuari = idUsuari;
	   }
	   
	   public int getIdUsuari(){
		   return idUsuari;
	   }
	   
	   public String getName() {
	       return name;
	   }
	 
	   public void setName(String name) {
	       this.name = name;
	   }
	   
	   public String getLlinatges() {
	       return llinatges;
	   }
	 
	   public void setLlinatges(String llinatges) {
	       this.llinatges = llinatges;
	   }
	   
	   public String getNomComplet() {
		   return name + " " + llinatges;
	   }
	   
	   public String getRol() {
	       return rol;
	   }
	 
	   public void setRol(String rol) {
	       this.rol = rol;
	   }
	   
	   public String getPassword() {
	       return password;
	   }
	 
	   public void setPassword(String password) {
	       this.password = password;
	   }
	   
	   public String getCarreg() {
	       return carreg;
	   }
	 
	   public void setCarreg(String carreg) {
	       this.carreg = carreg;
	   }

	public String getUsuari() {
		return usuari;
	}

	public void setUsuari(String usuari) {
		this.usuari = usuari;
	}

	public String getDepartament() {
		return departament;
	}

	public void setDepartament(String departament) {
		this.departament = departament;
	}
}
