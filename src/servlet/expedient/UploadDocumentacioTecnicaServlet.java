package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class UploadDocumentacioTecnicaServlet
 */
@WebServlet("/uploadDocumentacioTecnica")
public class UploadDocumentacioTecnicaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDocumentacioTecnicaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		Connection conn = MyUtils.getStoredConnection(request);	
		String ipRemote = request.getRemoteAddr();
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme"); 
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentInformeSupervisio"), idIncidencia, idActuacio, "", "", "", idInforme, "Informe Supervisió", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentProjecte"), idIncidencia, idActuacio, "", "", "", idInforme, "Projecte", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentNomenamentDF"), idIncidencia, idActuacio, "", "", "", idInforme, "NomenamentDF", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentPSS"), idIncidencia, idActuacio, "", "", "", idInforme, "PSS", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentPGR"), idIncidencia, idActuacio, "", "", "", idInforme, "PGR", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentPlaTreball"), idIncidencia, idActuacio, "", "", "", idInforme, "PlaTreball", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentControlQualitat"), idIncidencia, idActuacio, "", "", "", idInforme, "ControlQualitat", Usuari.getIdUsuari());		
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentFinalitzacioContratista"), idIncidencia, idActuacio, "", "", "", idInforme, "Finalització contratista", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentInformeDO"), idIncidencia, idActuacio, "", "", "", idInforme, "Informe DO", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentCFO"), idIncidencia, idActuacio, "", "", "", idInforme, "CFO", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentRepresentacioRecepcio"), idIncidencia, idActuacio, "", "", "", idInforme, "Representació Recepció", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentCertificacioFinal"), idIncidencia, idActuacio, "", "", "", idInforme, "CertificacioFinal", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentDevolucioAval"), idIncidencia, idActuacio, "", "", "", idInforme, "DevolucioAval", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentCertificatsBonaExecucio"), idIncidencia, idActuacio, "", "", "", idInforme, "CertificatExecucio", Usuari.getIdUsuari());
		Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentAltre"), idIncidencia, idActuacio, "", "", "", idInforme, "Documentació tècnica", Usuari.getIdUsuari());
		
		
		
		/*
		 * InformeCore.modificarEstat(conn, idInforme, "garantia");
		 * 
		 * 
		 * 
		 * */
		
		String documents = "";
		Fitxer fitxer = null;
		for(int i=0;i<multipartParams.getFitxers().size();i++){
			 fitxer = (Fitxer) multipartParams.getFitxers().get(i);
			 documents += fitxer.getFitxer().getName() + "<br>";
		}
		if (Usuari.getIdUsuari() != 4) {
			if (Usuari.getRol().contains("CAP")) {
				TascaCore.novaTasca(conn, "generic", UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia,  Usuari.getNomCompletReal() + ": S'ha afegit nova documentació tècnica <br>" + documents, "Nova documentació", idInforme, null, ipRemote, "automatic");
			} else {
				TascaCore.novaTasca(conn, "generic", UsuariCore.finCap(conn, Usuari.getDepartament()).getIdUsuari(), Usuari.getIdUsuari(), idActuacio, idIncidencia,  Usuari.getNomCompletReal() + ": S'ha afegit nova documentació tècnica <br>" + documents, "Nova documentació", idInforme, null, ipRemote, "automatic");
			}
		}
		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio + "&exp=" + idInforme);  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
