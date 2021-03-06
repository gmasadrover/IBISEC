package handler;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Configuracio;
import core.ConfiguracioCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class UpdateConfiguracio
 */
@WebServlet("/updateConfiguracio")
public class UpdateConfiguracio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateConfiguracio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);			
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);				
		double importObraMajor =  Double.parseDouble(multipartParams.getParametres().get("importObraMajor"));
		double importServeiMajor =  Double.parseDouble(multipartParams.getParametres().get("importServeiMajor"));
		double importSubministramentMajor =  Double.parseDouble(multipartParams.getParametres().get("importSubministramentMajor"));
	    String rutaBaseDocumentacio = multipartParams.getParametres().get("rutaBaseDocumentacio");	   
		int idUsuariRecercaPresuposts = Integer.parseInt(multipartParams.getParametres().get("idUsuariRecercaPresuposts"));
		int idUsuariFactures = Integer.parseInt(multipartParams.getParametres().get("idUsuariFactures"));	
		int idUsuariCertificacions = Integer.parseInt(multipartParams.getParametres().get("idUsuariCertificacions"));	
		int idUsuariOrdreInici = Integer.parseInt(multipartParams.getParametres().get("idUsuariOrdreInici"));	
		int idUsuariRedaccioContracte = Integer.parseInt(multipartParams.getParametres().get("idUsuariRedaccioContracte"));	
		int idUsuariActualitzarEmpresa = Integer.parseInt(multipartParams.getParametres().get("idUsuariActualitzarEmpresa"));	
		int idUsuariLlicencies = Integer.parseInt(multipartParams.getParametres().get("idUsuariLlicencies")); 
		int idUsuariDron = Integer.parseInt(multipartParams.getParametres().get("idUsuariDron")); 
	   	Configuracio novaConfiguracio = new Configuracio(); 
	    String errorString = null;
	    novaConfiguracio.setImportObraMajor(importObraMajor);
		novaConfiguracio.setImportServeiMajor(importServeiMajor);
		novaConfiguracio.setImportSubministramentMajor(importSubministramentMajor);
		novaConfiguracio.setRutaBaseDocumentacio(rutaBaseDocumentacio);
		novaConfiguracio.setIdUsuariRecercaPresuposts(idUsuariRecercaPresuposts);
		novaConfiguracio.setIdUsuariFactures(idUsuariFactures);
		novaConfiguracio.setIdUsuariCertificacions(idUsuariCertificacions);
		novaConfiguracio.setIdUsuariOrdreInici(idUsuariOrdreInici);
		novaConfiguracio.setIdUsuariRedaccioContracte(idUsuariRedaccioContracte);
		novaConfiguracio.setIdUsuariActualitzarEmpresa(idUsuariActualitzarEmpresa);
		novaConfiguracio.setIdUsuariLlicencies(idUsuariLlicencies);
		novaConfiguracio.setIdUsuariDron(idUsuariDron);
		
		
		ConfiguracioCore.updateConfiguracio(conn, novaConfiguracio);
	    
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	    response.sendRedirect(request.getContextPath() + "/control");
	}

}
