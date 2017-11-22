package servlet.feina;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.Actuacio.Feina;
import core.ActuacioCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateActuacioServlet
 */
@WebServlet("/DoCreateFeina")
public class DoCreateFeinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateFeinaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conn = MyUtils.getStoredConnection(request);
		
	    String idActuacio = request.getParameter("idActuacio");
	    String remitent = request.getParameter("remitent");	   
	    String destinatari = request.getParameter("destinatari");	    
	    String contingut = request.getParameter("contingut");	   
	    String notes = request.getParameter("notes");	   
	    
	    
	    Feina feina = new Actuacio().new Feina();
	    feina.setNomRemitent(remitent);
	    feina.setNomDestinatari(destinatari);
	    feina.setContingut(contingut);
	    feina.setNotes(notes);
	    
	    String errorString = null;	
   		try {
   			ActuacioCore.afegirFeina(conn, idActuacio, feina);
   		} catch (SQLException e) {
  			e.printStackTrace();
  			errorString = e.getMessage();
   		}
	       
	   	
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("idActuacio", idActuacio);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/createFeinaView.jsp");
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
