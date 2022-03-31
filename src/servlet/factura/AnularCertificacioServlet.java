package servlet.factura;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import bean.User;
import core.ConfiguracioCore;
import core.FacturaCore;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class AnularCertificacioServlet
 */
@WebServlet("/anularCertificacio")
public class AnularCertificacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnularCertificacioServlet() {
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
		String idFactura = request.getParameter("idFactura");
		String motiu = request.getParameter("motiu");
		FacturaCore.anularCertificacio(conn, idFactura, motiu);
		int usuariTasca = ConfiguracioCore.getConfiguracio(conn).getIdUsuariCertificacions();
		User Usuari = MyUtils.getLoginedUser(request.getSession());	
		TascaCore.novaTasca(conn, "generic", usuariTasca, Usuari.getIdUsuari(), "", "", "<span class='missatgeAutomatic'>S'ha anul·lat la certificació <a target='_blank' href='certificacioDetalls?ref=" + idFactura + "'>" + idFactura + "</a></span><br>Motiu: " + motiu, "Certificació anul·lada", "-1", null, request.getRemoteAddr(), "manual");
		myObj.addProperty("success", true);	
		
        out.println(myObj.toString());
 
        out.close();
	}

}
