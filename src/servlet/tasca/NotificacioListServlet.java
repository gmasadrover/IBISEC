package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class NotificacioListServlet
 */
@WebServlet("/notificacioList")
public class NotificacioListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificacioListServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{	   		
	        List<Tasca> list = null;
	        String usuariSelected = String.valueOf(usuari.getIdUsuari());
	        list = TascaCore.llistaNotificacionsUsuari(conn, usuari.getIdUsuari());	        
	        
	        // Store info in request attribute, before forward to views
	       
	        request.setAttribute("notificacionsList", list);  
	        request.setAttribute("usuariSelected", usuariSelected);
		    request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Notificacions"));
		    
	        // Forward to /WEB-INF/views/productListView.jsp
	        RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/tasca/notificacioListView.jsp");
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
