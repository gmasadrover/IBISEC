package servlet.feina;

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
import bean.Actuacio.Feina;
import bean.Registre;
import core.ActuacioCore;
import core.RegistreCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditFeinaServlet
 */
@WebServlet("/DoEditFeina")
public class DoEditFeinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditFeinaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);		
	    String idFeina = request.getParameter("idFeina");
	    String nomRemitent = request.getParameter("remitent");
	    String nomDestinatari = request.getParameter("destinatari");	   
	    String contingut = request.getParameter("contingut");	
	    String notes = request.getParameter("notes");	  
	    String idActuacio = request.getParameter("idActuacio");
	    String errorString = null;
	    Feina feina = new Actuacio().new Feina();
		try {
			feina.setIdFeina(idFeina);
			feina.setNomRemitent(nomRemitent);
			feina.setNomDestinatari(nomDestinatari);
			feina.setContingut(contingut);
			feina.setNotes(notes);
		    ActuacioCore.modificarFeina(conn, feina);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			errorString = e1.toString();
		}   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("feina", feina);
	   	request.setAttribute("idActuacio", idActuacio);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/editFeinaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   			response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
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
