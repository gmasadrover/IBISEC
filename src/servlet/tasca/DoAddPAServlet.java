package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Actuacio;
import bean.Expedient;
import bean.Factura;
import bean.InformeActuacio;
import bean.Tasca;
import bean.User;
import core.ActuacioCore;
import core.CreditCore;
import core.ExpedientCore;
import core.FacturaCore;
import core.InformeCore;
import core.LlicenciaCore;
import core.OfertaCore;
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
		Tasca tasca = new Tasca();		
		String document = multipartParams.getParametres().get("document");
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme"); 
	    String idFactura =  multipartParams.getParametres().get("idFactura"); 
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
	    if (!fitxers.isEmpty()) {
	    	try {
			    if ("paTecnic".equals(document)) {
			    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta actuació");
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	if (informe.getLlistaPropostes().size() == 1) {
			    		InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInforme);
			    	}
			    	TascaCore.reasignar(conn, UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), idTasca, tasca.getTipus());	
			    } else if ("autoritzacioCap".equals(document)) {
			    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Vistiplau cap");
			    	TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Informe aprovat", Usuari.getIdUsuari());
					ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta d'actuació realitzada");
					TascaCore.reasignar(conn, 900, idTasca, tasca.getTipus());
					TascaCore.tancar(conn, idTasca);
					int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
					TascaCore.novaTasca(conn, "resPartida", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", String.valueOf(idTasca), idInforme, null);					
			    } else if ("autoritzacioAreaFinancera".equals(document)) {
			    	InformeActuacio informe = new InformeActuacio();
			    	if (idInforme.contains("-MOD-")) {			    		 
					    informe = InformeCore.getMoficacioInforme(conn, idInforme);
			    		Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", informe.getIdInfOriginal(), idInforme, "Conforme àrea financera");			    		
			    	} else {
			    		Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Conforme àrea financera");			    		 
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	}		
				    String comentariHistoral = "S'ha reservat l'import de " + informe.getPropostaInformeSeleccionada().getPlicFormat() + "€ de la partida " + informe.getPartida();
			   		if(informe.getDataRebujada() != null) {			   		
				   		comentariHistoral = "S'ha rebutjat l'import de " + informe.getPropostaInformeSeleccionada().getPlicFormat() + "€ de la partida " + informe.getPartida();
				   		comentariHistoral += "</br>Motiu: " + informe.getPartidaRebutjadaMotiu(); 
				   	}
			   		TascaCore.nouHistoric(conn, String.valueOf(idTasca), comentariHistoral, Usuari.getIdUsuari());
			   		TascaCore.reasignar(conn, 901, idTasca, tasca.getTipus());
			   		TascaCore.tancar(conn, idTasca);
			   		int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
					String comentari = "S'ha realitzat la proposta d'actuació: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesT'>" + idActuacio + "</a>";
					if (informe.getExpcontratacio().getIdInforme() != null && !informe.getExpcontratacio().getIdInforme().equals("-1") && informe.getExpcontratacio().getContracte().equals("major")) {
						if (idInforme.contains("-MOD-")) {	
							comentari = "S'ha realitzat la proposta de modificació" + idInforme ;
							TascaCore.novaTasca(conn, "autoritzacioModificacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Autorització modificació actuació", idInforme, null);
						} else {
							comentari = "S'ha reservat partida per l'expedient: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesT'>" + informe.getExpcontratacio().getExpContratacio() + "</a>";
							TascaCore.novaTasca(conn, "certificatCreditGerencia", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Certificat existència de crèdit", idInforme, null);
						}						
					} else {
						if (idInforme.contains("-MOD-")) {	
							comentari = "S'ha realitzat la proposta de modificació" + idInforme ;
							TascaCore.novaTasca(conn, "autoritzacioModificacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Autorització modificació actuació", idInforme, null);
						} else {
							TascaCore.novaTasca(conn, "autoritzacioActuacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Autorització proposta actuació", idInforme, null);
						}
					}
			    } else if ("certificatCredit".equals(document)) {
			    	InformeActuacio informe = new InformeActuacio();
			    	if (idInforme.contains("-MOD-")) {			    		 
					    informe = InformeCore.getMoficacioInforme(conn, idInforme);
			    		Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", informe.getIdInfOriginal(), idInforme, "Conforme àrea financera");			    		
			    	} else {
			    		Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Conforme àrea financera");			    		 
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	}
			   		TascaCore.tancar(conn, idTasca);
			   		int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,JUR").get(0).getIdUsuari();
					String comentari = "S'ha reservat partida per l'expedient: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesT'>" + informe.getExpcontratacio() + "</a>";
					TascaCore.novaTasca(conn, "generic", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Certificat existència de crèdit", idInforme, null);
				} else if ("autoritzacioPA".equals(document)) {
			    	InformeActuacio informe = new InformeActuacio();
			    	if (idInforme.contains("-MOD-")) {
			    		informe = InformeCore.getMoficacioInforme(conn, idInforme);
			    		Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", informe.getIdInfOriginal(), idInforme, "Autorització Proposta modificació");
			    		TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Autorització Proposta modificació", Usuari.getIdUsuari());			   		
				   		TascaCore.tancar(conn, idTasca);
				   		CreditCore.assignar(conn, idInforme, informe.getOfertaSeleccionada().getPlic());
				   		if (informe.getUsuariCapValidacio() != null) TascaCore.novaTasca(conn, "generic", informe.getUsuariCapValidacio().getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, "S'ha aprovat la despesa de modificació per a l'informe: " + informe.getIdInfOriginal(), "Aprovació proposta despesa modificació",informe.getIdInf(),null);
			    	} else {			    	
				    	//aprovam actuació
						ActuacioCore.aprovarPA(conn, idActuacio, Usuari.getIdUsuari());				
						//aprovam informe
						InformeCore.aprovacioInforme(conn, idInforme, Usuari.getIdUsuari(), new Date());  
				    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Autorització Proposta d'actuació");
				    	TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Autorització Proposta d'actuació", Usuari.getIdUsuari());			   		
				   		TascaCore.tancar(conn, idTasca);
				    	String tipus = "";				    	
					    informe = InformeCore.getInformePrevi(conn, idInforme, false);
					   	int usuariTasca = -1;			   	
		   				if (("obr".equals(informe.getPropostaInformeSeleccionada().getTipusObra()) && informe.getPropostaInformeSeleccionada().getPbase() > 50000) || (!"obr".equals(informe.getPropostaInformeSeleccionada().getTipusObra()) && informe.getPropostaInformeSeleccionada().getPbase() > 18000)) { //Contracte d'obres major   					
		   					usuariTasca = UsuariCore.findUsuarisByRol(conn, "JUR").get(0).getIdUsuari();
		   					tipus = "liciMajor";	   					
		   				}else{ //Contracte d'obres menor
		   					if (informe.getUsuari().getDepartament().equals("instalacions")) {
		   						usuariTasca = informe.getUsuari().getIdUsuari();   			
		   					}else{
		   						usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariRecercaPresuposts"));   			
		   					}   							
							tipus = "liciMenor";
		   				}
		   				//Registrar tasca nova
		   				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Sol·licitud proposta tècnica");
		   				TascaCore.novaTasca(conn, tipus, usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", "",informe.getIdInf(),null);
		   				
		   				//Cream expedient	   						
		   				double importObraMajor = Double.parseDouble(getServletContext().getInitParameter("importObraMajor"));
		   				String nouCodi = ExpedientCore.crearExpedient(conn, informe, importObraMajor, false, "");
		   				
		   				//Nova tasca llicència
		   				if (informe.getPropostaInformeSeleccionada().isLlicencia() && informe.getPropostaInformeSeleccionada().getTipusLlicencia().equals("llicencia")) {
	   						usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariLlicencies"));   		
	   						tipus = "";
	   						TascaCore.novaTasca(conn, tipus, usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Sol·licitar llicència obra per expedient " + nouCodi, "Sol·licitud llicència",informe.getIdInf(),null);
	   						LlicenciaCore.novaLlicencia(conn, nouCodi, informe.getPropostaInformeSeleccionada().getTipusLlicencia());
		   				}
			    	}
			    } else if ("propostaTecnica".equals(document)) {
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, true);
			   		if (UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari() == Usuari.getIdUsuari()) {
			   			//Eliminam proposta tècnica amb 1 firma (per evitar duplicats)
				    	if (informe.getPropostaTecnica().getRuta() != null) Fitxers.eliminarFitxer(informe.getPropostaTecnica().getRuta());
				    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta tècnica");
				    	
				    	//Registrar comentari;	 
				    	OfertaCore.validacioCapOferta(conn, idInforme, Usuari.getIdUsuari());	   			
			   			ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta tècnica realitzada");
				   		TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Proposta tècnica aprovada", Usuari.getIdUsuari());
			   			TascaCore.reasignar(conn, 902, idTasca, tasca.getTipus());
						TascaCore.tancar(conn, idTasca);
						int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
						String comentari = "S'ha realitzat la proposta tècnica: " + idInforme;
						comentari = "S'ha realitzat la proposta tècnica: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesAprov'>" + idActuacio + "</a>";
						TascaCore.novaTasca(conn, "autoritzacioDespesa", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Proposta tècnica " + idInforme + " realitzada", idInforme, null);						
					} else {
			   			Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta tècnica");
				    	//Registrar comentari;	
				   		String comentariHistoral = "El tècnic proposa: " + informe.getOfertaSeleccionada().getNomEmpresa() + "<br>" + informe.getOfertaSeleccionada().getComentari() + "<br>Amb un termini d'execució de: " + informe.getOfertaSeleccionada().getTermini();	   		
			   			TascaCore.nouHistoric(conn, String.valueOf(idTasca), comentariHistoral, Usuari.getIdUsuari());
				   		TascaCore.reasignar(conn, UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), idTasca, tasca.getTipus());	
				   		if (informe.getPropostaInformeSeleccionada().isEbss() || informe.getPropostaInformeSeleccionada().isCoordinacio()) {
							String comentari = "";
							if (informe.getPropostaInformeSeleccionada().isEbss() && informe.getPropostaInformeSeleccionada().isCoordinacio()) {
								comentari = "Proposta d'actuació per la contratació de EBSS + Coordinació";
							} else if (informe.getPropostaInformeSeleccionada().isEbss()) {
								comentari = "Proposta d'actuació per la contratació d'EBSS";
							}	else {
								comentari = "Proposta d'actuació per la contratació de Coordinació";
							}
				   			TascaCore.novaTasca(conn, "infPrev", UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "", "", null);
						}
			   		}			   		
			    } else if ("contracteSignat".equals(document)) {
			    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Contracte signat");
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			    	if (informe.getUsuariCapValidacio() != null) TascaCore.novaTasca(conn, "generic", informe.getUsuariCapValidacio().getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia, "S'ha firmat el contracte per: " + informe.getIdInf(), "Contracte firmat",informe.getIdInf(),null);
			    } else if ("conformarFactura".equals(document)) {
			    	TascaCore.tancar(conn, idTasca);
			    	Factura factura = FacturaCore.getFactura(conn, idFactura);
			    	factura.setDataConformacio(new Date());
			    	int usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariRecercaPresuposts"));   	
			    	TascaCore.novaTasca(conn, "facturaConformada", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "factura conformada", "Factura conformada", idFactura, null);
			    	Fitxers.eliminarFitxer(factura.getArxiu().getRuta());
			    	FacturaCore.saveArxiu(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia(), idActuacio, idInforme, factura.getIdProveidor(), idFactura, multipartParams.getFitxers(), conn);
			    	FacturaCore.modificarFactura(conn, factura, Usuari.getIdUsuari());
			    }
			} catch (SQLException | NamingException e) {
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
	   		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);  
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
