package servlet.tasca;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import bean.Actuacio;
import bean.AssignacioCredit;
import bean.Factura;
import bean.InformeActuacio;
import bean.Oferta;
import bean.Registre;
import bean.Tasca;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import core.ActuacioCore;
import core.ConfiguracioCore;
import core.CreditCore;
import core.ExpedientCore;
import core.FacturaCore;
import core.InformeCore;
import core.JudicialCore;
import core.OfertaCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.Fitxers.Fitxer;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddPAServlet
 */
@WebServlet("/DoTasca")
public class DoTasca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoTasca() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		
		int idTasca = -1;
		String ipRemote = request.getRemoteAddr();
		Tasca tasca = new Tasca();		
		String document = multipartParams.getParametres().get("document");
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme"); 
	    String idFactura =  multipartParams.getParametres().get("idFactura"); 
	    String tramitar = multipartParams.getParametres().get("tramitar"); 
	    String modificar = multipartParams.getParametres().get("tramitar"); 
	    String comentariCap = "";
	    String errorString = null;
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	
	    if (multipartParams.getParametres().get("idTasca") != null) {
			idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));	 
			tasca = TascaCore.findTascaId(conn, idTasca, Usuari.getIdUsuari());
		}
	    //Guardar adjunts
	    List<Fitxer> fitxers = multipartParams.getFitxers();
	    if (!fitxers.isEmpty() || "paTecnic".equals(document) || "docTecnica".equals(document) || "certificacioValidada".equals(document)) {
	    	try {
	    		if ("paTecnic".equals(document)) {
	    			int idTecnic = Integer.parseInt(multipartParams.getParametres().get("llistaUsuaris"));	
	    		    String tipusObra = multipartParams.getParametres().get("tipusContracte");
	    		    String objecte = multipartParams.getParametres().get("objecteActuacio");
	    		    boolean llicencia = false;
	    		    String reqLlicencia = multipartParams.getParametres().get("reqLlicencia");
	    		    String tipusLlicencia = "";
	    		    
	    		    double pbase = 0;
	    			double iva = 0;
	    			double plic = 0;
	    			String termini = "";
	    			String comentari = "";
	    			String comentariAdministratiu = "";
	    			
			 	    InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
				    PropostaInforme proposta = informe.new PropostaInforme();
				    if (!informe.getLlistaPropostes().isEmpty() && informe.getLlistaPropostes().get(0) != null) {
				    	 proposta = informe.getLlistaPropostes().get(0);
				    }		
				    List<PropostaInforme> llistaPropostes = new ArrayList<PropostaInforme>(); 
				    proposta.setObjecte(objecte);
				    proposta.setTipusObra(tipusObra);
				    if (new String("obr").equals(tipusObra)) {
				    	llicencia = new String("si").equals(reqLlicencia);	 	   
						if (llicencia) tipusLlicencia = multipartParams.getParametres().get("tipusLlicencia") ;
				    }	
				    proposta.setLlicencia(llicencia);
				    proposta.setTipusLlicencia(tipusLlicencia);
				    proposta.setContracte(true);
				    pbase = Double.parseDouble(multipartParams.getParametres().get("pbase").replace(',','.'));
				    iva = Double.parseDouble(multipartParams.getParametres().get("iva"));
				    plic = Double.parseDouble(multipartParams.getParametres().get("plic").replace(',','.'));
				    proposta.setPbase(pbase);
				    proposta.setIva(iva);
				    proposta.setPlic(plic);
				    termini = multipartParams.getParametres().get("termini" );
				    comentari = multipartParams.getParametres().get("comentariTecnic");	
				    comentariAdministratiu = multipartParams.getParametres().get("comentariAdministratiu");
				    proposta.setTermini(termini);
				    proposta.setComentari(comentari);
				    proposta.setComentariAdministratiu(comentariAdministratiu);
				    proposta.setSeleccionada(true);			    
				    llistaPropostes.add(proposta);
				    informe.setLlistaPropostes(llistaPropostes);
				    informe.setPropostaInformeSeleccionada(proposta);	
				    InformeCore.modificarInforme(conn, informe, idTecnic);	 
				    informe = InformeCore.getInformePrevi(conn, idInforme, false);
				    InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInforme);	   
				    informe = InformeCore.getInformePrevi(conn, idInforme, false);
				    if (modificar == null && (informe.getExpcontratacio() == null || informe.getExpcontratacio().getExpContratacio().equals("-1"))) {
	   					
	   					String nouCodi = "";
		   				nouCodi = ExpedientCore.crearExpedient(conn, informe, false, "", idTecnic);	   				
		   				informe.setExpcontratacio(ExpedientCore.findExpedient(conn, nouCodi));			  
		   				
	   				}
				    if (informe.getAssignacioCredit() == null || informe.getAssignacioCredit().size() == 0 || informe.getAssignacioCredit().get(0).getPartida() == null || informe.getAssignacioCredit().get(0).getPartida().getCodi().isEmpty()) {
				    	if (!TascaCore.existTascaReservaCredit(conn, informe.getIdInf())) { // no existeix tasca creada
				    	 	int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
							TascaCore.novaTasca(conn, "resPartida", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", String.valueOf(idTasca), idInforme, null, ipRemote, "automatic");					
				    	}
				    }
				   
	    		} else if ("memoriaInici".equals(document)) {
	    			Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Memòria odre inici", Usuari.getIdUsuari());
	    			InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	if (informe.getLlistaPropostes().size() == 1) {
			    		InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInforme);
			    	}
			    	TascaCore.tancar(conn, idTasca);	
			    	int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
					String comentari = "Documentació prelicitació per signar: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesT'>" + informe.getExpcontratacio().getExpContratacio() + "</a>";
					TascaCore.novaTasca(conn, "autoritzacioActuacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Documentació prelicitació per signar", idInforme, null, ipRemote, "automatic");
	    		} else if ("autoritzacioCap".equals(document)) {
			    	Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Vistiplau cap", Usuari.getIdUsuari());
			    	TascaCore.nouHistoric(conn, idTasca, "Informe aprovat", Usuari.getIdUsuari(), ipRemote, "automatic");
					ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta d'actuació realitzada");
					TascaCore.reasignar(conn, 900, idTasca, tasca.getTipus(), tasca.getDescripcio());
					TascaCore.tancar(conn, idTasca);
					int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
					TascaCore.novaTasca(conn, "resPartida", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", String.valueOf(idTasca), idInforme, null, ipRemote, "automatic");					
	    		} else if ("autoritzacioAreaFinancera".equals(document)) {
			    	InformeActuacio informe = new InformeActuacio();
			    	if (idInforme.contains("-MOD-")) {			    		 
					    informe = InformeCore.getMoficacioInforme(conn, idInforme, false);
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", informe.getIdInfOriginal(), idInforme, "Conforme àrea financera", Usuari.getIdUsuari());			    		
			    	
			    	} else {
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Conforme àrea financera", Usuari.getIdUsuari());			    		 
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	}		
				    String comentariHistoral = "S'ha reservat l'import de " + informe.getPropostaInformeSeleccionada().getPlicFormat() + "€ de la partida " + informe.getAssignacioCredit().get(0).getPartida().getNom();
			   		String tipus = "automatic";
				    if(informe.getDataRebujada() != null) {	
				    	tipus = "manual";
				   		comentariHistoral = "<span class='missatgeAutomatic'>S'ha rebutjat l'import de " + informe.getPropostaInformeSeleccionada().getPlicFormat() + "€ de la partida " + informe.getAssignacioCredit().get(0).getPartida().getNom() + "</span>";
				   		comentariHistoral += "</br>Motiu: " + informe.getPartidaRebutjadaMotiu(); 
				   	}
			   		TascaCore.nouHistoric(conn, idTasca, comentariHistoral, Usuari.getIdUsuari(), ipRemote, tipus);
			   		TascaCore.reasignar(conn, 901, idTasca, tasca.getTipus(), tasca.getDescripcio());
			   		TascaCore.tancar(conn, idTasca);
			   		int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
					String comentari = "S'ha reservat l'import de " + informe.getPropostaInformeSeleccionada().getPlicFormat() + "€ de la partida " + informe.getAssignacioCredit().get(0).getPartida().getNom();
					if (informe.getExpcontratacio().getIdInforme() != null && !informe.getExpcontratacio().getIdInforme().equals("-1") && informe.getExpcontratacio().getContracte().equals("major")) {
						if (idInforme.contains("-MOD-")) {	
							comentari = "S'ha realitzat la proposta de modificació" + idInforme ;
							TascaCore.novaTasca(conn, "generica", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Partida modificació actuació", idInforme, null, ipRemote, "automatic");
						} 					
					} else {
						if (idInforme.contains("-MOD-")) {	
							comentari = "S'ha realitzat la proposta de modificació" + idInforme ;
							TascaCore.novaTasca(conn, "generica", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Partida modificació actuació", idInforme, null, ipRemote, "automatic");
						}
					}				
			    } else if ("autoritzacioAreaFinanceraJudicial".equals(document)) {
			    	AssignacioCredit assignacio = new AssignacioCredit();
			    	String idProcediment = multipartParams.getParametres().get("idprocediment");
			    	String importReserva = multipartParams.getParametres().get("importReserva");
			    	int idTramitacio = Integer.valueOf(multipartParams.getParametres().get("idTramit"));
			    	JudicialCore.guardarFitxerTramitacio(conn, multipartParams.getFitxers(), idProcediment, idTramitacio, Usuari.getIdUsuari());
		    		assignacio = JudicialCore.getPartidaTramit(conn, idProcediment, multipartParams.getParametres().get("idTramit"));		    			
			    	
				    String comentariHistoral = "S'ha reservat l'import de " + importReserva + "€ de la partida " + assignacio.getPartida().getCodi();
			   		String tipus = "automatic";
			   		TascaCore.nouHistoric(conn, idTasca, comentariHistoral, Usuari.getIdUsuari(), ipRemote, tipus);
			   		TascaCore.reasignar(conn, 901, idTasca, tasca.getTipus(), tasca.getDescripcio());
			   		TascaCore.tancar(conn, idTasca);
			   		int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
					String comentari = "S'ha reservat l'import de " + importReserva + "€ de la partida " + assignacio.getPartida().getCodi();
					TascaCore.novaTasca(conn, "partidaJudicial", idUsuari, Usuari.getIdUsuari(), multipartParams.getParametres().get("idTramit"), "-1", comentari, "Partida judicial", idProcediment, null, ipRemote, "automatic");
			    } else if ("certificatCredit".equals(document)) {
			    	InformeActuacio informe = new InformeActuacio();
			    	if (idInforme.contains("-MOD-")) {			    		 
					    informe = InformeCore.getMoficacioInforme(conn, idInforme, false);
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", informe.getIdInfOriginal(), idInforme, "Conforme àrea financera", Usuari.getIdUsuari());			    		
			    	} else {
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Conforme àrea financera", Usuari.getIdUsuari());			    		 
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	}
			   		TascaCore.tancar(conn, idTasca);
			   		int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,JUR").get(0).getIdUsuari();
					String comentari = "S'ha reservat partida per l'expedient: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesT'>" + informe.getExpcontratacio() + "</a>";
					TascaCore.novaTasca(conn, "generic", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Certificat existència de crèdit", idInforme, null, ipRemote, "automatic");
				} else if ("autoritzacioPA".equals(document)) {
			    	InformeActuacio informe = new InformeActuacio();
			    	if (idInforme.contains("-MOD-")) {
			    		informe = InformeCore.getMoficacioInforme(conn, idInforme, false);
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", informe.getIdInfOriginal(), idInforme, "Autorització Proposta modificació", Usuari.getIdUsuari());
			    		TascaCore.nouHistoric(conn, idTasca, "Autorització Proposta modificació", Usuari.getIdUsuari(), ipRemote, "automatic");			   		
				   		TascaCore.tancar(conn, idTasca);
				   		CreditCore.assignar(conn, idInforme, informe.getOfertaSeleccionada().getPlic());
				   		if (informe.getUsuariCapValidacio() != null) {
				   			int idUsuariJur = UsuariCore.findUsuarisByRol(conn, "CAP,JUR").get(0).getIdUsuari();
				   			TascaCore.novaTasca(conn, "generic", informe.getUsuariCapValidacio().getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, "S'ha aprovat la despesa de modificació per a l'informe: " + informe.getIdInfOriginal(), "Aprovació proposta despesa modificació",informe.getIdInf(),null, ipRemote, "automatic");
				   			TascaCore.novaTasca(conn, "generic", idUsuariJur, Usuari.getIdUsuari(), idActuacio, idIncidencia, "S'ha aprovat la despesa de modificació per a l'informe: " + informe.getIdInfOriginal(), "Formalització modificació",informe.getIdInf(),null, ipRemote, "automatic");
				   		}
			    	} else {			    	
				    	//aprovam actuació
						ActuacioCore.aprovarPA(conn, idActuacio, Usuari.getIdUsuari());				
						//aprovam informe
						InformeCore.aprovacioInforme(conn, idInforme, Usuari.getIdUsuari(), new Date());  
						if (multipartParams.getFitxersByName().get("ordreInici") != null) {
							Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("ordreInici"), idIncidencia, idActuacio, "", "", "", idInforme, "Memòria odre inici", Usuari.getIdUsuari());
					    }
				    	Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("certCredit"), idIncidencia, idActuacio, "", "", "", idInforme, "Conforme àrea financera", Usuari.getIdUsuari());
				    	TascaCore.nouHistoric(conn, idTasca, "Autorització Proposta d'actuació", Usuari.getIdUsuari(), ipRemote, "automatic");			   		
				   		TascaCore.tancar(conn, idTasca);
				   		InformeCore.modificarEstat(conn, idInforme, "licitacio");
				    	String tipus = "";				    	
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
					   	int usuariTasca = -1;			   	
		   				if (("obr".equals(informe.getPropostaInformeSeleccionada().getTipusObra()) && informe.getPropostaInformeSeleccionada().getPbase() > 50000) || (!"obr".equals(informe.getPropostaInformeSeleccionada().getTipusObra()) && informe.getPropostaInformeSeleccionada().getPbase() > 18000)) { //Contracte d'obres major   					
		   					usuariTasca = UsuariCore.finCap(conn, "juridica").getIdUsuari();
		   					tipus = "liciMajor";	   					
		   				}else{ //Contracte d'obres menor
		   					usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariRecercaPresuposts"));   			
		   					tipus = "liciMenor";
		   				}
		   				//Registrar tasca nova
		   				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Sol·licitud proposta tècnica");
		   				TascaCore.novaTasca(conn, tipus, usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", "",informe.getIdInf(),null, ipRemote, "automatic");
		   				
		   				//Cream expedient	
		   				String nouCodi = "";
		   				if (informe.getExpcontratacio() == null || informe.getExpcontratacio().getExpContratacio() == null || informe.getExpcontratacio().getExpContratacio().isEmpty()) {
		   					nouCodi = ExpedientCore.crearExpedient(conn, informe, false, "", informe.getUsuari().getIdUsuari());
		   				} else {
		   					nouCodi = informe.getExpcontratacio().getExpContratacio();
		   				}
		   				
		   				//Nova tasca llicència
		   				if (informe.getLlicencia() == null && informe.getPropostaInformeSeleccionada().isLlicencia() && informe.getPropostaInformeSeleccionada().getTipusLlicencia().equals("llicencia")) {
	   						usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariLlicencies"));   		
	   						tipus = "";
	   						TascaCore.novaTasca(conn, tipus, usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Sol·licitar llicència obra per expedient " + nouCodi, "Sol·licitud llicència",informe.getIdInf(),null, ipRemote, "automatic");
	   						//LlicenciaCore.novaLlicencia(conn, nouCodi, informe.getPropostaInformeSeleccionada().getTipusLlicencia());
		   				}
			    	}
				} else if ("docTecnica".equals(document)) {
					InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
				    PropostaInforme proposta = informe.new PropostaInforme();
				    if (!informe.getLlistaPropostes().isEmpty() && informe.getLlistaPropostes().get(0) != null) {
				    	 proposta = informe.getLlistaPropostes().get(0);
				    }		
					if (proposta.getPbase() > 0) {
						int idTecnic = Integer.parseInt(multipartParams.getParametres().get("llistaUsuaris"));	
		    		    String tipusObra = multipartParams.getParametres().get("tipusContracte");
		    		    String objecte = multipartParams.getParametres().get("objecteActuacio");
		    		    boolean llicencia = false;
		    		    String reqLlicencia = multipartParams.getParametres().get("reqLlicencia");
		    		    String tipusLlicencia = "";
		    		    
		    		    double pbase = 0;
		    			double iva = 0;
		    			double plic = 0;
		    			String termini = "";
		    			String comentari = "";
		    			String comentariAdministratiu = "";
				 	   
					    List<PropostaInforme> llistaPropostes = new ArrayList<PropostaInforme>(); 
					    proposta.setObjecte(objecte);
					    proposta.setTipusObra(tipusObra);
					    if (new String("obr").equals(tipusObra)) {
					    	llicencia = new String("si").equals(reqLlicencia);	 	   
							if (llicencia) tipusLlicencia = multipartParams.getParametres().get("tipusLlicencia") ;
					    }	
					    proposta.setLlicencia(llicencia);
					    proposta.setTipusLlicencia(tipusLlicencia);
					    proposta.setContracte(true);
					    pbase = Double.parseDouble(multipartParams.getParametres().get("pbase").replace(',','.'));
					    iva = Double.parseDouble(multipartParams.getParametres().get("iva"));
					    plic = Double.parseDouble(multipartParams.getParametres().get("plic").replace(',','.'));
					    proposta.setPbase(pbase);
					    proposta.setIva(iva);
					    proposta.setPlic(plic);
					    termini = multipartParams.getParametres().get("termini" );
					    comentari = multipartParams.getParametres().get("comentariTecnic");
					    comentariCap = multipartParams.getParametres().get("comentariCap");
					    comentariAdministratiu = multipartParams.getParametres().get("comentariAdministratiu");
					    proposta.setTermini(termini);
					    proposta.setComentari(comentari);
					    proposta.setComentariAdministratiu(comentariAdministratiu);
					    proposta.setSeleccionada(true);			    
					    llistaPropostes.add(proposta);
					    informe.setLlistaPropostes(llistaPropostes);
					    informe.setPropostaInformeSeleccionada(proposta);	
					    informe.setComentariCap(comentariCap);
					    InformeCore.modificarInforme(conn, informe, idTecnic);	 
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
					    InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInforme);	   
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);						
					}
					Fitxers.guardarFitxer(conn, multipartParams.getFitxers(), idIncidencia, idActuacio, "", "", "", idInforme, "Documentació tècnica", Usuari.getIdUsuari());
					if (tramitar != null) {
						if (idInforme == null || idInforme.isEmpty() || idInforme.equals("0")) {
							Actuacio actuacio = new Actuacio();
							actuacio.setReferencia(idActuacio);					
							informe.setIdTasca(idTasca);
							informe.setIdIncidencia(idIncidencia);
							informe.setActuacio(actuacio);
							idInforme = InformeCore.nouInforme(conn, informe, Usuari.getIdUsuari());
							TascaCore.actualitzarInforme(conn, idTasca, idInforme);
						}
						int usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariOrdreInici"));   	
						//TascaCore.novaTasca(conn, "docprelicitacio", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Prepara documentació per a licitació expedient ", "Preparació documentació expedient",informe.getIdInf(),null, ipRemote, "automatic");
						TascaCore.nouHistoric(conn, idTasca, "Documentació enviada per licitar", Usuari.getIdUsuari(), ipRemote, "automatic");
						TascaCore.reasignar(conn, usuariTasca, idTasca, "docprelicitacio", "Preparar documentació per a la licitació");
						//TascaCore.tancar(conn, idTasca);
					}
			    } else if ("propostaTecnica".equals(document)) {
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, true);
			   		if (UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari() == Usuari.getIdUsuari()) {
			   			//Eliminam proposta tècnica amb 1 firma (per evitar duplicats)
				    	Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta tècnica", Usuari.getIdUsuari());
				    	
				    	//Registrar comentari;	 
				    	OfertaCore.validacioCapOferta(conn, idInforme, Usuari.getIdUsuari());	   			
			   			ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta tècnica realitzada");
				   		TascaCore.nouHistoric(conn, idTasca, "Proposta tècnica aprovada", Usuari.getIdUsuari(), ipRemote, "automatic");
			   			TascaCore.reasignar(conn, 902, idTasca, tasca.getTipus(), tasca.getDescripcio());
						TascaCore.tancar(conn, idTasca);
						int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
						String comentari = "S'ha realitzat la proposta tècnica: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesAprov'>" + idActuacio + "</a>";
						TascaCore.novaTasca(conn, "autoritzacioDespesa", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Proposta tècnica " + idInforme + " realitzada", idInforme, null, ipRemote, "automatic");						
					} else {
			   			Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta tècnica", Usuari.getIdUsuari());
				    	//Registrar comentari;	
				   		String comentariHistoral = "<span class='missatgeAutomatic'>El tècnic proposa: " + informe.getOfertaSeleccionada().getNomEmpresa() + "</span><br>" + informe.getOfertaSeleccionada().getComentari() + "<br><span class='missatgeAutomatic'>Amb un termini d'execució de: " + informe.getOfertaSeleccionada().getTermini() + "</span>";	   		
			   			TascaCore.nouHistoric(conn, idTasca, comentariHistoral, Usuari.getIdUsuari(), ipRemote, "manual");
				   		TascaCore.reasignar(conn, UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), idTasca, tasca.getTipus(), tasca.getDescripcio());	
				   		if (informe.getPropostaInformeSeleccionada().isEbss() || informe.getPropostaInformeSeleccionada().isCoordinacio()) {
							String comentari = "";
							if (informe.getPropostaInformeSeleccionada().isEbss() && informe.getPropostaInformeSeleccionada().isCoordinacio()) {
								comentari = "Proposta d'actuació per la contratació de EBSS + Coordinació";
							} else if (informe.getPropostaInformeSeleccionada().isEbss()) {
								comentari = "Proposta d'actuació per la contratació d'EBSS";
							}	else {
								comentari = "Proposta d'actuació per la contratació de Coordinació";
							}
				   			TascaCore.novaTasca(conn, "infPrev", UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "", "", null, ipRemote, "automatic");
						}
			   		}		
			    } else if ("documentsPreLicitacio".equals(document)) {
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	if (multipartParams.getFitxersByName().get("memoriaOrdreInici") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("memoriaOrdreInici")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Memòria odre inici", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("justProcForma") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("justProcForma")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Justificació procediment i forma", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("justCriterisAdjudicacio") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("justCriterisAdjudicacio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Justificació criteris adjudicació", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("declaracioUrgencia") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("declaracioUrgencia")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Declaració urgència", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("aprovacioEXPPlecsDespesa") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("aprovacioEXPPlecsDespesa")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Aprovació expedient", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("aprovacioDispoTerrenys") != null) {
		    	    	int idNovaTasca = TascaCore.novaTasca(conn, "generic", UsuariCore.finCap(conn, informe.getUsuari().getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, "Procedir al replanteig del projecte en els termes de l’article 236 LCSP. </br>Un cop realitzat retornar la tasca a l'àrea de contractació.", "Aprovació disposició terrenys", idInforme, null, ipRemote, "automatic");
		    	    	TascaCore.seguirTasca(conn, idNovaTasca, UsuariCore.finCap(conn, "juridica").getIdUsuari());
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("aprovacioDispoTerrenys")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Disponibilitat terrenys", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("insuficienciaMitjans") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("insuficienciaMitjans")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Insuficiencia mitjans", Usuari.getIdUsuari());
		    	    }
		    	    TascaCore.tancar(conn, idTasca);
			    } else if ("documentsRatClassificacio".equals(document)) {
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	if (multipartParams.getFitxersByName().get("ratificacioClassificacio") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("ratificacioClassificacio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Ratificació classificació", Usuari.getIdUsuari());
		    	    }		
			    	if (multipartParams.getFitxersByName().get("resolucioVAD") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("resolucioVAD")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Resolució VAD", Usuari.getIdUsuari());
		    	    }		
		    	    TascaCore.tancar(conn, idTasca);
			    } else if ("contracteSignat".equals(document)) {
			    	Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Contracte signat", Usuari.getIdUsuari());
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	if (informe.getUsuariCapValidacio() != null) TascaCore.novaTasca(conn, "generic", informe.getUsuariCapValidacio().getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, "S'ha firmat el contracte per: " + informe.getIdInf(), "Contracte firmat",informe.getIdInf(),null, ipRemote, "automatic");
			    } else if ("conformarFactura".equals(document)) {
			    	TascaCore.tancar(conn, idTasca);
			    	Factura factura = FacturaCore.getFactura(conn, idFactura);
			    	factura.setDataConformacio(new Date());
			    	int usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariFactures"));   	
			    	TascaCore.novaTasca(conn, "facturaConformada", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "factura conformada", "Factura conformada", idFactura, null, ipRemote, "automatic");
			    	FacturaCore.saveArxiu(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia(), idActuacio, idInforme, factura.getIdProveidor(), idFactura, multipartParams.getFitxers(), conn, Usuari.getIdUsuari());
			    	FacturaCore.modificarFactura(conn, factura, Usuari.getIdUsuari());
			    } else if("certificacioValidada".equals(document)) {
			    	TascaCore.tancar(conn, idTasca);
			    	Factura certificacio = FacturaCore.getCertificacio(conn, idFactura);
			    	Actuacio actuacio = ActuacioCore.findActuacio(conn, certificacio.getIdActuacio());
			        TascaCore.novaTasca(conn, "firmaCertificacio", UsuariCore.finCap(conn, certificacio.getUsuariConformador().getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, "Firma certificació", "Firma certificació", idFactura, null, ipRemote, "automatic");
			        
	   				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    	        Date date = new Date();
	    	        for (int x=0;x<certificacio.getCertificacions().size()-1;x++) {
	    	        	PdfReader reader = new PdfReader(certificacio.getCertificacions().get(x).getRuta());
		    	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(certificacio.getCertificacions().get(x).getRuta().replace(".pdf", "RE.pdf")));
		    	        BaseFont font = BaseFont.createFont(); // Helvetica, WinAnsiEncoding
		    	        for (int i = 0; i < reader.getNumberOfPages(); ++i) {
		    	          PdfContentByte overContent = stamper.getOverContent( i + 1 );
		    	          overContent.saveState();
		    	          overContent.beginText();
		    	          overContent.setFontAndSize( font, 10.0f );
		    	          overContent.setTextMatrix( 24, 24 );
		    	          overContent.showText("Data registre " + dateFormat.format(date));
		    	          overContent.endText();
		    	          overContent.restoreState();
		    	        }
		    	        stamper.close();
		    	        reader.close();
		    	        Fitxers.eliminarFitxer(conn, Usuari.getIdUsuari(), certificacio.getCertificacions().get(x).getRuta());	
	    	        }	    	        
	    	        Registre registre = new Registre(RegistreCore.getNewCode(conn, "E"), date, "E", certificacio.getNomProveidor(), "Certificació " + idFactura, idIncidencia, certificacio.getIdInforme(), actuacio.getCentre().getIdCentre(), certificacio.getUsuariConformador().getIdUsuari(), date);
		   			RegistreCore.nouRegistre(conn, "E", registre);
			    } else if("conformarCertificacio".equals(document)) {
			    	TascaCore.tancar(conn, idTasca);
			    	Factura certificacio = FacturaCore.getCertificacio(conn, idFactura);
			    	int usuariTasca = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
			    	if (Usuari.getDepartament().equals("gerencia")) {
			    		usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariCertificacions")); 
			    		TascaCore.novaTasca(conn, "certificacioFirmada", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Certificació firmada", "Certificació firmada", idFactura, null, ipRemote, "automatic");
			    		InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
				 		if (certificacio.getTipus().equals("final")) {
				 				if (informe.getTotalCertificat() > informe.getOfertaSeleccionada().getPlic() + informe.getTotalModificacions()) {
				 					//Excés d'amidaments.
				 					Double totalExces = (informe.getTotalCertificat()) - (informe.getOfertaSeleccionada().getPlic() + informe.getTotalModificacions());
				 					System.out.println("total certificat " + informe.getTotalCertificat());
				 					System.out.println("total oferta " + informe.getOfertaSeleccionada().getPlic());
				 					System.out.println("total modificacions " + informe.getTotalModificacions());
				 					PropostaInforme proposta = informe.new PropostaInforme();
				 			  		proposta.setTipusObra(informe.getPropostaInformeSeleccionada().getTipusObra());
				 			  		proposta.setLlicencia(false);
				 			  		proposta.setObjecte("Excés d'amidaments Art. 242.4 lletra i) LCSP");
				 			  		proposta.setPbase(totalExces / 1.21);
				 			  		proposta.setIva(totalExces - (totalExces / 1.21));
				 			  		proposta.setPlic(totalExces);
				 			  		proposta.setRetencio(false);
				 			  		proposta.setTermini("");
				 			  		proposta.setOcults(false);		 			  		
				 			  						 			  		
				 					Oferta ofertaProposta = new Oferta();
				 			  		ofertaProposta.setIdActuacio(informe.getActuacio().getReferencia());
			 				  		ofertaProposta.setCifEmpresa(informe.getOfertaSeleccionada().getCifEmpresa());
			 				  		ofertaProposta.setPlic(totalExces);
			 				   		ofertaProposta.setPbase(totalExces / 1.21);
			 				   		ofertaProposta.setIva(totalExces - (totalExces / 1.21));			
			 				  		ofertaProposta.setComentari("Excés d'amidaments Art. 242.4 lletra i) LCSP");		 				  		
			 				  		
			 				  		//Cream modificació	  		
				 			  		String idModificacio = InformeCore.afegirModificacioInforme(conn, idInforme, proposta, ofertaProposta, informe.getUsuari(), "certfinal");
				 			  		ofertaProposta.setIdInforme(idModificacio);
				 			  		OfertaCore.novaOferta(conn, ofertaProposta, Usuari.getIdUsuari());
				 			  		int idUsuariTasca = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
				 			  		String idModificacioAUX = idModificacio.split("#")[0];
				 			  		TascaCore.novaTasca(conn, "resPartidaModificacio", idUsuariTasca, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getActuacio().getIdIncidencia(), "Modificació expedient " + informe.getExpcontratacio().getExpContratacio(), "Modificació expedient " + informe.getExpcontratacio().getExpContratacio(), idModificacioAUX, null, request.getRemoteAddr(), "automatic");
				 				
				 				} else if(informe.getTotalCertificat() < informe.getOfertaSeleccionada().getPlic() + informe.getTotalModificacions()) {
				 					//Finalització amb import menor.
				 					String comentari = "Certificació final amb import menor";
				 					int idUsuariTasca = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
				 		  			TascaCore.novaTasca(conn, "generica", idUsuariTasca, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getActuacio().getIdIncidencia(), comentari, "Certificació final amb import menor", idInforme, null, request.getRemoteAddr(), "automatic");
				 				}
				 			
				 		}			    	
			    	} else {
			    		TascaCore.novaTasca(conn, "firmaCertificacio", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Firma certificació", "Firma certificació", idFactura, null, ipRemote, "automatic");
				    }
			    	FacturaCore.saveArxiuCertificacio(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia(), idActuacio, idInforme, certificacio.getIdProveidor(), idFactura, multipartParams.getFitxers(), conn, Usuari.getIdUsuari());
			    } else if ("pagamentJudicial".equals(document)) {
			    	String idProcediment = multipartParams.getParametres().get("idProcediment");
			    	int idTramitacio = Integer.valueOf(multipartParams.getParametres().get("idTramitacio"));
			    	JudicialCore.guardarFitxerTramitacio(conn, multipartParams.getFitxers(), idProcediment, idTramitacio, Usuari.getIdUsuari());
			    }
			} catch (DocumentException e) {
				errorString = e.toString();
				e.printStackTrace();
			}	    	
	    } else {
	    	if ("facturaConformada".equals(document)) {
				TascaCore.tancar(conn, idTasca);				
				Factura factura = FacturaCore.getFactura(conn, idFactura);
				factura.setDataEnviatComptabilitat(new Date());
				FacturaCore.modificarFactura(conn, factura, Usuari.getIdUsuari());
				InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
				//Obra menor
				if (informe.getOfertaSeleccionada().getPlic() < ConfiguracioCore.getConfiguracio(conn).getImportObraMajor()) {
					if (informe.getOfertaSeleccionada().getPlic() > informe.getTotalFacturat()) {
							   				
					} else {
						InformeCore.modificarEstat(conn, factura.getIdInforme(), "acabat");
						InformeCore.tancar(conn, factura.getIdInforme());
					}		   				
				}
			} else {
				errorString = "Falta adjuntar document";
			}	    	
	    }
	   
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/tasca/tascaView.jsp");	        
	       dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {	   		
	   		if ("paTecnic".equals(document) || "docTecnica".equals(document) || "pagamentJudicial".equals(document) || "partidaJudicial".equals(document)) {
	   			response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);  
	   		}else{
	   			response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);  
	   		}
	   	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
