package servlet.factura;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Factura;
import core.ActuacioCore;
import core.FacturaCore;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class EnviarAConformarServlet
 */
@WebServlet("/enviarAConformar")
public class EnviarAConformarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnviarAConformarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);	
		
	    String idFactura = request.getParameter("ref");   
	    
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();	  
	    
	    Factura factura = new Factura();
	    
	    
	   		factura = FacturaCore.getFactura(conn, idFactura);	   			
			factura.setDataEnviatConformador(new Date());
			FacturaCore.modificarFactura(conn, factura, idUsuari);
			TascaCore.novaTasca(conn, "conformarFactura", factura.getUsuariConformador().getIdUsuari(), idUsuari, factura.getIdActuacio(), ActuacioCore.findActuacio(conn, factura.getIdActuacio()).getIdIncidencia(), "Conformar factura", "Conformar factura", idFactura, null, request.getRemoteAddr(), "automatic");
	       
	   	
	   	// Store infomation to request attribute, before forward to views.
	   
	   		response.sendRedirect(request.getContextPath() + "/facturaDetalls?ref=" + idFactura);
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
