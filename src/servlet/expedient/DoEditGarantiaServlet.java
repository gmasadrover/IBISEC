package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.User;
import core.ExpedientCore;
import core.InformeCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditGarantiaServlet
 */
@WebServlet("/doEditGarantia")
public class DoEditGarantiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditGarantiaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		Connection conn = MyUtils.getStoredConnection(request);
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInforme"); 
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, true);	
			idActuacio = informe.getActuacio().getReferencia();
			idIncidencia = informe.getIdIncidencia();
			if (multipartParams.getParametres().get("dataIniciGarantia") != null && ! multipartParams.getParametres().get("dataIniciGarantia").isEmpty()) {
	    	    	informe.getExpcontratacio().setDataRecepcio(formatter.parse(multipartParams.getParametres().get("dataIniciGarantia")));
	    	} else {
	    		informe.getExpcontratacio().setDataRecepcio(null);
	    	}
			if (multipartParams.getParametres().get("dataFiGarantia") != null && ! multipartParams.getParametres().get("dataFiGarantia").isEmpty()) {
    	    	informe.getExpcontratacio().setDataFiGarantia(formatter.parse(multipartParams.getParametres().get("dataFiGarantia")));
	    	} else {
	    		informe.getExpcontratacio().setDataFiGarantia(null);
	    	}
			if (multipartParams.getParametres().get("dataRetornGarantia") != null && ! multipartParams.getParametres().get("dataRetornGarantia").isEmpty()) {
    	    	informe.getExpcontratacio().setDataRetornGarantia(formatter.parse(multipartParams.getParametres().get("dataRetornGarantia")));
	    	} else {
	    		informe.getExpcontratacio().setDataRetornGarantia(null);
	    	}
			ExpedientCore.updateExpedient(conn, informe.getExpcontratacio());
			
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentSolDevolucio"), idIncidencia, idActuacio, "", "", "", idInforme, "Solicitud devolucio aval", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentInformeDevolucio"), idIncidencia, idActuacio, "", "", "", idInforme, "Informe devolucio aval", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentLiquidacioAval"), idIncidencia, idActuacio, "", "", "", idInforme, "Liquidacio aval", Usuari.getIdUsuari());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
