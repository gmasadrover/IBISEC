package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import core.ExpedientCore;
import core.InformeCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateEntradaServlet
 */
@WebServlet("/DoCreateExpedient")
public class DoCreateExpedientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateExpedientServlet() {
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
		
		String idInforme = multipartParams.getParametres().get("idInforme");
    	InformeActuacio informe = new InformeActuacio();
    	String nouCodi = "";    	
		informe = InformeCore.getInformePrevi(conn, idInforme, false);
		nouCodi = ExpedientCore.crearExpedient(conn,  informe, false, "", informe.getUsuari().getIdUsuari());		
		
	  
	   
	  	// If error, forward to Edit page.
	   
	   			response.sendRedirect(request.getContextPath() + "/expedient?ref=" + nouCodi);
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
