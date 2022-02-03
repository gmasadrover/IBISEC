package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.User;
import core.ControlPageCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CreateTecnicActuacioServlet
 */
@WebServlet("/createTecnicActuacio")
public class CreateTecnicActuacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTecnicActuacioServlet() {
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
 		   response.sendRedirect(request.getContextPath() + "/");
    	}else if (!usuari.getRol().contains("CAP") && !usuari.getRol().contains("ADMIN")) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String idInf = request.getParameter("idinf");  
	        InformeActuacio informe = new InformeActuacio();
	        List<User> llistaUsuaris = new ArrayList<User>();
	        informe = InformeCore.getInformePrevi(conn, idInf, false);		
			llistaUsuaris = UsuariCore.findUsuarisByDepartament(conn, usuari.getDepartament());
	       
	        // Store errorString in request attribute, before forward to views.	   
	        request.setAttribute("informe", informe);
	        request.setAttribute("llistaUsuaris", llistaUsuaris);
	        request.setAttribute("redireccio", request.getParameter("from"));
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"TecnicActuacio"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/actuacio/createTecnicActuacioView.jsp");
	        dispatcher.forward(request, response);
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
