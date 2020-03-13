package servlet.registre;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.Authenticator.RequestorType;
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
import bean.User;
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
 * Servlet implementation class DoCreateEntradaServlet
 */
@WebServlet("/DoCreateRegistreEntrada")
public class DoCreateEntradaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateEntradaServlet() {
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
	    String referencia = "";
	    String remitent = multipartParams.getParametres().get("remitent");
	    String tipus = URLDecoder.decode(multipartParams.getParametres().get("tipus"),  "UTF-8");	    
	    String idCentresSeleccionats = "";
	    String[] idCentres = null;
	    String contingut = multipartParams.getParametres().get("contingut"); 
	    idCentres = multipartParams.getParametres().get("idCentresSeleccionats").split("#");	
    	if (idCentres != null) {
    		idCentresSeleccionats = "";    	  		
	        for(int i=1; i<idCentres.length; i++) { 		
	        	idCentresSeleccionats += idCentres[i] + "#"; 		        			        	
	        }	    	
    	}	  
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    Date peticio = new Date();
		try {
			referencia = RegistreCore.getNewCode(conn, "E");
			peticio = formatter.parse(multipartParams.getParametres().get("peticio"));
		} catch (ParseException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Registre registre = new Registre(referencia, peticio, tipus, remitent, contingut, "-1", "-1", idCentresSeleccionats, idUsuari, new Date());
	    String errorString = null;
   		try {
   			RegistreCore.nouRegistre(conn, "E", registre);		   			
   			registre = RegistreCore.findRegistre(conn, "E", referencia);	 
   			
   			List<Fitxer> fitxers = multipartParams.getFitxersByName().get("file");
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
			tmpFile = new File(ruta + "/documents/Registre Entrada/" + registre.getId());
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
   			if (fitxers != null ) {
   				for(int i=0;i<fitxers.size();i++){		        	
		            Fitxer fitxer = (Fitxer) fitxers.get(i);	            
		            if (fitxer.getFitxer() != null && fitxer.getFitxer().getName() != "") {			            	
		            	File archivo_server = new File(tmpFile + "/" + fitxer.getFitxer().getName());
		            	try {
		               		fitxer.getFitxer().write(archivo_server);
		               		Fitxers.guardarRegistreFitxer(conn, fitxer.getFitxer().getName(), ruta + "/documents/Registre Entrada/" + registre.getId() + "/" + fitxer.getFitxer().getName(), usuari.getIdUsuari());
		           		} catch (Exception e) {
		           			e.printStackTrace();
		           		}
		               	
		            } 
		        }
   			}
   			
   			registre = RegistreCore.findRegistre(conn, tipus, registre.getId());
   			RegistreCore.crearResguard(registre, "E");   			
   			
   			if (!tipus.equals("Sol·licitud Personal") && !tipus.equals("Resposta Sol·licitud Personal") && !tipus.equals("Tramesa documentació Personal")){
   				int idNovaTasca = TascaCore.idNovaTasca(conn);
   				String assumpte = "<a href='registre?from=notificacio&idTasca=" + idNovaTasca + "&tipus=E&referencia=" + registre.getId() + "'>Nova entrada registre: " + registre.getId() + "</a>";
   				TascaCore.novaTasca(conn, "notificacio", 1, UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari(), "-1", "-1", assumpte, registre.getContingut(), "-1", null, request.getRemoteAddr(), "automatic", registre.getId(), registre.getRemDes());
   			}	   			
   		} catch (SQLException | NamingException e) {
  			e.printStackTrace();
  			errorString = e.getMessage();
   		}	   	   		   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("registre", registre);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/registre/createEntradaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   			response.sendRedirect(request.getContextPath() + "/registre?tipus=E&referencia=" + referencia);
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
