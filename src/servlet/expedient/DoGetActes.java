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
 * Servlet implementation class DoGetActes
 */
@WebServlet("/getActes")
public class DoGetActes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoGetActes() {
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
		adjunts = InformeCore.getDocumentActaReplanteigSignat(conn, idIncidencia, idActuacio, idInf);		
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("actaReplanteig", llistatObj);
		adjunts = InformeCore.getDocumentActaComprovacioReplanteigSignat(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("actaComprovacioReplanteig", llistatObj);
		adjunts = InformeCore.getDocumentActaIniciObraSignat(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("actaIniciObra", llistatObj);
		adjunts = InformeCore.getDocumentActaAprovacioPlaSeguretatSignat(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("actaAprovacioPlaSeguretat", llistatObj);
		adjunts = InformeCore.getDocumentActaAprovacioResidusSignat(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("actaAprovacioResidus", llistatObj);
		adjunts = InformeCore.getDocumentActaAprovacioProgramaTreballSignat(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("actaAprovacioProgramaTreball", llistatObj);
		adjunts = InformeCore.getDocumentActaRecepcioSignat(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("actaRecepcio", llistatObj);
		adjunts = InformeCore.getDocumentActaMedicioGeneralSignat(conn, idIncidencia, idActuacio, idInf);
		llistatObj = gson.toJsonTree(adjunts);
		myObj.add("actaMedicioGeneral", llistatObj);		
		out.println(myObj.toString());
		out.close();
	}

}
