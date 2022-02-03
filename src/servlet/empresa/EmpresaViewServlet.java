package servlet.empresa;

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
import bean.Bastanteo;
import bean.ControlPage.SectionPage;
import core.BastanteosCore;
import core.ControlPageCore;
import core.EmpresaCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EmpresaViewServlet
 */
@WebServlet("/empresa")
public class EmpresaViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpresaViewServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String cif = request.getParameter("cif");
	 
	        Empresa empresa = null;
	        List<InformeActuacio> informesEmpresa = new ArrayList<InformeActuacio>();
	        List<Bastanteo> validacions = new ArrayList<Bastanteo>();
	     
	 
	        empresa = EmpresaCore.findEmpresa(conn, cif);	   
			informesEmpresa = InformeCore.getInformesEmpresa(conn, cif);
			validacions = BastanteosCore.findBastanteosEmpresa(conn, cif);
	 
	         
	        // If no error.
	        // The product does not exist to edit.
	        // Redirect to productList page.
	        if (empresa == null) {
	            response.sendRedirect(request.getServletPath() + "/empresaList");
	            return;
	        }
	 
	        // Store errorString in request attribute, before forward to views.
	       
	        request.setAttribute("informesEmpresa", informesEmpresa);
	        request.setAttribute("empresa", empresa);
	        request.setAttribute("validacions", validacions);
	        request.setAttribute("canModificar", UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_crear));
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Empreses"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/empresa/empresaView.jsp");
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
