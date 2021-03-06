package servlet.aulesModulars;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.AulesModulars;
import bean.Centre;
import bean.ControlPage.SectionPage;
import core.AulesModularsCore;
import core.CentreCore;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class IncidenciaListServlet
 */
@WebServlet("/aulesModulars")
public class AulesModularsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AulesModularsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
		if (usuari == null) {
			response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.aulesmodulars_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			String errorString = null;
			List<AulesModulars> list = new ArrayList<AulesModulars>();			
			list = AulesModularsCore.getAulesModulars(conn);

			// Store info in request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			request.setAttribute("aulesModularsList", list);
			
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"AulesModulars"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/aulesmodulars/aulesModularsListView.jsp");

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
