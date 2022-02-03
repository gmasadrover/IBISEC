package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import core.ActuacioCore;
import core.CentreCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateActuacioServlet
 */
@WebServlet("/DoCreateActuacio")
public class DoCreateActuacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateActuacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conn = MyUtils.getStoredConnection(request);
		//Registrar actuaci�
	    String referencia = request.getParameter("referencia");
	    String idCentre = request.getParameter("idCentre");	   
	    String descripcio = request.getParameter("descripcio");	
	    String idIncidencia = request.getParameter("idIncidencia");	   
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    
	    Actuacio actuacio = new Actuacio();
	    actuacio.setReferencia(referencia);
	    actuacio.setDescripcio(descripcio);
	    try {
			actuacio.setCentre(CentreCore.findCentre(conn, idCentre, false));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    actuacio.setIdIncidencia(idIncidencia);
	    actuacio.setIdUsuariCreacio(idUsuari);
	    
	   
	   	referencia = ActuacioCore.novaActuacio(conn, actuacio);
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("actuacio", actuacio);
	  	
	    response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + referencia);
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
