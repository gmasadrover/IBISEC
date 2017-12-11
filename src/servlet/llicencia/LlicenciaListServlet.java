package servlet.llicencia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Llicencia;
import bean.User;
import bean.ControlPage.SectionPage;
import bean.InformeActuacio;
import core.ControlPageCore;
import core.InformeCore;
import core.LlicenciaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class LlicenciaListServlet
 */
@WebServlet("/llicencies")
public class LlicenciaListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LlicenciaListServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.llicencia_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			String errorString = "";
			List<InformeActuacio> informeList = new ArrayList<InformeActuacio>();
			String filtrar = request.getParameter("filtrar");
			String estat = "";
			String tipus = "";
			try {
				if (filtrar != null) {	
					estat = request.getParameter("estat");		
					tipus = request.getParameter("tipus");		
				} 
				informeList = InformeCore.getInformesLlicencia(conn, estat, tipus);				
			} catch (SQLException | NamingException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}

			// Store info in request attribute, before forward to views
			request.setAttribute("estatFilter", estat);
			request.setAttribute("tipusFilter", tipus);
			request.setAttribute("errorString", errorString);
			request.setAttribute("informeList", informeList);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"llicencies"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/llicencies/llicenciesListView.jsp");

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
