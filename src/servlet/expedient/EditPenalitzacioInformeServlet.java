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
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.EmpresaCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditPenalitzacioInformeServlet
 */
@WebServlet("/editPenalitzacioInforme")
public class EditPenalitzacioInformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPenalitzacioInformeServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_modificar)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String idMod = request.getParameter("idMod");
			String idInf = request.getParameter("idinf");	
	        List<Empresa> empresesList = new ArrayList<Empresa>();      
	        InformeActuacio informePrevi = new InformeActuacio();
	        InformeActuacio informePenalitzacio = new InformeActuacio();
	        informePenalitzacio = InformeCore.getMoficacioInforme(conn, idMod, false);	
			informePrevi = InformeCore.getInformePrevi(conn, idInf, false);
			empresesList = EmpresaCore.getEmpreses(conn);
			empresesList.addAll(EmpresaCore.getEmpresesUTE(conn));
	 
	         
	    
	 
	        // Store errorString in request attribute, before forward to views.
	        request.setAttribute("informePenalitzacio", informePenalitzacio);
	        request.setAttribute("informePrevi", informePrevi);
	 	    request.setAttribute("empresesList", empresesList);
	 	    request.setAttribute("isCap", UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_modificar));
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/expedients/editPenalitzacioView.jsp");
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
