package servlet.credit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Partida;
import bean.User;
import bean.AssignacioCredit;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.CreditCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class PartidaDetailsServlet
 */
@WebServlet("/partidaDetalls")
public class PartidaDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PartidaDetailsServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.partides_list)) {
			response.sendRedirect(request.getContextPath() + "/");	 	
		}else{		   
			String codi = request.getParameter("codi");
			String estat = request.getParameter("estat");
			String errorString = null;
			Partida partida = new Partida();	
			List<AssignacioCredit> llistaAssignacions = new ArrayList<AssignacioCredit>();
			boolean canEditPartida = false;
	       	try {
	       		partida = CreditCore.getPartida(conn,codi);	  
	       		llistaAssignacions = CreditCore.findAssignacionsPartida(conn, codi, estat);
	       		canEditPartida = UsuariCore.hasPermision(conn, usuari, SectionPage.partides_crear);
	       	} catch (SQLException | NamingException e) {
	       		e.printStackTrace();
	       		errorString = e.getMessage();
	       	}
	  
	       	// Store info in request attribute, before forward to views
	       	request.setAttribute("errorString", errorString);
	       	request.setAttribute("canEditPartida", canEditPartida);
	       	request.setAttribute("partida", partida);	    
	       	request.setAttribute("llistaAssignacions", llistaAssignacions);
	       	request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Partides"));
	       	// Forward to /WEB-INF/views/homeView.jsp
	       	// (Users can not access directly into JSP pages placed in WEB-INF)
	       	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/credit/partidaView.jsp");
	        
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
