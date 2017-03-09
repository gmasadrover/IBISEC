package servlet.obres;

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
import bean.ObraMajor;
import core.ControlPageCore;
import core.ObresCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class ObresMajorsListServlet
 */
@WebServlet("/obresMajors")
public class ObresMajorsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObresMajorsListServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.obres_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			String filtrar = request.getParameter("filtrar");
			boolean onlyActives = false;
			String idCentre = "";
			String idCentreSelector = "";
			
			String errorString = null;
			List<ObraMajor> list = new ArrayList<ObraMajor>();
			try {
				if (filtrar != null) {
					onlyActives = request.getParameter("nomesActives") != null;
					if (request.getParameter("filterCentre") != null) {
						idCentre = request.getParameter("idCentre").split("_")[0];
						idCentreSelector = request.getParameter("idCentre");
					}
					list = ObresCore.ObresMajor(conn);

				} else {
					list = ObresCore.ObresMajor(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}

			// Store info in request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			request.setAttribute("actuacionsList", list);
			request.setAttribute("nomesActives", onlyActives);
			request.setAttribute("idCentre", idCentreSelector);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Obres"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/obres/obresMajorsList.jsp");

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
