package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.InformeActuacio;
import bean.User;
import core.ActuacioCore;
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
		multipartParams = Fitxers.getParamsFromMultipartForm(request);		
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
			Fitxers.guardarFitxer(conn, multipartParams.getFitxers(), idIncidencia, idActuacio, "", String.valueOf(idTasca), "", idInformePrevi, "Informe previ", Usuari.getIdUsuari());
			TascaCore.nouHistoric(conn, idTasca, msg, Usuari.getIdUsuari(), ipRemote, "automatic");	
			if (idActuacio != "") idIncidencia = String.valueOf(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia());		   
	    } else if (tramitar != null) {
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
			TascaCore.nouHistoric(conn, idTasca, comentariGerencia, Usuari.getIdUsuari(), ipRemote, "manual");	
			int usuariTasca = UsuariCore.finCap(conn, informe.getUsuari().getDepartament()).getIdUsuari();
			TascaCore.reasignar(conn, usuariTasca, idTasca, "doctecnica", "Redacció documentació tècnica");
			TascaCore.assignarPrioritat(conn, idTasca, prioritat);		   			
	    }			
			   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	
   		if (guardar != null) {
		   	response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca); 
   		} else {
   			response.sendRedirect(request.getContextPath() + "/tascaList"); 
   		}  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
