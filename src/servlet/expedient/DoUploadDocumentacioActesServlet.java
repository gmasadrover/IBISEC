package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class DoUploadDocumentacioActesServlet
 */
@WebServlet("/uploadDocumentacioActes")
public class DoUploadDocumentacioActesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoUploadDocumentacioActesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		Connection conn = MyUtils.getStoredConnection(request);
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		User Usuari = MyUtils.getLoginedUser(request.getSession());	  
		String ipRemote = request.getRemoteAddr();
		String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme"); 		
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaReplanteig"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta replanteig", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaComprovacioReplanteig"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta comprovaci� replanteig", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaIniciObra"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta inici", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaAprovacioPlaSeguretat"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta aprovaci� pla seguretat", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaAprovacioResidus"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta aprovaci� residus", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaAprovacioProgramaTreball"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta aprovaci� treball", Usuari.getIdUsuari());
		if (multipartParams.getFitxersByName().get("documentActaRecepcio") != null) {
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaRecepcio"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta recepci�", Usuari.getIdUsuari());
			int idTasca = TascaCore.novaTasca(conn, "generica", 7, Usuari.getIdUsuari(), idActuacio, idIncidencia, "S'ha pujat l'acta de recepci�", "Acta de recepci�",idInforme,null, request.getRemoteAddr(), "automatic");
			TascaCore.seguirTasca(conn, idTasca, 6);
		}
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaMedicioGeneral"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta medici� general", Usuari.getIdUsuari());
		String documents = "";
		Fitxer fitxer = null;
		for(int i=0;i<multipartParams.getFitxers().size();i++){
			 fitxer = (Fitxer) multipartParams.getFitxers().get(i);
			 documents += fitxer.getFitxer().getName() + "<br>";
		}
		if (Usuari.getIdUsuari() != 4) {
			if (Usuari.getRol().contains("CAP")) {
				TascaCore.novaTasca(conn, "generic", UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia,  Usuari.getNomCompletReal() + ": S'ha afegit nova documentaci� t�cnica <br>" + documents, "Nova documentaci�", idInforme, null, ipRemote, "automatic");
			} else {
				TascaCore.novaTasca(conn, "generic", UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia,  Usuari.getNomCompletReal() + ": S'ha afegit nova documentaci� t�cnica <br>" + documents, "Nova documentaci�", idInforme, null, ipRemote, "automatic");
			}
		}
		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio + "&exp=" + idInforme);  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
