package servlet.registre;

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
import bean.Registre;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.RegistreCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditRegistreServlet
 */
@WebServlet("/editRegistre")
public class EditRegistreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRegistreServlet() {
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
        }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.registre_ent_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{ 	 
 	   		String idRegistre = request.getParameter("id");
 	   		String entradaSortida = request.getParameter("tipus");
 	   		Registre registre = new Registre();
	        try {
	        	registre = RegistreCore.findRegistre(conn, entradaSortida, idRegistre);
	        	request.setAttribute("registre", registre);
	        	request.setAttribute("idRegistre", idRegistre);
				request.setAttribute("entradaSortida", entradaSortida);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	        
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Registre"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/registre/registreEditView.jsp");
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
