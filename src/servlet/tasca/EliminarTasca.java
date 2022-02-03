package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class EliminarTasca
 */
@WebServlet("/eliminarTasca")
public class EliminarTasca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarTasca() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
 	   	if (MyUtils.getLoginedUser(request.getSession()) == null){
 	   		response.sendRedirect(request.getContextPath() + "/preLogin");
 	   	}else if (!usuari.getRol().contains("ADMIN")) {
 	   		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{
 	   		int idTasca = Integer.parseInt(request.getParameter("idtasca"));
 	   		TascaCore.eliminarTasca(conn, idTasca);
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
