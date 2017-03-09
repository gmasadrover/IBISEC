package servlet.actuacio;

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

import bean.Actuacio;
import bean.PropostaActuacio;
import bean.Oferta;
import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import bean.Factura;
import bean.Incidencia;
import core.ActuacioCore;
import core.ControlPageCore;
import core.FacturaCore;
import core.IncidenciaCore;
import core.InformeCore;
import core.OfertaCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/actuacionsDetalls"})
public class ActuacioDetailsServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public ActuacioDetailsServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   Connection conn = MyUtils.getStoredConnection(request);
	   User usuari = MyUtils.getLoginedUser(request.getSession());
	   if (usuari == null){
		   response.sendRedirect(request.getContextPath() + "/");
	   }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.actuacio_detalls)) {
   		response.sendRedirect(request.getContextPath() + "/");	 	
	   }else{		   
		   int referencia = Integer.parseInt(request.getParameter("ref"));
	       String errorString = null;
	       Actuacio actuacio = new Actuacio();	
	       Incidencia incidencia = new Incidencia();
	       List<Tasca> tasques = new ArrayList<Tasca>();
	       List<Fitxers.Fitxer> arxius = new ArrayList<Fitxers.Fitxer>();
	       List<PropostaActuacio> informes = new ArrayList<PropostaActuacio>();
	       List<Oferta> ofertes = new ArrayList<Oferta>();
	       List<Factura> factures = new ArrayList<Factura>();
	       Oferta ofertaSeleccionada = new Oferta();
	       String rutaActuacio = "V:/INTERCANVI D'OBRES/MAS, GUILLEM/documents/";
	       boolean canModificarActuacio = false;
	       boolean canCreateInformePrevi = false;
	       boolean canCreateTasca = false;
	       try {
	    	   actuacio = ActuacioCore.findActuacio(conn, referencia);
	    	   incidencia = IncidenciaCore.findIncidencia(conn, actuacio.getIdIncidencia());
	    	   tasques = TascaCore.findTasquesActuacio(conn, referencia);	    	  	    	  
	    	   arxius = Fitxers.ObtenirTotsFitxers(incidencia.getIdIncidencia());
	    	   informes = InformeCore.getInformesActuacio(conn, referencia);
	    	   factures = FacturaCore.getFacturesActuacio(conn, referencia);
	    	   ofertes = OfertaCore.findOfertes(conn, actuacio.getReferencia());
	    	   ofertaSeleccionada = OfertaCore.findOfertaSeleccionada(conn, actuacio.getReferencia());
	    	   rutaActuacio += incidencia.getIdIncidencia() + "/Actuacio";
	    	   canModificarActuacio = usuari.getRol().contains("GERENT");
	    	   canCreateInformePrevi = usuari.getRol().contains("GERENT");
	    	   canCreateTasca = UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_crear);
	       } catch (SQLException e) {
	           e.printStackTrace();
	           errorString = e.getMessage();
	       }
	  
	       // Store info in request attribute, before forward to views
	       request.setAttribute("errorString", errorString);
	       request.setAttribute("actuacio", actuacio);
	       request.setAttribute("incidencia", incidencia);
	       request.setAttribute("tasques", tasques);
	       request.setAttribute("arxius", arxius);	   
	       request.setAttribute("informes", informes);
	       request.setAttribute("ofertes", ofertes);
	       request.setAttribute("factures", factures);
	       request.setAttribute("rutaActuacio", rutaActuacio);
	       request.setAttribute("ofertaSeleccionada", ofertaSeleccionada);
	       request.setAttribute("canCreateInformePrevi", canCreateInformePrevi);
	       request.setAttribute("canCreateTasca", canCreateTasca);
	       request.setAttribute("canModificarActuacio", canModificarActuacio);
	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Actuacions"));
	       // Forward to /WEB-INF/views/homeView.jsp
	       // (Users can not access directly into JSP pages placed in WEB-INF)
	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/actuacio/actuacioView.jsp");
	        
	       dispatcher.forward(request, response);
	   }
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}