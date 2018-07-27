package servlet.judicial;

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

import bean.User;
import bean.ControlPage.SectionPage;
import bean.Partida;
import core.ControlPageCore;
import core.CreditCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class PagamentJudicialServlet
 */
@WebServlet("/pagamentJudicial")
public class PagamentJudicialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagamentJudicialServlet() {
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
 	   	}else{ 	  
 	   		List<Partida> partidesList = new ArrayList<Partida>();
 	   		try {
 	   			partidesList = CreditCore.getPartides(conn, false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	  
 	   		request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"judicials"));
 	   		request.setAttribute("procediment", request.getParameter("refPro"));
 	   		request.setAttribute("partidesList", partidesList);
 	   		
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/judicial/pagamentJudicialView.jsp");
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
