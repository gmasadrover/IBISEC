package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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

import org.apache.commons.fileupload.FileUploadException;

import bean.Actuacio;
import bean.InformeActuacio;
import bean.InformeActuacio.PropostaInforme;
import bean.User;
import core.ActuacioCore;
import core.ExpedientCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddInformeServlet
 */
@WebServlet("/DoAddInforme")
public class DoAddInformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddInformeServlet() {
        super();
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
			e1.printStackTrace();
		}		
		int idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));	    
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String guardar = multipartParams.getParametres().get("guardar");	  
	    String tramitar = multipartParams.getParametres().get("tramitar");	  
	    String errorString = null;
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	    
	    String idInformePrevi = multipartParams.getParametres().get("idInformePrevi");
	    InformeActuacio informe = new InformeActuacio();
		//Guardar adjunts
	    String ipRemote = request.getRemoteAddr();
		if (guardar != null) {
			try {
				System.out.println(idInformePrevi);
				String msg = "S'ha afegit l'informe";
				if (idInformePrevi == null || idInformePrevi.isEmpty() || idInformePrevi.equals("0")) {
					Actuacio actuacio = new Actuacio();
					actuacio.setReferencia(idActuacio);					
					informe.setIdTasca(idTasca);
					informe.setIdIncidencia(idIncidencia);
					informe.setActuacio(actuacio);
					idInformePrevi = InformeCore.nouInforme(conn, informe, Usuari.getIdUsuari());
					TascaCore.actualitzarInforme(conn, idTasca, idInformePrevi);
				} else {
					informe.setIdInf(idInformePrevi);
		   			InformeCore.modificarInforme(conn, informe, Usuari.getIdUsuari());
		   			msg = "S'ha modificat l'informe";	   		
				}				
				Fitxers.guardarFitxer(conn, multipartParams.getFitxers(), idIncidencia, idActuacio, "", String.valueOf(idTasca), "", idInformePrevi, "Informe prèvi", Usuari.getIdUsuari());
				TascaCore.nouHistoric(conn, String.valueOf(idTasca), msg, Usuari.getIdUsuari(), ipRemote, "automatic");	
		   		if (idActuacio != "") idIncidencia = String.valueOf(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia());
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errorString = e.toString();
			}		   
	    } else if (tramitar != null) {
	    	//Aprovació d'informe
			try {
				if (idInformePrevi == null || idInformePrevi.isEmpty() || idInformePrevi.equals("0")) {
					int idTecnic = Integer.parseInt(multipartParams.getParametres().get("llistaUsuaris"));	
					Actuacio actuacio = new Actuacio();
					actuacio.setReferencia(idActuacio);					
					informe.setIdTasca(idTasca);
					informe.setIdIncidencia(idIncidencia);
					informe.setActuacio(actuacio);
					idInformePrevi = InformeCore.nouInforme(conn, informe, idTecnic);
					TascaCore.actualitzarInforme(conn, idTasca, idInformePrevi);
				}
				informe = InformeCore.getInformePrevi(conn, idInformePrevi, false);	
				int prioritat = Integer.parseInt(multipartParams.getParametres().get("prioritatList"));	 				
				String comentariGerencia = multipartParams.getParametres().get("comentariGerencia");
				TascaCore.nouHistoric(conn, String.valueOf(idTasca), comentariGerencia, Usuari.getIdUsuari(), ipRemote, "manual");	
				int usuariTasca = UsuariCore.finCap(conn, informe.getUsuari().getDepartament()).getIdUsuari();
				TascaCore.reasignar(conn, usuariTasca, idTasca, "doctecnica");
				TascaCore.assignarPrioritat(conn, idTasca, prioritat);
			} catch (SQLException | NamingException e) {
				e.printStackTrace();
			}		   			
	    }			
			   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/tascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		if (guardar != null) {
			   	response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca); 
	   		} else {
	   			response.sendRedirect(request.getContextPath() + "/tascaList"); 
	   		}  
	   	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
