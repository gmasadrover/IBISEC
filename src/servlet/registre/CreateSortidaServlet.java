package servlet.registre;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.JudicialCore;
import core.RegistreCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CreateSortidaServlet
 */
@WebServlet("/novaSortida")
public class CreateSortidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSortidaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
        if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
        }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.registre_sort_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");
        }else{	       
	        String idIncidencia = request.getParameter("idIncidencia");
			String idInforme = request.getParameter("idInf");
			request.setAttribute("llistaProcediment", JudicialCore.getProcediments(conn, "-1", "-1"));
			request.setAttribute("idIncidencia", idIncidencia);
			request.setAttribute("idInforme", idInforme);
			request.setAttribute("nouCodi", RegistreCore.getNewCode(conn, "S"));
	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	        String today = df.format(new Date().getTime());	
	        request.setAttribute("data", today);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Registre"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/registre/createSortidaView.jsp");
	        dispatcher.forward(request, response);
 	   }
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
