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
		private double pbase;
		private double iva;
		private double plic;
		private String termini;
		private String comentari;
		private boolean seleccionada;
		private boolean ebss;
		private boolean coordinacio;
				
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
			if ("obr".equals(this.tipusObra)) {
				if (this.pbase > 50000) {
					return "Obra Major";
				} else {
					return "Obra menor";
				}
			}
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
		
		public String getTipusLlicenciaFormat() {
			if ("llicencia".equals(this.tipusLlicencia)) return "Llicència";
			if ("comun".equals(this.tipusLlicencia)) return "Comunicació prèvia";
			return "";
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

		
		public String getPbaseFormat(){
			DecimalFormat num = new DecimalFormat("#,##0.00");
		    return num.format(this.pbase) + '€';
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

		public double getPbase() {
			return pbase;
		}

		public void setPbase(double pbase) {
			this.pbase = pbase;
		}

		public boolean isEbss() {
			return ebss;
		}

		public void setEbss(boolean ebss) {
			this.ebss = ebss;
		}

		public boolean isCoordinacio() {
			return coordinacio;
		}

		public void setCoordinacio(boolean coordinacio) {
			this.coordinacio = coordinacio;
		}
		
	}
	
	private String idInf;
	private String idInfOriginal;
	private int idTasca;
	private Actuacio actuacio;
	private String idIncidencia;	
	private List<Fitxers.Fitxer> adjunts; 
	private List<PropostaInforme> llistaPropostes;
	private PropostaInforme propostaInformeSeleccionada;
	private User usuari;
	private Date dataCreacio;
	private String partidaRebutjadaMotiu;
	private Date dataRebujada;
	private String partida;
	private String codiPartida;
	private String comentariPartida;
	private User usuariCapValidacio;
	private Date dataCapValidacio;
	private String comentariCap;
	private User usuariAprovacio;
	private Date dataAprovacio;
	private List<Oferta> llistaOfertes;
	private Oferta ofertaSeleccionada;
	private List<Factura> llistaFactures;
	private List<Factura> llistaCertificacions;
	private List<InformeActuacio> llistaModificacions;
	private Date dataTancament;
	private Date dataRecepcio;
	private String notes;
	private String tipo;
	private Expedient expcontratacio;
	private Llicencia llicencia;
	private Date dataPD;
	private String tipoPD;
	private Fitxer propostaActuacio;
	private Fitxer vistiplauPropostaActuacio;
	private Fitxer informeSupervisio;
	private Fitxer conformeAreaEconomivaPropostaActuacio;
	private Fitxer autoritzacioPropostaAutoritzacio;
	private Fitxer autoritzacioConsellDeGovern;
	private Fitxer autoritzacioConseller;
	private Fitxer propostaTecnica;
	private Fitxer autoritzacioPropostaDespesa;
	private Fitxer resolucioModificacio;
	private Fitxer contracteSignat;
	private Fitxer memoriaOrdreInici;
	private Fitxer justProcForma;
	private Fitxer aprovacioEXPPlecsDespesa;
	
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
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
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
	
	public double getTotalFacturat() {
		double total = 0;
		if (this.llistaFactures != null) {
			for (Factura factura: this.llistaFactures) {
				if (!factura.isAnulada()) total += factura.getValor();
			}
		}
		return total;
	}
	
	public String getTotalFacturatFormat() {
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.getTotalFacturat()) + '€';
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

	public Expedient getExpcontratacio() {
		return expcontratacio;
	}

	public void setExpcontratacio(Expedient expcontratacio) {
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

	public String getCodiPartida() {
		return codiPartida;
	}

	public void setCodiPartida(String codiPartida) {
		this.codiPartida = codiPartida;
	}

	public List<InformeActuacio> getLlistaModificacions() {
		return llistaModificacions;
	}

	public void setLlistaModificacions(List<InformeActuacio> llistaModificacions) {
		this.llistaModificacions = llistaModificacions;
	}

	public String getIdInfOriginal() {
		return idInfOriginal;
	}

	public void setIdInfOriginal(String idInfOriginal) {
		this.idInfOriginal = idInfOriginal;
	}

	public Fitxer getContracteSignat() {
		return contracteSignat;
	}

	public void setContracteSignat(Fitxer contracteSignat) {
		this.contracteSignat = contracteSignat;
	}

	public Date getDataRecepcio() {
		return dataRecepcio;
	}

	public void setDataRecepcio(Date dataRecepcio) {
		this.dataRecepcio = dataRecepcio;
	}
	
	public String getDataRecepcioString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataRecepcio != null) dataString = df.format(this.dataRecepcio);
		return dataString;
	}

	public Actuacio getActuacio() {
		return actuacio;
	}

	public void setActuacio(Actuacio actuacio) {
		this.actuacio = actuacio;
	}
	
	public String getEstatEconomic() {
		String estat = "Sense partida";
		if (this.partida!= null && !this.partida.isEmpty()) {
			//RF (Reserva de fons)
			estat = "RF";			
		}
		//Contractat (PD i Contracte)
		if (this.autoritzacioPropostaDespesa.getRuta() != null || this.contracteSignat.getRuta() != null) {
			estat = "Contratat";
		}
		//Facturat (factures i certificacions)
		if (this.getTotalFacturat() > 0) {
			estat = "Facturat";
		}
		return estat;
	}

	public Fitxer getAutoritzacioConsellDeGovern() {
		return autoritzacioConsellDeGovern;
	}

	public void setAutoritzacioConsellDeGovern(Fitxer autoritzacioConsellDeGovern) {
		this.autoritzacioConsellDeGovern = autoritzacioConsellDeGovern;
	}

	public Fitxer getAutoritzacioConseller() {
		return autoritzacioConseller;
	}

	public void setAutoritzacioConseller(Fitxer autoritzacioConseller) {
		this.autoritzacioConseller = autoritzacioConseller;
	}

	public Fitxer getInformeSupervisio() {
		return informeSupervisio;
	}

	public void setInformeSupervisio(Fitxer informeSupervisio) {
		this.informeSupervisio = informeSupervisio;
	}

	public List<Factura> getLlistaCertificacions() {
		return llistaCertificacions;
	}

	public void setLlistaCertificacions(List<Factura> llistaCertificacions) {
		this.llistaCertificacions = llistaCertificacions;
	}

	public Fitxer getResolucioModificacio() {
		return resolucioModificacio;
	}

	public void setResolucioModificacio(Fitxer resolucioModificacio) {
		this.resolucioModificacio = resolucioModificacio;
	}

	public Llicencia getLlicencia() {
		return llicencia;
	}

	public void setLlicencia(Llicencia llicencia) {
		this.llicencia = llicencia;
	}

	public Fitxer getMemoriaOrdreInici() {
		return memoriaOrdreInici;
	}

	public void setMemoriaOrdreInici(Fitxer memoriaOrdreInici) {
		this.memoriaOrdreInici = memoriaOrdreInici;
	}

	public Fitxer getJustProcForma() {
		return justProcForma;
	}

	public void setJustProcForma(Fitxer justProcForma) {
		this.justProcForma = justProcForma;
	}

	public Fitxer getAprovacioEXPPlecsDespesa() {
		return aprovacioEXPPlecsDespesa;
	}

	public void setAprovacioEXPPlecsDespesa(Fitxer aprovacioEXPPlecsDespesa) {
		this.aprovacioEXPPlecsDespesa = aprovacioEXPPlecsDespesa;
	}
}
