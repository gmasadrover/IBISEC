package servlet.llistats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Resultat;
import bean.User;
import bean.Centre;
import bean.InformeActuacio;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.CentreCore;
import core.ControlPageCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class LlistatsListServlet
 */
@WebServlet("/llistats")
public class LlistatsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LlistatsListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
		if (usuari == null) {
			response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.llistats_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			String filtrar = request.getParameter("filtrar");
			String filterWithOutDate = request.getParameter("filterWithOutDate");
			String filterWithOutDateExec = request.getParameter("filterWithOutDateExec");
			String estat = "";
			String tipus = "";
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance(); 
			Date dataFi = cal.getTime();
			Date dataFiExec = cal.getTime();
			String dataFiString = df.format(dataFi);	 
			String dataFiExecString = df.format(dataFiExec);	 
			cal.set(2017, 0, 1);
			Date dataInici = cal.getTime();
			Date dataIniciExec = cal.getTime();
			String dataIniciString = df.format(dataInici);	
			String dataIniciExecString = df.format(dataIniciExec);	
			String errorString = "";
			
			List<Centre> centres = new ArrayList<Centre>();
			try {				
				if (filtrar != null) {					
					estat = request.getParameter("estat");		
					tipus = request.getParameter("tipus");		
					dataInici = null;
					dataFi = null;
					if (filterWithOutDate == null){
						dataInici = null;
						dataFi = null;
						if (!request.getParameter("dataInici").isEmpty()) dataInici = df.parse(request.getParameter("dataInici"));
						dataIniciString = request.getParameter("dataInici");
		    			if (!request.getParameter("dataFi").isEmpty()) dataFi = df.parse(request.getParameter("dataFi"));	    			
		    			dataFiString = request.getParameter("dataFi");
					}	
					dataIniciExec = null;
					dataFiExec = null;
					if (filterWithOutDateExec == null){
						dataIniciExec = null;
						dataFiExec = null;
						if (!request.getParameter("dataIniciExec").isEmpty()) dataIniciExec = df.parse(request.getParameter("dataIniciExec"));
						dataIniciExecString = request.getParameter("dataIniciExec");
		    			if (!request.getParameter("dataFiExec").isEmpty()) dataFiExec = df.parse(request.getParameter("dataFiExec"));	    			
		    			dataFiExecString = request.getParameter("dataFiExec");
					}
					centres = CentreCore.findCentresWithIncidencies(conn, tipus, dataInici, dataFi, estat, dataIniciExec, dataFiExec);
				} else {
					centres = CentreCore.findCentresWithIncidencies(conn, tipus, dataInici, dataFi, estat, null, null);
					filterWithOutDateExec = "on";
				}				
			} catch (SQLException | ParseException e) {
				errorString = e.getMessage();
				e.printStackTrace();
			}
			// Store info in request attribute, before forward to views
			request.setAttribute("filterWithOutDate", "on".equals(filterWithOutDate));
			request.setAttribute("filterWithOutDateExec", "on".equals(filterWithOutDateExec));
			request.setAttribute("dataInici", dataIniciString);
		    request.setAttribute("dataFi", dataFiString);
		    request.setAttribute("dataIniciExec", dataIniciExecString);
		    request.setAttribute("dataFiExec", dataFiExecString);
			request.setAttribute("estatFilter", estat);
			request.setAttribute("tipusFilter", tipus);
			request.setAttribute("errorString", errorString);
			request.setAttribute("centresList", centres);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Llistats"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			if ("full".equals(request.getParameter("viewType"))){
				RequestDispatcher dispatcher = this.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/llistats/pantallaCompletaView.jsp");
				dispatcher.forward(request, response);
			} else  {
				RequestDispatcher dispatcher = this.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/llistats/llistatsListView.jsp");
				dispatcher.forward(request, response);
			}		
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
