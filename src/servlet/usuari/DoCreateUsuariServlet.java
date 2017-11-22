package servlet.usuari;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
 * Servlet implementation class DoCreateUsuariServlet
 */
@WebServlet("/DoCreateUsuari")
public class DoCreateUsuariServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateUsuariServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.personal)) {
	  		response.sendRedirect(request.getContextPath() + "/");	
		}else{		  	
			int codiUsuari = -1;
		  	User nouUsuari = new User();
		  	nouUsuari.setUsuari(request.getParameter("usuari"));
		  	nouUsuari.setName(request.getParameter("nom"));
		  	nouUsuari.setLlinatges(request.getParameter("cognoms"));
		  	nouUsuari.setCarreg(request.getParameter("carreg"));
		  	nouUsuari.setDepartament(request.getParameter("departament"));
		  	try {
		  		codiUsuari = UsuariCore.nouUsuari(conn, nouUsuari);
		  	} catch (SQLException e) {
		  		e.printStackTrace();
		  	}		  	  	
		  	response.sendRedirect(request.getContextPath() + "/UsuariDetails?id=" + codiUsuari);		  	
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
