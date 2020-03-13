package servlet.registre;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import bean.Actuacio;
import bean.Incidencia;
import bean.Registre;
import bean.User;
import core.ActuacioCore;
import core.IncidenciaCore;
import core.RegistreCore;
import core.UsuariCore;
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
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User usuari = MyUtils.getLoginedUser(request.getSession());
	    String idRegistre =  multipartParams.getParametres().get("idCodiRegistre");
	    String entradaSortida =  multipartParams.getParametres().get("entradaSortida");
	    String remDes =  multipartParams.getParametres().get("remitent");
	    String tipus = URLDecoder.decode(multipartParams.getParametres().get("tipus"),  "UTF-8");
	    String contingut =  multipartParams.getParametres().get("contingut");	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	    
	    Date peticio = new Date();
	    String idCentresSeleccionats = "";
	    String[] idCentres = null;
	    String idIncidencies = "";
	    String idInformes = "";
	    List<String> idIncidenciesList = new ArrayList<String>();
	    String errorString = null;
	    Registre registreOld = new Registre();	  
	    Registre registre = new Registre();	  
	    String anular = multipartParams.getParametres().get("anular");
	    
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
		    	registreOld = RegistreCore.findRegistre(conn, entradaSortida, idRegistre);			    	
		    	idCentres = multipartParams.getParametres().get("idCentresSeleccionats").split("#");
		    	if (idCentres != null) {
		    		idCentresSeleccionats = "";
		    		idIncidencies = "";
		    		idInformes = "";
	 		        for(int i=0; i<idCentres.length; i++) { 		
	 		        	idCentresSeleccionats += idCentres[i] + "#";	 		        	
	 		        	try {
	 		        		if (!idCentres[i].isEmpty() && !"-1".equals(idCentres[i])) {
	 		        			if (tipus.equals("Procediment judicial")) {
	 		        				idIncidencies = multipartParams.getParametres().get("procedimentsList");
	 		        			} else {
	 		        				if (!"-1".equals(multipartParams.getParametres().get("incidenciesList" + idCentres[i])) && !"-2".equals(multipartParams.getParametres().get("incidenciesList" + idCentres[i]))) { 		        			
										Actuacio actuacio = ActuacioCore.findActuacio(conn, multipartParams.getParametres().get("incidenciesList" + idCentres[i]));							
										idIncidenciesList.add(actuacio.getIdIncidencia());
					 		        	idIncidencies +=  actuacio.getIdIncidencia() + "#";
					 		        	idInformes += multipartParams.getParametres().get("expedientsList" + idCentres[i]) + "#";
			 		        		} else if("-1".equals(multipartParams.getParametres().get("incidenciesList" + idCentres[i]))) {
			 		        			Incidencia incidencia = new Incidencia();
				 		   	   		    incidencia.setIdIncidencia(IncidenciaCore.getNewCode(conn));
				 		   	   		    incidencia.setIdCentre(idCentres[i]);
				 		   	   		    incidencia.setUsuCre(UsuariCore.findUsuariByID(conn, usuari.getIdUsuari()));
				 		   	   		    incidencia.setDescripcio(registreOld.getContingut());
				 		   	   		    incidencia.setSolicitant(registreOld.getRemDes());
				 		   	   		    IncidenciaCore.novaIncidencia(conn, incidencia);
			 		        			idIncidenciesList.add(incidencia.getIdIncidencia() + "#");
					 		        	idIncidencies +=  incidencia.getIdIncidencia() + "#";
			 		        		}
	 		        			}		 		        		
	 		        		}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	 		        	
	 		        }
		    	}
				peticio = formatter.parse(multipartParams.getParametres().get("peticio"));				
				registre = RegistreCore.findRegistre(conn, entradaSortida, idRegistre);
				registre.setRemDes(remDes);
				registre.setTipus(tipus);
				registre.setContingut(contingut);
				registre.setData(peticio);
				if (idIncidencies.isEmpty() || idIncidencies.equals("-1#")) idIncidencies = registreOld.getIdIncidencies();
				registre.setIdIncidencies(idIncidencies);
				if (idInformes.isEmpty() || idInformes.equals("-1#")) idInformes = registreOld.getIdInforme();
				registre.setIdInforme(idInformes);
				if (idCentresSeleccionats.isEmpty() || idCentresSeleccionats.equals("#")) idCentresSeleccionats = registreOld.getIdCentres();
				registre.setIdCentres(idCentresSeleccionats);
				if (multipartParams.getFitxersByName().get("confirmacioRecepcio") != null ) {
					RegistreCore.guardarConfirmacioRecepcio(registre, multipartParams.getFitxersByName().get("confirmacioRecepcio"));
				}
			    RegistreCore.modificarRegistre(conn, registre, entradaSortida);
			    RegistreCore.modificarNotificacio(conn, registre);
			    List<Fitxer> fitxers = multipartParams.getFitxersByName().get("file");
	   			if (fitxers != null ) {
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
	   				if (!entradaSortida.equals("E")) {
	   					tmpFile = new File(ruta + "/documents/Registre Sortida/" + registre.getId());
	   				} else {
	   					tmpFile = new File(ruta + "/documents/Registre Entrada/" + registre.getId());
	   				}
	   				
	   				if (!tmpFile.exists()) {
	   					tmpFile.mkdir();
	   				}
		   			for(int i=0;i<fitxers.size();i++){		        	
			            Fitxer fitxer = (Fitxer) fitxers.get(i);	            
			            if (fitxer.getFitxer() != null && fitxer.getFitxer().getName() != "") {			            	
			            	File archivo_server = new File(tmpFile + "/" + fitxer.getFitxer().getName());
			            	try {
			               		fitxer.getFitxer().write(archivo_server);
			               		if (!entradaSortida.equals("E")) {
			               			Fitxers.guardarRegistreFitxer(conn, fitxer.getFitxer().getName(), ruta + "/documents/Registre Sortida/" + registre.getId() + "/" + fitxer.getFitxer().getName(), usuari.getIdUsuari());
			               		} else {
			               			Fitxers.guardarRegistreFitxer(conn, fitxer.getFitxer().getName(), ruta + "/documents/Registre Entrada/" + registre.getId() + "/" + fitxer.getFitxer().getName(), usuari.getIdUsuari());
			               		}
			               		
			           		} catch (Exception e) {
			           			e.printStackTrace();
			           		}
			               	
			            } 
			        }
	   			}
	   			registre = RegistreCore.findRegistre(conn, entradaSortida, registre.getId());
	   			RegistreCore.crearResguard(registre, entradaSortida);  			
			    
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
