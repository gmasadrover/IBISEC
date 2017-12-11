package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.Fitxers.Fitxer;

public class Judicial {
	public class Tramitacio {
		private String numstcia;
		private Date data;
		private double quantia;
		private String recurs;
		private Date datapagament;
		private String sentencia;
		private String tipus;
		private String notes;
		private int idTramitacio;
		private String termini;
		private List<Fitxer> documentsList;
		public Tramitacio() {
			
		}
		public String getNumstcia() {
			return numstcia;
		}
		public void setNumstcia(String numstcia) {
			this.numstcia = numstcia;
		}
		public Date getData() {
			return data;
		}
		public String getDataString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.data != null) dataString = df.format(this.data);
			return dataString;
		}
		public void setData(Date data) {
			this.data = data;
		}
		public double getQuantia() {
			return quantia;
		}
		public void setQuantia(double quantia) {
			this.quantia = quantia;
		}
		public String getRecurs() {
			return recurs;
		}
		public void setRecurs(String recurs) {
			this.recurs = recurs;
		}
		public Date getDatapagament() {
			return datapagament;
		}
		public String getDataPagamentString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.datapagament != null) dataString = df.format(this.datapagament);
			return dataString;
		}
		public void setDatapagament(Date datapagament) {
			this.datapagament = datapagament;
		}
		public String getSentencia() {
			return sentencia;
		}
		public void setSentencia(String sentencia) {
			this.sentencia = sentencia;
		}
		public String getTipus() {
			return tipus;
		}
		public String getTipusFormat() {
			if ("dipso".equals(this.tipus)) return "Disposició Judicial";
			if ("propis".equals(this.tipus)) return "Propis IBISEC";
			if ("advoc".equals(this.tipus)) return "Advocacia";
			if ("altres".equals(this.tipus)) return "Altres";
			return "";
		}
		public void setTipus(String tipus) {
			this.tipus = tipus;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
		public int getIdTramitacio() {
			return idTramitacio;
		}
		public void setIdTramitacio(int idTramitacio) {
			this.idTramitacio = idTramitacio;
		}
		public List<Fitxer> getDocumentsList() {
			return documentsList;
		}
		public void setDocumentsList(List<Fitxer> documentsList) {
			this.documentsList = documentsList;
		}
		public String getTermini() {
			return termini;
		}
		public void setTermini(String termini) {
			this.termini = termini;
		}
		
	}
	private String referencia;
	private String jutjat;
	private String numAutos;
	private String demandant;
	private String demandat;
	private String objecteDemanda;
	private String quantia;
	private String estat;
	private String notes;
	private String anyProcediment;
	private List<Tramitacio> tramitacionsList;
	private List<Fitxer> documentsIniciList;
	private List<Fitxer> documentsComunicacioList;
	public Judicial() {
		 
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getJutjat() {
		return jutjat;
	}
	public void setJutjat(String jutjat) {
		this.jutjat = jutjat;
	}
	public String getNumAutos() {
		return numAutos;
	}
	public void setNumAutos(String numAutos) {
		this.numAutos = numAutos;
	}
	public String getDemandat() {
		return demandat;
	}
	public void setDemandat(String demandat) {
		this.demandat = demandat;
	}
	public String getDemandant() {
		return demandant;
	}
	public void setDemandant(String demandant) {
		this.demandant = demandant;
	}
	public String getObjecteDemanda() {
		return objecteDemanda;
	}
	public void setObjecteDemanda(String objecteDemanda) {
		this.objecteDemanda = objecteDemanda;
	}
	public String getQuantia() {
		return quantia;
	}
	public void setQuantia(String quantia) {
		this.quantia = quantia;
	}
	public String getEstat() {
		return estat;
	}
	public void setEstat(String estat) {
		this.estat = estat;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<Tramitacio> getTramitacionsList() {
		return tramitacionsList;
	}
	public void setTramitacionsList(List<Tramitacio> tramitacionsList) {
		this.tramitacionsList = tramitacionsList;
	}
	public String getAnyProcediment() {
		return anyProcediment;
	}
	public void setAnyProcediment(String anyProcediment) {
		this.anyProcediment = anyProcediment;
	}
	public List<Fitxer> getDocumentsIniciList() {
		return documentsIniciList;
	}
	public void setDocumentsIniciList(List<Fitxer> documentsIniciList) {
		this.documentsIniciList = documentsIniciList;
	}
	public List<Fitxer> getDocumentsComunicacioList() {
		return documentsComunicacioList;
	}
	public void setDocumentsComunicacioList(List<Fitxer> documentsComunicacioList) {
		this.documentsComunicacioList = documentsComunicacioList;
	}
}
