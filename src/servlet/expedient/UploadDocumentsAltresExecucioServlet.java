package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.User;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class UploadDocumentsAltresExecucioServlet
 */
@WebServlet("/uploadDocumentsAltresExecucio")
public class UploadDocumentsAltresExecucioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDocumentsAltresExecucioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = MyUtils.getStoredConnection(request);	
		String ipRemote = request.getRemoteAddr();
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme"); 
		try {
			Fitxers.guardarFitxer(conn, multipartParams.getFitxers(), idIncidencia, idActuacio, "", "", "", idInforme, "Documentació altres execució", Usuari.getIdUsuari());
			String documents = "";
		    Context env = (Context)new InitialContext().lookup("java:comp/env");
			String ruta =  (String)env.lookup("ruta_base");
			String ruta_base = ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Documentació altres execució/";
			Fitxer fitxer = null;
			for(int i=0;i<multipartParams.getFitxers().size();i++){
				 fitxer = (Fitxer) multipartParams.getFitxers().get(i);
				 documents += "<a target='_blanck' href='downloadFichero?ruta=" + java.net.URLEncoder.encode(ruta_base, "UTF-8") +  java.net.URLEncoder.encode(fitxer.getFitxer().getName(), "UTF-8") + "'>" + fitxer.getFitxer().getName() + "</a><br>";
			}
			if (Usuari.getRol().contains("CAP")) {
				TascaCore.novaTasca(conn, "generic", UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia,  Usuari.getNomCompletReal() + ": S'ha afegit nova documentació a execució <br>" + documents, "Nova documentació", idInforme, null, ipRemote, "automatic");
			} else {
				TascaCore.novaTasca(conn, "generic", UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia,  Usuari.getNomCompletReal() + ": S'ha afegit nova documentació a execució <br>" + documents, "Nova documentació", idInforme, null, ipRemote, "automatic");
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
