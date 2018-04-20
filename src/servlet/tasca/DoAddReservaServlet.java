package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.User;
import core.CreditCore;
import core.ExpedientCore;
import core.InformeCore;
import core.TascaCore;
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
	    String idPartida = request.getParameter("llistaPartides");
	    double valor = Double.parseDouble(request.getParameter("importReserva"));
	    String reservar = request.getParameter("reservar");
	    String rebutjar = request.getParameter("rebutjar");
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    String comentari = request.getParameter("comentariFinancer");	  
	    InformeActuacio informe = new InformeActuacio();
	    String errorString = null;
	   	//Registrar comentari;	   
	   	try {
	   		if (idInforme.contains("-MOD-")) {
	   			informe = InformeCore.getMoficacioInforme(conn, idInforme);
	   		} else {
	   			informe = InformeCore.getInformePrevi(conn, idInforme, false);
	   		}
	   		if (reservar != null) {
		   		//Reservem el crèdit
		   		CreditCore.reservar(conn, idPartida, idActuacio, idInforme, valor, comentari, Usuari.getIdUsuari());
		   	}else if(rebutjar != null) {
		   		InformeCore.rebutjarPartida(conn, idInforme, comentari, Usuari.getIdUsuari());		   		
		   	}	   	
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.getMessage();
		}	  	
	   
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/tascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {	
	   		response.sendRedirect(request.getContextPath() + "/CrearDocument?tipus=financeraMajor&idIncidencia=" + idIncidencia + "&idActuacio=" + idActuacio + "&idInforme=" + idInforme);  		
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
