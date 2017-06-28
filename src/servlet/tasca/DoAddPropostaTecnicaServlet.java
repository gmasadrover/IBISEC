package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.ActuacioCore;
import core.OfertaCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddPresupostsServlet
 */
@WebServlet("/DoAddPropostaTecnica")
public class DoAddPropostaTecnicaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddPropostaTecnicaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = MyUtils.getStoredConnection(request);		
	    int idTasca = Integer.parseInt(request.getParameter("idTasca"));
	    String idIncidencia = request.getParameter("idIncidencia");
	    String idActuacio = request.getParameter("idActuacio");
	    String idInforme = request.getParameter("idInformePrevi");
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    String comentari = request.getParameter("propostaTecnica");	   
	    String errorString = null;
	    String termini = request.getParameter("termini");
	    String seleccionada = request.getParameter("idOfertaSeleccionada");
	    //Agafam totes les ofertes
	    String guardar = request.getParameter("guardar");
	    if (guardar != null) {
		  	try {	   	
		  		OfertaCore.seleccionarOferta(conn, idInforme, seleccionada, termini, comentari);	
		   		/*;*/
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errorString = e.getMessage();
			} 
	    } else {	    	
	   		
	    }
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/tascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		if (guardar != null) {
	   			response.sendRedirect(request.getContextPath() + "/CrearDocument?tipus=PTObres&idIncidencia=" + idIncidencia + "&idActuacio=" + idActuacio + "&idInforme=" + idInforme); 
	   		} else {
	   			response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);	  
	   		}
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
