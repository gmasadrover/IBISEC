package servlet.gestio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.User;
import bean.Actuacio.Feina;
import core.ActuacioCore;
import core.CalendarCore;
import utils.MyUtils;

/**
 * Servlet implementation class doAddReservaVehicleServlet
 */
@WebServlet("/doAddReservaVehicle")
public class doAddReservaVehicleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public doAddReservaVehicleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuariLogetjat = MyUtils.getLoginedUser(request.getSession());
		
	    String vehicle = request.getParameter("vehicle");
	    int setmana = Integer.parseInt(request.getParameter("diaSetmana").split("#")[1]);
	    int dia =  Integer.parseInt(request.getParameter("diaSetmana").split("#")[0]);	   
	    int year = Integer.parseInt(request.getParameter("diaSetmana").split("#")[2]);
	    int horaIni =  Integer.parseInt(request.getParameter("horaIni"));	    
	    int horaFi =  Integer.parseInt(request.getParameter("horaFi"));	   
	    String motiu = request.getParameter("motiu");	   
	    int idUsuari = usuariLogetjat.getIdUsuari();
	   	Calendar cal = Calendar.getInstance();	
	    String errorString = null;	
   		try {
   			if (cal.get(Calendar.DAY_OF_WEEK) == dia && cal.get(Calendar.WEEK_OF_YEAR) == setmana) {
   				CalendarCore.reservar(conn, idUsuari, vehicle, setmana, dia, year, horaIni, horaFi, motiu);
   			}
   			else if (CalendarCore.potReservar(conn, idUsuari, vehicle, setmana, dia, year, horaIni, horaFi)) {
   				CalendarCore.reservar(conn, idUsuari, vehicle, setmana, dia, year, horaIni, horaFi, motiu);
   			} else {
   				errorString = "ocupat";
   			}
			
		} catch (SQLException e) {
			errorString = e.toString();
		}	          	
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	String error = "";
	   	if (errorString != null) error = "?error=" + errorString;
	   	response.sendRedirect(request.getContextPath() + "/CalendariCotxe" + error);
	   
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
