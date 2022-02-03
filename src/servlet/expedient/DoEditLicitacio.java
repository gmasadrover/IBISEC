package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Expedient;
import bean.InformeActuacio;
import bean.User;
import bean.Oferta;
import core.ActuacioCore;
import core.CreditCore;
import core.ExpedientCore;
import core.InformeCore;
import core.OfertaCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditLicitacio
 */
@WebServlet("/doEditLicitacio")
public class DoEditLicitacio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditLicitacio() {
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
		User Usuari = MyUtils.getLoginedUser(request.getSession());	
       	String refExp = multipartParams.getParametres().get("expedient");
       	String idInforme = multipartParams.getParametres().get("informe");
       	Expedient expedient = new Expedient();
       	String comentari = multipartParams.getParametres().get("propostaTecnica");	
	    String termini = multipartParams.getParametres().get("termini");
	    String seleccionada = multipartParams.getParametres().get("idOfertaSeleccionada");
	    String capDobra = multipartParams.getParametres().get("capDobra");
	    String correuLicitacio = multipartParams.getParametres().get("mailLicitacio");
       	String errorString = null;     
       	InformeActuacio informe = new InformeActuacio();
       	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       	try {       		
       		informe = InformeCore.getInformePrevi(conn, idInforme, false);
       		if (!informe.getLlistaOfertes().isEmpty() && informe.getLlistaOfertes().size() == 1) {
       			OfertaCore.seleccionarOferta(conn, idInforme, informe.getLlistaOfertes().get(0).getIdOferta(), termini, comentari);    
       		} else {
       			OfertaCore.seleccionarOferta(conn, idInforme, seleccionada, termini, comentari);    
       		}       			   
    	    expedient = ExpedientCore.findExpedient(conn, refExp);
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
    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("aprovacioDispoTerrenys")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Disponibilitat terrenys", Usuari.getIdUsuari());
    	    }
    	    if (multipartParams.getFitxersByName().get("insuficienciaMitjans") != null) {
    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("insuficienciaMitjans")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Insuficiencia mitjans", Usuari.getIdUsuari());
    	    }
    	    if (multipartParams.getFitxersByName().get("resoluciVAD") != null) {
    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("resoluciVAD")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Resolució VAD", Usuari.getIdUsuari());
    	    }  
    	    if (multipartParams.getFitxersByName().get("ratificacioClassificacio") != null) {
    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("ratificacioClassificacio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Ratificació classificació", Usuari.getIdUsuari());
    	    	int idtasca = TascaCore.novaTasca(conn, "generica", 7, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getIdIncidencia(), "S'ha realitzat la ratificació de classificació de l'expedient: " + informe.getExpcontratacio().getExpContratacio(), "Comprovació certificats SS i Tributaris",informe.getIdInf(),null, request.getRemoteAddr(), "automatic");
    	    	TascaCore.seguirTasca(conn, idtasca, 6);
    	    }  
    	    
    	    if (multipartParams.getParametres().get("dataPerfilContratant") != null && ! multipartParams.getParametres().get("dataPerfilContratant").isEmpty()) {
				expedient.setDataPublicacioPerfilContratant(formatter.parse(multipartParams.getParametres().get("dataPerfilContratant")));
				ExpedientCore.updateExpedient(conn, expedient);
    	    }	
    	   
    	    if (multipartParams.getFitxersByName().get("propostaTecnica") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("propostaTecnica")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Proposta tècnica", Usuari.getIdUsuari());
    	    if (multipartParams.getParametres().get("dataAdjudicacio") != null && ! multipartParams.getParametres().get("dataAdjudicacio").isEmpty()) {
	    		expedient.setDataAdjudicacio(formatter.parse(multipartParams.getParametres().get("dataAdjudicacio")));  
	    		if (informe.getAssignacioCredit() != null && !informe.getAssignacioCredit().get(0).getIdAssignacio().isEmpty()) CreditCore.assignar(conn, idInforme, OfertaCore.findOfertaById(conn, seleccionada).getPlic());   
    	    	ExpedientCore.updateExpedient(conn, expedient);
    	    	ActuacioCore.aprovar(conn, informe.getActuacio().getReferencia(), Usuari.getIdUsuari());
				OfertaCore.aprovarOferta(conn, idInforme, Usuari.getIdUsuari());
				InformeCore.modificarEstat(conn, idInforme, "execucio");
	    	}
    	    if (multipartParams.getFitxersByName().get("autoritzacioDespesa") != null) {    	    	
    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("autoritzacioDespesa")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Autorització  Proposta despesa", Usuari.getIdUsuari());
    	    	if (informe.getAssignacioCredit() != null && !informe.getAssignacioCredit().get(0).getIdAssignacio().isEmpty()) CreditCore.assignar(conn, idInforme, OfertaCore.findOfertaById(conn, seleccionada).getPlic());   
    	    	ActuacioCore.aprovar(conn, informe.getActuacio().getReferencia(), Usuari.getIdUsuari());
				ActuacioCore.actualitzarActuacio(conn, informe.getActuacio().getReferencia(), "Autorització generada");
				OfertaCore.aprovarOferta(conn, idInforme, Usuari.getIdUsuari());
				InformeCore.modificarEstat(conn, idInforme, "execucio");
				if (informe.getPropostaInformeSeleccionada().isLlicencia() && informe.getPropostaInformeSeleccionada().getTipusLlicencia().equals("comun")) {
					int usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariLlicencies"));   		
					TascaCore.novaTasca(conn, "generic", usuariTasca, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getIdIncidencia(), "Sol·licitar comunicació prèvia obra", "Sol·licitud comunicació prèvia",informe.getIdInf(),null, request.getRemoteAddr(), "automatic");
				}
    	    }
    	    if (multipartParams.getParametres().get("dataContracte") != null && ! multipartParams.getParametres().get("dataContracte").isEmpty()) {
    	    	expedient.setDataFormalitzacioContracte(formatter.parse(multipartParams.getParametres().get("dataContracte")));  
    	    	ExpedientCore.updateExpedient(conn, expedient);
    	    }
    	    if (multipartParams.getFitxersByName().get("contracte") != null) {
    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("contracte")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Contracte signat", Usuari.getIdUsuari());
    	    	String exp = informe.getIdInf();
    	    	if (informe.getExpcontratacio() != null && informe.getExpcontratacio().getExpContratacio() != null ) exp = informe.getExpcontratacio().getExpContratacio();
    	    	TascaCore.novaTasca(conn, "firmaContracte", UsuariCore.finCap(conn, informe.getUsuari().getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getIdIncidencia(), "S'ha pujat el contracte de l'expedient: " + exp, "Contracte",informe.getIdInf(),null, request.getRemoteAddr(), "automatic");
    	    	
    	    	if (informe.getExpcontratacio().getContracte().equals("major")) {
    	    		TascaCore.novaTasca(conn, "generica", 2, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getIdIncidencia(), "Remetre contracte signat a l'empresa, de l'expedient: " + exp, "Contracte",informe.getIdInf(),null, request.getRemoteAddr(), "automatic");
        	    } else {
        	    	TascaCore.novaTasca(conn, "generica", 7, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getIdIncidencia(), "Remetre contracte a la Junta Consultiva, de l'expedient: " + exp, "Contracte",informe.getIdInf(),null, request.getRemoteAddr(), "automatic");
        	    }
    	    	
    	    	InformeCore.modificarEstat(conn, idInforme, "execucio");
    	    }
    	    if (capDobra != null && !capDobra.isEmpty()) OfertaCore.actualitzarCapDobra(conn, seleccionada, capDobra);
    	    if (correuLicitacio != null && !correuLicitacio.isEmpty()) OfertaCore.actualitzarCorreuLicitacio(conn, seleccionada, correuLicitacio);
    	    Oferta oferta = OfertaCore.findOfertaById(conn, seleccionada);
    	    if (multipartParams.getFitxersByName().get("personalInscrit") != null) OfertaCore.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("personalInscrit")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme, oferta.getCifEmpresa(), "Personal Inscrit", Usuari.getIdUsuari());
       	} catch (ParseException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("expedient", expedient);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/expedients/editLicitacioView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
			if (multipartParams.getParametres().get("redireccio").equals("actuacions")) {
				response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + informe.getActuacio().getReferencia());
			}else{
				response.sendRedirect(request.getContextPath() + "/expedient?ref=" + refExp);
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
