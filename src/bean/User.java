package bean;

public class User {
	public enum RolUsuari{
		rol_cap, rol_arq, rol_bas, rol_jur, rol_eny, rol_admin, rol_adm, rol_tec 
	}
	private int idUsuari;
	private String usuari;
	private String name;
	private String llinatges;
	private String rol;
	private String password;
	private String carreg;
	 
	   public User() {
	 
	   }
	 
	   public User(int idUsuari, String name, String llinatges, String rol, String password, String carreg) {
		   this.idUsuari = idUsuari;
	       this.name = name;
	       this.llinatges = llinatges;
	       this.rol = rol;
	       this.password = password;
	       this.carreg = carreg;
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
}
