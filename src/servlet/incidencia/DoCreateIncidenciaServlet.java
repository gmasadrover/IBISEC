package servlet.incidencia;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Incidencia;
import core.IncidenciaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateIncidenciaServlet
 */
@WebServlet("/DoCreateIncidencia")
public class DoCreateIncidenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateIncidenciaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = MyUtils.getStoredConnection(request);
		//Registrar actuaci�
	    String referencia = request.getParameter("referencia");
	    String idCentre = request.getParameter("idCentre");	   
	    String solicitant = request.getParameter("solicitant");
	    String descripcio = request.getParameter("descripcio");	    
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();
	    
	  
	   		Incidencia incidencia = new Incidencia();
			incidencia.setIdIncidencia(referencia);
			incidencia.setIdCentre(idCentre);
			incidencia.setUsuCre(UsuariCore.findUsuariByID(conn, idUsuari));
			incidencia.setDescripcio(descripcio);
			incidencia.setSolicitant(solicitant);
			IncidenciaCore.novaIncidencia(conn, incidencia);
	   	
	   
	   
	   		response.sendRedirect(request.getContextPath() + "/incidencies");
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
