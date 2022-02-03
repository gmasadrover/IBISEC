package servlet.judicial;

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

import bean.Judicial;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.JudicialCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class JudicialListServlet
 */
@WebServlet("/judicials")
public class JudicialListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JudicialListServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.judicials_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			List<Judicial> procedimentsList = new ArrayList<Judicial>();
			List<Judicial> tramitacionsPendentsTercersList = new ArrayList<Judicial>();
			List<Judicial> tramitacionsPendentsProvisioList = new ArrayList<Judicial>();
			String filtrar = request.getParameter("filtrar");
			List<String> anysList = new ArrayList<String>();
			String estat = "obert";
			String yearInString = request.getParameter("any");
			boolean canCreateProcediment = false;
			boolean canViewPendentsTercers = false;
			boolean canViewPendentsProvisio = false;
			if (filtrar != null) {	
				estat = request.getParameter("estat");	
			} else {
				yearInString = "-1";
			}
			anysList = JudicialCore.getAnysProcediment(conn);
			procedimentsList = JudicialCore.getProcediments(conn, estat, yearInString);		
			tramitacionsPendentsTercersList = JudicialCore.ProcedimentsAmbtramitacionsPendentsTercersList(conn);
			tramitacionsPendentsProvisioList = JudicialCore.ProcedimentsAmbtramitacionsPendentsProvisioList(conn);
			canCreateProcediment = UsuariCore.hasPermision(conn, usuari, SectionPage.judicials_modificar);
			canViewPendentsTercers = UsuariCore.hasPermision(conn, usuari, SectionPage.judicials_modificar);
			canViewPendentsProvisio = usuari.getDepartament().equals("juridica") || (usuari.getRol().contains("CAP") && usuari.getDepartament().equals("comptabilitat"));

			// Store info in request attribute, before forward to views
			request.setAttribute("canCreateProcediment", canCreateProcediment);
			request.setAttribute("canViewPendentsTercers", canViewPendentsTercers);
			request.setAttribute("canViewPendentsProvisio", canViewPendentsProvisio);
			request.setAttribute("estatFilter", estat);
			request.setAttribute("anysList", anysList);
			request.setAttribute("anyFilter", yearInString);
			request.setAttribute("procedimentsList", procedimentsList);
			request.setAttribute("tramitacionsPendentsTercersList", tramitacionsPendentsTercersList);
			request.setAttribute("tramitacionsPendentsProvisioList", tramitacionsPendentsProvisioList);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"judicials"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/judicial/judicialListView.jsp");

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
