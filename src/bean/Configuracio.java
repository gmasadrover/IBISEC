package bean;

import java.text.DecimalFormat;

public class Configuracio {
	private double importObraMajor;
	private double importServeiMajor;
	private double importSubministramentMajor;
	private String rutaBaseDocumentacio;
	
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
	
}
