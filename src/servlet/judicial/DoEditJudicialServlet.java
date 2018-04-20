package servlet.judicial;

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
       	String refOriginal = multipartParams.getParametres().get("referenciaOriginal");
       	String tipus = multipartParams.getParametres().get("tipus"); 
    	String canvi = multipartParams.getParametres().get("canvi"); 
       	Judicial procediment = new Judicial();
       	String errorString = null;
       	try {
       		if (canvi != null && canvi.equals("canviEstat")) {
       			JudicialCore.canviarEstatProcediment(conn, multipartParams.getParametres().get("estat"), ref);
       			refOriginal = ref;
       		} else {
       			procediment.setReferencia(ref);
    			procediment.setJutjat(multipartParams.getParametres().get("jutjat"));
    			procediment.setNumAutos(multipartParams.getParametres().get("numautos"));
    			procediment.setDemandant(multipartParams.getParametres().get("demandant"));
    			procediment.setDemandat(multipartParams.getParametres().get("demandat"));
    			procediment.setObjecteDemanda(multipartParams.getParametres().get("objecte"));
    			procediment.setQuantia(multipartParams.getParametres().get("quantia"));			
    			procediment.setNotes(multipartParams.getParametres().get("notes"));
           		if (tipus.equals("1ainstancia")) {       			
        			JudicialCore.modificarProcediment(conn, procediment);    			
           		} else if (tipus.equals("2ainstancia")) {       			
           			if (ref == null || ref.isEmpty()) {
           				ref = JudicialCore.nouProcediement(conn, procediment, refOriginal, tipus);
           				procediment.setReferencia(ref);
           			} else {
           				JudicialCore.modificarProcediment(conn, procediment);
           			}
           		} else if (tipus.equals("altresrecursosobert")) {       			
        			if (ref == null || ref.isEmpty()) {
           				ref = JudicialCore.nouProcediement(conn, procediment, refOriginal, tipus);
           				procediment.setReferencia(ref);
           			} else {
           				JudicialCore.modificarProcediment(conn, procediment);
           			}
           		} else if (tipus.equals("execucio")) {       			
        			if (ref == null || ref.isEmpty()) {
           				ref = JudicialCore.nouProcediement(conn, procediment, refOriginal, tipus);
           				procediment.setReferencia(ref);
           			} else {
           				JudicialCore.modificarProcediment(conn, procediment);
           			}
           		} else if (tipus.equals("recursexecucio")) {       			
        			if (ref == null || ref.isEmpty()) {
           				ref = JudicialCore.nouProcediement(conn, procediment, refOriginal, tipus);
           				procediment.setReferencia(ref);
           			} else {
           				JudicialCore.modificarProcediment(conn, procediment);
           			}
           		} else if (tipus.equals("mesurescautelars")) {       			
        			if (ref == null || ref.isEmpty()) {
           				ref = JudicialCore.nouProcediement(conn, procediment, refOriginal, tipus);
           				procediment.setReferencia(ref);
           			} else {
           				JudicialCore.modificarProcediment(conn, procediment);
           			}
           		}           		
           		JudicialCore.guardarFitxer(multipartParams.getFitxers(), ref);
           		procediment = JudicialCore.findProcediment(conn, refOriginal);
       		}
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
           response.sendRedirect(request.getContextPath() + "/procediment?ref=" + refOriginal);
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
