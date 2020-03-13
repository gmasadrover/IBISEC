package servlet.factura;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
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
import bean.InformeActuacio;
import bean.Registre;
import bean.User;
import core.ActuacioCore;
import core.FacturaCore;
import core.InformeCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class DoEditFacturaServlet
 */
@WebServlet("/doEditFactura")
public class DoEditFacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditFacturaServlet() {
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
	    String idInforme = multipartParams.getParametres().get("expedientsList");
	    String idActuacio = multipartParams.getParametres().get("incidenciesList");
	    System.out.println(idInforme + " " + idActuacio);
	    String procediment = multipartParams.getParametres().get("procedimentsList");	   
	    String idProveidor = multipartParams.getParametres().get("idEmpresa");	    
	    String concepte = multipartParams.getParametres().get("concepte");	   
	    double valor = Double.parseDouble(multipartParams.getParametres().get("import"));	  
	    String nombreFactura = multipartParams.getParametres().get("nombre");
	    String tipusFactura = multipartParams.getParametres().get("tipus");
	    int idUsuariConformador = Integer.parseInt(multipartParams.getParametres().get("idConformador"));
	    String notes = multipartParams.getParametres().get("notes");
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date dataFactura = null;
	    Date dataEntrada = null;  
	    Date dataPasadaConformar = null;
	    Date dataConformada = null;
	    Date dataPasadaComptabilitat = null;
	    Date dataDescarregadaComptabilitat = null;
	    try {
			if (multipartParams.getParametres().get("dataEntrada") != null && ! multipartParams.getParametres().get("dataEntrada").isEmpty()) dataEntrada = formatter.parse(multipartParams.getParametres().get("dataEntrada"));
			if (multipartParams.getParametres().get("dataFactura") != null && ! multipartParams.getParametres().get("dataFactura").isEmpty()) dataFactura = formatter.parse(multipartParams.getParametres().get("dataFactura"));
			if (multipartParams.getParametres().get("dataPasadaConformar") != null && ! multipartParams.getParametres().get("dataPasadaConformar").isEmpty()) dataPasadaConformar = formatter.parse(multipartParams.getParametres().get("dataPasadaConformar"));
			if (multipartParams.getParametres().get("dataConformada") != null && ! multipartParams.getParametres().get("dataConformada").isEmpty()) dataConformada = formatter.parse(multipartParams.getParametres().get("dataConformada"));
			if (multipartParams.getParametres().get("dataPasadaComptabilitat") != null && ! multipartParams.getParametres().get("dataPasadaComptabilitat").isEmpty()) dataPasadaComptabilitat = formatter.parse(multipartParams.getParametres().get("dataPasadaComptabilitat"));
			if (multipartParams.getParametres().get("dataPasadaComptabilitat") != null && ! multipartParams.getParametres().get("dataDescarregadaComptabilitat").isEmpty()) dataDescarregadaComptabilitat = formatter.parse(multipartParams.getParametres().get("dataDescarregadaComptabilitat"));
	    } catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    Factura factura = new Factura();
	    List<Fitxer> fitxers = multipartParams.getFitxersByName().get("factura");
	    String errorString = null;	
	    
 	    if (idActuacio != null && idActuacio.equals("-1")) {
 	    	idActuacio = multipartParams.getParametres().get("incidenciesList");
 	    	idInforme = multipartParams.getParametres().get("expedientsList");
 	 	    if (idInforme == null || idActuacio == null) {
 		    	if (procediment == null) {
 		    		errorString = "Falta relacionar l'actuació o l'expedient";
 		    	} else {
 		    		idActuacio = procediment;
 		    		idInforme = procediment;
 		    		idProveidor = "";
 		    	}
 		    }	  
 	    	try {
 				factura = FacturaCore.getFactura(conn, idFactura);	
 				List<Fitxer> fitxerFactura = new ArrayList<Fitxer>();
 				fitxerFactura.add(factura.getFactura());
 				fitxers =  fitxerFactura;
 			} catch (SQLException | NamingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	    } else if(procediment != null) {
 	    	idActuacio = procediment;
 	    	idInforme = procediment;
 	    }
 	    factura = new Factura();
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
   		try {
   			factura.setUsuariConformador(UsuariCore.findUsuariByID(conn, idUsuariConformador));	
   			factura.setDataEnviatConformador(dataPasadaConformar);
   			factura.setDataConformacio(dataConformada); 
   			factura.setDataEnviatComptabilitat(dataPasadaComptabilitat);
   			factura.setDataDescarregadaConformada(dataDescarregadaComptabilitat);
   			FacturaCore.modificarFactura(conn, factura, idUsuari);		   			
   			//Tancar actuacio si es menor i tots els expedients estan facturats
   			if (dataPasadaComptabilitat!=null) {
   				InformeActuacio informeActual = InformeCore.getInformePrevi(conn, idInforme, false);
   				if (informeActual.getOfertaSeleccionada().getPlic() < Double.parseDouble(getServletContext().getInitParameter("importObraMajor"))) {
	   				if (informeActual.getOfertaSeleccionada().getPlic() > valor) {
	   					//	   				
	   				} else {
	   					InformeCore.modificarEstat(conn, idInforme, "garantia");
	   					//InformeCore.tancar(conn, idInforme);
	   				}		   				
   				}
   			}
			FacturaCore.saveArxiu(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia(), idActuacio, idInforme, idProveidor, idFactura, fitxers, conn, Usuari.getIdUsuari());
			FacturaCore.saveArxiuAltres(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia(), idActuacio, idInforme, idProveidor, idFactura, multipartParams.getFitxersByName().get("altres"), conn, Usuari.getIdUsuari());
   			
   		} catch (SQLException | NamingException e) {
  			e.printStackTrace();
  			errorString = e.getMessage();
   		}
	  	    
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("factura", factura);	   	
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/factura/editFacturaView.jsp");
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
