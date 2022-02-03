package servlet.registre;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Registre;
import bean.User;
import core.ConfiguracioCore;
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
		multipartParams = Fitxers.getParamsFromMultipartForm(request);		
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
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Registre registre = new Registre(referencia, peticio, tipus, remitent, contingut, "-1", "-1", idCentresSeleccionats, idUsuari, new Date());
	    
   		RegistreCore.nouRegistre(conn, "E", registre);		   			
		registre = RegistreCore.findRegistre(conn, "E", referencia);	 
		
		List<Fitxer> fitxers = multipartParams.getFitxersByName().get("file");
		String ruta = "";
		ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
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
		RegistreCore.crearResguard(conn, registre, "E");   			
		
		if (!tipus.equals("Sol·licitud Personal") && !tipus.equals("Resposta Sol·licitud Personal") && !tipus.equals("Tramesa documentació Personal")){
			int idNovaTasca = TascaCore.idNovaTasca(conn);
			String assumpte = "<a href='registre?from=notificacio&idTasca=" + idNovaTasca + "&tipus=E&referencia=" + registre.getId() + "'>Nova entrada registre: " + registre.getId() + "</a>";
			TascaCore.novaTasca(conn, "notificacio", 1, UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari(), "-1", "-1", assumpte, registre.getContingut(), "-1", null, request.getRemoteAddr(), "automatic", registre.getId(), registre.getRemDes());
		}	   	   		   	
	   	// Store infomation to request attribute, before forward to views.
	   
	   	request.setAttribute("registre", registre);
	  	// If error, forward to Edit page.
	   
	   			response.sendRedirect(request.getContextPath() + "/registre?tipus=E&referencia=" + referencia);
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
