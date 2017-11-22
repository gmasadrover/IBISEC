package servlet.factura;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Actuacio;
import bean.Factura;
import bean.Registre;
import core.ActuacioCore;
import core.EmpresaCore;
import core.FacturaCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
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
	    
	    String errorString = null;	 	      
	   	if (errorString == null) {
	   		try {
	   			factura = FacturaCore.getFactura(conn, idFactura);	   			
	   			factura.setDataEnviatConformador(new Date());
	   			FacturaCore.modificarFactura(conn, factura, idUsuari);
	   			TascaCore.novaTasca(conn, "conformarFactura", factura.getUsuariConformador().getIdUsuari(), idUsuari, factura.getIdActuacio(), ActuacioCore.findActuacio(conn, factura.getIdActuacio()).getIdIncidencia(), "Conformar factura", "Conformar factura", idFactura, null);
	   		} catch (SQLException | NamingException e) {
	  			e.printStackTrace();
	  			errorString = e.getMessage();
	   		}
	   	}	    
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/createActuacioView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		response.sendRedirect(request.getContextPath() + "/facturaDetalls?ref=" + idFactura);
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
