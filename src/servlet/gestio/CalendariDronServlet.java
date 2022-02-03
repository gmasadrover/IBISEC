package servlet.gestio;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ReservaVehicle;
import bean.User;
import bean.ControlPage.SectionPage;
import core.CalendarCore;
import core.ControlPageCore;
import core.GestioDron;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CalendariDronServlet
 */
@WebServlet("/CalendariDron")
public class CalendariDronServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendariDronServlet() {
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
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    String today = df.format(new Date().getTime());	
		        
			
	        String reservesPropies = GestioDron.pintarReservesUsuari(conn, usuariLogetjat.getIdUsuari());
			String totesReserves = GestioDron.pintarTotesReserves(conn);	        	        
	       
	        request.setAttribute("reservesPropies", reservesPropies);
	        request.setAttribute("totesReserves", totesReserves);
	        
	       // request.setAttribute("setmanaFurgoneta", setmanaFurgoneta);
	       // request.setAttribute("seguentSetmanaFurgoneta", seguentSetmanaFurgoneta);
	        request.setAttribute("data", today);
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuariLogetjat, "Usuari"));
	        request.setAttribute("idUsuariLogg", usuariLogetjat.getIdUsuari());
			RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/gestio/calendariDronView.jsp");
	
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
