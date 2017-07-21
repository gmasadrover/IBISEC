package servlet.expedient;

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
import bean.InformeActuacio;
import core.ActuacioCore;
import core.ControlPageCore;
import core.ExpedientCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CreateTascaServlet
 */
@WebServlet("/createExpedient")
public class CreateExpedientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateExpedientServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_modificar)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{ 
	        try {
	        	String idInforme = request.getParameter("idInforme");
	        	double importObraMajor = Double.parseDouble(getServletContext().getInitParameter("importObraMajor"));
	        	InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme);
	        	request.setAttribute("informe", informe);	     
	        	request.setAttribute("actuacio", ActuacioCore.findActuacio(conn, informe.getIdActuacio()));	     
				request.setAttribute("nouCodi", ExpedientCore.getNouCodiExpedient(conn, informe, importObraMajor));
				request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/expedients/createExpedientView.jsp");
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
