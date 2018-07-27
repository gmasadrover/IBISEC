package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Instalacions;
import bean.User;
import core.InformeCore;
import utils.Fitxers;
import utils.MyUtils;

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
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme"); 		
		try {			
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaReplanteig"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta replanteig", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaComprovacioReplanteig"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta comprovació replanteig", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaIniciObra"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta inici", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaAprovacioPlaSeguretat"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta aprovació pla seguretat", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaAprovacioResidus"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta aprovació residus", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaAprovacioProgramaTreball"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta aprovació treball", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaRecepcio"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta recepció", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentActaMedicioGeneral"), idIncidencia, idActuacio, "", "", "", idInforme, "Acta medició general", Usuari.getIdUsuari());
		} catch (NamingException e) {
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
