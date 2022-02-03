package servlet.usuari;

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

import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class UsuarisListServlet
 */
@WebServlet("/usuarisList")
public class UsuarisListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarisListServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.personal)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{	   		
 	   		String filtrar = request.getParameter("filtrar");
 	   		String filterWithActius = request.getParameter("filterWithActius");
	        String errorString = null;
	        List<User> llistaUsuaris = new ArrayList<User>();	       
	   	    try {
	        	if (filtrar != null) {
	        		
	        	}else{
	        		filterWithActius = "on";
	        	}	
	        	llistaUsuaris =  UsuariCore.llistaUsuaris(conn, "on".equals(filterWithActius));	        	
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }	        
	        
	        // Store info in request attribute, before forward to views
	     
	        request.setAttribute("llistaUsuaris", llistaUsuaris);
	        request.setAttribute("errorString", errorString);	       
	        request.setAttribute("filterWithActius", "on".equals(filterWithActius));
		    request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "personal"));
		
		    
           // Forward to /WEB-INF/views/productListView.jsp
	        RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/usuari/usuarisList.jsp");
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
