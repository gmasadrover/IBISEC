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
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.CreditCore;
import core.ExpedientCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditInformeServlet
 */
@WebServlet("/editInforme")
public class EditInformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditInformeServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_modificar)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String refExp = request.getParameter("ref");
			String idInf = request.getParameter("idinf");
			Expedient expedient = new Expedient();
	        List<Partida> partidesList = new ArrayList<Partida>();
	        String partidaPare = null;
	        List<User> llistaUsuaris = new ArrayList<User>();
	        List<User> llistaCaps = new ArrayList<User>();
	        InformeActuacio informePrevi = new InformeActuacio();
	        if (refExp == null || refExp.isEmpty()) {
				informePrevi = InformeCore.getInformePrevi(conn, idInf, false);					
			} else {
				expedient = ExpedientCore.findExpedient(conn, refExp);
				informePrevi = InformeCore.getInformePrevi(conn, expedient.getIdInforme(), true);
				if (informePrevi.getAssignacioCredit().size() > 0) partidaPare = informePrevi.getAssignacioCredit().get(0).getPartida().getSubpartidaDe();
			}
			partidesList = CreditCore.getPartides(conn, true);
			llistaUsuaris = UsuariCore.llistaUsuaris(conn, true);
			llistaCaps = UsuariCore.findUsuarisByRol(conn, "CAP");
	 
	      
	 
	        request.setAttribute("partidesList", partidesList);
	        request.setAttribute("partidaPare", partidaPare);
	        request.setAttribute("llistaUsuaris", llistaUsuaris);
	        request.setAttribute("llistaCaps", llistaCaps);
	        request.setAttribute("expedient", expedient);
	        request.setAttribute("informePrevi", informePrevi);
	        request.setAttribute("redireccio", request.getParameter("from"));
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/expedients/editInformeView.jsp");
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
