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
import bean.User;
import core.ActuacioCore;
import core.EmpresaCore;
import core.FacturaCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
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
		
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    String idFactura = multipartParams.getParametres().get("idFactura");
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idInforme = multipartParams.getParametres().get("idInforme");
	    String idProveidor = multipartParams.getParametres().get("idEmpresa");	    
	    String concepte = multipartParams.getParametres().get("concepte");	   
	    double valor = Double.parseDouble(multipartParams.getParametres().get("import").replace(",", "."));	  
	    String nombreFactura = multipartParams.getParametres().get("nombre");
	    String tipusFactura = multipartParams.getParametres().get("tipus");
	    int idUsuariConformador = Integer.parseInt(multipartParams.getParametres().get("idConformador"));
	    String notes = multipartParams.getParametres().get("notes");
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date dataFactura = null;
	    Date dataEntrada = null;
	    Date dataPasadaConformar = null;
		try {
			if (multipartParams.getParametres().get("dataEntrada") != null && ! multipartParams.getParametres().get("dataEntrada").isEmpty()) dataEntrada = formatter.parse(multipartParams.getParametres().get("dataEntrada"));
			if (multipartParams.getParametres().get("dataFactura") != null && ! multipartParams.getParametres().get("dataFactura").isEmpty()) dataFactura = formatter.parse(multipartParams.getParametres().get("dataFactura"));
			if (multipartParams.getParametres().get("dataPasadaConformar") != null && ! multipartParams.getParametres().get("dataPasadaConformar").isEmpty()) dataPasadaConformar = formatter.parse(multipartParams.getParametres().get("dataPasadaConformar"));
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
	    factura.setNotes(notes);
	    String errorString = null;	 	      
	   	if (errorString == null) {
	   		try {
	   			Actuacio actuacio = ActuacioCore.findActuacio(conn, idActuacio);
	   			factura.setUsuariConformador(UsuariCore.findUsuariByID(conn, idUsuariConformador));
	   			FacturaCore.newFactura(conn, factura, idUsuari);	
	   			FacturaCore.saveArxiu(actuacio.getIdIncidencia(), idActuacio, idInforme, idProveidor, idFactura, multipartParams.getFitxers(), conn, Usuari.getIdUsuari());
	   			//Registram la factura
	   			Date peticio = new Date();
	   			peticio = formatter.parse(factura.getDataEntradaString());
	   			Registre registre = new Registre(RegistreCore.getNewCode(conn, "E"), peticio, "E", EmpresaCore.findEmpresa(conn, idProveidor).getName(), "factura " + idFactura, actuacio.getIdIncidencia(), idInforme, actuacio.getCentre().getIdCentre(), idUsuari, new Date());
	   			RegistreCore.nouRegistre(conn, "E", registre);
	   		} catch (SQLException | NamingException | ParseException e) {
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
