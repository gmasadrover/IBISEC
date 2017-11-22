package servlet.gestio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Tasca;
import bean.User;
import bean.Vacances.Reserves;
import core.CalendarCore;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoAprovarVacancesServlet
 */
@WebServlet("/DoAprovarVacances")
public class DoAprovarVacancesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAprovarVacancesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuariLogetjat = MyUtils.getLoginedUser(request.getSession());
		int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
		int idTasca = Integer.parseInt(request.getParameter("idTasca"));
		Tasca tasca = new Tasca();
		try {
			tasca = TascaCore.findTascaId(conn, idTasca, usuariLogetjat.getIdUsuari());
		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String comentari = request.getParameter("comentari");
		String aprovar = request.getParameter("aprovar");
		int idUsuariPersonal = Integer.parseInt(getServletContext().getInitParameter("idUsuariPersonal"));   
		try {
			Reserves reserva = CalendarCore.getSolicitudVacances(conn, idSolicitud);
			if (reserva.getAutoritzacio() == null) {
				if (aprovar != null) {
					CalendarCore.aprovarAutoritzacioVacances(conn, idSolicitud);
					TascaCore.nouHistoric(conn, Integer.toString(idTasca), "Sol·licitud aprovada", usuariLogetjat.getIdUsuari());	
					TascaCore.reasignar(conn, idUsuariPersonal, idTasca, tasca.getTipus());
				} else {
					CalendarCore.rebutjarAutoritzacioVacances(conn, idSolicitud);
					TascaCore.nouHistoric(conn, Integer.toString(idTasca), "Sol·licitud rebutjada <br> Motiu: " + comentari, usuariLogetjat.getIdUsuari());				
					TascaCore.reasignar(conn, reserva.getIdUsuari(), idTasca, tasca.getTipus());
				}				
			} else if (reserva.getAutoritzacio() != null) {
				if (aprovar != null) {
					CalendarCore.aprovarVistiplauVacances(conn, idSolicitud);
					TascaCore.nouHistoric(conn, Integer.toString(idTasca), "Vitiplau a la sol·licitud", usuariLogetjat.getIdUsuari());				
				} else {
					CalendarCore.rebutjarVistiplauVacances(conn, idSolicitud);
					TascaCore.nouHistoric(conn, Integer.toString(idTasca), "Vitiplau a la sol·licitud rebutjada <br> Motiu: " + comentari, usuariLogetjat.getIdUsuari());				
				}
				TascaCore.reasignar(conn, reserva.getIdUsuari(), idTasca, tasca.getTipus());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/Vacances");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
