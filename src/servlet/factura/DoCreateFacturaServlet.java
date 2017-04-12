package servlet.factura;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Factura;
import core.FacturaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateFacturaServlet
 */
@WebServlet("/doCreateFactura")
public class DoCreateFacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateFacturaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		//Registrar actuaci�
	    String idFactura = request.getParameter("idFactura");
	    String idActuacio = request.getParameter("idActuacio");
	    String idInforme = request.getParameter("idInforme");
	    String idProveidor = request.getParameter("idEmpresa");	    
	    String concepte = request.getParameter("concepte");	   
	    double valor = Double.parseDouble(request.getParameter("import"));	  
	    String nombreFactura = request.getParameter("nombre");
	    String tipusFactura = request.getParameter("tipus");
	    int idUsuariConformador = Integer.parseInt(request.getParameter("idConformador"));
	    String notes = request.getParameter("notes");
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date dataFactura = new Date();
	    Date dataEntrada = new Date();
	    Date dataConformacio = new Date();
		try {
			dataEntrada = formatter.parse(request.getParameter("dataEntrada"));
			dataFactura = formatter.parse(request.getParameter("dataFactura"));
			dataConformacio = formatter.parse(request.getParameter("dataConformacio"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    
	    Factura factura = new Factura();
	    factura.setIdFactura(idFactura);
	    factura.setIdActuacio(idActuacio);
	    factura.setIdInforme(idInforme);
	    factura.setIdProveidor(idProveidor);
	    factura.setConcepte(concepte);
	    factura.setValor(valor);
	    factura.setDataFactura(dataFactura);
	    factura.setDataEntrada(dataEntrada);
	    factura.setNombreFactura(nombreFactura);
	    factura.setTipusFactura(tipusFactura);
	    
	    factura.setDataConformacio(dataConformacio);
	    factura.setNotes(notes);
	    
	    String errorString = null;	 	      
	   	if (errorString == null) {
	   		try {
	   			factura.setUsuariConformador(UsuariCore.findUsuariByID(conn, idUsuariConformador));
	   			FacturaCore.newFactura(conn, factura, idUsuari);
	   		} catch (SQLException e) {
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
	   		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
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
