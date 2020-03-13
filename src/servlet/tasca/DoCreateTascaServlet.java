package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.math.NumberUtils;

import bean.Actuacio;
import bean.Incidencia;
import bean.InformeActuacio;
import bean.User;
import core.ActuacioCore;
import core.CentreCore;
import core.IncidenciaCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateTascaServlet
 */
@WebServlet("/DoCreateTasca")
public class DoCreateTascaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateTascaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = MyUtils.getStoredConnection(request);
		String idIncidencia = "";		
		String idActuacio = "";
		String idInformePrevi = "";
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    int idTasca = -1;
	    String tipus = multipartParams.getParametres().get("tipus");
	    String assumpte = multipartParams.getParametres().get("assumpte");
	   	String comentari =multipartParams.getParametres().get("comentari");
	   	String usuari = multipartParams.getParametres().get("idUsuari");
	   	String idCentre = multipartParams.getParametres().get("idCentre");
	   	String idProcediment = multipartParams.getParametres().get("idProcediment");
	   	String idFactura = multipartParams.getParametres().get("idFactura");
	   	idInformePrevi = multipartParams.getParametres().get("idInforme");
	   	String errorString = null;	   		   	
	   	try {
	   		if (usuari == null) {
	   			usuari = String.valueOf(UsuariCore.finCap(conn, MyUtils.getLoginedUser(request.getSession()).getDepartament()).getIdUsuari());
	   		}
	   		if (usuari.equals("-1") ) {
	   			usuari = String.valueOf(idUsuari);		
   			}
	   		int idUsuariTasca = -1;
   			if (NumberUtils.isNumber(usuari)) {
   				idUsuariTasca = Integer.parseInt(usuari);
   			}else{
   				idUsuariTasca = UsuariCore.finCap(conn, usuari).getIdUsuari();
   			}	
   			if (tipus.equals("manual")) {
   				//Crear incidencia
   				Incidencia incidencia = new Incidencia();
   				idIncidencia = IncidenciaCore.getNewCode(conn);
	   		    incidencia.setIdIncidencia(idIncidencia);
	   		    incidencia.setIdCentre(idCentre);
	   		    incidencia.setUsuCre(UsuariCore.findUsuariByID(conn, idUsuari));
	   		    incidencia.setDescripcio(assumpte);
	   		    IncidenciaCore.novaIncidencia(conn, incidencia);   				
   				
   				//Crear actuació
	   		    Actuacio actuacio = new Actuacio();
	   		    idActuacio = ActuacioCore.getNewCode(conn);
		 	    actuacio.setReferencia(idActuacio);
		 	    actuacio.setDescripcio(assumpte);
		 	    actuacio.setCentre(CentreCore.findCentre(conn, idCentre, false));
		 	    actuacio.setIdIncidencia(incidencia.getIdIncidencia());
		 	    actuacio.setIdUsuariCreacio(idUsuari);
		 	    ActuacioCore.novaActuacio(conn, actuacio);   				
   				
		 	   tipus = "generic";
   			} else if (tipus.equals("centre")) {
   				//Crear incidencia
   				idCentre = multipartParams.getParametres().get("centre");
   				Incidencia incidencia = new Incidencia();
   				idIncidencia = IncidenciaCore.getNewCode(conn);
	   		    incidencia.setIdIncidencia(idIncidencia);
	   		    incidencia.setIdCentre(idCentre);
	   		    incidencia.setUsuCre(UsuariCore.findUsuariByID(conn, idUsuari));
	   		    incidencia.setDescripcio(assumpte);
	   		    IncidenciaCore.novaIncidencia(conn, incidencia);   				
   				
   				//Crear actuació
	   		    Actuacio actuacio = new Actuacio();
	   		    idActuacio = ActuacioCore.getNewCode(conn);
		 	    actuacio.setReferencia(idActuacio);
		 	    actuacio.setDescripcio(assumpte);
		 	    actuacio.setCentre(CentreCore.findCentre(conn, idCentre, false));
		 	    actuacio.setIdIncidencia(incidencia.getIdIncidencia());
		 	    actuacio.setIdUsuariCreacio(idUsuari);
		 	    ActuacioCore.novaActuacio(conn, actuacio);   				
   				
		 	    InformeActuacio informe = new InformeActuacio();
		 	    informe.setIdTasca(idTasca);
				informe.setIdIncidencia(idIncidencia);
				informe.setActuacio(actuacio);
				idInformePrevi = InformeCore.nouInforme(conn, informe, idUsuari);
		 	    
		 	    User user = UsuariCore.findUsuariByID(conn, idUsuariTasca);
		 	    if (user.getRol().contains("GER")) {
		 	    	 tipus = "infPrev";
		 	    } else if (user.getRol().contains("CAP")) {
		 	    	 tipus = "vistInfPrev";
		 	    } else {
		 	    	 tipus = "solInfPrev";
		 	    }   				
   			} else if (idProcediment != null && !idProcediment.equals("")) {
   				idIncidencia = "-1";
   				idActuacio = "-1";
   				idInformePrevi = idProcediment;
   			} else if (idFactura != null && !idFactura.equals("")) {
   				idIncidencia = "-1";
   				idActuacio = "-1";
   				idInformePrevi = idFactura;
   			} else {
   				idIncidencia = multipartParams.getParametres().get("idIncidencia"); 
   		   		if (multipartParams.getParametres().get("idActuacio") != "") {
   					idActuacio = multipartParams.getParametres().get("idActuacio"); 
   					String modificacio = "Crear nova tasca";
   					if ("infPrev".equals(tipus)) {
   						tipus = "solInfPrev";
   						modificacio = "Sol·licitar informe previ";
   					} else if ("notificacio".equals(tipus)) {
   						modificacio = "Enviar nova notificació";
   					} else if ("modificacio".equals(tipus)) {
   						User user = UsuariCore.findUsuariByID(conn, idUsuari);
   						if (user.getRol().contains("CAP")) {
   							idUsuariTasca =  UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
   						} 
   						tipus = "solModificacio";
   						modificacio = "Incidència execució";
   					}
   					ActuacioCore.actualitzarActuacio(conn, idActuacio, modificacio);
   					idIncidencia = ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia();
   				}	
   			}   			
   			idTasca = TascaCore.novaTasca(conn, tipus, idUsuariTasca, idUsuari, idActuacio, idIncidencia, comentari, assumpte, idInformePrevi, multipartParams.getFitxers(), request.getRemoteAddr(), "manual");
   		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.getMessage();
		}
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/tasca/createTascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.       
	   	
	   	response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
