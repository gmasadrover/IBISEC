package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bastanteo {
	private String ref;
	private Date databastanteo;
	private Empresa empresa;
	private String escritura;
	private String personaFacultada;
	private String carrec;
	private Date dataEscritura;
	private String numProtocol;
	private String notari;
	private String procedencia;
	private String desti;
	private String anyBastanteo;
	public Bastanteo() {
		 
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public Date getDatabastanteo() {
		return databastanteo;
	}
	public String getDatabastanteoString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.databastanteo != null) dataString = df.format(this.databastanteo);
		return dataString;
	}
	public void setDatabastanteo(Date databastanteo) {
		this.databastanteo = databastanteo;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getEscritura() {
		return escritura;
	}
	public void setEscritura(String escritura) {
		this.escritura = escritura;
	}
	public String getPersonaFacultada() {
		return personaFacultada;
	}
	public void setPersonaFacultada(String personaFacultada) {
		this.personaFacultada = personaFacultada;
	}
	public String getCarrec() {
		return carrec;
	}
	public void setCarrec(String carrec) {
		this.carrec = carrec;
	}
	public Date getDataEscritura() {
		return dataEscritura;
	}
	public String getDataEscrituraString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataEscritura != null) dataString = df.format(this.dataEscritura);
		return dataString;
	}
	public void setDataEscritura(Date dataEscritura) {
		this.dataEscritura = dataEscritura;
	}
	public String getNumProtocol() {
		return numProtocol;
	}
	public void setNumProtocol(String numProtocol) {
		this.numProtocol = numProtocol;
	}
	public String getNotari() {
		return notari;
	}
	public void setNotari(String notari) {
		this.notari = notari;
	}
	public String getProcedencia() {
		return procedencia;
	}
	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}
	public String getDesti() {
		return desti;
	}
	public void setDesti(String desti) {
		this.desti = desti;
	}
	public String getAnyBastanteo() {
		return anyBastanteo;
	}
	public void setAnyBastanteo(String anyBastanteo) {
		this.anyBastanteo = anyBastanteo;
	}
}
