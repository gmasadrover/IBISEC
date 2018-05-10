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
import bean.InformeActuacio;
import bean.Tasca;
import bean.User;
import core.ActuacioCore;
import core.ExpedientCore;
import core.InformeCore;
import core.LlicenciaCore;
import core.OfertaCore;
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
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		    	InformeCore.saveInformeModificacio(conn, idIncidencia, idActuacio, idInforme, idModificacio, fitxers, Usuari.getIdUsuari());
		    	InformeCore.validacioCapInforme(conn, idModificacio, Usuari.getIdUsuari(), comentariCap, new Date());
		    	TascaCore.nouHistoric(conn, String.valueOf(idTasca), "Informe aprovat", Usuari.getIdUsuari(), ipRemote, "automatic");
				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta modificació realitzada");
				TascaCore.reasignar(conn, 900, idTasca, tasca.getTipus());
				TascaCore.tancar(conn, idTasca);
				int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
				InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
				TascaCore.novaTasca(conn, "resPartidaModificacio", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Modificació expedient " + informe.getExpcontratacio().getExpContratacio(), "Modificació expedient " + informe.getExpcontratacio().getExpContratacio(), idModificacio, null, ipRemote, "automatic");
			} catch (SQLException | NamingException e) {
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
