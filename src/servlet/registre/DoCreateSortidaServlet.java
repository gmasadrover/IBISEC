package servlet.registre;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.Incidencia;
import bean.Registre;
import core.ActuacioCore;
import core.IncidenciaCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

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
		
	    String referencia = "";
	    String remitent = request.getParameter("destinatari");
	    String tipus = URLDecoder.decode(request.getParameter("tipus"),  "UTF-8");	    
	    String idCentresSeleccionats = "";
	    String[] idCentres = null;
	    String contingut = request.getParameter("contingut");
	    String idIncidencies = "";
	    List<String> idIncidenciesList = new ArrayList<String>();
	    if (request.getParameter("idIncidenciaSeleccionada") != null && request.getParameter("idIncidenciaSeleccionada") != "") {	    	
	    	try {
	    		idIncidencies =  request.getParameter("idIncidenciaSeleccionada");
		    	idIncidenciesList.add( request.getParameter("idIncidenciaSeleccionada"));
	    		idCentres = new String[] {IncidenciaCore.findIncidencia(conn,idIncidencies).getIdCentre()};
	    		idCentresSeleccionats = IncidenciaCore.findIncidencia(conn,idIncidencies).getIdCentre();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } else {
	    	idCentres = request.getParameterValues("idCentre");
	    	if (idCentres != null) {
	    		idCentresSeleccionats = "";
	    		idIncidencies = "";
 		        for(int i=0; i<idCentres.length; i++) { 		
 		        	idCentresSeleccionats += idCentres[i] + "#";
 		        	try {
 		        		if (!"-1".equals(idCentres[i])) {
	 		        		if (!"-1".equals(request.getParameter("incidenciesList" + idCentres[i]))) {
	 		        			Actuacio actuacio = ActuacioCore.findActuacio(conn, request.getParameter("incidenciesList" + idCentres[i]));
	 		        			idIncidenciesList.add(actuacio.getIdIncidencia());
			 		        	idIncidencies =  actuacio.getIdIncidencia() + "#";
							} else {
								idIncidenciesList.add(request.getParameter("incidenciesList" + idCentres[i]));
			 		        	idIncidencies = request.getParameter("incidenciesList" + idCentres[i]) + "#";
							}
 		        		}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
 		        }
	    	}
	    }
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    String referenciaIncidencia = "";
	    String referenciesIncidencies = "";
	    Date peticio = new Date();
		try {
			referencia = RegistreCore.getNewCode(conn, "S");
			peticio = formatter.parse(request.getParameter("peticio"));
		} catch (ParseException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Registre registre = new Registre(referencia, peticio, tipus, remitent, contingut, idIncidencies, idCentresSeleccionats, idUsuari, new Date());
	    
	    String errorString = null;
	 	      
	   	if (errorString == null) {
	   		try {
	   			RegistreCore.nouRegistre(conn, "S", registre);	
	   			//Crear Incidencia
	   			for(int i=0; i<idCentres.length; i++) { 
	   				if (!idIncidenciesList.isEmpty()) {
		   				if ("-1".equals(idIncidenciesList.get(i))) {
				   		    referenciesIncidencies += "-1#";			   		       			
			   			} else { //Cream notificació a gerència
			   				String idIncidencia = idIncidenciesList.get(i);
			   				if (idIncidencia == null) idIncidencia = "-1";
			   				int idNovaTasca = TascaCore.idNovaTasca(conn);
			   				String assumpte = "<a href='registre?from=notificacio&idTasca=" + idNovaTasca + "&tipus=S&referencia=" + registre.getId() + "'>Nova sortida registre: " + registre.getId() + "</a>";
			   				TascaCore.novaTasca(conn, "notificacio", 1, idUsuari, "", idIncidencia, "", assumpte, "", null);
			   				referenciesIncidencies += idIncidencia + "#";
			   			}	
	   				} else {
	   					String idIncidencia = "-1";
		   				int idNovaTasca = TascaCore.idNovaTasca(conn);
		   				String assumpte = "<a href='registre?from=notificacio&idTasca=" + idNovaTasca + "&tipus=S&referencia=" + registre.getId() + "'>Nova sortida registre: " + registre.getId() + "</a>";
		   				TascaCore.novaTasca(conn, "notificacio", 1, idUsuari, "", idIncidencia, "", assumpte, "", null);
		   				referenciesIncidencies += idIncidencia + "#";
	   				}
	   			}	 
	   			//Actualitzam referències registres
	   			RegistreCore.actualitzarIdIncidencia(conn, "S", referencia, referenciesIncidencies);
	   		} catch (SQLException e) {
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
