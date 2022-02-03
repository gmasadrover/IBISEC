package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Tasca;
import bean.User;
import core.TascaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCanvisTascaServlet
 */
@WebServlet("/DoCanvisTasca")
public class DoCanvisTascaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCanvisTascaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		
	    int idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));	   
	    String tipus = multipartParams.getParametres().get("tipusTasca");
	    String modificar= multipartParams.getParametres().get("modificar");
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	 	  
	    String errorString = null;
	  
	    try {
	    	Tasca tasca = TascaCore.findTascaId(conn, idTasca, Usuari.getIdUsuari());
	    	if (modificar != null) {
	    		TascaCore.modificarTipus(conn, idTasca, tipus);
	    	} else {
	    		TascaCore.reasignar(conn, Usuari.getIdUsuari(), idTasca, tipus, tasca.getDescripcio());
	    	}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.toString();
		}
	   	
	   
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/tascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);	   		
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
