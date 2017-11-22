package servlet.registre;

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

import bean.Actuacio;
import bean.Registre;
import core.ActuacioCore;
import core.LoggerCore;
import core.RegistreCore;
import utils.Fitxers;
import utils.Fitxers.Fitxer;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditRegistreServlet
 */
@WebServlet("/DoEditRegistre")
public class DoEditRegistreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditRegistreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);		
	    String idRegistre = request.getParameter("idCodiRegistre");
	    String entradaSortida = request.getParameter("entradaSortida");
	    String remDes = request.getParameter("remitent");
	    String tipus = URLDecoder.decode(request.getParameter("tipus"),  "UTF-8");	
	    String contingut = request.getParameter("contingut");	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	    
	    Date peticio = new Date();
	    String idCentresSeleccionats = "";
	    String[] idCentres = null;
	    String idIncidencies = "";
	    List<String> idIncidenciesList = new ArrayList<String>();
	    String errorString = null;
	    Registre registreOld = new Registre();	  
	    Registre registre = new Registre();	  
	    String anular = request.getParameter("anular");
	    if (anular != null) {
	    	try {
				RegistreCore.anularRegistre(conn, idRegistre, entradaSortida);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				errorString = e.toString();
				e.printStackTrace();
			}
	    } else {
			try {			
				idCentres = request.getParameterValues("idCentre");
		    	if (idCentres != null) {
		    		idCentresSeleccionats = "";
		    		idIncidencies = "";
	 		        for(int i=0; i<idCentres.length; i++) { 		
	 		        	idCentresSeleccionats += idCentres[i] + "#";
	 		        	try {
	 		        		if (!"-1".equals(idCentres[i])) {
		 		        		if (!"-1".equals(request.getParameter("incidenciesList" + idCentres[i])) && !"-2".equals(request.getParameter("incidenciesList" + idCentres[i]))) { 		        			
									Actuacio actuacio = ActuacioCore.findActuacio(conn, request.getParameter("incidenciesList" + idCentres[i]));							
									idIncidenciesList.add(actuacio.getIdIncidencia());
				 		        	idIncidencies =  actuacio.getIdIncidencia() + "#";
		 		        		} else {
		 		        			idIncidenciesList.add(request.getParameter("incidenciesList" + idCentres[i]));
				 		        	idIncidencies =  request.getParameter("incidenciesList" + idCentres[i]) + "#";
		 		        		}
	 		        		}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	 		        	
	 		        }
		    	}
		    	
		    	
				peticio = formatter.parse(request.getParameter("peticio"));
				registreOld = RegistreCore.findRegistre(conn, entradaSortida, idRegistre);
				registre = RegistreCore.findRegistre(conn, entradaSortida, idRegistre);
				registre.setRemDes(remDes);
				registre.setTipus(tipus);
				registre.setContingut(contingut);
				registre.setData(peticio);
				if (idIncidencies.isEmpty()) idIncidencies = registreOld.getIdIncidencies();
				registre.setIdIncidencies(idIncidencies);
				if (idCentresSeleccionats.isEmpty()) idCentresSeleccionats = registreOld.getIdCentres();
				registre.setIdCentres(idCentresSeleccionats);
				
			    RegistreCore.modificarRegistre(conn, registre, entradaSortida);
			    
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
			    List<Fitxer> fixers = Fitxers.ObtenirFitxers(registreOld.getPrimeraIncidencia(), "", "RegistreE", registreOld.getId(), "");
			    for (Fitxer arxiu: fixers) {			    			    	
			    	File incidenciaFile = new File(ruta + "/documents/" + registre.getPrimeraIncidencia());
	    			if (!incidenciaFile.exists()) {
	    				incidenciaFile.mkdir();
	    			}
	    			File tipusFile = new File(ruta + "/documents/" + registre.getPrimeraIncidencia() + "/RegistreE/");
        			if (!tipusFile.exists()) {
        				tipusFile.mkdir();
        			}
        			File idTipusFile = new File(ruta + "/documents/" + registre.getPrimeraIncidencia() + "/RegistreE/" + registre.getId());
        			if (!idTipusFile.exists()) {
        				idTipusFile.mkdir();
        			}    
        			File b = new File(ruta + "/documents/" + registre.getPrimeraIncidencia() + "/RegistreE/" + registre.getId() + "/" + arxiu.getNom());
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
			    
			} catch (ParseException | SQLException | NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				errorString = e1.toString();
			}   
	    }
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("registre", registre);	   	
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/registre/registreEditView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   			response.sendRedirect(request.getContextPath() + "/registre?tipus=" + entradaSortida + "&referencia=" + idRegistre);
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
