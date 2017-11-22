package servlet.registre;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Registre;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class RegistreDetailsServlet
 */
@WebServlet("/registre")
public class RegistreDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistreDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
		 if (usuari == null){
			   response.sendRedirect(request.getContextPath() + "/preLogin");
		 }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.registre_detalls)) {
	    		response.sendRedirect(request.getContextPath() + "/");
		   }else{			   
			   String referencia =	request.getParameter("referencia");
			   String tipus = request.getParameter("tipus");
			   String from = request.getParameter("from");
			   int idTasca = -1;					  
		       String errorString = null;
		       
		       Registre registre = new Registre();
		       try {
		    	   registre = RegistreCore.findRegistre(conn, tipus, referencia);			
		    	   if (from != null || "notificacio".equals(from)) {
		    		   idTasca = Integer.parseInt(request.getParameter("idTasca"));
		    		   TascaCore.llegirNotificacio(conn, idTasca);
		    	   }
		       } catch (SQLException e) {
		           e.printStackTrace();
		           errorString = e.getMessage();
		       }
		       List<Fitxers.Fitxer> arxius;
			try {
				arxius = Fitxers.ObtenirFitxers(registre.getPrimeraIncidencia(), "", "RegistreE", registre.getId(), "");
				request.setAttribute("arxius", arxius);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		       // Store info in request attribute, before forward to views
		       request.setAttribute("errorString", errorString);
		       request.setAttribute("registre", registre);
		       request.setAttribute("tipus", tipus);
		       request.setAttribute("canViewPersonal", UsuariCore.hasPermision(conn, usuari, SectionPage.personal));
		       request.setAttribute("canCreateRegistre", UsuariCore.hasPermision(conn, usuari, SectionPage.registre_ent_crear));
		       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Registre"));
		       // Forward to /WEB-INF/views/homeView.jsp
		       // (Users can not access directly into JSP pages placed in WEB-INF)
		       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registre/registreView.jsp");
		        
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
