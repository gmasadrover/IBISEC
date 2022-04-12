package bean;

import java.util.Date;

public class AulesModulars {
	private InformeActuacio informe;
	private Date dataLimitContracte;
	private double importPrevist;
	private InformeActuacio informeAutoritzacio;
	private InformeActuacio noAutoritzada;
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
	public void setDataLimitContracte(Date dataLimitContracte) {
		this.dataLimitContracte = dataLimitContracte;
	}
	public double getImportPrevist() {
		return importPrevist;
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
}
