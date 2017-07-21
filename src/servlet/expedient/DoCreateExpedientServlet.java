package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Expedient;
import bean.InformeActuacio;
import core.ActuacioCore;
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
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String errorString = null;
		String idInforme = multipartParams.getParametres().get("idInforme");
		String descripcio = multipartParams.getParametres().get("descripcio");
    	double importObraMajor = Double.parseDouble(getServletContext().getInitParameter("importObraMajor"));
    	InformeActuacio informe = new InformeActuacio();
    	String nouCodi = "";
    	String contracte = "";
    	String tipus = "";
    	Expedient nouExpedient = new Expedient();
		try {
			informe = InformeCore.getInformePrevi(conn, idInforme);
			nouCodi = ExpedientCore.getNouCodiExpedient(conn, informe, importObraMajor);
			
			if (informe.getPropostaInformeSeleccionada().getVec() < importObraMajor) { // Contractes menors		
				contracte = "menor";
			} else {																   // Contractes majors
				contracte = "major";
			}
			if (!informe.getPropostaInformeSeleccionada().isContracte()) {             // Fora contractes
				contracte = "pd";
			}
			if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("obr")) {
				tipus = "obra";				
			} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("srv")) {
				tipus = "servei";			
			} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("submi")) {
				tipus = "subministrament";			
			}
			
			if (informe.getOfertaSeleccionada().getVec() < importObraMajor) {
				nouExpedient.setDataAdjudicacio(ActuacioCore.findActuacio(conn, informe.getIdActuacio()).getDataAprovacio());
			}
			
			nouExpedient.setExpContratacio(nouCodi);
			nouExpedient.setDescripcio(descripcio);		
			nouExpedient.setTipus(tipus);
			nouExpedient.setContracte(contracte);
			ExpedientCore.crearExpedient(conn, nouExpedient);
			InformeCore.assignarExpedient(conn, idInforme, nouCodi);
		} catch (SQLException e2) {
			errorString = e2.toString();
		}		
		
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/expedients/createExpedientView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   			response.sendRedirect(request.getContextPath() + "/expedient?ref=" + nouCodi);
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
