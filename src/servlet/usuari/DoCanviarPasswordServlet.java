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
 * Servlet implementation class DoCanviarPasswordServlet
 */
@WebServlet("/DoCanviarPassword")
public class DoCanviarPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCanviarPasswordServlet() {
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
			String passwordActual = request.getParameter("passActual");
			String passwordNou = request.getParameter("nouPassword");
			String passwordNouRepetit = request.getParameter("repetirNouPassword");
		  	String errorString = "";
		  	try {
		  		if (!UsuariCore.coincideixPassword(conn, usuariLogetjat.getIdUsuari(), passwordActual)) { //No coincideix el password actual
			  		errorString += "No coincideix el password actual introduit amb el password actual<br>";
			  	}
		  		if (passwordNou.isEmpty()) {
			  		errorString += "El password nou no es correcte<br>";
			  	}
			  	if (!passwordNou.equals(passwordNouRepetit)) {
			  		errorString += "No coincideix el passwords nou amb la seva validació<br>";
			  	}
			  	if (passwordNou.equals(passwordActual)){
			  		errorString += "El password nou no pot ser igual al password actual<br>";
			  	}
			  	if (errorString.isEmpty()) {			  	
			  		UsuariCore.modificarPassword(conn, usuariLogetjat.getIdUsuari(), passwordNou);			  	
			  	}
		  	} catch (SQLException e) {
		  		e.printStackTrace();
		  		errorString = e.getMessage();
		  	}
		  	// Store info in request attribute, before forward to views
		  	request.setAttribute("errorString", errorString);
		  	request.setAttribute("usuari", usuariLogetjat);
		  	request.setAttribute("potModificar", true);
		  	request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuariLogetjat,"Usuaris"));  
		  	// If error, forward to Edit page.
		  	if (errorString != null) {
	           RequestDispatcher dispatcher = request.getServletContext()
	                   .getRequestDispatcher("/WEB-INF/views/usuari/usuariView.jsp");
	           dispatcher.forward(request, response);
		    } else {		  	
		  	// Forward to /WEB-INF/views/homeView.jsp
		  	// (Users can not access directly into JSP pages placed in WEB-INF)
		    	response.sendRedirect(request.getContextPath() + "/UsuariDetails?id=" + usuariLogetjat.getIdUsuari());
		    }
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
