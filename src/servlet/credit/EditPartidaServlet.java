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

import bean.AssignacioCredit;
import bean.Partida;
import bean.User;
import bean.ControlPage.SectionPage;
import bean.Partida.PartidaTipus;
import core.ControlPageCore;
import core.CreditCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditPartidaServlet
 */
@WebServlet("/editPartida")
public class EditPartidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPartidaServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.partides_crear)) {
			response.sendRedirect(request.getContextPath() + "/");	 	
		}else{		   
			String codi = request.getParameter("idPartida");
			String errorString = null;
			Partida partida = new Partida();
	       	try {
	       		partida = CreditCore.getPartida(conn,codi);	  	       	
	       	} catch (SQLException e) {
	       		e.printStackTrace();
	       		errorString = e.getMessage();
	       	}
	  
	       	// Store info in request attribute, before forward to views
	        request.setAttribute("tipusPartida", PartidaTipus.values());
	       	request.setAttribute("errorString", errorString);
	       	request.setAttribute("partida", partida);	    
	       	request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Partides"));
	       	// Forward to /WEB-INF/views/homeView.jsp
	       	// (Users can not access directly into JSP pages placed in WEB-INF)
	       	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/credit/editPartidaView.jsp");
	        
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
