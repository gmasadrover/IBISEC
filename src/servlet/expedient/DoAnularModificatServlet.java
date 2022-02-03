package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.CreditCore;
import core.ExpedientCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoAnularModificatServlet
 */
@WebServlet("/anularModificat")
public class DoAnularModificatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAnularModificatServlet() {
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
		String errorString = null;
		String refExp = multipartParams.getParametres().get("expedient");
		String idActuacio = multipartParams.getParametres().get("idActuacio");
		String motiuAnulacio = multipartParams.getParametres().get("motiuAnulacio");
		String idInformeModificacio = multipartParams.getParametres().get("idMofificat");
		//Anular Expedient			
		ExpedientCore.anularModificacioExpedient(conn, idInformeModificacio, motiuAnulacio);
		//Anular Reserva crèdit
		CreditCore.anularReserva(conn, idInformeModificacio);		
		
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   
	  	// If error, forward to Edit page.	   
   		if (multipartParams.getParametres().get("redireccio").equals("actuacions")) {
			response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
		}else{
			response.sendRedirect(request.getContextPath() + "/expedient?ref=" + refExp);
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
