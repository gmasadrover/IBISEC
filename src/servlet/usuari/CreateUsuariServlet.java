package servlet.usuari;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Actuacio;
import bean.Factura;
import bean.Registre;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.ControlPageCore;
import core.EmpresaCore;
import core.FacturaCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class CreateUsuariServlet
 */
@WebServlet("/nouUsuari")
public class CreateUsuariServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUsuariServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
        if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
        }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.personal)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{
 	   		request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"personal"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/usuari/nouUsuariView.jsp");
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
