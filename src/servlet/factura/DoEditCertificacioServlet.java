package servlet.factura;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Factura;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.FacturaCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditCertificacioServlet
 */
@WebServlet("/doEditCertificacio")
public class DoEditCertificacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditCertificacioServlet() {
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
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    String idCertificacio = multipartParams.getParametres().get("idCertificacio");
	    Factura certificacio = new Factura();
	    String idInforme;
	    String idActuacio;
	    String idProveidor;
	    if (UsuariCore.hasPermision(conn, Usuari, SectionPage.factures_crear)) {
		    idInforme = multipartParams.getParametres().get("idInforme");
		    idActuacio = multipartParams.getParametres().get("idActuacio");
		    idProveidor = multipartParams.getParametres().get("idEmpresa");	    
		    String concepte = multipartParams.getParametres().get("concepte");	   
		    double valor = Double.parseDouble(multipartParams.getParametres().get("import").replace(",", "."));	  
		    String nombreCertificacio = multipartParams.getParametres().get("nombre");
		    String notes = multipartParams.getParametres().get("notes");
		    String tipus = multipartParams.getParametres().get("tipusCertificacio");
		    
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    Date dataCertificacio = null;
		    Date dataEntrada = null;  
		    Date dataPasadaConformar = null;
		    Date dataConformada = null;
		    Date dataPasadaComptabilitat = null;
		    try {
				if (multipartParams.getParametres().get("dataEntrada") != null && ! multipartParams.getParametres().get("dataEntrada").isEmpty()) dataEntrada = formatter.parse(multipartParams.getParametres().get("dataEntrada"));
				if (multipartParams.getParametres().get("dataFactura") != null && ! multipartParams.getParametres().get("dataFactura").isEmpty()) dataCertificacio = formatter.parse(multipartParams.getParametres().get("dataFactura"));
				if (multipartParams.getParametres().get("dataPasadaConformar") != null && ! multipartParams.getParametres().get("dataPasadaConformar").isEmpty()) dataPasadaConformar = formatter.parse(multipartParams.getParametres().get("dataPasadaConformar"));
				if (multipartParams.getParametres().get("dataConformada") != null && ! multipartParams.getParametres().get("dataConformada").isEmpty()) dataConformada = formatter.parse(multipartParams.getParametres().get("dataConformada"));
				if (multipartParams.getParametres().get("dataPasadaComptabilitat") != null && ! multipartParams.getParametres().get("dataPasadaComptabilitat").isEmpty()) dataPasadaComptabilitat = formatter.parse(multipartParams.getParametres().get("dataPasadaComptabilitat"));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		    	    
		  
		    certificacio.setIdFactura(idCertificacio);
		    certificacio.setIdActuacio(idActuacio);
		    certificacio.setIdInforme(idInforme);
		    certificacio.setIdProveidor(idProveidor);
		    certificacio.setConcepte(concepte);
		    certificacio.setValor(valor);
		    certificacio.setDataFactura(dataCertificacio);
		    certificacio.setDataEntrada(dataEntrada);
		    certificacio.setNombreFactura(nombreCertificacio);
		    certificacio.setNotes(notes);
		    certificacio.setTipus(tipus);
		    
		
	   		certificacio.setUsuariConformador(InformeCore.getResponsableContracte(conn, idInforme));
			certificacio.setDataEnviatConformador(dataPasadaConformar);
			certificacio.setDataConformacio(dataConformada);
			certificacio.setDataEnviatComptabilitat(dataPasadaComptabilitat);
			FacturaCore.modificarCertificacio(conn, certificacio, Usuari.getIdUsuari());
	    } else {
	    	certificacio = FacturaCore.getCertificacio(conn, idCertificacio);
	    	idActuacio = certificacio.getIdActuacio();
	    	idInforme = certificacio.getIdInforme();
	    	idProveidor = certificacio.getIdProveidor();
	    }
		
	    int usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariCertificacions")); 
		String idIncidencia = ActuacioCore.findActuacio(conn, idActuacio).getIdIncidencia();
		if (multipartParams.getFitxersByName().get("certificacio") != null) {
			FacturaCore.saveArxiuCertificacio(idIncidencia, idActuacio, idInforme, idProveidor, idCertificacio, multipartParams.getFitxersByName().get("certificacio"), conn, Usuari.getIdUsuari());
			if (usuariTasca != Usuari.getIdUsuari()) {
				TascaCore.novaTasca(conn, "revisarCertificacio", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "Nova certificació", "Nova certificació", idCertificacio, null, request.getRemoteAddr(), "automatic");
			}
		}
		if (multipartParams.getFitxersByName().get("relaciovalorada") != null) {
			FacturaCore.saveArxiuRelacioValorada(idIncidencia, idActuacio, idInforme, idProveidor, idCertificacio, multipartParams.getFitxersByName().get("relaciovalorada"), conn, Usuari.getIdUsuari());   
			if (usuariTasca != Usuari.getIdUsuari()) {
				TascaCore.novaTasca(conn, "generica", usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "S'ha afegit la relació valorada de la certificació " + idCertificacio, "Relació valorada", idInforme, null, request.getRemoteAddr(), "automatic");
			}
		}
				
		// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("certificacio", certificacio);	   	
	  	// If error, forward to Edit page.
	   
	   	response.sendRedirect(request.getContextPath() + "/certificacioDetalls?ref=" + idCertificacio);
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
