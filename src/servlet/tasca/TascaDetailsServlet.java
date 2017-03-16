package servlet.tasca;

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
import bean.Empresa;
import bean.Historic;
import bean.Incidencia;
import bean.Oferta;
import bean.PropostaActuacio;
import bean.Partida;
import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.CreditCore;
import core.EmpresaCore;
import core.InformeCore;
import core.OfertaCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class IncidenciaDetailsServlet
 */
@WebServlet("/tasca")
public class TascaDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TascaDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
 	   if (MyUtils.getLoginedUser(request.getSession()) == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
 	   }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_detalls)) {
  		response.sendRedirect(request.getContextPath() + "/");	
 	   }else{
 		   int idTasca = Integer.parseInt(request.getParameter("id"));
 	       String errorString = null;
 	       Actuacio actuacio = new Actuacio();
 	       Incidencia incidencia = new Incidencia();
 	       Tasca tasca = new Tasca();
 	       boolean esCap = false;
 	       PropostaActuacio informePrevi = new PropostaActuacio();
 	       List<Historic> historial = new ArrayList<Historic>();
 	       List<Partida> partidesList = new ArrayList<Partida>();
 	       List<Empresa> empresesList = new ArrayList<Empresa>();
 	       List<User> llistaUsuaris = new ArrayList<User>();
 	       List<User> llistaCaps = new ArrayList<User>();
 	       boolean canRealitzarTasca = false;
 	       List<Oferta> ofertes = new ArrayList<Oferta>();
	       Oferta ofertaSeleccionada = new Oferta();
 	       try {
 	    	   tasca = TascaCore.findTascaId(conn, idTasca);
 	    	   actuacio = tasca.getActuacio();
 	    	   incidencia = tasca.getIncidencia();
 	    	   llistaUsuaris = UsuariCore.findUsuarisByDepartament(conn, tasca.getUsuari().getDepartament());
 	    	   llistaCaps.addAll(UsuariCore.findUsuarisByRol(conn, "CAP"));
 	    	   canRealitzarTasca = usuari.getDepartament().equals(tasca.getUsuari().getDepartament()); 
 	    	   if (actuacio != null) {
 	    		  historial = TascaCore.findHistorial(conn, idTasca, incidencia.getIdIncidencia(), actuacio.getReferencia());
 	    		  ofertes = OfertaCore.findOfertes(conn, actuacio.getReferencia());
 	    		  ofertaSeleccionada = OfertaCore.findOfertaSeleccionada(conn, actuacio.getReferencia());
 	    	   }else{
 	    		  historial = TascaCore.findHistorial(conn, idTasca, incidencia.getIdIncidencia(), -1);
 	    	   } 	    	  
 	    	   String tipusTasca = tasca.getTipus();
 	    	   if (tasca.getUsuari().getDepartament().equals(usuari.getDepartament()) && usuari.getRol().contains("CAP")) esCap = true;
 	    	   if ("infPrev".equals(tipusTasca)) {
 	    		  informePrevi = InformeCore.getInformeTasca(conn, idTasca);  
 	    		  if (!esCap) {
	 	    		  llistaUsuaris.clear();
	 	    		  llistaUsuaris.add(UsuariCore.finCap(conn, tasca.getUsuari().getDepartament()));
 	    		  }
 	    	   }else if ("resPartida".equals(tipusTasca)){
 	    		  int tascaInforme = Integer.parseInt(tasca.getDescripcio().split("-")[1].trim());
 	    		  informePrevi = InformeCore.getInformeTasca(conn, tascaInforme);
 	    		  partidesList = CreditCore.getPartides(conn, false);
 	    	   }else if ("liciMenor".equals(tipusTasca)){
 	    		  informePrevi = InformeCore.getInformesActuacio(conn, actuacio.getReferencia()).get(0); 
 	    		  empresesList = EmpresaCore.getEmpreses(conn);
 	    		  if (!esCap) {
	 	    		  llistaUsuaris.clear();
		    		  llistaUsuaris.add(UsuariCore.finCap(conn, tasca.getUsuari().getDepartament()));
 	    		  }
 	    	   }
 	    	   
 	       } catch (SQLException e) {
 	           e.printStackTrace();
 	           errorString = e.getMessage();
 	       }
 	       // Store info in request attribute, before forward to views
 	       request.setAttribute("errorString", errorString);
 	       request.setAttribute("actuacio", actuacio);
 	       request.setAttribute("incidencia", incidencia);
 	       request.setAttribute("tasca", tasca);
 	       request.setAttribute("esCap", esCap);
 	       request.setAttribute("historial", historial);
 	       request.setAttribute("informePrevi", informePrevi);
 	       request.setAttribute("partidesList", partidesList);
 	       request.setAttribute("empresesList", empresesList);
 	       request.setAttribute("llistaUsuaris", llistaUsuaris);
 	       request.setAttribute("llistaCaps", llistaCaps);
 	       request.setAttribute("ofertes", ofertes);
	       request.setAttribute("ofertaSeleccionada", ofertaSeleccionada);
 	       request.setAttribute("canRealitzarTasca", canRealitzarTasca);
 	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Tasques"));

 	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/tasca/tascaView.jsp");
 	        
 	       dispatcher.forward(request, response);
 	   }
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
