package servlet.registre;

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

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import utils.MyUtils;

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
		
	    String referencia = "";
	    String remitent = request.getParameter("remitent");
	    String tipus = URLDecoder.decode(request.getParameter("tipus"),  "UTF-8");	    
	    String idCentresSeleccionats = "";
	    String[] idCentres = null;
	    String contingut = request.getParameter("contingut");
	    String idIncidencies = "";
	    String idInformes = "";
	    String refPro = "";
	    String numAutos = "";
	    List<String> idIncidenciesList = new ArrayList<String>();
	    List<String> idInformesList = new ArrayList<String>();
	    if (request.getParameter("idIncidenciaSeleccionada") != null && request.getParameter("idIncidenciaSeleccionada") != "") {
	    	try {
	    		idIncidencies = request.getParameter("idIncidenciaSeleccionada");
	    		idInformes = request.getParameter("idInformeSeleccionat");
	    		idIncidenciesList.add(request.getParameter("idIncidenciaSeleccionada"));
	    		idInformesList.add(request.getParameter("idInformeSeleccionat"));
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
	    		idInformes = "";
 		        for(int i=0; i<idCentres.length; i++) { 		
 		        	idCentresSeleccionats += idCentres[i] + "#";
 		        	if (!tipus.equals("Procediment judicial")) {
	 		        	try {
	 		        		if (!"-1".equals(idCentres[i])) {
		 		        		if (!"-1".equals(request.getParameter("incidenciesList" + idCentres[i])) && !"-2".equals(request.getParameter("incidenciesList" + idCentres[i]))) { 		        			
									Actuacio actuacio = ActuacioCore.findActuacio(conn, request.getParameter("incidenciesList" + idCentres[i]));							
									idIncidenciesList.add(actuacio.getIdIncidencia());
				 		        	idIncidencies =  actuacio.getIdIncidencia() + "#";
				 		        	idInformes = request.getParameter("expedientsList" + idCentres[i]) + "#";
		 		        		} else {
		 		        			idIncidenciesList.add(request.getParameter("incidenciesList" + idCentres[i]));
				 		        	idIncidencies =  request.getParameter("incidenciesList" + idCentres[i]) + "#";
				 		        	idInformes = request.getParameter("expedientsList" + idCentres[i]) + "#";
		 		        		}
	 		        		}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
 		        	} else if(tipus.equals("Procediment judicial")) {
 			    		if (request.getParameter("idProcediment").equals("-2")) {	
 			    			numAutos = request.getParameter("numautos");
 			    			Judicial judicial = new Judicial();
 			    			judicial.setNumAutos(request.getParameter("numautos"));
 			    			judicial.setJutjat(request.getParameter("jutjat"));
 			    			judicial.setEstat("obert");
 			    			try {
 			    				refPro = JudicialCore.nouProcediement(conn, judicial, "", null);
 							} catch (SQLException e) {
 								// TODO Auto-generated catch block
 								e.printStackTrace();
 							}
 			    		} else {
 			    			refPro = request.getParameter("idProcediment");
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
	    String referenciaIncidencia = "";
	    String referenciesIncidencies = "";
	    Date peticio = new Date();
		try {
			referencia = RegistreCore.getNewCode(conn, "E");
			peticio = formatter.parse(request.getParameter("peticio"));
		} catch (ParseException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Registre registre = new Registre(referencia, peticio, tipus, remitent, contingut, idIncidencies, idInformes, idCentresSeleccionats, idUsuari, new Date());
	    
	    String errorString = null;
	 	      
	   	if (errorString == null) {
	   		try {
	   			RegistreCore.nouRegistre(conn, "E", registre);	
	   			//Crear Incidencia
	   			for(int i=0; i<idCentres.length; i++) { 
	   				if (!idIncidenciesList.isEmpty()) {
		   				if ("-1".equals(idIncidenciesList.get(i))) {
		   					referenciaIncidencia = IncidenciaCore.getNewCode(conn);
				   		    referenciesIncidencies += referenciaIncidencia + "#";
				   		    Incidencia incidencia = new Incidencia();
				   		    incidencia.setIdIncidencia(referenciaIncidencia);
				   		    incidencia.setIdCentre(idCentres[i]);
				   		    incidencia.setUsuCre(UsuariCore.findUsuariByID(conn, idUsuari));
				   		    incidencia.setDescripcio(contingut);
				   		    incidencia.setSolicitant(remitent);
				   		    IncidenciaCore.novaIncidencia(conn, incidencia);			   			
			   			} 
		   				else { //Cream notificació a gerència
			   				String idIncidencia = idIncidenciesList.get(i);
			   				if (idIncidencia == null) idIncidencia = "-1";
			   				/*int idNovaTasca = TascaCore.idNovaTasca(conn);
			   				String assumpte = "<a href='registre?from=notificacio&idTasca=" + idNovaTasca + "&tipus=E&referencia=" + registre.getId() + "'>Nova entrada registre: " + registre.getId() + "</a>";
			   				TascaCore.novaTasca(conn, "notificacio", 1, idUsuari, "", idIncidencia, "", assumpte, "", null);*/
			   				referenciesIncidencies += idIncidencia + "#";
			   			}	
	   				} 
	   				else {
	   					String idIncidencia = "-1";
		   				/*int idNovaTasca = TascaCore.idNovaTasca(conn);
		   				String assumpte = "<a href='registre?from=notificacio&idTasca=" + idNovaTasca + "&tipus=E&referencia=" + registre.getId() + "'>Nova entrada registre: " + registre.getId() + "</a>";
		   				TascaCore.novaTasca(conn, "notificacio", 1, idUsuari, "", idIncidencia, "", assumpte, "", null);*/
		   				referenciesIncidencies += idIncidencia + "#";
	   				}
	   			}	 
	   			
	   			//Si procediment cream tasca
	   			if(tipus.equals("Procediment judicial")) {	   				
	   				int usuariTasca = UsuariCore.finCap(conn, "juridica").getIdUsuari();
	   				TascaCore.novaTasca(conn, "judicial", usuariTasca, idUsuari, "-1", "-1", "S'ha registrat un document nou al procediment " + numAutos, "Nova documentació procediment", refPro, null);
	   				referenciesIncidencies = refPro;
	   			}
	   			//Actualitzam referències registres
	   			RegistreCore.actualitzarIdIncidencia(conn, "E", referencia, referenciesIncidencies);
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
