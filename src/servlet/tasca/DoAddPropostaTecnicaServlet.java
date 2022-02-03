package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.InformeActuacio.PropostaInforme;
import core.InformeCore;
import core.OfertaCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddPresupostsServlet
 */
@WebServlet("/DoAddPropostaTecnica")
public class DoAddPropostaTecnicaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddPropostaTecnicaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = MyUtils.getStoredConnection(request);		
	    int idTasca = Integer.parseInt(request.getParameter("idTasca"));
	    String idIncidencia = request.getParameter("idIncidencia");
	    String idActuacio = request.getParameter("idActuacio");
	    String idInforme = request.getParameter("idInformePrevi");
	    String comentari = request.getParameter("propostaTecnica");	   
	    String errorString = null;
	    String termini = request.getParameter("termini");
	    String seleccionada = request.getParameter("idOfertaSeleccionada");
	    Boolean EBSS = "on".equals(request.getParameter("ebss"));
	    Boolean coordinacio = "on".equals(request.getParameter("coordinacio"));
	    //Agafam totes les ofertes
	    String guardar = request.getParameter("guardar");
	    if (guardar != null) {
	    	if (seleccionada != null && !seleccionada.isEmpty()){
			  	OfertaCore.seleccionarOferta(conn, idInforme, seleccionada, termini, comentari);
				InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
				PropostaInforme proposta = informe.getPropostaInformeSeleccionada();
				proposta.setEbss(EBSS);
				proposta.setCoordinacio(coordinacio);
				InformeCore.modificarProposta(conn, proposta);
				/*;*/ 
	    	}else{
	    		errorString = "S'ha de seleccionar l'empresa adjudicatària";
	    	}
	    } else {	    	
	   		
	    }
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null && !errorString.isEmpty()) {	
	   		response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca + "&error=ofertaSeleccionada");	  
	   	} else {
	   		if (guardar != null) {
	   			response.sendRedirect(request.getContextPath() + "/CrearDocument?tipus=PTObres&idIncidencia=" + idIncidencia + "&idActuacio=" + idActuacio + "&idInforme=" + idInforme); 
	   		} else {
	   			response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);	  
	   		}
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
