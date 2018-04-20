package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.math.NumberUtils;

import bean.Actuacio;
import bean.Incidencia;
import bean.InformeActuacio;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import core.ActuacioCore;
import core.CentreCore;
import core.IncidenciaCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateTascaDerivadaServlet
 */
@WebServlet("/DoCreateTascaDerivada")
public class DoCreateTascaDerivadaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateTascaDerivadaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);		
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		User Usuari = MyUtils.getLoginedUser(request.getSession());	
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	    int idTasca = -1;
	    String idInformeOriginal = multipartParams.getParametres().get("idInforme");	  
	   	
	   	String comentari = multipartParams.getParametres().get("comentari");
		String assumpte = multipartParams.getParametres().get("assumpte");
	   	
	   	int idTecnic = Integer.parseInt(multipartParams.getParametres().get("llistaUsuaris"));
	   	if (idTecnic == -1 ) {
	   		idTecnic = Usuari.getIdUsuari();
		}
	   	String errorString = null;	   		   	
	   	try {	   		
	   		InformeActuacio informeOriginal = InformeCore.getInformePrevi(conn, idInformeOriginal, false);
		    String idActuacio = informeOriginal.getActuacio().getReferencia();
		   	String idIncidencia = informeOriginal.getIdIncidencia();
		   	
		  	double pbase = 0;
			double iva = 0;
			double plic = 0;
			String termini = "";
			 	    
	 	    InformeActuacio informe = new InformeActuacio();
	 	    informe.setIdTasca(idTasca);
			informe.setIdIncidencia(idIncidencia);
			informe.setActuacio(informeOriginal.getActuacio());
			String idInformePrevi = InformeCore.nouInforme(conn, informe, Usuari.getIdUsuari());
			informe = InformeCore.getInformePrevi(conn, idInformePrevi, false);
		    PropostaInforme proposta = informe.new PropostaInforme();
		    List<PropostaInforme> llistaPropostes = new ArrayList<PropostaInforme>();	    				 
		    proposta = informe.new PropostaInforme();
		    proposta.setObjecte(assumpte);
		    proposta.setTipusObra("srv");		   
		    proposta.setContracte(true);
		    pbase = Double.parseDouble(multipartParams.getParametres().get("pbase").replace(',','.'));
		    iva = Double.parseDouble(multipartParams.getParametres().get("iva"));
		    plic = Double.parseDouble(multipartParams.getParametres().get("plic").replace(',','.'));
		    proposta.setPbase(pbase);
		    proposta.setIva(iva);
		    proposta.setPlic(plic);
		    termini = multipartParams.getParametres().get("termini" );
		    comentari = multipartParams.getParametres().get("comentariTecnic");			   
		    proposta.setTermini(termini);
		    proposta.setComentari(comentari);
		    proposta.setSeleccionada(true);		
		    llistaPropostes.add(proposta);
		    informe.setLlistaPropostes(llistaPropostes);
		    informe.setPropostaInformeSeleccionada(proposta);	
		    InformeCore.modificarInforme(conn, informe, idTecnic);	 
		    informe = InformeCore.getInformePrevi(conn, idInformePrevi, false);
		    System.out.println(idInformePrevi);
		    InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInformePrevi);	   
		    informe = InformeCore.getInformePrevi(conn, idInformePrevi, false);
		    Fitxers.guardarFitxer(multipartParams.getFitxers(), idIncidencia, idActuacio, "", "", "", idInformePrevi, "Informe Previ");
		    
   			int idUsuariTasca = -1;
   			if (Usuari.getRol().contains("CAP")) {
   				idUsuariTasca = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
   			}else{
   				idUsuariTasca = UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari();
   			}	
   			
   			idTasca = TascaCore.novaTasca(conn, "docprelicitacio", idUsuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, assumpte, idInformePrevi, null);
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
	   				.getRequestDispatcher("/WEB-INF/views/tasca/createTascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.       
	   	
	   	response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
