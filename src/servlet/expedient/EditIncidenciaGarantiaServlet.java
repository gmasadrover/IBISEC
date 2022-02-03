package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.ControlPage.SectionPage;
import bean.InformeActuacio;
import bean.InformeActuacio.IncidenciaGarantia;
import core.ControlPageCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditIncidenciaGarantiaServlet
 */
@WebServlet("/editIncidenciaGarantia")
public class EditIncidenciaGarantiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditIncidenciaGarantiaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
		if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_modificar)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{ 
        	String idInforme = request.getParameter("idInforme");	  
        	int idIncidencia = Integer.parseInt(request.getParameter("idIncidencia"));	  
        	IncidenciaGarantia incidencia = new InformeActuacio().new IncidenciaGarantia();
			incidencia = InformeCore.getIncidenciaGarantia(conn, idIncidencia);
        	request.setAttribute("idInforme", idInforme);	
        	request.setAttribute("incidencia", incidencia);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));			
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/expedients/editIncidenciaGarantiaView.jsp");
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
