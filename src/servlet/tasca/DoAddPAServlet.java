package servlet.tasca;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import bean.Actuacio;
import bean.Expedient;
import bean.Factura;
import bean.Incidencia;
import bean.InformeActuacio;
import bean.Registre;
import bean.Tasca;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import core.ActuacioCore;
import core.CentreCore;
import core.CreditCore;
import core.EmpresaCore;
import core.ExpedientCore;
import core.FacturaCore;
import core.IncidenciaCore;
import core.InformeCore;
import core.JudicialCore;
import core.LlicenciaCore;
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
@WebServlet("/DoAddPA")
public class DoAddPAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddPAServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
	    String errorString = null;
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	
	    if (multipartParams.getParametres().get("idTasca") != null) {
			idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));	 
			try {
				tasca = TascaCore.findTascaId(conn, idTasca, Usuari.getIdUsuari());
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				    proposta.setTermini(termini);
				    proposta.setComentari(comentari);
				    proposta.setSeleccionada(true);			    
				    llistaPropostes.add(proposta);
				    informe.setLlistaPropostes(llistaPropostes);
				    informe.setPropostaInformeSeleccionada(proposta);	
				    InformeCore.modificarInforme(conn, informe, idTecnic);	 
				    informe = InformeCore.getInformePrevi(conn, idInforme, false);
				    InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInforme);	   
				    informe = InformeCore.getInformePrevi(conn, idInforme, false);
				    if (modificar == null && (informe.getExpcontratacio() == null || informe.getExpcontratacio().getExpContratacio().equals("-1"))) {
	   					//Cream expedient	   						
		   				double importObraMajor = Double.parseDouble(getServletContext().getInitParameter("importObraMajor"));
		   				if ("srv".equals(tipusObra)) {
		   					importObraMajor = Double.parseDouble(getServletContext().getInitParameter("importServeiMajor"));
		   				} else if ("submi".equals(tipusObra)) {
		   					importObraMajor = Double.parseDouble(getServletContext().getInitParameter("importSubministramentMajor"));
		   				}
	   					String nouCodi = "";
		   				nouCodi = ExpedientCore.crearExpedient(conn, informe, importObraMajor, false, "");	   				
		   				informe.setExpcontratacio(ExpedientCore.findExpedient(conn, nouCodi));	 
		   				int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
						TascaCore.novaTasca(conn, "resPartida", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", String.valueOf(idTasca), idInforme, null, ipRemote, "automatic");					
	   				}
	    		} else if ("memoriaInici".equals(document)) {
	    			Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Mem�ria odre inici", Usuari.getIdUsuari());
	    			InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	if (informe.getLlistaPropostes().size() == 1) {
			    		InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInforme);
			    	}
			    	TascaCore.tancar(conn, idTasca);	
			    	int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
					String comentari = "Documentaci� prelicitaci� per signar: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesT'>" + informe.getExpcontratacio().getExpContratacio() + "</a>";
					TascaCore.novaTasca(conn, "autoritzacioActuacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Documentaci� prelicitaci� per signar", idInforme, null, ipRemote, "automatic");
	    		} else if ("autoritzacioCap".equals(document)) {
			    	Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Vistiplau cap", Usuari.getIdUsuari());
			    	TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Informe aprovat", Usuari.getIdUsuari(), ipRemote, "automatic");
					ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta d'actuaci� realitzada");
					TascaCore.reasignar(conn, 900, idTasca, tasca.getTipus(), tasca.getDescripcio());
					TascaCore.tancar(conn, idTasca);
					int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
					TascaCore.novaTasca(conn, "resPartida", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", String.valueOf(idTasca), idInforme, null, ipRemote, "automatic");					
	    		} else if ("autoritzacioAreaFinancera".equals(document)) {
			    	InformeActuacio informe = new InformeActuacio();
			    	if (idInforme.contains("-MOD-")) {			    		 
					    informe = InformeCore.getMoficacioInforme(conn, idInforme);
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", informe.getIdInfOriginal(), idInforme, "Conforme �rea financera", Usuari.getIdUsuari());			    		
			    	} else {
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Conforme �rea financera", Usuari.getIdUsuari());			    		 
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	}		
				    String comentariHistoral = "S'ha reservat l'import de " + informe.getPropostaInformeSeleccionada().getPlicFormat() + "� de la partida " + informe.getAssignacioCredit().getPartida().getNom();
			   		String tipus = "automatic";
				    if(informe.getDataRebujada() != null) {	
				    	tipus = "manual";
				   		comentariHistoral = "<span class='missatgeAutomatic'>S'ha rebutjat l'import de " + informe.getPropostaInformeSeleccionada().getPlicFormat() + "� de la partida " + informe.getAssignacioCredit().getPartida().getNom() + "</span>";
				   		comentariHistoral += "</br>Motiu: " + informe.getPartidaRebutjadaMotiu(); 
				   	}
			   		TascaCore.nouHistoric(conn, String.valueOf(idTasca), comentariHistoral, Usuari.getIdUsuari(), ipRemote, tipus);
			   		TascaCore.reasignar(conn, 901, idTasca, tasca.getTipus(), tasca.getDescripcio());
			   		TascaCore.tancar(conn, idTasca);
			   		int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
					String comentari = "S'ha realitzat la proposta d'actuaci�: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesT'>" + idActuacio + "</a>";
					if (informe.getExpcontratacio().getIdInforme() != null && !informe.getExpcontratacio().getIdInforme().equals("-1") && informe.getExpcontratacio().getContracte().equals("major")) {
						if (idInforme.contains("-MOD-")) {	
							comentari = "S'ha realitzat la proposta de modificaci�" + idInforme ;
							TascaCore.novaTasca(conn, "autoritzacioModificacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Autoritzaci� modificaci� actuaci�", idInforme, null, ipRemote, "automatic");
						} 					
					} else {
						if (idInforme.contains("-MOD-")) {	
							comentari = "S'ha realitzat la proposta de modificaci�" + idInforme ;
							TascaCore.novaTasca(conn, "autoritzacioModificacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Autoritzaci� modificaci� actuaci�", idInforme, null, ipRemote, "automatic");
						}
					}
			    } else if ("certificatCredit".equals(document)) {
			    	InformeActuacio informe = new InformeActuacio();
			    	if (idInforme.contains("-MOD-")) {			    		 
					    informe = InformeCore.getMoficacioInforme(conn, idInforme);
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", informe.getIdInfOriginal(), idInforme, "Conforme �rea financera", Usuari.getIdUsuari());			    		
			    	} else {
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Conforme �rea financera", Usuari.getIdUsuari());			    		 
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	}
			   		TascaCore.tancar(conn, idTasca);
			   		int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,JUR").get(0).getIdUsuari();
					String comentari = "S'ha reservat partida per l'expedient: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesT'>" + informe.getExpcontratacio() + "</a>";
					TascaCore.novaTasca(conn, "generic", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Certificat exist�ncia de cr�dit", idInforme, null, ipRemote, "automatic");
				} else if ("autoritzacioPA".equals(document)) {
			    	InformeActuacio informe = new InformeActuacio();
			    	if (idInforme.contains("-MOD-")) {
			    		informe = InformeCore.getMoficacioInforme(conn, idInforme);
			    		Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", informe.getIdInfOriginal(), idInforme, "Autoritzaci� Proposta modificaci�", Usuari.getIdUsuari());
			    		TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Autoritzaci� Proposta modificaci�", Usuari.getIdUsuari(), ipRemote, "automatic");			   		
				   		TascaCore.tancar(conn, idTasca);
				   		CreditCore.assignar(conn, idInforme, informe.getOfertaSeleccionada().getPlic());
				   		if (informe.getUsuariCapValidacio() != null) TascaCore.novaTasca(conn, "generic", informe.getUsuariCapValidacio().getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, "S'ha aprovat la despesa de modificaci� per a l'informe: " + informe.getIdInfOriginal(), "Aprovaci� proposta despesa modificaci�",informe.getIdInf(),null, ipRemote, "automatic");
			    	} else {			    	
				    	//aprovam actuaci�
						ActuacioCore.aprovarPA(conn, idActuacio, Usuari.getIdUsuari());				
						//aprovam informe
						InformeCore.aprovacioInforme(conn, idInforme, Usuari.getIdUsuari(), new Date());  
						if (multipartParams.getFitxersByName().get("ordreInici") != null) {
							Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("ordreInici"), idIncidencia, idActuacio, "", "", "", idInforme, "Mem�ria odre inici", Usuari.getIdUsuari());
					    }
				    	Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("certCredit"), idIncidencia, idActuacio, "", "", "", idInforme, "Conforme �rea financera", Usuari.getIdUsuari());
				    	TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Autoritzaci� Proposta d'actuaci�", Usuari.getIdUsuari(), ipRemote, "automatic");			   		
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
		   				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Sol�licitud proposta t�cnica");
		   				TascaCore.novaTasca(conn, tipus, usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", "",informe.getIdInf(),null, ipRemote, "automatic");
		   				
		   				//Cream expedient	   						
		   				double importObraMajor = Double.parseDouble(getServletContext().getInitParameter("importObraMajor"));
		   				String nouCodi = "";
		   				if (informe.getExpcontratacio() == null || informe.getExpcontratacio().getExpContratacio() == null || informe.getExpcontratacio().getExpContratacio().isEmpty()) {
		   					nouCodi = ExpedientCore.crearExpedient(conn, informe, importObraMajor, false, "");
		   				} else {
		   					nouCodi = informe.getExpcontratacio().getExpContratacio();
		   				}
		   				
		   				//Nova tasca llic�ncia
		   				if (informe.getLlicencia() == null && informe.getPropostaInformeSeleccionada().isLlicencia() && informe.getPropostaInformeSeleccionada().getTipusLlicencia().equals("llicencia")) {
	   						usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariLlicencies"));   		
	   						tipus = "";
	   						TascaCore.novaTasca(conn, tipus, usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Sol�licitar llic�ncia obra per expedient " + nouCodi, "Sol�licitud llic�ncia",informe.getIdInf(),null, ipRemote, "automatic");
	   						LlicenciaCore.novaLlicencia(conn, nouCodi, informe.getPropostaInformeSeleccionada().getTipusLlicencia());
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
					    proposta.setTermini(termini);
					    proposta.setComentari(comentari);
					    proposta.setSeleccionada(true);			    
					    llistaPropostes.add(proposta);
					    informe.setLlistaPropostes(llistaPropostes);
					    informe.setPropostaInformeSeleccionada(proposta);	
					    InformeCore.modificarInforme(conn, informe, idTecnic);	 
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
					    InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInforme);	   
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);						
					}
					Fitxers.guardarFitxer(conn, multipartParams.getFitxers(), idIncidencia, idActuacio, "", "", "", idInforme, "Documentaci� t�cnica", Usuari.getIdUsuari());
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
						//TascaCore.novaTasca(conn, "docprelicitacio", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Prepara documentaci� per a licitaci� expedient ", "Preparaci� documentaci� expedient",informe.getIdInf(),null, ipRemote, "automatic");
						TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Documentaci� enviada per licitar", Usuari.getIdUsuari(), ipRemote, "automatic");
						TascaCore.reasignar(conn, usuariTasca, idTasca, "docprelicitacio", "Preparar documentaci� per a la licitaci�");
						//TascaCore.tancar(conn, idTasca);
					}
			    } else if ("propostaTecnica".equals(document)) {
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, true);
			   		if (UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari() == Usuari.getIdUsuari()) {
			   			//Eliminam proposta t�cnica amb 1 firma (per evitar duplicats)
				    	Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta t�cnica", Usuari.getIdUsuari());
				    	
				    	//Registrar comentari;	 
				    	OfertaCore.validacioCapOferta(conn, idInforme, Usuari.getIdUsuari());	   			
			   			ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta t�cnica realitzada");
				   		TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Proposta t�cnica aprovada", Usuari.getIdUsuari(), ipRemote, "automatic");
			   			TascaCore.reasignar(conn, 902, idTasca, tasca.getTipus(), tasca.getDescripcio());
						TascaCore.tancar(conn, idTasca);
						int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
						String comentari = "S'ha realitzat la proposta t�cnica: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesAprov'>" + idActuacio + "</a>";
						TascaCore.novaTasca(conn, "autoritzacioDespesa", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Proposta t�cnica " + idInforme + " realitzada", idInforme, null, ipRemote, "automatic");						
					} else {
			   			Fitxers.guardarFitxer(conn, fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta t�cnica", Usuari.getIdUsuari());
				    	//Registrar comentari;	
				   		String comentariHistoral = "<span class='missatgeAutomatic'>El t�cnic proposa: " + informe.getOfertaSeleccionada().getNomEmpresa() + "</span><br>" + informe.getOfertaSeleccionada().getComentari() + "<br><span class='missatgeAutomatic'>Amb un termini d'execuci� de: " + informe.getOfertaSeleccionada().getTermini() + "</span>";	   		
			   			TascaCore.nouHistoric(conn, String.valueOf(idTasca), comentariHistoral, Usuari.getIdUsuari(), ipRemote, "manual");
				   		TascaCore.reasignar(conn, UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), idTasca, tasca.getTipus(), tasca.getDescripcio());	
				   		if (informe.getPropostaInformeSeleccionada().isEbss() || informe.getPropostaInformeSeleccionada().isCoordinacio()) {
							String comentari = "";
							if (informe.getPropostaInformeSeleccionada().isEbss() && informe.getPropostaInformeSeleccionada().isCoordinacio()) {
								comentari = "Proposta d'actuaci� per la contrataci� de EBSS + Coordinaci�";
							} else if (informe.getPropostaInformeSeleccionada().isEbss()) {
								comentari = "Proposta d'actuaci� per la contrataci� d'EBSS";
							}	else {
								comentari = "Proposta d'actuaci� per la contrataci� de Coordinaci�";
							}
				   			TascaCore.novaTasca(conn, "infPrev", UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "", "", null, ipRemote, "automatic");
						}
			   		}		
			    } else if ("documentsPreLicitacio".equals(document)) {
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	if (multipartParams.getFitxersByName().get("memoriaOrdreInici") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("memoriaOrdreInici")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Mem�ria odre inici", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("justProcForma") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("justProcForma")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Justificaci� procediment i forma", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("justCriterisAdjudicacio") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("justCriterisAdjudicacio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Justificaci� criteris adjudicaci�", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("declaracioUrgencia") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("declaracioUrgencia")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Declaraci� urg�ncia", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("aprovacioEXPPlecsDespesa") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("aprovacioEXPPlecsDespesa")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Aprovaci� expedient", Usuari.getIdUsuari());
		    	    }
		    	    if (multipartParams.getFitxersByName().get("aprovacioDispoTerrenys") != null) {
		    	    	int idNovaTasca = TascaCore.novaTasca(conn, "generic", UsuariCore.finCap(conn, informe.getUsuari().getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, "Procedir al replanteig del projecte en els termes de l�article 236 LCSP. </br>Un cop realitzat retornar la tasca a l'�rea de contractaci�.", "Aprovaci� disposici� terrenys", idInforme, null, ipRemote, "automatic");
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
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("ratificacioClassificacio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Ratificaci� classificaci�", Usuari.getIdUsuari());
		    	    }		
			    	if (multipartParams.getFitxersByName().get("resolucioVAD") != null) {
		    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("resolucioVAD")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Resoluci� VAD", Usuari.getIdUsuari());
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
			        TascaCore.novaTasca(conn, "firmaCertificacio", UsuariCore.finCap(conn, certificacio.getUsuariConformador().getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, "Firma certificaci�", "Firma certificaci�", idFactura, null, ipRemote, "automatic");
			        Context env;
	   				String ruta = "";
	   				try {
	   					env = (Context)new InitialContext().lookup("java:comp/env");
	   					ruta = (String)env.lookup("ruta_base");
	   				} catch (NamingException e2) {
	   					// TODO Auto-generated catch block
	   					e2.printStackTrace();
	   				}
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
	    	        Registre registre = new Registre(RegistreCore.getNewCode(conn, "E"), date, "E", certificacio.getNomProveidor(), "Certificaci� " + idFactura, idIncidencia, certificacio.getIdInforme(), actuacio.getCentre().getIdCentre(), certificacio.getUsuariConformador().getIdUsuari(), date);
		   			RegistreCore.nouRegistre(conn, "E", registre);
			    } else if("conformarCertificacio".equals(document)) {
			    	TascaCore.tancar(conn, idTasca);
			    	Factura certificacio = FacturaCore.getCertificacio(conn, idFactura);
			    	int usuariTasca = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
			    	if (Usuari.getDepartament().equals("gerencia")) {
			    		usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariCertificacions")); 
			    		TascaCore.novaTasca(conn, "certificacioFirmada", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Certificaci� firmada", "Certificaci� firmada", idFactura, null, ipRemote, "automatic");
			    	} else {
			    		TascaCore.novaTasca(conn, "firmaCertificacio", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Firma certificaci�", "Firma certificaci�", idFactura, null, ipRemote, "automatic");
				    }
			    	FacturaCore.saveArxiuCertificacio(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia(), idActuacio, idInforme, certificacio.getIdProveidor(), idFactura, multipartParams.getFitxers(), conn, Usuari.getIdUsuari());
			    } else if ("pagamentJudicial".equals(document)) {
			    	String idProcediment = multipartParams.getParametres().get("idProcediment");
			    	int idTramitacio = Integer.valueOf(multipartParams.getParametres().get("idTramitacio"));
			    	JudicialCore.guardarFitxerTramitacio(conn, multipartParams.getFitxers(), idProcediment, idTramitacio, Usuari.getIdUsuari());
			    }
			} catch (SQLException | NamingException | DocumentException e) {
				errorString = e.toString();
				e.printStackTrace();
			}	    	
	    } else {
	    	try {
	 		    if ("facturaConformada".equals(document)) {
	 		    	TascaCore.tancar(conn, idTasca);				
	 		    	Factura factura = FacturaCore.getFactura(conn, idFactura);
	 		    	factura.setDataEnviatComptabilitat(new Date());
	 		    	FacturaCore.modificarFactura(conn, factura, Usuari.getIdUsuari());	
	 		    	boolean totFacturat = true;
	 		    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
	   				if (informe.getOfertaSeleccionada().getPlic() > informe.getTotalFacturat()) {
	   					totFacturat = false;	   				
	   				} else {
	   					InformeCore.modificarEstat(conn, factura.getIdInforme(), "garantia");
	   					InformeCore.tancar(conn, factura.getIdInforme());
	   				}
	   				if (totFacturat) {
	   					List<InformeActuacio> informesList = InformeCore.getInformesActuacio(conn, factura.getIdInforme());
		   				for (InformeActuacio informeA : informesList) {
		   					if (!informeA.getIdInf().equals(factura.getIdInforme()) && !informeA.getEstatEconomic().equals("Facturat")) {
		   						totFacturat = false;
		   					}
		   				}
	   				}
	   				if (totFacturat) {
	   					ActuacioCore.tancar(conn, informe.getActuacio().getReferencia(), "facturat", Usuari.getIdUsuari());
	   				}
	 		    } else {
	 		    	errorString = "Falta adjuntar document";
	 		    }
	 	    } catch (SQLException | NamingException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 			errorString = e.toString();
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
	   		if ("paTecnic".equals(document) || "docTecnica".equals(document) || "pagamentJudicial".equals(document)) {
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
