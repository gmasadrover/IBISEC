package servlet.judicial;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.ControlPage.SectionPage;
import bean.Judicial;
import bean.Judicial.Tramitacio;
import core.ControlPageCore;
import core.JudicialCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditTramitacioServlet
 */
@WebServlet("/editTramitacio")
public class EditTramitacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditTramitacioServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.judicials_modificar)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{ 
	        Tramitacio tramitacio = new Judicial().new Tramitacio();
	        tramitacio = JudicialCore.findTramitacio(conn, request.getParameter("ref"));
 	   		request.setAttribute("tramitacio", tramitacio);
 	   		request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"judicials"));
 	   		request.setAttribute("procediment", request.getParameter("refPro"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/judicial/editTramitacioView.jsp");
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
