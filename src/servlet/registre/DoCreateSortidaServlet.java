package servlet.registre;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Registre;
import core.IncidenciaCore;
import core.RegistreCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateSortidaServlet
 */
@WebServlet("/DoCreateSortidaServlet")
public class DoCreateSortidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateSortidaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
Connection conn = MyUtils.getStoredConnection(request);
		
	    String referencia = request.getParameter("referencia");
	    String remitent = request.getParameter("destinatari");
	    String tipus = URLDecoder.decode(request.getParameter("tipus"),  "UTF-8");	    
	    String idCentre = "";
	    String contingut = request.getParameter("contingut");
	    String idIncidencia = "";
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    Date peticio = new Date();
		try {
			if (request.getParameter("idIncidenciaSeleccionada") != null && request.getParameter("idIncidenciaSeleccionada") != "") {
		    	idIncidencia = request.getParameter("idIncidenciaSeleccionada");
		    	try {
					idCentre = IncidenciaCore.findIncidencia(conn,idIncidencia).getIdCentre();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } else {
				idIncidencia = request.getParameter("idIncidencia");
		    	idCentre = request.getParameter("idCentre");			
				peticio = formatter.parse(request.getParameter("peticio"));
		    }
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Registre registre = new Registre(referencia, peticio, tipus, remitent, contingut, idIncidencia, idCentre, "", idUsuari, new Date());
	    
	    String errorString = null;
	 	      
	   	if (errorString == null) {
	   		try {
	   			RegistreCore.nouRegistre(conn, "S", registre);
	   		} catch (SQLException e) {
	  			e.printStackTrace();
	  			errorString = e.getMessage();
	   		}
	   	}
	    
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("registre", registre);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/registre/createSortidaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		response.sendRedirect(request.getContextPath() + "/registreSortida");
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
