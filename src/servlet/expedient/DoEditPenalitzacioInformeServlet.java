package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.Oferta;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import core.InformeCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditPenalitzacioInformeServlet
 */
@WebServlet("/DoEditPenalitzacioInforme")
public class DoEditPenalitzacioInformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditPenalitzacioInformeServlet() {
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
	    boolean retencio = false;
	    if (multipartParams.getParametres().get("retencio") != null && multipartParams.getParametres().get("retencio").equals("on")) {
    		retencio = true;
    	}
    	if (multipartParams.getParametres().get("execucio") != null &&  multipartParams.getParametres().get("execucio").equals("on")) {
    		retencio = false;
    	}
	   
    	User Usuari = MyUtils.getLoginedUser(request.getSession());	  
	   
	    double plic = 0;	    
	    
    	plic = Double.parseDouble(multipartParams.getParametres().get("plicPenalitzacio").replace(',','.'));
    	if (plic > 0) plic = -1*plic;
	       	  	    
	    InformeActuacio informe = new InformeActuacio();
	    InformeActuacio informeModificacio = new InformeActuacio();
	  	informe = InformeCore.getInformePrevi(conn, idInforme, false);
		informeModificacio = InformeCore.getMoficacioInforme(conn, idModificacio, false);
		PropostaInforme proposta = informeModificacio.getPropostaInformeSeleccionada();	  		
		
		proposta.setObjecte(objecte);
		
		proposta.setPlic(plic);
		proposta.setRetencio(retencio);
		informeModificacio.setPropostaInformeSeleccionada(proposta);
		
		Oferta ofertaProposta = informeModificacio.getOfertaSeleccionada();
		
		 		
		//Modificar modificació	  		
		InformeCore.modificarModificacioInforme(conn, idModificacio, proposta, ofertaProposta, Usuari, "penalitzacio");	  		
		
		if (multipartParams.getFitxersByName().get("informe") != null) {
			InformeCore.saveInformeModificacio(conn, informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme, idModificacio, multipartParams.getFitxersByName().get("informe"), Usuari.getIdUsuari());
		}	 		
		
		if (multipartParams.getFitxersByName().get("tramitsPenalitzacio") != null) {
			InformeCore.saveTramitsPenalitzacio(conn, informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme, idModificacio, multipartParams.getFitxersByName().get("tramitsPenalitzacio"), Usuari.getIdUsuari());
		}	  		
		/*;*/ 
	  	
	  
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
