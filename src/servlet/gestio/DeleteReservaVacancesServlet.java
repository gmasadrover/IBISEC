package servlet.gestio;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import bean.Tasca;
import bean.Vacances.Reserves;
import core.CalendarCore;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class DeleteReservaVacancesServlet
 */
@WebServlet("/DeleteReservaVacances")
public class DeleteReservaVacancesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReservaVacancesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        response.setContentType("text/html");
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
    	Connection conn = MyUtils.getStoredConnection(request);
        JsonObject myObj = new JsonObject();      
		try {
			int idSolicitud = Integer.parseInt(request.getParameter("idsolicitud"));
			Reserves reserva = CalendarCore.getSolicitudVacances(conn, idSolicitud);
			CalendarCore.eliminarVacances(conn, idSolicitud);
			Tasca tasca = TascaCore.findTascaVacances(conn, idSolicitud);
			int idTasca = tasca.getIdTasca();
			TascaCore.nouHistoric(conn, idTasca, "Sol·licitud cancelada", reserva.getIdUsuari(), request.getRemoteAddr(), "automatic");
			TascaCore.tancar(conn, tasca.getIdTasca());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myObj.addProperty("success", true);	
		
        out.println(myObj.toString());
 
        out.close();
	}

}
