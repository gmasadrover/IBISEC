package servlet.llicencia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Llicencia;
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
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       	
       	String codi = multipartParams.getParametres().get("llicencia");
       	String from = multipartParams.getParametres().get("from");  
    	String idActuacio = multipartParams.getParametres().get("idActuacio");  
       	Llicencia llicencia = new Llicencia();
       	String errorString = null;
       	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       	try {
			llicencia.setCodi(codi);
			
			llicencia.setTaxa(Double.parseDouble(multipartParams.getParametres().get("taxa")));
			llicencia.setIco(Double.parseDouble(multipartParams.getParametres().get("ico")));
			
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
			LlicenciaCore.guardarArxiu(multipartParams.getFitxers(), codi);
			LlicenciaCore.updateLlicencia(conn, llicencia);
		} catch (ParseException | SQLException | NamingException e) {
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
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
			if (from.equals("expedient")) {
				response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
			} else {
				response.sendRedirect(request.getContextPath() + "/editLlicencia?codi=" + llicencia.getCodi());
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
