package servlet.projecte;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Resultat;
import bean.User;
import bean.ControlPage.SectionPage;
import bean.Projecte;
import core.ActuacioCore;
import core.ControlPageCore;
import core.ProjecteCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class ProjecteListServlet
 */
@WebServlet("/projectes")
public class ProjecteListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjecteListServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.projectes_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			String filtrar = request.getParameter("filtrar");
			String filterWithOutDate = request.getParameter("filterWithOutDate");
			String estat = "";
			String idCentre = "";
			String idCentreSelector = "";
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance(); 
			Date dataFi = cal.getTime();
			String dataFiString = df.format(dataFi);	
			dataFi = new Date(dataFi.getTime() + (1000 * 60 * 60 * 24));
			cal.set(2017, 0, 1);
			Date dataInici = cal.getTime();
			String dataIniciString = df.format(dataInici);	
			String errorString = null;
			List<Projecte> projectesList = new ArrayList<Projecte>();
			boolean canCreateProjecte = false;
			try {
				if (filtrar != null) {					
					estat = request.getParameter("estat");
					if (!"-1".equals(request.getParameter("idCentre").split("_")[0])) {						
						idCentre = request.getParameter("idCentre").split("_")[0];
						idCentreSelector = request.getParameter("idCentre");
					}
					dataInici = null;
					dataFi = null;
					if (filterWithOutDate == null){
						dataInici = null;
						dataFi = null;
						if (!request.getParameter("dataInici").isEmpty()) dataInici = df.parse(request.getParameter("dataInici"));
						dataIniciString = request.getParameter("dataInici");
		    			if (!request.getParameter("dataFi").isEmpty()) dataFi = df.parse(request.getParameter("dataFi"));	    
		    			dataFi = new Date(dataFi.getTime() + (1000 * 60 * 60 * 24));
		    			dataFiString = request.getParameter("dataFi");
					}					
					projectesList = ProjecteCore.getProjectes(conn);
				} else {
					projectesList = ProjecteCore.getProjectes(conn);
				}
				canCreateProjecte = UsuariCore.hasPermision(conn, usuari, SectionPage.projectes_crear);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Store info in request attribute, before forward to views
			request.setAttribute("errorString", errorString);	
			request.setAttribute("filterWithOutDate", "on".equals(filterWithOutDate));
			request.setAttribute("dataInici", dataIniciString);
		    request.setAttribute("dataFi", dataFiString);
			request.setAttribute("idCentre", idCentreSelector);
			request.setAttribute("estatFilter", estat);
			request.setAttribute("projectesList", projectesList);
			request.setAttribute("canCreateProjecte", canCreateProjecte);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"projectes"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/projecte/projecteListView.jsp");

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
