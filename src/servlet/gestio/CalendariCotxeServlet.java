package servlet.gestio;

import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.ControlPage.SectionPage;
import bean.ReservaElements;
import core.CalendarCore;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CalendariCotxeServlet
 */
@WebServlet("/CalendariCotxe")
public class CalendariCotxeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendariCotxeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
		Connection conn = MyUtils.getStoredConnection(request);
		User usuariLogetjat = MyUtils.getLoginedUser(request.getSession());
		if (usuariLogetjat == null){
			   response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuariLogetjat, SectionPage.usuari_detalls)) {
	  		response.sendRedirect(request.getContextPath() + "/");	
		}else{
			String errorString = "";
			if (request.getParameter("error") != null) {
				errorString = "No es pot realitzar la reserva";
			}
			Calendar cal = Calendar.getInstance();	
	        int diaSetmana = cal.get(Calendar.DAY_OF_WEEK);
	        String reservesPropies = "";
	        int idUsuari = usuariLogetjat.getIdUsuari();
	        if (usuariLogetjat.getRol().contains(User.RolsUsuaris.ADMIN.toString())) {
	        	idUsuari = -1;
	        }
			reservesPropies = CalendarCore.pintarReservesUsuari(CalendarCore.getReservesUsuari(conn, cal.get(Calendar.WEEK_OF_YEAR), idUsuari, cal.get(Calendar.YEAR), null), false);
			
			ReservaElements setmanaCotxe = CalendarCore.getSetmana(conn, cal, "cotxe");
			ReservaElements setmanaCotxeElectric = CalendarCore.getSetmana(conn, cal, "cotxeElectric");
			
	        cal.add(Calendar.DATE, 7);
	        ReservaElements seguentSetmanaCotxe = CalendarCore.getSetmana(conn, cal, "cotxe");
	        ReservaElements seguentSetmanaCotxeElectric = CalendarCore.getSetmana(conn, cal, "cotxeElectric");
	        
	        String optionDies = CalendarCore.getDiesPossibles();
	        	        
	        request.setAttribute("reservesPropies", reservesPropies);
	        request.setAttribute("optionDies", optionDies);
	        request.setAttribute("horesCotxe", ReservaElements.horesCotxe);
	        request.setAttribute("setmanaCotxe", setmanaCotxe);
	        request.setAttribute("seguentSetmanaCotxe", seguentSetmanaCotxe);
	        request.setAttribute("horesCotxeElectric", ReservaElements.horesCotxeElectric);
	        request.setAttribute("setmanaCotxeElectric", setmanaCotxeElectric);
	        request.setAttribute("seguentSetmanaCotxeElectric", seguentSetmanaCotxeElectric);	       
	        request.setAttribute("diaSetmana", diaSetmana);
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuariLogetjat, "Usuari"));
	        request.setAttribute("idUsuariLogg", usuariLogetjat.getIdUsuari());
			RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/gestio/calendariCotxeView.jsp");
	
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
