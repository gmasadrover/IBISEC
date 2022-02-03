package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.InformeCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddReservaServlet
 */
@WebServlet("/DoAddReserva")
public class DoAddReservaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddReservaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = MyUtils.getStoredConnection(request);		
		
	    String idInforme = request.getParameter("idInformePrevi");
	    String idActuacio = request.getParameter("idActuacio");
	    String idIncidencia = request.getParameter("idIncidencia");
	    String idPartida = request.getParameter("llistaSubPartides");
	    String idTramit = "";
	    String reservar = request.getParameter("reservar");
	    String rebutjar = request.getParameter("rebutjar");
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    String comentari = request.getParameter("comentariFinancer");
	    String tipus = "financeraMajor";
	   	if (idInforme.contains("PRO-")) {
			idActuacio = idInforme;	   
			tipus = "procedimentJudicial";
			idInforme = request.getParameter("idTramit");
			idTramit = request.getParameter("idTramit");
		} 
		if (reservar != null) {
			//Reservem el crèdit
			//CreditCore.reservar(conn, idPartida, idActuacio, idInforme, "-1", valor, comentari, Usuari.getIdUsuari());
		}else if(rebutjar != null) {
			InformeCore.rebutjarPartida(conn, idInforme, comentari, Usuari.getIdUsuari());		   		
		}	  	
	   
	   	
	   	// Store infomation to request attribute, before forward to views.
	  
	   	
	   		String redirect = request.getContextPath() + "/CrearDocument?tipus=" + tipus + "&idIncidencia=" + idIncidencia + "&idActuacio=" + idActuacio + "&idInforme=" + idInforme;
	   		if (!idTramit.isEmpty()) redirect += "&idTramit=" + idTramit + "&idPartida=" + idPartida;
	   		response.sendRedirect(redirect);  		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
