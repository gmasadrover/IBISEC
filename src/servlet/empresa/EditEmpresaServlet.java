package servlet.empresa;

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
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.EmpresaCore;
import core.UsuariCore;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/editEmpresa" })
public class EditEmpresaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public EditEmpresaServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	if (MyUtils.getLoginedUser(request.getSession()) == null){
 		   response.sendRedirect(request.getContextPath() + "/");
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String cif = request.getParameter("cif");
	 
	        Empresa empresa = null;
	        List<Empresa> empresesList = new ArrayList<Empresa>();
	        String errorString = null;
	 
	        try {
	            empresa = EmpresaCore.findEmpresa(conn, cif);
	            empresesList = EmpresaCore.getEmpreses(conn);
	        } catch (SQLException | NamingException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }
	 
	         
	        // If no error.
	        // The product does not exist to edit.
	        // Redirect to productList page.
	        if (errorString != null && empresa == null) {
	            response.sendRedirect(request.getServletPath() + "/empresaList");
	            return;
	        }
	 
	        // Store errorString in request attribute, before forward to views.
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("empresa", empresa);
	        request.setAttribute("empresesList", empresesList);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Empreses"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/empresa/editEmpresaView.jsp");
	        dispatcher.forward(request, response);
 	   	}
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}