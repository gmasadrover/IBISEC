package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.ConfiguracioCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class UploadDocumentsAltresGarantiaServlet
 */
@WebServlet("/uploadDocumentsAltresGarantia")
public class UploadDocumentsAltresGarantiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDocumentsAltresGarantiaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		Connection conn = MyUtils.getStoredConnection(request);	
		String ipRemote = request.getRemoteAddr();
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme"); 
		Fitxers.guardarFitxer(conn, multipartParams.getFitxers(), idIncidencia, idActuacio, "", "", "", idInforme, "Documentació altres garantia", Usuari.getIdUsuari());
		String documents = "";
		String ruta =   ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		String ruta_base = ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Documentació altres garantia/";
		Fitxer fitxer = null;
		for(int i=0;i<multipartParams.getFitxers().size();i++){
			 fitxer = (Fitxer) multipartParams.getFitxers().get(i);
			 documents += "<a target='_blanck' href='downloadFichero?ruta=" + java.net.URLEncoder.encode(ruta_base, "UTF-8") +  java.net.URLEncoder.encode(fitxer.getFitxer().getName(), "UTF-8") + "'>" + fitxer.getFitxer().getName() + "</a><br>";
		}
		if (Usuari.getIdUsuari() != 4) {
			if (Usuari.getRol().contains("CAP")) {
				TascaCore.novaTasca(conn, "generic", UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia,  Usuari.getNomCompletReal() + ": S'ha afegit nova documentació a garantia <br>" + documents, "Nova documentació", idInforme, null, ipRemote, "automatic");
			} else {
				TascaCore.novaTasca(conn, "generic", UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia,  Usuari.getNomCompletReal() + ": S'ha afegit nova documentació a garantia <br>" + documents, "Nova documentació", idInforme, null, ipRemote, "automatic");
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
