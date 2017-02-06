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
import bean.Informe;
import bean.Partida;
import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.CreditCore;
import core.EmpresaCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
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
 	       Tasca tasca = new Tasca();
 	       Informe informePrevi = new Informe();
 	       List<Historic> historial = new ArrayList<Historic>();
 	       List<Partida> partidesList = new ArrayList<Partida>();
 	       List<Empresa> empresesList = new ArrayList<Empresa>();
 	      
 	       try {
 	    	   tasca = TascaCore.findTascaId(conn, idTasca);
 	    	   actuacio = tasca.getActuacio();
 	    	   historial = TascaCore.findHistorial(conn, idTasca, actuacio.getReferencia());
 	    	   String tipusTasca = tasca.getTipus();
 	    	   if ("infPrev".equals(tipusTasca)) {
 	    		  informePrevi = InformeCore.getInformeTasca(conn, idTasca);
 	    	   }else if ("resPartida".equals(tipusTasca)){
 	    		  int tascaInforme = Integer.parseInt(tasca.getName().split("-")[1].trim());
 	    		  informePrevi = InformeCore.getInformeTasca(conn, tascaInforme);
 	    		  partidesList = CreditCore.getPartides(conn, false);
 	    	   }else if ("liciMenor".equals(tipusTasca)){
 	    		  informePrevi = InformeCore.getInformesActuacio(conn, actuacio.getReferencia()).get(0); 
 	    		  empresesList = EmpresaCore.getEmpreses(conn);
 	    	   }
 	       } catch (SQLException e) {
 	           e.printStackTrace();
 	           errorString = e.getMessage();
 	       }
 	       // Store info in request attribute, before forward to views
 	       request.setAttribute("errorString", errorString);
 	       request.setAttribute("actuacio", actuacio);
 	       request.setAttribute("tasca", tasca);
 	       request.setAttribute("historial", historial);
 	       request.setAttribute("informePrevi", informePrevi);
 	       request.setAttribute("partidesList", partidesList);
 	       request.setAttribute("empresesList", empresesList);
 	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Tasques"));
 	       // Forward to /WEB-INF/views/homeView.jsp
 	       // (Users can not access directly into JSP pages placed in WEB-INF)
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
