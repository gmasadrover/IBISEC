package bean;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class InformeActuacio {
	
	public class PropostaInforme {
		private String idProposta;
		private String objecte;
		private String tipusObra;
		private boolean llicencia;
		private String tipusLlicencia;
		private boolean contracte;
		private double vec;
		private double iva;
		private double plic;
		private String termini;
		private String comentari;
		private boolean seleccionada;
				
		public PropostaInforme() {
			
		}
		
		public String getObjecte() {
			return objecte;
		}

		public void setObjecte(String objecte) {
			this.objecte = objecte;
		}

		public String getTipusObra() {
			return tipusObra;
		}
		
		public String getTipusObraFormat() {
			if ("obr".equals(this.tipusObra)) return "Obra";
			if ("srv".equals(this.tipusObra)) return "Servei";
			if ("submi".equals(this.tipusObra)) return "Subministrament";
			return "";
		}

		public void setTipusObra(String tipusObra) {
			this.tipusObra = tipusObra;
		}

		public boolean isLlicencia() {
			return llicencia;
		}

		public void setLlicencia(boolean llicencia) {
			this.llicencia = llicencia;
		}

		public String getTipusLlicencia() {
			return tipusLlicencia;
		}

		public void setTipusLlicencia(String tipusLlicencia) {
			this.tipusLlicencia = tipusLlicencia;
		}

		public boolean isContracte() {
			return contracte;
		}

		public void setContracte(boolean contracte) {
			this.contracte = contracte;
		}

		public double getVec() {
			return vec;
		}
		
		public String getVecFormat(){
			DecimalFormat num = new DecimalFormat("#,##0.00");
		    return num.format(this.vec) + '€';
		}

		public void setVec(double vec) {
			this.vec = vec;
		}

		public double getIva() {
			return iva;
		}
		
		public String getIvaFormat(){
			DecimalFormat num = new DecimalFormat("#,##0.00");
		    return num.format(this.iva) + '€';
		}

		public void setIva(double iva) {
			this.iva = iva;
		}

		public double getPlic() {
			return plic;
		}

		public String getPlicFormat(){
			DecimalFormat num = new DecimalFormat("#,##0.00");
		    return num.format(this.plic) + '€';
		}
		
		public void setPlic(double plic) {
			this.plic = plic;
		}

		public String getTermini() {
			return termini;
		}

		public void setTermini(String termini) {
			this.termini = termini;
		}

		public String getComentari() {
			return comentari;
		}

		public void setComentari(String comentari) {
			this.comentari = comentari;
		}

		public String getIdProposta() {
			return idProposta;
		}

		public void setIdProposta(String idProposta) {
			this.idProposta = idProposta;
		}

		public boolean isSeleccionada() {
			return seleccionada;
		}

		public void setSeleccionada(boolean seleccionada) {
			this.seleccionada = seleccionada;
		}
		
	}
	
	private String idInf;
	private int idTasca;
	private String idActuacio;
	private String idIncidencia;	
	private List<Fitxers.Fitxer> adjunts; 
	private List<PropostaInforme> llistaPropostes;
	private PropostaInforme propostaInformeSeleccionada;
	private User usuari;
	private Date dataCreacio;
	private String partidaRebutjadaMotiu;
	private Date dataRebujada;
	private String partida;
	private String comentariPartida;
	private User usuariCapValidacio;
	private Date dataCapValidacio;
	private String comentariCap;
	private User usuariAprovacio;
	private Date dataAprovacio;
	private List<Oferta> llistaOfertes;
	private Oferta ofertaSeleccionada;
	private List<Factura> llistaFactures;
	private Date dataTancament;
	private String notes;
	private String tipo;
	private String expcontratacio;
	private Date dataPD;
	private String tipoPD;
	private Fitxer propostaActuacio;
	private Fitxer vistiplauPropostaActuacio;
	private Fitxer conformeAreaEconomivaPropostaActuacio;
	private Fitxer autoritzacioPropostaAutoritzacio;
	private Fitxer propostaTecnica;
	private Fitxer autoritzacioPropostaDespesa;
	
	public InformeActuacio() {	
		this.llistaPropostes = new ArrayList<PropostaInforme>();
	}

	public String getIdInf() {
		return idInf;
	}

	public void setIdInf(String idInf) {
		this.idInf = idInf;
	}

	public int getIdTasca() {
		return idTasca;
	}

	public void setIdTasca(int idTasca) {
		this.idTasca = idTasca;
	}

	public String getIdActuacio() {
		return idActuacio;
	}

	public void setIdActuacio(String idActuacio) {
		this.idActuacio = idActuacio;
	}

	

	public List<Fitxers.Fitxer> getAdjunts() {
		return adjunts;
	}

	public void setAdjunts(List<Fitxers.Fitxer> adjunts) {
		this.adjunts = adjunts;
	}

	public User getUsuari() {
		return usuari;
	}

	public void setUsuari(User usuari) {
		this.usuari = usuari;
	}

	public Date getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(Date dataCreacio) {
		this.dataCreacio = dataCreacio;
	}
	
	public String getDataCreacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataCreacio != null) dataString = df.format(this.dataCreacio);
		return dataString;
	}

	public String getPartida() {
		return partida;
	}

	public void setPartida(String partida) {
		this.partida = partida;
	}

	public String getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public User getUsuariAprovacio() {
		return usuariAprovacio;
	}

	public void setUsuariAprovacio(User usuariAprovacio) {
		this.usuariAprovacio = usuariAprovacio;
	}

	public Date getDataAprovacio() {
		return dataAprovacio;
	}

	public String getDataAprovacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataAprovacio != null) dataString = df.format(this.dataAprovacio);
		return dataString;
	}
	
	public void setDataAprovacio(Date dataAprovacio) {
		this.dataAprovacio = dataAprovacio;
	}

	public String getComentariCap() {
		return comentariCap;
	}

	public void setComentariCap(String comentariCap) {
		this.comentariCap = comentariCap;
	}

	public List<Oferta> getLlistaOfertes() {
		return llistaOfertes;
	}

	public void setLlistaOfertes(List<Oferta> llistaOfertes) {
		this.llistaOfertes = llistaOfertes;
	}

	public Oferta getOfertaSeleccionada() {
		return ofertaSeleccionada;
	}

	public void setOfertaSeleccionada(Oferta ofertaSeleccionada) {
		this.ofertaSeleccionada = ofertaSeleccionada;
	}

	public User getUsuariCapValidacio() {
		return usuariCapValidacio;
	}
	
	public void setUsuariCapValidacio(User usuariCapValidacio) {
		this.usuariCapValidacio = usuariCapValidacio;
	}

	public Date getDataCapValidacio() {
		return dataCapValidacio;
	}
	
	public String getDataCapValidacioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataCapValidacio != null) dataString = df.format(this.dataCapValidacio);
		return dataString;
	}

	public void setDataCapValidacio(Date dataCapValidacio) {
		this.dataCapValidacio = dataCapValidacio;
	}

	public List<Factura> getLlistaFactures() {
		return llistaFactures;
	}

	public void setLlistaFactures(List<Factura> llistaFactures) {
		this.llistaFactures = llistaFactures;
	}

	public Date getDataTancament() {
		return dataTancament;
	}

	public void setDataTancament(Date dataTancament) {
		this.dataTancament = dataTancament;
	}
	
	public String getDataTancamentString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataTancament != null) dataString = df.format(this.dataTancament);
		return dataString;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<PropostaInforme> getLlistaPropostes() {
		return llistaPropostes;
	}

	public void setLlistaPropostes(List<PropostaInforme> llistaPropostes) {
		this.llistaPropostes = llistaPropostes;
	}

	public PropostaInforme getPropostaInformeSeleccionada() {
		return propostaInformeSeleccionada;
	}

	public void setPropostaInformeSeleccionada(PropostaInforme propostaInformeSeleccionada) {
		this.propostaInformeSeleccionada = propostaInformeSeleccionada;
	}

	public String getPartidaRebutjadaMotiu() {
		return partidaRebutjadaMotiu;
	}

	public void setPartidaRebutjadaMotiu(String partidaRebutjadaMotiu) {
		this.partidaRebutjadaMotiu = partidaRebutjadaMotiu;
	}

	public Date getDataRebujada() {
		return dataRebujada;
	}

	public void setDataRebujada(Date dataRebujada) {
		this.dataRebujada = dataRebujada;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getExpcontratacio() {
		return expcontratacio;
	}

	public void setExpcontratacio(String expcontratacio) {
		this.expcontratacio = expcontratacio;
	}

	public Date getDataPD() {
		return dataPD;
	}
	
	public String getDataPDString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataPD != null) dataString = df.format(this.dataPD);
		return dataString;
	}

	public void setDataPD(Date dataPD) {
		this.dataPD = dataPD;
	}

	public String getTipoPD() {
		return tipoPD;
	}

	public void setTipoPD(String tipoPD) {
		this.tipoPD = tipoPD;
	}

	public String getComentariPartida() {
		return comentariPartida;
	}

	public void setComentariPartida(String comentariPartida) {
		this.comentariPartida = comentariPartida;
	}

	public Fitxer getPropostaActuacio() {
		return propostaActuacio;
	}

	public void setPropostaActuacio(Fitxer propostaActuacio) {
		this.propostaActuacio = propostaActuacio;
	}

	public Fitxer getVistiplauPropostaActuacio() {
		return vistiplauPropostaActuacio;
	}

	public void setVistiplauPropostaActuacio(Fitxer vistiplauPropostaActuacio) {
		this.vistiplauPropostaActuacio = vistiplauPropostaActuacio;
	}

	public Fitxer getConformeAreaEconomivaPropostaActuacio() {
		return conformeAreaEconomivaPropostaActuacio;
	}

	public void setConformeAreaEconomivaPropostaActuacio(Fitxer conformeAreaEconomivaPropostaActuacio) {
		this.conformeAreaEconomivaPropostaActuacio = conformeAreaEconomivaPropostaActuacio;
	}

	public Fitxer getAutoritzacioPropostaAutoritzacio() {
		return autoritzacioPropostaAutoritzacio;
	}

	public void setAutoritzacioPropostaAutoritzacio(Fitxer autoritzacioPropostaAutoritzacio) {
		this.autoritzacioPropostaAutoritzacio = autoritzacioPropostaAutoritzacio;
	}

	public Fitxer getPropostaTecnica() {
		return propostaTecnica;
	}

	public void setPropostaTecnica(Fitxer propostaTecnica) {
		this.propostaTecnica = propostaTecnica;
	}

	public Fitxer getAutoritzacioPropostaDespesa() {
		return autoritzacioPropostaDespesa;
	}

	public void setAutoritzacioPropostaDespesa(Fitxer autoritzacioPropostaDespesa) {
		this.autoritzacioPropostaDespesa = autoritzacioPropostaDespesa;
	}
}
