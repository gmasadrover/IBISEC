package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.InformeActuacio;
import bean.Oferta;
import bean.User;
import core.ActuacioCore;
import core.CreditCore;
import core.InformeCore;
import core.OfertaCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class DoCanvisActuacio
 */
@WebServlet("/DoCanvisActuacio")
public class DoCanvisActuacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCanvisActuacioServlet() {
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
		
		//Registrar actuació
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme");
	    String aprovarPA = multipartParams.getParametres().get("aprovarPA");
	    String aprovarPD = multipartParams.getParametres().get("aprovarPD");
	    String tancar = multipartParams.getParametres().get("tancar");	   
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    List<Fitxer> fitxers = multipartParams.getFitxers(); 	
   		try {
		    if (aprovarPA != null) { 
		    	//aprovam actuació
				ActuacioCore.aprovarPA(conn, idActuacio, Usuari.getIdUsuari());				
				//aprovam informe
				InformeCore.aprovacioInforme(conn, idInforme, Usuari.getIdUsuari());  
		    }else if (aprovarPD != null) {
		    	System.out.println("entra a pd");
		    	try {
					ActuacioCore.aprovar(conn, idActuacio, Usuari.getIdUsuari());
					ActuacioCore.actualitzarActuacio(conn, idActuacio, "Autorització generada");
					OfertaCore.aprovarOferta(conn, idInforme, Usuari.getIdUsuari());
		   			CreditCore.assignar(conn, idInforme, OfertaCore.findOfertaSeleccionada(conn, idInforme).getPlic());
		   			
		   			Fitxers.guardarFitxer(fitxers, idIncidencia, idActuacio, "", "", "", idInforme, "Autorització  Proposta despesa");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	   		}else if (tancar != null) { // tancam actuació
	   			try {
	   				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Tancar actuació");
	   				ActuacioCore.tancar(conn, idActuacio, Usuari.getIdUsuari());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   			
	   		}else {
	   			
	   		} 
	    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    if (aprovarPA != null) { 
	    	response.sendRedirect(request.getContextPath() + "/CrearDocument?tipus=AutoritzacioPAObres&idIncidencia=" + idIncidencia + "&idActuacio=" + idActuacio + "&idInforme=" + idInforme); 
	    }else{
	    	response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
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
