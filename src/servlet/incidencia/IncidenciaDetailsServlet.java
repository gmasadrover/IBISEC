package servlet.incidencia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Registre;
import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import bean.Incidencia;
import core.ControlPageCore;
import core.IncidenciaCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class IncidenciaDetailsServlet
 */
@WebServlet("/incidenciaDetalls")
public class IncidenciaDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IncidenciaDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
	   User usuari = MyUtils.getLoginedUser(request.getSession());
	   if (usuari == null){
		   response.sendRedirect(request.getContextPath() + "/");
	   }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.incidencia_detalls)) {
		   response.sendRedirect(request.getContextPath() + "/");	 	
	   }else{		   
		   int referencia = Integer.parseInt(request.getParameter("ref"));
	       String errorString = null;
	       Incidencia incidencia = new Incidencia();
	       List<Registre> entrades = new ArrayList<Registre>();
	       List<Registre> sortides = new ArrayList<Registre>();
	       List<Tasca> tasques = new ArrayList<Tasca>();
	       List<Fitxers.Fitxer> arxius = new ArrayList<Fitxers.Fitxer>();	
	       try {
	    	   incidencia = IncidenciaCore.findIncidencia(conn, referencia);
	    	   tasques = TascaCore.findTasquesIncidencia(conn, referencia);	    	  	    	  
	    	   entrades = RegistreCore.searchEntradesIncidencia(conn, referencia);
	    	   sortides = RegistreCore.searchSortidesIncidencia(conn, referencia);
	    	   arxius = Fitxers.ObtenirTotsFitxers(referencia);	    	   
	       } catch (SQLException e) {
	           e.printStackTrace();
	           errorString = e.getMessage();
	       }
	  
	       // Store info in request attribute, before forward to views
	       request.setAttribute("errorString", errorString);
	       request.setAttribute("incidencia", incidencia);
	       request.setAttribute("tasques", tasques);
	       request.setAttribute("entrades", entrades);
	       request.setAttribute("sortides", sortides);
	       request.setAttribute("arxius", arxius);	   
	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Incidencies"));
	       // Forward to /WEB-INF/views/homeView.jsp
	       // (Users can not access directly into JSP pages placed in WEB-INF)
	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/incidencia/incidenciaView.jsp");
	        
	       dispatcher.forward(request, response);
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
