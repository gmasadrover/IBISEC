package servlet.gestio;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Tasca;
import bean.User;
import core.ConfiguracioCore;
import core.GestioDron;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoAprovarDronServlet
 */
@WebServlet("/DoAprovarDron")
public class DoAprovarDronServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAprovarDronServlet() {
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
		tasca = TascaCore.findTascaId(conn, idTasca, usuariLogetjat.getIdUsuari());
		String ipRemote = request.getRemoteAddr();
		String comentari = request.getParameter("comentari");
		String aprovar = request.getParameter("aprovar");
		
		if (aprovar != null) {
			GestioDron.aprovarReserva(conn, idSolicitud);
			TascaCore.nouHistoric(conn, idTasca, "Autoritzada. " + comentari, usuariLogetjat.getIdUsuari(), ipRemote, "automatic");	
			TascaCore.reasignar(conn, ConfiguracioCore.getConfiguracio(conn).getIdUsuariDron(), idTasca, "generica", tasca.getDescripcio());
		} else {
			GestioDron.rebutjarReserva(conn, idSolicitud);
			TascaCore.nouHistoric(conn, idTasca, "<span class='missatgeAutomatic'>Sol·licitud rebutjada</span><br> Motiu: " + comentari, usuariLogetjat.getIdUsuari(), ipRemote, "manual");				
			TascaCore.reasignar(conn, Integer.parseInt(tasca.getUsuCre()), idTasca, tasca.getTipus(), tasca.getDescripcio());
		}				
		
	   	response.sendRedirect(request.getContextPath() + "/CalendariDron");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
