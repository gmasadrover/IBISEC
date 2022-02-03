package servlet.tasca;

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
import core.OfertaCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoDeleteOfertaServlet
 */
@WebServlet("/DoDeleteOferta")
public class DoDeleteOfertaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoDeleteOfertaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
        response.setContentType("text/html");
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
 
        JsonObject myObj = new JsonObject(); 
        
		Connection conn = MyUtils.getStoredConnection(request);	
		User usuari = MyUtils.getLoginedUser(request.getSession());
		String idOferta = request.getParameter("idOferta");
		OfertaCore.deleteOferta(conn, idOferta, usuari.getIdUsuari());
		
		myObj.addProperty("success", true);	
		
        out.println(myObj.toString());
 
        out.close();
	}

}
