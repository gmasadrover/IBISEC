package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class PenalitzacionsListServlet
 */
@WebServlet("/penalitzacions")
public class PenalitzacionsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PenalitzacionsListServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_list)) {
			response.sendRedirect(request.getContextPath() + "/");	 	
		}else{		   
			
			List<InformeActuacio> penalitzacionsList = new ArrayList<InformeActuacio>();
			try {
				penalitzacionsList = InformeCore.getPenalitzacionsInforme(conn, null, false);
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Store info in request attribute, before forward to views
			
			request.setAttribute("penalitzacionsList", penalitzacionsList);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/expedients/penalitzacionsView.jsp");

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
