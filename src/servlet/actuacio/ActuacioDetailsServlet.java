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
import bean.InformeActuacio;
import bean.Registre;
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
import core.RegistreCore;
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
		   String referencia = request.getParameter("ref");
	       String errorString = null;
	       Actuacio actuacio = new Actuacio();	
	       Incidencia incidencia = new Incidencia();
	       List<Tasca> tasques = new ArrayList<Tasca>();
	       List<Fitxers.Fitxer> arxius = new ArrayList<Fitxers.Fitxer>();
	       List<InformeActuacio> informes = new ArrayList<InformeActuacio>();	       
	       List<Factura> factures = new ArrayList<Factura>();	      
	       List<Registre> entrades = new ArrayList<Registre>();
	       List<Registre> sortides = new ArrayList<Registre>();
	       String rutaActuacio = Fitxers.RUTA_BASE +  "/documents/";
	       boolean canModificarActuacio = false;
	       boolean canCreateInformePrevi = false;
	       boolean canCreateTasca = false;
	       boolean canCreateFactura = false;
	       try {
	    	   actuacio = ActuacioCore.findActuacio(conn, referencia);
	    	   incidencia = IncidenciaCore.findIncidencia(conn, actuacio.getIdIncidencia());
	    	   actuacio.setArxiusAdjunts(Fitxers.ObtenirTotsFitxers(incidencia.getIdIncidencia()));
	    	   tasques = TascaCore.findTasquesActuacio(conn, referencia);	    	  	    	  
	    	   actuacio.setArxiusAdjunts(Fitxers.ObtenirTotsFitxers(incidencia.getIdIncidencia()));
	    	   informes = InformeCore.getInformesActuacio(conn, referencia);
	    	   factures = FacturaCore.getFacturesActuacio(conn, referencia);  
	    	   entrades = RegistreCore.searchEntradesIncidencia(conn, incidencia.getIdIncidencia());
	    	   sortides = RegistreCore.searchSortidesIncidencia(conn, incidencia.getIdIncidencia());
	    	   rutaActuacio += incidencia.getIdIncidencia() + "/Actuacio";
	    	   canModificarActuacio = "gerencia".equals(usuari.getDepartament()) || usuari.getRol().contains("ADMIN");
	    	   canCreateInformePrevi = "gerencia".equals(usuari.getDepartament()) || usuari.getRol().contains("ADMIN");
	    	   canCreateTasca = UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_crear) || "gerencia".equals(usuari.getDepartament());
	    	   canCreateFactura = UsuariCore.hasPermision(conn, usuari, SectionPage.factures_crear);
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
	       request.setAttribute("entrades", entrades);
	       request.setAttribute("sortides", sortides);
	       request.setAttribute("factures", factures);
	       request.setAttribute("rutaActuacio", rutaActuacio);
	       request.setAttribute("canCreateInformePrevi", canCreateInformePrevi);
	       request.setAttribute("canCreateTasca", canCreateTasca);
	       request.setAttribute("canModificarActuacio", canModificarActuacio);
	       request.setAttribute("canCreateFactura", canCreateFactura);
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