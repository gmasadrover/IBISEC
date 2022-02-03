package handler;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import core.CreditCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class ModificarAssignacioPartida
 */
@WebServlet("/modificarAssignacioPartida")
public class ModificarAssignacioPartida extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificarAssignacioPartida() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		Connection conn = MyUtils.getStoredConnection(request);		
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		
		String eliminar =  multipartParams.getParametres().get("eliminar");
	    String expedient = multipartParams.getParametres().get("expedient");
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idInforme = multipartParams.getParametres().get("idInforme");
	    String redireccio = multipartParams.getParametres().get("redireccio");	   
	    String idAssignacio = multipartParams.getParametres().get("idAssignacio");	  
	    String llistaPartides = multipartParams.getParametres().get("llistaPartides");	  
	    String llistaSubPartides = multipartParams.getParametres().get("llistaSubPartides");	  
	    double valorAssignacio =  Double.parseDouble(multipartParams.getParametres().get("valorAssignacio"));	  
	    
	    String errorString = null;
	    if (eliminar == null) {
	    	CreditCore.reservar(conn, llistaSubPartides, idActuacio, idInforme, idAssignacio, valorAssignacio, "", Usuari.getIdUsuari());
	    } else {
	    	CreditCore.eliminarReservar(conn, idAssignacio);
	    }
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	    response.sendRedirect(request.getContextPath() + redireccio);
		
	}

}
