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

import bean.Actuacio;
import bean.Factura;
import bean.User;
import core.ActuacioCore;
import core.FacturaCore;
import core.InformeCore;
import core.TascaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateCertificacioServlet
 */
@WebServlet("/doCreateCertificacio")
public class DoCreateCertificacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateCertificacioServlet() {
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
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idInforme = multipartParams.getParametres().get("idInforme");
	    String idProveidor = multipartParams.getParametres().get("idEmpresa");	    
	    String concepte = multipartParams.getParametres().get("concepte");	   
	    double valor = Double.parseDouble(multipartParams.getParametres().get("import").replace(",", "."));	  
	    String nombreFactura = multipartParams.getParametres().get("nombre");
	    String notes = multipartParams.getParametres().get("notes");
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date dataCertificacio = null;
	    Date dataEntrada = null;
	    Date dataPasadaConformar = null;
		try {
			if (multipartParams.getParametres().get("dataEntrada") != null && ! multipartParams.getParametres().get("dataEntrada").isEmpty()) dataEntrada = formatter.parse(multipartParams.getParametres().get("dataEntrada"));
			if (multipartParams.getParametres().get("dataFactura") != null && ! multipartParams.getParametres().get("dataFactura").isEmpty()) dataCertificacio = formatter.parse(multipartParams.getParametres().get("dataFactura"));
			if (multipartParams.getParametres().get("dataPasadaConformar") != null && ! multipartParams.getParametres().get("dataPasadaConformar").isEmpty()) dataPasadaConformar = formatter.parse(multipartParams.getParametres().get("dataPasadaConformar"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();	    
	    Factura certificacio = new Factura();
	    certificacio.setIdFactura(idCertificacio);
	    certificacio.setIdActuacio(idActuacio);
	    certificacio.setIdInforme(idInforme);
	    certificacio.setIdProveidor(idProveidor);
	    certificacio.setConcepte(concepte);
	    certificacio.setValor(valor);
	    certificacio.setDataFactura(dataCertificacio);
	    certificacio.setDataEntrada(dataEntrada);
	    certificacio.setNombreFactura(nombreFactura);
	    certificacio.setNotes(notes);
	    
	   
		
	    String errorString = null;	 	      
	   	if (errorString == null) {
	   		String tipusCertificacio = multipartParams.getParametres().get("tipusCertificacio");
			certificacio.setTipus(tipusCertificacio);
			Actuacio actuacio = ActuacioCore.findActuacio(conn, idActuacio);			
			certificacio.setUsuariConformador(InformeCore.getResponsableContracte(conn, idInforme));
			certificacio.setDataEnviatConformador(dataPasadaConformar);
			FacturaCore.newCertificacio(conn, certificacio, idUsuari);	
			int usuariTasca = Integer.parseInt(getServletContext().getInitParameter("idUsuariCertificacions")); 
			if (multipartParams.getFitxersByName().get("certificacio") != null) {
				FacturaCore.saveArxiuCertificacio(actuacio.getIdIncidencia(), idActuacio, idInforme, idProveidor, idCertificacio, multipartParams.getFitxersByName().get("certificacio"), conn, Usuari.getIdUsuari());
				if (usuariTasca != idUsuari) {
					TascaCore.novaTasca(conn, "revisarCertificacio", usuariTasca, idUsuari, idActuacio, actuacio.getIdIncidencia(), "Nova certificació", "Nova certificació", idCertificacio, null, request.getRemoteAddr(), "automatic");
				}
			}
			if (multipartParams.getFitxersByName().get("relaciovalorada") != null) {
				FacturaCore.saveArxiuRelacioValorada(actuacio.getIdIncidencia(), idActuacio, idInforme, idProveidor, idCertificacio, multipartParams.getFitxersByName().get("relaciovalorada"), conn, Usuari.getIdUsuari());
				if (usuariTasca != idUsuari) {
					TascaCore.novaTasca(conn, "generica", usuariTasca, idUsuari, idActuacio, actuacio.getIdIncidencia(), "S'ha afegit la relació valorada de la certificació " + idCertificacio, "Relació valorada certificació", idInforme, null, request.getRemoteAddr(), "automatic");
				}
			}
			
			
	   	}	    
	   	
	  
	   	response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
