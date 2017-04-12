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

import bean.Incidencia;
import bean.Registre;
import core.IncidenciaCore;
import core.RegistreCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateEntradaServlet
 */
@WebServlet("/DoCreateRegistreEntrada")
public class DoCreateEntradaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateEntradaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conn = MyUtils.getStoredConnection(request);
		
	    String referencia = request.getParameter("referencia");
	    String remitent = request.getParameter("remitent");
	    String tipus = URLDecoder.decode(request.getParameter("tipus"),  "UTF-8");	    
	    String idCentre = "";
	    String contingut = request.getParameter("contingut");
	    String idIncidencia = "";
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
	    	idCentre = request.getParameter("idCentre").split("_")[0];
	    }
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    String referenciaIncidencia = "";
	    Date peticio = new Date();
		try {
			peticio = formatter.parse(request.getParameter("peticio"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Registre registre = new Registre(referencia, peticio, tipus, remitent, contingut, idIncidencia, idCentre, "", idUsuari, new Date());
	    
	    String errorString = null;
	 	      
	   	if (errorString == null) {
	   		try {
	   			RegistreCore.nouRegistre(conn, "E", registre);	
	   			//Crear Incidencia
	   			if (!"-1".equals(idCentre) && "-1".equals(idIncidencia)) {
		   		    referenciaIncidencia = IncidenciaCore.getNewCode(conn);
		   		    Incidencia incidencia = new Incidencia();
		   		    incidencia.setIdIncidencia(referenciaIncidencia);
		   		    incidencia.setIdCentre(idCentre);
		   		    incidencia.setUsuCre(UsuariCore.findUsuariByID(conn, idUsuari));
		   		    incidencia.setDescripcio(contingut);
		   		    incidencia.setSolicitant(remitent);
		   		    IncidenciaCore.novaIncidencia(conn, incidencia);
		   			RegistreCore.actualitzarIdIncidencia(conn, "E", referencia, referenciaIncidencia);
	   			}	   			
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
	   				.getRequestDispatcher("/WEB-INF/views/registre/createEntradaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   			response.sendRedirect(request.getContextPath() + "/registre?tipus=E&referencia=" + referencia);
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
