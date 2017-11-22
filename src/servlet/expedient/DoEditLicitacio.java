package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Expedient;
import bean.InformeActuacio;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import bean.Oferta;
import core.ActuacioCore;
import core.CreditCore;
import core.ExpedientCore;
import core.InformeCore;
import core.OfertaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditLicitacio
 */
@WebServlet("/doEditLicitacio")
public class DoEditLicitacio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditLicitacio() {
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
		User Usuari = MyUtils.getLoginedUser(request.getSession());	
       	String refExp = multipartParams.getParametres().get("expedient");
       	String idInforme = multipartParams.getParametres().get("informe");
       	Expedient expedient = new Expedient();
       	String comentari = multipartParams.getParametres().get("propostaTecnica");	
	    String termini = multipartParams.getParametres().get("termini");
	    String seleccionada = multipartParams.getParametres().get("idOfertaSeleccionada");
	    String capDobra = multipartParams.getParametres().get("capDobra");
       	String errorString = null;     
       	InformeActuacio informe = new InformeActuacio();
       	try {       		
       		informe = InformeCore.getInformePrevi(conn, idInforme, false);
       		OfertaCore.seleccionarOferta(conn, idInforme, seleccionada, termini, comentari);    	   
    	    expedient = ExpedientCore.findExpedient(conn, refExp);
    	    if (informe.getPartida() != null && !informe.getPartida().isEmpty()) CreditCore.assignar(conn, idInforme, OfertaCore.findOfertaById(conn, seleccionada).getPlic());   
    	    if (multipartParams.getFitxersByName().get("propostaTecnica") != null) Fitxers.guardarFitxer(Arrays.asList(multipartParams.getFitxersByName().get("propostaTecnica")), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Proposta tècnica");
    	    if (multipartParams.getFitxersByName().get("autoritzacioDespesa") != null) {
    	    	Fitxers.guardarFitxer(Arrays.asList(multipartParams.getFitxersByName().get("autoritzacioDespesa")), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Autorització  Proposta despesa");
    	    	ActuacioCore.aprovar(conn, informe.getActuacio().getReferencia(), Usuari.getIdUsuari());
				ActuacioCore.actualitzarActuacio(conn, informe.getActuacio().getReferencia(), "Autorització generada");
				OfertaCore.aprovarOferta(conn, idInforme, Usuari.getIdUsuari());
    	    }
    	    if (!capDobra.isEmpty()) OfertaCore.actualitzarCapDobra(conn, seleccionada, capDobra);
    	    Oferta oferta = OfertaCore.findOfertaById(conn, seleccionada);
    	    if (multipartParams.getFitxersByName().get("personalInscrit") != null) OfertaCore.guardarFitxer(Arrays.asList(multipartParams.getFitxersByName().get("personalInscrit")), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme, oferta.getCifEmpresa(), "Personal Inscrit");
       	} catch (SQLException | NamingException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("expedient", expedient);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/expedients/editLicitacioView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
			if (multipartParams.getParametres().get("redireccio").equals("actuacions")) {
				response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + informe.getActuacio().getReferencia());
			}else{
				response.sendRedirect(request.getContextPath() + "/expedient?ref=" + refExp);
			}
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
