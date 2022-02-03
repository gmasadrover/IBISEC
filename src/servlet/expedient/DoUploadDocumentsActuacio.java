package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoUploadDocumentsActuacio
 */
@WebServlet("/uploadDocumentsActuacio")
public class DoUploadDocumentsActuacio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoUploadDocumentsActuacio() {
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
		User Usuari = MyUtils.getLoginedUser(request.getSession()); 
		String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
		Fitxers.guardarFitxer(conn, multipartParams.getFitxers(), idIncidencia, idActuacio, "", "", "", "", "Altre documentació actuació", Usuari.getIdUsuari());
		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
