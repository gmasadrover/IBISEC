package handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.ActuacioCore;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class SeguirActuacio
 */
@WebServlet("/seguirActuacio")
public class SeguirActuacio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeguirActuacio() {
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
        String idActuacio = request.getParameter("idActuacio");
        int idUsuari =  Integer.parseInt(request.getParameter("idUsuari"));    
        boolean seguir = Boolean.parseBoolean(request.getParameter("seguir"));
        Connection conn = MyUtils.getStoredConnection(request);       
		try {
			if (seguir) {
				ActuacioCore.seguirActuacio(conn, idActuacio, idUsuari);
			} else {
				ActuacioCore.desSeguirActuacio(conn, idActuacio, idUsuari);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}              
        out.println();
 
        out.close();
	}

}
