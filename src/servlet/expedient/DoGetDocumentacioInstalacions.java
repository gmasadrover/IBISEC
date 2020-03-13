package servlet.expedient;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
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
		try {
			adjunts = InformeCore.getDocumentsIntalacioBaixaTensio(conn, idIncidencia, idActuacio, idInf);		
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentsIntalacioBaixaTensio", llistatObj);
			adjunts = InformeCore.getDocumentsIntalacioFotovoltaica(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentsIntalacioFotovoltaica", llistatObj);
			adjunts = InformeCore.getDocumentsIntalacioContraincendis(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentsIntalacioContraincendis", llistatObj);
			adjunts = InformeCore.getDocumentsCertificatEficienciaEnergetica(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentsCertificatEficienciaEnergetica", llistatObj);
			adjunts = InformeCore.getDocumentsIntalacioTermica(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentsIntalacioTermica", llistatObj);
			adjunts = InformeCore.getDocumentsIntalacioAscensor(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentsIntalacioAscensor", llistatObj);
			adjunts = InformeCore.getDocumentsIntalacioAlarma(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentsIntalacioAlarma", llistatObj);
			adjunts = InformeCore.getDocumentsIntalacioSubministreAigua(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentsIntalacioSubministreAigua", llistatObj);
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
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		out.println(myObj.toString());
		out.close();
	}

}
