package servlet.feina;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.Actuacio.Feina;
import core.ActuacioCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditFeinaServlet
 */
@WebServlet("/DoEditFeina")
public class DoEditFeinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditFeinaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);		
	    String idFeina = request.getParameter("idFeina");
	    String nomRemitent = request.getParameter("remitent");
	    String nomDestinatari = request.getParameter("destinatari");	   
	    String contingut = request.getParameter("contingut");	
	    String notes = request.getParameter("notes");	  
	    String idActuacio = request.getParameter("idActuacio");
	    Feina feina = new Actuacio().new Feina();
		feina.setIdFeina(idFeina);
		feina.setNomRemitent(nomRemitent);
		feina.setNomDestinatari(nomDestinatari);
		feina.setContingut(contingut);
		feina.setNotes(notes);
		ActuacioCore.modificarFeina(conn, feina);   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("feina", feina);
	   	request.setAttribute("idActuacio", idActuacio);

	   	response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
