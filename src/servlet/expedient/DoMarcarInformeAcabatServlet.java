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
import core.CreditCore;
import core.InformeCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoMarcarInformeAcabatServlet
 */
@WebServlet("/DoMarcarInformeAcabat")
public class DoMarcarInformeAcabatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoMarcarInformeAcabatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        response.setContentType("text/html");
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
 
        JsonObject myObj = new JsonObject();      
        Connection conn = MyUtils.getStoredConnection(request);
		String idInforme = request.getParameter("idInforme");		
		InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
		InformeCore.modificarEstat(conn, idInforme, "acabat");	
		CreditCore.assignar(conn, idInforme, informe.getTotalFacturat());
		InformeCore.tancar(conn, idInforme);
		myObj.addProperty("success", true);	
		
        out.println(myObj.toString());
 
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
