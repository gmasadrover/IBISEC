package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Expedient;
import bean.InformeActuacio;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.ExpedientCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditGarantiaServlet
 */
@WebServlet("/editGarantia")
public class EditGarantiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditGarantiaServlet() {
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
    	} else{
			String idInf = request.getParameter("idinf");
	        String errorString = null;	          
	        InformeActuacio informe = new InformeActuacio();
	        try {
	        	informe = InformeCore.getInformePrevi(conn, idInf, true);	
	        } catch (SQLException | NamingException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }
	 
	         
	        // If no error.
	        // The product does not exist to edit.
	        // Redirect to productList page.
	        if (errorString != null && informe == null) {
	            response.sendRedirect(request.getServletPath() + "/expedients");
	            return;
	        }
	 
	        // Store errorString in request attribute, before forward to views.	       
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("informePrevi", informe);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/expedients/editGarantiaView.jsp");
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
