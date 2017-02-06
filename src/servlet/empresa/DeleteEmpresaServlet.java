package servlet.empresa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.EmpresaCore;
import core.UsuariCore;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/deleteProduct" })
public class DeleteEmpresaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public DeleteEmpresaServlet() {
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
	        String code = request.getParameter("code");
	 
	        String errorString = null;
	 
	        try {
	            EmpresaCore.deleteEmpresa(conn, code);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }
	         
	 
	        // If an error redirected to an error page.
	        if (errorString != null) {
	 
	            // Store the information in the request attribute, before forward to views.
	            request.setAttribute("errorString", errorString);
	            //
	            request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Empreses"));
	            RequestDispatcher dispatcher = request.getServletContext()
	                    .getRequestDispatcher("/WEB-INF/views/empresa/deleteEmpresaErrorView.jsp");
	            dispatcher.forward(request, response);
	        }
	 
	        // If everything nice.
	        // Redirect to the product listing page.        
	        else {
	            response.sendRedirect(request.getContextPath() + "/productList");
	        }
 	   	}
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}