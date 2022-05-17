package bean;

import java.text.DecimalFormat;

public class Configuracio {
	private double importObraMajor;
	private double importServeiMajor;
	private double importSubministramentMajor;
	private String rutaBaseDocumentacio;
	private int idUsuariRecercaPresuposts;
	private int idUsuariFactures;	
	private int idUsuariCertificacions;	
	private int idUsuariOrdreInici;	
	private int idUsuariRedaccioContracte;	
	private int idUsuariActualitzarEmpresa;	
	private int idUsuariLlicencies;	
	private int idUsuariDron;
	
	public Configuracio() {
		
	}

	public double getImportObraMajor() {
		return importObraMajor;
	}

	public String getImportObraMajorString() {
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.importObraMajor) + '€';
	}
	
	public void setImportObraMajor(double importObraMajor) {
		this.importObraMajor = importObraMajor;
	}

	public double getImportServeiMajor() {
		return importServeiMajor;
	}
	
	public String getImportServeiMajorString() {
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.importServeiMajor) + '€';
	}

	public void setImportServeiMajor(double importServeiMajor) {
		this.importServeiMajor = importServeiMajor;
	}

	public double getImportSubministramentMajor() {
		return importSubministramentMajor;
	}
	
	public String getImportSubministramentMajorString() {
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.importSubministramentMajor) + '€';
	}

	public void setImportSubministramentMajor(double importSubministramentMajor) {
		this.importSubministramentMajor = importSubministramentMajor;
	}

	public String getRutaBaseDocumentacio() {
		return rutaBaseDocumentacio;
	}

	public void setRutaBaseDocumentacio(String rutaBaseDocumentacio) {
		this.rutaBaseDocumentacio = rutaBaseDocumentacio;
	}

	public int getIdUsuariRecercaPresuposts() {
		return idUsuariRecercaPresuposts;
	}

	public void setIdUsuariRecercaPresuposts(int idUsuariRecercaPresuposts) {
		this.idUsuariRecercaPresuposts = idUsuariRecercaPresuposts;
	}

	public int getIdUsuariFactures() {
		return idUsuariFactures;
	}

	public void setIdUsuariFactures(int idUsuariFactures) {
		this.idUsuariFactures = idUsuariFactures;
	}

	public int getIdUsuariCertificacions() {
		return idUsuariCertificacions;
	}

	public void setIdUsuariCertificacions(int idUsuariCertificacions) {
		this.idUsuariCertificacions = idUsuariCertificacions;
	}

	public int getIdUsuariOrdreInici() {
		return idUsuariOrdreInici;
	}

	public void setIdUsuariOrdreInici(int idUsuariOrdreInici) {
		this.idUsuariOrdreInici = idUsuariOrdreInici;
	}

	public int getIdUsuariRedaccioContracte() {
		return idUsuariRedaccioContracte;
	}

	public void setIdUsuariRedaccioContracte(int idUsuariRedaccioContracte) {
		this.idUsuariRedaccioContracte = idUsuariRedaccioContracte;
	}

	public int getIdUsuariActualitzarEmpresa() {
		return idUsuariActualitzarEmpresa;
	}

	public void setIdUsuariActualitzarEmpresa(int idUsuariActualitzarEmpresa) {
		this.idUsuariActualitzarEmpresa = idUsuariActualitzarEmpresa;
	}

	public int getIdUsuariLlicencies() {
		return idUsuariLlicencies;
	}

	public void setIdUsuariLlicencies(int idUsuariLlicencies) {
		this.idUsuariLlicencies = idUsuariLlicencies;
	}

	public int getIdUsuariDron() {
		return idUsuariDron;
	}

	public void setIdUsuariDron(int idUsuariDron) {
		this.idUsuariDron = idUsuariDron;
	}
	
}
