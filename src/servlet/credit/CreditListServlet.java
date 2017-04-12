package servlet.credit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
import core.ControlPageCore;
import core.CreditCore;
import core.UsuariCore;
import utils.MyUtils;
 
@WebServlet(urlPatterns = { "/credit"})
public class CreditListServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public CreditListServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   User usuari = MyUtils.getLoginedUser(request.getSession());
	   Connection conn = MyUtils.getStoredConnection(request);
	   if (usuari == null){
		   response.sendRedirect(request.getContextPath() + "/");
	   }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.partides_list)) {
   			response.sendRedirect(request.getContextPath() + "/");	 	
	   }else{	  
	       String errorString = null;
	       List<Partida> list = new ArrayList<Partida>();
	       try {
	           list = CreditCore.getPartides(conn, true);
	       } catch (SQLException e) {
	           e.printStackTrace();
	           errorString = e.getMessage();
	       }
	  
	       // Store info in request attribute, before forward to views
	       request.setAttribute("errorString", errorString);
	       request.setAttribute("partidesList", list);
	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Partides"));
	       // Forward to /WEB-INF/views/homeView.jsp
	       // (Users can not access directly into JSP pages placed in WEB-INF)
	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/credit/creditListView.jsp");
	        
	       dispatcher.forward(request, response);
	   } 
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}