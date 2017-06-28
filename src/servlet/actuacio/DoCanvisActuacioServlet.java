package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.User;
import core.ActuacioCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCanvisActuacio
 */
@WebServlet("/DoCanvisActuacio")
public class DoCanvisActuacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCanvisActuacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
Connection conn = MyUtils.getStoredConnection(request);
		
		//Registrar actuació
	    String idActuacio = request.getParameter("idActuacio");
	    String idIncidencia = request.getParameter("idIncidencia");
	    String idInforme = request.getParameter("idInforme");
	    String aprovarPA = request.getParameter("aprovarPA");
	    String tancar = request.getParameter("tancar");	   
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    	   	
   		try {
		    if (aprovarPA != null) { 
		    	//aprovam actuació
				ActuacioCore.aprovarPA(conn, idActuacio, Usuari.getIdUsuari());				
				//aprovam informe
				InformeCore.aprovacioInforme(conn, idInforme, Usuari.getIdUsuari());   		
	   		}else if (tancar != null) { // tancam actuació
	   			try {
	   				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Tancar actuació");
	   				ActuacioCore.tancar(conn, idActuacio, Usuari.getIdUsuari());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   			
	   		}else {
	   			
	   		} 
	    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    if (aprovarPA != null) { 
	    	response.sendRedirect(request.getContextPath() + "/CrearDocument?tipus=AutoritzacioPAObres&idIncidencia=" + idIncidencia + "&idActuacio=" + idActuacio + "&idInforme=" + idInforme); 
	    }else{
	    	response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
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
