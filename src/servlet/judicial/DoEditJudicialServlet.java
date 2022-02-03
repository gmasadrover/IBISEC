package servlet.judicial;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Judicial;
import bean.User;
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
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
       	String ref = multipartParams.getParametres().get("referencia");
       	String refOriginal = multipartParams.getParametres().get("referenciaOriginal");
       	String tipus = multipartParams.getParametres().get("tipus"); 
    	String canvi = multipartParams.getParametres().get("canvi"); 
       	Judicial procediment = new Judicial();
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
					ref = JudicialCore.nouProcediment(conn, procediment, refOriginal, tipus);
					procediment.setReferencia(ref);
				} else {
					JudicialCore.modificarProcediment(conn, procediment);
				}
			} else if (tipus.equals("altresrecursosobert")) {       			
				if (ref == null || ref.isEmpty()) {
					ref = JudicialCore.nouProcediment(conn, procediment, refOriginal, tipus);
					procediment.setReferencia(ref);
				} else {
					JudicialCore.modificarProcediment(conn, procediment);
				}
			} else if (tipus.equals("execucio")) {       			
				if (ref == null || ref.isEmpty()) {
					ref = JudicialCore.nouProcediment(conn, procediment, refOriginal, tipus);
					procediment.setReferencia(ref);
				} else {
					JudicialCore.modificarProcediment(conn, procediment);
				}
			} else if (tipus.equals("recursexecucio")) {       			
				if (ref == null || ref.isEmpty()) {
					ref = JudicialCore.nouProcediment(conn, procediment, refOriginal, tipus);
					procediment.setReferencia(ref);
				} else {
					JudicialCore.modificarProcediment(conn, procediment);
				}
			} else if (tipus.equals("mesurescautelars")) {       			
				if (ref == null || ref.isEmpty()) {
					ref = JudicialCore.nouProcediment(conn, procediment, refOriginal, tipus);
					procediment.setReferencia(ref);
				} else {
					JudicialCore.modificarProcediment(conn, procediment);
				}
			}           		
			JudicialCore.guardarFitxer(conn, multipartParams.getFitxers(), ref, Usuari.getIdUsuari());
			procediment = JudicialCore.findProcediment(conn, refOriginal);
		}
		// Store infomation to request attribute, before forward to views.
		
		request.setAttribute("procediment", procediment);
 
 
		response.sendRedirect(request.getContextPath() + "/procediment?ref=" + refOriginal);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
