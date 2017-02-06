package servlet.actuacio;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import core.ActuacioCore;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateActuacioServlet
 */
@WebServlet("/DoCreateActuacio")
public class DoCreateActuacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateActuacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conn = MyUtils.getStoredConnection(request);
		//Registrar actuaci�
	    int referencia = Integer.parseInt(request.getParameter("referencia"));
	    String idCentre = request.getParameter("idCentre");	   
	    String descripcio = request.getParameter("descripcio");	    
	    int idIncidencia = Integer.parseInt(request.getParameter("idIncidencia"));	   
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    
	    Actuacio actuacio = new Actuacio();
	    actuacio.setReferencia(referencia);
	    actuacio.setDescripcio(descripcio);
	    actuacio.setIdCentre(idCentre);
	    actuacio.setIdIncidencia(idIncidencia);
	    actuacio.setIdUsuariCreacio(idUsuari);
	    
	    String errorString = null;	 	      
	   	if (errorString == null) {
	   		try {
	   			ActuacioCore.novaActuacio(conn, actuacio);
	   		} catch (SQLException e) {
	  			e.printStackTrace();
	  			errorString = e.getMessage();
	   		}
	   	}	    
	   	//Registrar incidència
	   	/*String comentari = request.getParameter("comentari");
	   	int usuariTasca = Integer.parseInt(request.getParameter("idUsuari"));
	   	try {
			TascaCore.novaTasca(conn, "", usuariTasca, idUsuari, referencia, comentari, "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("actuacio", actuacio);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/createActuacioView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		response.sendRedirect(request.getContextPath() + "/actuacions");
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
