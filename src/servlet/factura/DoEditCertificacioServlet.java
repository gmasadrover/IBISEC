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

import bean.Factura;
import bean.InformeActuacio;
import core.ActuacioCore;
import core.FacturaCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditCertificacioServlet
 */
@WebServlet("/doEditCertificacio")
public class DoEditCertificacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditCertificacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);		
	    String idCertificacio = request.getParameter("idCertificacio");
	    String idInforme = request.getParameter("idInforme");
	    String idActuacio = request.getParameter("idActuacio");
	    String idProveidor = request.getParameter("idEmpresa");	    
	    String concepte = request.getParameter("concepte");	   
	    double valor = Double.parseDouble(request.getParameter("import"));	  
	    String nombreCertificacio = request.getParameter("nombre");
	    int idUsuariConformador = Integer.parseInt(request.getParameter("idConformador"));
	    String notes = request.getParameter("notes");
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date dataCertificacio = null;
	    Date dataEntrada = null;  
	    Date dataPasadaConformar = null;
	    Date dataConformada = null;
	    Date dataPasadaComptabilitat = null;
	    try {
			if (request.getParameter("dataEntrada") != null && ! request.getParameter("dataEntrada").isEmpty()) dataEntrada = formatter.parse(request.getParameter("dataEntrada"));
			if (request.getParameter("dataFactura") != null && ! request.getParameter("dataFactura").isEmpty()) dataCertificacio = formatter.parse(request.getParameter("dataFactura"));
			if (request.getParameter("dataPasadaConformar") != null && ! request.getParameter("dataPasadaConformar").isEmpty()) dataPasadaConformar = formatter.parse(request.getParameter("dataPasadaConformar"));
			if (request.getParameter("dataConformada") != null && ! request.getParameter("dataConformada").isEmpty()) dataConformada = formatter.parse(request.getParameter("dataConformada"));
			if (request.getParameter("dataPasadaComptabilitat") != null && ! request.getParameter("dataPasadaComptabilitat").isEmpty()) dataPasadaComptabilitat = formatter.parse(request.getParameter("dataPasadaComptabilitat"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    
	    Factura certificacio = new Factura();
	    certificacio.setIdFactura(idCertificacio);
	    certificacio.setIdActuacio(idActuacio);
	    certificacio.setIdInforme(idInforme);
	    certificacio.setIdProveidor(idProveidor);
	    certificacio.setConcepte(concepte);
	    certificacio.setValor(valor);
	    certificacio.setDataFactura(dataCertificacio);
	    certificacio.setDataEntrada(dataEntrada);
	    certificacio.setNombreFactura(nombreCertificacio);
	    certificacio.setNotes(notes);
	    
	    String errorString = null;	 	      
		if (errorString == null) {
	   		try {
	   			certificacio.setUsuariConformador(UsuariCore.findUsuariByID(conn, idUsuariConformador));
	   			certificacio.setDataEnviatConformador(dataPasadaConformar);
	   			certificacio.setDataConformacio(dataConformada);
	   			certificacio.setDataEnviatComptabilitat(dataPasadaComptabilitat);
	   			FacturaCore.modificarCertificacio(conn, certificacio, idUsuari);	
	   		} catch (SQLException e) {
	  			e.printStackTrace();
	  			errorString = e.getMessage();
	   		}
	   	}	    
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("certificacio", certificacio);	   	
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/factura/editCertificacioView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   			response.sendRedirect(request.getContextPath() + "/certificacioDetalls?ref=" + idCertificacio);
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
