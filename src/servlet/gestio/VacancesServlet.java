package servlet.gestio;

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

import bean.User;
import bean.Vacances;
import bean.ControlPage.SectionPage;
import core.CalendarCore;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class VacancesServlet
 */
@WebServlet("/Vacances")
public class VacancesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VacancesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuariLogetjat = MyUtils.getLoginedUser(request.getSession());
		if (usuariLogetjat == null){
			   response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuariLogetjat, SectionPage.usuari_detalls)) {
	  		response.sendRedirect(request.getContextPath() + "/");	
		}else{
			String errorString = "";
			if (request.getParameter("error") != null) {
				errorString = "No queden suficients dies per aquest període";
			}
			String departament = request.getParameter("departament");
			Calendar printCal = Calendar.getInstance();	
			int actualMonth = printCal.get(Calendar.MONTH);
	        List<User> usuarisObres = new ArrayList<User>();
	        
	        printCal.set(printCal.get(Calendar.YEAR), 0, 1);
	        Integer[] diesMesGener = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 1, 1);
	        Integer[] diesMesFebrer = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 2, 1);
	        Integer[] diesMesMarç = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 3, 1);
	        Integer[] diesMesAbril = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 4, 1);
	        Integer[] diesMesMaig = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 5, 1);
	        Integer[] diesMesJuny = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 6, 1);
	        Integer[] diesMesJuliol = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 7, 1);
	        Integer[] diesMesAgost = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 8, 1);
	        Integer[] diesMesSetembre = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 9, 1);
	        Integer[] diesMesOctubre = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 10, 1);
	        Integer[] diesMesNovembre = CalendarCore.getDiesMes(printCal);
	        printCal.set(printCal.get(Calendar.YEAR), 11, 1);
	        Integer[] diesMesDesembre = CalendarCore.getDiesMes(printCal);
	        
	        Vacances vacances = new Vacances();	        
	        vacances.setFestius(getServletContext().getInitParameter("diesFestius"));
	        
	        int actualYear = printCal.get(Calendar.YEAR);
	        
	        String reservesPropiesPendents = "";
	        String reservesPropiesDisfrutades = "";
	        int diesDisponiblesVacances = 0;
	        int diesDisponiblesP7 = 0;
	        
 			if (departament == null) departament = usuariLogetjat.getDepartament();
			usuarisObres = UsuariCore.findUsuarisDepartament(conn, departament);
			vacances.setReservesList(CalendarCore.getVacances(conn, departament));
			reservesPropiesPendents = CalendarCore.getProperesVacancesUsuari(conn, usuariLogetjat.getIdUsuari());
			reservesPropiesDisfrutades = CalendarCore.getVacancesDisfrutadesUsuari(conn, usuariLogetjat.getIdUsuari());
			diesDisponiblesVacances = CalendarCore.getDiesDisponibles(conn, usuariLogetjat.getIdUsuari(), vacances, "vacances");
			diesDisponiblesP7 = CalendarCore.getDiesDisponibles(conn, usuariLogetjat.getIdUsuari(), vacances, "p7");
	        request.setAttribute("vacances", vacances);
	        request.setAttribute("actualMonth", actualMonth);
	        request.setAttribute("actualYear", actualYear);
	        request.setAttribute("usuarisObres", usuarisObres);
	        request.setAttribute("diesMesGener", diesMesGener);
	        request.setAttribute("diesMesFebrer", diesMesFebrer);
	        request.setAttribute("diesMesMarç", diesMesMarç);
	        request.setAttribute("diesMesAbril", diesMesAbril);
	        request.setAttribute("diesMesMaig", diesMesMaig);
	        request.setAttribute("diesMesJuny", diesMesJuny);
	        request.setAttribute("diesMesJuliol", diesMesJuliol);
	        request.setAttribute("diesMesAgost", diesMesAgost);
	        request.setAttribute("diesMesSetembre", diesMesSetembre);
	        request.setAttribute("diesMesOctubre", diesMesOctubre);
	        request.setAttribute("diesMesNovembre", diesMesNovembre);
	        request.setAttribute("diesMesDesembre", diesMesDesembre);
	        request.setAttribute("departament", departament);
	        request.setAttribute("reservesPropiesPendents", reservesPropiesPendents);
	        request.setAttribute("reservesPropiesDisfrutades", reservesPropiesDisfrutades);
	        request.setAttribute("diesDisponiblesVacances", diesDisponiblesVacances);
	        request.setAttribute("diesDisponiblesP7", diesDisponiblesP7);
	        
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuariLogetjat, "Usuari"));
	        request.setAttribute("idUsuariLogg", usuariLogetjat.getIdUsuari());
			RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/gestio/calendariVacances.jsp");
	
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
