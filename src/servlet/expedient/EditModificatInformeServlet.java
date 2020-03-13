package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Empresa;
import bean.Expedient;
import bean.InformeActuacio;
import bean.Partida;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.CreditCore;
import core.EmpresaCore;
import core.ExpedientCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditModificatInformeServlet
 */
@WebServlet("/editModificatInforme")
public class EditModificatInformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditModificatInformeServlet() {
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
 	   	}else{
			String idMod = request.getParameter("idMod");
			String idInf = request.getParameter("idinf");			
	        String errorString = null;
	        List<Empresa> empresesList = new ArrayList<Empresa>();      
	        InformeActuacio informePrevi = new InformeActuacio();
	        InformeActuacio informeModificacio = new InformeActuacio();
	        try {
	        	informeModificacio = InformeCore.getMoficacioInforme(conn, idMod, true);	
	        	informePrevi = InformeCore.getInformePrevi(conn, idInf, false);
 	    	   	empresesList = EmpresaCore.getEmpreses(conn);
 	    	   	empresesList.addAll(EmpresaCore.getEmpresesUTE(conn));
	        } catch (SQLException | NamingException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }
	 
	         
	        // If no error.
	        // The product does not exist to edit.
	        // Redirect to productList page.
	        if (errorString != null) {
	            response.sendRedirect(request.getServletPath() + "/expedients");
	            return;
	        }
	 
	        // Store errorString in request attribute, before forward to views.
	        request.setAttribute("errorString", errorString);	       
	        request.setAttribute("informeModificacio", informeModificacio);
	        request.setAttribute("informePrevi", informePrevi);
	 	    request.setAttribute("empresesList", empresesList);
	 	    request.setAttribute("isCap", UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_modificar));
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/expedients/editModificacioView.jsp");
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
