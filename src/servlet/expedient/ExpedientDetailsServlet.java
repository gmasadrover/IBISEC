package servlet.expedient;

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
import bean.User;
import bean.ControlPage.SectionPage;
import bean.Expedient;
import bean.Incidencia;
import core.ActuacioCore;
import core.ControlPageCore;
import core.ExpedientCore;
import core.IncidenciaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/expedient"})
public class ExpedientDetailsServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public ExpedientDetailsServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   Connection conn = MyUtils.getStoredConnection(request);
	   User usuari = MyUtils.getLoginedUser(request.getSession());
	   if (usuari == null){
		   response.sendRedirect(request.getContextPath() + "/");
	   }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_detalls)) {
   		response.sendRedirect(request.getContextPath() + "/");	 	
	   }else{		   
		   String referencia = request.getParameter("ref");   
	       String errorString = null;
	       Expedient expedient = new Expedient();
	       Actuacio actuacio = new Actuacio();	
	       Incidencia incidencia = new Incidencia();
	       Boolean canModificar = false;
	       try {
	    	   expedient = ExpedientCore.findExpedient(conn, referencia);
	    	   actuacio = expedient.getActuacio();
	    	   actuacio.setSeguiment(ActuacioCore.isSeguimentActuacio(conn, actuacio.getReferencia(), usuari.getIdUsuari()));	  
	    	   incidencia = IncidenciaCore.findIncidencia(conn, actuacio.getIdIncidencia());
	    	   actuacio.setArxiusAdjunts(Fitxers.ObtenirTotsFitxers(incidencia.getIdIncidencia()));
	    	   canModificar = UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_modificar);
	       } catch (SQLException e) {
	           e.printStackTrace();
	           errorString = e.getMessage();
	       }
	       // Store info in request attribute, before forward to views
	       request.setAttribute("errorString", errorString);
	       request.setAttribute("expedient", expedient);
	       request.setAttribute("actuacio", actuacio);
	       request.setAttribute("incidencia", incidencia);
	       request.setAttribute("canModificar", canModificar);
	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "expedients"));
	       request.setAttribute("idUsuariLogg", usuari.getIdUsuari());
	       // Forward to /WEB-INF/views/homeView.jsp
	       // (Users can not access directly into JSP pages placed in WEB-INF)
	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/expedients/expedientView.jsp");
	        
	       dispatcher.forward(request, response);
	   }
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}