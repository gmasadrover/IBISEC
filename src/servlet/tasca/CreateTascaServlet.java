package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
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
 	   	}else{ 
	        try {
	        	String tipus = request.getParameter("tipus");
	        	List<User> llistaUsuaris = new ArrayList<User>();
	        	request.setAttribute("idActuacio", request.getParameter("idActuacio"));
	        	request.setAttribute("idIncidencia", ActuacioCore.findActuacio(conn, request.getParameter("idActuacio")).getIdIncidencia());
	        	request.setAttribute("idProcediment", request.getParameter("idProcediment"));
	        	request.setAttribute("idFactura", request.getParameter("idfact"));
	        	request.setAttribute("idInforme", request.getParameter("idInf"));
	        	request.setAttribute("tipus", tipus);
	        	request.setAttribute("centre", request.getParameter("centre"));
				request.setAttribute("nouCodi", TascaCore.idNovaTasca(conn));
				boolean canReasignar = (!usuari.getDepartament().equals("obres") && !usuari.getDepartament().equals("instalacions")) || usuari.getRol().contains("CAP") || usuari.getRol().contains("MANUAL");
				
				if (("infPrev".equals(tipus) || "factura".equals(tipus)) && !usuari.getRol().contains("MANUAL") && !usuari.getRol().contains("CAP") && !usuari.getRol().contains("ADMIN")) {
					llistaUsuaris = UsuariCore.findUsuarisByRol(conn, "CAP");
				} else {
					llistaUsuaris = UsuariCore.findUsuarisByRol(conn, "");
				}
				
				request.setAttribute("canReasignar", canReasignar);
				request.setAttribute("llistaUsuaris", llistaUsuaris);
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
