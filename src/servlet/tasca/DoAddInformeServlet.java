package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

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
		
	    int idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));	    
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInformePrevi = multipartParams.getParametres().get("idInformePrevi");
	    String guardar = multipartParams.getParametres().get("guardar");	    
	    String errorString = null;
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	  
	    if (guardar != null) {
	    	String objecte = multipartParams.getParametres().get("objecteActuacio");
		    String tipusObra = multipartParams.getParametres().get("tipusContracte");
		    boolean llicencia = false;
		    String tipusLlicencia = "";
		    boolean contracte = false;
		    if (new String("obr").equals(tipusObra)) {
		    	llicencia = new String("si").equals(multipartParams.getParametres().get("reqLlicencia"));	 	   
				if (llicencia) {
					tipusLlicencia = multipartParams.getParametres().get("tipusLlicencia");
				}
				contracte = new String("si").equals(multipartParams.getParametres().get("formContracte"));
		    }	    
		    double vec = Double.parseDouble(multipartParams.getParametres().get("vec").replace(',','.'));
		    double iva = Double.parseDouble(multipartParams.getParametres().get("iva"));
		    double plic = Double.parseDouble(multipartParams.getParametres().get("plic"));
		    String termini = multipartParams.getParametres().get("termini");
		    String servei = multipartParams.getParametres().get("tipusServei");
		    String comentari = multipartParams.getParametres().get("comentariTecnic");
		    
		    InformeActuacio informe = new InformeActuacio();
		    informe.setIdTasca(idTasca);
		    informe.setIdIncidencia(idIncidencia);
		    informe.setIdActuacio(idActuacio);
		    informe.setObjecte(objecte);
		    informe.setTipusObra(tipusObra);
		    informe.setLlicencia(llicencia);
		    informe.setTipusLlicencia(tipusLlicencia);
		    informe.setContracte(contracte);
		    informe.setVec(vec);
		    informe.setIva(iva);
		    informe.setPlic(plic);
		    informe.setTermini(termini);
		    informe.setServei(servei);
		    informe.setComentari(comentari);		   
		   	//Registrar informe + comentari;	   
		   	try {
		   		String msg = "S'ha afegit l'informe";
		   		if (! idInformePrevi.isEmpty()) {
		   			informe.setIdInf(idInformePrevi);
		   			InformeCore.modificar(conn, informe, Usuari.getIdUsuari());
		   			msg = "S'ha modificat l'informe";	   				
		   		}else{
		   			InformeCore.nouInforme(conn, informe, Usuari.getIdUsuari());
		   		}
		   		TascaCore.nouHistoric(conn, idTasca, msg, Usuari.getIdUsuari());	
		   		if (idActuacio != "") idIncidencia = String.valueOf(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errorString = e.getMessage();
			}
		   	//Guardar adjunts
		   	Fitxers.guardarFitxer(multipartParams.getFitxers(), idIncidencia, idActuacio, "Informe Previ", String.valueOf(idTasca), "");
	    } else {
	    	//Aprovació d'informe
	    	//Registrar nova tasca d'asignament de partida
			try {
				String comentariCap = multipartParams.getParametres().get("comentariCap");	
				InformeCore.validacioCapInforme(conn, idInformePrevi, Usuari.getIdUsuari(), comentariCap);
				TascaCore.nouHistoric(conn, idTasca, "Informe aprovat", Usuari.getIdUsuari());
				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Proposta d'actuació realitzada");
				TascaCore.reasignar(conn, 900, idTasca);
				TascaCore.tancar(conn, idTasca);
				int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
				TascaCore.novaTasca(conn, "resPartida", idUsuari, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", String.valueOf(idTasca), idInformePrevi);
				if (idActuacio != "") idIncidencia = String.valueOf(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
	   		response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);   		
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
