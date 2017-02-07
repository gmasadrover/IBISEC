package servlet.tasca;

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
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CreateTascaServlet
 */
@WebServlet("/createTasca")
public class CreateTascaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTascaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
		if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{ 
	        try {
	        	String tipus = request.getParameter("tipus");
	        	String tipusUsuari = "";
	        	request.setAttribute("idActuacio", request.getParameter("idActuacio"));
	        	request.setAttribute("idIncidencia", request.getParameter("idIncidencia"));
	        	request.setAttribute("tipus", tipus);
				request.setAttribute("nouCodi", TascaCore.idNovaTasca(conn));
				if ("infPrev".equals(tipus)) {
					tipusUsuari = "CAP";
				}
				request.setAttribute("llistaUsuaris", UsuariCore.findUsuarisByTipus(conn, tipusUsuari));
				request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Tasques"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/tasca/createTascaView.jsp");
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
