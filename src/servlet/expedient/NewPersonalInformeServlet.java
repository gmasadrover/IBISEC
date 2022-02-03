package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Empresa;
import bean.InformeActuacio;
import bean.User;
import core.ControlPageCore;
import core.EmpresaCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class NewPersonalInformeServlet
 */
@WebServlet("/newPersonalInforme")
public class NewPersonalInformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewPersonalInformeServlet() {
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
    	}else if (!usuari.getRol().contains("CAP") && !usuari.getRol().contains("ADMIN")) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String idInf = request.getParameter("idinf");  
			String idActuacio = request.getParameter("idactuacio");  
	        List<User> llistaUsuaris = new ArrayList<User>();
			llistaUsuaris = UsuariCore.llistaUsuaris(conn, true);
			List<Empresa> empresesList = new ArrayList<Empresa>();
		    empresesList = EmpresaCore.getEmpreses(conn);
				
		 	    
	        // Store errorString in request attribute, before forward to views.	   
		    request.setAttribute("empresesList", empresesList);
	        request.setAttribute("idInforme", idInf);
	        request.setAttribute("idActuacio", idActuacio);
	        request.setAttribute("llistaUsuaris", llistaUsuaris);
	        request.setAttribute("redireccio", request.getParameter("from"));
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/expedients/newPersonalInformeView.jsp");
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
