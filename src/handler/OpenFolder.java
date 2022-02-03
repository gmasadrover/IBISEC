package handler;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import core.ConfiguracioCore;
import utils.MyUtils;

/**
 * Servlet implementation class OpenFolder
 */
@WebServlet("/OpenFolder")
public class OpenFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpenFolder() {
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
    	Connection conn = MyUtils.getStoredConnection(request);
        int idIncidencia = Integer.parseInt(request.getParameter("idIncidencia"));       
    	String ruta = "";
		ruta = ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
        File tmpFile = new File(ruta + "/documents/" + idIncidencia);
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		Desktop.getDesktop().open(new File(ruta + "/documents/" + idIncidencia));
		myObj.addProperty("success", true);	
		
        out.println(myObj.toString());
 
        out.close();
	}

}
