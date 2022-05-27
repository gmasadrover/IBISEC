package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.Oferta;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import core.CreditCore;
import core.InformeCore;
import core.OfertaCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditModificacioInformeServlet
 */
@WebServlet("/DoEditModificacioInforme")
public class DoEditModificacioInformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditModificacioInformeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);	
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
	    String idInforme = multipartParams.getParametres().get("idInforme");
	    String idModificacio = multipartParams.getParametres().get("idModificacio");
	    String objecte = multipartParams.getParametres().get("objecteModificacio");
	    String tipusIncidencia = multipartParams.getParametres().get("tipusIncidencia");
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    boolean reqLlicencia = false;	   
	    String tipusLlicencia = multipartParams.getParametres().get("tipusLlicencia");	   	   
	    double pbase = 0;
	    double iva = 0;
	    double plic = 0;
	 	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");			
	    if (tipusIncidencia.equals("penalitzacio")) {	
	    	plic = Double.parseDouble(multipartParams.getParametres().get("plicPenalitzacio").replace(',','.'));
	    	if (plic > 0) plic = -1*plic;	    
	    } else if(tipusIncidencia.equals("certfinal") || tipusIncidencia.equals("excesAmidament")  || tipusIncidencia.equals("decrementAmidament") ||  tipusIncidencia.equals("enriquimentInjust") || tipusIncidencia.equals("modificacio") || tipusIncidencia.equals("liquidacio") || tipusIncidencia.equals("preusContradictoris")) {
	    	pbase = Double.parseDouble(multipartParams.getParametres().get("pbase").replace(',','.'));
	    	iva = Double.parseDouble(multipartParams.getParametres().get("iva"));
	    	plic = Double.parseDouble(multipartParams.getParametres().get("plic").replace(',','.'));	
	    	 if (multipartParams.getParametres().get("reqLlicencia") != null) reqLlicencia = multipartParams.getParametres().get("reqLlicencia").equals("si");	   
	    }
	    
	    String termini = multipartParams.getParametres().get("termini");
	    
	    String empresaCif = multipartParams.getParametres().get("llistaEmpreses");	  
	    String propostaTecnica = multipartParams.getParametres().get("propostaTecnica");
	    
	    String errorString = null;	    
	    InformeActuacio informe = new InformeActuacio();
	    InformeActuacio informeModificacio = new InformeActuacio();
	  	try {	 
	  		informe = InformeCore.getInformePrevi(conn, idInforme, false);
	  		informeModificacio = InformeCore.getMoficacioInforme(conn, idModificacio, true);
	  		PropostaInforme proposta = informeModificacio.getPropostaInformeSeleccionada();	  		
	  		
	  		if (plic > 0 && proposta.getPlic() != plic) {
	  			int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();	  		
				String idModificacioAUX = idModificacio.split("#")[0];
				TascaCore.novaTasca(conn, "resPartidaModificacio", idUsuari, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getActuacio().getIdIncidencia(), "Modificació expedient " + informe.getExpcontratacio().getExpContratacio(), "Modificació expedient " + informe.getExpcontratacio().getExpContratacio(), idModificacioAUX, null, request.getRemoteAddr(), "automatic");
			}
	  		
	  		proposta.setLlicencia(reqLlicencia);
	  		if (reqLlicencia) proposta.setTipusLlicencia(tipusLlicencia);
	  		proposta.setObjecte(objecte);
	  		proposta.setPbase(pbase);
	  		proposta.setIva(iva);
	  		proposta.setPlic(plic);
	  		proposta.setTermini(termini);
	  		if (multipartParams.getParametres().get("dataContracte") != null && ! multipartParams.getParametres().get("dataContracte").isEmpty()) {
	  			proposta.setDataFirmaModificacio(formatter.parse(multipartParams.getParametres().get("dataContracte")));
		    }
	  		if (multipartParams.getParametres().get("retencio") != null && multipartParams.getParametres().get("retencio").equals("on")) {
	    		proposta.setRetencio(true);
	    	}
	    	if (multipartParams.getParametres().get("execucio") != null &&  multipartParams.getParametres().get("execucio").equals("on")) {
	    		proposta.setRetencio(false);
	    	}	
	  		informeModificacio.setPropostaInformeSeleccionada(proposta);
	  		
	  		Oferta ofertaProposta = informeModificacio.getOfertaSeleccionada();
	  		if (!tipusIncidencia.equals("penalitzacio")) {	 		  		
		  		ofertaProposta.setCifEmpresa(empresaCif);
		  		ofertaProposta.setPlic(plic);
		   		ofertaProposta.setPbase(pbase);
		   		ofertaProposta.setIva(iva);			
		  		ofertaProposta.setComentari(propostaTecnica);
	  		}
	  		 		
	  		
	  		boolean aprovarModificacio = false;
	  		//Modificar modificació	  		
	  		InformeCore.modificarModificacioInforme(conn, idModificacio, proposta, ofertaProposta, Usuari, tipusIncidencia);
	  		if (!tipusIncidencia.equals("penalitzacio")) {
		  		OfertaCore.modificarOferta(conn, ofertaProposta);
	  		}
	  		if (multipartParams.getFitxersByName().get("informeDF") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("informeDF"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Informe DF", Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("resinici") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("resinici"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Resolució Inici", Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("tramits") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("tramits"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Tramits", Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("resfinal") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("resfinal"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Resolució Final", Usuari.getIdUsuari());
	  			aprovarModificacio = true;
	  		}
	  		if (multipartParams.getFitxersByName().get("informe") != null) {
	  			InformeCore.saveInformeModificacio(conn, informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme, idModificacio.split("#")[0], multipartParams.getFitxersByName().get("informe"), Usuari.getIdUsuari());
	  		}	  		
	  		if (multipartParams.getFitxersByName().get("certificatEconomic") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("certificatEconomic"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Conforme àrea financera", Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("informeJuridic") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("informeJuridic"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Informe Juridic", Usuari.getIdUsuari());
	  		}
	  		if (multipartParams.getFitxersByName().get("autoritzacioDespesa") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("autoritzacioDespesa"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Autorització Despesa modificació", Usuari.getIdUsuari());
		  		aprovarModificacio = true;
	  		}	
	  		if (multipartParams.getFitxersByName().get("contracte") != null) {
	  			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("contracte"), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", idInforme, idModificacio.split("#")[0], "Contracte", Usuari.getIdUsuari());
	  		}
	  		
	  		if (aprovarModificacio) {
	  			OfertaCore.aprovarOferta(conn, idModificacio, Usuari.getIdUsuari());
		  		CreditCore.assignar(conn, idModificacio, informeModificacio.getOfertaSeleccionada().getPlic());
	  		}
	  		/*;*/
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.getMessage();
		} 
	  	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/expedients/editModificacioView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + informe.getActuacio().getReferencia());
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
