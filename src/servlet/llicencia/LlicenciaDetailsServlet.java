package servlet.llicencia;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.Llicencia;
import bean.Incidencia;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.ControlPageCore;
import core.LlicenciaCore;
import core.IncidenciaCore;
import core.InformeCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class LlicenciaDetailsServlet
 */
@WebServlet("/llicencia")
public class LlicenciaDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LlicenciaDetailsServlet() {
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
		   }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.llicencia_detalls)) {
	   		response.sendRedirect(request.getContextPath() + "/");	 	
		   }else{		   
			   String codi = request.getParameter("codi"); 	
		       Llicencia llicencia = new Llicencia();
		       Actuacio actuacio = new Actuacio();	
		       Incidencia incidencia = new Incidencia();
		       Boolean canModificar = false;
		       actuacio = ActuacioCore.findActuacio(conn, InformeCore.getInformePrevi(conn, llicencia.getCodiExpedient(), false).getActuacio().getReferencia());
			   llicencia = LlicenciaCore.findLlicencia(conn, codi);
			   actuacio.setSeguiment(ActuacioCore.isSeguimentActuacio(conn, actuacio.getReferencia(), usuari.getIdUsuari()));	  
			   incidencia = IncidenciaCore.findIncidencia(conn, actuacio.getIdIncidencia());
			   actuacio.setArxiusAdjunts(Fitxers.ObtenirTotsFitxers(conn, incidencia.getIdIncidencia()));
			   canModificar = UsuariCore.hasPermision(conn, usuari, SectionPage.llicencia_modificar);
		       // Store info in request attribute, before forward to views
		       
		       request.setAttribute("llicencia", llicencia);
		       request.setAttribute("actuacio", actuacio);		       
		       request.setAttribute("incidencia", incidencia);
		       request.setAttribute("canModificar", canModificar);
		       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "llicencies"));
		       request.setAttribute("idUsuariLogg", usuari.getIdUsuari());
		       // Forward to /WEB-INF/views/homeView.jsp
		       // (Users can not access directly into JSP pages placed in WEB-INF)
		       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/llicencies/llicenciaView.jsp");
		        
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
