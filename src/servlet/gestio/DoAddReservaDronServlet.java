package servlet.gestio;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.GestioDron;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddReservaDronServlet
 */
@WebServlet("/doAddReservaDron")
public class DoAddReservaDronServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddReservaDronServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuariLogetjat = MyUtils.getLoginedUser(request.getSession());
		String errorString = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date peticio = new Date();
		
	    String motiu = request.getParameter("motiu");	   
	    int idUsuari = usuariLogetjat.getIdUsuari();
	   	
	    try {
	    	peticio = formatter.parse(request.getParameter("peticio"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    GestioDron.reservar(conn, idUsuari, peticio, motiu);

		String assumpte = "Sol·licitud reserva Dron";
		TascaCore.novaTasca(conn, "reservaDron", 1, UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari(), "-1", "-1", motiu, assumpte, "-1", null, request.getRemoteAddr(), "automatic");
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	response.sendRedirect(request.getContextPath() + "/CalendariDron");
	   
	  	// If error, forward to Edit page.
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
