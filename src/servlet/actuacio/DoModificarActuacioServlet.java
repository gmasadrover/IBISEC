package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.ActuacioCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoModificarActuacioServlet
 */
@WebServlet("/DoModificarActuacio")
public class DoModificarActuacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoModificarActuacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = MyUtils.getStoredConnection(request);		
	    String referencia = request.getParameter("idActuacio");	   
	    String descripcio = request.getParameter("descripcio");	       
	    String notes = request.getParameter("observacio");
	    
	   	ActuacioCore.modificarActuacio(conn, referencia, descripcio, notes);
	   	    
		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + referencia);
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
