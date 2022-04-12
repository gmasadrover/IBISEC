package servlet.expedient;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import core.InformeCore;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class DoGetDocumentacioInstalacions
 */
@WebServlet("/getDocumentacioInstalacions")
public class DoGetDocumentacioInstalacions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoGetDocumentacioInstalacions() {
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
		PrintWriter out = response.getWriter();
        response.setContentType("text/html");
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
 
        JsonObject myObj = new JsonObject(); 
        Gson gson = new Gson();
        
		Connection conn = MyUtils.getStoredConnection(request);			
		String idIncidencia = request.getParameter("idIncidencia");
		String idActuacio = request.getParameter("idActuacio");
		String idInf = request.getParameter("idInf");
		List<Fitxer> adjunts = new ArrayList<Fitxer>();
		myObj.addProperty("success", true);	
		JsonElement llistatObj = null;
		adjunts = InformeCore.getDocumentsInstalacioBaixaTensio(conn, idIncidencia, idActuacio, idInf);		
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsInstalacioBaixaTensio", llistatObj);
		adjunts = InformeCore.getDocumentsInstalacioFotovoltaica(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsInstalacioFotovoltaica", llistatObj);
		adjunts = InformeCore.getDocumentsInstalacioContraincendis(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsInstalacioContraincendis", llistatObj);
		adjunts = InformeCore.getDocumentsCertificatEficienciaEnergetica(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsCertificatEficienciaEnergetica", llistatObj);
		adjunts = InformeCore.getDocumentsInstalacioTermica(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsInstalacioTermica", llistatObj);
		adjunts = InformeCore.getDocumentsInstalacioAscensor(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsInstalacioAscensor", llistatObj);
		adjunts = InformeCore.getDocumentsInstalacioAlarma(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsInstalacioAlarma", llistatObj);
		adjunts = InformeCore.getDocumentsInstalacioSubministreAigua(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsInstalacioSubministreAigua", llistatObj);
		adjunts = InformeCore.getDocumentsPlaAutoproteccio(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsPlaAutoproteccio", llistatObj);
		adjunts = InformeCore.getDocumentsCedulaDeHabitabilitat(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsCedulaDeHabitabilitat", llistatObj);
		adjunts = InformeCore.getDocumentsInstalacioPetrolifera(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsInstalacioPetrolifera", llistatObj);
		adjunts = InformeCore.getDocumentsInstalacioGas(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsInstalacioGas", llistatObj);	
		adjunts = InformeCore.getDocumentsIniciActivitat(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsIniciActivitat", llistatObj);
		
		
		
		out.println(myObj.toString());
		out.close();
	}

}
