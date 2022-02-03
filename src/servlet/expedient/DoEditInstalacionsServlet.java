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

import bean.Instalacions;
import bean.User;
import core.InformeCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditInstalacionsServlet
 */
@WebServlet("/doEditInstalacions")
public class DoEditInstalacionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditInstalacionsServlet() {
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
			Instalacions instalacions = InformeCore.getInstalacions(conn, idInforme);
			instalacions.setIdInf(idInforme);
			instalacions.setExpedientBaixaTensio(multipartParams.getParametres().get("expedientBaixaTensio"));
			if (multipartParams.getParametres().get("dataOCABaixaTensio") != null && ! multipartParams.getParametres().get("dataOCABaixaTensio").isEmpty()) {
	    	    	instalacions.setDataOCABaixaTensio(formatter.parse(multipartParams.getParametres().get("dataOCABaixaTensio")));
	    	} else {
	    		instalacions.setDataOCABaixaTensio(null);
	    	}
			instalacions.setExpedientFotovoltaica(multipartParams.getParametres().get("expedientFotovoltaica"));
			if (multipartParams.getParametres().get("dataOCAFotovoltaica") != null && ! multipartParams.getParametres().get("dataOCAFotovoltaica").isEmpty()) {
	    	    	instalacions.setDataOCAFotovoltaica(formatter.parse(multipartParams.getParametres().get("dataOCAFotovoltaica")));
	    	} else {
	    		instalacions.setDataOCAFotovoltaica(null);
	    	}
			instalacions.setExpedientContraincendis(multipartParams.getParametres().get("expedientContraincendis"));
			instalacions.setExpedientTermiques(multipartParams.getParametres().get("expedientTermiques"));
			instalacions.setExpedientPetrolifers(multipartParams.getParametres().get("expedientPetrolifers"));
			if (multipartParams.getParametres().get("dataPetrolifers") != null && ! multipartParams.getParametres().get("dataPetrolifers").isEmpty()) {
    	    	instalacions.setDataPetrolifers(formatter.parse(multipartParams.getParametres().get("dataPetrolifers")));
			} else {
	    		instalacions.setDataPetrolifers(null);
	    	}
			instalacions.setInstalacioPetrolifers(multipartParams.getParametres().get("instalacioPetrolifers"));
			instalacions.setDipositsPetrolifers(multipartParams.getParametres().get("dipositsPetrolifers"));
			instalacions.setCapTotalPetrolifers(multipartParams.getParametres().get("capTotalPetrolifers"));
			//instalacions.setExpedientGas(multipartParams.getParametres().get("expedient"));
			instalacions.setExpedientAscensor(multipartParams.getParametres().get("expedientAscensor"));
			instalacions.setExpedientEficienciaEnergetica(multipartParams.getParametres().get("expedientEficienciaEnergetica"));
			if (multipartParams.getParametres().get("dataRegistreEficienciaEnergetica") != null && ! multipartParams.getParametres().get("dataRegistreEficienciaEnergetica").isEmpty()) {
    	    	instalacions.setDataRegistreEficienciaEnergetica(formatter.parse(multipartParams.getParametres().get("dataRegistreEficienciaEnergetica")));
			} else {
	    		instalacions.setDataRegistreEficienciaEnergetica(null);
	    	}	
			instalacions.setExpedientPlaAutoproteccio(multipartParams.getParametres().get("expedientPlaAutoproteccio"));
			if (multipartParams.getParametres().get("dataCedulaHabitabilitat") != null && ! multipartParams.getParametres().get("dataCedulaHabitabilitat").isEmpty()) {
    	    	instalacions.setDataCedulaHabitabilitat(formatter.parse(multipartParams.getParametres().get("dataCedulaHabitabilitat")));
			} else {
	    		instalacions.setDataCedulaHabitabilitat(null);
	    	}	
			
			instalacions.setExpedientInstalacioGas(multipartParams.getParametres().get("expedientInstalacioGas"));
			instalacions.setTipusInstalacioGas(multipartParams.getParametres().get("tipusInstalacioGas"));
			if (multipartParams.getParametres().get("dataInstalacioGas") != null && ! multipartParams.getParametres().get("dataInstalacioGas").isEmpty()) {
    	    	instalacions.setDataInstalacioGas(formatter.parse(multipartParams.getParametres().get("dataInstalacioGas")));
			} else {
				instalacions.setDataInstalacioGas(null);
	    	}	
			
			InformeCore.actualitzarInstalacions(conn, idInforme, instalacions);
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioBaixaTensio"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio baixa tensio", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioFotovoltaica"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio fotovoltaica", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioContraincendis"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio contraincencis", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsCertificatEficienciaEnergetica"), idIncidencia, idActuacio, "", "", "", idInforme, "Certificat efici�ncia energ�tica", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioTermica"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio termica", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioAscensor"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio ascensor", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioAlarma"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio alarma", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioSubministreAigua"), idIncidencia, idActuacio, "", "", "", idInforme, "Subministre aigua", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsPlaAutoproteccio"), idIncidencia, idActuacio, "", "", "", idInforme, "Pla autoproteccio", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsCedulaDeHabitabilitat"), idIncidencia, idActuacio, "", "", "", idInforme, "Cedula habitabilitat", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsInstalacioPetrolifera"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalaci� petrolifers", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsInstalacioGas"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalaci� gas", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIniciActivitat"), idIncidencia, idActuacio, "", "", "", idInforme, "Inici activitat", Usuari.getIdUsuari());
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
