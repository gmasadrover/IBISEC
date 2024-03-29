package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.Tasca;
import bean.User;
import core.ActuacioCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class DoAddModificacioServlet
 */
@WebServlet("/DoAddModificacio")
public class DoAddModificacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddModificacioServlet() {
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
		String ipRemote = request.getRemoteAddr();
		int idTasca = -1;
		Tasca tasca = new Tasca();
		String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme");
	    String idModificacio = multipartParams.getParametres().get("idModificacio");
	    String comentariCap = multipartParams.getParametres().get("comentariCap");
	    String errorString = null;
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	
	    if (multipartParams.getParametres().get("idTasca") != null) {
	    	idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));	
	    	tasca = TascaCore.findTascaId(conn, idTasca, Usuari.getIdUsuari());
	    }
		
	    //Guardar adjunts
	    List<Fitxer> fitxers = multipartParams.getFitxers();
	    if (!fitxers.isEmpty()) {
	    	InformeCore.saveInformeModificacio(conn, idIncidencia, idActuacio, idInforme, idModificacio, fitxers, Usuari.getIdUsuari());
			InformeCore.validacioCapInforme(conn, idModificacio, Usuari.getIdUsuari(), comentariCap, new Date());
			TascaCore.nouHistoric(conn, idTasca, "Informe aprovat", Usuari.getIdUsuari(), ipRemote, "automatic");
			ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta modificaciˇ realitzada");
			TascaCore.reasignar(conn, 900, idTasca, tasca.getTipus(), tasca.getDescripcio());
			TascaCore.tancar(conn, idTasca);
			int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
			InformeActuacio modificacio = InformeCore.getMoficacioInforme(conn, idModificacio, false);
			InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			if (modificacio.getPropostaInformeSeleccionada().getPbase() > 0) {
				String idModificacioAUX = idModificacio.split("#")[0];
				TascaCore.novaTasca(conn, "resPartidaModificacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Modificaciˇ expedient " + informe.getExpcontratacio().getExpContratacio(), "Modificaciˇ expedient " + informe.getExpcontratacio().getExpContratacio(), idModificacioAUX, null, ipRemote, "automatic");
			} else {
				idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
				String idModificacioAUX = idModificacio.split("#")[0];
				String comentari = "S'ha realitzat la proposta de modificaciˇ " + idInforme ;
				TascaCore.novaTasca(conn, "autoritzacioModificacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, "Autoritzaciˇ modificaciˇ actuaciˇ", idModificacioAUX, null, ipRemote, "automatic");
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
