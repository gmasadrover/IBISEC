package servlet.registre;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import bean.Actuacio;
import bean.Incidencia;
import bean.Judicial;
import bean.Registre;
import core.ActuacioCore;
import core.IncidenciaCore;
import core.JudicialCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class DoCreateSortidaServlet
 */
@WebServlet("/DoCreateSortidaServlet")
public class DoCreateSortidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateSortidaServlet() {
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
	    String referencia = "";
	    String remitent = multipartParams.getParametres().get("destinatari");
	    String tipus = URLDecoder.decode(multipartParams.getParametres().get("tipus"),  "UTF-8");	    
	    String idCentresSeleccionats = "";
	    String[] idCentres = null;
	    String contingut = multipartParams.getParametres().get("contingut");
	    String idIncidencies = "";
	    String idInformes = "";
	    String refPro = "";
	    String numAutos = "";
	    List<String> idIncidenciesList = new ArrayList<String>();
	    List<String> idInformesList = new ArrayList<String>();
	    if (multipartParams.getParametres().get("idIncidenciaSeleccionada") != null && !multipartParams.getParametres().get("idIncidenciaSeleccionada").isEmpty()) {	    	
	    	try {
	    		idIncidencies =  multipartParams.getParametres().get("idIncidenciaSeleccionada");
		    	idIncidenciesList.add( multipartParams.getParametres().get("idIncidenciaSeleccionada"));
		    	idInformes = multipartParams.getParametres().get("idInformeSeleccionat");
		    	idInformesList.add( multipartParams.getParametres().get("idInformeSeleccionat"));
	    		idCentres = new String[] {IncidenciaCore.findIncidencia(conn,idIncidencies).getIdCentre()};
	    		idCentresSeleccionats = IncidenciaCore.findIncidencia(conn,idIncidencies).getIdCentre();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } else {
	    	idCentres = multipartParams.getParametres().get("idCentresSeleccionats").split("#");
	    	if (idCentres != null) {
	    		idCentresSeleccionats = "";
	    		idIncidencies = "";
	    		idInformes = "";
 		        for(int i=1; i<idCentres.length; i++) { 		
 		        	idCentresSeleccionats += idCentres[i] + "#";
 		        	if (!tipus.equals("Procediment judicial")) {
	 		        	try {
	 		        		if (!"-1".equals(idCentres[i])) {
		 		        		if (!"-1".equals(multipartParams.getParametres().get("incidenciesList" + idCentres[i])) && !"-2".equals(multipartParams.getParametres().get("incidenciesList" + idCentres[i]))) { 		        			
									Actuacio actuacio = ActuacioCore.findActuacio(conn, multipartParams.getParametres().get("incidenciesList" + idCentres[i]));							
									idIncidenciesList.add(actuacio.getIdIncidencia());
				 		        	idIncidencies =  actuacio.getIdIncidencia() + "#";
				 		        	idInformes = multipartParams.getParametres().get("expedientsList" + idCentres[i]) + "#";
		 		        		} else {
		 		        			idIncidenciesList.add(multipartParams.getParametres().get("incidenciesList" + idCentres[i]));
				 		        	idIncidencies =  multipartParams.getParametres().get("incidenciesList" + idCentres[i]) + "#";
				 		        	idInformes = multipartParams.getParametres().get("expedientsList" + idCentres[i]) + "#";
		 		        		}
	 		        		}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
 		        	} else if(tipus.equals("Procediment judicial")) {
 			    		if (multipartParams.getParametres().get("idProcediment").equals("-2")) {	
 			    			numAutos = multipartParams.getParametres().get("numautos");
 			    			Judicial judicial = new Judicial();
 			    			judicial.setNumAutos(multipartParams.getParametres().get("numautos"));
 			    			judicial.setJutjat(multipartParams.getParametres().get("jutjat"));
 			    			judicial.setEstat("obert");
 			    			try {
 			    				refPro = JudicialCore.nouProcediment(conn, judicial, "", null);
 							} catch (SQLException e) {
 								// TODO Auto-generated catch block
 								e.printStackTrace();
 							}
 			    		} else {
 			    			refPro = multipartParams.getParametres().get("idProcediment");
 							try {
 								Judicial judicial = JudicialCore.findProcediment(conn, refPro);
 								numAutos = judicial.getNumAutos();
 							} catch (SQLException | NamingException e) {
 								// TODO Auto-generated catch block
 								e.printStackTrace();
 							}
 			    			
 			    		}	
 			    		idIncidencies = refPro;
 			    	} 		        	
 		        }	    	
	    	}
	    }
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    String referenciesIncidencies = "";
	    Date peticio = new Date();
		try {
			referencia = RegistreCore.getNewCode(conn, "S");
			peticio = formatter.parse(multipartParams.getParametres().get("peticio"));
		} catch (ParseException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Registre registre = new Registre(referencia, peticio, tipus, remitent, contingut, idIncidencies, idInformes, idCentresSeleccionats, idUsuari, new Date());
	    
	    String errorString = null;
	 	String ipRemote = request.getRemoteAddr();
	   	if (errorString == null) {
	   		try {
	   			RegistreCore.nouRegistre(conn, "S", registre);	
	   			//Crear Incidencia
	   			for(int i=0; i<idCentres.length - 1; i++) { 
	   				if (!idIncidenciesList.isEmpty()) {
		   				if ("-1".equals(idIncidenciesList.get(i))) {
				   		    referenciesIncidencies += "-1#";			   		       			
			   			} else { //Cream notificació a gerència
			   				String idIncidencia = idIncidenciesList.get(i);
			   				if (idIncidencia == null) idIncidencia = "-1";
			   				referenciesIncidencies += idIncidencia + "#";
			   			}	
	   				} else {
	   					String idIncidencia = "-1";
		   				referenciesIncidencies += idIncidencia + "#";
	   				}
	   			}	 
	   			//Si procediment cream tasca
	   			if(tipus.equals("Procediment judicial")) {	   				
	   				int usuariTasca = UsuariCore.finCap(conn, "juridica").getIdUsuari();
	   				TascaCore.novaTasca(conn, "judicial", usuariTasca, idUsuari, "-1", "-1", "S'ha registrat un document de sortida al procediment " + numAutos, "Nova documentaci� procediment", refPro, null, ipRemote, "automatic");
	   				referenciesIncidencies = refPro;
	   			}
	   			//Actualitzam referències registres
	   			RegistreCore.actualitzarIdIncidencia(conn, "S", referencia, referenciesIncidencies);	   			
		   			
	   			Context env;
		    	String ruta = "";
   				try {
   					env = (Context)new InitialContext().lookup("java:comp/env");
   					ruta = (String)env.lookup("ruta_base");
   				} catch (NamingException e2) {
   					// TODO Auto-generated catch block
   					e2.printStackTrace();
   				}
   				File tmpFile =  null;
   				tmpFile = new File(ruta + "/documents/Registre Sortida/" + registre.getId());
   				if (!tmpFile.exists()) {
   					tmpFile.mkdir();
   				}
	   			List<Fitxer> fitxers = multipartParams.getFitxersByName().get("file");
	   			if (fitxers != null ) {
	   				
		   			for(int i=0;i<fitxers.size();i++){		        	
			            Fitxer fitxer = (Fitxer) fitxers.get(i);	            
			            if (fitxer.getFitxer() != null && fitxer.getFitxer().getName() != "") {			            	
			            	File archivo_server = new File(tmpFile + "/" + fitxer.getFitxer().getName());
			            	try {
			               		fitxer.getFitxer().write(archivo_server);
			               		Fitxers.guardarRegistreFitxer(conn, fitxer.getFitxer().getName(), ruta + "/documents/Registre Sortida/" + registre.getId() + "/" + fitxer.getFitxer().getName(), idUsuari);
			           		} catch (Exception e) {
			           			e.printStackTrace();
			           		}
			               	
			            } 
			        }
	   			}
	   			
	   			registre = RegistreCore.findRegistre(conn, "S", registre.getId());
	   			RegistreCore.crearResguard(registre, "S");   	
	   			
	   		} catch (SQLException | NamingException e) {
	  			e.printStackTrace();
	  			errorString = e.getMessage();
	   		}
	   		
	   	
	   	}
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("registre", registre);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/registre/createSortidaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		response.sendRedirect(request.getContextPath() + "/registreSortida");
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
