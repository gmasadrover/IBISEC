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
		
	    String idFactura = multipartParams.getParametres().get("idFactura");
	    String idInforme = multipartParams.getParametres().get("idInforme");
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idProveidor = multipartParams.getParametres().get("idEmpresa");	    
	    String concepte = multipartParams.getParametres().get("concepte");	   
	    double valor = Double.parseDouble(multipartParams.getParametres().get("import"));	  
	    String nombreFactura = multipartParams.getParametres().get("nombre");
	    String tipusFactura = multipartParams.getParametres().get("tipus");
	    int idUsuariConformador = Integer.parseInt(multipartParams.getParametres().get("idConformador"));
	    String notes = multipartParams.getParametres().get("notes");
	    boolean novaAsignacio = false;
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date dataFactura = null;
	    Date dataEntrada = null;  
	    Date dataPasadaConformar = null;
	    Date dataConformada = null;
	    Date dataPasadaComptabilitat = null;
	    try {
			if (multipartParams.getParametres().get("dataEntrada") != null && ! multipartParams.getParametres().get("dataEntrada").isEmpty()) dataEntrada = formatter.parse(multipartParams.getParametres().get("dataEntrada"));
			if (multipartParams.getParametres().get("dataFactura") != null && ! multipartParams.getParametres().get("dataFactura").isEmpty()) dataFactura = formatter.parse(multipartParams.getParametres().get("dataFactura"));
			if (multipartParams.getParametres().get("dataPasadaConformar") != null && ! multipartParams.getParametres().get("dataPasadaConformar").isEmpty()) dataPasadaConformar = formatter.parse(multipartParams.getParametres().get("dataPasadaConformar"));
			if (multipartParams.getParametres().get("dataConformada") != null && ! multipartParams.getParametres().get("dataConformada").isEmpty()) dataConformada = formatter.parse(multipartParams.getParametres().get("dataConformada"));
			if (multipartParams.getParametres().get("dataPasadaComptabilitat") != null && ! multipartParams.getParametres().get("dataPasadaComptabilitat").isEmpty()) dataPasadaComptabilitat = formatter.parse(multipartParams.getParametres().get("dataPasadaComptabilitat"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    Factura factura = new Factura();
	    List<Fitxer> fitxers = multipartParams.getFitxers();
	    if (idActuacio.equals("-1")) {
	    	novaAsignacio = true;
	    	idActuacio = multipartParams.getParametres().get("incidenciesList");
	    	idInforme = multipartParams.getParametres().get("expedientsList");
	    	try {
				factura = FacturaCore.getFactura(conn, idFactura);	
				fitxers.add(factura.getArxiu());				
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	    
	    String errorString = null;	 	      
		if (errorString == null) {
	   		try {
	   			factura.setUsuariConformador(UsuariCore.findUsuariByID(conn, idUsuariConformador));	   			
	   			factura.setDataConformacio(dataConformada); 
	   			FacturaCore.modificarFactura(conn, factura, idUsuari);		   			
	   			//Tancar actuacio si es menor
	   			if (dataPasadaComptabilitat!=null) {
	   				InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
	   				if (informe.getOfertaSeleccionada().getPlic() <= valor) ActuacioCore.tancar(conn, idActuacio, "facturat", idUsuari);
	   			}
	   			
	   			if (novaAsignacio) {
	   				String idIncidencia = ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia();	   				
	   				//modificar arxius
				    Context env;
			    	String ruta = "";
					try {
						env = (Context)new InitialContext().lookup("java:comp/env");
						ruta = (String)env.lookup("ruta_base");
					} catch (NamingException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					InputStream inStream = null;
					OutputStream outStream = null;			   
				    for (Fitxer arxiu: fitxers) {			    			    	
				    	File tmpFile =  new File(ruta + "/documents/" + idIncidencia);
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme);
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Empreses");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Empreses/" + idProveidor);
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Empreses/" + idProveidor + "/Factures");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Empreses/" + idProveidor + "/Factures/" + idFactura);
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						String fileName = ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Empreses/" + idProveidor + "/Factures/" + idFactura + "/";
	        			File b = new File(fileName + arxiu.getNom());
				    	
	        			inStream = new FileInputStream(new File(arxiu.getRuta()));
			    	    outStream = new FileOutputStream(b);

			    	    byte[] buffer = new byte[1024];

			    	    int length;
			    	    //copy the file content in bytes
			    	    while ((length = inStream.read(buffer)) > 0){
			    	    	outStream.write(buffer, 0, length);
			    	    }

			    	    inStream.close();
			    	    outStream.close();

			    	    //delete the original file
			    	    Fitxers.eliminarFitxer(arxiu.getRuta());
				    }
	   			} else {
	   				FacturaCore.saveArxiu(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia(), idActuacio, idInforme, idProveidor, idFactura, fitxers, conn);
		   		}
	   		} catch (SQLException | NamingException e) {
	  			e.printStackTrace();
	  			errorString = e.getMessage();
	   		}
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
