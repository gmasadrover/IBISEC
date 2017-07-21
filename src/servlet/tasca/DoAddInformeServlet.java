package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

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
	    int infPrev = Integer.parseInt(multipartParams.getParametres().get("infPrev"));	    
	    String idInformePrevi = "";
	    String idProposta = "";
		String objecte = "";
		String tipusObra = "";
		boolean llicencia = false;
		String tipusLlicencia = "";
		boolean contracte = false;
		double vec = 0;
		double iva = 0;
		double plic = 0;
		String termini = "";
		String comentari = "";
		List<PropostaInforme> llistaPropostes = new ArrayList<PropostaInforme>();
		//Guardar adjunts
		if (guardar != null) Fitxers.guardarFitxer(multipartParams.getFitxers(), idIncidencia, idActuacio, "Informe Previ", String.valueOf(idTasca), "", "", "");
		 
	    InformeActuacio informe = new InformeActuacio();
	    PropostaInforme proposta = informe.new PropostaInforme();
	    informe.setIdTasca(idTasca);
	    informe.setIdIncidencia(idIncidencia);
	    informe.setIdActuacio(idActuacio);
	    idInformePrevi = multipartParams.getParametres().get("idInformePrevi");
	    if (guardar != null) {	    	
	    	for(int i=1; i<=infPrev; i++) {	
	    		idProposta = multipartParams.getParametres().get("idProposta" + i);
				objecte = multipartParams.getParametres().get("objecteActuacio" + i);
				tipusObra = multipartParams.getParametres().get("tipusContracte" + i);		
				llicencia = false;
				tipusLlicencia = "";
				contracte = false;		 
			    if (new String("obr").equals(tipusObra)) {
			    	llicencia = new String("si").equals(multipartParams.getParametres().get("reqLlicencia" + i));	 	   
					if (llicencia) tipusLlicencia = multipartParams.getParametres().get("tipusLlicencia" + i);					
					contracte = new String("si").equals(multipartParams.getParametres().get("formContracte" + i));
			    }			    
			    vec = Double.parseDouble(multipartParams.getParametres().get("vec" + i).replace(',','.'));
			    iva = Double.parseDouble(multipartParams.getParametres().get("iva" + i));
			    plic = Double.parseDouble(multipartParams.getParametres().get("plic" + i).replace(',','.'));	
			    termini = multipartParams.getParametres().get("termini" + i);
			    comentari = multipartParams.getParametres().get("comentariTecnic" + i);			   
			    	
			    proposta = informe.new PropostaInforme();
			    proposta.setIdProposta(idProposta);
			    proposta.setObjecte(objecte);
			    proposta.setTipusObra(tipusObra);
			    proposta.setLlicencia(llicencia);
			    proposta.setTipusLlicencia(tipusLlicencia);
			    proposta.setContracte(contracte);
			    proposta.setVec(vec);
			    proposta.setIva(iva);
			    proposta.setPlic(plic);
			    proposta.setTermini(termini);
			    proposta.setComentari(comentari);
			    llistaPropostes.add(proposta);
	    	}
	    	
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
				InformeCore.validacioCapInforme(conn, idInformePrevi, Usuari.getIdUsuari(), comentariCap);				
				informe = InformeCore.getInformePrevi(conn, idInformePrevi);				
				if (informe.getLlistaPropostes().size() == 1) InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInformePrevi);
				if (idActuacio != "") idIncidencia = String.valueOf(ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia());
			} catch (SQLException e) {
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
