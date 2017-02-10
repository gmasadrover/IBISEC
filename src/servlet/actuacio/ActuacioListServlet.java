package servlet.actuacio;

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

import bean.Actuacio;
import bean.ControlPage.SectionPage;
import bean.User;
import core.ActuacioCore;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/actuacions" })
public class ActuacioListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActuacioListServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
		if (usuari == null) {
			response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.actuacio_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			String filtrar = request.getParameter("filtrar");
			boolean onlyActives = false;
			String idCentre = "";
			String idCentreSelector = "";
			
			String errorString = null;
			List<Actuacio> list = new ArrayList<Actuacio>();
			try {
				if (filtrar != null) {
					onlyActives = request.getParameter("nomesActives") != null;
					if (request.getParameter("filterCentre") != null) {
						idCentre = request.getParameter("idCentre").split("_")[0];
						idCentreSelector = request.getParameter("idCentre");
					}
					list = ActuacioCore.searchActuacions(conn, idCentre, onlyActives);

				} else {
					list = ActuacioCore.topAcuacions(conn);
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
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Actuacions"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/actuacio/actuacioListView.jsp");

			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}