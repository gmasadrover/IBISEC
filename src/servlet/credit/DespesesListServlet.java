package servlet.credit;

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

import bean.Partida;
import bean.User;
import bean.AssignacioCredit;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.CreditCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DespesesListServlet
 */
@WebServlet("/despeses")
public class DespesesListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DespesesListServlet() {
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
			response.sendRedirect(request.getContextPath() + "/");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.partides_list)) {
   			response.sendRedirect(request.getContextPath() + "/");	 	
		}else{	  
			String filtrar = request.getParameter("filtrar");
			String filterWithOutDate = request.getParameter("filterWithOutDate");
			String filterWithBEI = request.getParameter("filterBEI");
			String filterWithFEDER = request.getParameter("filterFEDER");
			String errorString = null;
			String idPartida = "";
			String idCentre = request.getParameter("idCentre");
			if (idCentre == null) idCentre = "-1";
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance(); 
			Date dataFi = cal.getTime();
			String dataFiString = df.format(dataFi);	 
			cal.set(Calendar.getInstance().get(Calendar.YEAR), 0, 1);
			Date dataInici = cal.getTime();
			String dataIniciString = df.format(dataInici);	
			List<AssignacioCredit> list = new ArrayList<AssignacioCredit>();
			List<Partida> llistaPartides = new ArrayList<Partida>();
			try {
				if (filtrar != null) {					
					if (!"-1".equals(request.getParameter("idPartida"))) {						
						idPartida = request.getParameter("idPartida");
					}
					dataInici = null;
					dataFi = null;
					if (filterWithOutDate == null){
						dataInici = df.parse(request.getParameter("dataInici"));
		    			dataIniciString = request.getParameter("dataInici");
		    			dataFi = df.parse(request.getParameter("dataFi"));
		    			dataFiString = request.getParameter("dataFi");
					}				
					list = CreditCore.findAssignacions(conn, idPartida, idCentre, dataInici, dataFi, "on".equals(filterWithBEI), "on".equals(filterWithFEDER));
				} else {
					list = CreditCore.findAssignacions(conn, "", idCentre, dataInici, dataFi, "on".equals(filterWithBEI), "on".equals(filterWithFEDER));
				}				
				llistaPartides = CreditCore.getPartides(conn, true);
			} catch (ParseException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		 // Store info in request attribute, before forward to views
	       request.setAttribute("errorString", errorString);
	       request.setAttribute("despesesList", list);
	       request.setAttribute("llistaPartides", llistaPartides);
	       request.setAttribute("filterWithOutDate", "on".equals(filterWithOutDate));
	       request.setAttribute("filterWithBEI", "on".equals(filterWithBEI));
	       request.setAttribute("filterWithFEDER", "on".equals(filterWithFEDER));
	       request.setAttribute("dataInici", dataIniciString);
		   request.setAttribute("dataFi", dataFiString);
		   request.setAttribute("idPartida", idPartida);
		   request.setAttribute("idCentre", idCentre);
	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Partides"));
	       // Forward to /WEB-INF/views/homeView.jsp
	       // (Users can not access directly into JSP pages placed in WEB-INF)
	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/credit/consultaDespesesView.jsp");
	        
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
