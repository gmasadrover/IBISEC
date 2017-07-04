package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.InformeActuacio;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import core.ActuacioCore;
import core.CreditCore;
import core.InformeCore;
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
		if (multipartParams.getParametres().get("idTasca") != null) idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));	 
		String document = multipartParams.getParametres().get("document");
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme"); 
	    String errorString = null;
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	
	    
	    //Guardar adjunts
	    List<Fitxer> fitxers = multipartParams.getFitxers();
	    if (!fitxers.isEmpty()) {
	    	try {
			    if ("paTecnic".equals(document)) {
			    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta actuació");
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme);
			    	if (informe.getLlistaPropostes().size() == 1) {
			    		InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInforme);
			    	}
			    	TascaCore.reasignar(conn, UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), idTasca);	
			    } else if ("autoritzacioCap".equals(document)) {
			    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Vistiplau cap");
			    	TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Informe aprovat", Usuari.getIdUsuari());
					ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta d'actuació realitzada");
					TascaCore.reasignar(conn, 900, idTasca);
					TascaCore.tancar(conn, idTasca);
					int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
					TascaCore.novaTasca(conn, "resPartida", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", String.valueOf(idTasca), idInforme);
					idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
					String comentari = "S'ha realitzat la proposta d'actuació: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesT'>" + idActuacio + "</a>";
					TascaCore.novaTasca(conn, "generic", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Proposta actuació " + idInforme + " realitzada", "");
			    } else if ("autoritzacioAreaFinancera".equals(document)) {
			    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Conforme àrea financera");
				    InformeActuacio informe = new InformeActuacio();
				    informe = InformeCore.getInformePrevi(conn, idInforme);
				    String comentariHistoral = "S'ha reservat l'import de " + informe.getPropostaInformeSeleccionada().getPlicFormat() + "€ de la partida " + informe.getPartida();
			   		if(informe.getDataRebujada() != null) {			   		
				   		comentariHistoral = "S'ha rebutjat l'import de " + informe.getPropostaInformeSeleccionada().getPlicFormat() + "€ de la partida " + informe.getPartida();
				   		comentariHistoral += "</br>Motiu: " + informe.getPartidaRebutjadaMotiu(); 
				   	}
			   		TascaCore.nouHistoric(conn, String.valueOf(idTasca), comentariHistoral, Usuari.getIdUsuari());
			   		TascaCore.reasignar(conn, 901, idTasca);
			   		TascaCore.tancar(conn, idTasca);
			    } else if ("autoritzacioPA".equals(document)) {
			    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Autorització Proposta d'actuació");
			    	String tipus = "";
			    	InformeActuacio informe = new InformeActuacio();
				    informe = InformeCore.getInformePrevi(conn, idInforme);
				   	int usuariTasca = -1;			   	
	   				if (("obr".equals(informe.getPropostaInformeSeleccionada().getTipusObra()) && informe.getPropostaInformeSeleccionada().getVec() > 50000) || (!"obr".equals(informe.getPropostaInformeSeleccionada().getTipusObra()) && informe.getPropostaInformeSeleccionada().getVec() > 18000)) { //Contracte d'obres major   					
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
	   				//Registrar incidència nova
	   				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Sol·licitud proposta tècnica");
	   				TascaCore.novaTasca(conn, tipus, usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", "",informe.getIdInf());
			    } else if ("propostaTecnica".equals(document)) {
			    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta tècnica");
			    	//Registrar comentari;	 
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme);
			   		String comentariHistoral = "El tècnic proposa: " + informe.getOfertaSeleccionada().getNomEmpresa() + "<br>" + informe.getOfertaSeleccionada().getComentari() + "<br>Amb un termini d'execució de: " + informe.getOfertaSeleccionada().getTermini();	   		
			   		TascaCore.nouHistoric(conn, String.valueOf(idTasca), comentariHistoral, Usuari.getIdUsuari());
			   		TascaCore.reasignar(conn, UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), idTasca);	
			    } else if ("vitiplauPropostaTecnica".equals(document)) {
			    	//Eliminam proposta tècnica amb 1 firma (per evitar duplicats)
			    	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme);
			    	Fitxers.eliminarFitxer(informe.getPropostaTecnica().getRuta());
			    	Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Proposta tècnica");
			    	//Registrar comentari;	 
			    	OfertaCore.validacioCapOferta(conn, idInforme, Usuari.getIdUsuari());	   			
		   			ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta tècnica realitzada");
		   			TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Proposta tècnica aprovada", Usuari.getIdUsuari());
		   			TascaCore.reasignar(conn, 902, idTasca);
					TascaCore.tancar(conn, idTasca);
					int idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
					String comentari = "S'ha realitzat la proposta tècnica: " + idInforme;
					comentari = "S'ha realitzat la proposta tècnica: <a href='actuacionsDetalls?ref=" + idActuacio + "&view=dadesAprov'>" + idActuacio + "</a>";
					TascaCore.novaTasca(conn, "generic", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Proposta tècnica " + idInforme + " realitzada", "");
			    }
			} catch (SQLException e) {
				errorString = e.toString();
				e.printStackTrace();
			}	    	
	    } else {
	    	errorString = "Falta adjuntar document";
	    }
	    
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/tasca/tascaView.jsp");	        
	       dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {	   		
	   		response.sendRedirect(request.getContextPath() + "/tascaList");  
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
