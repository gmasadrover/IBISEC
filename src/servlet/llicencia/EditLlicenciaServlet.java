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
import core.ActuacioCore;
import core.ControlPageCore;
import core.ExpedientCore;
import core.LlicenciaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditLlicenciaServlet
 */
@WebServlet("/editLlicencia")
public class EditLlicenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditLlicenciaServlet() {
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
			String codi = request.getParameter("codi");
			String expContractacio = request.getParameter("exp");
			String from = request.getParameter("from"); 
	        Llicencia llicencia = new Llicencia();	
	        Actuacio actuacio = new Actuacio();	
	        String errorString = null;	 
	        try {
	        	if (codi == null || codi.isEmpty()) {
	        		codi = LlicenciaCore.novaLlicencia(conn, expContractacio, "");
	        	}
	            llicencia = LlicenciaCore.findLlicencia(conn, codi);
	            actuacio = ActuacioCore.findActuacio(conn, ExpedientCore.findExpedient(conn, llicencia.getCodiExpedient()).getIdActuacio());
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
	        request.setAttribute("llicencia", llicencia);
	        request.setAttribute("from", from);
	        request.setAttribute("actuacio", actuacio);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"llicencies"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/llicencies/editLlicenciaView.jsp");
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
