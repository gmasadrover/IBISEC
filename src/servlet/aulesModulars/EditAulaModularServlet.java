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

import bean.Actuacio;
import bean.AulesModulars;
import bean.Incidencia;
import bean.InformeActuacio;
import bean.Registre;
import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.AulesModularsCore;
import core.ConfiguracioCore;
import core.ControlPageCore;
import core.IncidenciaCore;
import core.InformeCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class EditAulaModularServlet
 */
@WebServlet("/editAulaModular")
public class EditAulaModularServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAulaModularServlet() {
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
			String referencia = request.getParameter("ref");
			AulesModulars aula = new AulesModulars();			
			aula = AulesModularsCore.getAulaModular(conn, referencia);

			// Store info in request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			request.setAttribute("aulaModular", aula);
			
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"AulesModulars"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/aulesmodulars/editAulaModularView.jsp");

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
