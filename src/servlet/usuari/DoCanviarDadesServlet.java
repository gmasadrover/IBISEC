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
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCanviarDadesServlet
 */
@WebServlet("/DoCanviarDades")
public class DoCanviarDadesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCanviarDadesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
		if (usuari == null){
			   response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.usuari_detalls)) {
	  		response.sendRedirect(request.getContextPath() + "/");	
		}else{		  	
		  	User usuariModificat = new User();
		  	usuariModificat.setName(request.getParameter("nom"));
		  	usuariModificat.setLlinatges(request.getParameter("cognoms"));
		  	usuariModificat.setCarreg(request.getParameter("carreg"));
		  	UsuariCore.modificarDades(conn, usuari.getIdUsuari(), usuariModificat);		  	  	
		  	response.sendRedirect(request.getContextPath() + "/UsuariDetails?id=" + usuari.getIdUsuari());		  	
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
