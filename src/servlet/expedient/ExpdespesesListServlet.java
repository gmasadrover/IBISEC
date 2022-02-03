package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import core.ExpedientCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class ExpdespesesListServlet
 */
@WebServlet("/expdespeses")
public class ExpdespesesListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpdespesesListServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			
			List<InformeActuacio> informesList = new ArrayList<InformeActuacio>();
			List<String> anysList = new ArrayList<String>();
			String filtrar = request.getParameter("filtrar");			
			Calendar now = Calendar.getInstance();
			String yearInString = request.getParameter("any");
			int year = now.get(Calendar.YEAR);
			if (filtrar == null) yearInString = String.valueOf(year);				
			anysList = ExpedientCore.getAnysExpedients(conn);
			informesList = InformeCore.getInformesExpedients(conn, "-1", "acordMarc", "-1", yearInString);

			// Store info in request attribute, before forward to views
			request.setAttribute("anyFilter", yearInString);
			request.setAttribute("anysList", anysList);
			request.setAttribute("informesList", informesList);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/expedients/expdespesesListView.jsp");

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
