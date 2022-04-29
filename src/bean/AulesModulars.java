package bean;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AulesModulars {
	private InformeActuacio informe;
	private Date dataLimitContracte;
	private double importPrevist;
	private InformeActuacio informeAutoritzacio;
	private InformeActuacio noAutoritzada;
	private String darreraFactura;
	
	public AulesModulars() {
		 
	}
	public InformeActuacio getInforme() {
		return informe;
	}
	public void setInforme(InformeActuacio informe) {
		this.informe = informe;
	}
	public Date getDataLimitContracte() {
		return dataLimitContracte;
	}
	
	public String getDataLimitContracteString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataLimitContracte != null) dataString = df.format(this.dataLimitContracte);
		return dataString;	
	}
	
	public void setDataLimitContracte(Date dataLimitContracte) {
		this.dataLimitContracte = dataLimitContracte;
	}
	public double getImportPrevist() {
		return importPrevist;
	}
	
	public String getImportPrevistFormat(){
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.importPrevist) + '€';
	}
	
	public void setImportPrevist(double importPrevist) {
		this.importPrevist = importPrevist;
	}
	public InformeActuacio getInformeAutoritzacio() {
		return informeAutoritzacio;
	}
	public void setInformeAutoritzacio(InformeActuacio informeAutoritzacio) {
		this.informeAutoritzacio = informeAutoritzacio;
	}
	public InformeActuacio getNoAutoritzada() {
		return noAutoritzada;
	}
	public void setNoAutoritzada(InformeActuacio noAutoritzada) {
		this.noAutoritzada = noAutoritzada;
	}
	public String getDarreraFactura() {
		return darreraFactura;
	}
	public void setDarreraFactura(String darreraFactura) {
		this.darreraFactura = darreraFactura;
	}
}
