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
import core.ActuacioCore;
import core.FacturaCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateCertificacioServlet
 */
@WebServlet("/doCreateCertificacio")
public class DoCreateCertificacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateCertificacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String idCertificacio = multipartParams.getParametres().get("idCertificacio");
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idInforme = multipartParams.getParametres().get("idInforme");
	    String idProveidor = multipartParams.getParametres().get("idEmpresa");	    
	    String concepte = multipartParams.getParametres().get("concepte");	   
	    double valor = Double.parseDouble(multipartParams.getParametres().get("import").replace(",", "."));	  
	    String nombreFactura = multipartParams.getParametres().get("nombre");
	    int idUsuariConformador = Integer.parseInt(multipartParams.getParametres().get("idConformador"));
	    String notes = multipartParams.getParametres().get("notes");
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date dataCertificacio = null;
	    Date dataEntrada = null;
	    Date dataPasadaConformar = null;
		try {
			if (multipartParams.getParametres().get("dataEntrada") != null && ! multipartParams.getParametres().get("dataEntrada").isEmpty()) dataEntrada = formatter.parse(multipartParams.getParametres().get("dataEntrada"));
			if (multipartParams.getParametres().get("dataFactura") != null && ! multipartParams.getParametres().get("dataFactura").isEmpty()) dataCertificacio = formatter.parse(multipartParams.getParametres().get("dataFactura"));
			if (multipartParams.getParametres().get("dataPasadaConformar") != null && ! multipartParams.getParametres().get("dataPasadaConformar").isEmpty()) dataPasadaConformar = formatter.parse(multipartParams.getParametres().get("dataPasadaConformar"));
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
	    certificacio.setNombreFactura(nombreFactura);
	    certificacio.setNotes(notes);
	    
	    String errorString = null;	 	      
	   	if (errorString == null) {
	   		try {
	   			Actuacio actuacio = ActuacioCore.findActuacio(conn, idActuacio);
	   			certificacio.setUsuariConformador(UsuariCore.findUsuariByID(conn, idUsuariConformador));
	   			certificacio.setDataEnviatConformador(dataPasadaConformar);
	   			FacturaCore.newCertificacio(conn, certificacio, idUsuari);	
	   			FacturaCore.saveArxiuCertificacio(actuacio.getIdIncidencia(), idActuacio, idInforme, idProveidor, idCertificacio, multipartParams.getFitxers(), conn);
	   			int usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariCertificacions")); 
	   			if (usuariTasca != idUsuari) {
	   				TascaCore.novaTasca(conn, "revisarCertificacio", usuariTasca, idUsuari, idActuacio, actuacio.getIdIncidencia(), "Nova certificació", "Nova certificació", idCertificacio, null);
	   			}		    	
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
