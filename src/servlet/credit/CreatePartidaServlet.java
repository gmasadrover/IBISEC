package servlet.credit;

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

import bean.Partida;
import bean.User;
import bean.ControlPage.SectionPage;
import bean.Partida.PartidaTipus;
import core.ControlPageCore;
import core.CreditCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CreatePartidaServlet
 */
@WebServlet("/CreatePartida")
public class CreatePartidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePartidaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	List<Partida> partidesList = new ArrayList<Partida>();
    	if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/");
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.partides_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");	 	
 	   	}else{	    	
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/credit/createPartidaView.jsp");
	        request.setAttribute("nouCodi", CreditCore.getNewCode(conn));
			partidesList = CreditCore.getPartides(conn, false);
	        request.setAttribute("tipusPartida", PartidaTipus.values());
	        request.setAttribute("partidesList", partidesList);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Partides"));
	        dispatcher.forward(request, response);
 	   	}
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
