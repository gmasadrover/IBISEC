package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Instalacions;
import bean.User;
import core.ExpedientCore;
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
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
			instalacions.setExpedientFotovoltaica(multipartParams.getParametres().get("expedient"));
			//instalacions.setDataOCAFotovoltaica(rs.getTimestamp("fotovoltaicadataoca"));
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
			
			InformeCore.actualitzarInstalacions(conn, idInforme, instalacions);
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioBaixaTensio"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio baixa tensio", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioBaixaTensio"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio fotovoltaica", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioContraincendis"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio contraincencis", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsCertificatEficienciaEnergetica"), idIncidencia, idActuacio, "", "", "", idInforme, "Certificat eficiència energètica", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioTermica"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio termica", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioAscensor"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio ascensor", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioAlarma"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalacio alarma", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsIntalacioSubministreAigua"), idIncidencia, idActuacio, "", "", "", idInforme, "Subministre aigua", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsPlaAutoproteccio"), idIncidencia, idActuacio, "", "", "", idInforme, "Pla autoproteccio", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsCedulaDeHabitabilitat"), idIncidencia, idActuacio, "", "", "", idInforme, "Cedula habitabilitat", Usuari.getIdUsuari());
			Fitxers.guardarFitxer(conn, multipartParams.getFitxersByName().get("documentsInstalacioPetrolifera"), idIncidencia, idActuacio, "", "", "", idInforme, "Instalació petrolifers", Usuari.getIdUsuari());
		} catch (NamingException | SQLException | ParseException e) {
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
