package handler;

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

import bean.Tasca;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class LlistatActuacionsActives
 */
@WebServlet("/TasquesActuacio")
public class TasquesActuacio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TasquesActuacio() {
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
        Gson gson = new Gson(); 
        JsonObject myObj = new JsonObject();
        Connection conn = MyUtils.getStoredConnection(request);
        List<Tasca> llistatTasques = new ArrayList<Tasca>();
        String idActuacio = request.getParameter("idActuacio");
        String idInforme = request.getParameter("idInforme");
        Boolean withCancelades = Boolean.parseBoolean(request.getParameter("withCancelades"));
		if (!idInforme.equals("-1")) {
			llistatTasques = TascaCore.findTasquesInforme(conn, idInforme, withCancelades);
		} else {
			llistatTasques = TascaCore.findTasquesActuacio(conn, idActuacio, withCancelades);
		}
		myObj.addProperty("success", true);
		myObj.addProperty("idInforme", idInforme);
		JsonElement llistatObj = gson.toJsonTree(llistatTasques);
		myObj.add("llistatTasques", llistatObj);              
        out.println(myObj.toString());
 
        out.close();
	}

}

