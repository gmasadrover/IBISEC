package servlet.judicial;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.InformeActuacio;
import bean.Judicial;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import bean.Judicial.Tramitacio;
import bean.Oferta;
import core.CreditCore;
import core.InformeCore;
import core.JudicialCore;
import core.OfertaCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateTascaPagamentTramitacioServlet
 */
@WebServlet("/doCreateTascaPagamentTramitacio")
public class DoCreateTascaPagamentTramitacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateTascaPagamentTramitacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);		
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		User Usuari = MyUtils.getLoginedUser(request.getSession());	
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	    int idTasca = -1;
	    String idProcediment = multipartParams.getParametres().get("procediment");
	   	String comentari = multipartParams.getParametres().get("descripcio");
		String valor = multipartParams.getParametres().get("valor").replace(',','.');
		Tramitacio tramitacio = new Judicial().new Tramitacio();
		
	   	String errorString = null;	   		   	
	   	try {	
	   		//Cream Tramitació	
			tramitacio.setQuantia(multipartParams.getParametres().get("valor"));
			tramitacio.setTipus("Pagament");
			tramitacio.setDescripcio(comentari);
			tramitacio.setQuantia(valor);
			int idTramitacio = JudicialCore.novaTramitacio(conn, tramitacio, idProcediment, Usuari.getIdUsuari(), request.getRemoteAddr());
			JudicialCore.guardarFitxerTramitacio(conn, multipartParams.getFitxers(), idProcediment, idTramitacio, Usuari.getIdUsuari());
			
	   		int idUsuariTasca = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();   				
   			idTasca = TascaCore.novaTasca(conn, "pagamentJudicial", idUsuariTasca, Usuari.getIdUsuari(), String.valueOf(idTramitacio), "-1", comentari, "Despesa judicial", idProcediment, multipartParams.getFitxers(), request.getRemoteAddr(), "manual");
	   	} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.getMessage();
		}
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/tasca/createTascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.       
	   	
	   	response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
