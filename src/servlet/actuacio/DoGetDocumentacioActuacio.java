package servlet.actuacio;

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

import core.ActuacioCore;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class DoGetDocumentacioActuacio
 */
@WebServlet("/getDocumentacioActuacio")
public class DoGetDocumentacioActuacio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoGetDocumentacioActuacio() {
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
    	Connection conn = MyUtils.getStoredConnection(request);		
        JsonObject myObj = new JsonObject(); 
        Gson gson = new Gson();
        		
		String idIncidencia = request.getParameter("idIncidencia");		
		String idActuacio = request.getParameter("idActuacio");		
		List<Fitxer> adjunts = new ArrayList<Fitxer>();
		adjunts = ActuacioCore.findDocumentsTasques(idIncidencia, conn);	
		adjunts.addAll(ActuacioCore.findDocumentsAltres(idIncidencia, idActuacio, conn));
		
		myObj.addProperty("success", true);	
		JsonElement llistatObj = gson.toJsonTree(adjunts);
		myObj.add("documentsActuacio", llistatObj);
		
		out.println(myObj.toString());
		out.close();
	}

}
