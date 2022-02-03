package servlet.empresa;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
 
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

@WebServlet(urlPatterns = { "/empresaList" })
public class EmpresaListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public EmpresaListServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	if (MyUtils.getLoginedUser(request.getSession()) == null){
 		   response.sendRedirect(request.getContextPath() + "/");
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{	 
	        List<Empresa> list = null;
	        list = EmpresaCore.getEmpreses(conn);
			list.addAll(EmpresaCore.getEmpresesUTE(conn));
	   
	        // Store info in request attribute, before forward to views
	        
	        request.setAttribute("empresesList", list);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Empreses")); 
	     
	        // Forward to /WEB-INF/views/productListView.jsp
	        RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/empresa/empresaListView.jsp");
	        dispatcher.forward(request, response);
 	   	}
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}
