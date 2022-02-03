package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Expedient;
import bean.InformeActuacio;
import bean.Partida;
import bean.User;
import core.ControlPageCore;
import core.CreditCore;
import core.ExpedientCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditInstalacionsServlet
 */
@WebServlet("/editInstalacions")
public class EditInstalacionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditInstalacionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	if (MyUtils.getLoginedUser(request.getSession()) == null){
 		   response.sendRedirect(request.getContextPath() + "/");    	
 	   	}else{
			String refExp = request.getParameter("ref");
			String idInf = request.getParameter("idinf");
			Expedient expedient = new Expedient();
	        List<Partida> partidesList = new ArrayList<Partida>();
	        List<User> llistaUsuaris = new ArrayList<User>();
	        List<User> llistaCaps = new ArrayList<User>();
	     
	        InformeActuacio informePrevi = new InformeActuacio();
	        if (refExp == null || refExp.isEmpty()) {
				informePrevi = InformeCore.getInformePrevi(conn, idInf, false);
				refExp = ExpedientCore.crearExpedient(conn, informePrevi, true,"", informePrevi.getUsuari().getIdUsuari());
			}
			expedient = ExpedientCore.findExpedient(conn, refExp);
			if (expedient == null || expedient.getExpContratacio() == null || expedient.getExpContratacio().isEmpty()) {
				refExp = ExpedientCore.crearExpedient(conn, informePrevi, true, refExp, informePrevi.getUsuari().getIdUsuari());
				expedient = ExpedientCore.findExpedient(conn, refExp);
			}	           
			informePrevi = InformeCore.getInformePrevi(conn, expedient.getIdInforme(), true);
			partidesList = CreditCore.getPartides(conn, false);
			llistaUsuaris = UsuariCore.llistaUsuaris(conn, true);
			llistaCaps = UsuariCore.findUsuarisByRol(conn, "CAP");
	 
	 
	        // Store errorString in request attribute, before forward to views.
	        request.setAttribute("partidesList", partidesList);
	        request.setAttribute("llistaUsuaris", llistaUsuaris);
	        request.setAttribute("llistaCaps", llistaCaps);
	        request.setAttribute("expedient", expedient);
	        request.setAttribute("informePrevi", informePrevi);
	        request.setAttribute("redireccio", request.getParameter("from"));
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/expedients/editInstalacionsView.jsp");
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
