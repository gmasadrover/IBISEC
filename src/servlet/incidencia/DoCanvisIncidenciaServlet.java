package servlet.incidencia;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.IncidenciaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCanvisIncidenciaServlet
 */
@WebServlet("/DoCanvisIncidencia")
public class DoCanvisIncidenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCanvisIncidenciaServlet() {
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
		
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String tancar = multipartParams.getParametres().get("tancar");	   
	    String motiu = multipartParams.getParametres().get("motiu");	  
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
   		if (tancar != null) { // tancam incidència
			IncidenciaCore.tancar(conn, idIncidencia, motiu, Usuari.getIdUsuari());   			
   		}

	   	response.sendRedirect(request.getContextPath() + "/incidenciaDetalls?ref=" + idIncidencia);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
