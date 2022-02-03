package servlet.expedient;

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
import bean.User;
import bean.InformeActuacio.IncidenciaGarantia;
import core.InformeCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditIncidenciaGarantia
 */
@WebServlet("/doEditIncidenciaGarantia")
public class DoEditIncidenciaGarantia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditIncidenciaGarantia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User Usuari = MyUtils.getLoginedUser(request.getSession());	  
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		String errorString = null;
		String idInforme = multipartParams.getParametres().get("idInforme");
		int idIncidencia = Integer.parseInt(multipartParams.getParametres().get("idIncidencia"));
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");			
		InformeActuacio informe = new InformeActuacio();		
		try {
			informe = InformeCore.getInformePreviInfo(conn, idInforme);
			IncidenciaGarantia incidencia = InformeCore.getIncidenciaGarantia(conn, idIncidencia);			
			if (multipartParams.getParametres().get("dataInici") != null && ! multipartParams.getParametres().get("dataInici").isEmpty()) {
				incidencia.setDataInici(formatter.parse(multipartParams.getParametres().get("dataInici")));
			}
			if (multipartParams.getParametres().get("dataFi") != null && ! multipartParams.getParametres().get("dataFi").isEmpty()) {
				incidencia.setDataFi(formatter.parse(multipartParams.getParametres().get("dataFi")));
			}
			incidencia.setObjecte(multipartParams.getParametres().get("descripcio"));
			InformeCore.modificarIncidenciaGarantia(conn, incidencia, idIncidencia);
			if (multipartParams.getFitxersByName().get("documentIncidenciaGarantia") != null) {
	  			InformeCore.setFitxersIncidenciaGarantia(conn, informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme, Integer.toString(idIncidencia), multipartParams.getFitxersByName().get("documentIncidenciaGarantia"), Usuari.getIdUsuari());
	  		}
		} catch (ParseException e2) {
			errorString = e2.toString();
		}		
		
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/expedients/createIncidenciaGarantiaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + informe.getActuacio().getReferencia());
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
