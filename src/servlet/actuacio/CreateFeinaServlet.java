package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ControlPage.SectionPage;
import bean.User;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CreateActuacioServlet
 */
@WebServlet(urlPatterns = { "/createFeina" })
public class CreateFeinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFeinaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
    	Connection conn = MyUtils.getStoredConnection(request);
    	User usuari = MyUtils.getLoginedUser(request.getSession());
        if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/");
        }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.registre_ent_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");	 	
 	   	}else{ 	  
	        request.setAttribute("idActuacio", request.getParameter("idActuacio"));
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Actuacions"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/actuacio/createFeinaView.jsp");
	        dispatcher.forward(request, response);
 	   }
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
