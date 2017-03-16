package servlet.incidencia;

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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.ControlPage.SectionPage;
import bean.Incidencia;
import core.ControlPageCore;
import core.IncidenciaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class IncidenciaListServlet
 */
@WebServlet("/incidencies")
public class IncidenciaListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IncidenciaListServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.incidencia_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			String filtrar = request.getParameter("filtrar");
			String filterWithOutDate = request.getParameter("filterWithOutDate");
			boolean onlyActives = true;
			String idCentre = "";
			String idCentreSelector = "";			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance(); 
			Date dataFi = cal.getTime();
			String dataFiString = df.format(dataFi);	 
			cal.add(Calendar.MONTH, -2);
			Date dataInici = cal.getTime();
			String dataIniciString = df.format(dataInici);				
			String errorString = null;
			List<Incidencia> list = new ArrayList<Incidencia>();
			try {
				if (filtrar != null) {
					onlyActives = request.getParameter("nomesActives") != null;
					if (!"-1".equals(request.getParameter("idCentre").split("_")[0])) {
						idCentre = request.getParameter("idCentre").split("_")[0];
						idCentreSelector = request.getParameter("idCentre");
					}				
					dataInici = null;
					dataIniciString = "";
					dataFi = null;
					dataFiString = "";
					if (filterWithOutDate == null){
						dataInici = df.parse(request.getParameter("dataInici"));
		    			dataIniciString = request.getParameter("dataInici");
		    			dataFi = df.parse(request.getParameter("dataFi"));
		    			dataFiString = request.getParameter("dataFi");
					}		
				}
				list = IncidenciaCore.searchIncidencies(conn, idCentre, onlyActives, dataInici, dataFi);
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}

			// Store info in request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			request.setAttribute("dataInici", dataIniciString);
		    request.setAttribute("dataFi", dataFiString);
			request.setAttribute("incidenciesList", list);
			request.setAttribute("nomesActives", onlyActives);
			request.setAttribute("idCentre", idCentreSelector);
			request.setAttribute("filterWithOutDate", "on".equals(filterWithOutDate));
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Incidencies"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/incidencia/incidenciaListView.jsp");

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
