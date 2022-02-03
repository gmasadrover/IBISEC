package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;
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

import bean.Actuacio;
import bean.ControlPage.SectionPage;
import bean.User;
import core.ActuacioCore;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/actuacions" })
public class ActuacioListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActuacioListServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
		if (usuari == null) {
			response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.actuacio_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			String filtrar = request.getParameter("filtrar");
			String filterWithOutDate = request.getParameter("filterWithOutDate");
			String estat = null;
			String idCentre = null;
			String idCentreSelector = "";
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance(); 
			Date dataFi = cal.getTime();
			String dataFiString = df.format(dataFi);	
			dataFi = new Date(dataFi.getTime() + (1000 * 60 * 60 * 24));
			cal.set(2017, 0, 1);
			Date dataInici = cal.getTime();
			String dataIniciString = df.format(dataInici);	
			String errorString = null;		
			List<Actuacio> actuacioList = new ArrayList<Actuacio>();
			try {
				if (filtrar != null) {					
					estat = request.getParameter("estat");
					if (!"-1".equals(request.getParameter("idCentre").split("_")[0])) {						
						idCentre = request.getParameter("idCentre").split("_")[0];
						idCentreSelector = request.getParameter("idCentre");
					}
					dataInici = null;
					dataFi = null;
					if (filterWithOutDate == null){
						dataInici = null;
						dataFi = null;
						if (!request.getParameter("dataInici").isEmpty()) dataInici = df.parse(request.getParameter("dataInici"));
						dataIniciString = request.getParameter("dataInici");
		    			if (!request.getParameter("dataFi").isEmpty()) dataFi = df.parse(request.getParameter("dataFi"));	    
		    			dataFi = new Date(dataFi.getTime() + (1000 * 60 * 60 * 24));
		    			dataFiString = request.getParameter("dataFi");
					}	
				} 
				actuacioList = ActuacioCore.searchActuacionsList(conn, idCentre, estat, dataInici, dataFi, null, null, null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Store info in request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			request.setAttribute("canViewPersonal", UsuariCore.hasPermision(conn, usuari, SectionPage.personal));
			request.setAttribute("actuacionsList", actuacioList);			
			request.setAttribute("filterWithOutDate", "on".equals(filterWithOutDate));
			request.setAttribute("dataInici", dataIniciString);
		    request.setAttribute("dataFi", dataFiString);
			request.setAttribute("idCentre", idCentreSelector);
			request.setAttribute("estatFilter", estat);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Actuacions"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/actuacio/actuacioListView.jsp");

			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}