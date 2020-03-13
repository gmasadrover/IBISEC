package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.InformeActuacio;
import core.CreditCore;
import core.ExpedientCore;
import core.InformeCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoAnularExpedient
 */
@WebServlet("/anularExpedient")
public class DoAnularExpedient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAnularExpedient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String errorString = null;
		String refExp = multipartParams.getParametres().get("expedient");
		String idActuacio = multipartParams.getParametres().get("idActuacio");
		String motiuAnulacio = multipartParams.getParametres().get("motiuAnulacio");
		String idInforme = multipartParams.getParametres().get("idInforme");
		try {
			//Anular Expedient			
			ExpedientCore.anularExpedient(conn, refExp, motiuAnulacio);
			//Anular Reserva crèdit
			//CreditCore.anularReserva(conn, idInforme);
		} catch (SQLException e2) {
			errorString = e2.toString();
		}		
		
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
