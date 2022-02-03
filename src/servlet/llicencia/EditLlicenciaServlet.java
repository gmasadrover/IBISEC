package servlet.llicencia;

import java.io.IOException;
import java.sql.Connection;

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
			String idInforme = request.getParameter("exp");
			String from = request.getParameter("from"); 
			String mode = request.getParameter("mode");
	        Llicencia llicencia = new Llicencia();	
	        Actuacio actuacio = new Actuacio();	 
	        actuacio = InformeCore.getInformePrevi(conn, idInforme, false).getActuacio();
			llicencia = LlicenciaCore.findLlicencia(conn, codi);
	 
	  
	        // Store errorString in request attribute, before forward to views.
	        request.setAttribute("idInforme", idInforme);
	        request.setAttribute("llicencia", llicencia);
	        request.setAttribute("from", from);
	        request.setAttribute("actuacio", actuacio);
	        request.setAttribute("mode", mode);
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
