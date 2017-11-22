package servlet.manuals;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import utils.Fitxers;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/manuals"})
public class ManualsListServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public ManualsListServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   Connection conn = MyUtils.getStoredConnection(request);
	   User usuari = MyUtils.getLoginedUser(request.getSession());
	   if (usuari == null){
		   response.sendRedirect(request.getContextPath() + "/");
	   }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.manuals)) {
   		response.sendRedirect(request.getContextPath() + "/");	 	
	   }else{	
		   List<Fitxers.Fitxer> manuals;
		try {
			manuals = Fitxers.ObtenirManuals();
			// Get the base naming context
		    Context env = (Context)new InitialContext().lookup("java:comp/env");
		    // Get a single value
			String ruta =  (String)env.lookup("ruta_base") + "/Manuals/";
			request.setAttribute("manuals", manuals);
			request.setAttribute("ruta", ruta);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Manuals"));
	       // Forward to /WEB-INF/views/homeView.jsp
	       // (Users can not access directly into JSP pages placed in WEB-INF)
	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/manuals/manualsListView.jsp");
	        
	       dispatcher.forward(request, response);
	   }
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}