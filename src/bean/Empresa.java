package bean;
public class Empresa {
 
   private String cif;
   private String name;
   private String direccio;
   private String cp;
   private String ciutat;
   private String provincia;
   private String telefon;
   private String fax;
   private String email;
 
   public Empresa() {
 
   }
 
   public Empresa(String code, String name, String direccio, String cp, String ciutat, String provincia, String telefon, String fax, String email) {
       this.cif = code;
       this.name = name;
       this.direccio = direccio;
       this.cp = cp;
       this.ciutat = ciutat;
       this.provincia = provincia;
       this.telefon = telefon;
       this.setFax(fax);
       this.email = email;
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
 
}