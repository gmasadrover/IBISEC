package servlet.judicial;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Judicial;
import core.JudicialCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditJudicialServlet
 */
@WebServlet("/doEditProcediment")
public class DoEditJudicialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditJudicialServlet() {
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
       	
       	String ref = multipartParams.getParametres().get("referencia");
       	Judicial procediment = new Judicial();
       	String errorString = null;
       	try {
			procediment.setReferencia(ref);
			procediment.setJutjat(multipartParams.getParametres().get("jutjat"));
			procediment.setNumAutos(multipartParams.getParametres().get("numautos"));
			procediment.setDemandant(multipartParams.getParametres().get("demandant"));
			procediment.setDemandat(multipartParams.getParametres().get("demandat"));
			procediment.setObjecteDemanda(multipartParams.getParametres().get("objecte"));
			procediment.setQuantia(multipartParams.getParametres().get("quantia"));
			procediment.setEstat(multipartParams.getParametres().get("estat"));
			procediment.setNotes(multipartParams.getParametres().get("notes"));
			JudicialCore.modificarProcediment(conn, procediment);
			JudicialCore.guardarFitxer(multipartParams.getFitxers(), ref);
		} catch (SQLException | NamingException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("procediment", procediment);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/judicial/editJudicialView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
           response.sendRedirect(request.getContextPath() + "/procediment?ref=" + ref);
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
