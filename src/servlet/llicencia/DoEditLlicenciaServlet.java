package servlet.llicencia;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.Llicencia;
import bean.User;
import core.InformeCore;
import core.LlicenciaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditLlicenciaServlet
 */
@WebServlet("/doEditLlicencia")
public class DoEditLlicenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditLlicenciaServlet() {
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
       	String codi = multipartParams.getParametres().get("llicencia");
       	String idInforme = multipartParams.getParametres().get("idInforme"); 
    	String idActuacio = multipartParams.getParametres().get("idActuacio");  
       	Llicencia llicencia = new Llicencia();
       	String errorString = null;
       	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");      
       	try {
       		InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
       		llicencia = LlicenciaCore.findLlicencia(conn, codi);     
			llicencia.setTipus(multipartParams.getParametres().get("tipus"));
			llicencia.setTaxa(Double.parseDouble(multipartParams.getParametres().get("taxa")));
			llicencia.setIco(Double.parseDouble(multipartParams.getParametres().get("ico")));
			
			//Actualitzar reserva partida
			double valor = Double.parseDouble(multipartParams.getParametres().get("taxa")) + Double.parseDouble(multipartParams.getParametres().get("ico"));
			LlicenciaCore.actualitzarPartida(conn, llicencia, valor, informe);
		 
			if (multipartParams.getParametres().get("dataSolicitud") != null && ! multipartParams.getParametres().get("dataSolicitud").isEmpty()) {
				llicencia.setPeticio(formatter.parse(multipartParams.getParametres().get("dataSolicitud")));
			}
			if (multipartParams.getParametres().get("dataConcesio") != null && ! multipartParams.getParametres().get("dataConcesio").isEmpty()) {
				llicencia.setConcesio(formatter.parse(multipartParams.getParametres().get("dataConcesio")));
			}
			if (multipartParams.getParametres().get("dataPagamentTaxa") != null && ! multipartParams.getParametres().get("dataPagamentTaxa").isEmpty()) {
				llicencia.setPagamentTaxa(formatter.parse(multipartParams.getParametres().get("dataPagamentTaxa")));
			}
			if (multipartParams.getParametres().get("dataPagamentICO") != null && ! multipartParams.getParametres().get("dataPagamentICO").isEmpty()) {
				llicencia.setPagamentICO(formatter.parse(multipartParams.getParametres().get("dataPagamentICO")));
			}
			
			llicencia.setObservacio(multipartParams.getParametres().get("observacions"));
			
			LlicenciaCore.guardarArxiu(conn, multipartParams.getFitxersByName().get("documentSolLlicencia"), codi, Usuari.getIdUsuari(), "Solicitud");
			LlicenciaCore.guardarArxiu(conn, multipartParams.getFitxersByName().get("documentConcessioLlicencia"), codi, Usuari.getIdUsuari(), "Concessio");
			LlicenciaCore.guardarArxiu(conn, multipartParams.getFitxersByName().get("documentPagamentLlicencia"), codi, Usuari.getIdUsuari(), "Pagament");
			LlicenciaCore.guardarArxiu(conn, multipartParams.getFitxersByName().get("documentTitolHabilitant"), codi, Usuari.getIdUsuari(), "Habilitant");
			
			LlicenciaCore.updateLlicencia(conn, llicencia);
			
			
			
		} catch (ParseException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("llicencia", llicencia);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/llicencies/editLlicenciaView.jsp");
           dispatcher.forward(request, response);
		}         
		else {
			response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
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
