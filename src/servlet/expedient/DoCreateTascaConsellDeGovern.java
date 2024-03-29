package servlet.expedient;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import bean.InformeActuacio;
import bean.User;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateTascaConsellDeGovern
 */
@WebServlet("/DoCreateTascaConsellDeGovern")
public class DoCreateTascaConsellDeGovern extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateTascaConsellDeGovern() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);		}

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
		User Usuari = MyUtils.getLoginedUser(request.getSession());	
		String idInforme = request.getParameter("informe");
		InformeActuacio informe = new InformeActuacio();
		informe = InformeCore.getInformePrevi(conn, idInforme, false);
		int idUsuari = UsuariCore.finCap(conn, "juridica").getIdUsuari();
		String comentari = "Sol�licitud aprovaci� consell de govern expedient: " + informe.getExpcontratacio().getExpContratacio();			
		TascaCore.novaTasca(conn, "consellDeGovern", idUsuari, Usuari.getIdUsuari(), informe.getActuacio().getReferencia(), informe.getActuacio().getIdIncidencia(), comentari, "Sol�licitud aprovaci� consell de govern", idInforme, null, request.getRemoteAddr(), "automatic");
		myObj.addProperty("success", true);	
		
        out.println(myObj.toString());
 
        out.close();
	}

}
