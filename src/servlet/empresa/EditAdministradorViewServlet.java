package servlet.empresa;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Empresa.Administrador;
import bean.User;
import bean.Bastanteo;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.EmpresaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditAdministradorViewServlet
 */
@WebServlet("/editAdministrador")
public class EditAdministradorViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAdministradorViewServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String cif = request.getParameter("empresa");
			String dniAdministrador = request.getParameter("administrador");
	 
	        Administrador administrador = null;
	        Bastanteo bastanteo = null;
	 
	        administrador = EmpresaCore.findAdministrador(conn, cif, dniAdministrador); 	         
	       
	        if ((administrador != null && administrador.getDni() != null && !administrador.getDni().isEmpty())) {
	        	 // Store errorString in request attribute, before forward to views.		      
		        request.setAttribute("administrador", administrador);
		        request.setAttribute("cif", cif);	
	        } else {
	        	response.sendRedirect(request.getContextPath() + "/bastanteo?ref=" + dniAdministrador);	
	        	return;
	        }
	      
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Empreses"));	
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/empresa/editAdministradorView.jsp");
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
