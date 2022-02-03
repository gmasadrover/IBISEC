package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CrearTascaActuacioDerivadaServlet
 */
@WebServlet("/crearTascaActuacioDerivada")
public class CrearTascaActuacioDerivadaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrearTascaActuacioDerivadaServlet() {
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
 	   	}else{ 
	        request.setAttribute("idInforme", request.getParameter("idInforme"));	
			request.setAttribute("llistaUsuaris", UsuariCore.llistaUsuaris(conn, true));			
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Tasques"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/tasca/createTascaActuacioDerivada.jsp");
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
