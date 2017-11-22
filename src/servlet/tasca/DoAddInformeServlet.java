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
import core.InformeCore;
import core.TascaCore;
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
	    String errorString = null;
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	    
	    String idInformePrevi = "";
	    String idProposta = "";
		String objecte = "";
		String tipusObra = "";
		boolean llicencia = false;
		String tipusLlicencia = "";
		boolean contracte = false;
		double pbase = 0;
		double iva = 0;
		double plic = 0;
		String termini = "";
		String comentari = "";
		List<PropostaInforme> llistaPropostes = new ArrayList<PropostaInforme>();
		//Guardar adjunts
		if (guardar != null)
			try {
				Fitxers.guardarFitxer(multipartParams.getFitxers(), idIncidencia, idActuacio, "Informe Previ", String.valueOf(idTasca), "", "", "");
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 
	    InformeActuacio informe = new InformeActuacio();
	    Actuacio actuacio = new Actuacio();
	    actuacio.setReferencia(idActuacio);
	    PropostaInforme proposta = informe.new PropostaInforme();
	    informe.setIdTasca(idTasca);
	    informe.setIdIncidencia(idIncidencia);
	    informe.setActuacio(actuacio);
	    idInformePrevi = multipartParams.getParametres().get("idInformePrevi");
	    if (guardar != null) {	  
	    		idProposta = multipartParams.getParametres().get("idProposta");
				objecte = multipartParams.getParametres().get("objecteActuacio");
				tipusObra = multipartParams.getParametres().get("tipusContracte");						
				llicencia = false;
				tipusLlicencia = "";
				contracte = false;		 
			    if (new String("obr").equals(tipusObra)) {
			    	llicencia = new String("si").equals(multipartParams.getParametres().get("reqLlicencia"));	 	   
					if (llicencia) tipusLlicencia = multipartParams.getParametres().get("tipusLlicencia");					
					contracte = new String("si").equals(multipartParams.getParametres().get("formContracte"));
			    }			    
			    pbase = Double.parseDouble(multipartParams.getParametres().get("pbase").replace(',','.'));
			    iva = Double.parseDouble(multipartParams.getParametres().get("iva"));
			    plic = Double.parseDouble(multipartParams.getParametres().get("plic").replace(',','.'));	
			    termini = multipartParams.getParametres().get("termini");
			    comentari = multipartParams.getParametres().get("comentariTecnic");			   
			    	
			    proposta = informe.new PropostaInforme();
			    proposta.setIdProposta(idProposta);
			    proposta.setObjecte(objecte);
			    proposta.setTipusObra(tipusObra);
			    proposta.setLlicencia(llicencia);
			    proposta.setTipusLlicencia(tipusLlicencia);
			    proposta.setContracte(contracte);
			    proposta.setPbase(pbase);
			    proposta.setIva(iva);
			    proposta.setPlic(plic);
			    proposta.setTermini(termini);
			    proposta.setComentari(comentari);
			    llistaPropostes.add(proposta);
	    	
	    	informe.setLlistaPropostes(llistaPropostes);
	    	//Registrar informe + comentari;	   
		   	try {
		   		String msg = "S'ha afegit l'informe";
		   		if (! idInformePrevi.isEmpty()) {
		   			informe.setIdInf(idInformePrevi);
		   			InformeCore.modificarInforme(conn, informe, Usuari.getIdUsuari());
		   			msg = "S'ha modificat l'informe";	   				
		   		}else{
		   			idInformePrevi = InformeCore.nouInforme(conn, informe, Usuari.getIdUsuari());
		   		}
		   		TascaCore.nouHistoric(conn, String.valueOf(idTasca), msg, Usuari.getIdUsuari());	
		   		if (idActuacio != "") idIncidencia = String.valueOf(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia());
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}		   
	    } else {
	    	//Aprovació d'informe
			try {
				String comentariCap = multipartParams.getParametres().get("comentariCap");	
				InformeCore.validacioCapInforme(conn, idInformePrevi, Usuari.getIdUsuari(), comentariCap, new Date());				
				informe = InformeCore.getInformePrevi(conn, idInformePrevi, false);				
				if (informe.getLlistaPropostes().size() == 1) InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInformePrevi);
				if (idActuacio != "") idIncidencia = String.valueOf(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia());
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
			   	response.sendRedirect(request.getContextPath() + "/CrearDocument?tipus=PAObres&idIncidencia=" + idIncidencia + "&idActuacio=" + idActuacio + "&idInforme=" + idInformePrevi); 
	   		} else {
	   			response.sendRedirect(request.getContextPath() + "/CrearDocument?tipus=VistiplauPAObres&idIncidencia=" + idIncidencia + "&idActuacio=" + idActuacio + "&idInforme=" + idInformePrevi); 
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
