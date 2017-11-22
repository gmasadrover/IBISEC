package servlet.feina;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.Actuacio.Feina;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditFeinaServlet
 */
@WebServlet("/modificarFeina")
public class EditFeinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditFeinaServlet() {
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
        }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.registre_ent_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");	 	
 	   	}else{ 	   		
	        Feina feina = new Actuacio().new Feina();
	        String idActuacio = request.getParameter("idActuacio");
	        try {
				feina = ActuacioCore.findFeina(conn, request.getParameter("idFeina"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        request.setAttribute("feina", feina);
	        request.setAttribute("idActuacio", idActuacio);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Actuacions"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/feina/editFeinaView.jsp");
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
