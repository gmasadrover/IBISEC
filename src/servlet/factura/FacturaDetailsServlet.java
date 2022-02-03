package servlet.factura;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Factura;
import bean.InformeActuacio;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.FacturaCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class FacturaDetailsServlet
 */
@WebServlet("/facturaDetalls")
public class FacturaDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FacturaDetailsServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.factures_view)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String idFactura = request.getParameter("ref");	 
			boolean isUsuariConformador = false;
	        Factura factura = null;	 
	        InformeActuacio informe = new InformeActuacio();
	      
	 
	        factura = FacturaCore.getFactura(conn, idFactura);
			if (factura.getUsuariConformador() != null) isUsuariConformador = factura.getUsuariConformador().getIdUsuari()  == usuari.getIdUsuari();
			if (factura.getIdInforme() != null && !factura.getIdInforme().equals("-1")) {
				informe = InformeCore.getInformePrevi(conn, factura.getIdInforme(), false);
			}
	 
	         
	      
	        // Store errorString in request attribute, before forward to views.
	        request.setAttribute("canModificar", UsuariCore.hasPermision(conn, usuari, SectionPage.factures_crear));
	       
	        request.setAttribute("factura", factura);
	        request.setAttribute("informe", informe);
	        request.setAttribute("isUsuariConformador", isUsuariConformador);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Factures"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/factura/facturaView.jsp");
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
