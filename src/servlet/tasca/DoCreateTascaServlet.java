package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateTascaServlet
 */
@WebServlet("/DoCreateTasca")
public class DoCreateTascaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateTascaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = MyUtils.getStoredConnection(request);
		int idActuacio = -1;
		System.out.println(request.getParameter("idActuacio"));
	   	System.out.println(request.getParameter("idIncidencia"));
		if (request.getParameter("idActuacio") != "") idActuacio = Integer.parseInt(request.getParameter("idActuacio")); 
		int idIncidencia = -1;
		if (request.getParameter("idIncidencia") != "") idIncidencia = Integer.parseInt(request.getParameter("idIncidencia")); 
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    String tipus = request.getParameter("tipus");
	    String assumpte = request.getParameter("assumpte");
	   	String comentari = request.getParameter("comentari");
	   	int usuariTasca = Integer.parseInt(request.getParameter("idUsuari"));
	   	String errorString = null;	   		   	
	   	try {
			TascaCore.novaTasca(conn, tipus, usuariTasca, idUsuari, idActuacio, idIncidencia, comentari, assumpte);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.getMessage();
		}
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/tasca/createTascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else if (idActuacio > -1) {
	   		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
	   	} else {
	   		response.sendRedirect(request.getContextPath() + "/incidenciaDetalls?ref=" + idIncidencia);
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
