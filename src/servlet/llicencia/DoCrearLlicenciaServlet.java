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
 * Servlet implementation class DoCrearLlicenciaServlet
 */
@WebServlet("/doCrearLlicencia")
public class DoCrearLlicenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCrearLlicenciaServlet() {
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
       	String idInforme = multipartParams.getParametres().get("idInforme");      
    	String idActuacio =  multipartParams.getParametres().get("idActuacio");      
    	double taxa = 0;
    	if (!multipartParams.getParametres().get("taxa").isEmpty()) taxa = Double.parseDouble(multipartParams.getParametres().get("taxa"));
    	double ico = 0;
    	if (!multipartParams.getParametres().get("ico").isEmpty()) ico = Double.parseDouble(multipartParams.getParametres().get("ico"));
        String codi = "";
    	Llicencia llicencia = new Llicencia();
       	String errorString = null;
       	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");      
       	try {
       		InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);          		
			//Actualitzar reserva partida
			double valor = taxa + ico;	
			
			llicencia.setCodiExpedient(idInforme);
			llicencia.setTaxa(taxa);
			llicencia.setIco(ico);
			llicencia.setTipus(multipartParams.getParametres().get("tipusLlicencia"));
			
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
			codi = LlicenciaCore.novaLlicencia(conn, idInforme, llicencia); 
			llicencia = LlicenciaCore.findLlicencia(conn, codi);
			if (valor > 0) LlicenciaCore.actualitzarPartida(conn, llicencia, valor, informe);
			LlicenciaCore.guardarArxiu(conn, multipartParams.getFitxersByName().get("documentSolLlicencia"), codi, Usuari.getIdUsuari(), "Solicitud");
			LlicenciaCore.guardarArxiu(conn, multipartParams.getFitxersByName().get("documentConcessioLlicencia"), codi, Usuari.getIdUsuari(), "Concessio");
			LlicenciaCore.guardarArxiu(conn, multipartParams.getFitxersByName().get("documentPagamentLlicencia"), codi, Usuari.getIdUsuari(), "Pagament");
			LlicenciaCore.guardarArxiu(conn, multipartParams.getFitxersByName().get("documentTitolHabilitant"), codi, Usuari.getIdUsuari(), "Habilitant");
						
		} catch (ParseException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("llicencia", llicencia);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/llicencies/crearLlicenciaView.jsp");
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
