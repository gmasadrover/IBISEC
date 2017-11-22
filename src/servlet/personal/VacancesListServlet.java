package servlet.personal;

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

import bean.Resultat;
import bean.User;
import bean.Vacances;
import bean.Vacances.Reserves;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.CalendarCore;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class VacancesListServlet
 */
@WebServlet("/vacancesList")
public class VacancesListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VacancesListServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.personal)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			String filtrar = request.getParameter("filtrar");
			String filterWithOutDate = request.getParameter("filterWithOutDate");
			String estat = "-1";
			int idUsuari = -1;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance(); 			
			cal.set(cal.get(Calendar.YEAR), 0, 1);
			Date dataInici = cal.getTime();
			String dataIniciString = df.format(dataInici);	
			cal.set(cal.get(Calendar.YEAR), 11, 31);
			Date dataFi = cal.getTime();
			String dataFiString = df.format(dataFi);	
			String errorString = null;
			List<Reserves> vacancesList = new ArrayList<Reserves>();
			List<User> llistaUsuaris = new ArrayList<User>();
			int diesDisponiblesVacances = 0;
		    int diesDisponiblesP7 = 0;
			try {
				if (filtrar != null) {		
					idUsuari = Integer.parseInt(request.getParameter("idUsuari"));		
					estat = request.getParameter("estat");				
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
					Vacances vacances = new Vacances();	        
				    vacances.setFestius(getServletContext().getInitParameter("diesFestius"));
					diesDisponiblesVacances = CalendarCore.getDiesDisponibles(conn, idUsuari, vacances, "vacances");
					diesDisponiblesP7 = CalendarCore.getDiesDisponibles(conn, idUsuari, vacances, "p7");
				}
				vacancesList = CalendarCore.vacancesList(conn, idUsuari, estat, dataInici, dataFi);
				llistaUsuaris = UsuariCore.llistaUsuaris(conn, true);
				
			} catch (ParseException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Store info in request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			request.setAttribute("diesDisponiblesVacances", diesDisponiblesVacances);
			request.setAttribute("diesDisponiblesP7", diesDisponiblesP7);
			request.setAttribute("vacancesList", vacancesList);
			request.setAttribute("filterWithOutDate", "on".equals(filterWithOutDate));
			request.setAttribute("dataInici", dataIniciString);
		    request.setAttribute("dataFi", dataFiString);
			request.setAttribute("idUsuariFilter", idUsuari);
			request.setAttribute("llistaUsuaris", llistaUsuaris);
			request.setAttribute("estatFilter", estat);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"personal"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/personal/vacancesListView.jsp");

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
