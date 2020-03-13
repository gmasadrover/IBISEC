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
 * Servlet implementation class DoGetDocumentacioTecnica
 */
@WebServlet("/getDocumentacioTecnica")
public class DoGetDocumentacioTecnica extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoGetDocumentacioTecnica() {
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
			adjunts = InformeCore.getDocumentacioTecnia(conn, idIncidencia, idActuacio, idInf);		
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentacioTecnica", llistatObj);
			adjunts = InformeCore.getInformeSupervisio(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("informeSupervisio", llistatObj);
			adjunts = InformeCore.getProjecte(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("projecte", llistatObj);
			adjunts = InformeCore.getNomenamentDF(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("nomenamentDF", llistatObj);
			adjunts = InformeCore.getPSS(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentPSS", llistatObj);
			adjunts = InformeCore.getPGR(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentPGR", llistatObj);
			adjunts = InformeCore.getPlaTreball(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentPlaTreball", llistatObj);
			adjunts = InformeCore.getDocumentFinalitzacioContratistaSignat(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentFinalitzacioContratista", llistatObj);
			adjunts = InformeCore.getDocumentInformeDOSignat(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentInformeDO", llistatObj);
			adjunts = InformeCore.getDocumentCFOSignat(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentCFO", llistatObj);
			adjunts = InformeCore.getDocumentRepresentacioRecepcioSignat(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentRepresentacioRecepcio", llistatObj);
			adjunts = InformeCore.getCertificacioFinal(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentCertificacioFinal", llistatObj);
			adjunts = InformeCore.getDevolucioAval(conn, idIncidencia, idActuacio, idInf);
			llistatObj = gson.toJsonTree(adjunts);
			myObj.add("documentDevolucioAval", llistatObj);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		out.println(myObj.toString());
		out.close();
	}

}
