package servlet.gestio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.Vacances;
import core.CalendarCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddVacancesServlet
 */
@WebServlet("/doAddVacances")
public class DoAddVacancesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddVacancesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuariLogetjat = MyUtils.getLoginedUser(request.getSession());
		
	    String tipus = request.getParameter("tipus");	    
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	    
	    Date dataInici = null;
	    Date dataFi = null;	  
	    String motiu = request.getParameter("motiu");	   
	    int idUsuari = usuariLogetjat.getIdUsuari();
	    Vacances vacances = new Vacances();	        
        vacances.setFestius(getServletContext().getInitParameter("diesFestius"));
	    String errorString = null;	
   		try {
   			if (!request.getParameter("dataInici").isEmpty()) dataInici = df.parse(request.getParameter("dataInici"));	    
   			if (!request.getParameter("dataFi").isEmpty()) dataFi = df.parse(request.getParameter("dataFi"));
   			if (CalendarCore.quedenDies(conn, idUsuari, vacances, tipus, dataInici, dataFi)) {
   				int idSolicitud = CalendarCore.reservarVacances(conn, idUsuari, tipus, dataInici, dataFi, motiu, usuariLogetjat.getDepartament());
   				int idUsuariCap = UsuariCore.finCap(conn, usuariLogetjat.getDepartament()).getIdUsuari();
   				TascaCore.novaTasca(conn, "vacances", idUsuariCap, idUsuari, "", "", "Sol·licitud de " + tipus + " pel " + df.format(dataInici) + " al " +  df.format(dataFi), "Sol·licitud " + tipus, Integer.toString(idSolicitud), null);
   			} else {
   				errorString = "No queden suficients dies per aquest període";
   			}   			   
		} catch (SQLException | ParseException | NamingException e) {
			errorString = e.toString();
		}	          	
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	String error = "";
	   	if (errorString != null) error = "?error=" + errorString;
	   	response.sendRedirect(request.getContextPath() + "/Vacances" + error);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
