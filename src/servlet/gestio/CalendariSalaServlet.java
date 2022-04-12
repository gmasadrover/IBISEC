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

import bean.ReservaElements;
import bean.User;
import bean.ControlPage.SectionPage;
import core.CalendarCore;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CalendariSalaServlet
 */
@WebServlet("/CalendariSala")
public class CalendariSalaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendariSalaServlet() {
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
				errorString = "No es pot realitzar la reserva";
			}
			Calendar cal = Calendar.getInstance();	
	        int diaSetmana = cal.get(Calendar.DAY_OF_WEEK);
	        String reservesPropies = "";
			reservesPropies = CalendarCore.pintarReservesUsuari(CalendarCore.getReservesUsuari(conn, cal.get(Calendar.WEEK_OF_YEAR), usuariLogetjat.getIdUsuari(), cal.get(Calendar.YEAR), "sala"), false);
			
			ReservaElements setmanaSala = CalendarCore.getSetmana(conn, cal, "sala");
	        cal.add(Calendar.DATE, 7);
	        ReservaElements seguentSetmanaSala = CalendarCore.getSetmana(conn, cal, "sala");
	      
	        String optionDies = CalendarCore.getDiesPossibles();
	        	        
	        request.setAttribute("reservesPropies", reservesPropies);
	        request.setAttribute("optionDies", optionDies);
	        request.setAttribute("horesSala", ReservaElements.horesSala);
	        request.setAttribute("setmanaSala", setmanaSala);
	        request.setAttribute("seguentSetmanaSala", seguentSetmanaSala);
	        request.setAttribute("diaSetmana", diaSetmana);
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuariLogetjat, "Usuari"));
	        request.setAttribute("idUsuariLogg", usuariLogetjat.getIdUsuari());
			RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/gestio/calendariSalaView.jsp");
	
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
