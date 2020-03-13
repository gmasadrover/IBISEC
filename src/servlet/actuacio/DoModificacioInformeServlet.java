package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.InformeActuacio;
import bean.InformeActuacio.PropostaInforme;
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

/**
 * Servlet implementation class DoModificacioInformeServlet
 */
@WebServlet("/DoModificacioInforme")
public class DoModificacioInformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoModificacioInformeServlet() {
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
		
		String tipusIncidencia = multipartParams.getParametres().get("tipusIncidencia");
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		String idInforme = multipartParams.getParametres().get("idInforme");
	   
	    
		String errorString = null;	    
	    InformeActuacio informe = new InformeActuacio();
	  
	    boolean nousDocumentsSignar = false;
		
	    String objecte = multipartParams.getParametres().get("objecteModificacio");
	    
	    boolean reqLlicencia = false;	 
	    String tipusLlicencia = "";
	    		
	    double plic = 0;
	    boolean retencio = false;	   
		String propostaTecnica = "";
	    
		try {
			informe = InformeCore.getInformePrevi(conn, idInforme, false);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  		PropostaInforme proposta = informe.new PropostaInforme();
  		proposta.setTipusObra(informe.getPropostaInformeSeleccionada().getTipusObra());
	    
  		proposta.setLlicencia(reqLlicencia);
  		if (reqLlicencia) proposta.setTipusLlicencia(tipusLlicencia);
  		proposta.setObjecte(objecte);
  		proposta.setPbase(0);
  		proposta.setIva(0);
  		proposta.setPlic(0);
  		proposta.setRetencio(retencio);
  		proposta.setTermini(informe.getPropostaInformeSeleccionada().getTermini());
  		proposta.setOcults(false);
  		
  		Oferta ofertaProposta = new Oferta();
  		ofertaProposta.setIdActuacio(informe.getActuacio().getReferencia());
  		if (informe.getOfertaSeleccionada() != null) {
  			ofertaProposta.setCifEmpresa(informe.getOfertaSeleccionada().getCifEmpresa());
  		}  		
  		ofertaProposta.setPlic(0);
   		ofertaProposta.setPbase(0);
   		ofertaProposta.setIva(0);			
  		ofertaProposta.setComentari(propostaTecnica);
  		
  		
  		if (tipusIncidencia.equals("penalitzacio")) { // Penalitzacions
			plic = Double.parseDouble(multipartParams.getParametres().get("plicPenalitzacio").replace(',','.'));
	    	if (plic > 0) plic = -1*plic;
	    	proposta.setPlic(plic);
	    	ofertaProposta.setPlic(plic);
	    	if (multipartParams.getParametres().get("retencio") != null && multipartParams.getParametres().get("retencio").equals("on")) {
	    		proposta.setRetencio(true);
	    	}
	    	if (multipartParams.getParametres().get("execucio") != null &&  multipartParams.getParametres().get("execucio").equals("on")) {
	    		proposta.setRetencio(false);
	    	}			
		} else if(tipusIncidencia.equals("termini")) {
			proposta.setTermini(multipartParams.getParametres().get("termini"));			
			proposta.setOcults("on".equals(multipartParams.getParametres().get("partsOcultes"))); 
			ofertaProposta.setCifEmpresa(multipartParams.getParametres().get("llistaEmpreses"));			
		} else { // Modificacions
			 if (multipartParams.getParametres().get("reqLlicencia") != null && multipartParams.getParametres().get("reqLlicencia").equals("si")) {
				 proposta.setLlicencia(true);	   
				 proposta.setTipusLlicencia(multipartParams.getParametres().get("tipusLlicencia"));
			 }
			 proposta.setTermini(multipartParams.getParametres().get("termini"));			
			 proposta.setOcults("on".equals(multipartParams.getParametres().get("partsOcultes"))); 
			 proposta.setPbase(Double.parseDouble(multipartParams.getParametres().get("pbase").replace(',','.')));
			 proposta.setIva(Double.parseDouble(multipartParams.getParametres().get("iva")));
			 proposta.setPlic(Double.parseDouble(multipartParams.getParametres().get("plic").replace(',','.')));
			 
			 ofertaProposta.setCifEmpresa(multipartParams.getParametres().get("llistaEmpreses"));
			 ofertaProposta.setComentari(multipartParams.getParametres().get("propostaTecnica"));
			 ofertaProposta.setPlic(proposta.getPlic());
			 ofertaProposta.setPbase(proposta.getPbase());
			 ofertaProposta.setIva(proposta.getIva());		
		}
				
  		//Cream modificació	  		
  		
		try {
			String idModificacio = InformeCore.afegirModificacioInforme(conn, idInforme, proposta, ofertaProposta, Usuari, tipusIncidencia);
			ofertaProposta.setIdInforme(idModificacio.split("#")[0]);
	  		ofertaProposta.setSeleccionada(true);	  		
			if (tipusIncidencia.equals("modificacio") || tipusIncidencia.equals("certfinal") || tipusIncidencia.equals("preusContradictoris") || (tipusIncidencia.equals("penalitzacio") && !proposta.isRetencio())) {	 
		  		OfertaCore.novaOferta(conn, ofertaProposta, Usuari.getIdUsuari());
	  		}
	  		
			//documents
			
			if (multipartParams.getFitxersByName().get("informeDF") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("informeDF"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Informe DF", Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("informe") != null) {
	  			InformeCore.saveInformeModificacio(conn, informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme, idModificacio.split("#")[0], multipartParams.getFitxersByName().get("informe"), Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("informeJuridic") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("informeJuridic"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Informe Juridic", Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("resinici") != null) {
	  			nousDocumentsSignar = true;
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("resinici"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Resolució Inici", Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("tramits") != null) {
	  			nousDocumentsSignar = true;
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("tramits"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Tramits", Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("resfinal") != null) {
	  			nousDocumentsSignar = true;
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("resfinal"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio, "Resolució Final", Usuari.getIdUsuari());
	  		}
	  		
	  		if (proposta.isOcults()) { //Si hi ha parts ocultes crear notificació a Intervenció
	  			String comentari = "S'ha realitzat la proposta de modificació " + idModificacio.split("#")[1] + " que conté parts que quedaran ocultes";
	  			int idUsuari = 37; // Mateu Suñer
	  			int novaTasca = TascaCore.novaTasca(conn, "generica", idUsuari, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getActuacio().getIdIncidencia(), comentari, "Modificació amb parts ocultes", idInforme, null, request.getRemoteAddr(), "automatic");
	  			TascaCore.seguirTasca(conn, novaTasca, 6); //Seguiment a Marta Garcia
	  		}
	  		
	  		ActuacioCore.actualitzarActuacio(conn, informe.getActuacio().getReferencia(), "Proposta modificació realitzada");
			
	  		int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
	  		
			if (proposta.getPbase() > 0) {
				String idModificacioAUX = idModificacio.split("#")[0];
				TascaCore.novaTasca(conn, "resPartidaModificacio", idUsuari, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getActuacio().getIdIncidencia(), "Modificació expedient " + informe.getExpcontratacio().getExpContratacio(), "Modificació expedient " + informe.getExpcontratacio().getExpContratacio(), idModificacioAUX, null, request.getRemoteAddr(), "automatic");
			} else {
				idUsuari = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
				String comentari = "S'ha realitzat la proposta de modificació " + idModificacio.split("#")[1] ;
				TascaCore.novaTasca(conn, "autoritzacioModificacio", idUsuari, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getActuacio().getIdIncidencia(), comentari, "Autorització modificació actuació", idInforme, null, request.getRemoteAddr(), "automatic");
			}
			if (nousDocumentsSignar) {
				String idModificacioAUX = idModificacio.split("#")[1];
				TascaCore.novaTasca(conn, "generic", UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari(), Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getActuacio().getIdIncidencia(),  Usuari.getNomCompletReal() + ": S'ha afegit nova documentació per signar dins el procediment" + idModificacioAUX, "Nova documentació", idInforme, null, request.getRemoteAddr(), "automatic");
			}
		
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	  		
	   		/*;*/
		
	  	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + informe.getActuacio().getReferencia());	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
