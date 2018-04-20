package servlet.projecte;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.Incidencia;
import bean.Judicial;
import bean.Projecte;
import bean.Registre;
import core.ActuacioCore;
import core.IncidenciaCore;
import core.JudicialCore;
import core.ProjecteCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateProjecteServlet
 */
@WebServlet("/DoCreateProjecte")
public class DoCreateProjecteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateProjecteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
	    String idProjecte = "";
		String nomProjecte = request.getParameter("nomProjecte");
	    String idCentre = request.getParameter("idCentre");
	    String errorString = null;
	    Projecte projecte = new Projecte();
	   	// Store infomation to request attribute, before forward to views.
	    
	    try {
	    	projecte.setNomProjecte(nomProjecte);
	    	//projecte.setActuacio(actuacio);
			ProjecteCore.nouProjecte(conn, projecte);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("projecte", projecte);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/registre/createProjecteView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   			response.sendRedirect(request.getContextPath() + "/projecte?id=" + idProjecte);
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
