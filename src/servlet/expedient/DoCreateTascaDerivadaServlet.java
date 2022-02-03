package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
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
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
			
	    int idTasca = -1;
	    String idInformeOriginal = multipartParams.getParametres().get("idInforme");	  
	   	
	   	String comentari = multipartParams.getParametres().get("comentari");
		String assumpte = multipartParams.getParametres().get("assumpte");
		String comentariCap =  multipartParams.getParametres().get("comentariCap");
	   	
	   	int idTecnic = Integer.parseInt(multipartParams.getParametres().get("llistaUsuaris"));
	   	if (idTecnic == -1 ) {
	   		idTecnic = Usuari.getIdUsuari();
		} 		   	
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
		comentari = multipartParams.getParametres().get("comentari");			   
		proposta.setTermini(termini);
		proposta.setComentari(comentari);
		proposta.setSeleccionada(true);		
		llistaPropostes.add(proposta);
		informe.setLlistaPropostes(llistaPropostes);
		informe.setPropostaInformeSeleccionada(proposta);	
		informe.setComentariCap(comentariCap);
		InformeCore.modificarInforme(conn, informe, idTecnic);	 
		informe = InformeCore.getInformePrevi(conn, idInformePrevi, false);
		InformeCore.seleccionarProposta(conn, informe.getLlistaPropostes().get(0).getIdProposta(), idInformePrevi);	   
		informe = InformeCore.getInformePrevi(conn, idInformePrevi, false);
		Fitxers.guardarFitxer(conn, multipartParams.getFitxers(), idIncidencia, idActuacio, "", "", "", idInformePrevi, "Informe previ", Usuari.getIdUsuari());
		
		int idUsuariTasca = -1;
		String tipusTasca = "infPrev";
		if (Usuari.getRol().contains("CAP")) {
			idUsuariTasca = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();
		}else{
			idUsuariTasca = UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari();
			tipusTasca = "vistInfPrev";
		}	
		
		idTasca = TascaCore.novaTasca(conn, tipusTasca, idUsuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, comentari, assumpte, idInformePrevi, null, request.getRemoteAddr(), "manual");
	   
	   
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
