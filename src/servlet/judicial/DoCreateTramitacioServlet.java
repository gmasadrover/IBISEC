package servlet.judicial;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Judicial;
import bean.User;
import bean.Judicial.Tramitacio;
import core.JudicialCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateSentenciaServlet
 */
@WebServlet("/doCreateTramitacio")
public class DoCreateTramitacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateTramitacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
       	Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);       	
       	String ref = multipartParams.getParametres().get("referencia");
       	String refPro = multipartParams.getParametres().get("procediment");
       	Tramitacio tramitacio = new Judicial().new Tramitacio();
       	String errorString = null;
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       	try {
       		tramitacio.setNumstcia(ref);
       		//tramitacio.setSentencia(multipartParams.getParametres().get("sentencia"));
			if (multipartParams.getParametres().get("dataDocument") != null && ! multipartParams.getParametres().get("dataDocument").isEmpty()) {
				tramitacio.setDataDocument(formatter.parse(multipartParams.getParametres().get("dataDocument")));
			}
			//tramitacio.setQuantia(multipartParams.getParametres().get("quantia"));
			//tramitacio.setRecurs(multipartParams.getParametres().get("recurs"));
			if (multipartParams.getParametres().get("dataRegistre") != null && ! multipartParams.getParametres().get("dataRegistre").isEmpty()) {
				tramitacio.setDataRegistre(formatter.parse(multipartParams.getParametres().get("dataRegistre")));
			}
			tramitacio.setTipus(multipartParams.getParametres().get("llistatTipus"));
			//tramitacio.setTermini(multipartParams.getParametres().get("termini"));
			tramitacio.setDescripcio(multipartParams.getParametres().get("descripcio"));
			tramitacio.setPendentTercers(multipartParams.getParametres().get("pendentTercers") != null);
			tramitacio.setPendentProvisio(multipartParams.getParametres().get("pendentProvisio") != null);
			tramitacio.setNotes(multipartParams.getParametres().get("notes"));
			int idTramitacio = JudicialCore.novaTramitacio(conn, tramitacio, refPro, usuari.getIdUsuari(), request.getRemoteAddr());
			JudicialCore.guardarFitxerTramitacio(conn, multipartParams.getFitxers(), refPro, idTramitacio, usuari.getIdUsuari());
		} catch (ParseException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("tramitacio", tramitacio);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/judicial/editJudicialView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
           response.sendRedirect(request.getContextPath() + "/procediment?ref=" + refPro);
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
