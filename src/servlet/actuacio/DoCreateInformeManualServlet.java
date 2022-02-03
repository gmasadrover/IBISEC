package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.InformeActuacio;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import core.ActuacioCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateInformeManualServlet
 */
@WebServlet("/DoCreateInformeManual")
public class DoCreateInformeManualServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateInformeManualServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			Connection conn = MyUtils.getStoredConnection(request);
			
		    String idActuacio = request.getParameter("idActuacio");
		    int idTecnic =  Integer.parseInt(request.getParameter("idTecnic"));
		    String descripcio = request.getParameter("descripcio");
		    String tipusObra = request.getParameter("tipusContracte");
		    boolean llicencia = false;
		    String reqLlicencia = request.getParameter("reqLlicencia");
		    String tipusLlicencia = "";
		    boolean contracte = true;
		    String errorString = null;	 
		    double pbase = 0;
			double iva = 0;
			double plic = 0;
			String termini = "";
			String comentari = "";
			boolean tramitacioIBISEC = "on".equals(request.getParameter("tramitacioIBISEC"));
			boolean tramitacioConselleria = "on".equals(request.getParameter("tramitacioConselleria"));
			boolean tramitacioAltres = "on".equals(request.getParameter("tramitacioAltres"));
			boolean cessioCredit = "on".equals(request.getParameter("esCessioDeCredit"));
		   	if (errorString == null) {
		   		User tecnic = UsuariCore.findUsuariByID(conn, idTecnic);
				Actuacio actuacio = ActuacioCore.findActuacio(conn, idActuacio);
				//Crear informe
				InformeActuacio informe = new InformeActuacio();
				PropostaInforme proposta = informe.new PropostaInforme();
				List<PropostaInforme> llistaPropostes = new ArrayList<PropostaInforme>();
				informe.setIdIncidencia(actuacio.getIdIncidencia());
				informe.setActuacio(actuacio);
				informe.setUsuari(tecnic);
				proposta = informe.new PropostaInforme();
				proposta.setObjecte(descripcio);
				proposta.setTipusObra(tipusObra);
				if (new String("obr").equals(tipusObra)) {
					llicencia = new String("si").equals(reqLlicencia);	 	   
					if (llicencia) tipusLlicencia = request.getParameter("tipusLlicencia") ;
				}	
				proposta.setLlicencia(llicencia);
				proposta.setTipusLlicencia(tipusLlicencia);
				proposta.setContracte(contracte);
				pbase = Double.parseDouble(request.getParameter("pbase").replace(',','.'));
				iva = Double.parseDouble(request.getParameter("iva"));
				plic = Double.parseDouble(request.getParameter("plic").replace(',','.'));
				proposta.setPbase(pbase);
				proposta.setIva(iva);
				proposta.setPlic(plic);
				termini = request.getParameter("termini" );
				comentari = request.getParameter("comentariTecnic");			   
				proposta.setTermini(termini);
				proposta.setComentari(comentari);
				proposta.setSeleccionada(true);			    
				llistaPropostes.add(proposta);
				informe.setLlistaPropostes(llistaPropostes);
				informe.setPropostaInformeSeleccionada(proposta);	
				if (tipusObra.equals("conveni")) {
					String dependencies = "";				    	
					if (tramitacioIBISEC) dependencies += "IBISEC#";
					if (tramitacioConselleria) dependencies += "Conselleria#";
					if (tramitacioAltres) dependencies += "Altres#";
					informe.setOrganismeDependencia(dependencies);
					informe.setCessioCredit(cessioCredit);
				}
				String idinforme =  InformeCore.nouInforme(conn, informe, idTecnic);
				informe = InformeCore.getInformePrevi(conn, idinforme, false);
				InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idinforme);
				 
				
		   	}
		   	
		   	// Store infomation to request attribute, before forward to views.
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
