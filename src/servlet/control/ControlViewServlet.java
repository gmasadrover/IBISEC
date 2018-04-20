package servlet.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.ControlInfo;
import bean.Factura;
import bean.Incidencia;
import bean.InformeActuacio;
import bean.Registre;
import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.ControlCore;
import core.ControlPageCore;
import core.FacturaCore;
import core.IncidenciaCore;
import core.InformeCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class ControlViewServlet
 */
@WebServlet("/control")
public class ControlViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
		if (usuari == null){
			response.sendRedirect(request.getContextPath() + "/");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.control)) {
	   		response.sendRedirect(request.getContextPath() + "/");	 	
		}else{
			List<ControlInfo> controlCentres = new ArrayList<ControlInfo>();
			List<InformeActuacio> controlExpedients = new ArrayList<InformeActuacio>();
			try {
				controlExpedients = InformeCore.getInformesResumArea(conn, "obres");
				controlExpedients.addAll(InformeCore.getInformesResumArea(conn, "instalacions"));
			} catch (SQLException e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			request.setAttribute("controlExpedients", controlExpedients);
			request.setAttribute("controlCentres", controlCentres);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Control"));
	     
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/control/controlView.jsp");
	        
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
