package servlet.factura;

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

import bean.Factura;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.FacturaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CertificacioDetailsServlet
 */
@WebServlet("/certificacioDetalls")
public class CertificacioDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CertificacioDetailsServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.factures_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String idCertificacio = request.getParameter("ref");	 
			boolean isUsuariConformador = false;
	        Factura certificacio = null;	 
	        String errorString = null;
	 
	        try {
	            certificacio = FacturaCore.getCertificacio(conn, idCertificacio);
	            if (certificacio.getUsuariConformador() != null) isUsuariConformador = certificacio.getUsuariConformador().getIdUsuari()  == usuari.getIdUsuari();
	        } catch (SQLException | NamingException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }
	 
	         
	        // If no error.
	        // The product does not exist to edit.
	        // Redirect to productList page.
	        if (errorString != null && certificacio == null) {
	            response.sendRedirect(request.getServletPath() + "certificacions");
	            return;
	        }
	 
	        // Store errorString in request attribute, before forward to views.
	        request.setAttribute("canModificar", UsuariCore.hasPermision(conn, usuari, SectionPage.factures_crear));
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("certificacio", certificacio);
	        request.setAttribute("isUsuariConformador", isUsuariConformador);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Factures"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/factura/certificacioView.jsp");
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
