package servlet.expedient;

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

import bean.InformeActuacio;
import bean.Oferta;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import core.CreditCore;
import core.InformeCore;
import core.OfertaCore;
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
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	       	  
	    String errorString = null;	    
	    InformeActuacio informe = new InformeActuacio();
	    InformeActuacio informeModificacio = new InformeActuacio();
	  	try {	 
	  		informe = InformeCore.getInformePrevi(conn, idInforme, false);
	  		informeModificacio = InformeCore.getMoficacioInforme(conn, idModificacio, false);
	  		PropostaInforme proposta = informeModificacio.getPropostaInformeSeleccionada();	  		
	  		
	  		proposta.setObjecte(objecte);
	  		
	  		proposta.setPlic(plic);
	  		proposta.setRetencio(retencio);
	  		informeModificacio.setPropostaInformeSeleccionada(proposta);
	  		
	  		Oferta ofertaProposta = informeModificacio.getOfertaSeleccionada();
	  		
	  		 		
	  		//Modificar modificaci�	  		
	  		InformeCore.modificarModificacioInforme(conn, idModificacio, proposta, ofertaProposta, Usuari, "penalitzacio");	  		
	  		
	  		if (multipartParams.getFitxersByName().get("informe") != null) {
	  			InformeCore.saveInformeModificacio(conn, informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme, idModificacio, multipartParams.getFitxersByName().get("informe"), Usuari.getIdUsuari());
	  		}	 		
	  		
	  		if (multipartParams.getFitxersByName().get("tramitsPenalitzacio") != null) {
	  			InformeCore.saveTramitsPenalitzacio(conn, informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme, idModificacio, multipartParams.getFitxersByName().get("tramitsPenalitzacio"), Usuari.getIdUsuari());
		  	}	  		
	  		/*;*/
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
	   				.getRequestDispatcher("/WEB-INF/views/expedients/editPenalitzacioView.jsp");
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
