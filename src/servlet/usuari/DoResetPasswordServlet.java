package servlet.usuari;

import java.io.IOException;
import java.sql.Connection;

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
 * Servlet implementation class DoResetPasswordServlet
 */
@WebServlet("/DoResetPassword")
public class DoResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoResetPasswordServlet() {
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
			int idUsuari = Integer.parseInt(request.getParameter("idUsuari"));
		  	
		  	UsuariCore.modificarPassword(conn, idUsuari, "");
		  	// Store info in request attribute, before forward to views
		  	request.setAttribute("usuari", usuariLogetjat);
		  	request.setAttribute("potModificar", true);
		  	request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuariLogetjat,"Usuaris"));  
		  
		  	// Forward to /WEB-INF/views/homeView.jsp
		  	// (Users can not access directly into JSP pages placed in WEB-INF)
		    	response.sendRedirect(request.getContextPath() + "/UsuariDetails?id=" + usuariLogetjat.getIdUsuari());
		    
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
