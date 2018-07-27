package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.User;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoUploadDocumentacioFinalObraServlet
 */
@WebServlet("/uploadDocumentacioFinalObra")
public class DoUploadDocumentacioFinalObraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoUploadDocumentacioFinalObraServlet() {
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
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentFinalitzacioContratista"), idIncidencia, idActuacio, "", "", "", idInforme, "Finalització contratista", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentInformeDO"), idIncidencia, idActuacio, "", "", "", idInforme, "Informe DO", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentCFO"), idIncidencia, idActuacio, "", "", "", idInforme, "CFO", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentMedicioGeneral"), idIncidencia, idActuacio, "", "", "", idInforme, "Medició general", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentRepresentacioRecepcio"), idIncidencia, idActuacio, "", "", "", idInforme, "Representació Recepció", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentConvocatoriaRecepcio"), idIncidencia, idActuacio, "", "", "", idInforme, "Convocatòria recepció", Usuari.getIdUsuari());
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
