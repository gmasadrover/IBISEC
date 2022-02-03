package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class ObrirTasca
 */
@WebServlet("/obrirTasca")
public class ObrirTasca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObrirTasca() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
 	   	if (MyUtils.getLoginedUser(request.getSession()) == null){
 	   		response.sendRedirect(request.getContextPath() + "/preLogin"); 	   
 	   	}else{
 	   		int idTasca = Integer.parseInt(request.getParameter("idtasca"));
 	   		TascaCore.obrirTasca(conn, idTasca);
 	   		response.sendRedirect(request.getContextPath() + "/");	
 	   	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
