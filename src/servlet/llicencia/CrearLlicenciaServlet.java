package servlet.llicencia;

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

import bean.Actuacio;
import bean.Llicencia;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.InformeCore;
import core.LlicenciaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CrearLlicenciaServlet
 */
@WebServlet("/crearLlicencia")
public class CrearLlicenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrearLlicenciaServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.llicencia_modificar)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String idInforme = request.getParameter("idInforme");			       
	        Actuacio actuacio = new Actuacio();	
	        String errorString = null;	 
	        try {     	
	        	actuacio = InformeCore.getInformePrevi(conn, idInforme, false).getActuacio();	                   
	        } catch (SQLException | NamingException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }
	 
	         
	        // If no error.
	        // The product does not exist to edit.
	        // Redirect to productList page.
	        if (errorString != null) {
	            response.sendRedirect(request.getServletPath() + "/llicencies");
	            return;
	        }
	 
	        // Store errorString in request attribute, before forward to views.
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("idInforme", idInforme);
	        request.setAttribute("actuacio", actuacio);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"llicencies"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/llicencies/crearLlicenciaView.jsp");
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
