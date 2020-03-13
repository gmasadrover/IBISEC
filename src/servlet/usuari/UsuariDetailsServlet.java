package servlet.usuari;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class UsuariDetailsServlet
 */
@WebServlet("/UsuariDetails")
public class UsuariDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuariDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usuariLogetjat = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
		if (usuariLogetjat == null){
			   response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuariLogetjat, SectionPage.usuari_detalls)) {
	  		response.sendRedirect(request.getContextPath() + "/");	
		}else{
		  	int idUsuari = Integer.parseInt(request.getParameter("id"));
		  	String errorString = null;
		  	User usuari = new User();
		  	try {
		  		usuari = UsuariCore.findUsuariByID(conn, idUsuari);
		  	} catch (SQLException e) {
		  		e.printStackTrace();
		  		errorString = e.getMessage();
		  	}
		  
		  	// Store info in request attribute, before forward to views
		  	request.setAttribute("errorString", errorString);
		  	request.setAttribute("usuari", usuari);
		  	request.setAttribute("potModificar", usuari.getIdUsuari() == usuariLogetjat.getIdUsuari());
		  	request.setAttribute("isAdmin", usuariLogetjat.getRol().contains("ADMIN"));
		  	request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuariLogetjat,"Usuaris"));    
		  	// Forward to /WEB-INF/views/homeView.jsp
		  	// (Users can not access directly into JSP pages placed in WEB-INF)
		  	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/usuari/usuariView.jsp");
		        
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
