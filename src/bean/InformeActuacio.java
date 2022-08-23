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
	
	public class IncidenciaGarantia {
		private int idIncidencia;
		private String objecte;
		private Date dataInici;
		private Date dataFi;
		private List<Fitxers.Fitxer> documentsList;
		
		public IncidenciaGarantia() {
			
		}

		public int getIdIncidencia() {
			return idIncidencia;
		}

		public void setIdIncidencia(int idIncidencia) {
			this.idIncidencia = idIncidencia;
		}

		public String getObjecte() {
			return objecte;
		}

		public void setObjecte(String objecte) {
			this.objecte = objecte;
		}

		public Date getDataInici() {
			return dataInici;
		}
		
		public String getDataIniciString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.dataInici != null) dataString = df.format(this.dataInici);
			return dataString;
		}

		public void setDataInici(Date dataInici) {
			this.dataInici = dataInici;
		}

		public Date getDataFi() {
			return dataFi;
		}

		public String getDataFiString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.dataFi != null) dataString = df.format(this.dataFi);
			return dataString;
		}
		
		public void setDataFi(Date dataFi) {
			this.dataFi = dataFi;
		}

		public List<Fitxers.Fitxer> getDocumentsList() {
			return documentsList;
		}

		public void setDocumentsList(List<Fitxers.Fitxer> documentsList) {
			this.documentsList = documentsList;
		}
	}
	
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
		private double vec;
		private String termini;
		private String comentari;
		private String comentariAdministratiu;
		private boolean seleccionada;
		private boolean ebss;
		private boolean coordinacio;
		private boolean ocults;
		private boolean retencio;
		private Date dataFirmaModificacio;
				
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
				if (this.pbase > 40000) {
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
			if ("llicencia".equals(this.tipusLlicencia) || "major".equals(this.tipusLlicencia)) return "Llic�ncia";
			if ("comun".equals(this.tipusLlicencia)) return "Comunicaci� pr�via";
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
		    return num.format(this.pbase) + '�';
		}
		public double getIva() {
			return iva;
		}
		
		public String getIvaFormat(){
			DecimalFormat num = new DecimalFormat("#,##0.00");
		    return num.format(this.iva) + '�';
		}

		public void setIva(double iva) {
			this.iva = iva;
		}

		public double getPlic() {
			return plic;
		}

		public String getPlicFormat(){
			DecimalFormat num = new DecimalFormat("#,##0.00");
		    return num.format(this.plic) + '�';
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

		public boolean isOcults() {
			return ocults;
		}

		public void setOcults(boolean ocults) {
			this.ocults = ocults;
		}

		public String getComentariAdministratiu() {
			return comentariAdministratiu;
		}

		public void setComentariAdministratiu(String comentariAdministratiu) {
			this.comentariAdministratiu = comentariAdministratiu;
		}

		public boolean isRetencio() {
			return retencio;
		}

		public void setRetencio(boolean retencio) {
			this.retencio = retencio;
		}

		public Date getDataFirmaModificacio() {
			return dataFirmaModificacio;
		}

		public void setDataFirmaModificacio(Date dataFirmaModificacio) {
			this.dataFirmaModificacio = dataFirmaModificacio;
		}
		public String getDataFirmaModificacioString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.dataFirmaModificacio != null) dataString = df.format(this.dataFirmaModificacio);
			return dataString;
		}

		public double getVec() {
			return vec;
		}

		public void setVec(double vec) {
			this.vec = vec;
		}
	}
	
	public class Personal {
		private int relacioID;
		private User usuari;
		private String idInf;
		private String tecnic;
		private String funcio;
		private String empresa;
		private boolean actiu;
		private Date dataAlta;
		private Date dataBaixa;
		public User getUsuari() {
			return usuari;
		}
		public void setUsuari(User usuari) {
			this.usuari = usuari;
		}
		public String getIdInf() {
			return idInf;
		}
		public void setIdInf(String idInf) {
			this.idInf = idInf;
		}
		public String getFuncio() {
			return funcio;
		}
		public void setFuncio(String funcio) {
			this.funcio = funcio;
		}
		public boolean isActiu() {
			return actiu;
		}
		public void setActiu(boolean actiu) {
			this.actiu = actiu;
		}
		public Date getDataAlta() {
			return dataAlta;
		}
		public String getDataAltaString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.dataAlta != null) dataString = df.format(this.dataAlta);
			return dataString;
		}
		public void setDataAlta(Date dataAlta) {
			this.dataAlta = dataAlta;
		}
		public Date getDataBaixa() {
			return dataBaixa;
		}
		public String getDataBaixaString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
			String dataString = "";
			if (this.dataBaixa != null) dataString = df.format(this.dataBaixa);
			return dataString;
		}
		public void setDataBaixa(Date dataBaixa) {
			this.dataBaixa = dataBaixa;
		}
		public int getRelacioID() {
			return relacioID;
		}
		public void setRelacioID(int relacioID) {
			this.relacioID = relacioID;
		}
		public String getTecnic() {
			return tecnic;
		}
		public void setTecnic(String tecnic) {
			this.tecnic = tecnic;
		}
		public String getEmpresa() {
			return empresa;
		}
		public void setEmpresa(String empresa) {
			this.empresa = empresa;
		}
	}
	
	private String idInf;
	private String idInfOriginal;
	private String idInfEspecific;	
	private boolean informeAntic;
	private int idTasca;
	private Actuacio actuacio;
	private String idIncidencia;	
	private List<Fitxers.Fitxer> adjunts; 
	private List<Fitxers.Fitxer> informesPrevis; 
	private List<Fitxers.Fitxer> docTecnica;
	private List<PropostaInforme> llistaPropostes;
	private List<IncidenciaGarantia> llistaIncidenciesGarantia;
	private PropostaInforme propostaInformeSeleccionada;
	private User usuari;
	private User responsableContracte;
	private Date dataCreacio;
	private String partidaRebutjadaMotiu;
	private Date dataRebujada;
	private List<AssignacioCredit> assignacioCredit;
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
	private List<Fitxer> tramitsModificacio;
	private List<InformeActuacio> llistaPenalitzacions;
	private String tipusModificacio;
	private boolean anulat;
	private String motiuAnulat;
	private Date dataTancament;
	private Date dataRecepcio;
	private String notes;
	private String tipo;
	private Expedient expcontratacio;
	private Llicencia llicencia;
	private Instalacions instalacions;
	private Date dataPD;
	private String tipoPD;
	private String estat;
	private Fitxer propostaActuacio;
	private Fitxer vistiplauPropostaActuacio;
	
	private List<Fitxers.Fitxer> conformeAreaEconomivaPropostaActuacio;
	private Fitxer autoritzacioPropostaAutoritzacio;
	private List<Fitxers.Fitxer> autoritzacioConsellDeGovern;
	private Fitxer autoritzacioConseller;
	private List<Fitxer> propostaTecnica;
	private List<Fitxer> autoritzacioPropostaDespesa;
	private Fitxer resolucioModificacio;
	private Fitxer contracteSignat;
	private Fitxer memoriaOrdreInici;
	private Fitxer justProcForma;
	private Fitxer justCriterisAdjudicacio;
	private Fitxer declaracioUrgencia;
	private Fitxer aprovacioEXPPlecsDespesa;
	private Fitxer aprovacioDispoTerrenys;
	private Fitxer informeInsuficienciaMitjans;
	private List<Fitxer> resolucioVAD;
	private List<Fitxer> ratificacioClassificacio;
	private Date publicacioBOIB;
	private Fitxer documentBOIB;
	private Fitxer correuInvitacio;
	
	private List<Fitxers.Fitxer> documentsAltresPrevis; 
	private List<Fitxers.Fitxer> documentsAltresLicitacio; 
	private List<Fitxers.Fitxer> documentsAltresExecucio; 
	private List<Fitxers.Fitxer> documentsAltresGarantia; 
	private List<Fitxers.Fitxer> documentsAltresAutUrbanistica; 
	
	private String recursAdministratiu;
	private List<Fitxers.Fitxer> documentsRecursosAdministratius;
		
	private List<Fitxers.Fitxer> documentActaReplanteig;
	private List<Fitxers.Fitxer> documentActaComprovacioReplanteig;
	private List<Fitxers.Fitxer> documentActaIniciObra;
	private List<Fitxers.Fitxer> documentActaAprovacioPlaSeguretat;
	private List<Fitxers.Fitxer> documentActaAprovacioResidus;
	private List<Fitxers.Fitxer> documentActaAprovacioProgramaTreball;
	private List<Fitxers.Fitxer> documentActaRecepcio;
	private List<Fitxers.Fitxer> documentActaMedicioGeneral;
	
	private List<Fitxers.Fitxer> documentFinalitzacioContratista;
	private List<Fitxers.Fitxer> documentInformeDO;
	private List<Fitxers.Fitxer> documentCFO;
	private List<Fitxers.Fitxer> documentMedicioGeneral;
	private List<Fitxers.Fitxer> documentRepresentacioRecepcio;
	private List<Fitxers.Fitxer> documentConvocatoriaRecepcio;
	
	private List<Fitxers.Fitxer> documentSolDevolucio;
	private List<Fitxers.Fitxer> documentInformeDevolucio;
	private List<Fitxers.Fitxer> documentLiquidacioAval;
	
	private List<Fitxers.Fitxer> informeSupervisio;
	private List<Fitxers.Fitxer> projecte;	
	private List<Fitxers.Fitxer> documentNomenamentDF;
	private List<Fitxers.Fitxer> documentPSS;
	private List<Fitxers.Fitxer> documentPGR;
	private List<Fitxers.Fitxer> documentPlaTreball;
	private List<Fitxers.Fitxer> documentCertificacioFinal;
	private List<Fitxers.Fitxer> documentDevolucioAval;
	
	private List<Fitxers.Fitxer> informeDF;
	private List<Fitxers.Fitxer> informeJuridic;
	private List<Fitxers.Fitxer> resolucioFinalModificacio;
	private Fitxers.Fitxer formalitzacioSignat;	
	
	private List<Fitxers.Fitxer> resInici;
	private List<Fitxers.Fitxer> tramits;
	private List<Fitxers.Fitxer> resFinal;
	
	private Date dataPublicacioRegitreConvenis;
	private Date dataPublicacioPerfilContractant;
	private Date dataPublicacioDGPressuposts;
	private Date dataPublicacioDGTresoreria;
	
	private List<Registre> entrades;
	private List<Registre> sortides;
	private List<Tasca> tasques;
	private List<Personal> personal;
		
	private String organismeDependencia;
	private boolean cessioCredit;
	
	public InformeActuacio() {	
		this.llistaPropostes = new ArrayList<PropostaInforme>();
	}

	public String getIdInf() {
		return idInf;
	}

	public void setIdInf(String idInf) {
		this.idInf = idInf;
		if (idInf != null)	this.informeAntic = !idInf.contains("-INF-");
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

	public List<AssignacioCredit> getAssignacioCredit() {
		return assignacioCredit;
	}

	public void setAssignacioCredit(List<AssignacioCredit> assignacioCredit) {
		this.assignacioCredit = assignacioCredit;
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

	public int getEmpresesPresentades() {
		int empresesPresentades = 0;
		if (this.llistaOfertes != null) {
			for (Oferta oferta: this.llistaOfertes) {
				if (oferta.getPbase() > 0) empresesPresentades += 1;
			}
		}
		return empresesPresentades;
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
		if (this.llistaFactures != null) {
			for (Factura factura: this.llistaFactures) {
				if (!factura.isAnulada()) this.setTotalFacturat(this.getTotalFacturat() + factura.getValor());
			}
		}
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
	    return num.format(this.getTotalFacturat()) + '�';
	}
	
	public double getTotalCertificat() {
		double total = 0;
		if (this.llistaCertificacions != null) {
			for (Factura certificacio: this.llistaCertificacions) {
				if (!certificacio.isAnulada()) total += certificacio.getValor();
			}
		}
		return total;
	}

	public String getTotalCertificatFormat() {
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.getTotalCertificat()) + '�';
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

	public List<Fitxer> getConformeAreaEconomivaPropostaActuacio() {
		return conformeAreaEconomivaPropostaActuacio;
	}

	public void setConformeAreaEconomivaPropostaActuacio(List<Fitxer> conformeAreaEconomivaPropostaActuacio) {
		this.conformeAreaEconomivaPropostaActuacio = conformeAreaEconomivaPropostaActuacio;
	}

	public Fitxer getAutoritzacioPropostaAutoritzacio() {
		return autoritzacioPropostaAutoritzacio;
	}

	public void setAutoritzacioPropostaAutoritzacio(Fitxer autoritzacioPropostaAutoritzacio) {
		this.autoritzacioPropostaAutoritzacio = autoritzacioPropostaAutoritzacio;
	}

	public List<Fitxer> getPropostaTecnica() {
		return propostaTecnica;
	}

	public void setPropostaTecnica(List<Fitxer> propostaTecnica) {
		this.propostaTecnica = propostaTecnica;
	}

	public List<Fitxer> getAutoritzacioPropostaDespesa() {
		return autoritzacioPropostaDespesa;
	}

	public void setAutoritzacioPropostaDespesa(List<Fitxer> autoritzacioPropostaDespesa) {
		this.autoritzacioPropostaDespesa = autoritzacioPropostaDespesa;
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
		if (this.assignacioCredit != null && this.assignacioCredit.size() > 0) {
			//RF (Reserva de fons)
			estat = "RF";			
		}
		//Contractat (PD i Contracte)
		if (this.autoritzacioPropostaDespesa.size() > 0 || this.contracteSignat.getRuta() != null) {
			estat = "Contractat";
		}
		//Facturat (factures i certificacions)
		if (this.getTotalFacturat() > 0) {
			estat = "Facturat";
		}
		return estat;
	}

	public List<Fitxer> getAutoritzacioConsellDeGovern() {
		return autoritzacioConsellDeGovern;
	}

	public void setAutoritzacioConsellDeGovern(List<Fitxer> autoritzacioConsellDeGovern) {
		this.autoritzacioConsellDeGovern = autoritzacioConsellDeGovern;
	}

	public Fitxer getAutoritzacioConseller() {
		return autoritzacioConseller;
	}

	public void setAutoritzacioConseller(Fitxer autoritzacioConseller) {
		this.autoritzacioConseller = autoritzacioConseller;
	}

	public List<Fitxer> getInformeSupervisio() {
		return informeSupervisio;
	}

	public void setInformeSupervisio(List<Fitxer> informeSupervisio) {
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

	public Fitxer getAprovacioDispoTerrenys() {
		return aprovacioDispoTerrenys;
	}

	public void setAprovacioDispoTerrenys(Fitxer aprovacioDispoTerrenys) {
		this.aprovacioDispoTerrenys = aprovacioDispoTerrenys;
	}

	public Fitxer getDocumentBOIB() {
		return documentBOIB;
	}

	public void setDocumentBOIB(Fitxer documentBOIB) {
		this.documentBOIB = documentBOIB;
	}

	public Fitxer getCorreuInvitacio() {
		return correuInvitacio;
	}

	public void setCorreuInvitacio(Fitxer correuInvitacio) {
		this.correuInvitacio = correuInvitacio;
	}

	public Date getPublicacioBOIB() {
		return publicacioBOIB;
	}
	
	public String getPublicacioBOIBString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.publicacioBOIB != null) dataString = df.format(this.publicacioBOIB);
		return dataString;
	}

	public void setPublicacioBOIB(Date publicacioBOIB) {
		this.publicacioBOIB = publicacioBOIB;
	}
	
	public double getTotalModificacions() {
		double total = 0;
		for (InformeActuacio modificacio:  this.llistaModificacions) {
			if (modificacio.getAutoritzacioPropostaDespesa().size() > 0 || modificacio.getResFinal().size() > 0 || modificacio.formalitzacioSignat.getRuta() != null) {				
				total += modificacio.getOfertaSeleccionada().getPlic();
			}
		}
		return total;
	}
	
	public String getTotalModificacionsString() {
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.getTotalModificacions()) + '�';
	}
	
	public double getTotalAmbModificacions() {
		double total = 0;
		if (this.getOfertaSeleccionada() != null){
			for (InformeActuacio modificacio:  this.llistaModificacions) {
				if (modificacio.getAutoritzacioPropostaDespesa().size() > 0 || modificacio.getResFinal().size() > 0 || modificacio.formalitzacioSignat.getRuta() != null) {				
					total += modificacio.getOfertaSeleccionada().getPlic();
				}
			}
			total += this.getOfertaSeleccionada().getPlic();
		}
		return total;
	}
	
	public String getTotalAmbModificacionsString() {
		DecimalFormat num = new DecimalFormat("#,##0.00");
	    return num.format(this.getTotalAmbModificacions()) + '�';
	}
	
	public String getEstatExpedientFormat() {
		String estat = "";	
		if (this.estat != null) {
			estat = "En execuci�";	
			if (this.estat.equals("garantia")) {
				estat = "Garantia";
			} else if (this.estat.equals("previs")) {
				estat = "Previs licitaci�";
			} else if(this.estat.equals("licitacio")) {
				estat = "En licitaci�";
			} else if(this.estat.equals("acabat")) {
				estat = "Tancada";
			} else if(this.estat.equals("anulat")) {
				estat = "Anul�lat";
			}
		}		
		return estat;
	}

	public List<Fitxer> getRatificacioClassificacio() {
		return ratificacioClassificacio;
	}

	public void setRatificacioClassificacio(List<Fitxer> ratificacioClassificacio) {
		this.ratificacioClassificacio = ratificacioClassificacio;
	}

	public List<IncidenciaGarantia> getLlistaIncidenciesGarantia() {
		return llistaIncidenciesGarantia;
	}

	public void setLlistaIncidenciesGarantia(List<IncidenciaGarantia> llistaIncidenciesGarantia) {
		this.llistaIncidenciesGarantia = llistaIncidenciesGarantia;
	}

	public List<Fitxers.Fitxer> getInformesPrevis() {
		return informesPrevis;
	}

	public void setInformesPrevis(List<Fitxers.Fitxer> informesPrevis) {
		this.informesPrevis = informesPrevis;
	}

	public List<Fitxers.Fitxer> getDocTecnica() {
		return docTecnica;
	}

	public void setDocTecnica(List<Fitxers.Fitxer> docTecnica) {
		this.docTecnica = docTecnica;
	}

	public void setTotalFacturat(double totalFacturat) {
	}

	public List<Fitxers.Fitxer> getDocumentsAltresPrevis() {
		return documentsAltresPrevis;
	}

	public void setDocumentsAltresPrevis(List<Fitxers.Fitxer> documentsAltresPrevis) {
		this.documentsAltresPrevis = documentsAltresPrevis;
	}

	public List<Fitxers.Fitxer> getDocumentsAltresLicitacio() {
		return documentsAltresLicitacio;
	}

	public void setDocumentsAltresLicitacio(List<Fitxers.Fitxer> documentsAltresLicitacio) {
		this.documentsAltresLicitacio = documentsAltresLicitacio;
	}

	public List<Fitxers.Fitxer> getDocumentsAltresExecucio() {
		return documentsAltresExecucio;
	}

	public void setDocumentsAltresExecucio(List<Fitxers.Fitxer> documentsAltresExecucio) {
		this.documentsAltresExecucio = documentsAltresExecucio;
	}

	public List<Fitxers.Fitxer> getDocumentsAltresGarantia() {
		return documentsAltresGarantia;
	}

	public void setDocumentsAltresGarantia(List<Fitxers.Fitxer> documentsAltresGarantia) {
		this.documentsAltresGarantia = documentsAltresGarantia;
	}

	public List<Fitxers.Fitxer> getDocumentsAltresAutUrbanistica() {
		return documentsAltresAutUrbanistica;
	}

	public void setDocumentsAltresAutUrbanistica(List<Fitxers.Fitxer> documentsAltresAutUrbanistica) {
		this.documentsAltresAutUrbanistica = documentsAltresAutUrbanistica;
	}
	
	public String getRecursAdministratiu() {
		return recursAdministratiu;
	}

	public void setRecursAdministratiu(String recursAdministratiu) {
		this.recursAdministratiu = recursAdministratiu;
	}

	public List<Fitxers.Fitxer> getDocumentsRecursosAdministratius() {
		return documentsRecursosAdministratius;
	}

	public void setDocumentsRecursosAdministratius(List<Fitxers.Fitxer> documentsRecursosAdministratius) {
		this.documentsRecursosAdministratius = documentsRecursosAdministratius;
	}

	public Instalacions getInstalacions() {
		return instalacions;
	}

	public void setInstalacions(Instalacions instalacions) {
		this.instalacions = instalacions;
	}	

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public List<Registre> getEntrades() {
		return entrades;
	}

	public void setEntrades(List<Registre> entrades) {
		this.entrades = entrades;
	}

	public List<Registre> getSortides() {
		return sortides;
	}

	public void setSortides(List<Registre> sortides) {
		this.sortides = sortides;
	}

	public List<Tasca> getTasques() {
		return tasques;
	}

	public void setTasques(List<Tasca> tasques) {
		this.tasques = tasques;
	}

	public List<Personal> getPersonal() {
		return personal;
	}

	public void setPersonal(List<Personal> personal) {
		this.personal = personal;
	}

	public Fitxer getJustCriterisAdjudicacio() {
		return justCriterisAdjudicacio;
	}

	public void setJustCriterisAdjudicacio(Fitxer justCriterisAdjudicacio) {
		this.justCriterisAdjudicacio = justCriterisAdjudicacio;
	}

	public Fitxer getDeclaracioUrgencia() {
		return declaracioUrgencia;
	}

	public void setDeclaracioUrgencia(Fitxer declaracioUrgencia) {
		this.declaracioUrgencia = declaracioUrgencia;
	}

	public List<Fitxer> getResolucioVAD() {
		return resolucioVAD;
	}

	public void setResolucioVAD(List<Fitxer> resolucioVAD) {
		this.resolucioVAD = resolucioVAD;
	}

	public String getTipusModificacio() {
		return tipusModificacio;
	}
	
	public String getTipusModificacioFormat() {
		String estat = this.tipusModificacio;	
		
		if (this.tipusModificacio.equals("modificacio")) {
			estat = "Modificaci�";
		} else if (this.tipusModificacio.equals("liquidacio")) {
			estat = "Liquidaci�";
		} else if (this.tipusModificacio.equals("preusContradictoris")) {
			estat = "Preus contradictoris";
		} else if (this.tipusModificacio.equals("termini")) {
			estat = "Ampliaci� termini";				
		} else if(this.tipusModificacio.equals("penalitzacio")) {
			estat = "Penalitzaci�";
		} else if(this.tipusModificacio.equals("certfinal")) {
			estat = "Certificaci� final";
		} else if(this.tipusModificacio.equals("excesAmidament")) {
			estat = "Exc�s Amidament";
		} else if(this.tipusModificacio.equals("decrementAmidament")) {
				estat = "Decrement d'Amidament";			
		} else if(this.tipusModificacio.equals("resolucioContracte")) {
			estat = "Resoluci� Contracte";
		} else if(this.tipusModificacio.equals("informeExecucio")) {
			estat = "Informe d'execuci�";
		} else if(this.tipusModificacio.equals("enriquimentInjust")) {
			estat = "Enriquiment injust";
		} else if(this.tipusModificacio.equals("ocupacio")) {
			estat = "Ocupaci�";
		}
		
		return estat;
	}

	public void setTipusModificacio(String tipusModificacio) {
		this.tipusModificacio = tipusModificacio;
	}

	public List<Fitxers.Fitxer> getDocumentActaReplanteig() {
		return documentActaReplanteig;
	}

	public void setDocumentActaReplanteig(List<Fitxers.Fitxer> documentActaReplanteig) {
		this.documentActaReplanteig = documentActaReplanteig;
	}

	public List<Fitxers.Fitxer> getDocumentActaComprovacioReplanteig() {
		return documentActaComprovacioReplanteig;
	}

	public void setDocumentActaComprovacioReplanteig(List<Fitxers.Fitxer> documentActaComprovacioReplanteig) {
		this.documentActaComprovacioReplanteig = documentActaComprovacioReplanteig;
	}

	public List<Fitxers.Fitxer> getDocumentActaIniciObra() {
		return documentActaIniciObra;
	}

	public void setDocumentActaIniciObra(List<Fitxers.Fitxer> documentActaIniciObra) {
		this.documentActaIniciObra = documentActaIniciObra;
	}

	public List<Fitxers.Fitxer> getDocumentActaAprovacioPlaSeguretat() {
		return documentActaAprovacioPlaSeguretat;
	}

	public void setDocumentActaAprovacioPlaSeguretat(List<Fitxers.Fitxer> documentActaAprovacioPlaSeguretat) {
		this.documentActaAprovacioPlaSeguretat = documentActaAprovacioPlaSeguretat;
	}

	public List<Fitxers.Fitxer> getDocumentActaAprovacioResidus() {
		return documentActaAprovacioResidus;
	}

	public void setDocumentActaAprovacioResidus(List<Fitxers.Fitxer> documentActaAprovacioResidus) {
		this.documentActaAprovacioResidus = documentActaAprovacioResidus;
	}

	public List<Fitxers.Fitxer> getDocumentActaAprovacioProgramaTreball() {
		return documentActaAprovacioProgramaTreball;
	}

	public void setDocumentActaAprovacioProgramaTreball(List<Fitxers.Fitxer> documentActaAprovacioProgramaTreball) {
		this.documentActaAprovacioProgramaTreball = documentActaAprovacioProgramaTreball;
	}

	public List<Fitxers.Fitxer> getDocumentActaRecepcio() {
		return documentActaRecepcio;
	}

	public void setDocumentActaRecepcio(List<Fitxers.Fitxer> documentActaRecepcio) {
		this.documentActaRecepcio = documentActaRecepcio;
	}

	public List<Fitxers.Fitxer> getDocumentSolDevolucio() {
		return documentSolDevolucio;
	}

	public void setDocumentSolDevolucio(List<Fitxers.Fitxer> documentSolDevolucio) {
		this.documentSolDevolucio = documentSolDevolucio;
	}

	public List<Fitxers.Fitxer> getDocumentInformeDevolucio() {
		return documentInformeDevolucio;
	}

	public void setDocumentInformeDevolucio(List<Fitxers.Fitxer> documentInformeDevolucio) {
		this.documentInformeDevolucio = documentInformeDevolucio;
	}

	public List<Fitxers.Fitxer> getDocumentLiquidacioAval() {
		return documentLiquidacioAval;
	}

	public void setDocumentLiquidacioAval(List<Fitxers.Fitxer> documentLiquidacioAval) {
		this.documentLiquidacioAval = documentLiquidacioAval;
	}

	public List<Fitxers.Fitxer> getDocumentFinalitzacioContratista() {
		return documentFinalitzacioContratista;
	}

	public void setDocumentFinalitzacioContratista(List<Fitxers.Fitxer> documentFinalitzacioContratista) {
		this.documentFinalitzacioContratista = documentFinalitzacioContratista;
	}

	public List<Fitxers.Fitxer> getDocumentInformeDO() {
		return documentInformeDO;
	}

	public void setDocumentInformeDO(List<Fitxers.Fitxer> documentInformeDO) {
		this.documentInformeDO = documentInformeDO;
	}

	public List<Fitxers.Fitxer> getDocumentCFO() {
		return documentCFO;
	}

	public void setDocumentCFO(List<Fitxers.Fitxer> documentCFO) {
		this.documentCFO = documentCFO;
	}

	public List<Fitxers.Fitxer> getDocumentMedicioGeneral() {
		return documentMedicioGeneral;
	}

	public void setDocumentMedicioGeneral(List<Fitxers.Fitxer> documentMedicioGeneral) {
		this.documentMedicioGeneral = documentMedicioGeneral;
	}

	public List<Fitxers.Fitxer> getDocumentRepresentacioRecepcio() {
		return documentRepresentacioRecepcio;
	}

	public void setDocumentRepresentacioRecepcio(List<Fitxers.Fitxer> documentRepresentacioRecepcio) {
		this.documentRepresentacioRecepcio = documentRepresentacioRecepcio;
	}

	public List<Fitxers.Fitxer> getDocumentConvocatoriaRecepcio() {
		return documentConvocatoriaRecepcio;
	}

	public void setDocumentConvocatoriaRecepcio(List<Fitxers.Fitxer> documentConvocatoriaRecepcio) {
		this.documentConvocatoriaRecepcio = documentConvocatoriaRecepcio;
	}

	public List<Fitxers.Fitxer> getDocumentActaMedicioGeneral() {
		return documentActaMedicioGeneral;
	}

	public void setDocumentActaMedicioGeneral(List<Fitxers.Fitxer> documentActaMedicioGeneral) {
		this.documentActaMedicioGeneral = documentActaMedicioGeneral;
	}

	public List<InformeActuacio> getLlistaPenalitzacions() {
		return llistaPenalitzacions;
	}

	public void setLlistaPenalitzacions(List<InformeActuacio> llistaPenalitzacions) {
		this.llistaPenalitzacions = llistaPenalitzacions;
	}

	public Fitxer getInformeInsuficienciaMitjans() {
		return informeInsuficienciaMitjans;
	}

	public void setInformeInsuficienciaMitjans(Fitxer informeInsuficienciaMitjans) {
		this.informeInsuficienciaMitjans = informeInsuficienciaMitjans;
	}

	public List<Fitxers.Fitxer> getProjecte() {
		return projecte;
	}

	public void setProjecte(List<Fitxers.Fitxer> projecte) {
		this.projecte = projecte;
	}

	public List<Fitxers.Fitxer> getDocumentNomenamentDF() {
		return documentNomenamentDF;
	}

	public void setDocumentNomenamentDF(List<Fitxers.Fitxer> documentNomenamentDF) {
		this.documentNomenamentDF = documentNomenamentDF;
	}

	public List<Fitxers.Fitxer> getDocumentPSS() {
		return documentPSS;
	}

	public void setDocumentPSS(List<Fitxers.Fitxer> documentPSS) {
		this.documentPSS = documentPSS;
	}

	public List<Fitxers.Fitxer> getDocumentPGR() {
		return documentPGR;
	}

	public void setDocumentPGR(List<Fitxers.Fitxer> documentPGR) {
		this.documentPGR = documentPGR;
	}

	public List<Fitxers.Fitxer> getDocumentPlaTreball() {
		return documentPlaTreball;
	}

	public void setDocumentPlaTreball(List<Fitxers.Fitxer> documentPlaTreball) {
		this.documentPlaTreball = documentPlaTreball;
	}

	public List<Fitxers.Fitxer> getDocumentCertificacioFinal() {
		return documentCertificacioFinal;
	}

	public void setDocumentCertificacioFinal(List<Fitxers.Fitxer> documentCertificacioFinal) {
		this.documentCertificacioFinal = documentCertificacioFinal;
	}

	public List<Fitxers.Fitxer> getDocumentDevolucioAval() {
		return documentDevolucioAval;
	}

	public void setDocumentDevolucioAval(List<Fitxers.Fitxer> documentDevolucioAval) {
		this.documentDevolucioAval = documentDevolucioAval;
	}

	public String getOrganismeDependencia() {
		return organismeDependencia;
	}

	public String getOrganismeDependenciaString() {
		String organisme = "";
		if (organismeDependencia != null) organisme = organismeDependencia;
		return organisme.replace("#", "");
	}
	
	public void setOrganismeDependencia(String organismeDependencia) {
		this.organismeDependencia = organismeDependencia;
	}

	public List<Fitxer> getTramitsModificacio() {
		return tramitsModificacio;
	}

	public void setTramitsModificacio(List<Fitxer> tramitsModificacio) {
		this.tramitsModificacio = tramitsModificacio;
	}

	public boolean isAnulat() {
		return anulat;
	}

	public void setAnulat(boolean anulat) {
		this.anulat = anulat;
	}

	public String getMotiuAnulat() {
		return motiuAnulat;
	}

	public void setMotiuAnulat(String motiuAnulat) {
		this.motiuAnulat = motiuAnulat;
	}

	public List<Fitxers.Fitxer> getInformeDF() {
		return informeDF;
	}

	public void setInformeDF(List<Fitxers.Fitxer> informeDF) {
		this.informeDF = informeDF;
	}

	public List<Fitxers.Fitxer> getInformeJuridic() {
		return informeJuridic;
	}

	public void setInformeJuridic(List<Fitxers.Fitxer> informeJuridic) {
		this.informeJuridic = informeJuridic;
	}

	public Fitxers.Fitxer getFormalitzacioSignat() {
		return formalitzacioSignat;
	}

	public void setFormalitzacioSignat(Fitxers.Fitxer formalitzacioSignat) {
		this.formalitzacioSignat = formalitzacioSignat;
	}

	public Date getDataPublicacioRegitreConvenis() {
		return dataPublicacioRegitreConvenis;
	}

	public String getDataPublicacioRegitreConvenisString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataPublicacioRegitreConvenis != null) dataString = df.format(this.dataPublicacioRegitreConvenis);
		return dataString;
	}
	
	public void setDataPublicacioRegitreConvenis(Date dataPublicacioRegitreConvenis) {
		this.dataPublicacioRegitreConvenis = dataPublicacioRegitreConvenis;
	}

	public Date getDataPublicacioPerfilContractant() {
		return dataPublicacioPerfilContractant;
	}
	
	public String getDataPublicacioPerfilContractantString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataPublicacioPerfilContractant != null) dataString = df.format(this.dataPublicacioPerfilContractant);
		return dataString;
	}

	public void setDataPublicacioPerfilContractant(Date dataPublicacioPerfilContractant) {
		this.dataPublicacioPerfilContractant = dataPublicacioPerfilContractant;
	}

	public Date getDataPublicacioDGPressuposts() {
		return dataPublicacioDGPressuposts;
	}

	public String getDataPublicacioDGPressupostsString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataPublicacioDGPressuposts != null) dataString = df.format(this.dataPublicacioDGPressuposts);
		return dataString;
	}
	
	public void setDataPublicacioDGPressuposts(Date dataPublicacioDGPressuposts) {
		this.dataPublicacioDGPressuposts = dataPublicacioDGPressuposts;
	}

	public Date getDataPublicacioDGTresoreria() {
		return dataPublicacioDGTresoreria;
	}

	public String getDataPublicacioDGTresoreriaString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String dataString = "";
		if (this.dataPublicacioDGTresoreria != null) dataString = df.format(this.dataPublicacioDGTresoreria);
		return dataString;
	}
	
	public void setDataPublicacioDGTresoreria(Date dataPublicacioDGTresoreria) {
		this.dataPublicacioDGTresoreria = dataPublicacioDGTresoreria;
	}

	public boolean isCessioCredit() {
		return cessioCredit;
	}

	public void setCessioCredit(boolean cessioCredit) {
		this.cessioCredit = cessioCredit;
	}

	public String getIdInfEspecific() {
		return idInfEspecific;
	}

	public void setIdInfEspecific(String idInfEspecific) {
		this.idInfEspecific = idInfEspecific;
	}

	public List<Fitxers.Fitxer> getResInici() {
		return resInici;
	}

	public void setResInici(List<Fitxers.Fitxer> resInici) {
		this.resInici = resInici;
	}

	public List<Fitxers.Fitxer> getTramits() {
		return tramits;
	}

	public void setTramits(List<Fitxers.Fitxer> tramits) {
		this.tramits = tramits;
	}

	public List<Fitxers.Fitxer> getResFinal() {
		return resFinal;
	}

	public void setResFinal(List<Fitxers.Fitxer> resFinal) {
		this.resFinal = resFinal;
	}

	public List<Fitxers.Fitxer> getResolucioFinalModificacio() {
		return resolucioFinalModificacio;
	}

	public void setResolucioFinalModificacio(List<Fitxers.Fitxer> resolucioFinalModificacio) {
		this.resolucioFinalModificacio = resolucioFinalModificacio;
	}

	public boolean isInformeAntic() {
		return informeAntic;
	}

	public void setInformeAntic(boolean isInformeAntic) {
		this.informeAntic = isInformeAntic;
	}

	public User getResponsableContracte() {
		return responsableContracte;
	}

	public void setResponsableContracte(User responsableContracte) {
		this.responsableContracte = responsableContracte;
	}	
}
