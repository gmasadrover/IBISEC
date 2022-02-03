package servlet.credit;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Credit;
import core.CreditCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateCreditServlet
 */
@WebServlet("/DoCreateCredit")
public class DoCreateCreditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateCreditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		 
		String referencia = (String) request.getParameter("referencia");	  
		double presupost =  Double.parseDouble(request.getParameter("presupost"));	  
		Credit credit = new Credit(referencia, presupost);
 
		
	           CreditCore.nouCredit(conn, credit);
	      
	        
	       // Store infomation to request attribute, before forward to views.
	      
	       request.setAttribute("credit", credit);
	 
	       
	 
	           response.sendRedirect(request.getContextPath() + "/credit");
	       
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
