package handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
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

import bean.Actuacio;
import bean.User;
import core.ActuacioCore;
import core.CentreCore;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class NewNotificacio
 */
@WebServlet("/NewNotificacio")
public class NewNotificacio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewNotificacio() {
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
        User Usuari = MyUtils.getLoginedUser(request.getSession());	         
        if (Usuari != null) {
        	System.out.println("entra: " + Usuari.getIdUsuari());
            try {
    			myObj.addProperty("success", true);
    			boolean haveNewTasca = TascaCore.haveNewTasca(conn, Usuari.getIdUsuari());
    			JsonElement novaTascaObj = gson.toJsonTree(haveNewTasca);
    			myObj.add("novesTasques", novaTascaObj);
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			myObj.addProperty("success", false);
    		}              
        } else {
        	myObj.addProperty("success", true);
        	JsonElement novaTascaObj = gson.toJsonTree(false);
			myObj.add("novesTasques", novaTascaObj);
        }        
        out.println(myObj.toString());
        out.close();
	}

}
